package wtf.choco.arrows.arrow;

import java.util.Collection;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.api.property.ArrowProperty;
import wtf.choco.arrows.util.MathUtil;

public class AlchemicalArrowAir extends ConfigurableAlchemicalArrow {

    public static final ArrowProperty PROPERTY_BREATHE_RADIUS = new ArrowProperty(AlchemicalArrows.key("breathe_radius"), 2.0);
    public static final ArrowProperty PROPERTY_LAUNCH_STRENGTH_MIN = new ArrowProperty(AlchemicalArrows.key("launch_strength_min"), 1.0);
    public static final ArrowProperty PROPERTY_LAUNCH_STRENGTH_MAX = new ArrowProperty(AlchemicalArrows.key("launch_strength_max"), 2.0);

    private static final Random RANDOM = new Random();
    private static final int BREATHE_RADIUS_LIMIT = 4;

    private int lastTick = 10;

    public AlchemicalArrowAir(AlchemicalArrows plugin) {
        super(plugin, "Air", "&oAir Arrow", 132);

        this.properties.setProperty(ArrowProperty.SKELETONS_CAN_SHOOT, () -> plugin.getConfig().getBoolean("Arrow.Air.Skeleton.CanShoot", true));
        this.properties.setProperty(ArrowProperty.ALLOW_INFINITY, () -> plugin.getConfig().getBoolean("Arrow.Air.AllowInfinity", false));
        this.properties.setProperty(ArrowProperty.SKELETON_LOOT_WEIGHT, () -> plugin.getConfig().getDouble("Arrow.Air.Skeleton.LootDropWeight", 10.0));

        this.properties.setProperty(PROPERTY_BREATHE_RADIUS, () -> Math.min(plugin.getConfig().getDouble("Arrow.Air.Effect.BreatheRadius", 2.0), BREATHE_RADIUS_LIMIT));
        this.properties.setProperty(PROPERTY_LAUNCH_STRENGTH_MIN, () -> MathUtil.clamp(plugin.getConfig().getDouble("Arrow.Air.Effect.LaunchStrengthMin", 1.0), 0.0, 4.0));
        this.properties.setProperty(PROPERTY_LAUNCH_STRENGTH_MAX, () -> MathUtil.clamp(plugin.getConfig().getDouble("Arrow.Air.Effect.LaunchStrengthMax", 2.0), 0.0, 4.0));
    }

    @Override
    public void tick(AlchemicalArrowEntity arrow, Location location) {
        World world = location.getWorld();
        if (world == null) {
            return;
        }

        world.spawnParticle(Particle.CLOUD, location, 1, 0.1, 0.1, 0.1, 0.01);

        // Validate in-tile underwater arrow
        if (!arrow.getArrow().isInBlock() || lastTick-- > 0) {
            return;
        }

        Block block = location.getBlock();
        BlockData data = block.getBlockData();
        if (block.getType() != Material.WATER || (data instanceof Waterlogged && !((Waterlogged) data).isWaterlogged())) {
            return;
        }

        double radius = properties.getProperty(PROPERTY_BREATHE_RADIUS).getAsDouble();
        if (radius <= 0.0) {
            return;
        }

        // Replenish air of nearby underwater entities
        Collection<Entity> nearbyEntities = world.getNearbyEntities(location, radius, radius, radius);
        if (nearbyEntities.size() <= 1) {
            return;
        }

        for (Entity entity : nearbyEntities) {
            if (!(entity instanceof LivingEntity)) {
                continue;
            }

            LivingEntity livingEntity = (LivingEntity) entity;
            if (livingEntity.getRemainingAir() >= livingEntity.getMaximumAir() + 40) {
                continue;
            }

            livingEntity.setRemainingAir(livingEntity.getRemainingAir() - 40);
            if (livingEntity.getType() == EntityType.PLAYER) {
                ((Player) livingEntity).playSound(livingEntity.getLocation(), Sound.ENTITY_BOAT_PADDLE_WATER, 1, 0.5F);
            }

            this.lastTick = 20;
        }
    }

    @Override
    public void hitEntityEventHandler(AlchemicalArrowEntity arrow, EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof LivingEntity)) {
            return;
        }

        double min = MathUtil.clamp(properties.getProperty(PROPERTY_LAUNCH_STRENGTH_MIN).getAsDouble(), 0.0, 4.0);
        double max = MathUtil.clamp(properties.getProperty(PROPERTY_LAUNCH_STRENGTH_MAX).getAsDouble(), min, 4.0);

        LivingEntity entity = (LivingEntity) event.getEntity();
        entity.damage(event.getFinalDamage(), event.getDamager());
        entity.setVelocity(entity.getVelocity().setY((RANDOM.nextDouble() * (max - min)) + min));
        entity.getWorld().playSound(entity.getLocation(), Sound.ITEM_BUCKET_EMPTY, 1, 2);

        event.setCancelled(true);
        arrow.getArrow().remove();
    }

}
