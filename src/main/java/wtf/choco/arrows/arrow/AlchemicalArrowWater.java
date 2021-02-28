package wtf.choco.arrows.arrow;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Arrow;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.arrow.entity.ArrowEntityWater;

public class AlchemicalArrowWater extends ConfigurableAlchemicalArrow {

    public AlchemicalArrowWater(AlchemicalArrows plugin) {
        super(plugin, "Water", "&9Water Arrow", 147);
    }

    @Override
    public void tick(AlchemicalArrowEntity arrow, Location location) {
        if (!(arrow instanceof ArrowEntityWater)) {
            return;
        }

        World world = location.getWorld();
        if (world == null) {
            return;
        }

        world.spawnParticle(Particle.WATER_WAKE, location, 3, 0.1, 0.1, 0.1, 0.01);

        Arrow bukkitArrow = arrow.getArrow();
        if (bukkitArrow.getLocation().getBlock().getType() == Material.WATER) {
            bukkitArrow.setVelocity(((ArrowEntityWater) arrow).getVelocity(0.9995));
        }
    }

    @Override
    public AlchemicalArrowEntity createNewArrow(Arrow arrow) {
        return new ArrowEntityWater(this, arrow, arrow.getVelocity());
    }

}
