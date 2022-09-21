package wtf.choco.arrows;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
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
import wtf.choco.arrows.integration.alchema.PluginIntegrationAlchema;
import wtf.choco.arrows.listeners.ArrowHitEntityListener;
import wtf.choco.arrows.listeners.ArrowHitGroundListener;
import wtf.choco.arrows.listeners.ArrowHitPlayerListener;
import wtf.choco.arrows.listeners.ArrowRecipeDiscoverListener;
import wtf.choco.arrows.listeners.CraftingPermissionListener;
import wtf.choco.arrows.listeners.CrossbowLoadListener;
import wtf.choco.arrows.listeners.CustomDeathMessageListener;
import wtf.choco.arrows.listeners.PickupArrowListener;
import wtf.choco.arrows.listeners.ProjectileShootListener;
import wtf.choco.arrows.listeners.SkeletonKillListener;
import wtf.choco.arrows.registry.ArrowRegistry;
import wtf.choco.arrows.registry.ArrowStateManager;
import wtf.choco.arrows.util.AAConstants;
import wtf.choco.arrows.util.ArrowUpdateTask;
import wtf.choco.arrows.util.NumberUtils;
import wtf.choco.commons.integration.IntegrationHandler;
import wtf.choco.commons.util.UpdateChecker;
import wtf.choco.commons.util.UpdateChecker.UpdateReason;

/**
 * The entry point of the AlchemicalArrows plugin and its API.
 *
 * @author Parker Hawke - Choco
 */
public class AlchemicalArrows extends JavaPlugin {

    public static final String CHAT_PREFIX = ChatColor.GOLD.toString() + ChatColor.BOLD + "AlchemicalArrows | " + ChatColor.GRAY;

    private static AlchemicalArrows instance;

    private ArrowStateManager stateManager;
    private ArrowRegistry arrowRegistry;
    private ArrowUpdateTask arrowUpdateTask;

    private boolean worldGuardEnabled = false;

    private ArrowRecipeDiscoverListener recipeListener;

    private final IntegrationHandler integrationHandler = new IntegrationHandler(this);

    @Override
    public void onLoad() {
        this.integrationHandler.registerIntegrations("Alchema", () -> PluginIntegrationAlchema::new);
        this.integrationHandler.integrate();
    }

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
        manager.registerEvents(recipeListener = new ArrowRecipeDiscoverListener(), this);
        manager.registerEvents(new CraftingPermissionListener(this), this);
        manager.registerEvents(new CrossbowLoadListener(this), this);
        manager.registerEvents(new CustomDeathMessageListener(this), this);
        manager.registerEvents(new PickupArrowListener(this), this);
        manager.registerEvents(new SkeletonKillListener(this), this);
        manager.registerEvents(new ProjectileShootListener(this), this);

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

        // Enable integrations
        this.integrationHandler.enableIntegrations();

        // Load Metrics
        if (getConfig().getBoolean(AAConstants.CONFIG_METRICS_ENABLED, true)) {
            this.getLogger().info("Enabling Plugin Metrics");

            boolean alchemaEnabled = Bukkit.getPluginManager().isPluginEnabled("Alchema");

            Metrics metrics = new Metrics(this, 2427); // https://bstats.org/what-is-my-plugin-id
            metrics.addCustomChart(new SimplePie("crafting_type", () -> {
                FileConfiguration config = getConfig();

                boolean cauldronCrafting = config.getBoolean(AAConstants.CONFIG_CRAFTING_ALCHEMA_INTEGRATION_ENABLED, true);
                boolean vanillaCrafting = config.getBoolean(AAConstants.CONFIG_CRAFTING_VANILLA_CRAFTING, true);

                if ((cauldronCrafting && alchemaEnabled) && vanillaCrafting) {
                    return "Both";
                }
                else if (cauldronCrafting && alchemaEnabled) {
                    return "Cauldron Crafting";
                }
                else if (vanillaCrafting) {
                    return "Vanilla Crafting";
                }
                else {
                    return "Uncraftable";
                }
            }));

            metrics.addCustomChart(new SimplePie("alchema_integration", () -> String.valueOf(alchemaEnabled)));
        }

        // Check for newer version
        UpdateChecker updateChecker = UpdateChecker.init(this, 11693);
        if (getConfig().getBoolean(AAConstants.CONFIG_CHECK_FOR_UPDATES, true)) {
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
        this.integrationHandler.disableIntegrations(true);

        this.arrowRegistry.clear();
        this.stateManager.clear();
        this.arrowUpdateTask.cancel();
        this.recipeListener.clearRecipeKeys();
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
     * Get the integration handler instance.
     *
     * @return the integration handler
     */
    @NotNull
    public IntegrationHandler getIntegrationHandler() {
        return integrationHandler;
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

        if (executor instanceof TabCompleter tabCompleter) {
            command.setTabCompleter(tabCompleter);
        }
    }

    @Deprecated // NamespacedKey(String, String)
    private void createAndRegisterArrow(@NotNull AlchemicalArrow arrow, @NotNull String name, @NotNull Material... secondaryMaterials) {
        boolean cauldronCrafting = getConfig().getBoolean(AAConstants.CONFIG_CRAFTING_ALCHEMA_INTEGRATION_ENABLED, true);
        Optional<@NotNull PluginIntegrationAlchema> alchemaIntegration = integrationHandler.getIntegration(PluginIntegrationAlchema.class);

        if (cauldronCrafting && alchemaIntegration.isPresent()) {
            PluginIntegrationAlchema alchemaIntegrationInstance = alchemaIntegration.get();

            int arrowsRequired = getConfig().getInt(AAConstants.CONFIG_CRAFTING_ALCHEMA_INTEGRATION_ARROWS_REQUIRED, 1);
            int recipeYield = getConfig().getInt(AAConstants.CONFIG_CRAFTING_ALCHEMA_INTEGRATION_RECIPE_YIELD, 1);

            for (int i = 1; i <= secondaryMaterials.length; i++) {
                Material ingredient = secondaryMaterials[i - 1];
                NamespacedKey key = arrow.getKey();

                if (i > 1) {
                    key = new NamespacedKey(key.getNamespace(), key.getKey() + "_" + i);
                }

                alchemaIntegrationInstance.addRecipe(key, arrow, recipeYield, arrowsRequired, ingredient);
            }
        }

        if (getConfig().getBoolean(AAConstants.CONFIG_CRAFTING_VANILLA_CRAFTING, true)) {
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
