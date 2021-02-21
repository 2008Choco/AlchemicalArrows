package wtf.choco.arrows.util;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

import org.bukkit.NamespacedKey;
import org.bukkit.permissions.Permissible;

import wtf.choco.arrows.AlchemicalArrows;

/**
 * Various constants for the AlchemicalArrows plugin. Non-documented but field names should
 * be self-descriptive.
 *
 * @author Parker Hawke - Choco
 */
public final class AAConstants {

    // Configuration options
    public static final String CONFIG_CHECK_FOR_UPDATES = "CheckForUpdates";
    public static final String CONFIG_METRICS_ENABLED = "MetricsEnabled";

    public static final String CONFIG_DEATH_MESSAGES_ENABLED = "DeathMessages.Enabled";
    public static final String CONFIG_DEATH_MESSAGES_DEATH_BY_PLAYER = "DeathMessages.DeathByPlayer";
    public static final String CONFIG_DEATH_MESSAGES_DEATH_BY_SKELETON = "DeathMessages.DeathBySkeleton";
    public static final String CONFIG_DEATH_MESSAGES_DEATH_BY_BLOCK_SOURCE = "DeathMessages.DeathByBlockSource";

    public static final String CONFIG_SKELETONS_SHOOT_PERCENTAGE = "Skeletons.ShootPercentage";
    public static final String CONFIG_SKELETONS_LOOT_DROP_CHANCE = "Skeletons.LootDropChance";

    public static final String CONFIG_CRAFTING_VANILLA_CRAFTING = "Crafting.VanillaCrafting";
    public static final String CONFIG_CRAFTING_ALCHEMA_INTEGRATION_ENABLED = "Crafting.AlchemaIntegration.Enabled";
    public static final String CONFIG_CRAFTING_ALCHEMA_INTEGRATION_ARROWS_REQUIRED = "Crafting.AlchemaIntegration.ArrowsRequired";
    public static final String CONFIG_CRAFTING_ALCHEMA_INTEGRATION_RECIPE_YIELD = "Crafting.AlchemaIntegration.RecipeYield";

    public static final String CONFIG_INVENTORY_CROSSBOW_LOADING = "Inventory.CrossbowLoading";


    // Permission nodes
    public static final String PERMISSION_WORLDGUARD_OVERRIDE = "alchemicalarrows.worldguard.override";

    public static final String PERMISSION_SHOOT_ARROW = "alchemicalarrows.shoot.%s";
    public static final String PERMISSION_CRAFT_ARROW = "alchemicalarrows.craft.%s";

    public static final String PERMISSION_COMMAND_CLEAR = "alchemicalarrows.command.clear";
    public static final String PERMISSION_COMMAND_RELOAD = "alchemicalarrows.command.reload";
    public static final String PERMISSION_COMMAND_GIVEARROW = "alchemicalarrows.command.givearrow";
    public static final String PERMISSION_COMMAND_SUMMONARROW = "alchemicalarrows.command.summonarrow";

    // Legacy permission nodes
    public static final String PERMISSION_WORLDGUARD_OVERRIDE_LEGACY = "arrows.worldguardoverride";

    public static final String PERMISSION_SHOOT_ARROW_LEGACY = "arrows.shoot.%s";
    public static final String PERMISSION_CRAFT_ARROW_LEGACY = "arrows.craft.%s";

    public static final String PERMISSION_COMMAND_CLEAR_LEGACY = "arrows.command.clear";
    public static final String PERMISSION_COMMAND_RELOAD_LEGACY = "arrows.command.reload";
    public static final String PERMISSION_COMMAND_GIVEARROW_LEGACY = "arrows.command.givearrow";
    public static final String PERMISSION_COMMAND_SUMMONARROW_LEGACY = "arrows.command.summonarrow";

    // Utility permission predicates (for legacy support)
    public static final Predicate<Permissible> PERMISSION_WORLDGUARD_OVERRIDE_PREDICATE = simple(PERMISSION_WORLDGUARD_OVERRIDE, PERMISSION_WORLDGUARD_OVERRIDE_LEGACY);

    public static final BiPredicate<Permissible, String> PERMISSION_SHOOT_ARROW_PREDICATE = complex(PERMISSION_SHOOT_ARROW, PERMISSION_SHOOT_ARROW_LEGACY);
    public static final BiPredicate<Permissible, String> PERMISSION_CRAFT_ARROW_PREDICATE = complex(PERMISSION_CRAFT_ARROW, PERMISSION_CRAFT_ARROW_LEGACY);

    public static final Predicate<Permissible> PERMISSION_COMMAND_CLEAR_PREDICATE = simple(PERMISSION_COMMAND_CLEAR, PERMISSION_COMMAND_CLEAR_LEGACY);
    public static final Predicate<Permissible> PERMISSION_COMMAND_RELOAD_PREDICATE = simple(PERMISSION_COMMAND_RELOAD, PERMISSION_COMMAND_RELOAD_LEGACY);
    public static final Predicate<Permissible> PERMISSION_COMMAND_GIVEARROW_PREDICATE = simple(PERMISSION_COMMAND_GIVEARROW, PERMISSION_COMMAND_GIVEARROW_LEGACY);
    public static final Predicate<Permissible> PERMISSION_COMMAND_SUMMONARROW_PREDICATE = simple(PERMISSION_COMMAND_SUMMONARROW, PERMISSION_COMMAND_SUMMONARROW_LEGACY);


    // Persistent data keys
    public static final NamespacedKey KEY_TYPE = AlchemicalArrows.key("type");

    private AAConstants() { }

    private static Predicate<Permissible> simple(String first, String second) {
        return permissible -> permissible.hasPermission(first) || permissible.hasPermission(second);
    }

    private static BiPredicate<Permissible, String> complex(String first, String second) {
        return (permissible, input) -> permissible.hasPermission(String.format(first, input)) || permissible.hasPermission(String.format(second, input));
    }

}
