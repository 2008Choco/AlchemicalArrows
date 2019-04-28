package wtf.choco.arrows.registry;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.google.common.base.Preconditions;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.api.AlchemicalArrowEntity;

/**
 * Handles the registration of {@link AlchemicalArrow} implementations.
 *
 * @author Parker Hawke - Choco
 */
public final class ArrowRegistry {

    private static final AlchemicalArrows PLUGIN = AlchemicalArrows.getInstance(); // For legacy sake
    private static final String NATIVE_ARROW_PACKAGE = "wtf.choco.arrows.arrow";

    private final Map<String, AlchemicalArrow> arrows = new HashMap<>();

    /**
     * Register a custom AlchemicalArrow implementation. All arrows must be registered here
     * in order to be recognised by AlchemicalArrows when shooting custom arrows. The provided
     * instance must have a unique key ({@link AlchemicalArrow#getKey()}), as well as a
     * unique, non-null ItemStack of type {@link Material#ARROW}.
     *
     * @param arrow the arrow implementation to register
     *
     * @throws IllegalArgumentException If ItemStack parameter is not of type {@link Material#ARROW},
     * or if a registered arrow is already using the ItemStack provided
     *
     * @deprecated see {@link ArrowRegistry#register(AlchemicalArrow)}
     */
    @Deprecated
    public static void registerCustomArrow(@NotNull AlchemicalArrow arrow) {
        PLUGIN.getArrowRegistry().register(arrow);
    }

    /**
     * Unregister an {@link AlchemicalArrow} implementation from the arrow registry. Note that upon
     * unregistration, the arrow will no longer be recognised when attempting to shoot one.
     *
     * @param arrow the arrow to unregister
     *
     * @deprecated see {@link ArrowRegistry#unregister(AlchemicalArrow)}
     */
    @Deprecated
    public static void unregisterCustomArrow(@NotNull AlchemicalArrow arrow) {
        PLUGIN.getArrowRegistry().unregister(arrow);
    }

    /**
     * Unregister an {@link AlchemicalArrow} implementation from the arrow registry based upon its
     * class. Note that upon unregistration, the arrow will no longer be recognised when attempting
     * to shoot one.
     *
     * @param arrowClass the class of the arrow to unregister
     *
     * @deprecated see {@link ArrowRegistry#unregister(Class)}
     */
    @Deprecated
    public static void unregisterCustomArrow(@NotNull Class<? extends AlchemicalArrow> arrowClass) {
        PLUGIN.getArrowRegistry().unregister(arrowClass);
    }

    /**
     * Get the registered instance of the provided AlchemicalArrow class. The registration instance
     * provides various informational methods such as its representing ItemStack as well as a method
     * to construct a new instance of the AlchemicalArrowEntity such that an instance of {@link Arrow}
     * is provided.
     *
     * @param <T> the alchemical arrow class type
     * @param clazz the class of the AlchemicalArrow whose registration to get
     *
     * @return the registered AlchemicalArrow. null if none
     *
     * @deprecated see {@link ArrowRegistry#get(Class)}
     */
    @Deprecated
    @Nullable
    public static <T extends AlchemicalArrow> T getCustomArrow(@NotNull Class<T> clazz) {
        return PLUGIN.getArrowRegistry().get(clazz);
    }

    /**
     * Get the registered instance of an AlchemicalArrow represented by the provided ItemStack. The
     * registration instance provides various informational methods such as its representing ItemStack
     * as well as a method to construct a new instance of the AlchemicalArrowEntity such that an instance
     * of {@link Arrow} is provided.
     *
     * @param arrowItem the item of the AlchemicalArrow instance to retrieve
     *
     * @return the registered AlchemicalArrow represented by the provided ItemStack. null if none
     *
     * @deprecated see {@link ArrowRegistry#get(ItemStack)}
     */
    @Deprecated
    @Nullable
    public static AlchemicalArrow getCustomArrow(@NotNull ItemStack arrowItem) {
        return PLUGIN.getArrowRegistry().get(arrowItem);
    }

    /**
     * Get the registered instance of an AlchemicalArrow according to its registered name. The
     * registration instance provides various informational methods such as its representing ItemStack
     * as well as a method to construct a new instance of the AlchemicalArrowEntity such that an instance
     * of {@link Arrow} is provided.
     *
     * @param key the registered NamespacedKey of the AlchemicalArrow instance to retrieve
     *
     * @return the registered AlchemicalArrow represented by the provided ItemStack. null if none
     *
     * @deprecated see {@link ArrowRegistry#get(String)}
     */
    @Deprecated
    @Nullable
    public static AlchemicalArrow getCustomArrow(@NotNull String key) {
        return PLUGIN.getArrowRegistry().get(key);
    }

    /**
     * Get the registry for all instances of AlchemicalArrows.
     *
     * @return the arrow registration data
     *
     * @deprecated see {@link ArrowRegistry#getRegisteredArrows()}
     */
    @Deprecated
    @NotNull
    public static Set<AlchemicalArrow> getRegisteredCustomArrows() {
        return new HashSet<>(PLUGIN.getArrowRegistry().getRegisteredArrows());
    }

    /**
     * Clear all registry data. This will remove all data previously registered by AlchemicalArrows
     * and by plugins that have registered their own arrows.
     *
     * @deprecated see {@link ArrowRegistry#clear()}
     */
    @Deprecated
    public static void clearRegisteredArrows() {
        PLUGIN.getArrowRegistry().clear();
    }

    /**
     * Register a custom AlchemicalArrow implementation. All arrows must be registered here
     * in order to be recognised by AlchemicalArrows when shooting custom arrows. The provided
     * instance must have a unique, non-null key ({@link AlchemicalArrow#getKey()}), as well as
     * a unique, non-null ItemStack of type {@link Material#ARROW}.
     *
     * @param arrow the arrow implementation to register
     *
     * @throws IllegalArgumentException if arrow, its key or its ItemStack is null, or if the ItemStack
     * is not of type {@link Material#ARROW}
     * @throws IllegalStateException if an arrow with the ID has already been registered, or an arrow
     * with the ItemStack has already been registered
     */
    public void register(@NotNull AlchemicalArrow arrow) {
        Preconditions.checkArgument(arrow != null, "Cannot register null arrow");

        // Validate key
        NamespacedKey key = arrow.getKey();
        Preconditions.checkArgument(key != null, "Arrow key must not be null");
        Preconditions.checkState(!arrows.containsKey(key.toString()));

        // Validate item
        ItemStack arrowItem = arrow.getItem();
        Preconditions.checkArgument(arrowItem != null, "AlchemicalArrow#getItem() must not be null");
        Preconditions.checkArgument(arrowItem.getType() == Material.ARROW, "Result of AlchemicalArrow#getItem() must be of type Material.ARROW");

        for (AlchemicalArrow registeredArrow : arrows.values()) {
            if (registeredArrow.getItem().isSimilar(arrowItem)) {
                throw new IllegalStateException("ItemStack is already being used by class " + registeredArrow.getClass().getName());
            }
        }

        Class<?> arrowClass = arrow.getClass();
        if (!arrowClass.getPackage().getName().startsWith(NATIVE_ARROW_PACKAGE)) {
            PLUGIN.getLogger().info("Successfully registered external arrow (" + arrowClass.getName() + ")");
        }

        this.arrows.put(key.toString(), arrow);
    }

    /**
     * Unregister an {@link AlchemicalArrow} implementation from the arrow registry. Note that upon
     * unregistration, the arrow will no longer be recognised when attempting to shoot one.
     *
     * @param arrow the arrow to unregister
     */
    public void unregister(@NotNull AlchemicalArrow arrow) {
        Preconditions.checkArgument(arrow != null, "Cannot unregister null arrow");
        Preconditions.checkArgument(arrow.getKey() != null, "Cannot unregister arrow with null key");

        this.arrows.remove(arrow.getKey().toString());
    }

    /**
     * Unregister an {@link AlchemicalArrow} implementation from the arrow registry based upon its
     * class. Note that upon unregistration, the arrow will no longer be recognised when attempting
     * to shoot one.
     *
     * @param arrowClass the class of the arrow to unregister
     */
    public void unregister(@NotNull Class<? extends AlchemicalArrow> arrowClass) {
        Iterator<AlchemicalArrow> valueIterator = arrows.values().iterator();

        while (valueIterator.hasNext()) {
            if (valueIterator.next().getClass() == arrowClass) {
                valueIterator.remove();
            }
        }
    }

    /**
     * Get the registered instance of the provided AlchemicalArrow class. The registration instance
     * provides various informational methods such as its representing ItemStack as well as a method
     * to construct a new instance of the AlchemicalArrowEntity such that an instance of {@link Arrow}
     * is provided.
     *
     * @param <T> the alchemical arrow class type
     * @param clazz the class of the AlchemicalArrow whose registration to get
     *
     * @return the registered AlchemicalArrow. null if none
     */
    @Nullable
    public <T extends AlchemicalArrow> T get(@NotNull Class<T> clazz) {
        for (AlchemicalArrow arrow : arrows.values()) {
            if (arrow.getClass() == clazz) {
                return clazz.cast(arrow);
            }
        }

        return null;
    }

    /**
     * Get the registered instance of an AlchemicalArrow represented by the provided ItemStack. The
     * registration instance provides various informational methods such as its representing ItemStack
     * as well as a method to construct a new instance of the AlchemicalArrowEntity such that an instance
     * of {@link Arrow} is provided.
     *
     * @param arrowItem the item of the AlchemicalArrow instance to retrieve
     *
     * @return the registered AlchemicalArrow represented by the provided ItemStack. null if none
     */
    @Nullable
    public AlchemicalArrow get(@NotNull ItemStack arrowItem) {
        for (AlchemicalArrow arrow : arrows.values()) {
            if (arrow.getItem().isSimilar(arrowItem)) {
                return arrow;
            }
        }

        return null;
    }

    @Nullable
    public AlchemicalArrow get(@NotNull NamespacedKey key) {
        Preconditions.checkArgument(key != null, "Cannot get arrow from null key");
        return get(key.toString());
    }

    /**
     * Get the registered instance of an {@link AlchemicalArrow} according to its registered id. The
     * registration instance provides various informational methods such as its representing ItemStack
     * as well as a method to construct a new instance of the AlchemicalArrowEntity such that an instance
     * of {@link Arrow} is provided.
     *
     * @param key the registered NamespacedKey of the AlchemicalArrow instance to retrieve
     *
     * @return the registered AlchemicalArrow represented by the provided key. null if none
     */
    @Nullable
    public AlchemicalArrow get(@NotNull String key) {
        for (AlchemicalArrow arrow : arrows.values()) {
            if (arrow.getKey().toString().equals(key)) {
                return arrow;
            }
        }

        return null;
    }

    /**
     * Get the registry for all instances of AlchemicalArrows.
     *
     * @return the arrow registration data
     */
    @NotNull
    public Collection<AlchemicalArrow> getRegisteredArrows() {
        return Collections.unmodifiableCollection(arrows.values());
    }

    /**
     * Clear all registry data. This will remove all data previously registered by AlchemicalArrows
     * and by plugins that have registered their own arrows.
     */
    public void clear() {
        this.arrows.clear();
    }

    /**
     * Add an AlchemicalArrowEntity to the in-world alchemical arrow tracker. If an AlchemicalArrowEntity
     * has not been added through the use of this method, it will not be recognised by the plugin. Upon
     * invocation of the {@link AlchemicalArrow#createNewArrow(Arrow)} method, this method should be
     * invoked to add the returned instance.
     *
     * @param arrow the arrow to register
     *
     * @deprecated see {@link ArrowStateManager#add(AlchemicalArrowEntity)}
     */
    @Deprecated
    public void addAlchemicalArrow(@NotNull AlchemicalArrowEntity arrow) {
        PLUGIN.getArrowStateManager().add(arrow);
    }

    /**
     * Remove an AlchemicalArrowEntity from the in-world alchemical arrow tracker.
     * <p>
     * <b>NOTE:</b> This will prepare the arrow for deletion, not immediately delete it.
     * The {@link #purgeArrows()} method must be invoked, every tick by the plugin, to
     * remove it from the tracker entirely.
     *
     * @param arrow the arrow to unregister
     *
     * @deprecated see {@link ArrowStateManager#remove(AlchemicalArrowEntity)}
     */
    @Deprecated
    public void removeAlchemicalArrow(@NotNull AlchemicalArrowEntity arrow) {
        PLUGIN.getArrowStateManager().remove(arrow);
    }

    /**
     * Remove an Arrow (subsequently, its related AlchemicalArrowEntity) from the in-world
     * alchemical arrow tracker.
     * <p>
     * <b>NOTE:</b> This will prepare the arrow for deletion, not immediately delete it.
     * The {@link #purgeArrows()} method must be invoked, every tick by the plugin, to
     * remove it from the tracker entirely.
     *
     * @param arrow the Arrow entity to unregister
     *
     * @deprecated see {@link ArrowStateManager#remove(Arrow)}
     */
    @Deprecated
    public void removeAlchemicalArrow(@NotNull Arrow arrow) {
        PLUGIN.getArrowStateManager().remove(arrow);
    }

    /**
     * Remove an Arrow's UUID (subsequently, its related AlchemicalArrowEntity) from the
     * in-world alchemical arrow tracker.
     * <p>
     * <b>NOTE:</b> This will prepare the arrow for deletion, not immediately delete it.
     * The {@link #purgeArrows()} method must be invoked, every tick by the plugin, to
     * remove it from the tracker entirely.
     *
     * @param uuid the UUID of the arrow to unregister
     *
     * @deprecated see {@link ArrowStateManager#remove(Arrow)}
     */
    @Deprecated
    public void removeAlchemicalArrow(@NotNull UUID uuid) {
        Entity entity = Bukkit.getEntity(uuid);
        if (!(entity instanceof Arrow)) return;

        PLUGIN.getArrowStateManager().remove((Arrow) entity);
    }

    /**
     * Get an in-world AlchemicalArrowEntity tracked by the plugin.
     *
     * @param arrow the arrow from which to get an instance
     * @return an instance of the in-world AlchemicalArrowEntity associated with the arrow
     *
     * @deprecated see {@link ArrowStateManager#get(Arrow)}
     */
    @Deprecated
    @Nullable
    public AlchemicalArrowEntity getAlchemicalArrow(@NotNull Arrow arrow) {
        return PLUGIN.getArrowStateManager().get(arrow);
    }

    /**
     * Get an in-world AlchemicalArrowEntity tracked by the plugin.
     *
     * @param uuid the UUID from which to get an instance
     * @return an instance of the in-world AlchemicalArrowEntity associated with the arrow
     *
     * @deprecated see {@link ArrowStateManager#get(UUID)}
     */
    @Deprecated
    @Nullable
    public AlchemicalArrowEntity getAlchemicalArrow(@NotNull UUID uuid) {
        return PLUGIN.getArrowStateManager().get(uuid);
    }

    /**
     * Check whether an Arrow is being tracked as an AlchemicalArrowEntity or not.
     *
     * @param arrow the arrow to check
     *
     * @return true if being tracked, false otherwise
     *
     * @deprecated no longer necessary. returns false;
     */
    @Deprecated
    public boolean isAlchemicalArrow(@NotNull Arrow arrow) {
        return false;
    }

    /**
     * Check whether an Arrow's UUID is being tracked as an AlchemicalArrowEntity or not.
     *
     * @param uuid the UUID to check
     *
     * @return true if being tracked, false otherwise
     *
     * @deprecated no longer necessary. returns false
     */
    @Deprecated
    public boolean isAlchemicalArrow(@NotNull UUID uuid) {
        return false;
    }

    /**
     * Get an immutable Collection of all tracked arrows from the plugin.
     *
     * @return all registered AlchemicalArrowEntities
     *
     * @deprecated see {@link ArrowStateManager#getArrows()} and its various overloads
     */
    @Deprecated
    @NotNull
    public Collection<AlchemicalArrowEntity> getAlchemicalArrows() {
        return PLUGIN.getArrowStateManager().getArrows();
    }

    /**
     * Clear all tracked in-world arrows from the map.
     *
     * @deprecated see {@link ArrowStateManager#clear()}
     */
    @Deprecated
    public void clearAlchemicalArrows() {
        PLUGIN.getArrowStateManager().clear();
    }

    /**
     * Get the amount of arrows awaiting to be purged.
     *
     * @return the amount of arrows to be purged
     *
     * @deprecated no longer supported. returns 0
     */
    @Deprecated
    public int getArrowsToPurge() {
        return 0;
    }

    /**
     * Remove all arrows from the tracker that were prepared for removal.
     *
     * @deprecated no longer supported. Fails silently
     */
    @Deprecated
    public void purgeArrows() { } // No longer used

}