package me.choco.arrows.utils.arrows;

import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;

import me.choco.arrows.api.AlchemicalArrow;

public class LightArrow extends AlchemicalArrow{
	public LightArrow(Arrow arrow) {
		super(arrow);
	}
	
	@Override
	public void displayParticle(Player player) {
		player.spawnParticle(Particle.FIREWORKS_SPARK, getArrow().getLocation(), 1, 0.1, 0.1, 0.1, 0.01);
	}
}