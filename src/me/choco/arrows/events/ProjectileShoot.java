package me.choco.arrows.events;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.ArrowType;
import me.choco.arrows.api.events.SpecializedArrowShootEvent;
import me.choco.arrows.utils.Messages;

public class ProjectileShoot implements Listener{
	AlchemicalArrows plugin;
	public ProjectileShoot(AlchemicalArrows plugin){
		this.plugin = plugin;
	}
	
	Messages message = new Messages(plugin);
	
	@EventHandler
	public void onProjectileLaunch(ProjectileLaunchEvent event){
		FileConfiguration config = plugin.messages.getConfig();
		if (event.getEntity().getType() == EntityType.ARROW){
			Arrow arrow = (Arrow) event.getEntity();
			if (arrow.getShooter() instanceof Player){
				Player player = (Player) arrow.getShooter();
				if (player.getInventory().contains(Material.ARROW)){
					int arrowSlot = player.getInventory().first(Material.ARROW);
					ItemStack arrowItem = player.getInventory().getItem(arrowSlot);
					SpecializedArrowShootEvent specializedArrowShootEvent = new SpecializedArrowShootEvent(player, arrow);
					if (arrowItem.hasItemMeta() && arrowItem.getItemMeta().getDisplayName().equals(ChatColor.ITALIC + "Air Arrow")){
						if (player.hasPermission("arrows.shoot.air")){
							ArrowType.setArrowType(arrow, ArrowType.AIR);
							Bukkit.getServer().getPluginManager().callEvent(specializedArrowShootEvent);
							if (specializedArrowShootEvent.isCancelled()){
								event.setCancelled(true);
							}//Close if it's not cancelled
						}//Close if permissions == true
						else{
							event.setCancelled(true);
							message.sendMessage(player, config.getString("Events.NoPermissionToShootArrow").replaceAll("%arrow%", "an air arrow"));
						}//Close if permissions == false
					}//Close if arrowItem's name == "Air Arrow"
					
					if (arrowItem.hasItemMeta() && arrowItem.getItemMeta().getDisplayName().equals(ChatColor.GRAY + "Earth Arrow")){
						if (player.hasPermission("arrows.shoot.earth")){
							ArrowType.setArrowType(arrow, ArrowType.EARTH);
							Bukkit.getServer().getPluginManager().callEvent(specializedArrowShootEvent);
							if (specializedArrowShootEvent.isCancelled()){
								event.setCancelled(true);
							}//Close if it's not cancelled
						}//Close if permissions == true
						else{
							event.setCancelled(true);

							message.sendMessage(player, config.getString("Events.NoPermissionToShootArrow").replaceAll("%arrow%", "an earth arrow"));
						}//Close if permissions == false
					}//Close if arrowItem's name == "Air Arrow"
					
					if (arrowItem.hasItemMeta() && arrowItem.getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "Magic Arrow")){
						if (player.hasPermission("arrows.shoot.magic")){
							ArrowType.setArrowType(arrow, ArrowType.MAGIC);
							Bukkit.getServer().getPluginManager().callEvent(specializedArrowShootEvent);
							if (specializedArrowShootEvent.isCancelled()){
								event.setCancelled(true);
							}//Close if it's not cancelled
						}//Close if permissions == true
						else{
							event.setCancelled(true);
							message.sendMessage(player, config.getString("Events.NoPermissionToShootArrow").replaceAll("%arrow%", "a magic arrow"));
						}//Close if permissions == false
					}//Close if arrowItem's name == "Magic Arrow"
					
					if (arrowItem.hasItemMeta() && arrowItem.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "Ender Arrow")){
						if (player.hasPermission("arrows.shoot.ender")){
							ArrowType.setArrowType(arrow, ArrowType.ENDER);
							Bukkit.getServer().getPluginManager().callEvent(specializedArrowShootEvent);
							if (specializedArrowShootEvent.isCancelled()){
								event.setCancelled(true);
							}//Close if it's not cancelled
						}//Close if permissions == true
						else{
							event.setCancelled(true);
							message.sendMessage(player, config.getString("Events.NoPermissionToShootArrow").replaceAll("%arrow%", "an ender arrow"));
						}//Close if permissions == false
					}//Close if arrowItems' name == "Spectral Arrow"
					
					if (arrowItem.hasItemMeta() && arrowItem.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Life Arrow")){
						if (player.hasPermission("arrows.shoot.life")){
							ArrowType.setArrowType(arrow, ArrowType.LIFE);
							Bukkit.getServer().getPluginManager().callEvent(specializedArrowShootEvent);
							if (specializedArrowShootEvent.isCancelled()){
								event.setCancelled(true);
							}//Close if it's not cancelled
						}//Close if permissions == true
						else{
							event.setCancelled(true);
							message.sendMessage(player, config.getString("Events.NoPermissionToShootArrow").replaceAll("%arrow%", "a life arrow"));
						}//Close if permissions == false
					}//Close if arrowItem's name == "Life Arrow"
					
					if (arrowItem.hasItemMeta() && arrowItem.getItemMeta().getDisplayName().equals(ChatColor.BLACK + "Death Arrow")){
						if (player.hasPermission("arrows.shoot.death")){
							ArrowType.setArrowType(arrow, ArrowType.DEATH);
							Bukkit.getServer().getPluginManager().callEvent(specializedArrowShootEvent);
							if (specializedArrowShootEvent.isCancelled()){
								event.setCancelled(true);
							}//Close if it's not cancelled
						}//Close if permissions == true
						else{
							event.setCancelled(true);
							message.sendMessage(player, config.getString("Events.NoPermissionToShootArrow").replaceAll("%arrow%", "a death arrow"));
						}//Close if permissions == false
					}//Close if arrowItem's name == "Death Arrow"
					
					if (arrowItem.hasItemMeta() && arrowItem.getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Light Arrow")){
						if (player.hasPermission("arrows.shoot.light")){
							ArrowType.setArrowType(arrow, ArrowType.LIGHT);
							Bukkit.getServer().getPluginManager().callEvent(specializedArrowShootEvent);
							if (specializedArrowShootEvent.isCancelled()){
								event.setCancelled(true);
							}//Close if it's not cancelled
						}//Close if permissions == true
						else{
							event.setCancelled(true);
							message.sendMessage(player, config.getString("Events.NoPermissionToShootArrow").replaceAll("%arrow%", "a light arrow"));
						}//Close if permissions == false
					}//Close if arrowItem's name == "Light Arrow"
					
					if (arrowItem.hasItemMeta() && arrowItem.getItemMeta().getDisplayName().equals(ChatColor.DARK_GRAY + "Darkness Arrow")){
						if (player.hasPermission("arrows.shoot.darkness")){
							ArrowType.setArrowType(arrow, ArrowType.DARKNESS);
							Bukkit.getServer().getPluginManager().callEvent(specializedArrowShootEvent);
							if (specializedArrowShootEvent.isCancelled()){
								event.setCancelled(true);
							}//Close if it's not cancelled
						}//Close if permissions == true
						else{
							event.setCancelled(true);
							message.sendMessage(player, config.getString("Events.NoPermissionToShootArrow").replaceAll("%arrow%", "a darkness arrow"));
						}//Close if permissions == false
					}//Close if arrowItem's name == "Darkness Arrow"
					
					if (arrowItem.hasItemMeta() && arrowItem.getItemMeta().getDisplayName().equals(ChatColor.RED + "Fire Arrow")){
						if (player.hasPermission("arrows.shoot.fire")){
							ArrowType.setArrowType(arrow, ArrowType.FIRE);
							if (player.getItemInHand().containsEnchantment(Enchantment.ARROW_FIRE)){
								arrow.setFireTicks(0);
							}//Close if a fire bow is shot
							Bukkit.getServer().getPluginManager().callEvent(specializedArrowShootEvent);
							if (specializedArrowShootEvent.isCancelled()){
								event.setCancelled(true);
							}//Close if it's not cancelled
						}//Close if permissions == true
						else{
							event.setCancelled(true);
							message.sendMessage(player, config.getString("Events.NoPermissionToShootArrow").replaceAll("%arrow%", "a fire arrow"));
						}//Close if permissions == false
					}//Close if arrowItem's name == "Fire Arrow"
					
					if (arrowItem.hasItemMeta() && arrowItem.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Frost Arrow")){
						if (player.hasPermission("arrows.shoot.frost")){
							ArrowType.setArrowType(arrow, ArrowType.FROST);
							Bukkit.getServer().getPluginManager().callEvent(specializedArrowShootEvent);
							if (specializedArrowShootEvent.isCancelled()){
								event.setCancelled(true);
							}//Close if it's not cancelled
						}//Close if permissions == true
						else{
							event.setCancelled(true);
							message.sendMessage(player, config.getString("Events.NoPermissionToShootArrow").replaceAll("%arrow%", "a frost arrow"));
						}//Close if permissions == false
					}//Close if arrowItem's name == "Frost Arrow"
					
					if (arrowItem.hasItemMeta() && arrowItem.getItemMeta().getDisplayName().equals(ChatColor.BLUE + "Water Arrow")){
						if (player.hasPermission("arrows.shoot.water")){
							ArrowType.setArrowType(arrow, ArrowType.WATER);
							Bukkit.getServer().getPluginManager().callEvent(specializedArrowShootEvent);
							if (specializedArrowShootEvent.isCancelled()){
								event.setCancelled(true);
							}//Close if it's not cancelled
						}//Close if permissions == true
						else{
							event.setCancelled(true);
							message.sendMessage(player, config.getString("Events.NoPermissionToShootArrow").replaceAll("%arrow%", "a water arrow"));
						}//Close if permissions == false
					}//Close if arrowItem's name == "Water Arrow"
					
					if (arrowItem.hasItemMeta() && arrowItem.getItemMeta().getDisplayName().equals(ChatColor.DARK_GREEN + "Necrotic Arrow")){
						if (player.hasPermission("arrows.shoot.necrotic")){
							ArrowType.setArrowType(arrow, ArrowType.NECROTIC);
							Bukkit.getServer().getPluginManager().callEvent(specializedArrowShootEvent);
							if (specializedArrowShootEvent.isCancelled()){
								event.setCancelled(true);
							}//Close if it's not cancelled
						}//Close if permissions == true
						else{
							event.setCancelled(true);
							message.sendMessage(player, config.getString("Events.NoPermissionToShootArrow").replaceAll("%arrow%", "a necrotic arrow"));
						}//Close if permissions == false
					}//Close if arrowItem's name == "Necrotic Arrow"
					
					if (arrowItem.hasItemMeta() && arrowItem.getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "Confusion Arrow")){
						if (player.hasPermission("arrows.shoot.confusion")){
							ArrowType.setArrowType(arrow, ArrowType.CONFUSION);
							Bukkit.getServer().getPluginManager().callEvent(specializedArrowShootEvent);
							if (specializedArrowShootEvent.isCancelled()){
								event.setCancelled(true);
							}//Close if it's not cancelled
						}//Close if permissions == true
						else{
							event.setCancelled(true);
							message.sendMessage(player, config.getString("Events.NoPermissionToShootArrow").replaceAll("%arrow%", "a water arrow"));
						}//Close if permissions == false
					}//Close if arrowItem's name == "Confusion Arrow"
					
					if (arrowItem.hasItemMeta() && arrowItem.getItemMeta().getDisplayName().equals(ChatColor.GRAY + "Magnetic Arrow")){
						if (player.hasPermission("arrows.shoot.magnetic")){
							ArrowType.setArrowType(arrow, ArrowType.MAGNETIC);
							Bukkit.getServer().getPluginManager().callEvent(specializedArrowShootEvent);
							if (specializedArrowShootEvent.isCancelled()){
								event.setCancelled(true);
							}//Close if it's not cancelled
						}//Close if permissions == true
						else{
							event.setCancelled(true);
							message.sendMessage(player, config.getString("Events.NoPermissionToShootArrow").replaceAll("%arrow%", "a magnetic arrow"));
						}//Close if permissions == false
					}//Close if arrowItem's name == "Magnetic Arrow"
					
					//INFINITY ARROW HANDLING:
					
					if (!plugin.getConfig().getBoolean("AllowInfinity")){
						if (player.getItemInHand().containsEnchantment(Enchantment.ARROW_INFINITE)
								&& AlchemicalArrows.specializedArrows.contains(arrow)
								&& !(player.getGameMode() == GameMode.CREATIVE)){
							if (arrowItem.getAmount() > 1){
								arrowItem.setAmount(arrowItem.getAmount() - 1);
							}//Close if there is more than one item
							else{
								player.getInventory().clear(arrowSlot);
							}//Close if there is only one left
						}//Close if the bow is an infinite bow
					}
				}
			}//Close if the shooter is a player
			
			if (arrow.getShooter() instanceof Skeleton){
				if (plugin.getConfig().getBoolean("SkeletonsShootSpecializedArrows")){
					Random random = new Random();
					int randomInt = random.nextInt(100);
					if (randomInt < ArrowType.values().length){
						ArrowType.setArrowType(arrow, ArrowType.values()[randomInt]);
					}//Close if the random int is less than the amount of arrows in the arrow types class
				}//Close if the config is set to true
			}//Close if the shooter is a skeleton
		}//Close if projectile is arrow
	}//Close onProjectileShoot
}//Close class