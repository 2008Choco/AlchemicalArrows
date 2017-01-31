package me.choco.arrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
import me.choco.arrows.utils.arrows.AirArrow;
import me.choco.arrows.utils.arrows.ConfusionArrow;
import me.choco.arrows.utils.arrows.DarknessArrow;
import me.choco.arrows.utils.arrows.DeathArrow;
import me.choco.arrows.utils.arrows.EarthArrow;
import me.choco.arrows.utils.arrows.EnderArrow;
import me.choco.arrows.utils.arrows.FireArrow;
import me.choco.arrows.utils.arrows.FrostArrow;
import me.choco.arrows.utils.arrows.GrappleArrow;
import me.choco.arrows.utils.arrows.LifeArrow;
import me.choco.arrows.utils.arrows.LightArrow;
import me.choco.arrows.utils.arrows.MagicArrow;
import me.choco.arrows.utils.arrows.MagneticArrow;
import me.choco.arrows.utils.arrows.NecroticArrow;
import me.choco.arrows.utils.arrows.WaterArrow;
import me.choco.arrows.utils.commands.GiveArrowCmd;
import me.choco.arrows.utils.commands.MainCmd;
import me.choco.arrows.utils.general.Metrics;

public class AlchemicalArrows extends JavaPlugin{
	
	private static final int RESOURCE_ID = 11693;
	private static final String SPIGET_LINK = "https://api.spiget.org/v2/resources/" + RESOURCE_ID + "/versions/latest";
	
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
		this.getServerMod(); // Check for PaperSpigot... Damn it velocity caps
		this.saveDefaultConfig();
		ConfigOption.loadConfigurationValues(this);
		ItemRecipes recipes = new ItemRecipes(this);
		
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
		Bukkit.getPluginManager().registerEvents(recipes, this);
		
		//Register commands
		this.getLogger().info("Registering commands");
		
		MainCmd mc = new MainCmd(this);
		GiveArrowCmd gac = new GiveArrowCmd();
		this.getCommand("alchemicalarrows").setExecutor(mc); this.getCommand("alchemicalarrows").setTabCompleter(mc);
		this.getCommand("givearrow").setExecutor(gac); this.getCommand("givearrow").setTabCompleter(gac);
		
		//Register crafting recipes
		this.getLogger().info("Registering recipes");
		Server server = Bukkit.getServer();
		server.addRecipe(new ShapedRecipe(recipes.airArrow).shape("AAA","AFA","AAA").setIngredient('A', Material.ARROW).setIngredient('F', Material.FEATHER));
		server.addRecipe(new ShapedRecipe(recipes.confusionArrow).shape("AAA","APA","AAA").setIngredient('A', Material.ARROW).setIngredient('P', Material.POISONOUS_POTATO));
		server.addRecipe(new ShapedRecipe(recipes.darknessArrow).shape("AAA","ACA","AAA").setIngredient('A', Material.ARROW).setIngredient('C', Material.COAL));
		server.addRecipe(new ShapedRecipe(recipes.darknessArrow).shape("AAA","ACA","AAA").setIngredient('A', Material.ARROW).setIngredient('C', Material.COAL, (byte) 1));
		server.addRecipe(new ShapedRecipe(recipes.deathArrow).shape("AAA","ASA","AAA").setIngredient('A', Material.ARROW).setIngredient('S', Material.SKULL_ITEM, (byte) 1));
		server.addRecipe(new ShapedRecipe(recipes.earthArrow).shape("AAA","ADA","AAA").setIngredient('A', Material.ARROW).setIngredient('D', Material.DIRT));
		server.addRecipe(new ShapedRecipe(recipes.enderArrow).shape("AAA","AEA","AAA").setIngredient('A', Material.ARROW).setIngredient('E', Material.EYE_OF_ENDER));
		server.addRecipe(new ShapedRecipe(recipes.fireArrow).shape("AAA","AFA","AAA").setIngredient('A', Material.ARROW).setIngredient('F', Material.FIREBALL));
		server.addRecipe(new ShapedRecipe(recipes.frostArrow).shape("AAA","ASA","AAA").setIngredient('A', Material.ARROW).setIngredient('S', Material.SNOW_BALL));
		server.addRecipe(new ShapedRecipe(recipes.lifeArrow).shape("AAA","AMA","AAA").setIngredient('A', Material.ARROW).setIngredient('M', Material.SPECKLED_MELON));
		server.addRecipe(new ShapedRecipe(recipes.lightArrow).shape("AAA","AGA","AAA").setIngredient('A', Material.ARROW).setIngredient('G', Material.GLOWSTONE_DUST));
		server.addRecipe(new ShapedRecipe(recipes.magicArrow).shape("AAA","ABA","AAA").setIngredient('A', Material.ARROW).setIngredient('B', Material.BLAZE_POWDER));
		server.addRecipe(new ShapedRecipe(recipes.magneticArrow).shape("AAA","AIA","AAA").setIngredient('A', Material.ARROW).setIngredient('I', Material.IRON_INGOT));
		server.addRecipe(new ShapedRecipe(recipes.necroticArrow).shape("AAA","AFA","AAA").setIngredient('A', Material.ARROW).setIngredient('F', Material.ROTTEN_FLESH));
		server.addRecipe(new ShapedRecipe(recipes.waterArrow).shape("AAA","AWA","AAA").setIngredient('A', Material.ARROW).setIngredient('W', Material.WATER_BUCKET));
		server.addRecipe(new ShapedRecipe(recipes.grappleArrow).shape("AAA", "ATA", "AAA").setIngredient('A', Material.ARROW).setIngredient('T', Material.TRIPWIRE_HOOK));
		
		//Arrow registry
		this.getLogger().info("Registering all basic AlchemicalArrow arrows");
		ArrowRegistry.registerAlchemicalArrow(recipes.airArrow, AirArrow.class);
		ArrowRegistry.registerAlchemicalArrow(recipes.confusionArrow, ConfusionArrow.class);
		ArrowRegistry.registerAlchemicalArrow(recipes.darknessArrow, DarknessArrow.class);
		ArrowRegistry.registerAlchemicalArrow(recipes.deathArrow, DeathArrow.class);
		ArrowRegistry.registerAlchemicalArrow(recipes.earthArrow, EarthArrow.class);
		ArrowRegistry.registerAlchemicalArrow(recipes.enderArrow, EnderArrow.class);
		ArrowRegistry.registerAlchemicalArrow(recipes.fireArrow, FireArrow.class);
		ArrowRegistry.registerAlchemicalArrow(recipes.frostArrow, FrostArrow.class);
		ArrowRegistry.registerAlchemicalArrow(recipes.lifeArrow, LifeArrow.class);
		ArrowRegistry.registerAlchemicalArrow(recipes.lightArrow, LightArrow.class);
		ArrowRegistry.registerAlchemicalArrow(recipes.magicArrow, MagicArrow.class);
		ArrowRegistry.registerAlchemicalArrow(recipes.magneticArrow, MagneticArrow.class);
		ArrowRegistry.registerAlchemicalArrow(recipes.necroticArrow, NecroticArrow.class);
		ArrowRegistry.registerAlchemicalArrow(recipes.waterArrow, WaterArrow.class);
		ArrowRegistry.registerAlchemicalArrow(recipes.grappleArrow, GrappleArrow.class);
		
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
	
	public static AlchemicalArrows getPlugin(){
		return instance;
	}
	
	public ArrowRegistry getArrowRegistry(){
		return registry;
	}

	public boolean isWorldGuardSupported() {
		return worldGuardEnabled;
	}
	
	public boolean isNewVersionAvailable() {
		return newVersionAvailable;
	}
	
	public boolean isUsingPaper() {
		return usingPaper;
	}
	
	public void doVersionCheck() {
		Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(SPIGET_LINK).openStream()))){

				JSONParser parser = new JSONParser();
				JSONObject json = (JSONObject) parser.parse(reader);
				
				String currentVersion = getDescription().getVersion(), recentVersion = (String) json.get("name");
				
				if (!currentVersion.equals(recentVersion)) {
					getLogger().info("New version available. Your Version = " + currentVersion + ". New Version = " + recentVersion);
					newVersionAvailable = true;
				}
			}catch(IOException e){
				getLogger().info("Could not check for a new version. Perhaps the website is down?");
			} catch (ParseException e) {
				getLogger().info("There was an issue parsing JSON formatted data. If issues continue, please put in a ticket on the "
						+ "AlchemicalArrows development page with the following stacktrace");
				e.printStackTrace();
			}
		});
	}
	
	private void getServerMod() {
		Server server = Bukkit.getServer();
		String version = server.getClass().getPackage().getName().split("\\.")[3];
		try {
			Class<?> classCraftServer = Class.forName("org.bukkit.craftbukkit." + version + ".CraftServer");
			Object craftServer = classCraftServer.cast(server);
			Field serverName = classCraftServer.getField("serverName");
			
			serverName.setAccessible(true);
			this.usingPaper = ((String) serverName.get(craftServer)).equals("Paper");
			serverName.setAccessible(false);
			
			System.out.println("PaperSpigot detected. Arrow velocities will be limited");
		} catch (NoSuchFieldException | SecurityException | ClassNotFoundException | IllegalArgumentException | IllegalAccessException e) {
			System.out.println("Could not determine proper server implementation. Assuming Paper is not in use");
		}
	}
}

/* Changelog 2.3.3:
 * Commands can now be run through console
 * Modified some of the command code (It was rather old and poorly written)
 */