package wtf.choco.arrows.api;

import java.util.Objects;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.projectiles.BlockProjectileSource;
import org.jetbrains.annotations.NotNull;

import wtf.choco.arrows.api.property.ArrowProperty;
import wtf.choco.arrows.api.property.PropertyMap;
import wtf.choco.arrows.persistence.AAPersistentDataTypes;
import wtf.choco.arrows.util.AAConstants;

/**
 * Represents the base of an alchemical arrow with special effects upon hitting a
 * block, entity or player
 *
 * @author Parker Hawke - Choco
 */
public abstract class AlchemicalArrow {

    protected final PropertyMap properties = new PropertyMap();

    /**
     * Get this arrow's unique key.
     *
     * @return the unique key
     */
    @NotNull
    public abstract NamespacedKey getKey();

    /**
     * Get the display name of this alchemical arrow. This includes colour codes and formatting.
     * The returned String should be expected in messages sent to players
     *
     * @return the arrow's display name
     */
    @NotNull
    public abstract String getDisplayName();

    /**
     * Get the item representation of this alchemical arrow. The type must be
     * be tagged by {@link Tag#ITEMS_ARROWS}, otherwise an exception will be
     * thrown at registration.
     * <p>
     * <strong>NOTE:</strong> This item does NOT have applied the NamespacedKey
     * for this arrow and cannot be shot unless {@link #matchesItem(ItemStack)}
     * is overridden by implementation. Callers should prefer instead to call
     * {@link #createItemStack()}. This method is expected to be overridden by
     * implementations, NOT to be called by API.
     *
     * @return the arrow item
     */
    @NotNull
    public abstract ItemStack getItem();

    /**
     * Create a new {@link ItemStack} that represents this AlchemicalArrow in
     * an inventory. This arrow can be shot by a player.
     *
     * @return the item stack
     */
    public ItemStack createItemStack() {
        ItemStack item = getItem().clone();
        ItemMeta meta = item.getItemMeta();

        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(AAConstants.KEY_TYPE, AAPersistentDataTypes.NAMESPACED_KEY, getKey());

        item.setItemMeta(meta);
        return item;
    }

    /**
     * Create a new {@link ItemStack} that represents this AlchemicalArrow in
     * an inventory. This arrow can be shot by a player.
     *
     * @param amount the item amount
     *
     * @return the item stack
     */
    public final ItemStack createItemStack(int amount) {
        ItemStack item = createItemStack().clone();
        item.setAmount(amount);
        return item;
    }

    /**
     * Check whether or not the provided {@link ItemStack} represents this
     * AlchemicalArrow.
     *
     * @param item the item to check
     *
     * @return true if valid, false otherwise
     */
    public boolean matchesItem(ItemStack item) {
        if (item == null) {
            return false;
        }

        ItemMeta meta = item.getItemMeta();
        return meta != null && getKey().equals(meta.getPersistentDataContainer().get(AAConstants.KEY_TYPE, AAPersistentDataTypes.NAMESPACED_KEY));
    }

    /**
     * Get a map containing all properties for this arrow
     *
     * @return the arrow properties
     */
    @NotNull
    public final PropertyMap getProperties() {
        return properties;
    }

    /**
     * Called 20 times every second. This method is intended for displaying particles around
     * the arrow, performing tasks whilst the arrow is still in the world, etc.
     *
     * @param arrow the alchemical arrow entity instance
     * @param location the arrow's current location at this tick
     */
    public void tick(@NotNull AlchemicalArrowEntity arrow, @NotNull Location location) { }

    /**
     * Called when the arrow hits a solid block
     *
     * @param arrow the alchemical arrow entity instance
     * @param block the block on which the arrow landed
     */
    public void onHitBlock(@NotNull AlchemicalArrowEntity arrow, @NotNull Block block) { }

    /**
     * Called when the arrow hits a player
     *
     * @param arrow the alchemical arrow entity instance
     * @param player the player damaged by the arrow
     */
    public void onHitPlayer(@NotNull AlchemicalArrowEntity arrow, @NotNull Player player) { }

    /**
     * Called when the arrow hits an entity (this excludes Players. For Players, see
     * {@link #onHitPlayer(AlchemicalArrowEntity, Player)})
     *
     * @param arrow the alchemical arrow entity instance
     * @param entity the entity damaged by the arrow
     */
    public void onHitEntity(@NotNull AlchemicalArrowEntity arrow, @NotNull Entity entity) { }

    /**
     * Called at low priority when a player has successfully shot this alchemical arrow,
     * but it has yet to be registered. Such that this method returns true, the alchemical
     * arrow will be launched
     *
     * @param arrow the alchemical arrow entity instance
     * @param player the player that shot the arrow
     *
     * @return whether the shot should be permitted or not
     */
    public boolean onShootFromPlayer(@NotNull AlchemicalArrowEntity arrow, @NotNull Player player) {
        return true;
    }

    /**
     * Called at a low priority when a {@link Skeleton} successfully shoots an arrow,
     * but it has yet to be registered. Such that this method returns true, the alchemical
     * arrow will be launched. The {@link ArrowProperty#SKELETONS_CAN_SHOOT} property must
     * return true in order for this method to be invoked
     *
     * @param arrow the alchemical arrow entity instance
     * @param skeleton the skeleton that shot the arrow
     *
     * @return whether the shot should be permitted or not
     */
    public boolean onShootFromSkeleton(@NotNull AlchemicalArrowEntity arrow, @NotNull Skeleton skeleton) {
        return true;
    }

    /**
     * Called at a low priority when a {@link BlockProjectileSource} (i.e. Dispenser)
     * shoots an arrow, but it has yet to be registered. Such that this method returns
     * true, the alchemical arrow will be launched
     *
     * @param arrow the alchemical arrow entity instance
     * @param source the block source that shot the arrow
     *
     * @return whether the shot should be permitted or not
     */
    public boolean onShootFromBlockSource(@NotNull AlchemicalArrowEntity arrow, @NotNull BlockProjectileSource source) {
        return true;
    }

    /**
     * Called the instant before {@link #onHitPlayer(AlchemicalArrowEntity, Player)} or
     * {@link #onHitEntity(AlchemicalArrowEntity, Entity)} is called. Used to cancel events
     * if necessary
     *
     * @param arrow the alchemical arrow entity instance
     * @param event the EntityDamageByEntityEvent source
     */
    public void hitEntityEventHandler(@NotNull AlchemicalArrowEntity arrow, @NotNull EntityDamageByEntityEvent event) { }

    /**
     * Called the instant before {@link #onHitBlock(AlchemicalArrowEntity, Block)} is called.
     * Used to cancel events if necessary
     *
     * @param arrow the alchemical arrow entity instance
     * @param event the ProjectileHitEvent source
     */
    public void hitGroundEventHandler(@NotNull AlchemicalArrowEntity arrow, @NotNull ProjectileHitEvent event) { }

    /**
     * Called the instant before {@link #onShootFromPlayer(AlchemicalArrowEntity, Player)},
     * {@link #onShootFromSkeleton(AlchemicalArrowEntity, Skeleton)} or
     * {@link #onShootFromBlockSource(AlchemicalArrowEntity, BlockProjectileSource)} is called.
     * Used to cancel events if necessary
     *
     * @param arrow the alchemical arrow entity instance
     * @param event the ProjectileLaunchEvent source
     */
    public void shootEventHandler(@NotNull AlchemicalArrowEntity arrow, @NotNull ProjectileLaunchEvent event) { }

    /**
     * Create a new instance of an {@link AlchemicalArrowEntity}. If a custom AlchemicalArrowEntity
     * implementation is used, this method must be overridden to return a custom instance of it.
     * Under no circumstance should additional, non-arrow entity-related code be executed in an
     * overridden implementation of this method. For additional logic, see AlchemicalArrow's various
     * methods and override them where required
     *
     * @param arrow the Bukkit {@link Arrow} instance from which to create an AlchemicalArrowEntity
     *
     * @return the new AlchemicalArrowEntity instance of this implementation
     */
    @NotNull
    public AlchemicalArrowEntity createNewArrow(@NotNull Arrow arrow) {
        return new AlchemicalArrowEntity(this, arrow);
    }

    @Override
    public int hashCode() {
        return 31 * (getKey() == null ? 0 : getKey().hashCode());
    }

    @Override
    public boolean equals(Object object) {
        return object == this || (object instanceof AlchemicalArrow && Objects.equals(getKey(), ((AlchemicalArrow) object).getKey()));
    }

}
