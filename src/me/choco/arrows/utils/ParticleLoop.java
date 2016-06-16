package me.choco.arrows.utils;

import java.util.Iterator;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.AlchemicalArrow;

public class ParticleLoop extends BukkitRunnable{
	
	private ArrowRegistry registry;
	public ParticleLoop(AlchemicalArrows plugin){
		this.registry = plugin.getArrowRegistry();
	}
	
	@Override
	public void run(){
		Iterator<UUID> it = registry.getRegisteredArrows().keySet().iterator();
		while (it.hasNext()){
			AlchemicalArrow arrow = registry.getAlchemicalArrow(it.next());
			Arrow rawArrow = arrow.getArrow();
			if (rawArrow.isDead() || !rawArrow.isValid()){
				it.remove();
				continue;
			}
			
			for (Player player : Bukkit.getOnlinePlayers()){
				if (!player.getWorld().equals(rawArrow.getWorld())) continue;
				if (player.getLocation().distanceSquared(rawArrow.getLocation()) >= 400) continue;
				arrow.displayParticle(player);
			}
		}
	}
}