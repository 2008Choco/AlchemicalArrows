package me.choco.arrows.utils.arrows;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.AlchemicalArrow;
import me.choco.arrows.utils.ConfigOption;

public class EarthArrow extends AlchemicalArrow{
	
	private static final PotionEffect SLOWNESS_EFFECT = new PotionEffect(PotionEffectType.SLOW, 150, 2);
	private static final MaterialData DIRT = new MaterialData(Material.DIRT);
	
	public EarthArrow(Arrow arrow){
		super(arrow);
	}
	
	@Override
	public String getName() {
		return "Earth";
	}
	
	@Override
	public void displayParticle(Player player) {
		player.spawnParticle(Particle.BLOCK_DUST, arrow.getLocation(), 1, 0.1, 0.1, 0.1, 0.1, DIRT);
	}
	
	@Override
	public void onHitPlayer(Player player) {
		Location location = player.getLocation();
		while (!location.getBlock().getType().isSolid())
			location = location.subtract(0, 1, 0);
		location.setX(location.getBlockX() + 0.5);
		location.setZ(location.getBlockY() + 0.5);
		player.teleport(location);
		
		if (arrow.getShooter() instanceof Player)
			((Player) arrow.getShooter()).playSound(player.getLocation(), Sound.BLOCK_GRASS_BREAK, 1, 0.75F);
		player.playSound(player.getLocation(), Sound.BLOCK_GRASS_BREAK, 1, 0.75F);
		
		player.addPotionEffect(SLOWNESS_EFFECT);
	}
	
	@Override
	public void onHitEntity(Entity entity) {
		if (!(entity instanceof LivingEntity)) return;
		LivingEntity lEntity = (LivingEntity) entity;
		
		Location location = entity.getLocation();
		while (!location.getBlock().getType().isSolid())
			location = location.subtract(0, 1, 0);
		location.setX(location.getBlockX() + 0.5);
		location.setZ(location.getBlockY() + 0.5);
		lEntity.teleport(location);
		
		entity.getWorld().playSound(entity.getLocation(), Sound.BLOCK_GRASS_BREAK, 1, 0.75F);
		lEntity.addPotionEffect(SLOWNESS_EFFECT);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onShootFromPlayer(Player player) {
		if (!player.hasPermission("arrows.shoot.earth")){
			AlchemicalArrows.getPlugin().getArrowRegistry().unregisterAlchemicalArrow(this);
		}
	}
	
	@Override
	public boolean allowInfinity() {
		return ConfigOption.EARTH_ALLOW_INFINITY;
	}
	
	@Override
	public boolean skeletonsCanShoot() {
		return ConfigOption.EARTH_SKELETONS_CAN_SHOOT;
	}
	
	@Override
	public double skeletonLootWeight() {
		return ConfigOption.EARTH_SKELETON_LOOT_WEIGHT;
	}
}