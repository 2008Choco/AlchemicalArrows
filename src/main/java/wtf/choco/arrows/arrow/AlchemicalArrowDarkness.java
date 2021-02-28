package wtf.choco.arrows.arrow;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrowEntity;

public class AlchemicalArrowDarkness extends ConfigurableAlchemicalArrow {

    private static final PotionEffect BLINDNESS_EFFECT = new PotionEffect(PotionEffectType.BLINDNESS, 100, 1);

    public AlchemicalArrowDarkness(AlchemicalArrows plugin) {
        super(plugin, "Darkness", "&8Darkness Arrow", 134);
    }

    @Override
    public void tick(AlchemicalArrowEntity arrow, Location location) {
        World world = location.getWorld();
        if (world == null) {
            return;
        }

        world.spawnParticle(Particle.DAMAGE_INDICATOR, location, 1, 0.1, 0.1, 0.1, 0.1);
    }

    @Override
    public void onHitPlayer(AlchemicalArrowEntity arrow, Player player) {
        player.addPotionEffect(BLINDNESS_EFFECT);

        World world = player.getWorld();
        world.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXTINGUISH_FIRE, 1, 0.5F);
        world.spawnParticle(Particle.EXPLOSION_NORMAL, player.getLocation(), 10, 0, 0, 0);
    }

}
