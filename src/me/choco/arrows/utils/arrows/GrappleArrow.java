package me.choco.arrows.utils.arrows;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.AlchemicalArrow;

public class GrappleArrow extends AlchemicalArrow{
	
	double grappleForce;
	
	public GrappleArrow(Arrow arrow) {
		super(arrow);
		this.grappleForce = AlchemicalArrows.getPlugin().getConfig().getDouble("Arrows.GrappleArrow.GrappleForce");
	}
	
	@Override
	public void displayParticle(Player player) {
		player.spawnParticle(Particle.CRIT, getArrow().getLocation(), 3, 0.1, 0.1, 0.1, 0.1);
	}
	
	@Override
	public void onHitGround(Block block){
		if (getArrow().getShooter() instanceof LivingEntity){
			Vector grappleVelocity = getArrow().getLocation().toVector().subtract(((LivingEntity) getArrow().getShooter()).getLocation().toVector()).normalize();
			((LivingEntity) getArrow().getShooter()).setVelocity(grappleVelocity.multiply(grappleForce));
			arrow.getWorld().playSound(getArrow().getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1, 2);
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onShootFromPlayer(Player player) {
		if (!player.hasPermission("arrows.shoot.grapple")){
			AlchemicalArrows.getPlugin().getArrowRegistry().unregisterAlchemicalArrow(this);
		}
	}
	
	@Override
	public boolean allowInfinity() {
		return AlchemicalArrows.getPlugin().getConfig().getBoolean("Arrows.GrappleArrow.AllowInfinity");
	}
	
	@Override
	public boolean skeletonsCanShoot() {
		return AlchemicalArrows.getPlugin().getConfig().getBoolean("Arrows.GrappleArrow.SkeletonsCanShoot");
	}
}