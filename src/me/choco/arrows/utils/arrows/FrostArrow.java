package me.choco.arrows.utils.arrows;

import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;

import me.choco.arrows.api.AlchemicalArrow;

public class FrostArrow extends AlchemicalArrow{
	public FrostArrow(Arrow arrow) {
		super(arrow);
	}
	
	@Override
	public void displayParticle(Player player) {
		player.spawnParticle(Particle.SNOW_SHOVEL, getArrow().getLocation(), 3, 0.1, 0.1, 0.1, 0.01);
	}
}