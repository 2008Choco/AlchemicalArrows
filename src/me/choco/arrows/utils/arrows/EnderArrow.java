package me.choco.arrows.utils.arrows;

import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;

import me.choco.arrows.api.AlchemicalArrow;

public class EnderArrow extends AlchemicalArrow{
	public EnderArrow(Arrow arrow) {
		super(arrow);
	}
	
	@Override
	public void displayParticle(Player player) {
		player.spawnParticle(Particle.PORTAL, getArrow().getLocation(), 3, 0.1, 0.1, 0.1);
	}
}