package wtf.choco.arrows.arrow;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.api.property.ArrowProperty;

public class AlchemicalArrowLight extends AlchemicalArrowAbstract {

	public static final ArrowProperty<Boolean> PROPERTY_STRIKE_LIGHTNING = new ArrowProperty<>(new NamespacedKey(AlchemicalArrows.getInstance(), "strike_lightning"), Boolean.class, true);

	public AlchemicalArrowLight(AlchemicalArrows plugin) {
		super(plugin, "light", c -> c.getString("Arrow.Light.Item.DisplayName", "&eLight Arrow"), c -> c.getStringList("Arrow.Light.Item.Lore"));

		FileConfiguration config = plugin.getConfig();
		this.properties.setProperty(ArrowProperty.SKELETONS_CAN_SHOOT, config.getBoolean("Arrow.Light.Skeleton.CanShoot", true));
		this.properties.setProperty(ArrowProperty.ALLOW_INFINITY, config.getBoolean("Arrow.Light.AllowInfinity", false));
		this.properties.setProperty(ArrowProperty.SKELETON_LOOT_WEIGHT, config.getDouble("Arrow.Light.Skeleton.LootDropWeight", 10.0));
		this.properties.setProperty(PROPERTY_STRIKE_LIGHTNING, config.getBoolean("Arrow.Light.Effect.StrikeLightning", PROPERTY_STRIKE_LIGHTNING.getDefaultValue()));
	}

	@Override
	public void tick(AlchemicalArrowEntity arrow, Location location) {
		World world = location.getWorld();
		if (world == null) return;

		world.spawnParticle(Particle.FIREWORKS_SPARK, location, 1, 0.1, 0.1, 0.1, 0.01);
	}

	@Override
	public void onHitPlayer(AlchemicalArrowEntity arrow, Player player) {
		this.applyEffect(player);
	}

	@Override
	public void onHitEntity(AlchemicalArrowEntity arrow, Entity entity) {
		if (!(entity instanceof LivingEntity)) return;
		this.applyEffect((LivingEntity) entity);
	}

	private void applyEffect(LivingEntity entity) {
		if (properties.getPropertyValue(PROPERTY_STRIKE_LIGHTNING).booleanValue()) {
			entity.getWorld().strikeLightning(entity.getLocation());
		}

		Location upwards = entity.getLocation();
		upwards.setPitch(-180);
		entity.teleport(upwards);
	}

}