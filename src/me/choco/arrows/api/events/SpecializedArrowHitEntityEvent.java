package me.choco.arrows.api.events;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.projectiles.ProjectileSource;

public class SpecializedArrowHitEntityEvent extends Event implements Cancellable{
	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled;
	Entity damagedEntity;
	ProjectileSource shooter;
	Arrow arrow;
	
	public SpecializedArrowHitEntityEvent(Entity damagedEntity, ProjectileSource shooter, Arrow arrow) {
		this.damagedEntity = damagedEntity;
		this.shooter = shooter;
		this.arrow = arrow;
	}
	
	public HandlerList getHandlers(){
	    return handlers;
	}//Close handler
	 
	public static HandlerList getHandlerList(){
	    return handlers;
	}//Close handler

	@Override
	public boolean isCancelled(){
		return cancelled;
	}//Close isCancelled method

	@Override
	public void setCancelled(boolean cancel){
		cancelled = cancel;
	}//Close setCancelled method
	
	public Entity getEntity(){
		return damagedEntity;
	}//Close getEntity
	
	public ProjectileSource getShooter(){
		return shooter;
	}//Close getShooter
	
	public Arrow getArrow(){
		return arrow;
	}//Close getProjectile
}//Close SpecializedArrowHitEntityEvent event class