package me.choco.arrows.utils.arrows;

import java.util.Random;

import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.choco.arrows.api.AlchemicalArrow;

public class DeathArrow extends AlchemicalArrow{
	
	Random random = new Random();
	
	public DeathArrow(Arrow arrow) {
		super(arrow);
	}
	
	@Override
	public void displayParticle(Player player) {
		player.spawnParticle(Particle.SMOKE_LARGE, getArrow().getLocation(), 2, 0.1, 0.1, 0.1, 0.01);
	}
	
	@Override
	public void onHitPlayer(Player player) {
		int randomChance = random.nextInt(5) + 1;
		if (randomChance == 3){
			player.setHealth(0);
		}else{
			PotionEffect wither = PotionEffectType.WITHER.createEffect(300, 2);
			player.addPotionEffect(wither);
		}
	}
	
	@Override
	public void onHitEntity(Entity entity) {
		int randomChance = random.nextInt(5) + 1;
		if (randomChance == 3){
			if (entity instanceof LivingEntity)
				((LivingEntity) entity).setHealth(0);
		}else{
			PotionEffect wither = PotionEffectType.WITHER.createEffect(300, 2);
			if (entity instanceof LivingEntity) 
				((LivingEntity) entity).addPotionEffect(wither);
		}
	}
	
	@Override
	public boolean skeletonsCanShoot() {
		return false;
	}
}