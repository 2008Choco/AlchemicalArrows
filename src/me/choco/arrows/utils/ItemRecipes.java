package me.choco.arrows.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.choco.arrows.AlchemicalArrows;

public class ItemRecipes implements Listener{
	
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
		else if (!item.getItemMeta().hasDisplayName()) return;
		
		if (item.getItemMeta().getDisplayName().equals(airArrow.getItemMeta().getDisplayName()) && !event.getViewers().get(0).hasPermission("arrows.craft.air")){
			event.getInventory().setResult(new ItemStack(Material.AIR));
		}else if (item.getItemMeta().getDisplayName().equals(earthArrow.getItemMeta().getDisplayName()) && !event.getViewers().get(0).hasPermission("arrows.craft.earth")){
			event.getInventory().setResult(new ItemStack(Material.AIR));
		}else if (item.getItemMeta().getDisplayName().equals(magicArrow.getItemMeta().getDisplayName()) && !event.getViewers().get(0).hasPermission("arrows.craft.magic")){
			event.getInventory().setResult(new ItemStack(Material.AIR));
		}else if (item.getItemMeta().getDisplayName().equals(enderArrow.getItemMeta().getDisplayName()) && !event.getViewers().get(0).hasPermission("arrows.craft.ender")){
			event.getInventory().setResult(new ItemStack(Material.AIR));
		}else if (item.getItemMeta().getDisplayName().equals(lifeArrow.getItemMeta().getDisplayName()) && !event.getViewers().get(0).hasPermission("arrows.craft.life")){
			event.getInventory().setResult(new ItemStack(Material.AIR));
		}else if (item.getItemMeta().getDisplayName().equals(deathArrow.getItemMeta().getDisplayName()) && !event.getViewers().get(0).hasPermission("arrows.craft.death")){
			event.getInventory().setResult(new ItemStack(Material.AIR));
		}else if (item.getItemMeta().getDisplayName().equals(lightArrow.getItemMeta().getDisplayName()) && !event.getViewers().get(0).hasPermission("arrows.craft.light")){
			event.getInventory().setResult(new ItemStack(Material.AIR));
		}else if (item.getItemMeta().getDisplayName().equals(darknessArrow.getItemMeta().getDisplayName()) && !event.getViewers().get(0).hasPermission("arrows.craft.darkness")){
			event.getInventory().setResult(new ItemStack(Material.AIR));
		}else if (item.getItemMeta().getDisplayName().equals(fireArrow.getItemMeta().getDisplayName()) && !event.getViewers().get(0).hasPermission("arrows.craft.fire")){
			event.getInventory().setResult(new ItemStack(Material.AIR));
		}else if (item.getItemMeta().getDisplayName().equals(frostArrow.getItemMeta().getDisplayName()) && !event.getViewers().get(0).hasPermission("arrows.craft.frost")){
			event.getInventory().setResult(new ItemStack(Material.AIR));
		}else if (item.getItemMeta().getDisplayName().equals(waterArrow.getItemMeta().getDisplayName()) && !event.getViewers().get(0).hasPermission("arrows.craft.water")){
			event.getInventory().setResult(new ItemStack(Material.AIR));
		}else if (item.getItemMeta().getDisplayName().equals(necroticArrow.getItemMeta().getDisplayName()) && !event.getViewers().get(0).hasPermission("arrows.craft.necrotic")){
			event.getInventory().setResult(new ItemStack(Material.AIR));
		}else if (item.getItemMeta().getDisplayName().equals(confusionArrow.getItemMeta().getDisplayName()) && !event.getViewers().get(0).hasPermission("arrows.craft.confusion")){
			event.getInventory().setResult(new ItemStack(Material.AIR));
		}else if (item.getItemMeta().getDisplayName().equals(magneticArrow.getItemMeta().getDisplayName()) && !event.getViewers().get(0).hasPermission("arrows.craft.magnetic")){
			event.getInventory().setResult(new ItemStack(Material.AIR));
		}else if (item.getItemMeta().getDisplayName().equals(grappleArrow.getItemMeta().getDisplayName()) && !event.getViewers().get(0).hasPermission("arrows.craft.necrotic")){
			event.getInventory().setResult(new ItemStack(Material.AIR));
		}
	}
}