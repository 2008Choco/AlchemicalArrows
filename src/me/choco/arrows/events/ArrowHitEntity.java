package me.choco.arrows.events;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.choco.arrows.AlchemicalArrows;

public class ArrowHitEntity implements Listener{
	
	AlchemicalArrows plugin;
	public ArrowHitEntity(AlchemicalArrows plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onHitPlayer(EntityDamageByEntityEvent event){
		if (!(event.getDamager() instanceof Arrow)) return;
		if (event.getEntity() instanceof Player) return;
		
		Arrow arrow = (Arrow) event.getDamager();
		Entity entity = event.getEntity();
		
		if (plugin.getArrowRegistry().isAlchemicalArrow(arrow)){
			plugin.getArrowRegistry().getAlchemicalArrow(arrow).onHitEntity(entity);
		}
	}
}