package me.choco.arrows.startup;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.ArrowType;
import me.choco.arrows.particles.ParticleEffect;
import me.choco.arrows.particles.ParticleEffect.BlockData;
import me.choco.arrows.particles.ParticleEffect.ParticleData;

public class ParticleLoop implements Listener{
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
					}//Close if arrow is dead
					for (Player player : Bukkit.getOnlinePlayers()){
						if (arrow.getWorld() == player.getWorld()){
							if (arrow.getLocation().distanceSquared(player.getLocation()) <= 100){
								if (ArrowType.getArrowType(arrow) == ArrowType.AIR){
									ParticleEffect.CLOUD.display(0.1F, 0.1F, 0.1F, 0.01F, 1, arrow.getLocation(), player);
								}//Close if arrow is an Air arrow
								if (ArrowType.getArrowType(arrow) == ArrowType.EARTH){
									ParticleData ParticleData = new BlockData(Material.DIRT, (byte) 0);
									ParticleEffect.BLOCK_DUST.display(ParticleData, 0.1F, 0.1F, 0.1F, 0.1F, 1, arrow.getLocation(), player);
								}//Close if arrow is an Earth arrow
								if (ArrowType.getArrowType(arrow) == ArrowType.MAGIC){
									ParticleEffect.SPELL_WITCH.display(0.1F, 0.1F, 0.1F, 0.01F, 2, arrow.getLocation(), player);
								}//Close if arrow is a Magic arrow
								if (ArrowType.getArrowType(arrow) == ArrowType.SPECTRAL){
									ParticleEffect.PORTAL.display(0.1F, 0.1F, 0.1F, 0.1F, 3, arrow.getLocation(), player);
								}//Close if arrow is a Spectral arrow
								if (ArrowType.getArrowType(arrow) == ArrowType.LIFE){
									ParticleEffect.HEART.display(0.1F, 0.1F, 0.1F, 1F, 1, arrow.getLocation(), player);
								}//Close if arrow is a Life arrow
								if (ArrowType.getArrowType(arrow) == ArrowType.DEATH){
									ParticleEffect.SMOKE_LARGE.display(0.1F, 0.1F, 0.1F, 0.01F, 2, arrow.getLocation(), player);
								}//Close if arrow is a Death arrow
								if (ArrowType.getArrowType(arrow) == ArrowType.LIGHT){
									ParticleEffect.FIREWORKS_SPARK.display(0.1F, 0.1F, 0.1F, 0.01F, 1, arrow.getLocation(), player);
								}//Close if arrow is a Light arrow
								if (ArrowType.getArrowType(arrow) == ArrowType.DARKNESS){
									//TODO: Darkness particles (Spiral darkness clouds)
								}//Close if arrow is a Darkness arrow
								if (ArrowType.getArrowType(arrow) == ArrowType.FIRE){
									ParticleEffect.SMOKE_NORMAL.display(0.1F, 0.1F, 0.1F, 0.001F, 3, arrow.getLocation(), player);
									ParticleEffect.FLAME.display(0.1F, 0.1F, 0.1F, 0.001F, 1, arrow.getLocation(), player);
								}//Close if arrow is a Fire arrow
								if (ArrowType.getArrowType(arrow) == ArrowType.FROST){
									ParticleEffect.SNOW_SHOVEL.display(0.1F, 0.1F, 0.1F, 0.01F, 3, arrow.getLocation(), player);
								}//Close if arrow is a Frost arrow
								if (ArrowType.getArrowType(arrow) == ArrowType.WATER){
									ParticleEffect.WATER_WAKE.display(0.1F, 0.1F, 0.1F, 0.01F, 3, arrow.getLocation(), player);
								}//Close if arrow is a Water arrow
								if (ArrowType.getArrowType(arrow) == ArrowType.NECROTIC){
									//TODO
								}//Close if arrow is a Necrotic arrow
								if (ArrowType.getArrowType(arrow) == ArrowType.CONFUSION){
									//TODO
								}//Close if arrow is a Confusion arrow
								if (ArrowType.getArrowType(arrow) == ArrowType.MAGNETIC){
									//TODO
								}//Close if arrow is a Magnetic arrow
							}//Close if player is near arrow
							if (ArrowType.getArrowType(arrow) == ArrowType.WATER){
								if (arrow.getLocation().getBlock().getType() == Material.WATER || arrow.getLocation().getBlock().getType() == Material.STATIONARY_WATER){
									Vector underwaterVelocity = new Vector(arrow.getVelocity().getX() * 3, arrow.getVelocity().getY(), arrow.getVelocity().getZ() * 3);
									arrow.setVelocity(underwaterVelocity);
								}//Close if arrow is in water
							}//Close if arrow is a Water arrow
						}//Close if in same world
					}//Close iterate through players
				}//Close iterate through all specialized arrows
			}//Close run
		}, 0L, 1L);
	}//Close enable method
}//Close class