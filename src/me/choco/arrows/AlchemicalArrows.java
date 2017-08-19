package me.choco.arrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;

import me.choco.arrows.arrows.AirArrow;
import me.choco.arrows.arrows.ConfusionArrow;
import me.choco.arrows.arrows.DarknessArrow;
import me.choco.arrows.arrows.DeathArrow;
import me.choco.arrows.arrows.EarthArrow;
import me.choco.arrows.arrows.EnderArrow;
import me.choco.arrows.arrows.FireArrow;
import me.choco.arrows.arrows.FrostArrow;
import me.choco.arrows.arrows.GrappleArrow;
import me.choco.arrows.arrows.LifeArrow;
import me.choco.arrows.arrows.LightArrow;
import me.choco.arrows.arrows.MagicArrow;
import me.choco.arrows.arrows.MagneticArrow;
import me.choco.arrows.arrows.NecroticArrow;
import me.choco.arrows.arrows.WaterArrow;
import me.choco.arrows.events.ArrowHitEntityListener;
import me.choco.arrows.events.ArrowHitGroundListener;
import me.choco.arrows.events.ArrowHitPlayerListener;
import me.choco.arrows.events.CustomDeathMsgListener;
import me.choco.arrows.events.PickupArrowListener;
import me.choco.arrows.events.ProjectileShootListener;
import me.choco.arrows.events.SkeletonKillListener;
import me.choco.arrows.registry.ArrowRegistry;
import me.choco.arrows.utils.ConfigOption;
import me.choco.arrows.utils.ItemRecipes;
import me.choco.arrows.utils.ParticleLoop;
import me.choco.arrows.utils.commands.GiveArrowCmd;
import me.choco.arrows.utils.commands.MainCmd;
import me.choco.arrows.utils.general.ItemBuilder;
import me.choco.arrows.utils.general.Metrics;

public class AlchemicalArrows extends JavaPlugin {
	
	private static final int RESOURCE_ID = 11693;
	private static final String SPIGET_LINK = "https://api.spiget.org/v2/resources/" + RESOURCE_ID + "/versions/latest";
	
	private static final Gson GSON = new Gson();
	
	private static AlchemicalArrows instance;
	private ArrowRegistry registry;
	
	private boolean worldGuardEnabled = false;
	private boolean newVersionAvailable = false;
	
	private boolean usingPaper = false;
	
	@SuppressWarnings("deprecation")
	@Override
	public void onEnable(){
		instance = this;
		this.registry = new ArrowRegistry();
		this.usingPaper = this.checkServerMod(); // Check for PaperSpigot... Damn it velocity caps
		this.saveDefaultConfig();
		ConfigOption.loadConfigurationValues(this);
		
		//Check if WorldGuard is available
		worldGuardEnabled = Bukkit.getPluginManager().getPlugin("WorldGuard") != null;	
		
		//Enable the particle loop
		new ParticleLoop(this).runTaskTimerAsynchronously(this, 0L, 1L);
		
		//Register events
		this.getLogger().info("Registering events");
		Bukkit.getPluginManager().registerEvents(new ArrowHitEntityListener(this), this);
		Bukkit.getPluginManager().registerEvents(new ArrowHitGroundListener(this), this);
		Bukkit.getPluginManager().registerEvents(new ArrowHitPlayerListener(this), this);
		Bukkit.getPluginManager().registerEvents(new ProjectileShootListener(this), this);
		Bukkit.getPluginManager().registerEvents(new CustomDeathMsgListener(this), this);
		Bukkit.getPluginManager().registerEvents(new PickupArrowListener(this), this);
		Bukkit.getPluginManager().registerEvents(new SkeletonKillListener(this), this);
		Bukkit.getPluginManager().registerEvents(new ItemRecipes(), this);
		
		//Register commands
		this.getLogger().info("Registering commands");
		
		MainCmd mc = new MainCmd(this);
		GiveArrowCmd gac = new GiveArrowCmd();
		this.getCommand("alchemicalarrows").setExecutor(mc); this.getCommand("alchemicalarrows").setTabCompleter(mc);
		this.getCommand("givearrow").setExecutor(gac); this.getCommand("givearrow").setTabCompleter(gac);
		
		//Register crafting recipes
		this.getLogger().info("Registering recipes");
		this.newRecipe("air_arrow", ItemRecipes.AIR_ARROW, ConfigOption.AIR_CRAFTS, Material.FEATHER);
		this.newRecipe("confusion_arrow", ItemRecipes.CONFUSION_ARROW, ConfigOption.CONFUSION_CRAFTS, Material.POISONOUS_POTATO);
		this.newRecipe("darnkess_arrow", ItemRecipes.DARKNESS_ARROW, ConfigOption.DARKNESS_CRAFTS, Material.COAL);
		this.newRecipe("darnkess_arrow2", ItemRecipes.DARKNESS_ARROW, ConfigOption.DARKNESS_CRAFTS, new MaterialData(Material.COAL, (byte) 1));
		this.newRecipe("death_arrow", ItemRecipes.DEATH_ARROW, ConfigOption.DEATH_CRAFTS, new MaterialData(Material.SKULL, (byte) 1));
		this.newRecipe("earth_arrow", ItemRecipes.EARTH_ARROW, ConfigOption.EARTH_CRAFTS, Material.DIRT);
		this.newRecipe("ender_arrow", ItemRecipes.ENDER_ARROW, ConfigOption.ENDER_CRAFTS, Material.EYE_OF_ENDER);
		this.newRecipe("fire_arrow", ItemRecipes.FIRE_ARROW, ConfigOption.FIRE_CRAFTS, Material.FIREBALL);
		this.newRecipe("frost_arrow", ItemRecipes.FROST_ARROW, ConfigOption.FROST_CRAFTS, Material.SNOW_BALL);
		this.newRecipe("life_arrow", ItemRecipes.LIFE_ARROW, ConfigOption.LIFE_CRAFTS, Material.SPECKLED_MELON);
		this.newRecipe("light_arrow", ItemRecipes.LIGHT_ARROW, ConfigOption.LIGHT_CRAFTS, Material.GLOWSTONE_DUST);
		this.newRecipe("magic_arrow", ItemRecipes.MAGIC_ARROW, ConfigOption.MAGIC_CRAFTS, Material.BLAZE_POWDER);
		this.newRecipe("magnetic_arrow", ItemRecipes.MAGNETIC_ARROW, ConfigOption.MAGNETIC_CRAFTS, Material.IRON_INGOT);
		this.newRecipe("necrotic_arrow", ItemRecipes.NECROTIC_ARROW, ConfigOption.NECROTIC_CRAFTS, Material.ROTTEN_FLESH);
		this.newRecipe("water_arrow", ItemRecipes.WATER_ARROW, ConfigOption.WATER_CRAFTS, Material.WATER_BUCKET);
		this.newRecipe("grapple_arrow", ItemRecipes.GRAPPLE_ARROW, ConfigOption.GRAPPLE_CRAFTS, Material.TRIPWIRE_HOOK);
		
		//Arrow registry
		this.getLogger().info("Registering all basic AlchemicalArrow arrows");
		ArrowRegistry.registerAlchemicalArrow(ItemRecipes.AIR_ARROW, AirArrow.class);
		ArrowRegistry.registerAlchemicalArrow(ItemRecipes.CONFUSION_ARROW, ConfusionArrow.class);
		ArrowRegistry.registerAlchemicalArrow(ItemRecipes.DARKNESS_ARROW, DarknessArrow.class);
		ArrowRegistry.registerAlchemicalArrow(ItemRecipes.DEATH_ARROW, DeathArrow.class);
		ArrowRegistry.registerAlchemicalArrow(ItemRecipes.EARTH_ARROW, EarthArrow.class);
		ArrowRegistry.registerAlchemicalArrow(ItemRecipes.ENDER_ARROW, EnderArrow.class);
		ArrowRegistry.registerAlchemicalArrow(ItemRecipes.FIRE_ARROW, FireArrow.class);
		ArrowRegistry.registerAlchemicalArrow(ItemRecipes.FROST_ARROW, FrostArrow.class);
		ArrowRegistry.registerAlchemicalArrow(ItemRecipes.LIFE_ARROW, LifeArrow.class);
		ArrowRegistry.registerAlchemicalArrow(ItemRecipes.LIGHT_ARROW, LightArrow.class);
		ArrowRegistry.registerAlchemicalArrow(ItemRecipes.MAGIC_ARROW, MagicArrow.class);
		ArrowRegistry.registerAlchemicalArrow(ItemRecipes.MAGNETIC_ARROW, MagneticArrow.class);
		ArrowRegistry.registerAlchemicalArrow(ItemRecipes.NECROTIC_ARROW, NecroticArrow.class);
		ArrowRegistry.registerAlchemicalArrow(ItemRecipes.WATER_ARROW, WaterArrow.class);
		ArrowRegistry.registerAlchemicalArrow(ItemRecipes.GRAPPLE_ARROW, GrappleArrow.class);
		
		//Load Metrics
		if (ConfigOption.METRICS_ENABLED){
			this.getLogger().info("Enabling Plugin Metrics");
		    try{
		        Metrics metrics = new Metrics(this);
		        metrics.start();
		    }catch (IOException e){
		    	e.printStackTrace();
		        getLogger().warning("Could not enable Plugin Metrics. If issues continue, please put in a ticket on the "
		        	+ "AlchemicalArrows development page");
		    }
		}
		
		//Check for newer version (Spiget API)
		if (ConfigOption.CHECK_FOR_UPDATES){
			this.getLogger().info("Getting version information...");
			this.doVersionCheck();
		}
	}
	
	@Override
	public void onDisable() {
		this.registry.clearRegisteredArrows();
		this.registry.clearArrowRegistry();
	}
	
	/**
	 * Get an instance of AlchemicalArrows
	 * 
	 * @return the AlchemicalArrows instance
	 */
	public static AlchemicalArrows getPlugin(){
		return instance;
	}
	
	/**
	 * Get the arrow registry instance used to register arrows 
	 * to AlchemicalArrows. All arrows must be registered in order
	 * to be recognized by the plugin
	 * 
	 * @return the arrow registry instance
	 */
	public ArrowRegistry getArrowRegistry(){
		return registry;
	}

	/**
	 * Whether WorldGuard support is available or not. If the returned
	 * value is true, some arrow functionality may be limited in WorldGuard
	 * regions
	 * 
	 * @return true if WorldGuard is present on the server
	 */
	public boolean isWorldGuardSupported() {
		return worldGuardEnabled;
	}
	
	/**
	 * Whether a new version of AlchemicalArrows is available or not. This
	 * method does not make a version check, but simply retrieves a cached value
	 * 
	 * @return true if a new version is available
	 */
	public boolean isNewVersionAvailable() {
		return newVersionAvailable;
	}
	
	/**
	 * Whether the server is using Paper Spigot or not. If the returned
	 * value is true, velocity-based arrows will be limited due to limits
	 * set by the server mod (|4| in each direction)
	 * 
	 * @return true if using Paper Spigot
	 */
	public boolean isUsingPaper() {
		return usingPaper;
	}
	
	/**
	 * Perform an asynchronous version check to SpiGet. Results may be
	 * retrieved with {@link #isNewVersionAvailable()}
	 */
	public void doVersionCheck() {
		Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(SPIGET_LINK).openStream()))){
				JsonObject object = GSON.fromJson(reader, JsonObject.class);
				String currentVersion = getDescription().getVersion(), recentVersion = object.get("name").getAsString();
				
				if (!currentVersion.equals(recentVersion)) {
					getLogger().info("New version available. Your Version = " + currentVersion + ". New Version = " + recentVersion);
					newVersionAvailable = true;
				}
			}catch(IOException e){
				getLogger().info("Could not check for a new version. Perhaps the website is down?");
			}
		});
	}
	
	private void newRecipe(String itemName, ItemStack item, int amount, MaterialData secondaryMaterial) {
		ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, itemName), new ItemBuilder(item).setAmount(amount).build())
				.shape("AAA", "ASA", "AAA")
				.setIngredient('A', Material.ARROW)
				.setIngredient('S', secondaryMaterial);
		this.getServer().addRecipe(recipe);
	}
	
	private void newRecipe(String itemName, ItemStack item, int amount, Material material) {
		this.newRecipe(itemName, item, amount, new MaterialData(material));
	}
	
	private boolean checkServerMod() {
		Server server = Bukkit.getServer();
		String version = server.getClass().getPackage().getName().split("\\.")[3];
		try {
			Class<?> classCraftServer = Class.forName("org.bukkit.craftbukkit." + version + ".CraftServer");
			Object craftServer = classCraftServer.cast(server);
			Field serverName = classCraftServer.getField("serverName");
			
			serverName.setAccessible(true);
			boolean usingPaper = ((String) serverName.get(craftServer)).equals("Paper");
			serverName.setAccessible(false);
			
			System.out.println("PaperSpigot detected. Arrow velocities will be limited");
			return usingPaper;
		} catch (NoSuchFieldException | SecurityException | ClassNotFoundException | IllegalArgumentException | IllegalAccessException e) {
			System.out.println("Could not determine proper server implementation. Assuming Paper is not in use");
			return false;
		}
	}
	
}