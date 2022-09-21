package wtf.choco.arrows.arrow;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.api.property.ArrowProperty;

public class AlchemicalArrowDeath extends ConfigurableAlchemicalArrow {

    public static final ArrowProperty PROPERTY_INSTANT_DEATH_POSSIBLE = new ArrowProperty(AlchemicalArrows.key("instant_death_possible"), true);
    public static final ArrowProperty PROPERTY_INSTANT_DEATH_CHANCE = new ArrowProperty(AlchemicalArrows.key("isntant_death_chance"), 20.0);

    private static final PotionEffect WITHER_EFFECT = new PotionEffect(PotionEffectType.WITHER, 100, 2);
    private static final Random RANDOM = new Random();

    public AlchemicalArrowDeath(AlchemicalArrows plugin) {
        super(plugin, "Death", "&0Death Arrow", 135);

        this.properties.setProperty(PROPERTY_INSTANT_DEATH_POSSIBLE, () -> plugin.getConfig().getBoolean("Arrow.Death.Effect.InstantDeathPossible", true));
        this.properties.setProperty(PROPERTY_INSTANT_DEATH_CHANCE, () -> plugin.getConfig().getDouble("Arrow.Death.Effect.InstantDeathChance", 20.0));
    }

    @Override
    public void tick(AlchemicalArrowEntity arrow, Location location) {
        World world = location.getWorld();
        if (world == null) {
            return;
        }

        world.spawnParticle(Particle.SMOKE_LARGE, location, 2, 0.1, 0.1, 0.1, 0.01);
    }

    @Override
    public void onHitPlayer(AlchemicalArrowEntity arrow, Player player) {
        this.attemptInstantDeath(arrow.getArrow(), player);
        player.addPotionEffect(WITHER_EFFECT);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_SKELETON_DEATH, 1, 0.5F);
    }

    @Override
    public void onHitEntity(AlchemicalArrowEntity arrow, Entity entity) {
        if (!(entity instanceof LivingEntity livingEntity)) {
            return;
        }

        this.attemptInstantDeath(arrow.getArrow(), livingEntity);
        livingEntity.addPotionEffect(WITHER_EFFECT);
        livingEntity.getWorld().playSound(livingEntity.getLocation(), Sound.ENTITY_WITHER_SKELETON_DEATH, 1, 0.5F);
    }

    private void attemptInstantDeath(AbstractArrow source, LivingEntity entity) {
        if (!properties.getProperty(PROPERTY_INSTANT_DEATH_POSSIBLE).getAsBoolean()) {
            return;
        }

        int chance = RANDOM.nextInt(100);
        if (chance > properties.getProperty(PROPERTY_INSTANT_DEATH_CHANCE).getAsDouble()) {
            return;
        }

        entity.damage(entity.getHealth(), source);
    }

}
