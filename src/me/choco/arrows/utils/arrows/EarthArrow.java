package me.choco.arrows.utils.arrows;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;

import me.choco.arrows.api.AlchemicalArrow;

public class EarthArrow extends AlchemicalArrow{
	public EarthArrow(Arrow arrow){
		super(arrow);
	}
	
	@Override
	public void displayParticle(Player player) {
		player.spawnParticle(Particle.BLOCK_DUST, getArrow().getLocation(), 1, 0.1, 0.1, 0.1, Material.DIRT);
	}
}