package me.choco.arrows.utils.arrows;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import me.choco.arrows.api.AlchemicalArrow;

public class EnderArrow extends AlchemicalArrow{
	public EnderArrow(Arrow arrow) {
		super(arrow);
	}
	
	@Override
	public void displayParticle(Player player) {
		player.spawnParticle(Particle.PORTAL, getArrow().getLocation(), 3, 0.1, 0.1, 0.1);
	}
	
	@Override
	public void onHitPlayer(Player player) {
		if (getArrow().getShooter() instanceof LivingEntity){
			LivingEntity shooter = (LivingEntity) getArrow().getShooter();
			getArrow().setKnockbackStrength(0);
			Location damagedLocation = player.getLocation();
			Vector damagedVelocity = player.getVelocity();
			
			player.teleport(shooter.getLocation());
			player.setVelocity(shooter.getVelocity());
			
			shooter.teleport(damagedLocation);
			shooter.setVelocity(damagedVelocity);
			
			player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 1, 3);
			shooter.getWorld().playSound(shooter.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 1, 3);
			
			player.spawnParticle(Particle.PORTAL, player.getLocation(), 50, 1, 1, 1);
			player.spawnParticle(Particle.PORTAL, shooter.getLocation(), 50, 1, 1, 1);
			
			if (shooter instanceof Player){
				((Player) shooter).spawnParticle(Particle.PORTAL, player.getLocation(), 50, 1, 1, 1);
				((Player) shooter).spawnParticle(Particle.PORTAL, shooter.getLocation(), 50, 1, 1, 1);
			}
		}
	}
	
	@Override
	public void onHitEntity(Entity entity) {
		if (getArrow().getShooter() instanceof LivingEntity){
			LivingEntity shooter = (LivingEntity) getArrow().getShooter();
			getArrow().setKnockbackStrength(0);
			Location damagedLocation = entity.getLocation();
			Vector damagedVelocity = entity.getVelocity();
			
			entity.teleport(shooter.getLocation());
			entity.setVelocity(shooter.getVelocity());
			
			shooter.teleport(damagedLocation);
			shooter.setVelocity(damagedVelocity);
			
			entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 1, 3);
			shooter.getWorld().playSound(shooter.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 1, 3);
			
			if (shooter instanceof Player){
				((Player) shooter).spawnParticle(Particle.PORTAL, entity.getLocation(), 50, 1, 1, 1);
				((Player) shooter).spawnParticle(Particle.PORTAL, shooter.getLocation(), 50, 1, 1, 1);
			}
		}
	}
}