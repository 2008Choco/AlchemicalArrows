package me.choco.arrows.events;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.inventory.ItemStack;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.AlchemicalArrow;
import me.choco.arrows.registry.ArrowRegistry;

public class PickupArrow implements Listener{
	
	private final ArrowRegistry arrowRegistry;
	public PickupArrow(AlchemicalArrows plugin){
		this.arrowRegistry = plugin.getArrowRegistry();
	}
	
	@EventHandler
	public void onPickupArrow(PlayerPickupArrowEvent event){
		if (event.isCancelled()) return;
		
		for (AlchemicalArrow aarrow : this.arrowRegistry.getRegisteredArrows().values()){
			if (aarrow.getArrow().equals(event.getArrow())){
				for (ItemStack item : ArrowRegistry.getArrowRegistry().keySet()){
					if (ArrowRegistry.getArrowRegistry().get(item).equals(aarrow.getClass())){
						Player player = event.getPlayer();
						
						event.setCancelled(true);
						event.getArrow().remove();
						player.getInventory().addItem(item);
						player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
						break;
					}
				}
				break;
			}
		}
	}
}