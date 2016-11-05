package me.choco.arrows.utils.arrows;

import java.util.Random;

import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.AlchemicalArrow;
import me.choco.arrows.utils.ConfigOption;

public class FireArrow extends AlchemicalArrow{
	
	private static final Random random = new Random();
	
	public FireArrow(Arrow arrow) {
		super(arrow);
	}
	
	@Override
	public String getName() {
		return "Fire";
	}
	
	@Override
	public void displayParticle(Player player) {
		player.spawnParticle(Particle.SMOKE_NORMAL, arrow.getLocation(), 1, 0.1, 0.1, 0.1, 0.001);
		player.spawnParticle(Particle.FLAME, arrow.getLocation(), 1, 0.1, 0.1, 0.1, 0.001);
	}
	
	@Override
	public void onHitPlayer(Player player) {
		player.setFireTicks(40 + random.nextInt(61));
	}
	
	@Override
	public void onHitEntity(Entity entity) {
		entity.setFireTicks(40 + random.nextInt(61));
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onShootFromPlayer(Player player) {
		if (!player.hasPermission("arrows.shoot.fire")){
			AlchemicalArrows.getPlugin().getArrowRegistry().unregisterAlchemicalArrow(this);
		}
	}
	
	@Override
	public boolean allowInfinity() {
		return ConfigOption.FIRE_ALLOW_INFINITY;
	}
	
	@Override
	public boolean skeletonsCanShoot() {
		return ConfigOption.FIRE_SKELETONS_CAN_SHOOT;
	}
}