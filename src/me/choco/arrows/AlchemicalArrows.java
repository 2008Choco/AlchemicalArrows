package me.choco.arrows;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import me.choco.arrows.events.ArrowHitEntity;
import me.choco.arrows.events.ArrowHitGround;
import me.choco.arrows.events.ArrowHitPlayer;
import me.choco.arrows.events.ProjectileShoot;
import me.choco.arrows.utils.ArrowRegistry;
import me.choco.arrows.utils.ItemRecipes;
import me.choco.arrows.utils.ParticleLoop;
import me.choco.arrows.utils.arrows.AirArrow;
import me.choco.arrows.utils.arrows.ConfusionArrow;
import me.choco.arrows.utils.arrows.DarknessArrow;
import me.choco.arrows.utils.arrows.DeathArrow;
import me.choco.arrows.utils.arrows.EarthArrow;
import me.choco.arrows.utils.arrows.EnderArrow;
import me.choco.arrows.utils.arrows.FireArrow;
import me.choco.arrows.utils.arrows.FrostArrow;
import me.choco.arrows.utils.arrows.LifeArrow;
import me.choco.arrows.utils.arrows.LightArrow;
import me.choco.arrows.utils.arrows.MagicArrow;
import me.choco.arrows.utils.arrows.MagneticArrow;
import me.choco.arrows.utils.arrows.NecroticArrow;
import me.choco.arrows.utils.arrows.WaterArrow;

public class AlchemicalArrows extends JavaPlugin{
	
	private static AlchemicalArrows instance;
	private ArrowRegistry registry;
	
	@SuppressWarnings("deprecation")
	@Override
	public void onEnable(){
		instance = this;
		registry = new ArrowRegistry(this);
		
		//Enable the particle loop
		new ParticleLoop(this).runTaskTimerAsynchronously(this, 0L, 1L);
		
		//Register events
		this.getLogger().info("Registering events");
		Bukkit.getPluginManager().registerEvents(new ArrowHitEntity(this), this);
		Bukkit.getPluginManager().registerEvents(new ArrowHitGround(this), this);
		Bukkit.getPluginManager().registerEvents(new ArrowHitPlayer(this), this);
		Bukkit.getPluginManager().registerEvents(new ProjectileShoot(this), this);
		Bukkit.getPluginManager().registerEvents(new ItemRecipes(), this);
		
		//Arrow registry
		this.getLogger().info("Registering all basic AlchemicalArrow arrows");
		ArrowRegistry.registerAlchemicalArrow(ItemRecipes.airArrow, AirArrow.class);
		ArrowRegistry.registerAlchemicalArrow(ItemRecipes.confusionArrow, ConfusionArrow.class);
		ArrowRegistry.registerAlchemicalArrow(ItemRecipes.darknessArrow, DarknessArrow.class);
		ArrowRegistry.registerAlchemicalArrow(ItemRecipes.deathArrow, DeathArrow.class);
		ArrowRegistry.registerAlchemicalArrow(ItemRecipes.earthArrow, EarthArrow.class);
		ArrowRegistry.registerAlchemicalArrow(ItemRecipes.enderArrow, EnderArrow.class);
		ArrowRegistry.registerAlchemicalArrow(ItemRecipes.fireArrow, FireArrow.class);
		ArrowRegistry.registerAlchemicalArrow(ItemRecipes.frostArrow, FrostArrow.class);
		ArrowRegistry.registerAlchemicalArrow(ItemRecipes.lifeArrow, LifeArrow.class);
		ArrowRegistry.registerAlchemicalArrow(ItemRecipes.lightArrow, LightArrow.class);
		ArrowRegistry.registerAlchemicalArrow(ItemRecipes.magicArrow, MagicArrow.class);
		ArrowRegistry.registerAlchemicalArrow(ItemRecipes.magneticArrow, MagneticArrow.class);
		ArrowRegistry.registerAlchemicalArrow(ItemRecipes.necroticArrow, NecroticArrow.class);
		ArrowRegistry.registerAlchemicalArrow(ItemRecipes.waterArrow, WaterArrow.class);
		
		//Register crafting recipes
		this.getLogger().info("Registering recipes");
		Bukkit.getServer().addRecipe(new ShapelessRecipe(ItemRecipes.airArrow).addIngredient(Material.ARROW).addIngredient(Material.FEATHER));
		Bukkit.getServer().addRecipe(new ShapelessRecipe(ItemRecipes.confusionArrow).addIngredient(Material.ARROW).addIngredient(Material.POISONOUS_POTATO));
		Bukkit.getServer().addRecipe(new ShapelessRecipe(ItemRecipes.darknessArrow).addIngredient(Material.ARROW).addIngredient(Material.COAL));
		Bukkit.getServer().addRecipe(new ShapelessRecipe(ItemRecipes.darknessArrow).addIngredient(Material.ARROW).addIngredient(Material.COAL, (byte) 1));
		Bukkit.getServer().addRecipe(new ShapelessRecipe(ItemRecipes.deathArrow).addIngredient(Material.ARROW).addIngredient(Material.SKULL_ITEM, (byte) 1));
		Bukkit.getServer().addRecipe(new ShapelessRecipe(ItemRecipes.earthArrow).addIngredient(Material.ARROW).addIngredient(Material.DIRT));
		Bukkit.getServer().addRecipe(new ShapelessRecipe(ItemRecipes.enderArrow).addIngredient(Material.ARROW).addIngredient(Material.EYE_OF_ENDER));
		Bukkit.getServer().addRecipe(new ShapelessRecipe(ItemRecipes.fireArrow).addIngredient(Material.ARROW).addIngredient(Material.FIREBALL));
		Bukkit.getServer().addRecipe(new ShapelessRecipe(ItemRecipes.frostArrow).addIngredient(Material.ARROW).addIngredient(Material.SNOW_BALL));
		Bukkit.getServer().addRecipe(new ShapelessRecipe(ItemRecipes.lifeArrow).addIngredient(Material.ARROW).addIngredient(Material.SPECKLED_MELON));
		Bukkit.getServer().addRecipe(new ShapelessRecipe(ItemRecipes.lightArrow).addIngredient(Material.ARROW).addIngredient(Material.GLOWSTONE_DUST));
		Bukkit.getServer().addRecipe(new ShapelessRecipe(ItemRecipes.magicArrow).addIngredient(Material.ARROW).addIngredient(Material.BLAZE_POWDER));
		Bukkit.getServer().addRecipe(new ShapelessRecipe(ItemRecipes.magneticArrow).addIngredient(Material.ARROW).addIngredient(Material.IRON_INGOT));
		Bukkit.getServer().addRecipe(new ShapelessRecipe(ItemRecipes.necroticArrow).addIngredient(Material.ARROW).addIngredient(Material.ROTTEN_FLESH));
		Bukkit.getServer().addRecipe(new ShapelessRecipe(ItemRecipes.waterArrow).addIngredient(Material.ARROW).addIngredient(Material.WATER_BUCKET));
	}
	
	@Override
	public void onDisable() {
		registry.getRegisteredArrows().clear();
		ArrowRegistry.getArrowRegistry().clear();
	}
	
	public static AlchemicalArrows getPlugin(){
		return instance;
	}
	
	public ArrowRegistry getArrowRegistry(){
		return registry;
	}
}

/* 2.0 CHANGELOG:
 * 
 */

/* PLANS
 * AlchemicalArrow interface class
 * A class that implements AlchemicalArrow for every arrow
 * Create a listener that runs the default AlchemicalArrow method
 * 
 * Entire goal of the rewrite:
 *     Make it so that it's infinitely expandable for developers to create their own arrows
 *     Remove the idea that all arrows are fixed within code, because they shouldn't be
 *       -> AlchemicalArrows should be expandable
 */