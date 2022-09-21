package wtf.choco.arrows.util;

import org.bukkit.NamespacedKey;
import org.bukkit.permissions.Permissible;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrow;

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

    private static final String PERMISSION_SHOOT_ARROW = "alchemicalarrows.shoot.%s"; // See #hasPermissionShootArrow()
    private static final String PERMISSION_CRAFT_ARROW = "alchemicalarrows.craft.%s"; // See #hasPermissionCraftArrow()

    public static final String PERMISSION_COMMAND_CLEAR = "alchemicalarrows.command.clear";
    public static final String PERMISSION_COMMAND_RELOAD = "alchemicalarrows.command.reload";
    public static final String PERMISSION_COMMAND_GIVEARROW = "alchemicalarrows.command.givearrow";
    public static final String PERMISSION_COMMAND_SUMMONARROW = "alchemicalarrows.command.summonarrow";

    // Persistent data keys
    public static final NamespacedKey KEY_TYPE = AlchemicalArrows.key("type");

    private AAConstants() { }

    public static boolean hasPermissionShootArrow(Permissible permissible, AlchemicalArrow arrow) {
        return permissible.hasPermission(PERMISSION_SHOOT_ARROW.formatted(arrow.getKey().getKey()));
    }

    public static boolean hasPermissionCraftArrow(Permissible permissible, AlchemicalArrow arrow) {
        return permissible.hasPermission(PERMISSION_CRAFT_ARROW.formatted(arrow.getKey().getKey()));
    }

}
