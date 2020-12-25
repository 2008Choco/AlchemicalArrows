package wtf.choco.arrows.util;

/**
 * Miscellaneous math utility methods.
 *
 * @author Parker Hawke - Choco
 */
public final class MathUtil {

    private MathUtil() { }

    /**
     * Clamp a value between a minimum and maximum value. If the value exceeds the
     * specified bounds, it will be limited to its exceeding bound.
     *
     * @param value the value to clamp
     * @param min the minimum allowed value
     * @param max the maximum allowed value
     *
     * @return the clamped value. Itself if the boundaries were not exceeded
     */
    public static int clamp(int value, int min, int max) {
        return (value < min ? min : (value > max ? max : value));
    }

    /**
     * Clamp a value between a minimum and maximum value. If the value exceeds the
     * specified bounds, it will be limited to its exceeding bound.
     *
     * @param value the value to clamp
     * @param min the minimum allowed value
     * @param max the maximum allowed value
     *
     * @return the clamped value. Itself if the boundaries were not exceeded
     */
    public static float clamp(float value, float min, float max) {
        return (value < min ? min : (value > max ? max : value));
    }

}