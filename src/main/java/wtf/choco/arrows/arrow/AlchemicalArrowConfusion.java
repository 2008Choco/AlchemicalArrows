package wtf.choco.arrows.arrow;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.api.property.ArrowProperty;

public class AlchemicalArrowConfusion extends AlchemicalArrowInternal {

    private static final PotionEffect CONFUSION_EFFECT = new PotionEffect(PotionEffectType.CONFUSION, 100, 0);

    public AlchemicalArrowConfusion(AlchemicalArrows plugin) {
        super(plugin, "Confusion", "&dConfusion Arrow");

        FileConfiguration config = plugin.getConfig();
        this.properties.setProperty(ArrowProperty.SKELETONS_CAN_SHOOT, config.getBoolean("Arrow.Confusion.Skeleton.CanShoot", true));
        this.properties.setProperty(ArrowProperty.ALLOW_INFINITY, config.getBoolean("Arrow.Confusion.AllowInfinity", false));
        this.properties.setProperty(ArrowProperty.SKELETON_LOOT_WEIGHT, config.getDouble("Arrow.Confusion.Skeleton.LootDropWeight", 10.0));
    }

    @Override
    public void tick(AlchemicalArrowEntity arrow, Location location) {
        World world = location.getWorld();
        if (world == null) {
            return;
        }

        world.spawnParticle(Particle.SPELL, location, 2, 0.1, 0.1, 0.1, 1);
    }

    @Override
    public void onHitPlayer(AlchemicalArrowEntity arrow, Player player) {
        player.addPotionEffect(CONFUSION_EFFECT);

        Location backwards = player.getLocation();
        backwards.setYaw(player.getLocation().getYaw() + 180);
        player.teleport(backwards);
    }

    @Override
    public void onHitEntity(AlchemicalArrowEntity arrow, Entity entity) {
        if (!(entity instanceof Creature)) {
            return;
        }

        ((Creature) entity).setTarget(null);
    }

}