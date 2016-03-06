package me.choco.arrows.events;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.BlockProjectileSource;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.AlchemicalArrow;
import me.choco.arrows.utils.ArrowRegistry;

public class ProjectileShoot implements Listener{

	Random random = new Random();
	
	AlchemicalArrows plugin;
	public ProjectileShoot(AlchemicalArrows plugin){
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onShootArrow(ProjectileLaunchEvent event){
		if (!(event.getEntity() instanceof Arrow)) return;
		Arrow arrow = (Arrow) event.getEntity();
		
		if (arrow.getShooter() instanceof Player){
			Player player = (Player) arrow.getShooter();
			if (!player.getInventory().contains(Material.ARROW)) return;
			
			//Register the arrow if it's in the arrow registry
			ItemStack reference = player.getInventory().getItem(player.getInventory().first(Material.ARROW));
			ItemStack item = new ItemStack(reference.getType()); item.setItemMeta(reference.hasItemMeta() ? reference.getItemMeta() : null);
			if (ArrowRegistry.getArrowRegistry().containsKey(item)){
				try{
					plugin.getArrowRegistry().registerAlchemicalArrow(ArrowRegistry.getArrowRegistry().get(item).getDeclaredConstructor(Arrow.class).newInstance(arrow));
				}catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e){
					plugin.getLogger().info("Something went wrong while attempting to register arrow " + ArrowRegistry.getArrowRegistry().get(item));
					e.printStackTrace(); 
				}
			}else{ return; }
			
			AlchemicalArrow aarrow = plugin.getArrowRegistry().getAlchemicalArrow(arrow);
			if (plugin.worldGuardEnabled){
				ApplicableRegionSet regions = WGBukkit.getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation());
				if (regions.allows(DefaultFlag.PVP)){
					aarrow.shootEventHandler(event);
					aarrow.onShootFromPlayer(player);
				}else if ((!regions.allows(DefaultFlag.PVP) || event.isCancelled()) && !player.hasPermission("arrows.worldguardoverride")){
					player.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "You cannot shoot alchemical arrows from a PvP protected region");
					plugin.getArrowRegistry().unregisterAlchemicalArrow(aarrow);
					event.setCancelled(true);
				}
			}else{
				aarrow.shootEventHandler(event);
				aarrow.onShootFromPlayer(player);
			}
			
			if (player.getInventory().getItemInMainHand() == null) return;
			else if (player.getInventory().getItemInOffHand() == null) return;
			if (!player.getGameMode().equals(GameMode.CREATIVE)){
				if (player.getInventory().getItemInMainHand().getEnchantments().containsKey(Enchantment.ARROW_INFINITE)){
					if (reference.getAmount() > 1) reference.setAmount(reference.getAmount() - 1);
					else{ player.getInventory().setItem(player.getInventory().first(Material.ARROW), new ItemStack(Material.AIR)); }
				}else if (player.getInventory().getItemInOffHand().getEnchantments().containsKey(Enchantment.ARROW_INFINITE)){
					if (!aarrow.allowInfinity()){
						if (reference.getAmount() > 1) reference.setAmount(reference.getAmount() - 1);
						else{ player.getInventory().setItem(player.getInventory().first(Material.ARROW), new ItemStack(Material.AIR)); }
					}
				}
			}
		}
		else if (arrow.getShooter() instanceof Skeleton){
			if (random.nextInt(100) + 1 <= 10){
				Object[] values = ArrowRegistry.getArrowRegistry().values().toArray();
				@SuppressWarnings("unchecked")
				Class<? extends AlchemicalArrow> randomValue = (Class<? extends AlchemicalArrow>) values[random.nextInt(values.length)];
				try {
					AlchemicalArrow aarrow = randomValue.getConstructor(Arrow.class).newInstance(arrow);
					if (aarrow.skeletonsCanShoot()){
						plugin.getArrowRegistry().registerAlchemicalArrow(aarrow);
						Skeleton skeleton = (Skeleton) arrow.getShooter();
						plugin.getArrowRegistry().getAlchemicalArrow(arrow).onShootFromSkeleton(skeleton);
					}else{ return; }
				}catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
					plugin.getLogger().config("Something went wrong while attempting to allow a skeleton to shoot an arrow");
					e.printStackTrace(); 
				}
			}
		}else if (arrow.getShooter() instanceof BlockProjectileSource){
//			TODO:
//			BlockProjectileSource bps = (BlockProjectileSource) arrow.getShooter();
//			plugin.getArrowRegistry().getAlchemicalArrow(arrow).onShootFromBlockSource(bps);
		}
	}
}