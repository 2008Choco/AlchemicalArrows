package me.choco.arrows.events;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.events.SpecializedArrowHitEntityEvent;
import me.choco.arrows.utils.Messages;

public class SpecializedArrowHitEntity implements Listener{
	AlchemicalArrows plugin;
	public SpecializedArrowHitEntity(AlchemicalArrows plugin){
		this.plugin = plugin;
	}
	
	Messages message = new Messages(plugin);
	
	int temp = 0;
	
	@EventHandler
	public void specialArrowHitEntity(SpecializedArrowHitEntityEvent event){
		FileConfiguration config = plugin.messages.getConfig();
		if (event.getEntity() instanceof Player && event.getShooter() instanceof Player){
			Player shooter = (Player) event.getShooter();
			Player damaged = (Player) event.getEntity();
			if (Bukkit.getPluginManager().getPlugin("WorldGuard") != null){
				//IF SHOOTER IS IN PVP DENIED AREA
				ApplicableRegionSet shooterSet = WGBukkit.getPlugin().getRegionManager(shooter.getWorld()).getApplicableRegions(shooter.getLocation());
				if (shooterSet.queryState(null, DefaultFlag.PVP) == StateFlag.State.DENY && temp == 0){
					event.setCancelled(true);
					message.sendMessage(shooter, config.getString("Events.CannotShootInsideRegion"));
					temp = 1;
				}//Close if PvP is DENIED
				
				//IF DAMAGED IS IN A PVP DENIED AREA
				if (shooter != damaged){
					ApplicableRegionSet damagedSet = WGBukkit.getPlugin().getRegionManager(damaged.getWorld()).getApplicableRegions(damaged.getLocation());
					if (damagedSet.queryState(null, DefaultFlag.PVP) == StateFlag.State.DENY && temp == 0){
						event.setCancelled(true);
						message.sendMessage(shooter, config.getString("Events.CannotShootPlayerInsideRegion"));
						temp = 1;
					}//Close if PvP is DENIED
				}//Close if shooter isn't themselves
				temp = 0;
			}//Close if WorldGuard is installed
		}//Close if the entity shot was a player
	}//Close SpecializedArrowHitEntity event
}//Close class