package me.choco.arrows.events;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.methods.Arrows;

public class ArrowPickup implements Listener{
	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent event){
		Item item = event.getItem();
		if (item.getItemStack().getType() == Material.ARROW){
			if (item.hasMetadata("Air")){
				event.setCancelled(true);
				pickupArrow(item, event.getPlayer(), Arrows.airArrow(1));
			}//Close if it's an air arrow being picked up
				
			if (item.hasMetadata("Earth")){
				event.setCancelled(true);
				pickupArrow(item, event.getPlayer(), Arrows.earthArrow(1));
			}//Close if it's an air arrow being picked up
			
			if (item.hasMetadata("Magic")){
				event.setCancelled(true);
				pickupArrow(item, event.getPlayer(), Arrows.magicArrow(1));
			}//Close if it's an air arrow being picked up
			
			if (item.hasMetadata("Ender")){
				event.setCancelled(true);
				pickupArrow(item, event.getPlayer(), Arrows.enderArrow(1));
			}//Close if it's an air arrow being picked up
			
			if (item.hasMetadata("Life")){
				event.setCancelled(true);
				pickupArrow(item, event.getPlayer(), Arrows.lifeArrow(1));
			}//Close if it's an air arrow being picked up
			
			if (item.hasMetadata("Death")){
				event.setCancelled(true);
				pickupArrow(item, event.getPlayer(), Arrows.deathArrow(1));
			}//Close if it's an air arrow being picked up
		
			if (item.hasMetadata("Light")){
				event.setCancelled(true);
				pickupArrow(item, event.getPlayer(), Arrows.lightArrow(1));
			}//Close if it's an air arrow being picked up
			
			if (item.hasMetadata("Darkness")){
				event.setCancelled(true);
				pickupArrow(item, event.getPlayer(), Arrows.darknessArrow(1));
			}//Close if it's an air arrow being picked up
			
			if (item.hasMetadata("Fire")){
				event.setCancelled(true);
				pickupArrow(item, event.getPlayer(), Arrows.fireArrow(1));
			}//Close if it's an air arrow being picked up
			
			if (item.hasMetadata("Frost")){
				event.setCancelled(true);
				pickupArrow(item, event.getPlayer(), Arrows.frostArrow(1));
			}//Close if it's an air arrow being picked up
			
			if (item.hasMetadata("Water")){
				event.setCancelled(true);
				pickupArrow(item, event.getPlayer(), Arrows.waterArrow(1));
			}//Close if it's an air arrow being picked up
			
			if (item.hasMetadata("Necrotic")){
				event.setCancelled(true);
				pickupArrow(item, event.getPlayer(), Arrows.necroticArrow(1));
			}//Close if it's an air arrow being picked up
			
			if (item.hasMetadata("Confusion")){
				event.setCancelled(true);
				pickupArrow(item, event.getPlayer(), Arrows.confusionArrow(1));
			}//Close if it's an air arrow being picked up
			
			if (item.hasMetadata("Magnetic")){
				event.setCancelled(true);
				pickupArrow(item, event.getPlayer(), Arrows.magneticArrow(1));
			}//Close if it's an air arrow being picked up
		}//Close if an arrow is being picked up
	}//Close onItemPickup event
	
	private static void pickupArrow(Item item, Player player, ItemStack arrow){
		item.remove();
		player.getInventory().addItem(Arrows.airArrow(1));
		player.getWorld().playSound(player.getLocation(), Sound.ITEM_PICKUP, 1, 2);
		AlchemicalArrows.specializedArrows.remove(item);
	}//Close pickupArrow method
}//Close class