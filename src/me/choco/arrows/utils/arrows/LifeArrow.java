package me.choco.arrows.utils.arrows;

import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.AlchemicalArrow;
import me.choco.arrows.utils.ConfigOption;

public class LifeArrow extends AlchemicalArrow{
	
	private static final PotionEffect REGENERATION_EFFECT = new PotionEffect(PotionEffectType.REGENERATION, 300, 2);
	
	public LifeArrow(Arrow arrow) {
		super(arrow);
	}
	
	@Override
	public String getName() {
		return "Life";
	}
	
	@Override
	public void displayParticle(Player player) {
		player.spawnParticle(Particle.HEART, arrow.getLocation(), 1, 0.1, 0.1, 0.1);
	}
	
	@Override
	public void onHitPlayer(Player player) {
		player.addPotionEffect(REGENERATION_EFFECT);
	}
	
	@Override
	public void onHitEntity(Entity entity) {
		if (!(entity instanceof LivingEntity)) return;
		LivingEntity lEntity = (LivingEntity) entity;
		
		lEntity.addPotionEffect(REGENERATION_EFFECT);
	}
	
	@Override
	public void hitEntityEventHandler(EntityDamageByEntityEvent event) {
		event.setDamage(0);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onShootFromPlayer(Player player) {
		if (!player.hasPermission("arrows.shoot.life")){
			AlchemicalArrows.getPlugin().getArrowRegistry().unregisterAlchemicalArrow(this);
		}
	}
	
	@Override
	public boolean allowInfinity() {
		return ConfigOption.LIFE_ALLOW_INFINITY;
	}
	
	@Override
	public boolean skeletonsCanShoot() {
		return ConfigOption.LIFE_SKELETONS_CAN_SHOOT;
	}
}