package me.choco.arrows.utils.arrows;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.AlchemicalArrow;

public class MagneticArrow extends AlchemicalArrow{
	
	private static final MaterialData iron = new MaterialData(Material.IRON_BLOCK), gold = new MaterialData(Material.GOLD_BLOCK);
	private static final Random random = new Random();
	
	public MagneticArrow(Arrow arrow) {
		super(arrow);
	}
	
	@Override
	public void displayParticle(Player player) {
		player.spawnParticle(Particle.FALLING_DUST, arrow.getLocation(), 1, 0.1, 0.1, 0.1, iron);
		if (random.nextInt(10) == 0)
			player.spawnParticle(Particle.FALLING_DUST, arrow.getLocation(), 1, 0.1, 0.1, 0.1, gold);
	}
	
	@Override
	public void onHitPlayer(Player player) {
		player.setVelocity(new Vector(-(getArrow().getVelocity().getX() * 1.5), -(getArrow().getVelocity().getY() * 1.1), -(getArrow().getVelocity().getZ()) * 1.5));
		player.playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1, 2);
	}
	
	@Override
	public void onHitEntity(Entity entity) {
		entity.setVelocity(new Vector(-(getArrow().getVelocity().getX() * 1.5), -(getArrow().getVelocity().getY() * 1.1), -(getArrow().getVelocity().getZ()) * 1.5));
		entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1, 2);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onShootFromPlayer(Player player) {
		if (!player.hasPermission("arrows.shoot.magnetic")){
			AlchemicalArrows.getPlugin().getArrowRegistry().unregisterAlchemicalArrow(this);
		}
	}
	
	@Override
	public boolean allowInfinity() {
		return AlchemicalArrows.getPlugin().getConfig().getBoolean("Arrows.MagneticArrow.AllowInfinity");
	}
	
	@Override
	public boolean skeletonsCanShoot() {
		return AlchemicalArrows.getPlugin().getConfig().getBoolean("Arrows.MagneticArrow.SkeletonsCanShoot");
	}
}