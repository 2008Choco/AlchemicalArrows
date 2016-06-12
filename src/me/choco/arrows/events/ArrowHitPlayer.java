package me.choco.arrows.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag.State;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.AlchemicalArrow;

public class ArrowHitPlayer implements Listener{
	
	private AlchemicalArrows plugin;
	public ArrowHitPlayer(AlchemicalArrows plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onHitPlayer(EntityDamageByEntityEvent event){
		if (!(event.getDamager() instanceof Arrow)) return;
		if (!(event.getEntity() instanceof Player)) return;
		
		Arrow arrow = (Arrow) event.getDamager();
		Player player = (Player) event.getEntity();
		
		if (plugin.getArrowRegistry().isAlchemicalArrow(arrow)){
			if (plugin.isWorldGuardSupported()){
				if (arrow.getShooter() instanceof Player){
					Player shooter = (Player) arrow.getShooter();
					ApplicableRegionSet shooterRegions = WGBukkit.getRegionManager(shooter.getWorld()).getApplicableRegions(shooter.getLocation());
					if (shooterRegions.queryState(null, DefaultFlag.PVP) != null){
						if (shooterRegions.queryState(null, DefaultFlag.PVP).equals(State.DENY) && !shooter.hasPermission("arrows.worldguardoverride")){
							shooter.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "You cannot hit a player whilst protected by PvP");
							event.setCancelled(true);
						}
					}
				}
				
				ApplicableRegionSet damagedRegions = WGBukkit.getRegionManager(player.getWorld()).getApplicableRegions(player.getLocation());
				if (damagedRegions.queryState(null, DefaultFlag.PVP) == null){
					if (!player.isBlocking()){
						AlchemicalArrow aarrow = plugin.getArrowRegistry().getAlchemicalArrow(arrow);
						aarrow.hitEntityEventHandler(event);
						aarrow.onHitPlayer(player);
					}
				}else if (damagedRegions.queryState(null, DefaultFlag.PVP).equals(State.ALLOW)){
					if (!player.isBlocking()){
						AlchemicalArrow aarrow = plugin.getArrowRegistry().getAlchemicalArrow(arrow);
						aarrow.hitEntityEventHandler(event);
						aarrow.onHitPlayer(player);
					}
				}else if ((!damagedRegions.testState(null, DefaultFlag.PVP) || event.isCancelled())){
					if (arrow.getShooter() instanceof Player){
						((Player) arrow.getShooter()).sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "This player is protected from PvP");
					}
				}
			}else{
				if (!player.isBlocking()){
					AlchemicalArrow aarrow = plugin.getArrowRegistry().getAlchemicalArrow(arrow);
					aarrow.hitEntityEventHandler(event);
					aarrow.onHitPlayer(player);
				}
			}
		}
	}
}