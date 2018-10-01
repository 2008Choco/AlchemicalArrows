package wtf.choco.arrows.arrow;

import java.util.Iterator;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.api.AlchemicalArrowEntity;
import wtf.choco.arrows.api.property.ArrowProperty;
import wtf.choco.arrows.utils.ItemBuilder;

public class AlchemicalArrowNecrotic extends AlchemicalArrow {
	
	private static final ItemStack ROTTEN_FLESH = new ItemStack(Material.ROTTEN_FLESH);
	
	private final ItemStack item;
	private final NamespacedKey key;
	private final FileConfiguration config;
	
	public AlchemicalArrowNecrotic(AlchemicalArrows plugin) {
		this.config = plugin.getConfig();
		this.key = new NamespacedKey(plugin, "necrotic");
		this.item = new ItemBuilder(Material.ARROW)
				.setName(ChatColor.translateAlternateColorCodes('&', config.getString("Arrow.Necrotic.Item.DisplayName", "&2Necrotic Arrow")))
				.setLore(config.getStringList("Arrow.Necrotic.Item.Lore").stream()
						.map(s -> ChatColor.translateAlternateColorCodes('&', s))
						.collect(Collectors.toList())
				).build();
		
		this.properties.setProperty(ArrowProperty.SKELETONS_CAN_SHOOT, config.getBoolean("Arrow.Necrotic.Skeleton.CanShoot", true));
		this.properties.setProperty(ArrowProperty.ALLOW_INFINITY, config.getBoolean("Arrow.Necrotic.AllowInfinity", false));
		this.properties.setProperty(ArrowProperty.SKELETON_LOOT_WEIGHT, config.getDouble("Arrow.Necrotic.Skeleton.LootDropWeight", 10.0));
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
		location.getWorld().spawnParticle(Particle.ITEM_CRACK, location, 2, 0.1, 0.1, 0.1, 0.1, ROTTEN_FLESH);
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
	
	@Override
	public boolean onShootFromPlayer(AlchemicalArrowEntity arrow, Player player) {
		return player.hasPermission("arrows.shoot.necrotic");
	}
	
	private void lifeSap(Entity shooter, double damage) {
		if (!(shooter instanceof LivingEntity)) return;
		
		LivingEntity source = (LivingEntity) shooter;
		source.setHealth(Math.max(source.getHealth() + (damage / 2), source.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()));
	}
	
}