package wtf.choco.arrows.api.property;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A map backed by a HashMap to associate a property to its set value. If no value has been
 * explicitly set for a property, its default value will be returned
 * 
 * @author Parker Hawke - 2008Choco
 */
public final class PropertyMap {
	
	private final Map<ArrowProperty<?>, Object> properties = new HashMap<>();
	
	/**
	 * Set a property's value
	 * 
	 * @param property the property whose value to set
	 * @param value the value to set
	 * 
	 * @param <T> the arrow property value type
	 */
	public <T> void setProperty(ArrowProperty<T> property, T value) {
		this.properties.put(property, value);
	}
	
	/**
	 * Remove a property and its value
	 * 
	 * @param property the property to remove
	 */
	public void removeProperty(ArrowProperty<?> property) {
		this.properties.remove(property);
	}
	
	/**
	 * Check whether the specified property has been explicitly set or not
	 * 
	 * @param property the property to check
	 * @return true if set, false otherwise
	 */
	public boolean hasProperty(ArrowProperty<?> property) {
		return properties.containsKey(property);
	}
	
	/**
	 * Get the value of the specified property. If the property has not yet
	 * been set explicitly, the specified default value will instead be returned
	 * 
	 * @param property the property whose value to get
	 * @param defaultValue the default value to return if not explicitly set
	 * 
	 * @param <T> the arrow property value type
	 * 
	 * @return the property's value
	 */
	public <T> T getPropertyValue(ArrowProperty<T> property, T defaultValue) {
		return property.getType().cast(properties.getOrDefault(property, defaultValue));
	}
	
	/**
	 * Get the value of the specified property. If the property has not yet
	 * been set explicitly, the property's default value will be returned instead
	 * 
	 * @param property the property whose value to get
	 * 
	 * @param <T> the arrow property value type
	 * 
	 * @return the property's value
	 */
	public <T> T getPropertyValue(ArrowProperty<T> property) {
		return getPropertyValue(property, property.getDefaultValue());
	}
	
	/**
	 * Get an immutable set of all properties that have been explicitly set
	 * 
	 * @return all set properties
	 */
	public Set<ArrowProperty<?>> getProperties() {
		return Collections.unmodifiableSet(properties.keySet());
	}
	
	/**
	 * Clear all properties and their values from the map
	 */
	public void clearProperties() {
		this.properties.clear();
	}
	
}