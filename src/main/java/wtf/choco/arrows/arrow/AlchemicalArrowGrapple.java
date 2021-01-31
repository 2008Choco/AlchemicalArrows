package wtf.choco.arrows.arrow;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.api.property.ArrowProperty;
import wtf.choco.arrows.util.MathUtil;

public class AlchemicalArrowGrapple extends ConfigurableAlchemicalArrow {

    public static final ArrowProperty PROPERTY_GRAPPLE_FORCE = new ArrowProperty(AlchemicalArrows.key("grapple_force"), 2.5);

    private static final double GRAPPLE_FORCE_LIMIT = 4.0;

    public AlchemicalArrowGrapple(AlchemicalArrows plugin) {
        super(plugin, "Grapple", "&eGrapple Arrow", 141);

        this.properties.setProperty(ArrowProperty.SKELETONS_CAN_SHOOT, () -> plugin.getConfig().getBoolean("Arrow.Grapple.Skeleton.CanShoot", true));
        this.properties.setProperty(ArrowProperty.ALLOW_INFINITY, () -> plugin.getConfig().getBoolean("Arrow.Grapple.AllowInfinity", false));
        this.properties.setProperty(ArrowProperty.SKELETON_LOOT_WEIGHT, () -> plugin.getConfig().getDouble("Arrow.Grapple.Skeleton.LootDropWeight", 10.0));

        this.properties.setProperty(PROPERTY_GRAPPLE_FORCE, () -> MathUtil.clamp(plugin.getConfig().getDouble("Arrow.Grapple.Effect.GrappleForce", 2.5D), 0.0D, GRAPPLE_FORCE_LIMIT));
    }

    @Override
    public void tick(AlchemicalArrowEntity arrow, Location location) {
        World world = location.getWorld();
        if (world == null) {
            return;
        }

        world.spawnParticle(Particle.CRIT, location, 3, 0.1, 0.1, 0.1, 0.1);
    }

    @Override
    public void onHitBlock(AlchemicalArrowEntity arrow, Block block) {
        Arrow bukkitArrow = arrow.getArrow();
        if (!(bukkitArrow.getShooter() instanceof LivingEntity)) {
            return;
        }

        LivingEntity shooter = (LivingEntity) bukkitArrow.getShooter();
        if (shooter == null) {
            return;
        }

        Vector grappleVelocity = bukkitArrow.getLocation().toVector().subtract(shooter.getLocation().toVector()).normalize();
        grappleVelocity.multiply(properties.getProperty(PROPERTY_GRAPPLE_FORCE).getAsDouble());

        shooter.setVelocity(grappleVelocity);
        bukkitArrow.getWorld().playSound(bukkitArrow.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1, 2);
        bukkitArrow.remove();
    }

}
