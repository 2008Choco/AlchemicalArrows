package me.choco.arrows.startup;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.Plugin;

import me.choco.arrows.api.Methods;

public class Recipes extends Methods implements Listener{
	
	static Plugin AA = Bukkit.getServer().getPluginManager().getPlugin("AlchemicalArrows");
	private static ArrayList<String> disabledArrows = new ArrayList<String>();
	
	public static void enable(){
		
		ItemStack airArrow = createSpecializedArrow(AA.getConfig().getInt("ElementalArrows.AirArrow.Crafts"), ChatColor.ITALIC + "Air Arrow");
		ItemStack earthArrow = createSpecializedArrow(AA.getConfig().getInt("ElementalArrows.EarthArrow.Crafts"), ChatColor.GRAY + "Earth Arrow");
		ItemStack magicArrow = createSpecializedArrow(AA.getConfig().getInt("ElementalArrows.MagicArrow.Crafts"), ChatColor.LIGHT_PURPLE + "Magic Arrow");
		ItemStack enderArrow = createSpecializedArrow(AA.getConfig().getInt("ElementalArrows.EnderArrow.Crafts"), ChatColor.DARK_PURPLE + "Ender Arrow");
		ItemStack lifeArrow = createSpecializedArrow(AA.getConfig().getInt("ElementalArrows.LifeArrow.Crafts"), ChatColor.GREEN + "Life Arrow");
		ItemStack deathArrow = createSpecializedArrow(AA.getConfig().getInt("ElementalArrows.DeathArrow.Crafts"), ChatColor.BLACK + "Death Arrow");
		ItemStack lightArrow = createSpecializedArrow(AA.getConfig().getInt("ElementalArrows.LightArrow.Crafts"), ChatColor.YELLOW + "Light Arrow");
		ItemStack darknessArrow = createSpecializedArrow(AA.getConfig().getInt("ElementalArrows.DarknessArrow.Crafts"), ChatColor.DARK_GRAY + "Darkness Arrow");
		ItemStack fireArrow = createSpecializedArrow(AA.getConfig().getInt("ElementalArrows.FireArrow.Crafts"), ChatColor.RED + "Fire Arrow");
		ItemStack frostArrow = createSpecializedArrow(AA.getConfig().getInt("ElementalArrows.FrostArrow.Crafts"), ChatColor.AQUA + "Frost Arrow");
		ItemStack waterArrow = createSpecializedArrow(AA.getConfig().getInt("ElementalArrows.WaterArrow.Crafts"), ChatColor.BLUE + "Water Arrow");
		ItemStack necroticArrow = createSpecializedArrow(AA.getConfig().getInt("ElementalArrows.NecroticArrow.Crafts"), ChatColor.DARK_GREEN + "Necrotic Arrow");
		ItemStack confusionArrow = createSpecializedArrow(AA.getConfig().getInt("ElementalArrows.ConfusionArrow.Crafts"), ChatColor.LIGHT_PURPLE + "Confusion Arrow");
		ItemStack magneticArrow = createSpecializedArrow(AA.getConfig().getInt("ElementalArrows.MagneticArrow.Crafts"), ChatColor.GRAY + "Magnetic Arrow");
		
		createRecipe(airArrow, Material.FEATHER, AA.getConfig().getBoolean("ElementalArrows.AirArrow.Craftable"));
		createRecipe(earthArrow, Material.DIRT, AA.getConfig().getBoolean("ElementalArrows.EarthArrow.Craftable"));
		createRecipe(magicArrow, Material.BLAZE_POWDER, AA.getConfig().getBoolean("ElementalArrows.MagicArrow.Craftable"));
		createRecipe(enderArrow, Material.EYE_OF_ENDER, AA.getConfig().getBoolean("ElementalArrows.EnderArrow.Craftable"));
		createRecipe(lifeArrow, Material.SPECKLED_MELON, AA.getConfig().getBoolean("ElementalArrows.LifeArrow.Craftable"));
		createRecipe(deathArrow, Material.SKULL_ITEM, (byte) 3, AA.getConfig().getBoolean("ElementalArrows.DeathArrow.Craftable"));
		createRecipe(lightArrow, Material.GLOWSTONE_DUST, AA.getConfig().getBoolean("ElementalArrows.LightArrow.Craftable"));
		createRecipe(darknessArrow, Material.COAL, AA.getConfig().getBoolean("ElementalArrows.DarknessArrow.Craftable"));
		createRecipe(darknessArrow, Material.COAL, (byte) 1, AA.getConfig().getBoolean("ElementalArrows.DarknessArrow.Craftable"));
		createRecipe(fireArrow, Material.FIREBALL, AA.getConfig().getBoolean("ElementalArrows.FireArrow.Craftable"));
		createRecipe(frostArrow, Material.SNOW_BALL, AA.getConfig().getBoolean("ElementalArrows.FrostArrow.Craftable"));
		createRecipe(waterArrow, Material.WATER_BUCKET, AA.getConfig().getBoolean("ElementalArrows.WaterArrow.Craftable"));
		createRecipe(necroticArrow, Material.ROTTEN_FLESH, AA.getConfig().getBoolean("ElementalArrows.NecroticArrow.Craftable"));
		createRecipe(confusionArrow, Material.POISONOUS_POTATO, AA.getConfig().getBoolean("ElementalArrows.ConfusionArrow.Craftable"));
		createRecipe(magneticArrow, Material.IRON_INGOT, AA.getConfig().getBoolean("ElementalArrows.MagneticArrow.Craftable"));
		
		AA.getLogger().info("Disabled Arrows: " + disabledArrows);
	}//Close enable method
	
	private static void createRecipe(ItemStack recipeItem, Material secondaryMaterial, boolean configCraftableFilePath){
		if (configCraftableFilePath == true){
			ShapelessRecipe recipe = new ShapelessRecipe(recipeItem).addIngredient(secondaryMaterial).addIngredient(Material.ARROW);
			Bukkit.getServer().addRecipe(recipe);
		}
		else{
			disabledArrows.add(recipeItem.getItemMeta().getDisplayName().replace(" Arrow", ""));
		}
	}
	
	@SuppressWarnings("deprecation")
	private static void createRecipe(ItemStack recipeItem, Material secondaryMaterial, byte byteData, boolean configCraftableFilePath){
		if (configCraftableFilePath == true){
			ShapelessRecipe recipe = new ShapelessRecipe(recipeItem).addIngredient(secondaryMaterial, (byte) byteData).addIngredient(Material.ARROW);
			Bukkit.getServer().addRecipe(recipe);
		}
		else{
			disabledArrows.add(recipeItem.getItemMeta().getDisplayName().replace(" Arrow", ""));
		}
	}
}//Close class