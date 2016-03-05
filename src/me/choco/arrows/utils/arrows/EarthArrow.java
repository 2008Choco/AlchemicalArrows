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

import me.choco.arrows.api.AlchemicalArrow;

public class EarthArrow extends AlchemicalArrow{
	public EarthArrow(Arrow arrow){
		super(arrow);
	}
	
	@Override
	public void displayParticle(Player player) {
		player.spawnParticle(Particle.BLOCK_DUST, getArrow().getLocation(), 1, 0.1, 0.1, 0.1, 0.1, new MaterialData(Material.DIRT));
	}
	
	@Override
	public void onHitPlayer(Player player) {
		Location location = player.getLocation();
		location.setX(Math.floor(location.getX()));
		location.setY(Math.floor(location.getY()));
		location.setZ(Math.floor(location.getZ()));
		while (!location.getBlock().getType().isSolid())
			location = location.subtract(0, 1, 0);
		player.teleport(location);
		
		if (arrow.getShooter() instanceof Player)
			((Player) arrow.getShooter()).playSound(player.getLocation(), Sound.BLOCK_GRASS_BREAK, 1, 0.75F);
		player.playSound(player.getLocation(), Sound.BLOCK_GRASS_BREAK, 1, 0.75F);
		PotionEffect slowness = PotionEffectType.SLOW.createEffect(150, 2);
		player.addPotionEffect(slowness);
	}
	
	@Override
	public void onHitEntity(Entity entity) {
		Location location = entity.getLocation();
		location.setX(Math.floor(location.getX()));
		location.setY(Math.floor(location.getY()));
		location.setZ(Math.floor(location.getZ()));
		while (!location.getBlock().getType().isSolid())
			location = location.subtract(0, 1, 0);
		entity.teleport(location);
		
		entity.getWorld().playSound(entity.getLocation(), Sound.BLOCK_GRASS_BREAK, 1, 0.75F);
		if (entity instanceof LivingEntity){
			PotionEffect slowness = PotionEffectType.SLOW.createEffect(150, 2);
			((LivingEntity) entity).addPotionEffect(slowness);
		}
	}
}