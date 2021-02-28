package wtf.choco.arrows.arrow;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrowEntity;

public class AlchemicalArrowMagic extends ConfigurableAlchemicalArrow {

    public AlchemicalArrowMagic(AlchemicalArrows plugin) {
        super(plugin, "Magic", "&dMagic Arrow", 144);
    }

    @Override
    public void tick(AlchemicalArrowEntity arrow, Location location) {
        World world = location.getWorld();
        if (world == null) {
            return;
        }

        world.spawnParticle(Particle.SPELL_WITCH, location, 2, 0.1, 0.1, 0.1);
    }

    @Override
    public void onHitPlayer(AlchemicalArrowEntity arrow, Player player) {
        this.launchEntity(arrow.getArrow(), player);
    }

    @Override
    public void onHitEntity(AlchemicalArrowEntity arrow, Entity entity) {
        if (!(entity instanceof LivingEntity)) {
            return;
        }

        this.launchEntity(arrow.getArrow(), (LivingEntity) entity);
    }

    private void launchEntity(Arrow source, LivingEntity entity) {
        Vector sourceVelocity = source.getVelocity();
        Vector targetVelocity = new Vector(sourceVelocity.getX() * 2, 0.75, sourceVelocity.getZ() * 2);

        if (Math.abs(targetVelocity.getX()) > 4 || Math.abs(targetVelocity.getY()) > 4 || Math.abs(targetVelocity.getZ()) > 4) {
            targetVelocity.normalize().multiply(4);
        }

        entity.setVelocity(targetVelocity);
        entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1, 2);
    }

}
