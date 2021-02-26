package wtf.choco.arrows.arrow;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.api.property.ArrowProperty;
import wtf.choco.commons.util.MathUtil;

public class AlchemicalArrowFrost extends ConfigurableAlchemicalArrow {

    public static final ArrowProperty PROPERTY_WATER_FREEZE_RADIUS = new ArrowProperty(AlchemicalArrows.key("water_freeze_radius"), 3.5);

    private static final PotionEffect SLOWNESS_EFFECT = new PotionEffect(PotionEffectType.SLOW, 100, 254);
    private static final PotionEffect ANTI_JUMP_EFFECT = new PotionEffect(PotionEffectType.JUMP, 100, 500);
    private static final double WATER_FREEZE_RADIUS_LIMIT = 7.0;

    public AlchemicalArrowFrost(AlchemicalArrows plugin) {
        super(plugin, "Frost", "&bFrost Arrow", 140);

        this.properties.setProperty(ArrowProperty.SKELETONS_CAN_SHOOT, () -> plugin.getConfig().getBoolean("Arrow.Frost.Skeleton.CanShoot", true));
        this.properties.setProperty(ArrowProperty.ALLOW_INFINITY, () -> plugin.getConfig().getBoolean("Arrow.Frost.AllowInfinity", false));
        this.properties.setProperty(ArrowProperty.SKELETON_LOOT_WEIGHT, () -> plugin.getConfig().getDouble("Arrow.Frost.Skeleton.LootDropWeight", 10.0));

        this.properties.setProperty(PROPERTY_WATER_FREEZE_RADIUS, () -> MathUtil.clamp(plugin.getConfig().getDouble("Arrow.Frost.Effect.WaterFreezeRadius", 3.5D), 0.0D, WATER_FREEZE_RADIUS_LIMIT));
    }

    @Override
    public void tick(AlchemicalArrowEntity arrow, Location location) {
        World world = location.getWorld();
        if (world == null) {
            return;
        }

        world.spawnParticle(Particle.SNOW_SHOVEL, location, 3, 0.1, 0.1, 0.1, 0.01);
        if (location.getBlock().getType() != Material.WATER) {
            return;
        }

        this.freezeRadius(arrow, location);
    }

    @Override
    public void onHitPlayer(AlchemicalArrowEntity arrow, Player player) {
        player.addPotionEffect(SLOWNESS_EFFECT);
        player.addPotionEffect(ANTI_JUMP_EFFECT);
    }

    @Override
    public void onHitEntity(AlchemicalArrowEntity arrow, Entity entity) {
        if (!(entity instanceof LivingEntity)) {
            return;
        }

        LivingEntity livingEntity = (LivingEntity) entity;
        livingEntity.addPotionEffect(SLOWNESS_EFFECT);
        livingEntity.addPotionEffect(ANTI_JUMP_EFFECT);
    }

    @Override
    public void onHitBlock(AlchemicalArrowEntity arrow, Block block) {
        this.freezeRadius(arrow, block.getLocation());
    }

    private void freezeRadius(AlchemicalArrowEntity arrow, Location location) {
        double radius = properties.getProperty(PROPERTY_WATER_FREEZE_RADIUS).getAsDouble();
        if (radius <= 0.0) {
            return;
        }

        // Center the location
        location.setX(location.getBlockX() + 0.5);
        location.setZ(location.getBlockZ() + 0.5);

        boolean blocksChanged = false;

        // Slowly fill ice from outer radius to center
        for (double currentRadius = radius; currentRadius >= 0; currentRadius -= 0.5) {
            for (double theta = 0; theta < Math.PI * 2; theta += Math.PI / 16) {
                double x = Math.cos(theta) * currentRadius;
                double z = Math.sin(theta) * currentRadius;

                location.add(x, 0, z);

                Block block = location.getBlock();
                if (block.getType() == Material.WATER) {
                    location.getBlock().setType(Material.FROSTED_ICE);
                    blocksChanged = true;
                }

                location.subtract(x, 0, z);
            }
        }

        if (blocksChanged) {
            arrow.getArrow().remove();
        }
    }

}
