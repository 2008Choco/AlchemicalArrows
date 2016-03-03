package me.choco.arrows.startup;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.ArrowType;

public class ParticleLoop{
	public static void enable(){
		Plugin AA = Bukkit.getServer().getPluginManager().getPlugin("AlchemicalArrows");
		
		AA.getServer().getScheduler().scheduleSyncRepeatingTask(AA, new Runnable(){
			@Override
			public void run(){
				for (Iterator<Arrow> it = AlchemicalArrows.specializedArrows.iterator(); it.hasNext();){
					Arrow arrow = it.next();
					if (arrow.isDead()){
						it.remove();
						continue;
					}
					for (Player player : Bukkit.getOnlinePlayers()){
						if (arrow.getWorld() == player.getWorld()){
							if (arrow.getLocation().distanceSquared(player.getLocation()) <= 400){
								if (ArrowType.getArrowType(arrow) == ArrowType.AIR){
									player.spawnParticle(Particle.CLOUD, arrow.getLocation(), 1, 0.1, 0.1, 0.1);
								}
								else if (ArrowType.getArrowType(arrow) == ArrowType.EARTH){
									player.spawnParticle(Particle.BLOCK_DUST, arrow.getLocation(), 1, 0.1, 0.1, 0.1, Material.DIRT);
								}
								else if (ArrowType.getArrowType(arrow) == ArrowType.MAGIC){
									player.spawnParticle(Particle.SPELL_WITCH, arrow.getLocation(), 2, 0.1, 0.1, 0.1);
								}
								if (ArrowType.getArrowType(arrow) == ArrowType.ENDER){
									player.spawnParticle(Particle.PORTAL, arrow.getLocation(), 3, 0.1, 0.1, 0.1);
								}
								if (ArrowType.getArrowType(arrow) == ArrowType.LIFE){
									player.spawnParticle(Particle.HEART, arrow.getLocation(), 1, 0.1, 0.1, 0.1);
								}
								if (ArrowType.getArrowType(arrow) == ArrowType.DEATH){
									player.spawnParticle(Particle.SMOKE_LARGE, arrow.getLocation(), 2, 0.1, 0.1, 0.1, 0.01);
								}
								if (ArrowType.getArrowType(arrow) == ArrowType.LIGHT){
									player.spawnParticle(Particle.FIREWORKS_SPARK, arrow.getLocation(), 1, 0.1, 0.1, 0.1, 0.01);
								}
								if (ArrowType.getArrowType(arrow) == ArrowType.DARKNESS){
									//TODO: Darkness particles (Spiral darkness clouds)
								}
								if (ArrowType.getArrowType(arrow) == ArrowType.FIRE){
									player.spawnParticle(Particle.SMOKE_NORMAL, arrow.getLocation(), 1, 0.1, 0.1, 0.1, 0.001);
									player.spawnParticle(Particle.FLAME, arrow.getLocation(), 1, 0.1, 0.1, 0.1, 0.001);
								}
								if (ArrowType.getArrowType(arrow) == ArrowType.FROST){
									player.spawnParticle(Particle.SNOW_SHOVEL, arrow.getLocation(), 3, 0.1, 0.1, 0.1, 0.01);
								}
								if (ArrowType.getArrowType(arrow) == ArrowType.WATER){
									player.spawnParticle(Particle.WATER_WAKE, arrow.getLocation(), 3, 0.1, 0.1, 0.1);
								}
								if (ArrowType.getArrowType(arrow) == ArrowType.NECROTIC){
									//TODO
								}
								if (ArrowType.getArrowType(arrow) == ArrowType.CONFUSION){
									//TODO
								}
								if (ArrowType.getArrowType(arrow) == ArrowType.MAGNETIC){
									//TODO
								}
							}
							if (ArrowType.getArrowType(arrow) == ArrowType.WATER){
								if (arrow.getLocation().getBlock().getType() == Material.WATER || arrow.getLocation().getBlock().getType() == Material.STATIONARY_WATER){
									Vector underwaterVelocity = new Vector(arrow.getVelocity().getX() * 3, arrow.getVelocity().getY(), arrow.getVelocity().getZ() * 3);
									arrow.setVelocity(underwaterVelocity);
								}
							}
						}
					}
				}
			}
		}, 0L, 1L);
	}
}