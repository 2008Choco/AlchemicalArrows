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
import wtf.choco.arrows.api.property.ArrowProperty;

public class AlchemicalArrowNecrotic extends ConfigurableAlchemicalArrow {

    private static final ItemStack ROTTEN_FLESH = new ItemStack(Material.ROTTEN_FLESH);

    public AlchemicalArrowNecrotic(AlchemicalArrows plugin) {
        super(plugin, "Necrotic", "&2Necrotic Arrow", 146);

        this.properties.setProperty(ArrowProperty.SKELETONS_CAN_SHOOT, () -> plugin.getConfig().getBoolean("Arrow.Necrotic.Skeleton.CanShoot", true));
        this.properties.setProperty(ArrowProperty.ALLOW_INFINITY, () -> plugin.getConfig().getBoolean("Arrow.Necrotic.AllowInfinity", false));
        this.properties.setProperty(ArrowProperty.SKELETON_LOOT_WEIGHT, () -> plugin.getConfig().getDouble("Arrow.Necrotic.Skeleton.LootDropWeight", 10.0));
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
            if (entity instanceof Monster) {
                ((Monster) entity).setTarget(player);
            }
        }
    }

    @Override
    public void hitEntityEventHandler(AlchemicalArrowEntity arrow, EntityDamageByEntityEvent event) {
        this.lifeSap(event.getDamager(), event.getFinalDamage());
    }

    private void lifeSap(Entity shooter, double damage) {
        if (!(shooter instanceof LivingEntity)) {
            return;
        }

        LivingEntity source = (LivingEntity) shooter;
        AttributeInstance attributeMaxHealth = source.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        source.setHealth(Math.max(source.getHealth() + (damage / 2), (attributeMaxHealth != null) ? attributeMaxHealth.getValue() : 20));
    }

}
