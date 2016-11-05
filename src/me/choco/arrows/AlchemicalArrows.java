package me.choco.arrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import me.choco.arrows.events.ArrowHitEntity;
import me.choco.arrows.events.ArrowHitGround;
import me.choco.arrows.events.ArrowHitPlayer;
import me.choco.arrows.events.CustomDeathMessage;
import me.choco.arrows.events.PickupArrow;
import me.choco.arrows.events.ProjectileShoot;
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
	
	private static AlchemicalArrows instance;
	private ArrowRegistry registry;
	
	private boolean worldGuardEnabled = false;
	private boolean newVersionAvailable = false;
	
	@SuppressWarnings("deprecation")
	@Override
	public void onEnable(){
		instance = this;
		registry = new ArrowRegistry();
		this.saveDefaultConfig();
		ConfigOption.loadConfigurationValues(this);
		ItemRecipes recipes = new ItemRecipes(this);
		
		//Check if WorldGuard is available
		worldGuardEnabled = Bukkit.getPluginManager().getPlugin("WorldGuard") != null;	
		
		//Enable the particle loop
		new ParticleLoop(this).runTaskTimerAsynchronously(this, 0L, 1L);
		
		//Register events
		this.getLogger().info("Registering events");
		Bukkit.getPluginManager().registerEvents(new ArrowHitEntity(this), this);
		Bukkit.getPluginManager().registerEvents(new ArrowHitGround(this), this);
		Bukkit.getPluginManager().registerEvents(new ArrowHitPlayer(this), this);
		Bukkit.getPluginManager().registerEvents(new ProjectileShoot(this), this);
		Bukkit.getPluginManager().registerEvents(new CustomDeathMessage(this), this);
		Bukkit.getPluginManager().registerEvents(new PickupArrow(this), this);
		Bukkit.getPluginManager().registerEvents(recipes, this);
		
		//Register commands
		this.getLogger().info("Registering commands");
		
		MainCmd mc = new MainCmd(this);
		GiveArrowCmd gac = new GiveArrowCmd();
		this.getCommand("alchemicalarrows").setExecutor(mc); this.getCommand("alchemicalarrows").setTabCompleter(mc);
		this.getCommand("givearrow").setExecutor(gac); this.getCommand("givearrow").setTabCompleter(gac);
		
		//Register crafting recipes
		this.getLogger().info("Registering recipes");
		Bukkit.getServer().addRecipe(new ShapedRecipe(recipes.airArrow).shape("AAA","AFA","AAA").setIngredient('A', Material.ARROW).setIngredient('F', Material.FEATHER));
		Bukkit.getServer().addRecipe(new ShapedRecipe(recipes.confusionArrow).shape("AAA","APA","AAA").setIngredient('A', Material.ARROW).setIngredient('P', Material.POISONOUS_POTATO));
		Bukkit.getServer().addRecipe(new ShapedRecipe(recipes.darknessArrow).shape("AAA","ACA","AAA").setIngredient('A', Material.ARROW).setIngredient('C', Material.COAL));
		Bukkit.getServer().addRecipe(new ShapedRecipe(recipes.darknessArrow).shape("AAA","ACA","AAA").setIngredient('A', Material.ARROW).setIngredient('C', Material.COAL, (byte) 1));
		Bukkit.getServer().addRecipe(new ShapedRecipe(recipes.deathArrow).shape("AAA","ASA","AAA").setIngredient('A', Material.ARROW).setIngredient('S', Material.SKULL_ITEM, (byte) 1));
		Bukkit.getServer().addRecipe(new ShapedRecipe(recipes.earthArrow).shape("AAA","ADA","AAA").setIngredient('A', Material.ARROW).setIngredient('D', Material.DIRT));
		Bukkit.getServer().addRecipe(new ShapedRecipe(recipes.enderArrow).shape("AAA","AEA","AAA").setIngredient('A', Material.ARROW).setIngredient('E', Material.EYE_OF_ENDER));
		Bukkit.getServer().addRecipe(new ShapedRecipe(recipes.fireArrow).shape("AAA","AFA","AAA").setIngredient('A', Material.ARROW).setIngredient('F', Material.FIREBALL));
		Bukkit.getServer().addRecipe(new ShapedRecipe(recipes.frostArrow).shape("AAA","ASA","AAA").setIngredient('A', Material.ARROW).setIngredient('S', Material.SNOW_BALL));
		Bukkit.getServer().addRecipe(new ShapedRecipe(recipes.lifeArrow).shape("AAA","AMA","AAA").setIngredient('A', Material.ARROW).setIngredient('M', Material.SPECKLED_MELON));
		Bukkit.getServer().addRecipe(new ShapedRecipe(recipes.lightArrow).shape("AAA","AGA","AAA").setIngredient('A', Material.ARROW).setIngredient('G', Material.GLOWSTONE_DUST));
		Bukkit.getServer().addRecipe(new ShapedRecipe(recipes.magicArrow).shape("AAA","ABA","AAA").setIngredient('A', Material.ARROW).setIngredient('B', Material.BLAZE_POWDER));
		Bukkit.getServer().addRecipe(new ShapedRecipe(recipes.magneticArrow).shape("AAA","AIA","AAA").setIngredient('A', Material.ARROW).setIngredient('I', Material.IRON_INGOT));
		Bukkit.getServer().addRecipe(new ShapedRecipe(recipes.necroticArrow).shape("AAA","AFA","AAA").setIngredient('A', Material.ARROW).setIngredient('F', Material.ROTTEN_FLESH));
		Bukkit.getServer().addRecipe(new ShapedRecipe(recipes.waterArrow).shape("AAA","AWA","AAA").setIngredient('A', Material.ARROW).setIngredient('W', Material.WATER_BUCKET));
		Bukkit.getServer().addRecipe(new ShapedRecipe(recipes.grappleArrow).shape("AAA", "ATA", "AAA").setIngredient('A', Material.ARROW).setIngredient('T', Material.TRIPWIRE_HOOK));
		
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
		
		//Check for newer version (Bukget API)
		if (ConfigOption.CHECK_FOR_UPDATES){
			this.getLogger().info("Getting version information...");
			new BukkitRunnable(){
				@Override
				public void run(){
					try{
						JSONParser parser = new JSONParser();
						URL news = new URL("https://api.bukget.org/3/plugins/bukkit/alchemical-arrows/latest");
						BufferedReader in = new BufferedReader(new InputStreamReader(news.openStream()));
						
						JSONObject json = (JSONObject) parser.parse(in.readLine());
						JSONArray array = (JSONArray) json.get("versions");
						if (!((JSONObject) array.get(0)).get("version").equals(getDescription().getVersion())){
							getLogger().info("\n" + StringUtils.repeat('*', 40) + "\n"
									+ "** There is a newer version of AlchemicalArrows available!\n"
									+ "**\n"
									+ "** Your version: " + getDescription().getVersion() + "\n"
									+ "** Newest version: " + ((JSONObject) array.get(0)).get("version") + "\n"
									+ "**\n"
									+ "** You can download it from " + ((JSONObject) array.get(0)).get("link") + "\n" 
									+ StringUtils.repeat('*', 40));
							newVersionAvailable = true;
						}
						in.close();
					}catch(IOException e){
						getLogger().info("Could not check for a new version. Perhaps the website is down?");
					}catch(ParseException e){
						getLogger().info("There was an issue parsing JSON formatted data. If issues continue, please put in a ticket on the "
								+ "AlchemicalArrows development page with the following stacktrace");
						e.printStackTrace();
					}
				}
			}.runTaskAsynchronously(this);
		}
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

	public boolean isWorldGuardSupported() {
		return worldGuardEnabled;
	}
	
	public boolean isNewVersionAvailable() {
		return newVersionAvailable;
	}
}

/* Changelog 2.2.0:
 * Rewrote a lot of individual arrow code for efficiency purposes
 * Loads of general efficiency improvements throughout the code
 * Some arrows now only affect LivingEntities whereas previous they would affect ALL entities
 * API: Added a new abstract getName() method to get the name of arrow (e.g. "Air" for AirArrow)
 * API: Added a static AlchemicalArrow#createAlchemicalArrow(Class<? extends AlchemicalArrow>, Arrow) method to create a new Alchemical Arrow of any specified type
 * API: Added a AlchemicalArrows#isNewVersionAvailable() method to check if a new version is present on BukkitDev
 * Added a ConfigOption class to manage configuration options a bit better and more efficiently throughout the plugin
 * Encapsulated and externalized plenty of variables that required it
 * The "/alchemicalarrows version" command will now display if there is a new version available or not
 * Fixed a few "off-by-one" randomization patterns
 * Further improved the way arrows are created and registered upon being launched
 * Moved a few classes into new different packages to be more organized
 * 
 * An in-depth explanation (code-wise) of the changelog can be found on AlchemicalArrows BitBucket's commit history. Yes, AlchemicalArrows's repository is now public on the main page under the AlchemicalArrows API category!
 */