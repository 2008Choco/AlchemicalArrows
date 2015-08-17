package me.choco.arrows.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.Methods;

public class ArrowPickup extends Methods implements Listener{
	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent event){
		Item item = event.getItem();
		if (item.getItemStack().getType() == Material.ARROW){
			if (item.hasMetadata("Air")){
				event.setCancelled(true);
				pickupArrow(item, event.getPlayer(), createSpecializedArrow(1, ChatColor.ITALIC + "Air Arrow"));
			}//Close if it's an air arrow being picked up
				
			if (item.hasMetadata("Earth")){
				event.setCancelled(true);
				pickupArrow(item, event.getPlayer(), createSpecializedArrow(1, ChatColor.GRAY + "Earth Arrow"));
			}//Close if it's an air arrow being picked up
			
			if (item.hasMetadata("Magic")){
				event.setCancelled(true);
				pickupArrow(item, event.getPlayer(), createSpecializedArrow(1, ChatColor.LIGHT_PURPLE + "Magic Arrow"));
			}//Close if it's an air arrow being picked up
			
			if (item.hasMetadata("Ender")){
				event.setCancelled(true);
				pickupArrow(item, event.getPlayer(), createSpecializedArrow(1, ChatColor.DARK_PURPLE + "Ender Arrow"));
			}//Close if it's an air arrow being picked up
			
			if (item.hasMetadata("Life")){
				event.setCancelled(true);
				pickupArrow(item, event.getPlayer(), createSpecializedArrow(1, ChatColor.GREEN + "Life Arrow"));
			}//Close if it's an air arrow being picked up
			
			if (item.hasMetadata("Death")){
				event.setCancelled(true);
				pickupArrow(item, event.getPlayer(), createSpecializedArrow(1, ChatColor.BLACK + "Death Arrow"));
			}//Close if it's an air arrow being picked up
		
			if (item.hasMetadata("Light")){
				event.setCancelled(true);
				pickupArrow(item, event.getPlayer(), createSpecializedArrow(1, ChatColor.YELLOW + "Light Arrow"));
			}//Close if it's an air arrow being picked up
			
			if (item.hasMetadata("Darkness")){
				event.setCancelled(true);
				pickupArrow(item, event.getPlayer(), createSpecializedArrow(1, ChatColor.DARK_GRAY + "Darkness Arrow"));
			}//Close if it's an air arrow being picked up
			
			if (item.hasMetadata("Fire")){
				event.setCancelled(true);
				pickupArrow(item, event.getPlayer(), createSpecializedArrow(1, ChatColor.RED + "Fire Arrow"));
			}//Close if it's an air arrow being picked up
			
			if (item.hasMetadata("Frost")){
				event.setCancelled(true);
				pickupArrow(item, event.getPlayer(), createSpecializedArrow(1, ChatColor.AQUA + "Frost Arrow"));
			}//Close if it's an air arrow being picked up
			
			if (item.hasMetadata("Water")){
				event.setCancelled(true);
				pickupArrow(item, event.getPlayer(), createSpecializedArrow(1, ChatColor.BLUE + "Water Arrow"));
			}//Close if it's an air arrow being picked up
			
			if (item.hasMetadata("Necrotic")){
				event.setCancelled(true);
				pickupArrow(item, event.getPlayer(), createSpecializedArrow(1, ChatColor.DARK_GREEN + "Necrotic Arrow"));
			}//Close if it's an air arrow being picked up
			
			if (item.hasMetadata("Confusion")){
				event.setCancelled(true);
				pickupArrow(item, event.getPlayer(), createSpecializedArrow(1, ChatColor.LIGHT_PURPLE + "Confusion Arrow"));
			}//Close if it's an air arrow being picked up
			
			if (item.hasMetadata("Magnetic")){
				event.setCancelled(true);
				pickupArrow(item, event.getPlayer(), createSpecializedArrow(1, ChatColor.GRAY + "Magnetic Arrow"));
			}//Close if it's an air arrow being picked up
		}//Close if an arrow is being picked up
	}//Close onItemPickup event
	
	private static void pickupArrow(Item item, Player player, ItemStack arrow){
		item.remove();
		player.getInventory().addItem(arrow);
		player.getWorld().playSound(player.getLocation(), Sound.ITEM_PICKUP, 1, 2);
		AlchemicalArrows.specializedArrows.remove(item);
	}//Close pickupArrow method
}//Close class