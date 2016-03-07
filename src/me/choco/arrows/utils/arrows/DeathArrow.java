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
		if (AlchemicalArrows.getPlugin().getConfig().getBoolean("Arrows.DeathArrow.InstantDeathPossible")){
			int randomChance = random.nextInt(100) + 1;
			if (randomChance <= AlchemicalArrows.getPlugin().getConfig().getInt("Arrows.DeathArrow.InstantDeathPercentChance")){
				player.setHealth(0);
			}else{
				PotionEffect wither = PotionEffectType.WITHER.createEffect(300, 2);
				player.addPotionEffect(wither);
			}
		}else{
			PotionEffect wither = PotionEffectType.WITHER.createEffect(300, 2);
			player.addPotionEffect(wither);
		}
	}
	
	@Override
	public void onHitEntity(Entity entity) {
		if (AlchemicalArrows.getPlugin().getConfig().getBoolean("Arrows.DeathArrow.InstantDeathPossible")){
			int randomChance = random.nextInt(100) + 1;
			if (randomChance <= AlchemicalArrows.getPlugin().getConfig().getInt("Arrows.DeathArrow.InstantDeathPercentChance")){
				if (entity instanceof LivingEntity)
					((LivingEntity) entity).setHealth(0);
			}else{
				PotionEffect wither = PotionEffectType.WITHER.createEffect(300, 2);
				if (entity instanceof LivingEntity) 
					((LivingEntity) entity).addPotionEffect(wither);
			}
		}else{
			PotionEffect wither = PotionEffectType.WITHER.createEffect(300, 2);
			if (entity instanceof LivingEntity) 
				((LivingEntity) entity).addPotionEffect(wither);
		}
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
		return AlchemicalArrows.getPlugin().getConfig().getBoolean("Arrows.DeathArrow.AllowInfinity");
	}
	
	@Override
	public boolean skeletonsCanShoot() {
		return AlchemicalArrows.getPlugin().getConfig().getBoolean("Arrows.DeathArrow.SkeletonsCanShoot");
	}
}