package wtf.choco.arrows.arrow;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.api.property.ArrowProperty;
import wtf.choco.arrows.arrow.entity.ArrowEntityWater;

public class AlchemicalArrowWater extends AlchemicalArrowAbstract {

	public AlchemicalArrowWater(AlchemicalArrows plugin) {
		super(plugin, "water", c -> c.getString("Arrow.Water.Item.DisplayName", "&9Water Arrow"), c -> c.getStringList("Arrow.Water.Item.Lore"));

		FileConfiguration config = plugin.getConfig();
		this.properties.setProperty(ArrowProperty.SKELETONS_CAN_SHOOT, config.getBoolean("Arrow.Water.Skeleton.CanShoot", true));
		this.properties.setProperty(ArrowProperty.ALLOW_INFINITY, config.getBoolean("Arrow.Water.AllowInfinity", false));
		this.properties.setProperty(ArrowProperty.SKELETON_LOOT_WEIGHT, config.getDouble("Arrow.Water.Skeleton.LootDropWeight", 10.0));
	}

	@Override
	public void tick(AlchemicalArrowEntity arrow, Location location) {
		if (!(arrow instanceof ArrowEntityWater)) return;

		World world = location.getWorld();
		if (world == null) return;

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