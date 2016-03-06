package me.choco.arrows.utils.arrows;

import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.util.Vector;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.AlchemicalArrow;

public class WaterArrow extends AlchemicalArrow{
	public WaterArrow(Arrow arrow) {
		super(arrow);
	}
	
	private Vector initialVector;
	
	@Override
	public void displayParticle(Player player) {
		player.spawnParticle(Particle.WATER_WAKE, getArrow().getLocation(), 3, 0.1, 0.1, 0.1, 0.01);
		if (getArrow().getLocation().getBlock().getType().name().contains("WATER")){
			getArrow().setVelocity(initialVector.normalize().multiply(0.95));
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onShootFromPlayer(Player player) {
		if (!player.hasPermission("arrows.shoot.water")){
			AlchemicalArrows.getPlugin().getArrowRegistry().unregisterAlchemicalArrow(this);
		}else{
			this.initialVector = getArrow().getVelocity();
		}
	}
	
	@Override
	public void onShootFromSkeleton(Skeleton skeleton) {
		this.initialVector = getArrow().getVelocity();
	}
}