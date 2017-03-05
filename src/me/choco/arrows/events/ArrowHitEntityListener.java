package me.choco.arrows.events;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.AlchemicalArrow;
import me.choco.arrows.registry.ArrowRegistry;

public class ArrowHitEntityListener implements Listener {
	
	private final ArrowRegistry arrowRegistry;
	
	public ArrowHitEntityListener(AlchemicalArrows plugin){
		this.arrowRegistry = plugin.getArrowRegistry();
	}
	
	@EventHandler
	public void onHitPlayer(EntityDamageByEntityEvent event){
		if (!(event.getDamager() instanceof Arrow)) return;
		if (event.getEntity() instanceof Player) return;
		
		Arrow arrow = (Arrow) event.getDamager();
		Entity entity = event.getEntity();
		
		if (!this.arrowRegistry.isAlchemicalArrow(arrow)) return;
		
		AlchemicalArrow aarrow = this.arrowRegistry.getAlchemicalArrow(arrow);
		aarrow.hitEntityEventHandler(event);
		aarrow.onHitEntity(entity);
	}
}