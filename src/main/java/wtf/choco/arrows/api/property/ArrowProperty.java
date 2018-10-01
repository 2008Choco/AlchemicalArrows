package wtf.choco.arrows.api.property;

import java.util.Objects;

import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrow;

/**
 * Represents a single-value property held by an {@link AlchemicalArrow}
 * 
 * @author Parker Hawke - 2008Choco
 * 
 * @param <T> the value type for this property
 */
public final class ArrowProperty<T> implements Keyed {
	
	/**
	 * A boolean valued property determining whether skeletons may shoot an arrow or not. Defaults to true
	 */
	public static final ArrowProperty<Boolean> SKELETONS_CAN_SHOOT = new ArrowProperty<>(new NamespacedKey(AlchemicalArrows.getInstance(), "skeletons_can_shoot"), Boolean.class, true);
	
	/**
	 * A boolean valued property determining whether the infinity enchantment is permitted or not
	 */
	public static final ArrowProperty<Boolean> ALLOW_INFINITY = new ArrowProperty<>(new NamespacedKey(AlchemicalArrows.getInstance(), "allow_infinity"), Boolean.class, false);
	
	/**
	 * A double valued property determining the weighted chance for an alchemical arrow to drop
	 */
	public static final ArrowProperty<Double> SKELETON_LOOT_WEIGHT = new ArrowProperty<>(new NamespacedKey(AlchemicalArrows.getInstance(), "skeleton_loot_weight"), Double.class, 10.0);
	
	
	private final NamespacedKey key;
	private final Class<T> type;
	private final T defaultValue;
	
	/**
	 * Construct a new ArrowProperty given an ID, type and default value
	 * 
	 * @param key the unique namespaced key
	 * @param type the type of value
	 * @param defaultValue the value to which this property will default if not explicitly set
	 */
	public ArrowProperty(NamespacedKey key, Class<T> type, T defaultValue) {
		this.key = key;
		this.type = type;
		this.defaultValue = defaultValue;
	}
	
	@Override
	public NamespacedKey getKey() {
		return key;
	}
	
	/**
	 * Get the class representing the value type held by this property
	 * 
	 * @return the value type
	 */
	public Class<T> getType() {
		return type;
	}
	
	/**
	 * Get the value to which this property will default if not explicitly set
	 * 
	 * @return the default value
	 */
	public T getDefaultValue() {
		return defaultValue;
	}
	
	@Override
	public int hashCode() {
		return 31 * (key == null ? 0 : key.hashCode());
	}
	
	@Override
	public boolean equals(Object object) {
		return object == this || (object instanceof ArrowProperty<?> && Objects.equals(key, ((ArrowProperty<?>) object).key));
	}
	
}