package wtf.choco.arrows.arrow;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.api.property.ArrowProperty;

import java.util.Random;


public class AlchemicalArrowChain extends AlchemicalArrowAbstract {

    public AlchemicalArrowChain(AlchemicalArrows plugin) {
        super(plugin, "Chain", "&f&nChain Arrow");

        FileConfiguration config = plugin.getConfig();
        this.properties.setProperty(ArrowProperty.SKELETONS_CAN_SHOOT, config.getBoolean("Arrow.Chain.Skeleton.CanShoot", true));
        this.properties.setProperty(ArrowProperty.ALLOW_INFINITY, config.getBoolean("Arrow.Chain.AllowInfinity", false));
        this.properties.setProperty(ArrowProperty.SKELETON_LOOT_WEIGHT, config.getDouble("Arrow.Chain.Skeleton.LootDropWeight", 10.0));
    }

    @Override
    public void tick(AlchemicalArrowEntity arrow, Location location) {
        World world = location.getWorld();
        if (world == null) return;

        world.spawnParticle(Particle.CRIT, location, 1, 0.1, 0.1, 0.1, 0.001);
    }

    @Override
    public void onHitPlayer(AlchemicalArrowEntity arrow, Player player) {
        Arrow bukkitArrow = arrow.getArrow();
        this.attemptChain(bukkitArrow, (LivingEntity) bukkitArrow.getShooter(), (LivingEntity) player);
        bukkitArrow.remove();
    }

    @Override
    public void onHitEntity(AlchemicalArrowEntity arrow, Entity entity) {
        Arrow bukkitArrow = arrow.getArrow();
        this.attemptChain(bukkitArrow, (LivingEntity) bukkitArrow.getShooter(), (LivingEntity) entity);
        bukkitArrow.remove();
    }

    /*
    Chain the arrow to other nearby targets.
     */
    private void attemptChain(Arrow source, LivingEntity shooter, LivingEntity hitEntity) {
        if(shooter == hitEntity){
            return;
        }
        World world = source.getWorld();
        // Source location is center of hit entity instead of their feet
        Location newArrowSourceLoc = hitEntity.getLocation().add(0,hitEntity.getHeight() / 2,0);
        for(Entity newTarget : world.getNearbyEntities(newArrowSourceLoc,5,5,5)){
            // Don't chain to non-living entities, shooter or the original hit entity
            if(!(newTarget instanceof LivingEntity) || newTarget == hitEntity || newTarget == shooter) {
                continue;
            }
            // Calc vector to center of new target entity
            Vector dirToTarget = newTarget.getLocation().toVector().add(new Vector(0,newTarget.getHeight() / 2,0)).subtract(newArrowSourceLoc.toVector());
            Arrow chainArrow = world.spawnArrow(newArrowSourceLoc,dirToTarget, 1.2F,12);
            chainArrow.setShooter(shooter);
            chainArrow.setDamage(chainArrow.getDamage() * 0.80);
            chainArrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
            world.playSound(newArrowSourceLoc, Sound.ENTITY_ARROW_SHOOT,1,1);
        }
    }
}