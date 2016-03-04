package me.choco.arrows.utils.arrows;

import java.util.Random;

import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import me.choco.arrows.api.AlchemicalArrow;

public class FireArrow extends AlchemicalArrow{
	
	Random random = new Random();
	
	public FireArrow(Arrow arrow) {
		super(arrow);
	}
	
	@Override
	public void displayParticle(Player player) {
		player.spawnParticle(Particle.SMOKE_NORMAL, getArrow().getLocation(), 1, 0.1, 0.1, 0.1, 0.001);
		player.spawnParticle(Particle.FLAME, getArrow().getLocation(), 1, 0.1, 0.1, 0.1, 0.001);
	}
	
	@Override
	public void onHitPlayer(Player player) {
		player.setFireTicks(40 + random.nextInt(61));
	}
	
	@Override
	public void onHitEntity(Entity entity) {
		entity.setFireTicks(40 + random.nextInt(61));
	}
}