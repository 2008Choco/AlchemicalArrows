package me.choco.arrows.events;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.methods.Arrows;

public class ArrowPickup implements Listener{
	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent event){
		Item item = event.getItem();
		if (item.getItemStack().getType() == Material.ARROW){
			if (item.hasMetadata("Air")){
				event.setCancelled(true);
				item.remove();
				event.getPlayer().getInventory().addItem(Arrows.airArrow(1));
				event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.ITEM_PICKUP, 1, 2);
				AlchemicalArrows.specializedArrows.remove(item);
			}//Close if it's an air arrow being picked up
				
			if (item.hasMetadata("Earth")){
				event.setCancelled(true);
				item.remove();
				event.getPlayer().getInventory().addItem(Arrows.earthArrow(1));
				event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.ITEM_PICKUP, 1, 2);
				AlchemicalArrows.specializedArrows.remove(item);
			}//Close if it's an air arrow being picked up
			
			if (item.hasMetadata("Magic")){
				event.setCancelled(true);
				item.remove();
				event.getPlayer().getInventory().addItem(Arrows.magicArrow(1));
				event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.ITEM_PICKUP, 1, 2);
				AlchemicalArrows.specializedArrows.remove(item);
			}//Close if it's an air arrow being picked up
			
			if (item.hasMetadata("Spectral")){
				event.setCancelled(true);
				item.remove();
				event.getPlayer().getInventory().addItem(Arrows.spectralArrow(1));
				event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.ITEM_PICKUP, 1, 2);
				AlchemicalArrows.specializedArrows.remove(item);
			}//Close if it's an air arrow being picked up
			
			if (item.hasMetadata("Life")){
				event.setCancelled(true);
				item.remove();
				event.getPlayer().getInventory().addItem(Arrows.lifeArrow(1));
				event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.ITEM_PICKUP, 1, 2);
				AlchemicalArrows.specializedArrows.remove(item);
			}//Close if it's an air arrow being picked up
			
			if (item.hasMetadata("Death")){
				event.setCancelled(true);
				item.remove();
				event.getPlayer().getInventory().addItem(Arrows.deathArrow(1));
				event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.ITEM_PICKUP, 1, 2);
				AlchemicalArrows.specializedArrows.remove(item);
			}//Close if it's an air arrow being picked up
		
			if (item.hasMetadata("Light")){
				event.setCancelled(true);
				item.remove();
				event.getPlayer().getInventory().addItem(Arrows.lightArrow(1));
				event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.ITEM_PICKUP, 1, 2);
				AlchemicalArrows.specializedArrows.remove(item);
			}//Close if it's an air arrow being picked up
			
			if (item.hasMetadata("Darkness")){
				event.setCancelled(true);
				item.remove();
				event.getPlayer().getInventory().addItem(Arrows.darknessArrow(1));
				event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.ITEM_PICKUP, 1, 2);
				AlchemicalArrows.specializedArrows.remove(item);
			}//Close if it's an air arrow being picked up
			
			if (item.hasMetadata("Fire")){
				event.setCancelled(true);
				item.remove();
				event.getPlayer().getInventory().addItem(Arrows.fireArrow(1));
				event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.ITEM_PICKUP, 1, 2);
				AlchemicalArrows.specializedArrows.remove(item);
			}//Close if it's an air arrow being picked up
			
			if (item.hasMetadata("Frost")){
				event.setCancelled(true);
				item.remove();
				event.getPlayer().getInventory().addItem(Arrows.frostArrow(1));
				event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.ITEM_PICKUP, 1, 2);
				AlchemicalArrows.specializedArrows.remove(item);
			}//Close if it's an air arrow being picked up
			
			if (item.hasMetadata("Water")){
				event.setCancelled(true);
				item.remove();
				event.getPlayer().getInventory().addItem(Arrows.waterArrow(1));
				event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.ITEM_PICKUP, 1, 2);
				AlchemicalArrows.specializedArrows.remove(item);
			}//Close if it's an air arrow being picked up
			
			if (item.hasMetadata("Necrotic")){
				event.setCancelled(true);
				item.remove();
				event.getPlayer().getInventory().addItem(Arrows.necroticArrow(1));
				event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.ITEM_PICKUP, 1, 2);
				AlchemicalArrows.specializedArrows.remove(item);
			}//Close if it's an air arrow being picked up
			
			if (item.hasMetadata("Confusion")){
				event.setCancelled(true);
				item.remove();
				event.getPlayer().getInventory().addItem(Arrows.confusionArrow(1));
				event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.ITEM_PICKUP, 1, 2);
				AlchemicalArrows.specializedArrows.remove(item);
			}//Close if it's an air arrow being picked up
			
			if (item.hasMetadata("Magnetic")){
				event.setCancelled(true);
				item.remove();
				event.getPlayer().getInventory().addItem(Arrows.magicArrow(1));
				event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.ITEM_PICKUP, 1, 2);
				AlchemicalArrows.specializedArrows.remove(item);
			}//Close if it's an air arrow being picked up
		}//Close if an arrow is being picked up
	}//Close onItemPickup event
}//Close class