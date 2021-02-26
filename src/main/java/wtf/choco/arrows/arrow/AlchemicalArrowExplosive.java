package wtf.choco.arrows.arrow;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.api.property.ArrowProperty;
import wtf.choco.arrows.arrow.entity.ArrowEntityFused;
import wtf.choco.commons.util.MathUtil;

public class AlchemicalArrowExplosive extends ConfigurableAlchemicalArrow {

    public static final ArrowProperty PROPERTY_EXPLOSION_STRENGTH = new ArrowProperty(AlchemicalArrows.key("explosion_strength"), 4.0F);
    public static final ArrowProperty PROPERTY_FUSE_TICKS = new ArrowProperty(AlchemicalArrows.key("fuse_ticks"), 40);
    public static final ArrowProperty PROPERTY_IGNITE_CREEPERS = new ArrowProperty(AlchemicalArrows.key("ignite_creepers"), true);

    private static final BlockData TNT = Material.TNT.createBlockData();
    private static final float EXPLOSION_STRENGTH_LIMIT = 10.0F;

    public AlchemicalArrowExplosive(AlchemicalArrows plugin) {
        super(plugin, "Explosive", "&cExplosive Arrow", 138);

        this.properties.setProperty(ArrowProperty.SKELETONS_CAN_SHOOT, () -> plugin.getConfig().getBoolean("Arrow.Explosive.Skeleton.CanShoot", true));
        this.properties.setProperty(ArrowProperty.ALLOW_INFINITY, () -> plugin.getConfig().getBoolean("Arrow.Explosive.AllowInfinity", false));
        this.properties.setProperty(ArrowProperty.SKELETON_LOOT_WEIGHT, () -> plugin.getConfig().getDouble("Arrow.Explosive.Skeleton.LootDropWeight", 10.0));

        this.properties.setProperty(PROPERTY_EXPLOSION_STRENGTH, () -> MathUtil.clamp((float) plugin.getConfig().getDouble("Arrow.Explosive.Effect.ExplosionStrength", 4.0), 0.0F, EXPLOSION_STRENGTH_LIMIT));
        this.properties.setProperty(PROPERTY_FUSE_TICKS, () -> plugin.getConfig().getInt("Arrow.Explosive.Effect.FuseTicks", 40));
        this.properties.setProperty(PROPERTY_IGNITE_CREEPERS, () -> plugin.getConfig().getBoolean("Arrow.Explosive.Effect.IgniteCreepers", true));
    }

    @Override
    public void tick(AlchemicalArrowEntity arrow, Location location) {
        World world = arrow.getWorld();
        if (!arrow.getArrow().isInBlock()) {
            world.spawnParticle(Particle.BLOCK_CRACK, location, 1, 0.1, 0.1, 0.1, 0.001, TNT);
            return;
        }

        world.spawnParticle(Particle.SMOKE_LARGE, location, 1, 0.1, 0.1, 0.1, 0.001);

        ArrowEntityFused fusedArrow = (ArrowEntityFused) arrow;
        if (fusedArrow.isFuseFinished()) {
            world.createExplosion(location, properties.getProperty(PROPERTY_EXPLOSION_STRENGTH).getAsFloat());
            arrow.getArrow().remove();
        } else {
            fusedArrow.tickFuse();
            world.playSound(location, Sound.ENTITY_CREEPER_HURT, 1, 0.75F + (1 / ((float) fusedArrow.getMaxFuseTicks() / (float) fusedArrow.getFuse())));
        }
    }

    @Override
    public void onHitEntity(@NotNull AlchemicalArrowEntity arrow, @NotNull Entity entity) {
        if (entity.getType() != EntityType.CREEPER || !properties.getProperty(PROPERTY_IGNITE_CREEPERS).getAsBoolean()) {
            return;
        }

        ((Creeper) entity).ignite();
    }

    @Override
    public AlchemicalArrowEntity createNewArrow(Arrow arrow) {
        return new ArrowEntityFused(this, arrow, properties.getProperty(PROPERTY_FUSE_TICKS).getAsInt());
    }

}
