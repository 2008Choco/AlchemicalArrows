package me.choco.arrows.events;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.projectiles.BlockProjectileSource;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.AlchemicalArrow;

public class CustomDeathMessage implements Listener{

	private final String playerDeathMessage = "%player% was shot by %killer% using a %type% arrow";
	private final String skeletonDeathMessage = "%player% was shot by a skeleton using a %type% arrow";
	private final String blockSourceDeathMessage = "%player% was shot using a %type% arrow";

	AlchemicalArrows plugin;
	public CustomDeathMessage(AlchemicalArrows plugin){
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerDeath(PlayerDeathEvent event){
		if (plugin.getConfig().getBoolean("CustomDeathMessages")){
			if(event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent){
				EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event.getEntity().getLastDamageCause();
				if(e.getDamager() instanceof Arrow){
					Arrow arrow = (Arrow) e.getDamager();
					if (plugin.getArrowRegistry().isAlchemicalArrow(arrow)){
						AlchemicalArrow aarrow = plugin.getArrowRegistry().getAlchemicalArrow(arrow);
						if (arrow.getShooter() instanceof Player){
							Player killer = (Player) arrow.getShooter();
							event.setDeathMessage(playerDeathMessage
									.replace("%player%", event.getEntity().getName())
									.replace("%killer%", killer.getName())
									.replace("%type%", aarrow.getClass().getSimpleName().replace("Arrow", "").toLowerCase()));
						}else if (arrow.getShooter() instanceof Skeleton){
							event.setDeathMessage(skeletonDeathMessage
									.replace("%player%", event.getEntity().getName())
									.replace("%type%", aarrow.getClass().getSimpleName().replace("Arrow", "").toLowerCase()));
						}else if (arrow.getShooter() instanceof BlockProjectileSource){
							event.setDeathMessage(blockSourceDeathMessage
									.replace("%player%", event.getEntity().getName())
									.replace("%type%", aarrow.getClass().getSimpleName().replace("Arrow", "").toLowerCase()));
						}
					}
				}
			}
		}
	}
}