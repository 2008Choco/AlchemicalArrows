package wtf.choco.arrows.arrow;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
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


public class AlchemicalArrowChain extends AlchemicalArrowAbstract {

    private static final ArrowProperty<Double> CHAIN_DAMAGE_FACTOR = new ArrowProperty<>(new NamespacedKey(AlchemicalArrows.getInstance(), "damage_factor"), Double.class, 0.80);
    private static final ArrowProperty<Integer> CHAIN_SEARCH_DISTANCE = new ArrowProperty<>(new NamespacedKey(AlchemicalArrows.getInstance(), "search_distance"), Integer.class, 5);

    private static final int SEARCH_DISTANCE_MAX = 10;
    private static final int SEARCH_DISTANCE_MIN = 1;

    private static final double DAMAGE_FACTOR_MAX = 1.0;
    private static final double DAMAGE_FACTOR_MIN = 0.0;

    public AlchemicalArrowChain(AlchemicalArrows plugin) {
        super(plugin, "Chain", "&2&nChain Arrow");

        FileConfiguration config = plugin.getConfig();
        this.properties.setProperty(ArrowProperty.SKELETONS_CAN_SHOOT, config.getBoolean("Arrow.Chain.Skeleton.CanShoot", true));
        this.properties.setProperty(ArrowProperty.ALLOW_INFINITY, config.getBoolean("Arrow.Chain.AllowInfinity", false));
        this.properties.setProperty(ArrowProperty.SKELETON_LOOT_WEIGHT, config.getDouble("Arrow.Chain.Skeleton.LootDropWeight", 10.0));

        this.properties.setProperty(CHAIN_DAMAGE_FACTOR, Math.max(DAMAGE_FACTOR_MIN, Math.min(config.getDouble("Arrow.Chain.Effect.DamageFactor", 0.80), DAMAGE_FACTOR_MAX)));
        this.properties.setProperty(CHAIN_SEARCH_DISTANCE, Math.max(SEARCH_DISTANCE_MIN, Math.min(config.getInt("Arrow.Chain.Effect.SearchDistance", 5), SEARCH_DISTANCE_MAX)));
    }

    @Override
    public void tick(AlchemicalArrowEntity arrow, Location location) {
        World world = location.getWorld();
        if (world == null) return;

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
        Arrow bukkitArrow = arrow.getArrow();
        this.attemptChain(bukkitArrow, bukkitArrow.getShooter(), (LivingEntity) entity);
        bukkitArrow.remove();
    }

    /*
    Chain the arrow to other nearby targets.
     */
    private void attemptChain(Arrow source, ProjectileSource shooter, LivingEntity hitEntity) {
        if(shooter == hitEntity) {
            return;
        }

        World world = source.getWorld();
        // Source location is center of hit entity instead of their feet
        Location newArrowSourceLoc = hitEntity.getLocation().add(0,hitEntity.getHeight() / 2,0);
        int searchRadius = properties.getProperty(CHAIN_SEARCH_DISTANCE).orElse(5);
        for(Entity newTarget : world.getNearbyEntities(newArrowSourceLoc,searchRadius,searchRadius,searchRadius)){
            // Don't chain to non-living entities, shooter or the original hit entity
            if(!(newTarget instanceof LivingEntity) || newTarget == hitEntity || newTarget == shooter) {
                continue;
            }

            // Calc vector to center of new target entity
            Vector dirToTarget = newTarget.getLocation().toVector().add(new Vector(0,newTarget.getHeight() / 2,0)).subtract(newArrowSourceLoc.toVector());
            Arrow chainArrow = world.spawnArrow(newArrowSourceLoc,dirToTarget, 1.2F,12);
            chainArrow.setShooter(shooter);
            chainArrow.setDamage(chainArrow.getDamage() * properties.getProperty(CHAIN_DAMAGE_FACTOR).orElse(0.80));
            chainArrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
            world.playSound(newArrowSourceLoc, Sound.ENTITY_ARROW_SHOOT,1,1);
        }
    }
}