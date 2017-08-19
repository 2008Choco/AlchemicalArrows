package me.choco.arrows.events;

import java.util.Collection;
import java.util.Random;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Iterables;

import me.choco.arrows.AlchemicalArrows;
import me.choco.arrows.api.AlchemicalArrow;
import me.choco.arrows.registry.ArrowRegistry;
import me.choco.arrows.utils.ConfigOption;

public class SkeletonKillListener implements Listener {
	
	private static final Random RANDOM = new Random();
	
	private final ArrowRegistry arrowRegistry;
	
	public SkeletonKillListener(AlchemicalArrows plugin) {
		this.arrowRegistry = plugin.getArrowRegistry();
	}
	
	@EventHandler
	public void onKillSkeleton(EntityDeathEvent event) {
		LivingEntity entity = event.getEntity();
		
		if (!(entity instanceof Skeleton)) return;
		if ((RANDOM.nextInt(100) + 1) > ConfigOption.SKELETON_LOOT_PERCENTAGE) return;
		
		event.getDrops().removeIf(i -> i.getType() == Material.ARROW);
		event.getDrops().add(this.getWeightedRandom(RANDOM.nextInt(2) + 1));
	}
	
	public ItemStack getWeightedRandom(int amount) {
		double totalWeight = 0;
		
		Collection<Class<? extends AlchemicalArrow>> arrows = ArrowRegistry.getArrowRegistry().values();
		for (Class<? extends AlchemicalArrow> arrow : arrows) {
			totalWeight += this.arrowRegistry.getInformationalInstance(arrow).skeletonLootWeight();
		}
		
		double randomValue = RANDOM.nextDouble() * totalWeight;
		ItemStack item = null;
		for (int i = 0; i < arrows.size(); i++) {
			Class<? extends AlchemicalArrow> arrow = Iterables.get(arrows, i);
			
			randomValue -= this.arrowRegistry.getInformationalInstance(arrow).skeletonLootWeight();
			if (randomValue <= 0){
				item = ArrowRegistry.getArrowRegistry().inverse().get(arrow);
				break;
			}
		}

		if (item == null) {
			Set<ItemStack> possibleItems = ArrowRegistry.getArrowRegistry().keySet();
			item = Iterables.get(possibleItems, RANDOM.nextInt(possibleItems.size())).clone();
		}
		
		item.setAmount(amount);
		return item;
	}
}