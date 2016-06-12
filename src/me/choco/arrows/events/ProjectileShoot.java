package me.choco.arrows.events;

import java.lang.reflect.InvocationTargetException;
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

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.AlchemicalArrow;
import me.choco.arrows.utils.ArrowRegistry;

public class ProjectileShoot implements Listener{

	private static final Random random = new Random();
	int skeletonPercent = 10;
	
	private AlchemicalArrows plugin;
	public ProjectileShoot(AlchemicalArrows plugin){
		this.plugin = plugin;
		this.skeletonPercent = plugin.getConfig().getInt("SkeletonShootPercentage");
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
			if (ArrowRegistry.getArrowRegistry().containsKey(item)){
				try{
					plugin.getArrowRegistry().registerAlchemicalArrow(ArrowRegistry.getArrowRegistry().get(item).getDeclaredConstructor(Arrow.class).newInstance(arrow));
				}catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e){
					plugin.getLogger().info("Something went wrong while attempting to register arrow " + ArrowRegistry.getArrowRegistry().get(item));
					e.printStackTrace(); 
				}
			}else{ return; }
			
			AlchemicalArrow aarrow = plugin.getArrowRegistry().getAlchemicalArrow(arrow);
			aarrow.shootEventHandler(event);
			aarrow.onShootFromPlayer(player);
			
			if (aarrow.allowInfinity()) return;
			if (!player.getGameMode().equals(GameMode.CREATIVE)){
				if (inventory.getItemInMainHand() != null &&
						inventory.getItemInMainHand().getEnchantments().containsKey(Enchantment.ARROW_INFINITE)){
					
					if (reference.getAmount() > 1){
						reference.setAmount(reference.getAmount() - 1);
					}else{ 
						inventory.setItem(arrowSlot, new ItemStack(Material.AIR)); 
					}
				}
				
				else if (inventory.getItemInOffHand() != null && 
						inventory.getItemInOffHand().getEnchantments().containsKey(Enchantment.ARROW_INFINITE)){
					
					if (reference.getAmount() > 1){
						reference.setAmount(reference.getAmount() - 1);
					}else{ 
						inventory.setItem(arrowSlot, new ItemStack(Material.AIR)); 
					}
				}
			}
		}
		
		else if (arrow.getShooter() instanceof Skeleton){
			if ((random.nextInt(100) + 1) <= skeletonPercent){
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