package wtf.choco.arrows;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.commons.lang.math.NumberUtils;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.inventory.RecipeChoice.MaterialChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.arrow.*;
import wtf.choco.arrows.commands.AlchemicalArrowsCommand;
import wtf.choco.arrows.commands.GiveArrowCommand;
import wtf.choco.arrows.commands.SummonArrowCommand;
import wtf.choco.arrows.crafting.AlchemicalCauldron;
import wtf.choco.arrows.crafting.CauldronRecipe;
import wtf.choco.arrows.crafting.CauldronUpdateTask;
import wtf.choco.arrows.listeners.ArrowHitEntityListener;
import wtf.choco.arrows.listeners.ArrowHitGroundListener;
import wtf.choco.arrows.listeners.ArrowHitPlayerListener;
import wtf.choco.arrows.listeners.ArrowRecipeDiscoverListener;
import wtf.choco.arrows.listeners.CauldronManipulationListener;
import wtf.choco.arrows.listeners.CraftingPermissionListener;
import wtf.choco.arrows.listeners.CustomDeathMsgListener;
import wtf.choco.arrows.listeners.PickupArrowListener;
import wtf.choco.arrows.listeners.ProjectileShootListener;
import wtf.choco.arrows.listeners.SkeletonKillListener;
import wtf.choco.arrows.registry.ArrowRegistry;
import wtf.choco.arrows.registry.ArrowStateManager;
import wtf.choco.arrows.registry.CauldronManager;
import wtf.choco.arrows.utils.ArrowUpdateTask;
import wtf.choco.arrows.utils.ItemBuilder;

/**
 * The entry point of the AlchemicalArrows plugin and its API.
 *
 * @author Parker Hawke - Choco
 */
public class AlchemicalArrows extends JavaPlugin {

    public static final String CHAT_PREFIX = ChatColor.GOLD.toString() + ChatColor.BOLD + "AlchemicalArrows | " + ChatColor.GRAY;

    private static final int RESOURCE_ID = 11693;
    private static final String SPIGET_LINK = "https://api.spiget.org/v2/resources/" + RESOURCE_ID + "/versions/latest";

    private static final Gson GSON = new Gson();
    private static AlchemicalArrows instance;

    private ArrowStateManager stateManager;
    private ArrowRegistry arrowRegistry;
    private CauldronManager cauldronManager;
    private ArrowUpdateTask arrowUpdateTask;
    private CauldronUpdateTask cauldronUpdateTask;

    private File cauldronFile;

    private boolean worldGuardEnabled = false;
    private boolean newVersionAvailable = false;

    private ArrowRecipeDiscoverListener recipeListener;

    @Override
    public void onEnable() {
        instance = this;
        this.stateManager = new ArrowStateManager();
        this.arrowRegistry = new ArrowRegistry();
        this.cauldronManager = new CauldronManager();
        this.saveDefaultConfig();
        this.cauldronFile = new File(getDataFolder(), "cauldrons.data");

        this.worldGuardEnabled = Bukkit.getPluginManager().isPluginEnabled("WorldGuard");
        this.arrowUpdateTask = ArrowUpdateTask.startArrowUpdateTask(this);

        if (getConfig().getBoolean("Crafting.CauldronCrafting", true)) {
            this.cauldronUpdateTask = CauldronUpdateTask.startTask(this);
        }

        // Register events
        this.getLogger().info("Registering events");
        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new ArrowHitEntityListener(this), this);
        manager.registerEvents(new ArrowHitGroundListener(this), this);
        manager.registerEvents(new ArrowHitPlayerListener(this), this);
        manager.registerEvents(new ProjectileShootListener(this), this);
        manager.registerEvents(new CustomDeathMsgListener(this), this);
        manager.registerEvents(new PickupArrowListener(this), this);
        manager.registerEvents(new SkeletonKillListener(this), this);
        manager.registerEvents(new CraftingPermissionListener(this), this);
        manager.registerEvents(new CauldronManipulationListener(this), this);
        manager.registerEvents(recipeListener = new ArrowRecipeDiscoverListener(), this);

        // Register commands
        this.getLogger().info("Registering commands");
        this.setupCommand("alchemicalarrows", new AlchemicalArrowsCommand(this), AlchemicalArrowsCommand.TAB_COMPLETER);
        this.setupCommand("givearrow", new GiveArrowCommand(this), GiveArrowCommand.TAB_COMPLETER);
        this.setupCommand("summonarrow", new SummonArrowCommand(this), SummonArrowCommand.TAB_COMPLETER);

        // Register alchemical arrows
        this.getLogger().info("Registering default alchemical arrows and their recipes");
        this.createArrow(new AlchemicalArrowAir(this), "Air", Material.FEATHER); // Custom model data 132 (+1 for every registration)
        this.createArrow(new AlchemicalArrowChain(this), "Chain", Material.SLIME_BALL);
        this.createArrow(new AlchemicalArrowConfusion(this), "Confusion", Material.POISONOUS_POTATO);
        this.createArrow(new AlchemicalArrowDarkness(this), "Darkness", Material.COAL, Material.CHARCOAL);
        this.createArrow(new AlchemicalArrowDeath(this), "Death", Material.WITHER_SKELETON_SKULL);
        this.createArrow(new AlchemicalArrowEarth(this), "Earth", Material.DIRT);
        this.createArrow(new AlchemicalArrowEnder(this), "Ender", Material.ENDER_EYE);
        this.createArrow(new AlchemicalArrowExplosive(this), "Explosive", Material.TNT);
        this.createArrow(new AlchemicalArrowFire(this), "Fire", Material.FIRE_CHARGE);
        this.createArrow(new AlchemicalArrowFrost(this), "Frost", Material.SNOWBALL);
        this.createArrow(new AlchemicalArrowGrapple(this), "Grapple", Material.TRIPWIRE_HOOK);
        this.createArrow(new AlchemicalArrowLife(this), "Life", Material.GLISTERING_MELON_SLICE);
        this.createArrow(new AlchemicalArrowLight(this), "Light", Material.GLOWSTONE_DUST);
        this.createArrow(new AlchemicalArrowMagic(this), "Magic", Material.BLAZE_POWDER);
        this.createArrow(new AlchemicalArrowMagnetic(this), "Magnetic", Material.IRON_INGOT);
        this.createArrow(new AlchemicalArrowNecrotic(this), "Necrotic", Material.ROTTEN_FLESH);
        this.createArrow(new AlchemicalArrowWater(this), "Water", Material.WATER_BUCKET); // Custom model data 147

        // Load cauldrons
        if (cauldronFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(cauldronFile))) {
                String line = null;
                while ((line = reader.readLine()) != null) {
                    Block block = blockFromString(line);
                    if (block == null) continue;

                    this.cauldronManager.addAlchemicalCauldron(new AlchemicalCauldron(block));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Load Metrics
        if (getConfig().getBoolean("MetricsEnabled", true)) {
            this.getLogger().info("Enabling Plugin Metrics");

            Metrics metrics = new Metrics(this);
            metrics.addCustomChart(new Metrics.SimplePie("crafting_type", () -> getConfig().getBoolean("Crafting.CauldronCrafting", true) ? "Cauldron Crafting" : "Vanilla Crafting"));
        }

        // Check for newer version (Spiget API)
        if (getConfig().getBoolean("CheckForUpdates", true)) {
            this.getLogger().info("Getting version information...");
            this.doVersionCheck();
        }
    }

    @Override
    public void onDisable() {
        this.arrowRegistry.clear();
        this.stateManager.clear();
        this.arrowUpdateTask.cancel();
        this.recipeListener.clearRecipeKeys();

        if (cauldronUpdateTask != null) {
            this.cauldronUpdateTask.cancel();
        }

        Collection<AlchemicalCauldron> cauldrons = this.cauldronManager.getAlchemicalCauldrons();
        if (cauldrons.size() >= 1) {
            try {
                this.cauldronFile.createNewFile();
                PrintWriter writer = new PrintWriter(cauldronFile);

                for (AlchemicalCauldron cauldron : cauldrons) {
                    Block block = cauldron.getCauldronBlock();
                    writer.println(block.getWorld().getUID() + "," + block.getX() + "," + block.getY() + "," + block.getZ());
                }

                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            this.cauldronFile.delete();
        }

        this.cauldronManager.clearAlchemicalCauldrons();
        this.cauldronManager.clearRecipes();
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
     * Get the cauldron manager instance used to track in-world alchemical cauldrons.
     *
     * @return the cauldron manager
     */
    public CauldronManager getCauldronManager() {
        return this.cauldronManager;
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

    /**
     * Whether a new version of AlchemicalArrows is available or not. This method does not make a
     * version check, but simply retrieves a cached value.
     *
     * @return true if a new version is available
     */
    public boolean isNewVersionAvailable() {
        return newVersionAvailable;
    }

    private void doVersionCheck() {
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(SPIGET_LINK).openStream()))) {
                JsonObject object = GSON.fromJson(reader, JsonObject.class);
                String currentVersion = getDescription().getVersion(), recentVersion = object.get("name").getAsString();

                if (!currentVersion.equals(recentVersion)) {
                    getLogger().info("New version available. Your Version = " + currentVersion + ". New Version = " + recentVersion);
                    this.newVersionAvailable = true;
                }
            } catch(IOException e) {
                getLogger().info("Could not check for a new version. Perhaps the website is down?");
            }
        });
    }

    private void setupCommand(@NotNull String commandString, @NotNull CommandExecutor executor, @Nullable TabCompleter tabCompleter) {
        PluginCommand command = getCommand(commandString);
        if (command == null) return;

        command.setExecutor(executor);
        command.setTabCompleter(tabCompleter);
    }

    private void createArrow(@NotNull AlchemicalArrow arrow, @NotNull String name, @NotNull Material... secondaryMaterials) {
        boolean cauldronCrafting = getConfig().getBoolean("Crafting.CauldronCrafting");

        if (cauldronCrafting) {
            for (Material secondaryMaterial : secondaryMaterials) {
                this.cauldronManager.registerCauldronRecipe(new CauldronRecipe(arrow.getKey(), arrow, Material.ARROW, secondaryMaterial));
            }
        } else {
            int amount = getConfig().getInt("Arrow." + name + "RecipeYield", 8);
            Bukkit.addRecipe(new ShapedRecipe(arrow.getKey(), ItemBuilder.modify(arrow.getItem()).amount(amount).build())
                    .shape("AAA", "ASA", "AAA").setIngredient('A', Material.ARROW)
                    .setIngredient('S', new MaterialChoice(Arrays.asList(secondaryMaterials))));
            this.recipeListener.includeRecipeKey(arrow.getKey());
        }

        this.arrowRegistry.register(arrow);
    }

    @Nullable
    private Block blockFromString(@NotNull String value) {
        String[] parts = value.split(",");
        if (parts.length != 4) return null;

        World world = Bukkit.getWorld(UUID.fromString(parts[0]));
        if (world == null) return null;

        int x = NumberUtils.toInt(parts[1], Integer.MIN_VALUE), y = NumberUtils.toInt(parts[2], Integer.MIN_VALUE), z = NumberUtils.toInt(parts[3], Integer.MIN_VALUE);
        if (x == Integer.MIN_VALUE || y == Integer.MIN_VALUE || z == Integer.MIN_VALUE) return null;

        return world.getBlockAt(x, y, z);
    }

}