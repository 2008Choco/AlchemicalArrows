package me.choco.arrows.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemRecipes implements Listener{
	
	public static ItemStack 
			airArrow = createItem(Material.ARROW, ChatColor.ITALIC + "Air Arrow"),
			earthArrow = createItem(Material.ARROW, ChatColor.GRAY + "Earth Arrow"),
			magicArrow = createItem(Material.ARROW, ChatColor.LIGHT_PURPLE + "Magic Arrow"),
			enderArrow = createItem(Material.ARROW, ChatColor.DARK_PURPLE + "Ender Arrow"),
			lifeArrow = createItem(Material.ARROW, ChatColor.GREEN + "Life Arrow"),
			deathArrow = createItem(Material.ARROW, ChatColor.BLACK + "Death Arrow"),
			lightArrow = createItem(Material.ARROW, ChatColor.YELLOW + "Light Arrow"),
			darknessArrow = createItem(Material.ARROW, ChatColor.DARK_GRAY + "Darkness Arrow"),
			fireArrow = createItem(Material.ARROW, ChatColor.RED + "Fire Arrow"),
			frostArrow = createItem(Material.ARROW, ChatColor.AQUA + "Frost Arrow"),
			waterArrow = createItem(Material.ARROW, ChatColor.BLUE + "Water Arrow"),
			necroticArrow = createItem(Material.ARROW, ChatColor.DARK_GREEN + "Necrotic Arrow"),
			confusionArrow = createItem(Material.ARROW, ChatColor.LIGHT_PURPLE + "Confusion Arrow"),
			magneticArrow = createItem(Material.ARROW, ChatColor.GRAY + "Magnetic Arrow");
	
	public static ItemStack createItem(Material material, String name){
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}
}