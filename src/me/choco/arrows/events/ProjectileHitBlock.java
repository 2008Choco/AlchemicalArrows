package me.choco.arrows.events;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ProjectileHitBlock implements Listener{
	@EventHandler
	public void onProjectileHitBlock(ProjectileHitEvent event){
		if (event.getEntity().getType() == EntityType.ARROW){
			Arrow arrow = (Arrow) event.getEntity();
			if (arrow.isOnGround()){
				
			}//Close 
		}//Close if projectile is an arrow
	}//Close onProjectileHitBlock
}//Close class