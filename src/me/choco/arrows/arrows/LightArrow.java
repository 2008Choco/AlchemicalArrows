package me.choco.arrows.arrows;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.AlchemicalArrow;
import me.choco.arrows.utils.ConfigOption;

public class LightArrow extends AlchemicalArrow {
	
	public LightArrow(Arrow arrow) {
		super(arrow);
	}
	
	@Override
	public String getName() {
		return "Light";
	}
	
	@Override
	public void displayParticle(Player player) {
		player.spawnParticle(Particle.FIREWORKS_SPARK, arrow.getLocation(), 1, 0.1, 0.1, 0.1, 0.01);
	}
	
	@Override
	public void onHitPlayer(Player player) {
		if (ConfigOption.LIGHT_STRIKES_LIGHTNING) 
			player.getWorld().strikeLightning(player.getLocation());
		
		Location upwards = player.getLocation();
		upwards.setPitch(-180);
		player.teleport(upwards);
	}
	
	@Override
	public void onHitEntity(Entity entity) {
		if (ConfigOption.LIGHT_STRIKES_LIGHTNING)
			entity.getWorld().strikeLightning(entity.getLocation());
		
		Location upwards = entity.getLocation();
		upwards.setPitch(-180);
		entity.teleport(upwards);
	}
	
	@Override
	public void onShootFromPlayer(Player player) {
		if (!player.hasPermission("arrows.shoot.light")){
			AlchemicalArrows.getPlugin().getArrowRegistry().unregisterAlchemicalArrow(this);
		}
	}
	
	@Override
	public boolean allowInfinity() {
		return ConfigOption.LIGHT_ALLOW_INFINITY;
	}
	
	@Override
	public boolean skeletonsCanShoot() {
		return ConfigOption.LIGHT_SKELETONS_CAN_SHOOT;
	}
	
	@Override
	public double skeletonLootWeight() {
		return ConfigOption.LIGHT_SKELETON_LOOT_WEIGHT;
	}
}