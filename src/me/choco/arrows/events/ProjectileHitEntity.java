package me.choco.arrows.events;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.choco.arrows.api.ArrowType;
import me.choco.arrows.api.events.SpecializedArrowHitEntityEvent;
import me.choco.arrows.particles.ParticleEffect;

public class ProjectileHitEntity implements Listener{
	
	@EventHandler(priority = EventPriority.LOW)
	public void onProjectileHitPlayer(EntityDamageByEntityEvent event){
		final Plugin AA = Bukkit.getPluginManager().getPlugin("AlchemicalArrows");
		if (event.getDamager().getType() == EntityType.ARROW){
			if (event.getDamager().getCustomName() != null){
				if (event.getEntity().getWorld().getPVP() == true){
					final Entity damaged = event.getEntity();
					if (damaged instanceof LivingEntity){
						double damage = event.getDamage();
						Arrow arrow = (Arrow) event.getDamager();
						Random random = new Random();
						
						SpecializedArrowHitEntityEvent specializedArrowHitEntityEvent = new SpecializedArrowHitEntityEvent(damaged, arrow.getShooter(), arrow);
						Bukkit.getPluginManager().callEvent(specializedArrowHitEntityEvent);
						
						if (ArrowType.getArrowType(arrow) == ArrowType.AIR){
							if (!specializedArrowHitEntityEvent.isCancelled()){
								event.setCancelled(true);
								arrow.remove();
								double randomY = random.nextDouble();
								randomY++;
								damaged.setVelocity(new Vector(0, randomY, 0));
								((LivingEntity) damaged).damage(damage);
							}
						}//Close if arrow name is "Air"
						
						if (ArrowType.getArrowType(arrow) == ArrowType.EARTH){
							if (!specializedArrowHitEntityEvent.isCancelled()){
								event.setCancelled(true);
								arrow.remove();
								Location blocation = damaged.getLocation();
								int topX = blocation.getBlockX();
								int topY = blocation.getBlockY();
								int topZ = blocation.getBlockZ();
								float eyeX = blocation.getYaw();
								float eyeY = blocation.getPitch();
								
								while (!blocation.getBlock().getType().isSolid()){
									blocation = new Location (damaged.getWorld(), topX, --topY, topZ, eyeX, eyeY);
								}//Close while loop
								damaged.teleport(blocation);
								
								new BukkitRunnable(){
									int counter = 0;
									public void run(){
										if (counter++ >= 5){
											cancel();
										}//Close while loop != 5
										damaged.getWorld().playSound(damaged.getLocation(), Sound.DIG_GRASS, 1, 0.75F);	
									}//Close run
								}.runTaskTimer(AA, 0L, 1L);
								PotionEffect slowness = PotionEffectType.SLOW.createEffect(150, 2);
								LivingEntity damagedEntity = (LivingEntity) damaged;
								damagedEntity.addPotionEffect(slowness);	
								((LivingEntity) damaged).damage(damage);
							}
						}//Close if arrow name is "Earth"
						
						if (ArrowType.getArrowType(arrow) == ArrowType.MAGIC){
							if (!specializedArrowHitEntityEvent.isCancelled()){
								event.setCancelled(true);
								arrow.remove();
								damaged.setVelocity(new Vector((arrow.getVelocity().getX() * 2), 0.75, (arrow.getVelocity().getZ()) * 2));
								damaged.getWorld().playSound(damaged.getLocation(), Sound.BAT_TAKEOFF, 1, 2);
								((LivingEntity) damaged).damage(damage);
							}
						}//Close if arrow name is "Magic"
						
						if (ArrowType.getArrowType(arrow) == ArrowType.SPECTRAL){
							if (!specializedArrowHitEntityEvent.isCancelled()){
								if (arrow.getShooter() instanceof Player){
									arrow.setKnockbackStrength(0);
									Player shooter = (Player) arrow.getShooter();
									Location damagedLocation = damaged.getLocation();
									Vector damagedVelocity = damaged.getVelocity();
									
									damaged.teleport(shooter.getLocation());
									damaged.setVelocity(shooter.getVelocity());
									
									shooter.teleport(damagedLocation);
									shooter.setVelocity(damagedVelocity);
									
									damaged.getWorld().playSound(damaged.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 3);
									shooter.getWorld().playSound(shooter.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 3);
									
									ParticleEffect.PORTAL.display(1F, 1F, 1F, 1F, 50, damaged.getLocation(), shooter);
									ParticleEffect.PORTAL.display(1F, 1F, 1F, 1F, 50, shooter.getLocation(), shooter);
									
									if (damaged instanceof Player){
										ParticleEffect.PORTAL.display(1F, 1F, 1F, 1F, 50, shooter.getLocation(), (Player) damaged, shooter);
										ParticleEffect.PORTAL.display(1F, 1F, 1F, 1F, 50, damaged.getLocation(), (Player) damaged, shooter);
									}//Close if damaged is player
								}//Close if shooter is player	
							}
						}//Close if arrow name is "Spectral"
						
						if (ArrowType.getArrowType(arrow) == ArrowType.LIFE){
							if (!specializedArrowHitEntityEvent.isCancelled()){
								event.setDamage(0);
								PotionEffect regen = PotionEffectType.REGENERATION.createEffect(300, 2);
								LivingEntity damagedEntity = (LivingEntity) damaged;
								damagedEntity.addPotionEffect(regen);	
							}
						}//Close if arrow name is "Life"
						
						if (ArrowType.getArrowType(arrow) == ArrowType.DEATH){
							if (!specializedArrowHitEntityEvent.isCancelled()){
								int randomInt = random.nextInt(4);
								if (damaged instanceof Damageable){
									if (randomInt == 3){
										if (AA.getConfig().getBoolean("ElementalArrows.DeathArrow.InstantDeathPossible") == true){
											event.setDamage(((LivingEntity) damaged).getMaxHealth());
										}//Close if the config enables instant death
									}//Close if int == 3
									PotionEffect wither = PotionEffectType.WITHER.createEffect(300, 2);
									LivingEntity damagedEntity = (LivingEntity) damaged;
									damagedEntity.addPotionEffect(wither);
								}//Close if entity is damageable	
							}
						}//Close if arrow name is "Death"
						
						if (ArrowType.getArrowType(arrow) == ArrowType.LIGHT){
							if (!specializedArrowHitEntityEvent.isCancelled()){
								double x = damaged.getLocation().getX();
								double y = damaged.getLocation().getY();
								double z = damaged.getLocation().getZ();
								damaged.getWorld().strikeLightning(damaged.getLocation());
								damaged.teleport(new Location(damaged.getWorld(), x, y, z, 0, -180));	
							}
						}//Close if arrow name is "Light"
						
						if (ArrowType.getArrowType(arrow) == ArrowType.DARKNESS){
							if (!specializedArrowHitEntityEvent.isCancelled()){
								PotionEffect blindness = PotionEffectType.BLINDNESS.createEffect(300, 0);
								LivingEntity damagedEntity = (LivingEntity) damaged;
								damagedEntity.addPotionEffect(blindness);
								if (damaged instanceof Player){
									ParticleEffect.EXPLOSION_LARGE.display(1f, 1f, 1f, 1f, 10, damaged.getLocation().add(0, 0.75, 0), (Player) damaged);
									if (arrow.getShooter() instanceof Player){
										ParticleEffect.EXPLOSION_LARGE.display(1f, 1f, 1f, 1f, 10, damaged.getLocation().add(0, 0.75, 0), (Player) arrow.getShooter());
									}//Close if shooter is player
								}//Close if damaged is player
								else{
									if (arrow.getShooter() instanceof Player){
										ParticleEffect.EXPLOSION_LARGE.display(1f, 1f, 1f, 1f, 10, damaged.getLocation().add(0, 0.75, 0), (Player) arrow.getShooter());
									}//Close if shooter is a player
								}//Close if an entity is hit	
							}
						}//Close if arrow name is "Darkness"
						
						if (ArrowType.getArrowType(arrow) == ArrowType.FIRE){
							if (!specializedArrowHitEntityEvent.isCancelled()){
								int randomTicks = 40 + random.nextInt(61);
								damaged.setFireTicks(randomTicks);		
							}
						}//Close if arrow name is "Fire"
						
						if (ArrowType.getArrowType(arrow) == ArrowType.FROST){
							if (!specializedArrowHitEntityEvent.isCancelled()){
								PotionEffect slowness = PotionEffectType.SLOW.createEffect(250, 254);
								PotionEffect antijump = PotionEffectType.JUMP.createEffect(125, 500);
								((LivingEntity) damaged).addPotionEffect(slowness);
								((LivingEntity) damaged).addPotionEffect(antijump);	
							}
						}//Close if arrow name is "Frost"
						
						if (ArrowType.getArrowType(arrow) == ArrowType.NECROTIC){
							if (!specializedArrowHitEntityEvent.isCancelled()){
								if (damaged instanceof Player){
									List<Entity> nearbyEntities = damaged.getNearbyEntities(50, 10, 50);
									for (Iterator<Entity> it = nearbyEntities.iterator(); it.hasNext();){
										Entity entity = it.next();
										if (entity instanceof Monster){
											((Monster)entity).setTarget((LivingEntity) damaged);
										}//Close if the entity it is looking at, is a monster
									}//Close listing through every entity
								}//Close if a player is shot	
							}
						}//Close if arrow name is "Nectrotic"
						
						if (ArrowType.getArrowType(arrow) == ArrowType.CONFUSION){
							if (!specializedArrowHitEntityEvent.isCancelled()){
								if (damaged instanceof Player){
									PotionEffect confusion = PotionEffectType.CONFUSION.createEffect(500, 0);
									((Player) damaged).addPotionEffect(confusion);
								}//Close if a player is hit
								if (damaged instanceof Creature){
									Creature creature = (Creature) damaged;
									creature.setTarget(null);
								}//Close if another entity is hit
								Vector velocity = damaged.getVelocity();
								float xAxis = damaged.getLocation().getYaw();
								float xAxisModified = xAxis + 180;
								damaged.teleport(new Location(damaged.getWorld(), damaged.getLocation().getX(), damaged.getLocation().getY(), damaged.getLocation().getZ(), xAxisModified, damaged.getLocation().getPitch()));
								damaged.setVelocity(velocity);	
							}
						}//Close if arrow name is "Confusion"
						
						if (ArrowType.getArrowType(arrow) == ArrowType.MAGNETIC){
							if (!specializedArrowHitEntityEvent.isCancelled()){
								event.setCancelled(true);
								arrow.remove();
								damaged.setVelocity(new Vector(-(arrow.getVelocity().getX() * 1.5), -(arrow.getVelocity().getY() * 1.1), -(arrow.getVelocity().getZ()) * 1.5));
								damaged.getWorld().playSound(damaged.getLocation(), Sound.BAT_TAKEOFF, 1, 2);
								((LivingEntity) damaged).damage(damage);
							}
						}//Close if arrow name is "Confusion"
						
						if (specializedArrowHitEntityEvent.isCancelled()){
							event.setCancelled(true);
						}//Close if the event is cancelled
					}//Close if damaged is a LIVING entity
				}//Close if PvP is enabled
			}//Close if arrow has a name
		}//Close if projectile is an arrow
	}//Close onProjectileHitBlock
}//Close class