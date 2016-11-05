package me.choco.arrows.utils.arrows;

import java.util.Random;

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

public class DeathArrow extends AlchemicalArrow{
	
	private static final PotionEffect WITHER_EFFECT = new PotionEffect(PotionEffectType.WITHER, 300, 2);
	private static final Random random = new Random();
	
	public DeathArrow(Arrow arrow) {
		super(arrow);
	}
	
	@Override
	public String getName() {
		return "Death";
	}
	
	@Override
	public void displayParticle(Player player) {
		player.spawnParticle(Particle.SMOKE_LARGE, arrow.getLocation(), 2, 0.1, 0.1, 0.1, 0.01);
	}
	
	@Override
	public void onHitPlayer(Player player) {
		if (ConfigOption.DEATH_INSTANT_DEATH_POSSIBLE){
			int randomChance = random.nextInt(100);
			if (randomChance < ConfigOption.DEATH_INSTANT_DEATH_PERCENT_CHANCE){
				player.setHealth(0);
				return;
			}
		}
		
		player.addPotionEffect(WITHER_EFFECT);
	}
	
	@Override
	public void onHitEntity(Entity entity) {
		if (!(entity instanceof LivingEntity)) return;
		LivingEntity lEntity = (LivingEntity) entity;
		
		if (ConfigOption.DEATH_INSTANT_DEATH_POSSIBLE){
			int randomChance = random.nextInt(100);
			if (randomChance < ConfigOption.DEATH_INSTANT_DEATH_PERCENT_CHANCE){
				lEntity.setHealth(0);
				return;
			}
		}
		
		lEntity.addPotionEffect(WITHER_EFFECT);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onShootFromPlayer(Player player) {
		if (!player.hasPermission("arrows.shoot.death")){
			AlchemicalArrows.getPlugin().getArrowRegistry().unregisterAlchemicalArrow(this);
		}
	}
	
	@Override
	public boolean allowInfinity() {
		return ConfigOption.DEATH_ALLOW_INFINITY;
	}
	
	@Override
	public boolean skeletonsCanShoot() {
		return ConfigOption.DEATH_SKELETONS_CAN_SHOOT;
	}
}