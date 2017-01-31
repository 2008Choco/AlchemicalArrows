package me.choco.arrows.utils.arrows;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.AlchemicalArrow;
import me.choco.arrows.utils.ConfigOption;

public class MagneticArrow extends AlchemicalArrow{
	
	private static final AlchemicalArrows plugin = AlchemicalArrows.getPlugin();
	
	private static final MaterialData IRON = new MaterialData(Material.IRON_BLOCK), GOLD = new MaterialData(Material.GOLD_BLOCK);
	private static final Random random = new Random();
	
	public MagneticArrow(Arrow arrow) {
		super(arrow);
	}
	
	@Override
	public String getName() {
		return "Magnetic";
	}
	
	@Override
	public void displayParticle(Player player) {
		player.spawnParticle(Particle.FALLING_DUST, arrow.getLocation(), 1, 0.1, 0.1, 0.1, IRON);
		if (random.nextInt(10) == 0)
			player.spawnParticle(Particle.FALLING_DUST, arrow.getLocation(), 1, 0.1, 0.1, 0.1, GOLD);
	}
	
	@Override
	public void onHitPlayer(Player player) {
		Vector arrowVelocity = arrow.getVelocity();
		Vector newVelocity = new Vector((arrowVelocity.getX() * -1.5), arrowVelocity.getY() * -1.1, (arrowVelocity.getZ()) * -1.5);
		if (plugin.isUsingPaper()) {
			boolean negativeX = newVelocity.getX() < 0, negativeY = newVelocity.getY() < 0, negativeZ = newVelocity.getZ() < 0;
			
			newVelocity.setX(Math.min(Math.abs(newVelocity.getX()), 4) * (negativeX ? -1 : 1));
			newVelocity.setY(Math.min(Math.abs(newVelocity.getY()), 4) * (negativeY ? -1 : 1));
			newVelocity.setZ(Math.min(Math.abs(newVelocity.getZ()), 4) * (negativeZ ? -1 : 1));
		}
		
		player.setVelocity(newVelocity);
		player.playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1, 2);
	}
	
	@Override
	public void onHitEntity(Entity entity) {
		Vector arrowVelocity = arrow.getVelocity();
		Vector newVelocity = new Vector((arrowVelocity.getX() * -1.5), arrowVelocity.getY() * -1.1, (arrowVelocity.getZ()) * -1.5);
		if (plugin.isUsingPaper()) {
			boolean negativeX = newVelocity.getX() < 0, negativeY = newVelocity.getY() < 0, negativeZ = newVelocity.getZ() < 0;
			
			newVelocity.setX(Math.min(Math.abs(newVelocity.getX()), 4) * (negativeX ? -1 : 1));
			newVelocity.setY(Math.min(Math.abs(newVelocity.getY()), 4) * (negativeY ? -1 : 1));
			newVelocity.setZ(Math.min(Math.abs(newVelocity.getZ()), 4) * (negativeZ ? -1 : 1));
		}
		
		entity.setVelocity(newVelocity);
		entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1, 2);
	}
	
	@Override
	public void onShootFromPlayer(Player player) {
		if (!player.hasPermission("arrows.shoot.magnetic")){
			AlchemicalArrows.getPlugin().getArrowRegistry().unregisterAlchemicalArrow(this);
		}
	}
	
	@Override
	public boolean allowInfinity() {
		return ConfigOption.MAGNETIC_ALLOW_INFINITY;
	}
	
	@Override
	public boolean skeletonsCanShoot() {
		return ConfigOption.MAGNETIC_SKELETONS_CAN_SHOOT;
	}
	
	@Override
	public double skeletonLootWeight() {
		return ConfigOption.MAGNETIC_SKELETON_LOOT_WEIGHT;
	}
}