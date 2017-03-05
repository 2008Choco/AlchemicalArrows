package me.choco.arrows.arrows;

import java.util.Random;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.AlchemicalArrow;
import me.choco.arrows.utils.ConfigOption;

public class AirArrow extends AlchemicalArrow {
	
	private static final Random RANDOM = new Random();
	
	public AirArrow(Arrow arrow) {
		super(arrow);
	}
	
	@Override
	public String getName() {
		return "Air";
	}
	
	@Override
	public void displayParticle(Player player){
		player.spawnParticle(Particle.CLOUD, arrow.getLocation(), 1, 0.1, 0.1, 0.1, 0.01);
	}
	
	@Override
	public void hitEntityEventHandler(EntityDamageByEntityEvent event) {
		if (!(event.getEntity() instanceof LivingEntity)) return;
		LivingEntity entity = (LivingEntity) event.getEntity();
		
		event.setCancelled(true);
		entity.damage(event.getFinalDamage(), event.getDamager());
		entity.setVelocity(entity.getVelocity().setY((RANDOM.nextDouble() * 2) + 1));
		
		if (entity instanceof Player) {
			((Player) entity).playSound(entity.getLocation(), Sound.ITEM_BUCKET_EMPTY, 1, 2);
		}
		
		event.getDamager().remove(); // This will be unregistered
	}
	
	@Override
	public void onHitEntity(Entity entity){
		double x = entity.getVelocity().getX(), z = entity.getVelocity().getZ();
		entity.setVelocity(new Vector(x, (RANDOM.nextDouble() * 2) + 1, z));
		entity.getWorld().playSound(entity.getLocation(), Sound.ITEM_BUCKET_EMPTY, 1, 2);
	}
	
	@Override
	public void onShootFromPlayer(Player player) {
		if (!player.hasPermission("arrows.shoot.air")){
			AlchemicalArrows.getPlugin().getArrowRegistry().unregisterAlchemicalArrow(this);
		}
	}
	
	@Override
	public boolean allowInfinity() {
		return ConfigOption.AIR_ALLOW_INFINITY;
	}
	
	@Override
	public boolean skeletonsCanShoot() {
		return ConfigOption.AIR_SKELETONS_CAN_SHOOT;
	}
	
	@Override
	public double skeletonLootWeight() {
		return ConfigOption.AIR_SKELETON_LOOT_WEIGHT;
	}
}