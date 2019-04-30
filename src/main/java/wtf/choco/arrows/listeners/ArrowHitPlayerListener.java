package wtf.choco.arrows.listeners;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.internal.platform.WorldGuardPlatform;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.RegionQuery;

import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.registry.ArrowStateManager;

public final class ArrowHitPlayerListener implements Listener {

    private final AlchemicalArrows plugin;
    private final ArrowStateManager stateManager;

    public ArrowHitPlayerListener(AlchemicalArrows plugin) {
        this.plugin = plugin;
        this.stateManager = plugin.getArrowStateManager();
    }

    @EventHandler
    public void onAlchemicalArrowHitPlayer(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Arrow) || !(event.getEntity() instanceof Player)) return;

        Player player = (Player) event.getEntity();
        Arrow arrow = (Arrow) event.getDamager();
        AlchemicalArrowEntity alchemicalArrow = stateManager.get(arrow);

        if (alchemicalArrow == null || player.isBlocking()) return;

        /* WorldGuard Support */
        ProjectileSource source = arrow.getShooter();
        if (source != null && plugin.isWorldGuardSupported()) {
            WorldGuardPlugin worldguardPlugin = WorldGuardPlugin.inst();
            WorldGuardPlatform worldguard = WorldGuard.getInstance().getPlatform();
            RegionQuery query = worldguard.getRegionContainer().createQuery();

            // Check state of shooter
            if (arrow.getShooter() instanceof Player) {
                Player shooter = (Player) source;
                if (!shooter.hasPermission("arrows.worldguardoverride")) {
                    StateFlag.State state = query.queryState(BukkitAdapter.adapt(shooter.getLocation()), worldguardPlugin.wrapPlayer(shooter), Flags.PVP);

                    if (!shooter.hasPermission("arrows.worldguardoverride") && state == StateFlag.State.DENY) {
                        shooter.sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "You cannot hit a player whilst protected by PvP");
                        event.setCancelled(true);
                        return;
                    }
                }
            }

            // Check state of damaged
            if (event.isCancelled() || !query.testState(BukkitAdapter.adapt(player.getLocation()), null, Flags.PVP)) {
                if (arrow.getShooter() instanceof Player){
                    ((Player) source).sendMessage(ChatColor.DARK_AQUA + "AlchemicalArrows> " + ChatColor.GRAY + "This player is protected from PvP");
                }

                event.setCancelled(true);
                return;
            }
        }

        // AlchemicalArrows arrow handling
        AlchemicalArrow type = alchemicalArrow.getImplementation();
        type.hitEntityEventHandler(alchemicalArrow, event);
        type.onHitPlayer(alchemicalArrow, player);
    }

}