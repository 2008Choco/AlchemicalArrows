package wtf.choco.arrows.arrow;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.api.property.ArrowProperty;

public class AlchemicalArrowEnder extends ConfigurableAlchemicalArrow {

    public static final ArrowProperty PROPERTY_TELEPORT_ON_HIT_BLOCK = new ArrowProperty(AlchemicalArrows.key("teleport_on_hit_block"), true);

    public AlchemicalArrowEnder(AlchemicalArrows plugin) {
        super(plugin, "Ender", "&5Ender Arrow", 137);

        this.properties.setProperty(ArrowProperty.SKELETONS_CAN_SHOOT, () -> plugin.getConfig().getBoolean("Arrow.Ender.Skeleton.CanShoot", true));
        this.properties.setProperty(ArrowProperty.ALLOW_INFINITY, () -> plugin.getConfig().getBoolean("Arrow.Ender.AllowInfinity", false));
        this.properties.setProperty(ArrowProperty.SKELETON_LOOT_WEIGHT, () -> plugin.getConfig().getDouble("Arrow.Ender.Skeleton.LootDropWeight", 10.0));

        this.properties.setProperty(PROPERTY_TELEPORT_ON_HIT_BLOCK, () -> plugin.getConfig().getBoolean("Arrow.Ender.Effect.TeleportOnHitBlock", true));
    }

    @Override
    public void tick(AlchemicalArrowEntity arrow, Location location) {
        World world = location.getWorld();
        if (world == null) {
            return;
        }

        world.spawnParticle(Particle.PORTAL, location, 3, 0.1, 0.1, 0.1);
    }

    @Override
    public void onHitPlayer(AlchemicalArrowEntity arrow, Player player) {
        Arrow bukkitArrow = arrow.getArrow();
        if (!(bukkitArrow.getShooter() instanceof LivingEntity)) {
            return;
        }

        this.swapLocations(bukkitArrow, (LivingEntity) bukkitArrow.getShooter(), player);
    }

    @Override
    public void onHitEntity(AlchemicalArrowEntity arrow, Entity entity) {
        Arrow bukkitArrow = arrow.getArrow();
        if (!(bukkitArrow.getShooter() instanceof LivingEntity) || !(entity instanceof LivingEntity) || entity.getType() == EntityType.ARMOR_STAND) {
            return;
        }

        this.swapLocations(bukkitArrow, (LivingEntity) bukkitArrow.getShooter(), (LivingEntity) entity);
    }

    @Override
    public void onHitBlock(AlchemicalArrowEntity arrow, Block block) {
        if (!properties.getProperty(PROPERTY_TELEPORT_ON_HIT_BLOCK).getAsBoolean()) {
            return;
        }

        ProjectileSource shooter = arrow.getArrow().getShooter();
        if (!(shooter instanceof LivingEntity)) {
            return;
        }

        LivingEntity shooterEntity = (LivingEntity) shooter;
        Location shooterLocation = shooterEntity.getLocation();

        arrow.getArrow().remove(); // Remove the arrow before we teleport the player

        Location teleportLocation = block.getLocation().add(0.5, 1, 0.5);
        teleportLocation.setPitch(shooterLocation.getPitch());
        teleportLocation.setYaw(shooterLocation.getYaw());
        shooterEntity.teleport(teleportLocation);
    }

    private void swapLocations(Arrow source, LivingEntity shooter, LivingEntity target) {
        source.setKnockbackStrength(0);

        Location targetLocation = target.getLocation();
        Vector targetVelocity = target.getVelocity();

        // Swap player locations
        target.teleport(shooter.getLocation());
        target.setVelocity(shooter.getVelocity());
        shooter.teleport(targetLocation);
        shooter.setVelocity(targetVelocity);

        // Play sounds and display particles
        World world = source.getWorld();
        world.playSound(source.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 3);
        world.playSound(target.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 3);
        world.spawnParticle(Particle.PORTAL, source.getLocation(), 50, 1, 1, 1);
        world.spawnParticle(Particle.PORTAL, target.getLocation(), 5, 1, 1, 1);
    }

}
