package wtf.choco.arrows.api.property;

import com.google.common.base.Preconditions;

import java.util.Objects;

import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrow;

/**
 * Represents a single-value property held by an {@link AlchemicalArrow}.
 *
 * @author Parker Hawke - Choco
 *
 * @param <T> the value type for this property
 */
public final class ArrowProperty<T> implements Keyed {

    /**
     * A boolean valued property determining whether skeletons may shoot an arrow or not. Defaults to true.
     */
    public static final ArrowProperty<Boolean> SKELETONS_CAN_SHOOT = new ArrowProperty<>(key("skeletons_can_shoot"), Boolean.class, true);

    /**
     * A boolean valued property determining whether the infinity enchantment is permitted or not.
     */
    public static final ArrowProperty<Boolean> ALLOW_INFINITY = new ArrowProperty<>(key("allow_infinity"), Boolean.class, false);

    /**
     * A double valued property determining the weighted chance for an alchemical arrow to drop.
     */
    public static final ArrowProperty<Double> SKELETON_LOOT_WEIGHT = new ArrowProperty<>(key("skeleton_loot_weight"), Double.class, 10.0);


    private final NamespacedKey key;
    private final Class<T> type;
    private final T defaultValue;

    /**
     * Construct a new ArrowProperty given an ID, type and default value.
     *
     * @param key the unique namespaced key
     * @param type the type of value
     * @param defaultValue the value to which this property will default if not explicitly set
     */
    public ArrowProperty(@NotNull NamespacedKey key, @NotNull Class<T> type, @Nullable T defaultValue) {
        Preconditions.checkArgument(key != null, "Property key must not be null");
        Preconditions.checkArgument(type != null, "Type must not be null");

        this.key = key;
        this.type = type;
        this.defaultValue = defaultValue;
    }

    @Override
    public NamespacedKey getKey() {
        return key;
    }

    /**
     * Get the class representing the value type held by this property.
     *
     * @return the value type
     */
    @NotNull
    public Class<T> getType() {
        return type;
    }

    /**
     * Get the value to which this property will default if not explicitly set.
     *
     * @return the default value
     */
    @Nullable
    public T getDefaultValue() {
        return defaultValue;
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        return object == this || (object instanceof ArrowProperty<?> && Objects.equals(key, ((ArrowProperty<?>) object).key));
    }

    private static NamespacedKey key(String value) {
        return new NamespacedKey(AlchemicalArrows.getInstance(), value);
    }

}
