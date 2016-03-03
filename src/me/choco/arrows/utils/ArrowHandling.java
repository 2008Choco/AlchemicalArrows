package me.choco.arrows.utils;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.choco.arrows.api.ArrowType;

public class ArrowHandling {
	
	static Plugin AA = Bukkit.getPluginManager().getPlugin("AlchemicalArrows");
	static Random random = new Random();
	
	public static void arrowEffects(EntityDamageByEntityEvent event, final Entity damaged, Arrow arrow){
		double damage = event.getDamage();
		if (ArrowType.getArrowType(arrow) == ArrowType.AIR){
			event.setCancelled(true);
			arrow.remove();
			double randomY = random.nextDouble() + 1;
			damaged.setVelocity(new Vector(0, randomY, 0));
			((LivingEntity) damaged).damage(damage);
		}
		
		if (ArrowType.getArrowType(arrow) == ArrowType.EARTH){
			event.setCancelled(true);
			arrow.remove();
			Location blocation = damaged.getLocation();
			
			while (!blocation.getBlock().getType().isSolid()){
				blocation = blocation.subtract(0, 1, 0);
			}
			damaged.teleport(blocation);
			
			new BukkitRunnable(){
				int counter = 0;
				public void run(){
					if (counter++ >= 5){
						cancel();
					}
					damaged.getWorld().playSound(damaged.getLocation(), Sound.BLOCK_GRASS_BREAK, 1, 0.75F);	
				}
			}.runTaskTimer(AA, 0L, 1L);
			PotionEffect slowness = PotionEffectType.SLOW.createEffect(150, 2);
			LivingEntity damagedEntity = (LivingEntity) damaged;
			damagedEntity.addPotionEffect(slowness);	
			((LivingEntity) damaged).damage(damage);
		}
		
		if (ArrowType.getArrowType(arrow) == ArrowType.MAGIC){
			event.setCancelled(true);
			arrow.remove();
			damaged.setVelocity(new Vector((arrow.getVelocity().getX() * 2), 0.75, (arrow.getVelocity().getZ()) * 2));
			damaged.getWorld().playSound(damaged.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1, 2);
			((LivingEntity) damaged).damage(damage);
		}
		
		if (ArrowType.getArrowType(arrow) == ArrowType.ENDER){
			if (arrow.getShooter() instanceof Player){
				arrow.setKnockbackStrength(0);
				Player shooter = (Player) arrow.getShooter();
				Location damagedLocation = damaged.getLocation();
				Vector damagedVelocity = damaged.getVelocity();
				
				damaged.teleport(shooter.getLocation());
				damaged.setVelocity(shooter.getVelocity());
				
				shooter.teleport(damagedLocation);
				shooter.setVelocity(damagedVelocity);
				
				damaged.getWorld().playSound(damaged.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 1, 3);
				shooter.getWorld().playSound(shooter.getLocation(), Sound.ENTITY_ENDERMEN_TELEPORT, 1, 3);
				
				shooter.spawnParticle(Particle.PORTAL, damaged.getLocation(), 50, 1, 1, 1);
				shooter.spawnParticle(Particle.PORTAL, shooter.getLocation(), 50, 1, 1, 1);
				
				if (damaged instanceof Player){
					((Player) damaged).spawnParticle(Particle.PORTAL, damaged.getLocation(), 50, 1, 1, 1);
					((Player) damaged).spawnParticle(Particle.PORTAL, shooter.getLocation(), 50, 1, 1, 1);
				}
			}
		}
		
		if (ArrowType.getArrowType(arrow) == ArrowType.LIFE){
			event.setDamage(0);
			PotionEffect regen = PotionEffectType.REGENERATION.createEffect(300, 2);
			LivingEntity damagedEntity = (LivingEntity) damaged;
			damagedEntity.addPotionEffect(regen);	
		}
		
		if (ArrowType.getArrowType(arrow) == ArrowType.DEATH){
			int randomInt = random.nextInt(4);
			if (damaged instanceof Damageable){
				if (randomInt == 3){
					if (AA.getConfig().getBoolean("ElementalArrows.DeathArrow.InstantDeathPossible") == true){
						event.setDamage(((LivingEntity) damaged).getMaxHealth());
					}
				}
				PotionEffect wither = PotionEffectType.WITHER.createEffect(300, 2);
				LivingEntity damagedEntity = (LivingEntity) damaged;
				damagedEntity.addPotionEffect(wither);
			}
		}
		
		if (ArrowType.getArrowType(arrow) == ArrowType.LIGHT){
			if (AA.getConfig().getBoolean("ElementalArrows.LightArrow.StrikesLightning")){
				double x = damaged.getLocation().getX();
				double y = damaged.getLocation().getY();
				double z = damaged.getLocation().getZ();
				damaged.getWorld().strikeLightning(damaged.getLocation());
				damaged.teleport(new Location(damaged.getWorld(), x, y, z, 0, -180));
			}
		}
		
		if (ArrowType.getArrowType(arrow) == ArrowType.DARKNESS){
			PotionEffect blindness = PotionEffectType.BLINDNESS.createEffect(300, 0);
			LivingEntity damagedEntity = (LivingEntity) damaged;
			damagedEntity.addPotionEffect(blindness);
			if (arrow.getShooter() instanceof Player)
				((Player) arrow.getShooter()).spawnParticle(Particle.EXPLOSION_LARGE, damaged.getLocation().add(0, 0.75, 0), 10, 1, 1, 1);
			if (damaged instanceof Player)
				((Player) damaged).spawnParticle(Particle.EXPLOSION_LARGE, damaged.getLocation().add(0, 0.75, 0), 10, 1, 1, 1);
		}
		
		if (ArrowType.getArrowType(arrow) == ArrowType.FIRE){
			damaged.setFireTicks(40 + random.nextInt(59));
		}
		
		if (ArrowType.getArrowType(arrow) == ArrowType.FROST){
			PotionEffect slowness = PotionEffectType.SLOW.createEffect(250, 254);
			PotionEffect antijump = PotionEffectType.JUMP.createEffect(125, 500);
			((LivingEntity) damaged).addPotionEffect(slowness);
			((LivingEntity) damaged).addPotionEffect(antijump);	
		}
		
		if (ArrowType.getArrowType(arrow) == ArrowType.NECROTIC){
			if (damaged instanceof Player){
				List<Entity> nearbyEntities = damaged.getNearbyEntities(50, 10, 50);
				for (Iterator<Entity> it = nearbyEntities.iterator(); it.hasNext();){
					Entity entity = it.next();
					if (entity instanceof Monster){
						((Monster)entity).setTarget((LivingEntity) damaged);
					}
				}
			}
		}
		
		if (ArrowType.getArrowType(arrow) == ArrowType.CONFUSION){
			if (damaged instanceof Player){
				PotionEffect confusion = PotionEffectType.CONFUSION.createEffect(500, 0);
				((Player) damaged).addPotionEffect(confusion);
			}
			if (damaged instanceof Creature){
				Creature creature = (Creature) damaged;
				creature.setTarget(null);
			}
			Vector velocity = damaged.getVelocity();
			float xAxis = damaged.getLocation().getYaw();
			float xAxisModified = xAxis + 180;
			damaged.teleport(new Location(damaged.getWorld(), damaged.getLocation().getX(), damaged.getLocation().getY(), damaged.getLocation().getZ(), xAxisModified, damaged.getLocation().getPitch()));
			damaged.setVelocity(velocity);	
		}
		
		if (ArrowType.getArrowType(arrow) == ArrowType.MAGNETIC){
			event.setCancelled(true);
			arrow.remove();
			damaged.setVelocity(new Vector(-(arrow.getVelocity().getX() * 1.5), -(arrow.getVelocity().getY() * 1.1), -(arrow.getVelocity().getZ()) * 1.5));
			damaged.getWorld().playSound(damaged.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1, 2);
			((LivingEntity) damaged).damage(damage);
		}
		damaged.setLastDamageCause(event);
	}
}