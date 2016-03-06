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

public class LifeArrow extends AlchemicalArrow{
	public LifeArrow(Arrow arrow) {
		super(arrow);
	}
	
	@Override
	public void displayParticle(Player player) {
		player.spawnParticle(Particle.HEART, getArrow().getLocation(), 1, 0.1, 0.1, 0.1);
	}
	
	@Override
	public void onHitPlayer(Player player) {
		PotionEffect regen = PotionEffectType.REGENERATION.createEffect(300, 2);
		player.addPotionEffect(regen);
	}
	
	@Override
	public void onHitEntity(Entity entity) {
		if (entity instanceof LivingEntity){
			PotionEffect regen = PotionEffectType.REGENERATION.createEffect(300, 2);
			((LivingEntity) entity).addPotionEffect(regen);
		}
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
}