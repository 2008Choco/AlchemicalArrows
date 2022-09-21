package wtf.choco.arrows.arrow;

import java.util.Iterator;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrowEntity;

public class AlchemicalArrowNecrotic extends ConfigurableAlchemicalArrow {

    private static final ItemStack ROTTEN_FLESH = new ItemStack(Material.ROTTEN_FLESH);

    public AlchemicalArrowNecrotic(AlchemicalArrows plugin) {
        super(plugin, "Necrotic", "&2Necrotic Arrow", 146);
    }

    @Override
    public void tick(AlchemicalArrowEntity arrow, Location location) {
        World world = location.getWorld();
        if (world == null) {
            return;
        }

        world.spawnParticle(Particle.ITEM_CRACK, location, 2, 0.1, 0.1, 0.1, 0.1, ROTTEN_FLESH);
    }

    @Override
    public void onHitPlayer(AlchemicalArrowEntity arrow, Player player) {
        Iterator<Entity> nearbyEntities = player.getNearbyEntities(50, 10, 50).iterator();

        while (nearbyEntities.hasNext()) {
            Entity entity = nearbyEntities.next();
            if (entity instanceof Monster monster) {
                monster.setTarget(player);
            }
        }
    }

    @Override
    public void hitEntityEventHandler(AlchemicalArrowEntity arrow, EntityDamageByEntityEvent event) {
        this.lifeSap(event.getDamager(), event.getFinalDamage());
    }

    private void lifeSap(Entity shooter, double damage) {
        if (!(shooter instanceof LivingEntity livingEntity)) {
            return;
        }

        AttributeInstance attributeMaxHealth = livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        livingEntity.setHealth(Math.max(livingEntity.getHealth() + (damage / 2), (attributeMaxHealth != null) ? attributeMaxHealth.getValue() : 20));
    }

}
