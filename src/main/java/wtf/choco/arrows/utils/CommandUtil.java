package wtf.choco.arrows.utils;

import org.apache.commons.lang.StringUtils;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Miscellaneous utility methods used in the AlchemicalArrows commands.
 *
 * @author Parker Hawke - Choco
 */
public final class CommandUtil {

    private CommandUtil() { }

    /**
     * Convert an argument to a namespaced key argument. The possible outcomes of this method
     * are as follows:
     * <ul>
     *   <li>"" or null {@literal ->} null
     *   <li>":arg" {@literal ->} null
     *   <li>"namespace:arg:erroneous_value" {@literal ->} null
     *   <li>"arg" {@literal ->} "pluginName:arg"
     *   <li>"namespace:arg" {@literal ->} "namespace:arg" (unchanged)
     * </ul>
     * null will only be returned if the argument is invalid. The {@code plugin}'s name
     * (i.e. {@link Plugin#getName()}) will be used as the default namespace if none is
     * provided by the argument.
     *
     * @param arg the arg to namespaceify
     * @param plugin the plugin whose namespace to use
     *
     * @return the namespaced argument
     */
    @Nullable
    public static String argToNamespace(@Nullable String arg, @NotNull Plugin plugin) {
        return argToNamespace(arg, plugin.getName());
    }

    /**
     * Convert an argument to a namespaced key argument. The possible outcomes of this method
     * are as follows:
     * <ul>
     *   <li>"" or null {@literal ->} null
     *   <li>":arg" {@literal ->} null
     *   <li>"namespace:arg:erroneous_value" {@literal ->} null
     *   <li>"arg" {@literal ->} "pluginName:arg"
     *   <li>"namespace:arg" {@literal ->} "namespace:arg" (unchanged)
     * </ul>
     * null will only be returned if the argument is invalid.
     *
     * @param arg the arg to namespaceify
     * @param defaultNamespace the default namespace to use if none is provided by the
     * argument
     *
     * @return the namespaced argument
     *
     * @see #argToNamespace(String, Plugin)
     */
    @Nullable
    public static String argToNamespace(@Nullable String arg, @NotNull String defaultNamespace) {
        if (arg == null || StringUtils.isEmpty(arg)) {
            return null;
        }

        if (!arg.contains(":")) {
            return defaultNamespace.toLowerCase() + ":" + arg.toLowerCase();
        }

        return (arg.startsWith(":") || arg.split(":", 3).length > 2) ? null : arg;
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