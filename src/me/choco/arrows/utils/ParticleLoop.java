package me.choco.arrows.utils;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.scheduler.BukkitRunnable;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.AlchemicalArrow;
import me.choco.arrows.registry.ArrowRegistry;

public class ParticleLoop extends BukkitRunnable{
	
	private final ArrowRegistry arrowRegistry;
	public ParticleLoop(AlchemicalArrows plugin){
		this.arrowRegistry = plugin.getArrowRegistry();
	}
	
	@Override
	public void run(){
		for (UUID arrowUUID : this.arrowRegistry.getRegisteredArrows().keySet()) {
			AlchemicalArrow arrow = this.arrowRegistry.getAlchemicalArrow(arrowUUID);
			Arrow rawArrow = arrow.getArrow();
			if (rawArrow.isDead() || !rawArrow.isValid()){
				this.arrowRegistry.unregisterAlchemicalArrow(arrow);
				continue;
			}
			
			Bukkit.getOnlinePlayers().stream()
				.filter(p -> p.getWorld() == rawArrow.getWorld())
				.filter(p -> p.getLocation().distanceSquared(rawArrow.getLocation()) <= 400)
				.forEach(p -> arrow.displayParticle(p));
		}
		
		this.arrowRegistry.purgeArrows();
	}
}