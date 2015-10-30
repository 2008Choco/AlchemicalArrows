package me.choco.arrows.events;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;

import me.choco.arrows.api.events.SpecializedArrowHitEntityEvent;
import me.choco.arrows.utils.ArrowHandling;

public class ProjectileHitEntity implements Listener{
	
	Plugin AA = Bukkit.getPluginManager().getPlugin("AlchemicalArrows");
	
	@EventHandler(priority = EventPriority.LOW)
	public void onProjectileHitEntity(EntityDamageByEntityEvent event){
		if (event.getDamager().getType() == EntityType.ARROW){
			if (event.getDamager().getCustomName() != null){
				if (event.getEntity().getWorld().getPVP() == true){
					final Entity damaged = event.getEntity();
					if (damaged instanceof LivingEntity){
						Arrow arrow = (Arrow) event.getDamager();
						
						SpecializedArrowHitEntityEvent specializedArrowHitEntityEvent = new SpecializedArrowHitEntityEvent(damaged, arrow.getShooter(), arrow);
						Bukkit.getPluginManager().callEvent(specializedArrowHitEntityEvent);
						
						if (!AA.getConfig().getBoolean("ShooterCanAffectSelf") && arrow.getShooter() == arrow.getShooter())
							specializedArrowHitEntityEvent.setCancelled(true);
						
						if (!specializedArrowHitEntityEvent.isCancelled() || !event.isCancelled()){
							ArrowHandling.arrowEffects(event, damaged, arrow);
							if (arrow.getShooter() instanceof Player)
								((Player)arrow.getShooter()).playSound(((Player)arrow.getShooter()).getLocation(), Sound.NOTE_PLING, 1, 1);
						}//Close if specializedArrowHitEntityEvent isn't cancelled
						else{
							event.setCancelled(true);
						}//Close if the event is cancelled
					}//Close if damaged is a LIVING entity
				}//Close if PvP is enabled
			}//Close if arrow has a name
		}//Close if projectile is an arrow
	}//Close onProjectileHitBlock
}//Close class