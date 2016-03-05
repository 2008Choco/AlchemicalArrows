package me.choco.arrows.utils.arrows;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.AlchemicalArrow;

public class LightArrow extends AlchemicalArrow{
	public LightArrow(Arrow arrow) {
		super(arrow);
	}
	
	@Override
	public void displayParticle(Player player) {
		player.spawnParticle(Particle.FIREWORKS_SPARK, getArrow().getLocation(), 1, 0.1, 0.1, 0.1, 0.01);
	}
	
	@Override
	public void onHitPlayer(Player player) {
		if (AlchemicalArrows.getPlugin().getConfig().getBoolean("Arrows.LightArrow.StrikesLightning")) 
			player.getWorld().strikeLightning(player.getLocation());
		
		player.teleport(new Location(player.getWorld(), 
				player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), 
				player.getLocation().getYaw(), -180));
	}
	
	@Override
	public void onHitEntity(Entity entity) {
		if (AlchemicalArrows.getPlugin().getConfig().getBoolean("Arrows.LightArrow.StrikesLightning"))
			entity.getWorld().strikeLightning(entity.getLocation());
		
		entity.teleport(new Location(entity.getWorld(), 
				entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ(), 
				entity.getLocation().getYaw(), -180));
	}
}