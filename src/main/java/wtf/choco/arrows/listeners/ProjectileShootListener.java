package wtf.choco.arrows.listeners;

import com.google.common.collect.Iterables;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.AbstractArrow.PickupStatus;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.projectiles.BlockProjectileSource;
import org.bukkit.projectiles.ProjectileSource;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.api.event.AlchemicalArrowShootEvent;
import wtf.choco.arrows.api.property.ArrowProperty;
import wtf.choco.arrows.util.AAConstants;

public final class ProjectileShootListener implements Listener {

    private static final Random RANDOM = new Random();

    private final Map<Block, AlchemicalArrow> recentlyDispensed = new HashMap<>();
    private final Map<UUID, Long> lastShotMultishotArrow = new HashMap<>();

    private final AlchemicalArrows plugin;

    public ProjectileShootListener(AlchemicalArrows plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent event) {
        if (!(event.getProjectile() instanceof Arrow arrow)) {
            return;
        }

        LivingEntity shooter = event.getEntity();
        AlchemicalArrow alchemicalArrow = null;
        ItemStack arrowItem = event.getConsumable();

        if (arrowItem == null) {
            if (!(shooter instanceof Skeleton) || RANDOM.nextInt(100) > plugin.getConfig().getDouble(AAConstants.CONFIG_SKELETONS_SHOOT_PERCENTAGE, 10.0)) {
                return;
            }

            Collection<AlchemicalArrow> arrows = plugin.getArrowRegistry().getRegisteredArrows();
            AlchemicalArrow type = Iterables.get(arrows, RANDOM.nextInt(arrows.size()));
            if (type == null || !type.getProperties().getProperty(ArrowProperty.SKELETONS_CAN_SHOOT).getAsBoolean()) {
                return;
            }
        } else {
            alchemicalArrow = plugin.getArrowRegistry().get(arrowItem);
        }

        if (alchemicalArrow == null) {
            return;
        }

        AlchemicalArrowEntity alchemicalArrowEntity = alchemicalArrow.createNewArrow(arrow);

        if (!handleOnShootFromSource(alchemicalArrow, alchemicalArrowEntity, shooter)) {
            event.setCancelled(true);
            return;
        }

        ItemStack bow = event.getBow();
        if (bow != null && bow.hasItemMeta()) {
            ItemMeta bowMeta = bow.getItemMeta();

            if (bowMeta.hasEnchant(Enchantment.ARROW_INFINITE)) {
                if (alchemicalArrow.getProperties().getProperty(ArrowProperty.ALLOW_INFINITY).getAsBoolean()) {
                    event.setConsumeItem(false);
                    if (shooter instanceof Player player) {
                        player.updateInventory();
                    }
                }
                else if (shooter instanceof Player player && player.getGameMode() != GameMode.CREATIVE) {
                    event.setConsumeItem(true);
                } else {
                    arrow.setPickupStatus(PickupStatus.CREATIVE_ONLY);
                }
            }

            if (bowMeta.hasEnchant(Enchantment.MULTISHOT) && bowMeta instanceof CrossbowMeta && !alchemicalArrow.getProperties().getProperty(ArrowProperty.ALLOW_MULTISHOT).getAsBoolean()) {
                long now = System.currentTimeMillis();
                UUID shooterUUID = shooter.getUniqueId();
                Long lastShot = lastShotMultishotArrow.put(shooterUUID, now);

                if (lastShot != null) {
                    long difference = now - lastShot.longValue();

                    if (difference <= 50) { // 1 tick
                        event.setCancelled(true);
                        return;
                    }
                }
            }
        }

        AlchemicalArrowShootEvent aasEvent = new AlchemicalArrowShootEvent(alchemicalArrowEntity, shooter);
        Bukkit.getPluginManager().callEvent(aasEvent);
        if (aasEvent.isCancelled()) {
            event.setCancelled(true);
            return;
        }

        this.plugin.getArrowStateManager().add(alchemicalArrowEntity);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onDispenserShootAlchemicalArrow(BlockDispenseEvent event) {
        AlchemicalArrow alchemicalArrow = plugin.getArrowRegistry().get(event.getItem());
        if (alchemicalArrow == null) {
            return;
        }

        this.recentlyDispensed.put(event.getBlock(), alchemicalArrow);
    }

    @EventHandler
    public void onDispenserShootAlchemicalArrow(ProjectileLaunchEvent event) {
        ProjectileSource source = event.getEntity().getShooter();
        if (!(source instanceof BlockProjectileSource) || !(event.getEntity() instanceof Arrow)) {
            return;
        }

        BlockProjectileSource blockSource = (BlockProjectileSource) source;
        AlchemicalArrow alchemicalArrow = recentlyDispensed.remove(blockSource.getBlock());
        if (alchemicalArrow == null) {
            return;
        }

        AlchemicalArrowEntity alchemicalArrowEntity = alchemicalArrow.createNewArrow((Arrow) event.getEntity());
        if (!alchemicalArrow.onShootFromBlockSource(alchemicalArrowEntity, blockSource)) {
            event.setCancelled(true);
            return;
        }

        AlchemicalArrowShootEvent aasEvent = new AlchemicalArrowShootEvent(alchemicalArrowEntity, blockSource);
        Bukkit.getPluginManager().callEvent(aasEvent);
        if (aasEvent.isCancelled()) {
            event.setCancelled(true);
            return;
        }

        this.plugin.getArrowStateManager().add(alchemicalArrowEntity);
    }

    private boolean handleOnShootFromSource(AlchemicalArrow alchemicalArrow, AlchemicalArrowEntity alchemicalArrowEntity, LivingEntity entity) {
        if (entity instanceof Player) {
            return alchemicalArrow.onShootFromPlayer(alchemicalArrowEntity, (Player) entity);
        }
        else if (entity instanceof Skeleton) {
            return alchemicalArrow.onShootFromSkeleton(alchemicalArrowEntity, (Skeleton) entity);
        }

        return false;
    }

}
