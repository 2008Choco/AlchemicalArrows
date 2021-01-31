package wtf.choco.arrows.api.property;

import org.bukkit.util.NumberConversions;

/**
 * Represents an {@link ArrowPropertyValue} with a fixed value.
 *
 * @author Parker Hawke - Choco
 */
public class SimpleArrowProperty implements ArrowPropertyValue {

    private final Object value;

    /**
     * Construct a new arrow property with the given value.
     *
     * @param value the value
     */
    public SimpleArrowProperty(Object value) {
        this.value = value;
    }

    @Override
    public int getAsInt() {
        return NumberConversions.toInt(getValue());
    }

    @Override
    public float getAsFloat() {
        return NumberConversions.toFloat(getValue());
    }

    @Override
    public double getAsDouble() {
        return NumberConversions.toDouble(getValue());
    }

    @Override
    public long getAsLong() {
        return NumberConversions.toLong(getValue());
    }

    @Override
    public short getAsShort() {
        return NumberConversions.toShort(getValue());
    }

    @Override
    public byte getAsByte() {
        return NumberConversions.toByte(getValue());
    }

    @Override
    public boolean getAsBoolean() {
        Object value = getValue();

        if (value instanceof Boolean) {
            return ((Boolean) value).booleanValue();
        }

        if (value instanceof Number) {
            return ((Number) value).intValue() != 0;
        }

        if (value instanceof String) {
            return Boolean.getBoolean((String) value);
        }

        return value != null;
    }

    @Override
    public String getAsString() {
        Object value = getValue();

        if (value == null) {
            return "";
        }

        return value instanceof String ? (String) value : value.toString();
    }

    @Override
    public Object getValue() {
        return value;
    }

}
