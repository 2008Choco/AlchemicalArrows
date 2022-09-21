package wtf.choco.arrows.util;

public final class NumberUtils {

    private NumberUtils() { }

    public static int toInt(String value) {
        return toInt(value, 0);
    }

    public static int toInt(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static double toDouble(String value) {
        return toDouble(value, 0);
    }

    public static double toDouble(String value, double defaultValue) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static float toFloat(String value) {
        return toFloat(value, 0);
    }

    public static float toFloat(String value, float defaultValue) {
        try {
            return Float.parseFloat(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

}
