package me.choco.arrows.events;

import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.inventory.ItemStack;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.AlchemicalArrow;
import me.choco.arrows.utils.ArrowRegistry;

public class PickupArrow implements Listener{
	
	private AlchemicalArrows plugin;
	public PickupArrow(AlchemicalArrows plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPickupArrow(PlayerPickupArrowEvent event){
		for (AlchemicalArrow aarrow : plugin.getArrowRegistry().getRegisteredArrows().values()){
			if (aarrow.getArrow().equals(event.getArrow())){
				for (ItemStack item : ArrowRegistry.getArrowRegistry().keySet()){
					if (ArrowRegistry.getArrowRegistry().get(item).equals(aarrow.getClass())){
						if (event.isCancelled()) return;
						
						event.setCancelled(true);
						event.getArrow().remove();
						event.getPlayer().getInventory().addItem(item);
						event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
						break;
					}
				}
				break;
			}
		}
	}
}