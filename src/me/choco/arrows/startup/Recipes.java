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
	
	@SuppressWarnings("deprecation")
	public static void enable(){
		ArrayList<String> disabledArrows = new ArrayList<String>();
		
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
		
		if (AA.getConfig().getBoolean("ElementalArrows.AirArrow.Craftable") == true){
			ShapelessRecipe airArrowRecipe = new ShapelessRecipe(airArrow).addIngredient(Material.FEATHER).addIngredient(Material.ARROW);
			Bukkit.getServer().addRecipe(airArrowRecipe);
		}
		else{
			disabledArrows.add("Air Arrow");
		}
		
		if (AA.getConfig().getBoolean("ElementalArrows.EarthArrow.Craftable") == true){
			ShapelessRecipe earthArrowRecipe = new ShapelessRecipe(earthArrow).addIngredient(Material.DIRT).addIngredient(Material.ARROW);
			Bukkit.getServer().addRecipe(earthArrowRecipe);
		}
		else{
			disabledArrows.add("Earth Arrow");
		}
		
		if (AA.getConfig().getBoolean("ElementalArrows.MagicArrow.Craftable") == true){
			ShapelessRecipe magicArrowRecipe = new ShapelessRecipe(magicArrow).addIngredient(Material.BLAZE_POWDER).addIngredient(Material.ARROW);
			Bukkit.getServer().addRecipe(magicArrowRecipe);
		}
		else{
			disabledArrows.add("Magic Arrow");
		}

		if (AA.getConfig().getBoolean("ElementalArrows.EnderArrow.Craftable") == true){
			ShapelessRecipe enderArrowRecipe = new ShapelessRecipe(enderArrow).addIngredient(Material.EYE_OF_ENDER).addIngredient(Material.ARROW);
			Bukkit.getServer().addRecipe(enderArrowRecipe);
		}
		else{
			disabledArrows.add("Ender Arrow");
		}
		
		if (AA.getConfig().getBoolean("ElementalArrows.LifeArrow.Craftable") == true){
			ShapelessRecipe lifeArrowRecipe = new ShapelessRecipe(lifeArrow).addIngredient(Material.SPECKLED_MELON).addIngredient(Material.ARROW);
			Bukkit.getServer().addRecipe(lifeArrowRecipe);
		}
		else{
			disabledArrows.add("Life Arrow");
		}
		
		if (AA.getConfig().getBoolean("ElementalArrows.DeathArrow.Craftable") == true){
			ShapelessRecipe deathArrowRecipe = new ShapelessRecipe(deathArrow).addIngredient(Material.SKULL_ITEM, (byte)1).addIngredient(Material.ARROW);
			Bukkit.getServer().addRecipe(deathArrowRecipe);
		}
		else{
			disabledArrows.add("Death Arrow");
		}
		
		if (AA.getConfig().getBoolean("ElementalArrows.LightArrow.Craftable") == true){
			ShapelessRecipe lightArrowRecipe = new ShapelessRecipe(lightArrow).addIngredient(Material.GLOWSTONE_DUST).addIngredient(Material.ARROW);
			Bukkit.getServer().addRecipe(lightArrowRecipe);
		}
		else{
			disabledArrows.add("Light Arrow");
		}
		
		if (AA.getConfig().getBoolean("ElementalArrows.DarknessArrow.Craftable") == true){
			ShapelessRecipe darknessArrowRecipe = new ShapelessRecipe(darknessArrow).addIngredient(Material.COAL).addIngredient(Material.ARROW);
			ShapelessRecipe darknessArrowRecipe2 = new ShapelessRecipe(darknessArrow).addIngredient(Material.COAL, (byte) 1).addIngredient(Material.ARROW);
			Bukkit.getServer().addRecipe(darknessArrowRecipe);
			Bukkit.getServer().addRecipe(darknessArrowRecipe2);
		}
		else{
			disabledArrows.add("Darkness Arrow");
		}
		
		if (AA.getConfig().getBoolean("ElementalArrows.FireArrow.Craftable") == true){
			ShapelessRecipe fireArrowRecipe = new ShapelessRecipe(fireArrow).addIngredient(Material.FIREBALL).addIngredient(Material.ARROW);
			Bukkit.getServer().addRecipe(fireArrowRecipe);
		}
		else{
			disabledArrows.add("Fire Arrow");
		}
		
		if (AA.getConfig().getBoolean("ElementalArrows.FrostArrow.Craftable") == true){
			ShapelessRecipe frostArrowRecipe = new ShapelessRecipe(frostArrow).addIngredient(Material.SNOW_BALL).addIngredient(Material.ARROW);
			Bukkit.getServer().addRecipe(frostArrowRecipe);
		}
		else{
			disabledArrows.add("Frost Arrow");
		}
		
		if (AA.getConfig().getBoolean("ElementalArrows.WaterArrow.Craftable") == true){
			ShapelessRecipe waterArrowRecipe = new ShapelessRecipe(waterArrow).addIngredient(Material.WATER_BUCKET).addIngredient(Material.ARROW);
			Bukkit.getServer().addRecipe(waterArrowRecipe);	
		}
		else{
			disabledArrows.add("Water Arrow");
		}
		
		if (AA.getConfig().getBoolean("ElementalArrows.NecroticArrow.Craftable") == true){
			ShapelessRecipe necroticArrowRecipe = new ShapelessRecipe(necroticArrow).addIngredient(Material.ROTTEN_FLESH).addIngredient(Material.ARROW);
			Bukkit.getServer().addRecipe(necroticArrowRecipe);
		}
		else{
			disabledArrows.add("Necrotic Arrow");
		}
		
		if (AA.getConfig().getBoolean("ElementalArrows.ConfusionArrow.Craftable") == true){
			ShapelessRecipe confusionArrowRecipe = new ShapelessRecipe(confusionArrow).addIngredient(Material.POISONOUS_POTATO).addIngredient(Material.ARROW);
			Bukkit.getServer().addRecipe(confusionArrowRecipe);
		}
		else{
			disabledArrows.add("Confusion Arrow");
		}
		
		if (AA.getConfig().getBoolean("ElementalArrows.MagneticArrow.Craftable") == true){
			ShapelessRecipe magneticArrowRecipe = new ShapelessRecipe(magneticArrow).addIngredient(Material.IRON_INGOT).addIngredient(Material.ARROW);
			Bukkit.getServer().addRecipe(magneticArrowRecipe);
		}
		else{
			disabledArrows.add("Magnetic Arrow");
		}
		
		AA.getLogger().info("Disabled Arrows: " + disabledArrows);
	}//Close enable method
}//Close class