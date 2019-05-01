package wtf.choco.arrows.arrow;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.api.property.ArrowProperty;

public class AlchemicalArrowFire extends AlchemicalArrowAbstract {

    private static final Random RANDOM = new Random();

    public AlchemicalArrowFire(AlchemicalArrows plugin) {
        super(plugin, "Fire", "&cFire Arrow");

        FileConfiguration config = plugin.getConfig();
        this.properties.setProperty(ArrowProperty.SKELETONS_CAN_SHOOT, config.getBoolean("Arrow.Fire.Skeleton.CanShoot", true));
        this.properties.setProperty(ArrowProperty.ALLOW_INFINITY, config.getBoolean("Arrow.Fire.AllowInfinity", false));
        this.properties.setProperty(ArrowProperty.SKELETON_LOOT_WEIGHT, config.getDouble("Arrow.Fire.Skeleton.LootDropWeight", 10.0));
    }

    @Override
    public void tick(AlchemicalArrowEntity arrow, Location location) {
        World world = location.getWorld();
        if (world == null) return;

        world.spawnParticle(Particle.SMOKE_NORMAL, location, 1, 0.1, 0.1, 0.1, 0.001);
        world.spawnParticle(Particle.FLAME, location, 1, 0.1, 0.1, 0.1, 0.001);
    }

    @Override
    public void onHitPlayer(AlchemicalArrowEntity arrow, Player player) {
        player.setFireTicks(40 + RANDOM.nextInt(61));
    }

    @Override
    public void onHitEntity(AlchemicalArrowEntity arrow, Entity entity) {
        entity.setFireTicks(40 + RANDOM.nextInt(61));
    }

}