package me.choco.arrows.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.choco.arrows.AlchemicalArrows;

/**
 * Handles all arrow recipes for the core arrows and permission-based 
 * crafting events
 * 
 * @author Parker Hawke - 2008Choco
 */
public class ItemRecipes implements Listener {
	
	private static final ItemStack AIR = new ItemStack(Material.AIR);
	public final ItemStack airArrow, earthArrow, magicArrow, enderArrow, lifeArrow, deathArrow, lightArrow, darknessArrow, fireArrow, frostArrow, 
		waterArrow, necroticArrow, confusionArrow, magneticArrow, grappleArrow;
	
	public ItemRecipes(AlchemicalArrows plugin){
		airArrow = createItem(ConfigOption.AIR_CRAFTS, ChatColor.ITALIC + ConfigOption.AIR_DISPLAY_NAME);
		earthArrow = createItem(ConfigOption.EARTH_CRAFTS, ChatColor.GRAY + ConfigOption.EARTH_DISPLAY_NAME);
		magicArrow = createItem(ConfigOption.MAGIC_CRAFTS, ChatColor.LIGHT_PURPLE + ConfigOption.MAGIC_DISPLAY_NAME);
		enderArrow = createItem(ConfigOption.MAGIC_CRAFTS, ChatColor.DARK_PURPLE + ConfigOption.ENDER_DISPLAY_NAME);
		lifeArrow = createItem(ConfigOption.LIFE_CRAFTS, ChatColor.GREEN + ConfigOption.LIFE_DISPLAY_NAME);
		deathArrow = createItem(ConfigOption.DEATH_CRAFTS, ChatColor.BLACK + ConfigOption.DEATH_DISPLAY_NAME);
		lightArrow = createItem(ConfigOption.LIGHT_CRAFTS, ChatColor.YELLOW + ConfigOption.LIGHT_DISPLAY_NAME);
		darknessArrow = createItem(ConfigOption.DARKNESS_CRAFTS, ChatColor.DARK_GRAY + ConfigOption.DARKNESS_DISPLAY_NAME);
		fireArrow = createItem(ConfigOption.FIRE_CRAFTS, ChatColor.RED + ConfigOption.FIRE_DISPLAY_NAME);
		frostArrow = createItem(ConfigOption.FROST_CRAFTS, ChatColor.AQUA + ConfigOption.FROST_DISPLAY_NAME);
		waterArrow = createItem(ConfigOption.WATER_CRAFTS, ChatColor.BLUE + ConfigOption.WATER_DISPLAY_NAME);
		necroticArrow = createItem(ConfigOption.NECROTIC_CRAFTS, ChatColor.DARK_GREEN + ConfigOption.NECROTIC_DISPLAY_NAME);
		confusionArrow = createItem(ConfigOption.CONFUSION_CRAFTS, ChatColor.LIGHT_PURPLE + ConfigOption.CONFUSION_DISPLAY_NAME);
		magneticArrow = createItem(ConfigOption.MAGIC_CRAFTS, ChatColor.GRAY + ConfigOption.MAGNETIC_DISPLAY_NAME);
		grappleArrow = createItem(ConfigOption.GRAPPLE_CRAFTS, ChatColor.YELLOW + ConfigOption.GRAPPLE_DISPLAY_NAME);
	}
	
	private ItemStack createItem(int count, String name){
		ItemStack item = new ItemStack(Material.ARROW, count);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}
	
	@EventHandler
	public void onPrepareCraftingRecipe(PrepareItemCraftEvent event){
		ItemStack item = event.getInventory().getResult();
		if (item == null) return;
		if (!item.hasItemMeta()) return;
		if (!item.getItemMeta().hasDisplayName()) return;
		
		String name = item.getItemMeta().getDisplayName();
		Player player = (Player) event.getViewers().get(0);
		CraftingInventory inventory = event.getInventory();
		
		if (name.equals(airArrow.getItemMeta().getDisplayName()) && !player.hasPermission("arrows.craft.air")){
			inventory.setResult(AIR);
		}else if (name.equals(earthArrow.getItemMeta().getDisplayName()) && !player.hasPermission("arrows.craft.earth")){
			inventory.setResult(AIR);
		}else if (name.equals(magicArrow.getItemMeta().getDisplayName()) && !player.hasPermission("arrows.craft.magic")){
			inventory.setResult(AIR);
		}else if (name.equals(enderArrow.getItemMeta().getDisplayName()) && !player.hasPermission("arrows.craft.ender")){
			inventory.setResult(AIR);
		}else if (name.equals(lifeArrow.getItemMeta().getDisplayName()) && !player.hasPermission("arrows.craft.life")){
			inventory.setResult(AIR);
		}else if (name.equals(deathArrow.getItemMeta().getDisplayName()) && !player.hasPermission("arrows.craft.death")){
			inventory.setResult(AIR);
		}else if (name.equals(lightArrow.getItemMeta().getDisplayName()) && !player.hasPermission("arrows.craft.light")){
			inventory.setResult(AIR);
		}else if (name.equals(darknessArrow.getItemMeta().getDisplayName()) && !player.hasPermission("arrows.craft.darkness")){
			inventory.setResult(AIR);
		}else if (name.equals(fireArrow.getItemMeta().getDisplayName()) && !player.hasPermission("arrows.craft.fire")){
			inventory.setResult(AIR);
		}else if (name.equals(frostArrow.getItemMeta().getDisplayName()) && !player.hasPermission("arrows.craft.frost")){
			inventory.setResult(AIR);
		}else if (name.equals(waterArrow.getItemMeta().getDisplayName()) && !player.hasPermission("arrows.craft.water")){
			inventory.setResult(AIR);
		}else if (name.equals(necroticArrow.getItemMeta().getDisplayName()) && !player.hasPermission("arrows.craft.necrotic")){
			inventory.setResult(AIR);
		}else if (name.equals(confusionArrow.getItemMeta().getDisplayName()) && !player.hasPermission("arrows.craft.confusion")){
			inventory.setResult(AIR);
		}else if (name.equals(magneticArrow.getItemMeta().getDisplayName()) && !player.hasPermission("arrows.craft.magnetic")){
			inventory.setResult(AIR);
		}else if (name.equals(grappleArrow.getItemMeta().getDisplayName()) && !player.hasPermission("arrows.craft.necrotic")){
			inventory.setResult(AIR);
		}
	}
}