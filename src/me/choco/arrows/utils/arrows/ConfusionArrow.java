package me.choco.arrows.utils.arrows;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.AlchemicalArrow;
import me.choco.arrows.utils.ConfigOption;

public class ConfusionArrow extends AlchemicalArrow{
	
	private static final PotionEffect CONFUSION_EFFECT = new PotionEffect(PotionEffectType.CONFUSION, 500, 0);
	
	public ConfusionArrow(Arrow arrow) {
		super(arrow);
	}
	
	@Override
	public String getName() {
		return "Confusion";
	}
	
	@Override
	public void displayParticle(Player player){
		player.spawnParticle(Particle.SPELL, arrow.getLocation(), 2, 0.1, 0.1, 0.1, 1);
	}
	
	@Override
	public void onHitPlayer(Player player) {
		player.addPotionEffect(CONFUSION_EFFECT);
		
		Location backwards = player.getLocation();
		backwards.setYaw(player.getLocation().getYaw() + 180);
		player.teleport(backwards);
	}
	
	@Override
	public void onHitEntity(Entity entity) {
		if (entity instanceof Creature){
			((Creature) entity).setTarget(null);
		}
	}
	
	@Override
	public void onShootFromPlayer(Player player) {
		if (!player.hasPermission("arrows.shoot.confusion")){
			AlchemicalArrows.getPlugin().getArrowRegistry().unregisterAlchemicalArrow(this);
		}
	}
	
	@Override
	public boolean allowInfinity() {
		return ConfigOption.CONFUSION_ALLOW_INFINITY;
	}
	
	@Override
	public boolean skeletonsCanShoot() {
		return ConfigOption.CONFUSION_SKELETONS_CAN_SHOOT;
	}
	
	@Override
	public double skeletonLootWeight() {
		return ConfigOption.CONFUSION_SKELETON_LOOT_WEIGHT;
	}
}