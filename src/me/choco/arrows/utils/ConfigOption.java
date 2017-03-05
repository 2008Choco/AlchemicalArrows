package me.choco.arrows.utils;

import org.bukkit.configuration.file.FileConfiguration;

import me.choco.arrows.AlchemicalArrows;

/**
 * An easy-access configuration class holding all configured values
 * 
 * @author Parker Hawke - 2008Choco
 */
public class ConfigOption {
	
	public static boolean CHECK_FOR_UPDATES;
	public static boolean METRICS_ENABLED;
	public static boolean CUSTOM_DEATH_MESSAGES;
	
	// Skeleton configuration options
	public static int SKELETON_SHOOT_PERCENTAGE;
	public static int SKELETON_LOOT_PERCENTAGE;
	
	// Arrow configuration options
	public static int AIR_CRAFTS;
	public static boolean AIR_ALLOW_INFINITY;
	public static boolean AIR_SKELETONS_CAN_SHOOT;
	public static double AIR_SKELETON_LOOT_WEIGHT;
	public static String AIR_DISPLAY_NAME;
	
	public static int EARTH_CRAFTS;
	public static boolean EARTH_ALLOW_INFINITY;
	public static boolean EARTH_SKELETONS_CAN_SHOOT;
	public static double EARTH_SKELETON_LOOT_WEIGHT;
	public static String EARTH_DISPLAY_NAME;
	
	public static int MAGIC_CRAFTS;
	public static boolean MAGIC_ALLOW_INFINITY;
	public static boolean MAGIC_SKELETONS_CAN_SHOOT;
	public static double MAGIC_SKELETON_LOOT_WEIGHT;
	public static String MAGIC_DISPLAY_NAME;
	
	public static int ENDER_CRAFTS;
	public static boolean ENDER_ALLOW_INFINITY;
	public static boolean ENDER_SKELETONS_CAN_SHOOT;
	public static double ENDER_SKELETON_LOOT_WEIGHT;
	public static String ENDER_DISPLAY_NAME;
	
	public static int LIFE_CRAFTS;
	public static boolean LIFE_ALLOW_INFINITY;
	public static boolean LIFE_SKELETONS_CAN_SHOOT;
	public static double LIFE_SKELETON_LOOT_WEIGHT;
	public static String LIFE_DISPLAY_NAME;
	
	public static int DEATH_CRAFTS;
	public static boolean DEATH_ALLOW_INFINITY;
	public static boolean DEATH_SKELETONS_CAN_SHOOT;
	public static double DEATH_SKELETON_LOOT_WEIGHT;
	public static boolean DEATH_INSTANT_DEATH_POSSIBLE;
	public static int DEATH_INSTANT_DEATH_PERCENT_CHANCE;
	public static String DEATH_DISPLAY_NAME;
	
	public static int LIGHT_CRAFTS;
	public static boolean LIGHT_ALLOW_INFINITY;
	public static boolean LIGHT_SKELETONS_CAN_SHOOT;
	public static double LIGHT_SKELETON_LOOT_WEIGHT;
	public static boolean LIGHT_STRIKES_LIGHTNING;
	public static String LIGHT_DISPLAY_NAME;
	
	public static int DARKNESS_CRAFTS;
	public static boolean DARKNESS_ALLOW_INFINITY;
	public static boolean DARKNESS_SKELETONS_CAN_SHOOT;
	public static double DARKNESS_SKELETON_LOOT_WEIGHT;
	public static String DARKNESS_DISPLAY_NAME;
	
	public static int FIRE_CRAFTS;
	public static boolean FIRE_ALLOW_INFINITY;
	public static boolean FIRE_SKELETONS_CAN_SHOOT;
	public static double FIRE_SKELETON_LOOT_WEIGHT;
	public static String FIRE_DISPLAY_NAME;
	
	public static int FROST_CRAFTS;
	public static boolean FROST_ALLOW_INFINITY;
	public static boolean FROST_SKELETONS_CAN_SHOOT;
	public static double FROST_SKELETON_LOOT_WEIGHT;
	public static String FROST_DISPLAY_NAME;
	
	public static int WATER_CRAFTS;
	public static boolean WATER_ALLOW_INFINITY;
	public static boolean WATER_SKELETONS_CAN_SHOOT;
	public static double WATER_SKELETON_LOOT_WEIGHT;
	public static String WATER_DISPLAY_NAME;
	
	public static int NECROTIC_CRAFTS;
	public static boolean NECROTIC_ALLOW_INFINITY;
	public static boolean NECROTIC_SKELETONS_CAN_SHOOT;
	public static double NECROTIC_SKELETON_LOOT_WEIGHT;
	public static String NECROTIC_DISPLAY_NAME;
	
	public static int CONFUSION_CRAFTS;
	public static boolean CONFUSION_ALLOW_INFINITY;
	public static boolean CONFUSION_SKELETONS_CAN_SHOOT;
	public static double CONFUSION_SKELETON_LOOT_WEIGHT;
	public static String CONFUSION_DISPLAY_NAME;
	
	public static int MAGNETIC_CRAFTS;
	public static boolean MAGNETIC_ALLOW_INFINITY;
	public static boolean MAGNETIC_SKELETONS_CAN_SHOOT;
	public static double MAGNETIC_SKELETON_LOOT_WEIGHT;
	public static String MAGNETIC_DISPLAY_NAME;
	
	public static int GRAPPLE_CRAFTS;
	public static boolean GRAPPLE_ALLOW_INFINITY;
	public static boolean GRAPPLE_SKELETONS_CAN_SHOOT;
	public static double GRAPPLE_SKELETON_LOOT_WEIGHT;
	public static double GRAPPLE_GRAPPLE_FORCE;
	public static String GRAPPLE_DISPLAY_NAME;
	
	/**
	 * Load all values from the main configuration file to the fields. All
	 * fields are null until this method is invoked at least once.
	 * 
	 * @param plugin - An instance of AlchemicalArrows to retrieve the config file
	 */
	public static void loadConfigurationValues(AlchemicalArrows plugin){
		FileConfiguration config = plugin.getConfig();
		
		CHECK_FOR_UPDATES = config.getBoolean("CheckForUpdates", true);
		METRICS_ENABLED = config.getBoolean("MetricsEnabled", true);
		CUSTOM_DEATH_MESSAGES = config.getBoolean("CustomDeathMessages", true);

		SKELETON_SHOOT_PERCENTAGE = config.getInt("Skeletons.SkeletonShootPercentage", 10);
		SKELETON_LOOT_PERCENTAGE = config.getInt("Skeletons.SkeletonLootPercentage", 15);
		
		AIR_CRAFTS = config.getInt("Arrows.AirArrow.Crafts", 8);
		AIR_ALLOW_INFINITY = config.getBoolean("Arrows.AirArrow.AllowInfinity", false);
		AIR_SKELETONS_CAN_SHOOT = config.getBoolean("Arrows.AirArrow.SkeletonsCanShoot", true);
		AIR_SKELETON_LOOT_WEIGHT = config.getDouble("Arrows.AirArrow.SkeletonLootWeight", 6.66);
		AIR_DISPLAY_NAME = config.getString("Arrows.AirArrow.DisplayName", "Air Arrow");
		
		EARTH_CRAFTS = config.getInt("Arrows.EarthArrow.Crafts", 8);
		EARTH_ALLOW_INFINITY = config.getBoolean("Arrows.EarthArrow.AllowInfinity", false);
		EARTH_SKELETONS_CAN_SHOOT = config.getBoolean("Arrows.EarthArrow.SkeletonsCanShoot", true);
		EARTH_SKELETON_LOOT_WEIGHT = config.getDouble("Arrows.EarthArrow.SkeletonLootWeight", 6.66);
		EARTH_DISPLAY_NAME = config.getString("Arrows.EarthArrow.DisplayName", "Earth Arrow");
		
		MAGIC_CRAFTS = config.getInt("Arrows.MagicArrow.Crafts", 8);
		MAGIC_ALLOW_INFINITY = config.getBoolean("Arrows.MagicArrow.AllowInfinity", false);
		MAGIC_SKELETONS_CAN_SHOOT = config.getBoolean("Arrows.MagicArrow.SkeletonsCanShoot", true);
		MAGIC_SKELETON_LOOT_WEIGHT = config.getDouble("Arrows.MagicArrow.SkeletonLootWeight", 6.66);
		MAGIC_DISPLAY_NAME = config.getString("Arrows.MagicArrow.DisplayName", "Magic Arrow");
		
		ENDER_CRAFTS = config.getInt("Arrows.EnderArrow.Crafts", 8);
		ENDER_ALLOW_INFINITY = config.getBoolean("Arrows.EnderArrow.AllowInfinity", false);
		ENDER_SKELETONS_CAN_SHOOT = config.getBoolean("Arrows.EnderArrow.SkeletonsCanShoot", true);
		ENDER_SKELETON_LOOT_WEIGHT = config.getDouble("Arrows.EnderArrow.SkeletonLootWeight", 6.66);
		ENDER_DISPLAY_NAME = config.getString("Arrows.EnderArrow.DisplayName", "Ender Arrow");
		
		LIFE_CRAFTS = config.getInt("Arrows.LifeArrow.Crafts", 8);
		LIFE_ALLOW_INFINITY = config.getBoolean("Arrows.LifeArrow.AllowInfinity", false);
		LIFE_SKELETONS_CAN_SHOOT = config.getBoolean("Arrows.LifeArrow.SkeletonsCanShoot", true);
		LIFE_SKELETON_LOOT_WEIGHT = config.getDouble("Arrows.LifeArrow.SkeletonLootWeight", 6.66);
		LIFE_DISPLAY_NAME = config.getString("Arrows.LifeArrow.DisplayName", "Life Arrow");
		
		DEATH_CRAFTS = config.getInt("Arrows.DeathArrow.Crafts", 8);
		DEATH_ALLOW_INFINITY = config.getBoolean("Arrows.DeathArrow.AllowInfinity", false);
		DEATH_SKELETONS_CAN_SHOOT = config.getBoolean("Arrows.DeathArrow.SkeletonsCanShoot", false);
		DEATH_SKELETON_LOOT_WEIGHT = config.getDouble("Arrows.DeathArrow.SkeletonLootWeight", 6.66);
		DEATH_INSTANT_DEATH_POSSIBLE = config.getBoolean("Arrows.DeathArrow.InstantDeathPossible");
		DEATH_INSTANT_DEATH_PERCENT_CHANCE = config.getInt("Arrows.DeathArrow.InstantDeathPercentChance");
		DEATH_DISPLAY_NAME = config.getString("Arrows.DeathArrow.DisplayName", "Death Arrow");
		
		LIGHT_CRAFTS = config.getInt("Arrows.LightArrow.Crafts", 8);
		LIGHT_ALLOW_INFINITY = config.getBoolean("Arrows.LightArrow.AllowInfinity", false);
		LIGHT_SKELETONS_CAN_SHOOT = config.getBoolean("Arrows.LightArrow.SkeletonsCanShoot", true);
		LIGHT_SKELETON_LOOT_WEIGHT = config.getDouble("Arrows.LightArrow.SkeletonLootWeight", 6.66);
		LIGHT_STRIKES_LIGHTNING = config.getBoolean("Arrows.LightArrow.StrikesLightning");
		LIGHT_DISPLAY_NAME = config.getString("Arrows.LightArrow.DisplayName", "Light Arrow");
		
		DARKNESS_CRAFTS = config.getInt("Arrows.DarknessArrow.Crafts", 8);
		DARKNESS_ALLOW_INFINITY = config.getBoolean("Arrows.DarknessArrow.AllowInfinity", false);
		DARKNESS_SKELETONS_CAN_SHOOT = config.getBoolean("Arrows.DarknessArrow.SkeletonsCanShoot", true);
		DARKNESS_SKELETON_LOOT_WEIGHT = config.getDouble("Arrows.DarknessArrow.SkeletonLootWeight", 6.66);
		DARKNESS_DISPLAY_NAME = config.getString("Arrows.DarknessArrow.DisplayName", "Darkness Arrow");
		
		FIRE_CRAFTS = config.getInt("Arrows.FireArrow.Crafts", 8);
		FIRE_ALLOW_INFINITY = config.getBoolean("Arrows.FireArrow.AllowInfinity", false);
		FIRE_SKELETONS_CAN_SHOOT = config.getBoolean("Arrows.FireArrow.SkeletonsCanShoot", true);
		FIRE_SKELETON_LOOT_WEIGHT = config.getDouble("Arrows.FireArrow.SkeletonLootWeight", 6.66);
		FIRE_DISPLAY_NAME = config.getString("Arrows.FireArrow.DisplayName", "Fire Arrow");
		
		FROST_CRAFTS = config.getInt("Arrows.FrostArrow.Crafts", 8);
		FROST_ALLOW_INFINITY = config.getBoolean("Arrows.FrostArrow.AllowInfinity", false);
		FROST_SKELETONS_CAN_SHOOT = config.getBoolean("Arrows.FrostArrow.SkeletonsCanShoot", true);
		FROST_SKELETON_LOOT_WEIGHT = config.getDouble("Arrows.FrostArrow.SkeletonLootWeight", 6.66);
		FROST_DISPLAY_NAME = config.getString("Arrows.FrostArrow.DisplayName", "Frost Arrow");
		
		WATER_CRAFTS = config.getInt("Arrows.WaterArrow.Crafts", 8);
		WATER_ALLOW_INFINITY = config.getBoolean("Arrows.WaterArrow.AllowInfinity", false);
		WATER_SKELETONS_CAN_SHOOT = config.getBoolean("Arrows.WaterArrow.SkeletonsCanShoot", true);
		WATER_SKELETON_LOOT_WEIGHT = config.getDouble("Arrows.WaterArrow.SkeletonLootWeight", 6.66);
		WATER_DISPLAY_NAME = config.getString("Arrows.WaterArrow.DisplayName", "Water Arrow");
		
		NECROTIC_CRAFTS = config.getInt("Arrows.NecroticArrow.Crafts", 8);
		NECROTIC_ALLOW_INFINITY = config.getBoolean("Arrows.NecroticArrow.AllowInfinity", false);
		NECROTIC_SKELETONS_CAN_SHOOT = config.getBoolean("Arrows.NecroticArrow.SkeletonsCanShoot", true);
		NECROTIC_SKELETON_LOOT_WEIGHT = config.getDouble("Arrows.NecroticArrow.SkeletonLootWeight", 6.66);
		NECROTIC_DISPLAY_NAME = config.getString("Arrows.NecroticArrow.DisplayName", "Necrotic Arrow");
		
		CONFUSION_CRAFTS = config.getInt("Arrows.ConfusionArrow.Crafts", 8);
		CONFUSION_ALLOW_INFINITY = config.getBoolean("Arrows.ConfusionArrow.AllowInfinity", false);
		CONFUSION_SKELETONS_CAN_SHOOT = config.getBoolean("Arrows.ConfusionArrow.SkeletonsCanShoot", true);
		CONFUSION_SKELETON_LOOT_WEIGHT = config.getDouble("Arrows.ConfusionArrow.SkeletonLootWeight", 6.66);
		CONFUSION_DISPLAY_NAME = config.getString("Arrows.ConfusionArrow.DisplayName", "Confusion Arrow");
		
		MAGNETIC_CRAFTS = config.getInt("Arrows.MagneticArrow.Crafts", 8);
		MAGNETIC_ALLOW_INFINITY = config.getBoolean("Arrows.MagneticArrow.AllowInfinity", false);
		MAGNETIC_SKELETONS_CAN_SHOOT = config.getBoolean("Arrows.MagneticArrow.SkeletonsCanShoot", true);
		MAGNETIC_SKELETON_LOOT_WEIGHT = config.getDouble("Arrows.MagneticArrow.SkeletonLootWeight", 6.66);
		MAGNETIC_DISPLAY_NAME = config.getString("Arrows.MagneticArrow.DisplayName", "Magnetic Arrow");
		
		GRAPPLE_CRAFTS = config.getInt("Arrows.GrappleArrow.Crafts", 8);
		GRAPPLE_ALLOW_INFINITY = config.getBoolean("Arrows.GrappleArrow.AllowInfinity", false);
		GRAPPLE_SKELETONS_CAN_SHOOT = config.getBoolean("Arrows.GrappleArrow.SkeletonsCanShoot", true);
		GRAPPLE_SKELETON_LOOT_WEIGHT = config.getDouble("Arrows.GrappleArrow.SkeletonLootWeight", 6.66);
		GRAPPLE_GRAPPLE_FORCE = config.getDouble("Arrows.GrappleArrow.GrappleForce");
		GRAPPLE_DISPLAY_NAME = config.getString("Arrows.GrappleArrow.DisplayName", "Grapple Arrow");
	}
}