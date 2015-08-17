package me.choco.arrows.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.ArrowType;
import me.choco.arrows.api.events.SpecializedArrowShootEvent;
import me.choco.arrows.api.methods.Messages;

public class ProjectileShoot implements Listener{
	
	Plugin AA = Bukkit.getServer().getPluginManager().getPlugin("AlchemicalArrows");
	
	@EventHandler
	public void onProjectileLaunch(ProjectileLaunchEvent event){
		if (event.getEntity().getType() == EntityType.ARROW && event.getEntity().getShooter() instanceof Player){
			Arrow arrow = (Arrow) event.getEntity();
			Player player = (Player) arrow.getShooter();
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
					Messages.notification(player, "You don't have permission to fire an air arrow");
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
					Messages.notification(player, "You don't have permission to fire an earth arrow");
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
					Messages.notification(player, "You don't have permission to fire a magic arrow");
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
					Messages.notification(player, "You don't have permission to fire an ender arrow");
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
					Messages.notification(player, "You don't have permission to fire a life arrow");
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
					Messages.notification(player, "You don't have permission to fire a death arrow");
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
					Messages.notification(player, "You don't have permission to fire a light arrow");
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
					Messages.notification(player, "You don't have permission to fire a darkness arrow");
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
					Messages.notification(player, "You don't have permission to fire a fire arrow");
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
					Messages.notification(player, "You don't have permission to fire a frost arrow");
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
					Messages.notification(player, "You don't have permission to fire a water arrow");
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
					Messages.notification(player, "You don't have permission to fire a necrotic arrow");
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
					Messages.notification(player, "You don't have permission to fire a water arrow");
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
					Messages.notification(player, "You don't have permission to fire a magnetic arrow");
				}//Close if permissions == false
			}//Close if arrowItem's name == "Magnetic Arrow"
			
			//INFINITY ARROW HANDLING:
			
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
		}//Close if projectile is arrow
	}//Close onProjectileShoot
}//Close class