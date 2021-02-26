package wtf.choco.arrows.arrow;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.api.property.ArrowProperty;
import wtf.choco.commons.util.MathUtil;

public class AlchemicalArrowChain extends ConfigurableAlchemicalArrow {

    private static final ArrowProperty PROPERTY_DAMAGE_FACTOR = new ArrowProperty(AlchemicalArrows.key("damage_factor"), 0.80);
    private static final ArrowProperty PROPERY_SEARCH_DISTANCE = new ArrowProperty(AlchemicalArrows.key("search_distance"), 5);

    private static final int SEARCH_DISTANCE_MAX = 10;
    private static final int SEARCH_DISTANCE_MIN = 1;

    private static final double DAMAGE_FACTOR_MAX = 1.0;
    private static final double DAMAGE_FACTOR_MIN = 0.0;

    public AlchemicalArrowChain(AlchemicalArrows plugin) {
        super(plugin, "Chain", "&2&nChain Arrow", 148);

        this.properties.setProperty(ArrowProperty.SKELETONS_CAN_SHOOT, () -> plugin.getConfig().getBoolean("Arrow.Chain.Skeleton.CanShoot", true));
        this.properties.setProperty(ArrowProperty.ALLOW_INFINITY, () -> plugin.getConfig().getBoolean("Arrow.Chain.AllowInfinity", false));
        this.properties.setProperty(ArrowProperty.SKELETON_LOOT_WEIGHT, () -> plugin.getConfig().getDouble("Arrow.Chain.Skeleton.LootDropWeight", 10.0));

        this.properties.setProperty(PROPERTY_DAMAGE_FACTOR, () -> MathUtil.clamp(plugin.getConfig().getDouble("Arrow.Chain.Effect.DamageFactor", 0.80), DAMAGE_FACTOR_MIN, DAMAGE_FACTOR_MAX));
        this.properties.setProperty(PROPERY_SEARCH_DISTANCE, () -> MathUtil.clamp(plugin.getConfig().getInt("Arrow.Chain.Effect.SearchDistance", 5), SEARCH_DISTANCE_MIN, SEARCH_DISTANCE_MAX));
    }

    @Override
    public void tick(AlchemicalArrowEntity arrow, Location location) {
        World world = location.getWorld();
        if (world == null) {
            return;
        }

        world.spawnParticle(Particle.SLIME, location, 1, 0.1, 0.1, 0.1, 0.001);
    }

    @Override
    public void onHitPlayer(AlchemicalArrowEntity arrow, Player player) {
        Arrow bukkitArrow = arrow.getArrow();
        this.attemptChain(bukkitArrow, bukkitArrow.getShooter(), player);
        bukkitArrow.remove();
    }

    @Override
    public void onHitEntity(AlchemicalArrowEntity arrow, Entity entity) {
        if (!(entity instanceof LivingEntity)) {
            return;
        }

        Arrow bukkitArrow = arrow.getArrow();
        this.attemptChain(bukkitArrow, bukkitArrow.getShooter(), (LivingEntity) entity);
        bukkitArrow.remove();
    }

    // Chain the arrow to other nearby targets.
    private void attemptChain(Arrow source, ProjectileSource shooter, LivingEntity hitEntity) {
        if (shooter == hitEntity) {
            return;
        }

        World world = source.getWorld();
        Location newArrowLocation = hitEntity.getLocation().add(0, hitEntity.getHeight() / 2.0, 0); // Source location is center of hit entity instead of their feet
        Vector newArrowLocationVector = newArrowLocation.toVector();
        int searchRadius = properties.getProperty(PROPERY_SEARCH_DISTANCE).getAsInt();

        for (Entity newTarget : world.getNearbyEntities(newArrowLocation, searchRadius, searchRadius, searchRadius)) {
            // Don't chain to non-living entities, shooter or the original hit entity
            if (!(newTarget instanceof LivingEntity) || newTarget == hitEntity || newTarget == shooter) {
                continue;
            }

            // Calculate vector to center of new target entity
            Vector directionToTarget = newTarget.getLocation().toVector().add(new Vector(0, newTarget.getHeight() / 2.0, 0)).subtract(newArrowLocationVector);
            Arrow chainArrow = world.spawnArrow(newArrowLocation, directionToTarget, 1.2F, 12);
            chainArrow.setShooter(shooter);
            chainArrow.setDamage(chainArrow.getDamage() * properties.getProperty(PROPERTY_DAMAGE_FACTOR).getAsDouble());
            chainArrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
            world.playSound(newArrowLocation, Sound.ENTITY_ARROW_SHOOT, 1, 1);
        }
    }

}
