package wtf.choco.arrows.arrow;

import java.util.Random;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
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

public class AlchemicalArrowDeath extends AlchemicalArrow {
	
	private static final PotionEffect WITHER_EFFECT = new PotionEffect(PotionEffectType.WITHER, 100, 2);
	private static final Random RANDOM = new Random();
	
	private final ItemStack item;
	private final NamespacedKey key;
	private final FileConfiguration config;
	
	public AlchemicalArrowDeath(AlchemicalArrows plugin) {
		this.config = plugin.getConfig();
		this.key = new NamespacedKey(plugin, "death");
		this.item = ItemBuilder.of(Material.ARROW)
				.name(ChatColor.translateAlternateColorCodes('&', config.getString("Arrow.Death.Item.DisplayName", "&0Death Arrow")))
				.lore(config.getStringList("Arrow.Death.Item.Lore").stream()
						.map(s -> ChatColor.translateAlternateColorCodes('&', s))
						.collect(Collectors.toList())
				).build();
		
		this.properties.setProperty(ArrowProperty.SKELETONS_CAN_SHOOT, config.getBoolean("Arrow.Death.Skeleton.CanShoot", true));
		this.properties.setProperty(ArrowProperty.ALLOW_INFINITY, config.getBoolean("Arrow.Death.AllowInfinity", false));
		this.properties.setProperty(ArrowProperty.SKELETON_LOOT_WEIGHT, config.getDouble("Arrow.Death.Skeleton.LootDropWeight", 10.0));
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
		location.getWorld().spawnParticle(Particle.SMOKE_LARGE, location, 2, 0.1, 0.1, 0.1, 0.01);
	}
	
	@Override
	public void onHitPlayer(AlchemicalArrowEntity arrow, Player player) {
		this.attemptInstantDeath(arrow.getArrow(), player);
		player.addPotionEffect(WITHER_EFFECT);
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_WITHER_SKELETON_DEATH, 1, 0.5F);
	}
	
	@Override
	public void onHitEntity(AlchemicalArrowEntity arrow, Entity entity) {
		if (!(entity instanceof LivingEntity)) return;
		LivingEntity lEntity = (LivingEntity) entity;
		
		this.attemptInstantDeath(arrow.getArrow(), lEntity);
		lEntity.addPotionEffect(WITHER_EFFECT);
		lEntity.getWorld().playSound(lEntity.getLocation(), Sound.ENTITY_WITHER_SKELETON_DEATH, 1, 0.5F);
	}
	
	@Override
	public boolean onShootFromPlayer(AlchemicalArrowEntity arrow, Player player) {
		return player.hasPermission("arrows.shoot.death");
	}
	
	private void attemptInstantDeath(Arrow source, LivingEntity entity) {
		if (!config.getBoolean("Arrow.Death.Effect.InstantDeathPossible", true)) return;
		
		int chance = RANDOM.nextInt(100);
		if (chance > config.getDouble("Arrow.Death.Effect.InstantDeathChance", 20.0)) return;
		
		entity.damage(entity.getHealth(), source);
	}
	
}