package wtf.choco.arrows;

import java.util.Arrays;
import java.util.UUID;

import org.apache.commons.lang.math.NumberUtils;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.RecipeChoice.MaterialChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import wtf.choco.alchema.crafting.CauldronIngredientMaterial;
import wtf.choco.alchema.crafting.CauldronRecipe;
import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.arrow.AlchemicalArrowAir;
import wtf.choco.arrows.arrow.AlchemicalArrowChain;
import wtf.choco.arrows.arrow.AlchemicalArrowConfusion;
import wtf.choco.arrows.arrow.AlchemicalArrowDarkness;
import wtf.choco.arrows.arrow.AlchemicalArrowDeath;
import wtf.choco.arrows.arrow.AlchemicalArrowEarth;
import wtf.choco.arrows.arrow.AlchemicalArrowEnder;
import wtf.choco.arrows.arrow.AlchemicalArrowExplosive;
import wtf.choco.arrows.arrow.AlchemicalArrowFire;
import wtf.choco.arrows.arrow.AlchemicalArrowFrost;
import wtf.choco.arrows.arrow.AlchemicalArrowGrapple;
import wtf.choco.arrows.arrow.AlchemicalArrowLife;
import wtf.choco.arrows.arrow.AlchemicalArrowLight;
import wtf.choco.arrows.arrow.AlchemicalArrowMagic;
import wtf.choco.arrows.arrow.AlchemicalArrowMagnetic;
import wtf.choco.arrows.arrow.AlchemicalArrowNecrotic;
import wtf.choco.arrows.arrow.AlchemicalArrowWater;
import wtf.choco.arrows.commands.AlchemicalArrowsCommand;
import wtf.choco.arrows.commands.GiveArrowCommand;
import wtf.choco.arrows.commands.SummonArrowCommand;
import wtf.choco.arrows.listeners.AlchemaRecipeIntegrationListener;
import wtf.choco.arrows.listeners.ArrowHitEntityListener;
import wtf.choco.arrows.listeners.ArrowHitGroundListener;
import wtf.choco.arrows.listeners.ArrowHitPlayerListener;
import wtf.choco.arrows.listeners.ArrowRecipeDiscoverListener;
import wtf.choco.arrows.listeners.CraftingPermissionListener;
import wtf.choco.arrows.listeners.CustomDeathMessageListener;
import wtf.choco.arrows.listeners.LegacyArrowConvertionListener;
import wtf.choco.arrows.listeners.PickupArrowListener;
import wtf.choco.arrows.listeners.ProjectileShootListener;
import wtf.choco.arrows.listeners.SkeletonKillListener;
import wtf.choco.arrows.registry.ArrowRegistry;
import wtf.choco.arrows.registry.ArrowStateManager;
import wtf.choco.arrows.util.ArrowUpdateTask;
import wtf.choco.arrows.util.UpdateChecker;
import wtf.choco.arrows.util.UpdateChecker.UpdateReason;

/**
 * The entry point of the AlchemicalArrows plugin and its API.
 *
 * @author Parker Hawke - Choco
 */
@SuppressWarnings("deprecation") // LegacyArrowConvertionListener
public class AlchemicalArrows extends JavaPlugin {

    public static final String CHAT_PREFIX = ChatColor.GOLD.toString() + ChatColor.BOLD + "AlchemicalArrows | " + ChatColor.GRAY;

    private static AlchemicalArrows instance;

    private ArrowStateManager stateManager;
    private ArrowRegistry arrowRegistry;
    private ArrowUpdateTask arrowUpdateTask;

    private boolean worldGuardEnabled = false;

    private ArrowRecipeDiscoverListener recipeListener;
    private AlchemaRecipeIntegrationListener alchemaIntegrationListener;

    @Override
    public void onEnable() {
        instance = this;
        this.stateManager = new ArrowStateManager();
        this.arrowRegistry = new ArrowRegistry();
        this.saveDefaultConfig();

        this.worldGuardEnabled = Bukkit.getPluginManager().isPluginEnabled("WorldGuard");
        this.arrowUpdateTask = ArrowUpdateTask.startArrowUpdateTask(this);

        // Register events
        this.getLogger().info("Registering events");
        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new ArrowHitEntityListener(this), this);
        manager.registerEvents(new ArrowHitGroundListener(this), this);
        manager.registerEvents(new ArrowHitPlayerListener(this), this);
        manager.registerEvents(new ProjectileShootListener(this), this);
        manager.registerEvents(new CustomDeathMessageListener(this), this);
        manager.registerEvents(new PickupArrowListener(this), this);
        manager.registerEvents(new LegacyArrowConvertionListener(this), this);
        manager.registerEvents(new SkeletonKillListener(this), this);
        manager.registerEvents(new CraftingPermissionListener(this), this);
        manager.registerEvents(recipeListener = new ArrowRecipeDiscoverListener(), this);

        boolean alchemaInstalled = Bukkit.getPluginManager().isPluginEnabled("Alchema");
        if (alchemaInstalled) {
            if (getConfig().getBoolean("Crafting.AlchemaIntegration.Enabled", true)) {
                this.getLogger().info("Found Alchema! Registering cauldron crafting recipes for all default arrows.");

                this.alchemaIntegrationListener = new AlchemaRecipeIntegrationListener();
                manager.registerEvents(alchemaIntegrationListener, this);
            } else {
                this.getLogger().info("Found Alchema but integration was disabled in the configuration file.");
            }
        }

        // Register commands
        this.getLogger().info("Registering commands");
        this.registerCommandSafely("alchemicalarrows", new AlchemicalArrowsCommand(this));
        this.registerCommandSafely("givearrow", new GiveArrowCommand(this));
        this.registerCommandSafely("summonarrow", new SummonArrowCommand(this));

        // Register alchemical arrows
        this.getLogger().info("Registering default alchemical arrows and their recipes");
        this.createAndRegisterArrow(new AlchemicalArrowAir(this), "Air", Material.FEATHER);
        this.createAndRegisterArrow(new AlchemicalArrowChain(this), "Chain", Material.SLIME_BALL);
        this.createAndRegisterArrow(new AlchemicalArrowConfusion(this), "Confusion", Material.POISONOUS_POTATO);
        this.createAndRegisterArrow(new AlchemicalArrowDarkness(this), "Darkness", Material.COAL, Material.CHARCOAL);
        this.createAndRegisterArrow(new AlchemicalArrowDeath(this), "Death", Material.WITHER_SKELETON_SKULL);
        this.createAndRegisterArrow(new AlchemicalArrowEarth(this), "Earth", Material.DIRT);
        this.createAndRegisterArrow(new AlchemicalArrowEnder(this), "Ender", Material.ENDER_EYE);
        this.createAndRegisterArrow(new AlchemicalArrowExplosive(this), "Explosive", Material.TNT);
        this.createAndRegisterArrow(new AlchemicalArrowFire(this), "Fire", Material.FIRE_CHARGE);
        this.createAndRegisterArrow(new AlchemicalArrowFrost(this), "Frost", Material.SNOWBALL);
        this.createAndRegisterArrow(new AlchemicalArrowGrapple(this), "Grapple", Material.TRIPWIRE_HOOK);
        this.createAndRegisterArrow(new AlchemicalArrowLife(this), "Life", Material.GLISTERING_MELON_SLICE);
        this.createAndRegisterArrow(new AlchemicalArrowLight(this), "Light", Material.GLOWSTONE_DUST);
        this.createAndRegisterArrow(new AlchemicalArrowMagic(this), "Magic", Material.BLAZE_POWDER);
        this.createAndRegisterArrow(new AlchemicalArrowMagnetic(this), "Magnetic", Material.IRON_INGOT);
        this.createAndRegisterArrow(new AlchemicalArrowNecrotic(this), "Necrotic", Material.ROTTEN_FLESH);
        this.createAndRegisterArrow(new AlchemicalArrowWater(this), "Water", Material.WATER_BUCKET);

        // Load Metrics
        if (getConfig().getBoolean("MetricsEnabled", true)) {
            this.getLogger().info("Enabling Plugin Metrics");

            Metrics metrics = new Metrics(this);
            metrics.addCustomChart(new Metrics.SimplePie("crafting_type", () -> {
                FileConfiguration config = getConfig();

                boolean cauldronCrafting = config.getBoolean("Crafting.AlchemaIntegration.Enabled", true);
                boolean vanillaCrafting = config.getBoolean("Crafting.VanillaCrafting", true);

                if ((cauldronCrafting && alchemaInstalled) && vanillaCrafting) {
                    return "Both";
                }
                else if (cauldronCrafting && alchemaInstalled) {
                    return "Cauldron Crafting";
                }
                else if (vanillaCrafting) {
                    return "Vanilla Crafting";
                }
                else {
                    return "Uncraftable";
                }
            }));

            metrics.addCustomChart(new Metrics.SimplePie("alchema_integration", () -> String.valueOf(alchemaInstalled)));
        }

        // Check for newer version
        UpdateChecker updateChecker = UpdateChecker.init(this, 11693);
        if (getConfig().getBoolean("CheckForUpdates", true)) {
            this.getLogger().info("Getting version information...");
            updateChecker.requestUpdateCheck().whenComplete((result, exception) -> {
                if (result.requiresUpdate()) {
                    this.getLogger().info(String.format("An update is available! AlchemicalArrows %s may be downloaded on SpigotMC", result.getNewestVersion()));
                    return;
                }

                UpdateReason reason = result.getReason();
                if (reason == UpdateReason.UP_TO_DATE) {
                    this.getLogger().info(String.format("Your version of AlchemicalArrows (%s) is up to date!", result.getNewestVersion()));
                } else if (reason == UpdateReason.UNRELEASED_VERSION) {
                    this.getLogger().info(String.format("Your version of AlchemicalArrows (%s) is more recent than the one publicly available. Are you on a development build?", result.getNewestVersion()));
                } else {
                    this.getLogger().warning("Could not check for a new version of AlchemicalArrows. Reason: " + reason);
                }
            });
        }
    }

    @Override
    public void onDisable() {
        this.arrowRegistry.clear();
        this.stateManager.clear();
        this.arrowUpdateTask.cancel();
        this.recipeListener.clearRecipeKeys();

        if (alchemaIntegrationListener != null) {
            this.alchemaIntegrationListener.clearRecipes();
        }
    }

    /**
     * Get an instance of AlchemicalArrows.
     *
     * @return the AlchemicalArrows instance
     */
    public static AlchemicalArrows getInstance() {
        return instance;
    }

    /**
     * Create a NamespacedKey with this plugin.
     *
     * @param key the key
     *
     * @return the created namespaced key
     */
    public static NamespacedKey key(String key) {
        return new NamespacedKey(instance, key);
    }

    /**
     * Get the arrow registry instance used to register arrows to AlchemicalArrows. All arrows
     * must be registered in order to be recognized by the plugin.
     *
     * @return the arrow registry instance
     */
    public ArrowRegistry getArrowRegistry() {
        return arrowRegistry;
    }

    /**
     * Get the arrow state manager.
     *
     * @return the arrow state manager instance
     */
    public ArrowStateManager getArrowStateManager() {
        return stateManager;
    }

    /**
     * Whether WorldGuard support is available or not. If the returned value is true, some arrow
     * functionality may be limited in WorldGuard regions.
     *
     * @return true if WorldGuard is present on the server
     */
    public boolean isWorldGuardSupported() {
        return worldGuardEnabled;
    }

    private void registerCommandSafely(@NotNull String commandString, @NotNull CommandExecutor executor) {
        PluginCommand command = getCommand(commandString);
        if (command == null) {
            return;
        }

        command.setExecutor(executor);

        if (executor instanceof TabCompleter) {
            command.setTabCompleter((TabCompleter) executor);
        }
    }

    private void createAndRegisterArrow(@NotNull AlchemicalArrow arrow, @NotNull String name, @NotNull Material... secondaryMaterials) {
        boolean cauldronCrafting = getConfig().getBoolean("Crafting.AlchemaIntegration.Enabled", true);

        if (cauldronCrafting && alchemaIntegrationListener != null) {
            int arrowsRequired = getConfig().getInt("Crafting.AlchemaIntegration.ArrowsRequired", 1);
            int recipeYield = getConfig().getInt("Crafting.AlchemaIntegration.RecipeYield", 1);

            for (int i = 1; i <= secondaryMaterials.length; i++) {
                Material ingredient = secondaryMaterials[i - 1];
                NamespacedKey key = arrow.getKey();

                if (i > 1) {
                    key = new NamespacedKey(key.getNamespace(), key.getKey() + "_" + i);
                }

                this.alchemaIntegrationListener.addRecipe(new CauldronRecipe(key, arrow.createItemStack(recipeYield), new CauldronIngredientMaterial(Material.ARROW, arrowsRequired), new CauldronIngredientMaterial(ingredient)));
            }
        }

        if (getConfig().getBoolean("Crafting.VanillaCrafting", true)) {
            int amount = getConfig().getInt("Arrow." + name + "RecipeYield", 8);
            Bukkit.addRecipe(new ShapedRecipe(arrow.getKey(), arrow.createItemStack(amount))
                    .shape("AAA", "ASA", "AAA").setIngredient('A', Material.ARROW)
                    .setIngredient('S', new MaterialChoice(Arrays.asList(secondaryMaterials))));
            this.recipeListener.includeRecipeKey(arrow.getKey());
        }

        this.arrowRegistry.register(arrow);
    }

    @Nullable
    private Block blockFromString(@NotNull String value) {
        String[] parts = value.split(",");
        if (parts.length != 4) {
            return null;
        }

        World world = Bukkit.getWorld(UUID.fromString(parts[0]));
        if (world == null) {
            return null;
        }

        int x = NumberUtils.toInt(parts[1], Integer.MIN_VALUE), y = NumberUtils.toInt(parts[2], Integer.MIN_VALUE), z = NumberUtils.toInt(parts[3], Integer.MIN_VALUE);
        return (x != Integer.MIN_VALUE && y != Integer.MIN_VALUE && z != Integer.MIN_VALUE) ? world.getBlockAt(x, y, z) : null;
    }

}
