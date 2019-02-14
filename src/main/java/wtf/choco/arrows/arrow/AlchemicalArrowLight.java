package wtf.choco.arrows.arrow;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.api.property.ArrowProperty;

public class AlchemicalArrowLight extends AlchemicalArrowAbstract {

	private final FileConfiguration config;

	public AlchemicalArrowLight(AlchemicalArrows plugin) {
		super(plugin, "light", c -> c.getString("Arrow.Light.Item.DisplayName", "&eLight Arrow"), c -> c.getStringList("Arrow.Light.Item.Lore"));

		this.config = plugin.getConfig();
		this.properties.setProperty(ArrowProperty.SKELETONS_CAN_SHOOT, config.getBoolean("Arrow.Light.Skeleton.CanShoot", true));
		this.properties.setProperty(ArrowProperty.ALLOW_INFINITY, config.getBoolean("Arrow.Light.AllowInfinity", false));
		this.properties.setProperty(ArrowProperty.SKELETON_LOOT_WEIGHT, config.getDouble("Arrow.Light.Skeleton.LootDropWeight", 10.0));
	}

	@Override
	public void tick(AlchemicalArrowEntity arrow, Location location) {
		location.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, location, 1, 0.1, 0.1, 0.1, 0.01);
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
		if (config.getBoolean("Arrow.Light.Effect.StrikeLightning", true)) {
			entity.getWorld().strikeLightning(entity.getLocation());
		}

		Location upwards = entity.getLocation();
		upwards.setPitch(-180);
		entity.teleport(upwards);
	}

}