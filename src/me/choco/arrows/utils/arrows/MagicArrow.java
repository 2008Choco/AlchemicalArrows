package me.choco.arrows.utils.arrows;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.AlchemicalArrow;
import me.choco.arrows.utils.ConfigOption;

public class MagicArrow extends AlchemicalArrow{
	
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
		player.setVelocity(new Vector((arrow.getVelocity().getX() * 2), 0.75, (arrow.getVelocity().getZ()) * 2));
		player.playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1, 2);
	}
	
	@Override
	public void onHitEntity(Entity entity) {
		entity.setVelocity(new Vector((arrow.getVelocity().getX() * 2), 0.75, (arrow.getVelocity().getZ()) * 2));
		entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1, 2);
	}
	
	@SuppressWarnings("deprecation")
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
}