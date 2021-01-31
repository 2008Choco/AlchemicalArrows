package wtf.choco.arrows.api.property;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A map backed by a HashMap to associate a property to its set value. If no value has been
 * explicitly set for a property, its default value will be returned.
 *
 * @author Parker Hawke - Choco
 */
public final class PropertyMap {

    private final Map<ArrowProperty, ArrowPropertyValue> properties = new HashMap<>();

    /**
     * Set a property's value that is to be recomputed every time it is fetched.
     *
     * @param property the property whose value to set
     * @param value the value to set
     */
    public void setProperty(@NotNull ArrowProperty property, @NotNull Supplier<Object> value) {
        this.properties.put(property, new DynamicArrowPropertyValue(value));
    }

    /**
     * Set a property's value.
     *
     * @param property the property whose value to set
     * @param value the value to set
     */
    public void setProperty(@NotNull ArrowProperty property, @Nullable Object value) {
        this.properties.put(property, new SimpleArrowProperty(value));
    }

    /**
     * Remove a property and its value.
     *
     * @param property the property to remove
     */
    public void removeProperty(@NotNull ArrowProperty property) {
        this.properties.remove(property);
    }

    /**
     * Check whether the specified property has been explicitly set or not.
     *
     * @param property the property to check
     *
     * @return true if set, false otherwise
     */
    public boolean hasProperty(@NotNull ArrowProperty property) {
        return properties.containsKey(property);
    }

    /**
     * Get the value of the specified property. If the property has not yet
     * been set explicitly, the specified default value will instead be returned.
     *
     * @param property the property whose value to get
     * @param defaultValue the default value to return if not explicitly set
     *
     * @return the property's value
     */
    @NotNull
    public ArrowPropertyValue getProperty(@NotNull ArrowProperty property, @Nullable Object defaultValue) {
        return properties.getOrDefault(property, new SimpleArrowProperty(defaultValue));
    }

    /**
     * Get the value of the specified property. If the property has not yet
     * been set explicitly, the property's default value will be returned instead.
     *
     * @param property the property whose value to get
     *
     * @return the property's value
     */
    @NotNull
    public ArrowPropertyValue getProperty(@NotNull ArrowProperty property) {
        return getProperty(property, property.getDefaultValue());
    }

    /**
     * Get an immutable set of all properties that have been explicitly set.
     *
     * @return all set properties
     */
    @NotNull
    public Set<ArrowProperty> getProperties() {
        return Collections.unmodifiableSet(properties.keySet());
    }

    /**
     * Clear all properties and their values from the map.
     */
    public void clearProperties() {
        this.properties.clear();
    }

}
