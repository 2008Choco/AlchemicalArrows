package wtf.choco.arrows.listeners;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.internal.platform.WorldGuardPlatform;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.RegionQuery;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.util.AAConstants;

public final class ArrowHitPlayerListener implements Listener {

    private static final Map<UUID, Long> LAST_WARNED = new HashMap<>();
    private static final long WARN_TIMEOUT_MILLISECONDS = TimeUnit.SECONDS.toMillis(2);

    private final AlchemicalArrows plugin;

    public ArrowHitPlayerListener(AlchemicalArrows plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAlchemicalArrowHitPlayer(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Arrow) || !(event.getEntity() instanceof Player)) {
            return;
        }

        Player damaged = (Player) event.getEntity();
        Arrow arrow = (Arrow) event.getDamager();
        AlchemicalArrowEntity alchemicalArrow = plugin.getArrowStateManager().get(arrow);

        if (alchemicalArrow == null) {
            return;
        }

        /* WorldGuard Support */
        ProjectileSource source = arrow.getShooter();
        if (source != null && plugin.isWorldGuardSupported()) {
            WorldGuardPlugin worldguardPlugin = WorldGuardPlugin.inst();
            WorldGuardPlatform worldguard = WorldGuard.getInstance().getPlatform();
            RegionQuery query = worldguard.getRegionContainer().createQuery();

            // Check state of shooter
            if (source instanceof Player && !AAConstants.PERMISSION_WORLDGUARD_OVERRIDE_PREDICATE.test((Player) source)) {
                Player shooter = (Player) source;
                LocalPlayer localShooter = worldguardPlugin.wrapPlayer(shooter);

                // Check state of shooter
                if (query.queryState(BukkitAdapter.adapt(shooter.getLocation()), localShooter, Flags.PVP) == StateFlag.State.DENY) {
                    this.attemptWarn(shooter, AlchemicalArrows.CHAT_PREFIX + "You cannot hit a player whilst protected by PvP");
                    event.setCancelled(true);
                    return;
                }

                // Check state of damaged
                if (event.isCancelled() || query.queryState(BukkitAdapter.adapt(damaged.getLocation()), localShooter, Flags.PVP) == StateFlag.State.DENY) {
                    this.attemptWarn(shooter, AlchemicalArrows.CHAT_PREFIX + "This player is protected from PvP");
                    event.setCancelled(true);
                    return;
                }
            }
        }

        // AlchemicalArrows arrow handling
        AlchemicalArrow type = alchemicalArrow.getImplementation();
        type.hitEntityEventHandler(alchemicalArrow, event);
        type.onHitPlayer(alchemicalArrow, damaged);
    }

    private void attemptWarn(Player player, String message) {
        long now = System.currentTimeMillis();
        UUID playerUUID = player.getUniqueId();

        if (now - LAST_WARNED.getOrDefault(playerUUID, 0L) < WARN_TIMEOUT_MILLISECONDS) {
            return;
        }

        LAST_WARNED.put(playerUUID, now);
        player.sendMessage(message);
    }

}
