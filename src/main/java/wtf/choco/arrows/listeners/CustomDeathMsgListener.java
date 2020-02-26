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
import org.jetbrains.annotations.NotNull;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrowEntity;

public final class CustomDeathMsgListener implements Listener {

    private final AlchemicalArrows plugin;

    public CustomDeathMsgListener(@NotNull AlchemicalArrows plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerDeath(PlayerDeathEvent event) {
        FileConfiguration config = plugin.getConfig();
        if (!config.getBoolean("DeathMessages.Enabled", true)) {
            return;
        }

        EntityDamageEvent lastDamageCause = event.getEntity().getLastDamageCause();
        if (!(lastDamageCause instanceof EntityDamageByEntityEvent)) {
            return;
        }

        EntityDamageByEntityEvent lastEntityDamage = (EntityDamageByEntityEvent) lastDamageCause;
        if (!(lastEntityDamage.getDamager() instanceof Arrow)) {
            return;
        }

        Arrow arrow = (Arrow) lastEntityDamage.getDamager();
        AlchemicalArrowEntity alchemicalArrow = plugin.getArrowStateManager().get(arrow);
        if (alchemicalArrow == null) {
            return;
        }

        String killedName = event.getEntity().getName();
        String arrowType = alchemicalArrow.getImplementation().getDisplayName();

        // Change death messages
        ProjectileSource source = arrow.getShooter();
        if (source instanceof Player) {
            Player killer = (Player) source;
            String message = config.getString("DeathMessages.DeathByPlayer", "%player% was killed by %killer% using a %type%");
            event.setDeathMessage(message.replace("%player%", killedName).replace("%killer%", killer.getName()).replace("%type%", arrowType));
        }
        else if (source instanceof Skeleton) {
            String message = config.getString("DeathMessages.DeathBySkeleton", "%player% was killed by a skeleton using a %type%");
            event.setDeathMessage(message.replace("%player%", killedName).replace("%type%", arrowType));
        }
        else if (source instanceof BlockProjectileSource) {
            String message = config.getString("DeathMessages.DeathByBlockSource", "%player% was shot using a %type%");
            event.setDeathMessage(message.replace("%player%", killedName).replace("%type%", arrowType));
        }
    }

}