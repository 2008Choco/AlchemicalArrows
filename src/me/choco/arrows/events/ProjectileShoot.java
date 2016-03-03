package me.choco.arrows.events;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.utils.ArrowRegistry;

public class ProjectileShoot implements Listener{
	
	AlchemicalArrows plugin;
	ArrowRegistry registry;
	public ProjectileShoot(AlchemicalArrows plugin){
		this.plugin = plugin;
		this.registry = plugin.getArrowRegistry();
	}
	
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
					registry.registerAlchemicalArrow(ArrowRegistry.getArrowRegistry().get(item).getDeclaredConstructor(Arrow.class).newInstance(arrow));
				}catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e){
					System.out.println("Something went wrong while attempting to register arrow " + ArrowRegistry.getArrowRegistry().get(item));
					e.printStackTrace(); 
				}
			}else{ return; }
			plugin.getArrowRegistry().getAlchemicalArrow(arrow).onShootFromPlayer(player);
		}
//		else if (arrow.getShooter() instanceof Skeleton){
//			//TODO: Randomize whether skeletons can shoot arrows or not
//			Skeleton skeleton = (Skeleton) arrow.getShooter();
//			plugin.getArrowRegistry().getAlchemicalArrow(arrow).onShootFromSkeleton(skeleton);
//		}else if (arrow.getShooter() instanceof BlockProjectileSource){
//			BlockProjectileSource bps = (BlockProjectileSource) arrow.getShooter();
//			plugin.getArrowRegistry().getAlchemicalArrow(arrow).onShootFromBlockSource(bps);
//		}
	}
}