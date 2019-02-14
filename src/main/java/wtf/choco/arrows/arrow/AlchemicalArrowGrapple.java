package wtf.choco.arrows.arrow;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.api.property.ArrowProperty;

public class AlchemicalArrowGrapple extends AlchemicalArrowAbstract {

	public static final ArrowProperty<Double> PROPERTY_GRAPPLE_FORCE = new ArrowProperty<>(new NamespacedKey(AlchemicalArrows.getInstance(), "grapple_force"), Double.class, 2.5);

	private static final double GRAPPLE_FORCE_LIMIT = 4.0;

	public AlchemicalArrowGrapple(AlchemicalArrows plugin) {
		super(plugin, "grapple", c -> c.getString("Arrow.Grapple.Item.DisplayName", "&eGrapple Arrow"), c -> c.getStringList("Arrow.Grapple.Item.Lore"));

		FileConfiguration config = plugin.getConfig();
		this.properties.setProperty(ArrowProperty.SKELETONS_CAN_SHOOT, config.getBoolean("Arrow.Grapple.Skeleton.CanShoot", true));
		this.properties.setProperty(ArrowProperty.ALLOW_INFINITY, config.getBoolean("Arrow.Grapple.AllowInfinity", false));
		this.properties.setProperty(ArrowProperty.SKELETON_LOOT_WEIGHT, config.getDouble("Arrow.Grapple.Skeleton.LootDropWeight", 10.0));
		this.properties.setProperty(PROPERTY_GRAPPLE_FORCE, Math.min(config.getDouble("Arrow.Grapple.Effect.GrappleForce", PROPERTY_GRAPPLE_FORCE.getDefaultValue()), GRAPPLE_FORCE_LIMIT));
	}

	@Override
	public void tick(AlchemicalArrowEntity arrow, Location location) {
		location.getWorld().spawnParticle(Particle.CRIT, location, 3, 0.1, 0.1, 0.1, 0.1);
	}

	@Override
	public void onHitBlock(AlchemicalArrowEntity arrow, Block block) {
		Arrow bukkitArrow = arrow.getArrow();
		if (!(bukkitArrow.getShooter() instanceof LivingEntity)) return;
		LivingEntity shooter = (LivingEntity) bukkitArrow.getShooter();

		Vector grappleVelocity = bukkitArrow.getLocation().toVector().subtract(shooter.getLocation().toVector()).normalize();
		grappleVelocity.multiply(properties.getPropertyValue(PROPERTY_GRAPPLE_FORCE).doubleValue());

		shooter.setVelocity(grappleVelocity);
		bukkitArrow.getWorld().playSound(bukkitArrow.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1, 2);
		bukkitArrow.remove();
	}

}