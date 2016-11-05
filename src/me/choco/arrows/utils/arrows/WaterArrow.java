package me.choco.arrows.utils.arrows;

import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.util.Vector;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.AlchemicalArrow;
import me.choco.arrows.utils.ConfigOption;

public class WaterArrow extends AlchemicalArrow{
	
	private Vector initialVector;
	
	public WaterArrow(Arrow arrow) {
		super(arrow);
	}
	
	@Override
	public String getName() {
		return "Water";
	}
	
	@Override
	public void displayParticle(Player player) {
		player.spawnParticle(Particle.WATER_WAKE, arrow.getLocation(), 3, 0.1, 0.1, 0.1, 0.01);
		if (arrow.getLocation().getBlock().getType().name().contains("WATER")){
			arrow.setVelocity(initialVector.normalize().multiply(0.95));
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onShootFromPlayer(Player player) {
		if (!player.hasPermission("arrows.shoot.water")){
			AlchemicalArrows.getPlugin().getArrowRegistry().unregisterAlchemicalArrow(this);
			return;
		}
		this.initialVector = arrow.getVelocity();
	}
	
	@Override
	public void onShootFromSkeleton(Skeleton skeleton) {
		this.initialVector = arrow.getVelocity();
	}
	
	@Override
	public boolean allowInfinity() {
		return ConfigOption.WATER_ALLOW_INFINITY;
	}
	
	@Override
	public boolean skeletonsCanShoot() {
		return ConfigOption.WATER_SKELETONS_CAN_SHOOT;
	}
}