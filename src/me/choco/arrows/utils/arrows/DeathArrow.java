package me.choco.arrows.utils.arrows;

import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;

import me.choco.arrows.api.AlchemicalArrow;

public class DeathArrow extends AlchemicalArrow{
	public DeathArrow(Arrow arrow) {
		super(arrow);
	}
	
	@Override
	public void displayParticle(Player player) {
		player.spawnParticle(Particle.SMOKE_LARGE, getArrow().getLocation(), 2, 0.1, 0.1, 0.1, 0.01);
	}
}