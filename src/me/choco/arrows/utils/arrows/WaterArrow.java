package me.choco.arrows.utils.arrows;

import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import me.choco.arrows.api.AlchemicalArrow;

public class WaterArrow extends AlchemicalArrow{
	public WaterArrow(Arrow arrow) {
		super(arrow);
	}
	
	@Override
	public void displayParticle(Player player) {
		player.spawnParticle(Particle.WATER_WAKE, getArrow().getLocation(), 3, 0.1, 0.1, 0.1, 0.01);
		if (getArrow().getLocation().getBlock().getType().name().contains("WATER")){
			Vector underwaterVelocity = new Vector(getArrow().getVelocity().getX() * 3, getArrow().getVelocity().getY(), getArrow().getVelocity().getZ() * 3);
			getArrow().setVelocity(underwaterVelocity);
		}
	}
}