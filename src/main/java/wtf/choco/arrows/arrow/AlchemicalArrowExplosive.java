package wtf.choco.arrows.arrow;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.api.property.ArrowProperty;
import wtf.choco.arrows.arrow.entity.ArrowEntityFused;

public class AlchemicalArrowExplosive extends AlchemicalArrowInternal {

    public static final ArrowProperty<Float> PROPERTY_EXPLOSION_STRENGTH = new ArrowProperty<>(AlchemicalArrows.key("explosion_strength"), Float.class, 4.0F);
    public static final ArrowProperty<Integer> PROPERTY_FUSE_TICKS = new ArrowProperty<>(AlchemicalArrows.key("fuse_ticks"), Integer.class, 40);

    private static final BlockData TNT = Material.TNT.createBlockData();
    private static final int EXPLOSION_STRENGTH_LIMIT = 10;

    public AlchemicalArrowExplosive(AlchemicalArrows plugin) {
        super(plugin, "Explosive", "&cExplosive Arrow", 138);

        FileConfiguration config = plugin.getConfig();
        this.properties.setProperty(ArrowProperty.SKELETONS_CAN_SHOOT, config.getBoolean("Arrow.Explosive.Skeleton.CanShoot", true));
        this.properties.setProperty(ArrowProperty.ALLOW_INFINITY, config.getBoolean("Arrow.Explosive.AllowInfinity", false));
        this.properties.setProperty(ArrowProperty.SKELETON_LOOT_WEIGHT, config.getDouble("Arrow.Explosive.Skeleton.LootDropWeight", 10.0));
        this.properties.setProperty(PROPERTY_EXPLOSION_STRENGTH, (float) Math.min(config.getDouble("Arrow.Explosive.Effect.ExplosionStrength", 4.0F), EXPLOSION_STRENGTH_LIMIT));
        this.properties.setProperty(PROPERTY_FUSE_TICKS, config.getInt("Arrow.Explosive.Effect.FuseTicks", 40));
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
            world.createExplosion(location, properties.getProperty(PROPERTY_EXPLOSION_STRENGTH).orElse(4.0F));
            arrow.getArrow().remove();
        } else {
            fusedArrow.tickFuse();
            world.playSound(location, Sound.ENTITY_CREEPER_HURT, 1, 0.75F + (1 / ((float) fusedArrow.getMaxFuseTicks() / (float) fusedArrow.getFuse())));
        }
    }

    @Override
    public AlchemicalArrowEntity createNewArrow(Arrow arrow) {
        return new ArrowEntityFused(this, arrow, properties.getProperty(PROPERTY_FUSE_TICKS).orElse(40));
    }

}
