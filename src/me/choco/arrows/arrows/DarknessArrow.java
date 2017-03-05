package me.choco.arrows.arrows;

import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.AlchemicalArrow;
import me.choco.arrows.utils.ConfigOption;

public class DarknessArrow extends AlchemicalArrow {
	
	private static final PotionEffect BLINDNESS_EFFECT = new PotionEffect(PotionEffectType.BLINDNESS, 500, 1);
	
	public DarknessArrow(Arrow arrow) {
		super(arrow);
	}
	
	@Override
	public String getName() {
		return "Darkness";
	}
	
	@Override
	public void displayParticle(Player player) {
		player.spawnParticle(Particle.DAMAGE_INDICATOR, arrow.getLocation(), 1, 0.1, 0.1, 0.1, 0.1);
	}
	
	@Override
	public void onHitPlayer(Player player) {
		player.addPotionEffect(BLINDNESS_EFFECT);
	}
	
	@Override
	public void onShootFromPlayer(Player player) {
		if (!player.hasPermission("arrows.shoot.darkness")){
			AlchemicalArrows.getPlugin().getArrowRegistry().unregisterAlchemicalArrow(this);
		}
	}
	
	@Override
	public boolean allowInfinity() {
		return ConfigOption.DARKNESS_ALLOW_INFINITY;
	}
	
	@Override
	public boolean skeletonsCanShoot() {
		return ConfigOption.DARKNESS_SKELETONS_CAN_SHOOT;
	}
	
	@Override
	public double skeletonLootWeight() {
		return ConfigOption.DARKNESS_SKELETON_LOOT_WEIGHT;
	}
}