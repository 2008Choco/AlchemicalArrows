package wtf.choco.arrows.arrow;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.api.property.ArrowProperty;

public class AlchemicalArrowLight extends ConfigurableAlchemicalArrow {

    public static final ArrowProperty PROPERTY_DISORIENT = new ArrowProperty(AlchemicalArrows.key("disorient"), true);
    public static final ArrowProperty PROPERTY_LIGHTNING_CHANCE = new ArrowProperty(AlchemicalArrows.key("lightning_chance"), 5.0);

    private static final Random RANDOM = new Random();

    public AlchemicalArrowLight(AlchemicalArrows plugin) {
        super(plugin, "Light", "&eLight Arrow", 143);

        this.properties.setProperty(ArrowProperty.SKELETONS_CAN_SHOOT, () -> plugin.getConfig().getBoolean("Arrow.Light.Skeleton.CanShoot", true));
        this.properties.setProperty(ArrowProperty.ALLOW_INFINITY, () -> plugin.getConfig().getBoolean("Arrow.Light.AllowInfinity", false));
        this.properties.setProperty(ArrowProperty.SKELETON_LOOT_WEIGHT, () -> plugin.getConfig().getDouble("Arrow.Light.Skeleton.LootDropWeight", 10.0));

        this.properties.setProperty(PROPERTY_DISORIENT, () -> plugin.getConfig().getBoolean("Arrow.Light.Effect.Disorient", true));
        this.properties.setProperty(PROPERTY_LIGHTNING_CHANCE, () -> plugin.getConfig().getDouble("Arrow.Light.Effect.LightningChance", 5.0));
    }

    @Override
    public void tick(AlchemicalArrowEntity arrow, Location location) {
        World world = location.getWorld();
        if (world == null) {
            return;
        }

        world.spawnParticle(Particle.FIREWORKS_SPARK, location, 1, 0.1, 0.1, 0.1, 0.01);
    }

    @Override
    public void onHitPlayer(AlchemicalArrowEntity arrow, Player player) {
        this.applyEffect(player);
    }

    @Override
    public void onHitEntity(AlchemicalArrowEntity arrow, Entity entity) {
        if (!(entity instanceof LivingEntity)) {
            return;
        }

        this.applyEffect((LivingEntity) entity);
    }

    private void applyEffect(LivingEntity entity) {
        double lightningChance = properties.getProperty(PROPERTY_LIGHTNING_CHANCE).getAsDouble();
        if (lightningChance > 0.0 && RANDOM.nextDouble() * 100 < lightningChance) {
            entity.getWorld().strikeLightning(entity.getLocation());
        }

        if (properties.getProperty(PROPERTY_DISORIENT).getAsBoolean()) {
            Location upwards = entity.getLocation();
            upwards.setPitch(-180);
            entity.teleport(upwards);
        }
    }

}
