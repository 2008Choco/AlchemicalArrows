package me.choco.arrows.arrows;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.AlchemicalArrow;
import me.choco.arrows.utils.ConfigOption;

public class GrappleArrow extends AlchemicalArrow {
	
	private static final AlchemicalArrows PLUGIN = AlchemicalArrows.getPlugin();
	
	public GrappleArrow(Arrow arrow) {
		super(arrow);
	}
	
	@Override
	public String getName() {
		return "Grapple";
	}
	
	@Override
	public void displayParticle(Player player) {
		player.spawnParticle(Particle.CRIT, arrow.getLocation(), 3, 0.1, 0.1, 0.1, 0.1);
	}
	
	@Override
	public void onHitGround(Block block){
		if (!(arrow.getShooter() instanceof LivingEntity)) return;
		LivingEntity shooter = (LivingEntity) arrow.getShooter();
		
		Vector grappleVelocity = arrow.getLocation().toVector().subtract(shooter.getLocation().toVector()).normalize();
		grappleVelocity.multiply(ConfigOption.GRAPPLE_GRAPPLE_FORCE);
		if (PLUGIN.isUsingPaper()) {
			boolean negativeX = grappleVelocity.getX() < 0, negativeY = grappleVelocity.getY() < 0, negativeZ = grappleVelocity.getZ() < 0;
			
			grappleVelocity.setX(Math.min(Math.abs(grappleVelocity.getX()), 4) * (negativeX ? -1 : 1));
			grappleVelocity.setY(Math.min(Math.abs(grappleVelocity.getY()), 4) * (negativeY ? -1 : 1));
			grappleVelocity.setZ(Math.min(Math.abs(grappleVelocity.getZ()), 4) * (negativeZ ? -1 : 1));
		}
		shooter.setVelocity(grappleVelocity);
		
		arrow.getWorld().playSound(arrow.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1, 2);
	}
	
	@Override
	public void onShootFromPlayer(Player player) {
		if (!player.hasPermission("arrows.shoot.grapple")){
			AlchemicalArrows.getPlugin().getArrowRegistry().unregisterAlchemicalArrow(this);
		}
	}
	
	@Override
	public boolean allowInfinity() {
		return ConfigOption.GRAPPLE_ALLOW_INFINITY;
	}
	
	@Override
	public boolean skeletonsCanShoot() {
		return ConfigOption.GRAPPLE_SKELETONS_CAN_SHOOT;
	}
	
	@Override
	public double skeletonLootWeight() {
		return ConfigOption.GRAPPLE_SKELETON_LOOT_WEIGHT;
	}
}