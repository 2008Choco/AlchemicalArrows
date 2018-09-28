package wtf.choco.arrows.registry;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.inventory.ItemStack;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.api.AlchemicalArrowEntity;

/**
 * A registration class that holds information about {@link AlchemicalArrow} implementations
 * as well as their in-world representations, {@link AlchemicalArrowEntity}
 * 
 * @author Parker Hawke - 2008Choco
 */
public final class ArrowRegistry {

	private static final Set<AlchemicalArrow> ARROW_REGISTRY = new HashSet<>();
	
	// Keeps track of all arrows currently active in the world
	private final Map<UUID, AlchemicalArrowEntity> inWorld = new HashMap<>();
	private final Set<UUID> arrowsToPurge = new HashSet<>();
	
	/**
	 * Register a custom AlchemicalArrow implementation. All arrows must be registered here
	 * in order to be recognised by AlchemicalArrows when shooting custom arrows. The provided
	 * instance must have a unique key ({@link AlchemicalArrow#getKey()}), as well as a
	 * unique, non-null ItemStack of type {@link Material#ARROW}
	 * 
	 * @param arrow the arrow implementation to register
	 * 
	 * @throws IllegalArgumentException If ItemStack parameter is not of type {@link Material#ARROW},
	 * or if a registered arrow is already using the ItemStack provided
	 */
	public static void registerCustomArrow(AlchemicalArrow arrow) {
		ItemStack arrowItem = arrow.getItem();
		if (arrowItem == null || arrowItem.getType() != Material.ARROW) {
			throw new IllegalArgumentException("AlchemicalArrow ItemStacks must be of type Material#ARROW. Given " + (arrowItem != null ? arrowItem.getType() : "null"));
		}
		
		for (AlchemicalArrow registeredArrow : ARROW_REGISTRY) {
			if (registeredArrow.getItem().isSimilar(arrowItem)) {
				throw new IllegalArgumentException("ItemStack is already being used by class " + registeredArrow.getClass().getName());
			}
			else if (registeredArrow.getKey().equals(arrow.getKey())) {
				throw new IllegalArgumentException("Arrow key \"" + arrow.getKey() + "\" already in use by class " + registeredArrow.getClass().getName());
			}
		}
		
		Class<?> arrowClass = arrow.getClass();
		if (!arrowClass.getPackage().getName().startsWith("wtf.choco.arrows.arrow")) {
			AlchemicalArrows.getInstance().getLogger().info("Successfully registered external arrow (" + arrowClass.getName() + ")");
		}
		
		ARROW_REGISTRY.add(arrow);
	}
	
	/**
	 * Unregister an {@link AlchemicalArrow} implementation from the arrow registry. Note that upon
	 * unregistration, the arrow will no longer be recognised when attempting to shoot one
	 * 
	 * @param arrow the arrow to unregister
	 */
	public static void unregisterCustomArrow(AlchemicalArrow arrow) {
		ARROW_REGISTRY.remove(arrow);
	}
	
	/**
	 * Unregister an {@link AlchemicalArrow} implementation from the arrow registry based upon its
	 * class. Note that upon unregistration, the arrow will no longer be recognised when attempting
	 * to shoot one
	 * 
	 * @param arrowClass the class of the arrow to unregister
	 */
	public static void unregisterCustomArrow(Class<? extends AlchemicalArrow> arrowClass) {
		ARROW_REGISTRY.removeIf(a -> a.getClass().equals(arrowClass));
	}
	
	/**
	 * Get the registered instance of the provided AlchemicalArrow class. The registration instance
	 * provides various informational methods such as its representing ItemStack as well as a method
	 * to construct a new instance of the AlchemicalArrowEntity such that an instance of {@link Arrow}
	 * is provided
	 * 
	 * @param clazz the class of the AlchemicalArrow whose registration to get
	 * 
	 * @param <T> the alchemical arrow class type
	 * 
	 * @return the registered AlchemicalArrow. null if none
	 */
	public static <T extends AlchemicalArrow> T getCustomArrow(Class<T> clazz) {
		for (AlchemicalArrow arrow : ARROW_REGISTRY)
			if (arrow.getClass() == clazz) return clazz.cast(arrow);
		return null;
	}
	
	/**
	 * Get the registered instance of an AlchemicalArrow represented by the provided ItemStack. The
	 * registration instance provides various informational methods such as its representing ItemStack
	 * as well as a method to construct a new instance of the AlchemicalArrowEntity such that an instance
	 * of {@link Arrow} is provided
	 * 
	 * @param arrowItem the item of the AlchemicalArrow instance to retrieve
	 * @return the registered AlchemicalArrow represented by the provided ItemStack. null if none
	 */
	public static AlchemicalArrow getCustomArrow(ItemStack arrowItem) {
		for (AlchemicalArrow arrow : ARROW_REGISTRY)
			if (arrow.getItem().isSimilar(arrowItem)) return arrow;
		return null;
	}
	
	/**
	 * Get the registered instance of an AlchemicalArrow according to its registered name. The
	 * registration instance provides various informational methods such as its representing ItemStack
	 * as well as a method to construct a new instance of the AlchemicalArrowEntity such that an instance
	 * of {@link Arrow} is provided
	 * 
	 * @param key the registered NamespacedKey of the AlchemicalArrow instance to retrieve
	 * @return the registered AlchemicalArrow represented by the provided ItemStack. null if none
	 */
	public static AlchemicalArrow getCustomArrow(String key) {
		for (AlchemicalArrow arrow : ARROW_REGISTRY)
			if (arrow.getKey().toString().equals(key)) return arrow;
		return null;
	}
	
	/**
	 * Get the registry for all instances of AlchemicalArrows
	 * 
	 * @return the arrow registration data
	 */
	public static Set<AlchemicalArrow> getRegisteredCustomArrows() {
		return Collections.unmodifiableSet(ARROW_REGISTRY);
	}
	
	/**
	 * Clear all registry data. This will remove all data previously registered by AlchemicalArrows
	 * and by plugins that have registered their own arrows
	 */
	public static void clearRegisteredArrows() {
		ARROW_REGISTRY.clear();
	}
	
	/**
	 * Add an AlchemicalArrowEntity to the in-world alchemical arrow tracker. If an AlchemicalArrowEntity
	 * has not been added through the use of this method, it will not be recognised by the plugin. Upon
	 * invocation of the {@link AlchemicalArrow#createNewArrow(Arrow)} method, this method should be
	 * invoked to add the returned instance
	 * 
	 * @param arrow the arrow to register
	 */
	public void addAlchemicalArrow(AlchemicalArrowEntity arrow) {
		this.inWorld.put(arrow.getArrow().getUniqueId(), arrow);
	}
	
	/**
	 * Remove an AlchemicalArrowEntity from the in-world alchemical arrow tracker
	 * <p>
	 * <b>NOTE:</b> This will prepare the arrow for deletion, not immediately delete it.
	 * The {@link #purgeArrows()} method must be invoked, every tick by the plugin, to
	 * remove it from the tracker entirely.
	 * 
	 * @param arrow the arrow to unregister
	 */
	public void removeAlchemicalArrow(AlchemicalArrowEntity arrow) {
		this.arrowsToPurge.add(arrow.getArrow().getUniqueId());
	}
	
	/**
	 * Remove an Arrow (subsequently, its related AlchemicalArrowEntity) from the in-world
	 * alchemical arrow tracker
	 * <p>
	 * <b>NOTE:</b> This will prepare the arrow for deletion, not immediately delete it.
	 * The {@link #purgeArrows()} method must be invoked, every tick by the plugin, to
	 * remove it from the tracker entirely.
	 * 
	 * @param arrow the Arrow entity to unregister
	 */
	public void removeAlchemicalArrow(Arrow arrow) {
		this.arrowsToPurge.add(arrow.getUniqueId());
	}
	
	/**
	 * Remove an Arrow's UUID (subsequently, its related AlchemicalArrowEntity) from the
	 * in-world alchemical arrow tracker
	 * <p>
	 * <b>NOTE:</b> This will prepare the arrow for deletion, not immediately delete it.
	 * The {@link #purgeArrows()} method must be invoked, every tick by the plugin, to
	 * remove it from the tracker entirely.
	 * 
	 * @param uuid the UUID of the arrow to unregister
	 */
	public void removeAlchemicalArrow(UUID uuid) {
		this.arrowsToPurge.add(uuid);
	}
	
	/**
	 * Get an in-world AlchemicalArrowEntity tracked by the plugin
	 * 
	 * @param arrow the arrow from which to get an instance
	 * @return an instance of the in-world AlchemicalArrowEntity associated with the arrow
	 */
	public AlchemicalArrowEntity getAlchemicalArrow(Arrow arrow) {
		return (arrow != null) ? getAlchemicalArrow(arrow.getUniqueId()) : null;
	}
	
	/**
	 * Get an in-world AlchemicalArrowEntity tracked by the plugin
	 * 
	 * @param uuid the UUID from which to get an instance
	 * @return an instance of the in-world AlchemicalArrowEntity associated with the arrow
	 */
	public AlchemicalArrowEntity getAlchemicalArrow(UUID uuid) {
		return (uuid != null) ? inWorld.get(uuid) : null;
	}
	
	/**
	 * Check whether an Arrow is being tracked as an AlchemicalArrowEntity or not
	 * 
	 * @param arrow the arrow to check
	 * @return true if being tracked, false otherwise
	 */
	public boolean isAlchemicalArrow(Arrow arrow) {
		return arrow != null && inWorld.containsKey(arrow.getUniqueId());
	}
	
	/**
	 * Check whether an Arrow's UUID is being tracked as an AlchemicalArrowEntity or not
	 * 
	 * @param uuid the UUID to check
	 * @return true if being tracked, false otherwise
	 */
	public boolean isAlchemicalArrow(UUID uuid) {
		return inWorld.containsKey(uuid);
	}
	
	/**
	 * Get an immutable Collection of all tracked arrows from the plugin
	 * 
	 * @return all registered AlchemicalArrowEntities
	 */
	public Collection<AlchemicalArrowEntity> getAlchemicalArrows() {
		return Collections.unmodifiableCollection(inWorld.values());
	}
	
	/**
	 * Clear all tracked in-world arrows from the map
	 */
	public void clearAlchemicalArrows() {
		this.inWorld.clear();
	}
	
	/**
	 * Get the amount of arrows awaiting to be purged
	 * 
	 * @return the amount of arrows to be purged
	 */
	public int getArrowsToPurge() {
		return arrowsToPurge.size();
	}
	
	/**
	 * Remove all arrows from the tracker that were prepared for removal
	 */
	public void purgeArrows() {
		this.arrowsToPurge.forEach(inWorld::remove);
		this.arrowsToPurge.clear();
	}
	
}