package me.choco.arrows.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.choco.arrows.AlchemicalArrows;

public class PickupArrow implements Listener{
	
	AlchemicalArrows plugin;
	public PickupArrow(AlchemicalArrows plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPickupArrow(){
//		for (AlchemicalArrow arrow : plugin.getArrowRegistry().getRegisteredArrows().values()){
//			if (arrow.getArrow().equals(event.getArrow())){
//				event.setCancelled(true);
//				for (ItemStack item : ArrowRegistry.getArrowRegistry().keySet()){
//					if (ArrowRegistry.getArrowRegistry().get(item) instanceof arrow){
//						event.getPlayer().getInventory().add(item);
//						break;
//					}
//				}
//				break;
//			}
//		}
	}
}