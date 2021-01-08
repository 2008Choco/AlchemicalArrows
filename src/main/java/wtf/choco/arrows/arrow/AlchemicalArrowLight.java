package wtf.choco.arrows.arrow;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.api.property.ArrowProperty;

public class AlchemicalArrowLight extends AlchemicalArrowInternal {

    public static final ArrowProperty<Double> PROPERTY_LIGHTNING_CHANCE = new ArrowProperty<>(new NamespacedKey(AlchemicalArrows.getInstance(), "lightning_chance"), Double.class, 5.0);

    private static final Random RANDOM = new Random();

    public AlchemicalArrowLight(AlchemicalArrows plugin) {
        super(plugin, "Light", "&eLight Arrow", 143);

        FileConfiguration config = plugin.getConfig();
        this.properties.setProperty(ArrowProperty.SKELETONS_CAN_SHOOT, config.getBoolean("Arrow.Light.Skeleton.CanShoot", true));
        this.properties.setProperty(ArrowProperty.ALLOW_INFINITY, config.getBoolean("Arrow.Light.AllowInfinity", false));
        this.properties.setProperty(ArrowProperty.SKELETON_LOOT_WEIGHT, config.getDouble("Arrow.Light.Skeleton.LootDropWeight", 10.0));
        this.properties.setProperty(PROPERTY_LIGHTNING_CHANCE, config.getDouble("Arrow.Light.Effect.LightningChance", 5.0));
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
        double lightningChance = properties.getProperty(PROPERTY_LIGHTNING_CHANCE).orElse(5.0);
        if (lightningChance > 0.0 && RANDOM.nextDouble() * 100 < lightningChance) {
            entity.getWorld().strikeLightning(entity.getLocation());
        }

        Location upwards = entity.getLocation();
        upwards.setPitch(-180);
        entity.teleport(upwards);
    }

}
