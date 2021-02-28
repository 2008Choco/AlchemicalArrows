package wtf.choco.arrows.api.property;

import com.google.common.base.Preconditions;

import java.util.Objects;

import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrow;

/**
 * Represents a single-value property held by an {@link AlchemicalArrow}.
 *
 * @author Parker Hawke - Choco
 */
public class ArrowProperty {

    /**
     * A boolean valued property determining whether skeletons may shoot an arrow or not. Defaults to true.
     */
    public static final ArrowProperty SKELETONS_CAN_SHOOT = new ArrowProperty(AlchemicalArrows.key("skeletons_can_shoot"), true);

    /**
     * A boolean valued property determining whether or not the infinity enchantment is permitted.
     */
    public static final ArrowProperty ALLOW_INFINITY = new ArrowProperty(AlchemicalArrows.key("allow_infinity"), false);

    /**
     * A boolean valued property determining whether or not the multishot enchantment is permitted.
     */
    public static final ArrowProperty ALLOW_MULTISHOT = new ArrowProperty(AlchemicalArrows.key("allow_multishot"), true);

    /**
     * A double valued property determining the weighted chance for an alchemical arrow to drop.
     */
    public static final ArrowProperty SKELETON_LOOT_WEIGHT = new ArrowProperty(AlchemicalArrows.key("skeleton_loot_weight"), 10.0);


    private final NamespacedKey key;
    private final Object defaultValue;

    /**
     * Construct a new ArrowProperty given an ID, type and default value.
     *
     * @param key the unique namespaced key
     * @param defaultValue the value to which this property will default if not explicitly set
     */
    public ArrowProperty(@NotNull NamespacedKey key, @Nullable Object defaultValue) {
        Preconditions.checkArgument(key != null, "Property key must not be null");

        this.key = key;
        this.defaultValue = defaultValue;
    }

    /**
     * Get the {@link NamespacedKey} for this property.
     *
     * @return the key
     */
    public NamespacedKey getKey() {
        return key;
    }

    /**
     * Get the value to which this property will default if not explicitly set.
     *
     * @return the default value
     */
    @Nullable
    public Object getDefaultValue() {
        return defaultValue;
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        return object == this || (object instanceof ArrowProperty && Objects.equals(key, ((ArrowProperty) object).key));
    }

}
