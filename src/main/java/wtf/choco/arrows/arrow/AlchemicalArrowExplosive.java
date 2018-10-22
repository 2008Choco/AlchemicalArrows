package wtf.choco.arrows.arrow;

import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.api.property.ArrowProperty;
import wtf.choco.arrows.arrow.entity.ArrowEntityFused;
import wtf.choco.arrows.utils.ItemBuilder;

public class AlchemicalArrowExplosive extends AlchemicalArrow {
	
	private static final BlockData TNT = Material.TNT.createBlockData();
	
	private final ItemStack item;
	private final NamespacedKey key;
	private final FileConfiguration config;
	
	public AlchemicalArrowExplosive(AlchemicalArrows plugin) {
		this.config = plugin.getConfig();
		this.key = new NamespacedKey(plugin, "explosive");
		this.item = ItemBuilder.of(Material.ARROW)
				.name(ChatColor.translateAlternateColorCodes('&', config.getString("Arrow.Explosive.Item.DisplayName", "&cExplosive Arrow")))
				.lore(config.getStringList("Arrow.Explosive.Item.Lore").stream()
						.map(s -> ChatColor.translateAlternateColorCodes('&', s))
						.collect(Collectors.toList())
				).build();
		
		this.properties.setProperty(ArrowProperty.SKELETONS_CAN_SHOOT, config.getBoolean("Arrow.Explosive.Skeleton.CanShoot", true));
		this.properties.setProperty(ArrowProperty.ALLOW_INFINITY, config.getBoolean("Arrow.Explosive.AllowInfinity", false));
		this.properties.setProperty(ArrowProperty.SKELETON_LOOT_WEIGHT, config.getDouble("Arrow.Explosive.Skeleton.LootDropWeight", 10.0));
	}
	
	@Override
	public NamespacedKey getKey() {
		return key;
	}
	
	@Override
	public String getDisplayName() {
		return item.getItemMeta().getDisplayName();
	}
	
	@Override
	public ItemStack getItem() {
		return item.clone();
	}
	
	@Override
	public void tick(AlchemicalArrowEntity arrow, Location location) {
		World world = arrow.getWorld();
		if (!arrow.getArrow().isInBlock()) {
			world.spawnParticle(Particle.BLOCK_CRACK, location, 1, 0.1, 0.1, 0.1, 0.001, TNT);
			return;
		}
		
		world.spawnParticle(Particle.SMOKE_LARGE, location, 1, 0.1, 0.1, 0.1, 0.001);
		
		ArrowEntityFused fusedArrow = (ArrowEntityFused) arrow;
		if (fusedArrow.isFuseFinished()) {
			world.createExplosion(location, Math.max(config.getInt("Arrow.Explosive.Effect.ExplosionStrength", 4), 10));
			arrow.getArrow().remove();
		} else {
			fusedArrow.tickFuse();
			world.playSound(location, Sound.ENTITY_CREEPER_HURT, 1, 0.75F + (1 / ((float) fusedArrow.getMaxFuseTicks() / (float) fusedArrow.getFuse())));
		}
	}
	
	@Override
	public boolean onShootFromPlayer(AlchemicalArrowEntity arrow, Player player) {
		return player.hasPermission("arrows.shoot.explosive");
	}
	
	@Override
	public AlchemicalArrowEntity createNewArrow(Arrow arrow) {
		return new ArrowEntityFused(this, arrow, config.getInt("Arrow.Explosive.Effect.FuseTicks", 40));
	}
	
}