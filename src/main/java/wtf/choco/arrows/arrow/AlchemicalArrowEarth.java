package wtf.choco.arrows.arrow;

import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.api.property.ArrowProperty;
import wtf.choco.arrows.utils.ItemBuilder;

public class AlchemicalArrowEarth extends AlchemicalArrow {
	
	private static final PotionEffect SLOWNESS_EFFECT = new PotionEffect(PotionEffectType.SLOW, 100, 2);
	private static final BlockData DIRT = Material.DIRT.createBlockData();
	
	private final ItemStack item;
	private final NamespacedKey key;
	private final FileConfiguration config;
	
	public AlchemicalArrowEarth(AlchemicalArrows plugin) {
		this.config = plugin.getConfig();
		this.key = new NamespacedKey(plugin, "earth");
		this.item = ItemBuilder.of(Material.ARROW)
				.name(ChatColor.translateAlternateColorCodes('&', config.getString("Arrow.Earth.Item.DisplayName", "&7Earth Arrow")))
				.lore(config.getStringList("Arrow.Earth.Item.Lore").stream()
						.map(s -> ChatColor.translateAlternateColorCodes('&', s))
						.collect(Collectors.toList())
				).build();
		
		this.properties.setProperty(ArrowProperty.SKELETONS_CAN_SHOOT, config.getBoolean("Arrow.Earth.Skeleton.CanShoot", true));
		this.properties.setProperty(ArrowProperty.ALLOW_INFINITY, config.getBoolean("Arrow.Earth.AllowInfinity", false));
		this.properties.setProperty(ArrowProperty.SKELETON_LOOT_WEIGHT, config.getDouble("Arrow.Earth.Skeleton.LootDropWeight", 10.0));
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
		location.getWorld().spawnParticle(Particle.BLOCK_DUST, location, 1, 0.1, 0.1, 0.1, 0.1, DIRT);
	}
	
	@Override
	public void onHitPlayer(AlchemicalArrowEntity arrow, Player player) {
		this.burryEntity(player);
	}
	
	@Override
	public void onHitEntity(AlchemicalArrowEntity arrow, Entity entity) {
		if (!(entity instanceof LivingEntity)) return;
		this.burryEntity((LivingEntity) entity);
	}
	
	@Override
	public boolean onShootFromPlayer(AlchemicalArrowEntity arrow, Player player) {
		return player.hasPermission("arrows.shoot.earth");
	}
	
	private void burryEntity(LivingEntity entity) {
		Location location = entity.getLocation();
		while (location.getBlockY() >= 1 && !location.getBlock().getType().isSolid()) {
			location.subtract(0, 1, 0);
		}
		
		// Round to block coordinate and add 0.5 (centre coordinates)
		location.setX(location.getBlockX() + 0.5);
		location.setZ(location.getBlockZ() + 0.5);
		entity.teleport(location);
		
		entity.getWorld().playSound(entity.getLocation(), Sound.BLOCK_GRASS_BREAK, 1, 0.75F);
		entity.addPotionEffect(SLOWNESS_EFFECT);
	}
	
}