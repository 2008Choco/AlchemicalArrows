package me.choco.arrows.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;

import me.choco.arrows.utils.general.ItemBuilder;

/**
 * Handles all arrow recipes for the core arrows and permission-based 
 * crafting events
 * 
 * @author Parker Hawke - 2008Choco
 */
public class ItemRecipes implements Listener {
	
	private static final ItemStack AIR = new ItemStack(Material.AIR);
	
	public static final ItemStack AIR_ARROW = new ItemBuilder(Material.ARROW).setName(ConfigOption.AIR_DISPLAY_NAME).build();
	public static final ItemStack EARTH_ARROW = new ItemBuilder(Material.ARROW).setName(ConfigOption.EARTH_DISPLAY_NAME).build();
	public static final ItemStack MAGIC_ARROW = new ItemBuilder(Material.ARROW).setName(ConfigOption.EARTH_DISPLAY_NAME).build();
	public static final ItemStack ENDER_ARROW = new ItemBuilder(Material.ARROW).setName(ConfigOption.EARTH_DISPLAY_NAME).build();
	public static final ItemStack LIFE_ARROW = new ItemBuilder(Material.ARROW).setName(ConfigOption.EARTH_DISPLAY_NAME).build();
	public static final ItemStack DEATH_ARROW = new ItemBuilder(Material.ARROW).setName(ConfigOption.EARTH_DISPLAY_NAME).build();
	public static final ItemStack LIGHT_ARROW = new ItemBuilder(Material.ARROW).setName(ConfigOption.EARTH_DISPLAY_NAME).build();
	public static final ItemStack DARKNESS_ARROW = new ItemBuilder(Material.ARROW).setName(ConfigOption.EARTH_DISPLAY_NAME).build();
	public static final ItemStack FIRE_ARROW = new ItemBuilder(Material.ARROW).setName(ConfigOption.EARTH_DISPLAY_NAME).build();
	public static final ItemStack FROST_ARROW = new ItemBuilder(Material.ARROW).setName(ConfigOption.EARTH_DISPLAY_NAME).build();
	public static final ItemStack WATER_ARROW = new ItemBuilder(Material.ARROW).setName(ConfigOption.EARTH_DISPLAY_NAME).build();
	public static final ItemStack NECROTIC_ARROW = new ItemBuilder(Material.ARROW).setName(ConfigOption.EARTH_DISPLAY_NAME).build();
	public static final ItemStack CONFUSION_ARROW = new ItemBuilder(Material.ARROW).setName(ConfigOption.EARTH_DISPLAY_NAME).build();
	public static final ItemStack MAGNETIC_ARROW = new ItemBuilder(Material.ARROW).setName(ConfigOption.EARTH_DISPLAY_NAME).build();
	public static final ItemStack GRAPPLE_ARROW = new ItemBuilder(Material.ARROW).setName(ConfigOption.EARTH_DISPLAY_NAME).build();
	
	@EventHandler
	public void onPrepareCraftingRecipe(PrepareItemCraftEvent event){
		ItemStack item = event.getInventory().getResult();
		if (item == null) return;
		
		Player player = (Player) event.getViewers().get(0);
		CraftingInventory inventory = event.getInventory();
		
		if (AIR_ARROW.isSimilar(item) && !player.hasPermission("arrows.craft.air")){
			inventory.setResult(AIR);
		}
		else if (EARTH_ARROW.isSimilar(item) && !player.hasPermission("arrows.craft.earth")){
			inventory.setResult(AIR);
		}
		else if (MAGIC_ARROW.isSimilar(item) && !player.hasPermission("arrows.craft.magic")){
			inventory.setResult(AIR);
		}
		else if (ENDER_ARROW.isSimilar(item) && !player.hasPermission("arrows.craft.ender")){
			inventory.setResult(AIR);
		}
		else if (LIFE_ARROW.isSimilar(item) && !player.hasPermission("arrows.craft.life")){
			inventory.setResult(AIR);
		}
		else if (DEATH_ARROW.isSimilar(item) && !player.hasPermission("arrows.craft.death")){
			inventory.setResult(AIR);
		}
		else if (LIGHT_ARROW.isSimilar(item) && !player.hasPermission("arrows.craft.light")){
			inventory.setResult(AIR);
		}
		else if (DARKNESS_ARROW.isSimilar(item) && !player.hasPermission("arrows.craft.darkness")){
			inventory.setResult(AIR);
		}
		else if (FIRE_ARROW.isSimilar(item) && !player.hasPermission("arrows.craft.fire")){
			inventory.setResult(AIR);
		}
		else if (FROST_ARROW.isSimilar(item) && !player.hasPermission("arrows.craft.frost")){
			inventory.setResult(AIR);
		}
		else if (WATER_ARROW.isSimilar(item) && !player.hasPermission("arrows.craft.water")){
			inventory.setResult(AIR);
		}
		else if (NECROTIC_ARROW.isSimilar(item) && !player.hasPermission("arrows.craft.necrotic")){
			inventory.setResult(AIR);
		}
		else if (CONFUSION_ARROW.isSimilar(item) && !player.hasPermission("arrows.craft.confusion")){
			inventory.setResult(AIR);
		}
		else if (MAGNETIC_ARROW.isSimilar(item) && !player.hasPermission("arrows.craft.magnetic")){
			inventory.setResult(AIR);
		}
		else if (GRAPPLE_ARROW.isSimilar(item) && !player.hasPermission("arrows.craft.necrotic")){
			inventory.setResult(AIR);
		}
	}
}