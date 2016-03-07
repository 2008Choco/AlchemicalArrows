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

public class FrostArrow extends AlchemicalArrow{
	public FrostArrow(Arrow arrow) {
		super(arrow);
	}
	
	@Override
	public void displayParticle(Player player) {
		player.spawnParticle(Particle.SNOW_SHOVEL, getArrow().getLocation(), 3, 0.1, 0.1, 0.1, 0.01);
	}
	
	@Override
	public void onHitPlayer(Player player) {
		PotionEffect slowness = PotionEffectType.SLOW.createEffect(250, 254);
		PotionEffect antijump = PotionEffectType.JUMP.createEffect(125, 500);
		player.addPotionEffect(slowness);
		player.addPotionEffect(antijump);
	}
	
	@Override
	public void onHitEntity(Entity entity) {
		if (entity instanceof LivingEntity){
			PotionEffect slowness = PotionEffectType.SLOW.createEffect(250, 254);
			PotionEffect antijump = PotionEffectType.JUMP.createEffect(125, 500);
			((LivingEntity) entity).addPotionEffect(slowness);
			((LivingEntity) entity).addPotionEffect(antijump);
		}
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
		return AlchemicalArrows.getPlugin().getConfig().getBoolean("Arrows.FrostArrow.AllowInfinity");
	}
	
	@Override
	public boolean skeletonsCanShoot() {
		return AlchemicalArrows.getPlugin().getConfig().getBoolean("Arrows.FrostArrow.SkeletonsCanShoot");
	}
}