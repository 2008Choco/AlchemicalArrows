package me.choco.arrows.utils.arrows;

import java.util.Random;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import me.choco.arrows.api.AlchemicalArrow;

public class AirArrow extends AlchemicalArrow{
	
	Random random = new Random();
	
	public AirArrow(Arrow arrow) {
		super(arrow);
	}
	
	@Override
	public void displayParticle(Player player){
		player.spawnParticle(Particle.CLOUD, getArrow().getLocation(), 1, 0.1, 0.1, 0.1, 0.01);
	}
	
	@Override
	public void onHitPlayer(Player player){
		player.setVelocity(new Vector(0, random.nextDouble() + 1, 0));
		player.playSound(player.getLocation(), Sound.ITEM_BUCKET_EMPTY, 1, 2);
	}
	
	@Override
	public void onHitEntity(Entity entity){
		entity.setVelocity(new Vector(0, random.nextDouble() + 1, 0));
		entity.getWorld().playSound(entity.getLocation(), Sound.ITEM_BUCKET_EMPTY, 1, 2);
	}
}