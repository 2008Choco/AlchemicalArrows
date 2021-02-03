package wtf.choco.arrows.arrow.entity;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.internal.platform.WorldGuardPlatform;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.regions.RegionQuery;

import java.util.EnumSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import wtf.choco.arrows.AlchemicalArrows;
import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.api.AlchemicalArrowEntity;

public class ArrowEntityFire extends AlchemicalArrowEntity {

    private static final Set<Material> MELTABLE_BLOCKS = EnumSet.of(
        Material.ICE,
        Material.FROSTED_ICE,
        Material.BLUE_ICE,
        Material.SNOW_BLOCK,
        Material.SNOW
    );

    private Block currentBlock;
    private int currentMeltingTicks;
    private Vector lastVelocity;

    private boolean inWorldGuardedBlock = false;

    private boolean extinguished = false;

    private final AlchemicalArrows plugin;
    private final int ticksToMelt;

    public ArrowEntityFire(@NotNull AlchemicalArrow implementation, @NotNull Arrow arrow, @NotNull AlchemicalArrows plugin, int ticksToMelt) {
        super(implementation, arrow);

        this.plugin = plugin;
        this.ticksToMelt = ticksToMelt;
    }

    public void setExtinguished(boolean extinguished) {
        this.extinguished = extinguished;
    }

    public boolean isExtinguished() {
        return extinguished;
    }

    public void tick() {
        if (extinguished || ticksToMelt < 0) {
            return;
        }

        if (!arrow.isInBlock()) {
            this.lastVelocity = arrow.getVelocity().normalize().multiply(0.1);
            this.inWorldGuardedBlock = false;
            return;
        }

        if (inWorldGuardedBlock) {
            return;
        }

        // We have to do this... for some reason, Arrow#getAttachedBlock() returns the wrong block
        Location arrowLocation = arrow.getLocation();
        Block block = arrowLocation.add(lastVelocity).getBlock();
        Material type = block.getType();

        if (!MELTABLE_BLOCKS.contains(type)) {
            return;
        }

        boolean blockChanged = false;
        if (!block.equals(currentBlock)) {
            this.currentBlock = block;
            this.currentMeltingTicks = 0;
            blockChanged = true;
        }

        if (currentMeltingTicks++ >= ticksToMelt) {
            this.currentBlock = null;
            this.currentMeltingTicks = 0;

            if (blockChanged && plugin.isWorldGuardSupported()) {
                WorldGuardPlugin worldguardPlugin = WorldGuardPlugin.inst();
                WorldGuardPlatform worldguard = WorldGuard.getInstance().getPlatform();
                RegionQuery query = worldguard.getRegionContainer().createQuery();

                ProjectileSource shooter = arrow.getShooter();

                com.sk89q.worldedit.util.Location worldguardLocation = BukkitAdapter.adapt(block.getLocation());
                if (!query.testState(worldguardLocation, shooter instanceof Player ? worldguardPlugin.wrapPlayer((Player) shooter) : null, Flags.ICE_MELT, Flags.BLOCK_BREAK)) {
                    this.inWorldGuardedBlock = true;
                    return;
                }
            }

            block.setType(type == Material.ICE || type == Material.FROSTED_ICE ? Material.WATER : Material.AIR);
        }
    }

}
