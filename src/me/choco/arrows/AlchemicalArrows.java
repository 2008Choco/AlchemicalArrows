package me.choco.arrows;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.choco.arrows.commands.GiveArrowsCommand;
import me.choco.arrows.commands.MainCommand;
import me.choco.arrows.events.ArrowPickup;
import me.choco.arrows.events.ProjectileHitBlock;
import me.choco.arrows.events.ProjectileHitEntity;
import me.choco.arrows.events.ProjectileShoot;
import me.choco.arrows.events.SpecializedArrowHitEntity;
import me.choco.arrows.startup.Metrics;
import me.choco.arrows.startup.ParticleLoop;
import me.choco.arrows.startup.Recipes;

public class AlchemicalArrows extends JavaPlugin implements Listener{
	
	public static ArrayList<Arrow> specializedArrows = new ArrayList<Arrow>();
	
	FileConfiguration config;
	File cfile;
	
	public void onEnable(){
		getServer().getPluginManager().registerEvents(new ProjectileShoot(), this);
		getServer().getPluginManager().registerEvents(new ProjectileHitEntity(), this);
		getServer().getPluginManager().registerEvents(new ProjectileHitBlock(), this);
		getServer().getPluginManager().registerEvents(new ArrowPickup(), this);
		getServer().getPluginManager().registerEvents(new SpecializedArrowHitEntity(), this);
		getServer().getPluginManager().registerEvents(this, this);
		Bukkit.getPluginCommand("alchemicalarrows").setExecutor(new MainCommand());
		Bukkit.getPluginCommand("givearrow").setExecutor(new GiveArrowsCommand());
		this.config = getConfig();
		saveDefaultConfig();
		this.cfile = new File(getDataFolder(), "config.yml");
		
		this.getLogger().info("Loading arrows and crafting recipes");
		Recipes.enable();
		this.getLogger().info("Enabling particle effects for specialized arrows");
		ParticleLoop.enable();
		
		if (getConfig().getBoolean("MetricsEnabled") == true){
			getLogger().info("Enabling Plugin Metrics");
		    try{
		        Metrics metrics = new Metrics(this);
		        metrics.start();
		    }//Close an attempt to start metrics
		    catch (IOException e){
		        getLogger().warning("Could not enable Plugin Metrics. If issues continue, please put in a ticket on the "
		        	+ "Alchemical Arrows development page");
		    }//Close if an IOException occurs
		}//Close if pluginmetrics is enabled in config
	}//Close onEnable method
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		if (player.getUniqueId().toString().equals("73c62196-2af7-463d-8be1-a7a2270f4696")/*2008Choco*/){
			player.sendMessage(ChatColor.GOLD + "--------------------------------------------");
			player.sendMessage(ChatColor.GRAY + "     You are the developer of " + ChatColor.ITALIC + "AlchemicalArrows");
			player.sendMessage("");
			player.sendMessage(ChatColor.GRAY + "                What's up, " + ChatColor.GOLD + "" + ChatColor.ITALIC + "" + ChatColor.BOLD + player.getName());
			player.sendMessage(ChatColor.DARK_AQUA + "                     Version: " + ChatColor.GRAY + this.getDescription().getVersion());
			player.sendMessage(ChatColor.GOLD + "--------------------------------------------");
		}//Close if my UUID joins
	}//Close onPlayerJoin event
}//Close class

/* CHANGELOG FOR 1.0.0:
 * Officially released the first full version, out of Beta, version 1.0.0! Many official changes will be hapenning.
 * Removed the OP join message for the BETA version
 * Allowed the /givearrow command to be run from the console (Must use all parameters to give to another player)

(Sorry if you were expecting a fancier version 1.0.0 :P There's not a whole lot of bugs to fix anymore now that I am out of Beta)
 */

/* ARROW TYPES:
 * 
 *    ELEMENTAL ARROWS:
 *    
 *        EARTH:
 *            - If it hits the player: Teleport the player in the ground and give them slowness for 5 seconds
 *            - If it hits the ground: create a seismic ripple damaging all players in range
 *            Crafting Recipe: Arrow + Dirt
 *        FIRE:
 *            - If it hits the player: Display fire rings around the player which will randomly harm them
 *            - If it hits the ground: Hitting a flammable block, fire spreads quickly around the arrow
 *            Crafting Recipe: Arrow + Fire Charge
 *        WATER:
 *            - If it hits the player: Shoot 3x as fast underwater
 *            - If it hits the ground: Mini tsunami in the direction it landed?
 *            Crafting Recipe: Arrow + Water Bucket
 *        AIR:
 *            - If it hits the player: Lift the player in the air a random amount of blocks
 *            - If it hits the ground: Create a tiny tornado?
 *            Crafting Recipe: Arrow + Feather
 *        SPECTRAL (TODO: CHANGE NAME TO "ENDER ARROW"):
 *            - If it hits the player: Teleport the shooter to the player it hits
 *            - If it hits the ground: Teleport the player to the location the arrow landed
 *            Crafting Recipe: Arrow + Eye of Ender
 *        DARKNESS:
 *            - If it hits the player: Give the player a blindness effect
 *            - If it hits the ground: Destroy nearby torches?
 *            Crafting Recipe: Arrow + Coal
 *        LIGHT:
 *            - If it hits the player: Make the player look upwards and lightning strike him
 *            - If it hits the ground: Place a torch on the area it lands
 *            Crafting Recipe: Arrow + Glowstone Dust
 *        LIFE:
 *            - If it hits the player: Heal the player it hits
 *            - If it hits the ground: Place flowers around the arrow
 *            Crafting Recipe: Arrow + Speckled Melon
 *        DEATH:
 *            - If it hits the player: Instantly kill the player it hits (EXPENSIVE! COSTS WITHER SKULLS!)
 *            - If it hits the ground: Kill all floral life and mobs (excluding players) around it
 *            Crafting Recipe: Arrow + Wither Skull
 *        MAGIC:
 *            - If it hits the player: Shoot the player backwards very far
 *            - If it hits the ground: Summon a random splash potion
 *            Crafting Recipe: Arrow + Blaze Powder
 *        FROST:
 *            - If it hits the player: Prevent the player from moving for 5 seconds
 *            - If it hits the ground: Let the ground freeze over (snow layers)
 *            - SPECIAL (While in air): Freeze all water it traverses into ice
 *            Crafting Recipe: Arrow + Snowball
 *        CONFUSION:
 *            - If it hits the player: Confuse them and turn them around
 *            - If it hits the ground: N/A (TODO)
 *            Crafting Recipe: Arrow + Poisonous Potato
 *        NECROTIC:
 *            - If it hits the player: All hostile mobs in a 50 block radius are hostile towards that player
 *            - If it hits the ground: Summon a Zombie and kill the arrow?
 *            Crafting Recipe: Arrow + Rotten Flesh?
 *        MAGNETIC:
 *            - If it hits a player: Launch them towards the player that shot the arrow
 *            - If it hits the ground: Attract all items in a radius towards the arrow
 *            Crafting Recipe: Arrow + Iron Ingot
 */

/* TODO LIST
 * MainCommand:
 *     Sub-command for information about the individual arrows, "/aa info <arrow>"
 * General:
 *     Add optional sound effects to the arrows
 */