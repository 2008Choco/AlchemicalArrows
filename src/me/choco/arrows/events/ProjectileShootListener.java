package me.choco.arrows.events;

import java.util.Collection;
import java.util.Random;

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
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.projectiles.BlockProjectileSource;

import com.google.common.collect.Iterables;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.AlchemicalArrow;
import me.choco.arrows.registry.ArrowRegistry;
import me.choco.arrows.utils.ConfigOption;

public class ProjectileShootListener implements Listener {

	private static final Random RANDOM = new Random();
	
	private final ArrowRegistry arrowRegistry;
	
	public ProjectileShootListener(AlchemicalArrows plugin){
		this.arrowRegistry = plugin.getArrowRegistry();
	}
	
	@EventHandler
	public void onShootArrow(ProjectileLaunchEvent event){
		if (!(event.getEntity() instanceof Arrow)) return;
		Arrow arrow = (Arrow) event.getEntity();
		
		if (arrow.getShooter() instanceof Player){
			Player player = (Player) arrow.getShooter();
			PlayerInventory inventory = player.getInventory();
			if (!inventory.contains(Material.ARROW)) return;
			
			//Register the arrow if it's in the arrow registry
			int arrowSlot = (isShotFromMainHand(player) ? inventory.first(Material.ARROW) : inventory.getHeldItemSlot());
			ItemStack reference = inventory.getItem(arrowSlot);
			
			if (reference == null || !reference.getType().equals(Material.ARROW)){
				arrowSlot = inventory.first(Material.ARROW);
				reference = inventory.getItem(arrowSlot);
			}
			
			ItemStack item = new ItemStack(reference); item.setAmount(1);
			if (!ArrowRegistry.getArrowRegistry().containsKey(item)) return;
			
			AlchemicalArrow aarrow = AlchemicalArrow.createNewArrow(ArrowRegistry.getArrowRegistry().get(item), arrow);
			if (aarrow == null) return;
			
			this.arrowRegistry.registerAlchemicalArrow(aarrow);
			aarrow.shootEventHandler(event);
			aarrow.onShootFromPlayer(player);
			
			if (aarrow.allowInfinity()) return;
			if (!player.getGameMode().equals(GameMode.CREATIVE)){
				if ((inventory.getItemInMainHand() != null && inventory.getItemInMainHand().getEnchantments().containsKey(Enchantment.ARROW_INFINITE))
					|| (inventory.getItemInOffHand() != null && inventory.getItemInOffHand().getEnchantments().containsKey(Enchantment.ARROW_INFINITE))){
					
					if (reference.getAmount() > 1){
						reference.setAmount(reference.getAmount() - 1);
					}else{ 
						inventory.setItem(arrowSlot, new ItemStack(Material.AIR)); 
					}
				}
			}
		}
		
		else if (arrow.getShooter() instanceof Skeleton){
			if (RANDOM.nextInt(100) < ConfigOption.SKELETON_SHOOT_PERCENTAGE){
				Collection<Class<? extends AlchemicalArrow>> arrows = ArrowRegistry.getArrowRegistry().values();
				Class<? extends AlchemicalArrow> type = Iterables.get(arrows, RANDOM.nextInt(arrows.size()));
				
				AlchemicalArrow aarrow = AlchemicalArrow.createNewArrow(type, arrow);
				if (arrow == null || !aarrow.skeletonsCanShoot()) return;
				
				this.arrowRegistry.registerAlchemicalArrow(aarrow);
				Skeleton skeleton = (Skeleton) arrow.getShooter();
				this.arrowRegistry.getAlchemicalArrow(arrow).onShootFromSkeleton(skeleton);
			}
		}
		
		else if (arrow.getShooter() instanceof BlockProjectileSource){
//			TODO:
//			BlockProjectileSource bps = (BlockProjectileSource) arrow.getShooter();
//			plugin.getArrowRegistry().getAlchemicalArrow(arrow).onShootFromBlockSource(bps);
		}
	}
	
	private boolean isShotFromMainHand(Player player){
		ItemStack mainHand = player.getInventory().getItemInMainHand();
		ItemStack offHand = player.getInventory().getItemInOffHand();
		
		return ((mainHand != null && mainHand.getType().equals(Material.BOW)) || 
				(mainHand == null && offHand != null && offHand.getType().equals(Material.BOW)));
	}
}