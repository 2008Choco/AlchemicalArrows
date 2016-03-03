package me.choco.arrows.utils;

import java.util.Iterator;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.AlchemicalArrow;

public class ParticleLoop extends BukkitRunnable{
	
	AlchemicalArrows plugin;
	ArrowRegistry registry;
	public ParticleLoop(AlchemicalArrows plugin){
		this.plugin = plugin;
		this.registry = plugin.getArrowRegistry();
	}
	
	@Override
	public void run(){
		Iterator<UUID> it = registry.getRegisteredArrows().keySet().iterator();
		while (it.hasNext()){
			AlchemicalArrow arrow = registry.getAlchemicalArrow(it.next());
			if (arrow.getArrow().isDead()){
				it.remove();
				continue;
			}
			
			for (Player player : Bukkit.getOnlinePlayers()){
				if (!player.getWorld().getName().equals(arrow.getArrow().getWorld().getName())) continue;
				if (player.getLocation().distance(arrow.getArrow().getLocation()) >= Math.sqrt(400)) continue;
				arrow.displayParticle(player);
			}
		}
	}
}