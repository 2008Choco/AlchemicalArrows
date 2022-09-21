package wtf.choco.arrows.listeners;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.projectiles.BlockProjectileSource;
import org.bukkit.projectiles.ProjectileSource;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.util.AAConstants;

public final class CustomDeathMessageListener implements Listener {

    private final AlchemicalArrows plugin;

    public CustomDeathMessageListener(AlchemicalArrows plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDeath(PlayerDeathEvent event) {
        FileConfiguration config = plugin.getConfig();
        if (!config.getBoolean(AAConstants.CONFIG_DEATH_MESSAGES_ENABLED, true)) {
            return;
        }

        EntityDamageEvent lastDamageCause = event.getEntity().getLastDamageCause();
        if (!(lastDamageCause instanceof EntityDamageByEntityEvent lastEntityDamage) || !(lastEntityDamage.getDamager() instanceof Arrow arrow)) {
            return;
        }

        AlchemicalArrowEntity alchemicalArrow = plugin.getArrowStateManager().get(arrow);
        if (alchemicalArrow == null) {
            return;
        }

        String killedName = event.getEntity().getName();
        String arrowType = alchemicalArrow.getImplementation().getDisplayName();

        // Change death messages
        ProjectileSource source = arrow.getShooter();
        if (source instanceof Player killer) {
            String message = config.getString(AAConstants.CONFIG_DEATH_MESSAGES_DEATH_BY_PLAYER, "%player% was killed by %killer% using a %type%");
            event.setDeathMessage(message.replace("%player%", killedName).replace("%killer%", killer.getName()).replace("%type%", arrowType));
        }
        else if (source instanceof Skeleton) {
            String message = config.getString(AAConstants.CONFIG_DEATH_MESSAGES_DEATH_BY_SKELETON, "%player% was killed by a skeleton using a %type%");
            event.setDeathMessage(message.replace("%player%", killedName).replace("%type%", arrowType));
        }
        else if (source instanceof BlockProjectileSource) {
            String message = config.getString(AAConstants.CONFIG_DEATH_MESSAGES_DEATH_BY_BLOCK_SOURCE, "%player% was shot using a %type%");
            event.setDeathMessage(message.replace("%player%", killedName).replace("%type%", arrowType));
        }
    }

}
