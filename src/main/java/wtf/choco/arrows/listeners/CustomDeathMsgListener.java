package wtf.choco.arrows.listeners;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.projectiles.BlockProjectileSource;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.registry.ArrowRegistry;

public class CustomDeathMsgListener implements Listener {

	private final FileConfiguration config;
	private final ArrowRegistry arrowRegistry;

	public CustomDeathMsgListener(AlchemicalArrows plugin) {
		this.config = plugin.getConfig();
		this.arrowRegistry = plugin.getArrowRegistry();
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerDeath(PlayerDeathEvent event) {
		if (!config.getBoolean("DeathMessages.Enabled", true)) return;
		if (!(event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent)) return;

		EntityDamageByEntityEvent e = (EntityDamageByEntityEvent) event.getEntity().getLastDamageCause();
		if (!(e.getDamager() instanceof Arrow)) return;

		Arrow arrow = (Arrow) e.getDamager();
		AlchemicalArrowEntity alchemicalArrow = arrowRegistry.getAlchemicalArrow(arrow);
		if (alchemicalArrow == null) return;

		String killedName = event.getEntity().getName();
		String arrowType = alchemicalArrow.getImplementation().getDisplayName();

		// Change death messages
		if (arrow.getShooter() instanceof Player) {
			Player killer = (Player) arrow.getShooter();
			String message = config.getString("DeathMessages.DeathByPlayer");
			event.setDeathMessage(message.replace("%player%", killedName).replace("%killer%", killer.getName()).replace("%type%", arrowType));
		}
		else if (arrow.getShooter() instanceof Skeleton) {
			String message = config.getString("DeathMessages.DeathBySkeleton");
			event.setDeathMessage(message.replace("%player%", killedName).replace("%type%", arrowType));
		}
		else if (arrow.getShooter() instanceof BlockProjectileSource) {
			String message = config.getString("DeathMessages.DeathByBlockSource");
			event.setDeathMessage(message.replace("%player%", killedName).replace("%type%", arrowType));
		}
	}

}