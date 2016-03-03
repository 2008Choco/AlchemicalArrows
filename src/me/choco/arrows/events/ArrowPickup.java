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
			}

			if (item.hasMetadata("Earth")){
				event.setCancelled(true);
				pickupArrow(item, event.getPlayer(), createSpecializedArrow(1, ChatColor.GRAY + "Earth Arrow"));
			}
			
			if (item.hasMetadata("Magic")){
				event.setCancelled(true);
				pickupArrow(item, event.getPlayer(), createSpecializedArrow(1, ChatColor.LIGHT_PURPLE + "Magic Arrow"));
			}
			
			if (item.hasMetadata("Ender")){
				event.setCancelled(true);
				pickupArrow(item, event.getPlayer(), createSpecializedArrow(1, ChatColor.DARK_PURPLE + "Ender Arrow"));
			}
			
			if (item.hasMetadata("Life")){
				event.setCancelled(true);
				pickupArrow(item, event.getPlayer(), createSpecializedArrow(1, ChatColor.GREEN + "Life Arrow"));
			}
			
			if (item.hasMetadata("Death")){
				event.setCancelled(true);
				pickupArrow(item, event.getPlayer(), createSpecializedArrow(1, ChatColor.BLACK + "Death Arrow"));
			}
		
			if (item.hasMetadata("Light")){
				event.setCancelled(true);
				pickupArrow(item, event.getPlayer(), createSpecializedArrow(1, ChatColor.YELLOW + "Light Arrow"));
			}
			
			if (item.hasMetadata("Darkness")){
				event.setCancelled(true);
				pickupArrow(item, event.getPlayer(), createSpecializedArrow(1, ChatColor.DARK_GRAY + "Darkness Arrow"));
			}
			
			if (item.hasMetadata("Fire")){
				event.setCancelled(true);
				pickupArrow(item, event.getPlayer(), createSpecializedArrow(1, ChatColor.RED + "Fire Arrow"));
			}
			
			if (item.hasMetadata("Frost")){
				event.setCancelled(true);
				pickupArrow(item, event.getPlayer(), createSpecializedArrow(1, ChatColor.AQUA + "Frost Arrow"));
			}
			
			if (item.hasMetadata("Water")){
				event.setCancelled(true);
				pickupArrow(item, event.getPlayer(), createSpecializedArrow(1, ChatColor.BLUE + "Water Arrow"));
			}
			
			if (item.hasMetadata("Necrotic")){
				event.setCancelled(true);
				pickupArrow(item, event.getPlayer(), createSpecializedArrow(1, ChatColor.DARK_GREEN + "Necrotic Arrow"));
			}
			
			if (item.hasMetadata("Confusion")){
				event.setCancelled(true);
				pickupArrow(item, event.getPlayer(), createSpecializedArrow(1, ChatColor.LIGHT_PURPLE + "Confusion Arrow"));
			}
			
			if (item.hasMetadata("Magnetic")){
				event.setCancelled(true);
				pickupArrow(item, event.getPlayer(), createSpecializedArrow(1, ChatColor.GRAY + "Magnetic Arrow"));
			}
		}
	}
	
	private static void pickupArrow(Item item, Player player, ItemStack arrow){
		item.remove();
		player.getInventory().addItem(arrow);
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 2);
		AlchemicalArrows.specializedArrows.remove(item);
	}
}