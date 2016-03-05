package me.choco.arrows.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.choco.arrows.AlchemicalArrows;

public class ItemRecipes implements Listener{
	
	public ItemStack airArrow, earthArrow, magicArrow, enderArrow, lifeArrow, deathArrow, lightArrow, darknessArrow, fireArrow, frostArrow, 
		waterArrow, necroticArrow, confusionArrow, magneticArrow;
	
	AlchemicalArrows plugin;
	public ItemRecipes(AlchemicalArrows plugin){
		this.plugin = plugin;
		
		airArrow = createItem(Material.ARROW, plugin.getConfig().getInt("Arrows.AirArrow.Crafts"), ChatColor.ITALIC + "Air Arrow");
		earthArrow = createItem(Material.ARROW, plugin.getConfig().getInt("Arrows.EarthArrow.Crafts"), ChatColor.GRAY + "Earth Arrow");
		magicArrow = createItem(Material.ARROW, plugin.getConfig().getInt("Arrows.MagicArrow.Crafts"), ChatColor.LIGHT_PURPLE + "Magic Arrow");
		enderArrow = createItem(Material.ARROW, plugin.getConfig().getInt("Arrows.EnderArrow.Crafts"), ChatColor.DARK_PURPLE + "Ender Arrow");
		lifeArrow = createItem(Material.ARROW, plugin.getConfig().getInt("Arrows.LifeArrow.Crafts"), ChatColor.GREEN + "Life Arrow");
		deathArrow = createItem(Material.ARROW, plugin.getConfig().getInt("Arrows.DeathArrow.Crafts"), ChatColor.BLACK + "Death Arrow");
		lightArrow = createItem(Material.ARROW, plugin.getConfig().getInt("Arrows.LightArrow.Crafts"), ChatColor.YELLOW + "Light Arrow");
		darknessArrow = createItem(Material.ARROW, plugin.getConfig().getInt("Arrows.DarknessArrow.Crafts"), ChatColor.DARK_GRAY + "Darkness Arrow");
		fireArrow = createItem(Material.ARROW, plugin.getConfig().getInt("Arrows.FireArrow.Crafts"), ChatColor.RED + "Fire Arrow");
		frostArrow = createItem(Material.ARROW, plugin.getConfig().getInt("Arrows.FrostArrow.Crafts"), ChatColor.AQUA + "Frost Arrow");
		waterArrow = createItem(Material.ARROW, plugin.getConfig().getInt("Arrows.WaterArrow.Crafts"), ChatColor.BLUE + "Water Arrow");
		necroticArrow = createItem(Material.ARROW, plugin.getConfig().getInt("Arrows.NecroticArrow.Crafts"), ChatColor.DARK_GREEN + "Necrotic Arrow");
		confusionArrow = createItem(Material.ARROW, plugin.getConfig().getInt("Arrows.ConfusonArrow.Crafts"), ChatColor.LIGHT_PURPLE + "Confusion Arrow");
		magneticArrow = createItem(Material.ARROW, plugin.getConfig().getInt("Arrows.MagneticArrow.Crafts"), ChatColor.GRAY + "Magnetic Arrow");
	}
	
	private ItemStack createItem(Material material, int count, String name){
		ItemStack item = new ItemStack(material, count);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}
}