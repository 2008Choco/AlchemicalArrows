package wtf.choco.arrows.api.property;

/**
 * Represents a transformable value of an {@link ArrowProperty}.
 *
 * @author Parker Hawke - Choco
 */
public interface ArrowPropertyValue {

    /**
     * Get this value as a primitive int.
     *
     * @return an int
     */
    public int getAsInt();

    /**
     * Get this value as a primitive float.
     *
     * @return a float
     */
    public float getAsFloat();

    /**
     * Get this value as a primitive double.
     *
     * @return a double
     */
    public double getAsDouble();

    /**
     * Get this value as a primitive long.
     *
     * @return a long
     */
    public long getAsLong();

    /**
     * Get this value as a primitive short.
     *
     * @return a short
     */
    public short getAsShort();

    /**
     * Get this value as a primitive byte.
     *
     * @return a byte
     */
    public byte getAsByte();

    /**
     * Get this value as a primitive boolean.
     *
     * @return a boolean
     */
    public boolean getAsBoolean();

    /**
     * Get this value as a String.
     *
     * @return a string
     */
    public String getAsString();

    /**
     * Get this value.
     *
     * @return the value
     */
    public Object getValue();

}
