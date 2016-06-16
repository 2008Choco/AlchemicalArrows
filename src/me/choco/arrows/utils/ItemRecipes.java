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

public class ItemRecipes implements Listener{
	
	private static final ItemStack air = new ItemStack(Material.AIR);
	public ItemStack airArrow, earthArrow, magicArrow, enderArrow, lifeArrow, deathArrow, lightArrow, darknessArrow, fireArrow, frostArrow, 
		waterArrow, necroticArrow, confusionArrow, magneticArrow, grappleArrow;
	
	public ItemRecipes(AlchemicalArrows plugin){
		airArrow = createItem(Material.ARROW, plugin.getConfig().getInt("Arrows.AirArrow.Crafts"), ChatColor.ITALIC + plugin.getConfig().getString("Arrows.AirArrow.DisplayName"));
		earthArrow = createItem(Material.ARROW, plugin.getConfig().getInt("Arrows.EarthArrow.Crafts"), ChatColor.GRAY + plugin.getConfig().getString("Arrows.EarthArrow.DisplayName"));
		magicArrow = createItem(Material.ARROW, plugin.getConfig().getInt("Arrows.MagicArrow.Crafts"), ChatColor.LIGHT_PURPLE + plugin.getConfig().getString("Arrows.MagicArrow.DisplayName"));
		enderArrow = createItem(Material.ARROW, plugin.getConfig().getInt("Arrows.EnderArrow.Crafts"), ChatColor.DARK_PURPLE + plugin.getConfig().getString("Arrows.EnderArrow.DisplayName"));
		lifeArrow = createItem(Material.ARROW, plugin.getConfig().getInt("Arrows.LifeArrow.Crafts"), ChatColor.GREEN + plugin.getConfig().getString("Arrows.LifeArrow.DisplayName"));
		deathArrow = createItem(Material.ARROW, plugin.getConfig().getInt("Arrows.DeathArrow.Crafts"), ChatColor.BLACK + plugin.getConfig().getString("Arrows.DeathArrow.DisplayName"));
		lightArrow = createItem(Material.ARROW, plugin.getConfig().getInt("Arrows.LightArrow.Crafts"), ChatColor.YELLOW + plugin.getConfig().getString("Arrows.LightArrow.DisplayName"));
		darknessArrow = createItem(Material.ARROW, plugin.getConfig().getInt("Arrows.DarknessArrow.Crafts"), ChatColor.DARK_GRAY + plugin.getConfig().getString("Arrows.DarknessArrow.DisplayName"));
		fireArrow = createItem(Material.ARROW, plugin.getConfig().getInt("Arrows.FireArrow.Crafts"), ChatColor.RED + plugin.getConfig().getString("Arrows.FireArrow.DisplayName"));
		frostArrow = createItem(Material.ARROW, plugin.getConfig().getInt("Arrows.FrostArrow.Crafts"), ChatColor.AQUA + plugin.getConfig().getString("Arrows.FrostArrow.DisplayName"));
		waterArrow = createItem(Material.ARROW, plugin.getConfig().getInt("Arrows.WaterArrow.Crafts"), ChatColor.BLUE + plugin.getConfig().getString("Arrows.WaterArrow.DisplayName"));
		necroticArrow = createItem(Material.ARROW, plugin.getConfig().getInt("Arrows.NecroticArrow.Crafts"), ChatColor.DARK_GREEN + plugin.getConfig().getString("Arrows.NecroticArrow.DisplayName"));
		confusionArrow = createItem(Material.ARROW, plugin.getConfig().getInt("Arrows.ConfusionArrow.Crafts"), ChatColor.LIGHT_PURPLE + plugin.getConfig().getString("Arrows.ConfusionArrow.DisplayName"));
		magneticArrow = createItem(Material.ARROW, plugin.getConfig().getInt("Arrows.MagneticArrow.Crafts"), ChatColor.GRAY + plugin.getConfig().getString("Arrows.MagneticArrow.DisplayName"));
		grappleArrow = createItem(Material.ARROW, plugin.getConfig().getInt("Arrows.GrappleArrow.Crafts"), ChatColor.YELLOW + plugin.getConfig().getString("Arrows.GrappleArrow.DisplayName"));
	}
	
	private ItemStack createItem(Material material, int count, String name){
		ItemStack item = new ItemStack(material, count);
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
			inventory.setResult(air);
		}else if (name.equals(earthArrow.getItemMeta().getDisplayName()) && !player.hasPermission("arrows.craft.earth")){
			inventory.setResult(air);
		}else if (name.equals(magicArrow.getItemMeta().getDisplayName()) && !player.hasPermission("arrows.craft.magic")){
			inventory.setResult(air);
		}else if (name.equals(enderArrow.getItemMeta().getDisplayName()) && !player.hasPermission("arrows.craft.ender")){
			inventory.setResult(air);
		}else if (name.equals(lifeArrow.getItemMeta().getDisplayName()) && !player.hasPermission("arrows.craft.life")){
			inventory.setResult(air);
		}else if (name.equals(deathArrow.getItemMeta().getDisplayName()) && !player.hasPermission("arrows.craft.death")){
			inventory.setResult(air);
		}else if (name.equals(lightArrow.getItemMeta().getDisplayName()) && !player.hasPermission("arrows.craft.light")){
			inventory.setResult(air);
		}else if (name.equals(darknessArrow.getItemMeta().getDisplayName()) && !player.hasPermission("arrows.craft.darkness")){
			inventory.setResult(air);
		}else if (name.equals(fireArrow.getItemMeta().getDisplayName()) && !player.hasPermission("arrows.craft.fire")){
			inventory.setResult(air);
		}else if (name.equals(frostArrow.getItemMeta().getDisplayName()) && !player.hasPermission("arrows.craft.frost")){
			inventory.setResult(air);
		}else if (name.equals(waterArrow.getItemMeta().getDisplayName()) && !player.hasPermission("arrows.craft.water")){
			inventory.setResult(air);
		}else if (name.equals(necroticArrow.getItemMeta().getDisplayName()) && !player.hasPermission("arrows.craft.necrotic")){
			inventory.setResult(air);
		}else if (name.equals(confusionArrow.getItemMeta().getDisplayName()) && !player.hasPermission("arrows.craft.confusion")){
			inventory.setResult(air);
		}else if (name.equals(magneticArrow.getItemMeta().getDisplayName()) && !player.hasPermission("arrows.craft.magnetic")){
			inventory.setResult(air);
		}else if (name.equals(grappleArrow.getItemMeta().getDisplayName()) && !player.hasPermission("arrows.craft.necrotic")){
			inventory.setResult(air);
		}
	}
}