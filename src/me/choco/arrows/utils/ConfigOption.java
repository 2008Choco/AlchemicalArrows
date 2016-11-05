package me.choco.arrows.utils;

import org.bukkit.configuration.file.FileConfiguration;

import me.choco.arrows.AlchemicalArrows;

public class ConfigOption {
	
	public static boolean CHECK_FOR_UPDATES;
	public static boolean METRICS_ENABLED;
	public static int SKELETON_SHOOT_PERCENTAGE;
	public static boolean CUSTOM_DEATH_MESSAGES;
	
	public static int AIR_CRAFTS;
	public static boolean AIR_ALLOW_INFINITY;
	public static boolean AIR_SKELETONS_CAN_SHOOT;
	public static String AIR_DISPLAY_NAME;
	
	public static int EARTH_CRAFTS;
	public static boolean EARTH_ALLOW_INFINITY;
	public static boolean EARTH_SKELETONS_CAN_SHOOT;
	public static String EARTH_DISPLAY_NAME;
	
	public static int MAGIC_CRAFTS;
	public static boolean MAGIC_ALLOW_INFINITY;
	public static boolean MAGIC_SKELETONS_CAN_SHOOT;
	public static String MAGIC_DISPLAY_NAME;
	
	public static int ENDER_CRAFTS;
	public static boolean ENDER_ALLOW_INFINITY;
	public static boolean ENDER_SKELETONS_CAN_SHOOT;
	public static String ENDER_DISPLAY_NAME;
	
	public static int LIFE_CRAFTS;
	public static boolean LIFE_ALLOW_INFINITY;
	public static boolean LIFE_SKELETONS_CAN_SHOOT;
	public static String LIFE_DISPLAY_NAME;
	
	public static int DEATH_CRAFTS;
	public static boolean DEATH_ALLOW_INFINITY;
	public static boolean DEATH_SKELETONS_CAN_SHOOT;
	public static boolean DEATH_INSTANT_DEATH_POSSIBLE;
	public static int DEATH_INSTANT_DEATH_PERCENT_CHANCE;
	public static String DEATH_DISPLAY_NAME;
	
	public static int LIGHT_CRAFTS;
	public static boolean LIGHT_ALLOW_INFINITY;
	public static boolean LIGHT_SKELETONS_CAN_SHOOT;
	public static boolean LIGHT_STRIKES_LIGHTNING;
	public static String LIGHT_DISPLAY_NAME;
	
	public static int DARKNESS_CRAFTS;
	public static boolean DARKNESS_ALLOW_INFINITY;
	public static boolean DARKNESS_SKELETONS_CAN_SHOOT;
	public static String DARKNESS_DISPLAY_NAME;
	
	public static int FIRE_CRAFTS;
	public static boolean FIRE_ALLOW_INFINITY;
	public static boolean FIRE_SKELETONS_CAN_SHOOT;
	public static String FIRE_DISPLAY_NAME;
	
	public static int FROST_CRAFTS;
	public static boolean FROST_ALLOW_INFINITY;
	public static boolean FROST_SKELETONS_CAN_SHOOT;
	public static String FROST_DISPLAY_NAME;
	
	public static int WATER_CRAFTS;
	public static boolean WATER_ALLOW_INFINITY;
	public static boolean WATER_SKELETONS_CAN_SHOOT;
	public static String WATER_DISPLAY_NAME;
	
	public static int NECROTIC_CRAFTS;
	public static boolean NECROTIC_ALLOW_INFINITY;
	public static boolean NECROTIC_SKELETONS_CAN_SHOOT;
	public static String NECROTIC_DISPLAY_NAME;
	
	public static int CONFUSION_CRAFTS;
	public static boolean CONFUSION_ALLOW_INFINITY;
	public static boolean CONFUSION_SKELETONS_CAN_SHOOT;
	public static String CONFUSION_DISPLAY_NAME;
	
	public static int MAGNETIC_CRAFTS;
	public static boolean MAGNETIC_ALLOW_INFINITY;
	public static boolean MAGNETIC_SKELETONS_CAN_SHOOT;
	public static String MAGNETIC_DISPLAY_NAME;
	
	public static int GRAPPLE_CRAFTS;
	public static boolean GRAPPLE_ALLOW_INFINITY;
	public static boolean GRAPPLE_SKELETONS_CAN_SHOOT;
	public static String GRAPPLE_DISPLAY_NAME;
	public static double GRAPPLE_GRAPPLE_FORCE;
	
	public static void loadConfigurationValues(AlchemicalArrows plugin){
		FileConfiguration config = plugin.getConfig();
		
		CHECK_FOR_UPDATES = config.getBoolean("CheckForUpdates");
		METRICS_ENABLED = config.getBoolean("MetricsEnabled");
		SKELETON_SHOOT_PERCENTAGE = config.getInt("SkeletonShootPercentage");
		CUSTOM_DEATH_MESSAGES = config.getBoolean("CustomDeathMessages");
		
		AIR_CRAFTS = config.getInt("Arrows.AirArrow.Crafts");
		AIR_ALLOW_INFINITY = config.getBoolean("Arrows.AirArrow.AllowInfinity");
		AIR_SKELETONS_CAN_SHOOT = config.getBoolean("Arrows.AirArrow.SkeletonsCanShoot");
		AIR_DISPLAY_NAME = config.getString("Arrows.AirArrow.DisplayName");
		
		EARTH_CRAFTS = config.getInt("Arrows.EarthArrow.Crafts");
		EARTH_ALLOW_INFINITY = config.getBoolean("Arrows.EarthArrow.AllowInfinity");
		EARTH_SKELETONS_CAN_SHOOT = config.getBoolean("Arrows.EarthArrow.SkeletonsCanShoot");
		EARTH_DISPLAY_NAME = config.getString("Arrows.EarthArrow.DisplayName");
		
		MAGIC_CRAFTS = config.getInt("Arrows.MagicArrow.Crafts");
		MAGIC_ALLOW_INFINITY = config.getBoolean("Arrows.MagicArrow.AllowInfinity");
		MAGIC_SKELETONS_CAN_SHOOT = config.getBoolean("Arrows.MagicArrow.SkeletonsCanShoot");
		MAGIC_DISPLAY_NAME = config.getString("Arrows.MagicArrow.DisplayName");
		
		ENDER_CRAFTS = config.getInt("Arrows.EnderArrow.Crafts");
		ENDER_ALLOW_INFINITY = config.getBoolean("Arrows.EnderArrow.AllowInfinity");
		ENDER_SKELETONS_CAN_SHOOT = config.getBoolean("Arrows.EnderArrow.SkeletonsCanShoot");
		ENDER_DISPLAY_NAME = config.getString("Arrows.EnderArrow.DisplayName");
		
		LIFE_CRAFTS = config.getInt("Arrows.LifeArrow.Crafts");
		LIFE_ALLOW_INFINITY = config.getBoolean("Arrows.LifeArrow.AllowInfinity");
		LIFE_SKELETONS_CAN_SHOOT = config.getBoolean("Arrows.LifeArrow.SkeletonsCanShoot");
		LIFE_DISPLAY_NAME = config.getString("Arrows.LifeArrow.DisplayName");
		
		DEATH_CRAFTS = config.getInt("Arrows.DeathArrow.Crafts");
		DEATH_ALLOW_INFINITY = config.getBoolean("Arrows.DeathArrow.AllowInfinity");
		DEATH_SKELETONS_CAN_SHOOT = config.getBoolean("Arrows.DeathArrow.SkeletonsCanShoot");
		DEATH_INSTANT_DEATH_POSSIBLE = config.getBoolean("Arrows.DeathArrow.InstantDeathPossible");
		DEATH_INSTANT_DEATH_PERCENT_CHANCE = config.getInt("Arrows.DeathArrow.InstantDeathPercentChance");
		DEATH_DISPLAY_NAME = config.getString("Arrows.DeathArrow.DisplayName");
		
		LIGHT_CRAFTS = config.getInt("Arrows.LightArrow.Crafts");
		LIGHT_ALLOW_INFINITY = config.getBoolean("Arrows.LightArrow.AllowInfinity");
		LIGHT_SKELETONS_CAN_SHOOT = config.getBoolean("Arrows.LightArrow.SkeletonsCanShoot");
		LIGHT_STRIKES_LIGHTNING = config.getBoolean("Arrows.LightArrow.StrikesLightning");
		LIGHT_DISPLAY_NAME = config.getString("Arrows.LightArrow.DisplayName");
		
		DARKNESS_CRAFTS = config.getInt("Arrows.DarknessArrow.Crafts");
		DARKNESS_ALLOW_INFINITY = config.getBoolean("Arrows.DarknessArrow.AllowInfinity");
		DARKNESS_SKELETONS_CAN_SHOOT = config.getBoolean("Arrows.DarknessArrow.SkeletonsCanShoot");
		DARKNESS_DISPLAY_NAME = config.getString("Arrows.DarknessArrow.DisplayName");
		
		FIRE_CRAFTS = config.getInt("Arrows.FireArrow.Crafts");
		FIRE_ALLOW_INFINITY = config.getBoolean("Arrows.FireArrow.AllowInfinity");
		FIRE_SKELETONS_CAN_SHOOT = config.getBoolean("Arrows.FireArrow.SkeletonsCanShoot");
		FIRE_DISPLAY_NAME = config.getString("Arrows.FireArrow.DisplayName");
		
		FROST_CRAFTS = config.getInt("Arrows.FrostArrow.Crafts");
		FROST_ALLOW_INFINITY = config.getBoolean("Arrows.FrostArrow.AllowInfinity");
		FROST_SKELETONS_CAN_SHOOT = config.getBoolean("Arrows.FrostArrow.SkeletonsCanShoot");
		FROST_DISPLAY_NAME = config.getString("Arrows.FrostArrow.DisplayName");
		
		WATER_CRAFTS = config.getInt("Arrows.WaterArrow.Crafts");
		WATER_ALLOW_INFINITY = config.getBoolean("Arrows.WaterArrow.AllowInfinity");
		WATER_SKELETONS_CAN_SHOOT = config.getBoolean("Arrows.WaterArrow.SkeletonsCanShoot");
		WATER_DISPLAY_NAME = config.getString("Arrows.WaterArrow.DisplayName");
		
		NECROTIC_CRAFTS = config.getInt("Arrows.NecroticArrow.Crafts");
		NECROTIC_ALLOW_INFINITY = config.getBoolean("Arrows.NecroticArrow.AllowInfinity");
		NECROTIC_SKELETONS_CAN_SHOOT = config.getBoolean("Arrows.NecroticArrow.SkeletonsCanShoot");
		NECROTIC_DISPLAY_NAME = config.getString("Arrows.NecroticArrow.DisplayName");
		
		CONFUSION_CRAFTS = config.getInt("Arrows.ConfusionArrow.Crafts");
		CONFUSION_ALLOW_INFINITY = config.getBoolean("Arrows.ConfusionArrow.AllowInfinity");
		CONFUSION_SKELETONS_CAN_SHOOT = config.getBoolean("Arrows.ConfusionArrow.SkeletonsCanShoot");
		CONFUSION_DISPLAY_NAME = config.getString("Arrows.ConfusionArrow.DisplayName");
		
		MAGNETIC_CRAFTS = config.getInt("Arrows.MagneticArrow.Crafts");
		MAGNETIC_ALLOW_INFINITY = config.getBoolean("Arrows.MagneticArrow.AllowInfinity");
		MAGNETIC_SKELETONS_CAN_SHOOT = config.getBoolean("Arrows.MagneticArrow.SkeletonsCanShoot");
		MAGNETIC_DISPLAY_NAME = config.getString("Arrows.MagneticArrow.DisplayName");
		
		GRAPPLE_CRAFTS = config.getInt("Arrows.GrappleArrow.Crafts");
		GRAPPLE_ALLOW_INFINITY = config.getBoolean("Arrows.GrappleArrow.AllowInfinity");
		GRAPPLE_SKELETONS_CAN_SHOOT = config.getBoolean("Arrows.GrappleArrow.SkeletonsCanShoot");
		GRAPPLE_DISPLAY_NAME = config.getString("Arrows.GrappleArrow.DisplayName");
		GRAPPLE_GRAPPLE_FORCE = config.getDouble("Arrows.GrappleArrow.GrappleForce");
	}
}