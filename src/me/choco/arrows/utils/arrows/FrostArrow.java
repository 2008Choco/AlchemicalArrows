package me.choco.arrows.utils.arrows;

import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.AlchemicalArrow;
import me.choco.arrows.utils.ConfigOption;

public class FrostArrow extends AlchemicalArrow{
	
	private static final PotionEffect SLOWNESS_EFFECT = new PotionEffect(PotionEffectType.SLOW, 250, 254);
	private static final PotionEffect ANTI_JUMP_EFFECT = new PotionEffect(PotionEffectType.JUMP, 125, 500);
	
	public FrostArrow(Arrow arrow) {
		super(arrow);
	}
	
	@Override
	public String getName() {
		return "Frost";
	}
	
	@Override
	public void displayParticle(Player player) {
		player.spawnParticle(Particle.SNOW_SHOVEL, arrow.getLocation(), 3, 0.1, 0.1, 0.1, 0.01);
	}
	
	@Override
	public void onHitPlayer(Player player) {
		player.addPotionEffect(SLOWNESS_EFFECT);
		player.addPotionEffect(ANTI_JUMP_EFFECT);
	}
	
	@Override
	public void onHitEntity(Entity entity) {
		if (!(entity instanceof LivingEntity)) return;
		LivingEntity lEntity = (LivingEntity) entity;
		
		lEntity.addPotionEffect(SLOWNESS_EFFECT);
		lEntity.addPotionEffect(ANTI_JUMP_EFFECT);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onShootFromPlayer(Player player) {
		if (!player.hasPermission("arrows.shoot.frost")){
			AlchemicalArrows.getPlugin().getArrowRegistry().unregisterAlchemicalArrow(this);
		}
	}
	
	@Override
	public boolean allowInfinity() {
		return ConfigOption.FROST_ALLOW_INFINITY;
	}
	
	@Override
	public boolean skeletonsCanShoot() {
		return ConfigOption.FROST_SKELETONS_CAN_SHOOT;
	}
}