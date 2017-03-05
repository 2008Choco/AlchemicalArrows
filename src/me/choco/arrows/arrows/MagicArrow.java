package me.choco.arrows.arrows;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.AlchemicalArrow;
import me.choco.arrows.utils.ConfigOption;

public class MagicArrow extends AlchemicalArrow {
	
	private static final AlchemicalArrows PLUGIN = AlchemicalArrows.getPlugin();
	
	public MagicArrow(Arrow arrow) {
		super(arrow);
	}
	
	@Override
	public String getName() {
		return "Magic";
	}
	
	@Override
	public void displayParticle(Player player) {
		player.spawnParticle(Particle.SPELL_WITCH, arrow.getLocation(), 2, 0.1, 0.1, 0.1);
	}
	
	@Override
	public void onHitPlayer(Player player) {
		Vector arrowVelocity = arrow.getVelocity();
		Vector newVelocity = new Vector((arrowVelocity.getX() * 2), 0.75, (arrowVelocity.getZ()) * 2);
		if (PLUGIN.isUsingPaper()) {
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
		Vector newVelocity = new Vector((arrow.getVelocity().getX() * 2), 0.75, (arrow.getVelocity().getZ()) * 2);
		if (PLUGIN.isUsingPaper()) {
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
		if (!player.hasPermission("arrows.shoot.magic")){
			AlchemicalArrows.getPlugin().getArrowRegistry().unregisterAlchemicalArrow(this);
		}
	}
	
	@Override
	public boolean allowInfinity() {
		return ConfigOption.MAGIC_ALLOW_INFINITY;
	}
	
	@Override
	public boolean skeletonsCanShoot() {
		return ConfigOption.MAGIC_SKELETONS_CAN_SHOOT;
	}
	
	@Override
	public double skeletonLootWeight() {
		return ConfigOption.MAGIC_SKELETON_LOOT_WEIGHT;
	}
}