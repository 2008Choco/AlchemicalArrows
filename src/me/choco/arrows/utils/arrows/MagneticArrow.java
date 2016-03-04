package me.choco.arrows.utils.arrows;

import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import me.choco.arrows.api.AlchemicalArrow;

public class MagneticArrow extends AlchemicalArrow{
	public MagneticArrow(Arrow arrow) {
		super(arrow);
	}
	
	@Override
	public void displayParticle(Player player) {
		//TODO
	}
	
	@Override
	public void onHitPlayer(Player player) {
		player.setVelocity(new Vector(-(getArrow().getVelocity().getX() * 1.5), -(getArrow().getVelocity().getY() * 1.1), -(getArrow().getVelocity().getZ()) * 1.5));
		player.playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1, 2);
	}
	
	@Override
	public void onHitEntity(Entity entity) {
		entity.setVelocity(new Vector(-(getArrow().getVelocity().getX() * 1.5), -(getArrow().getVelocity().getY() * 1.1), -(getArrow().getVelocity().getZ()) * 1.5));
		entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1, 2);
	}
}