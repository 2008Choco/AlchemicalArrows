package wtf.choco.arrows.registry;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import wtf.choco.arrows.api.AlchemicalArrow;
import wtf.choco.arrows.api.AlchemicalArrowEntity;

/**
 * Handles the in-world entities for {@link AlchemicalArrow} types. All alchemical arrows in the
 * world have an associated {@link AlchemicalArrowEntity} registered in this state manager so long
 * as they are still valid according to {@link Entity#isValid()}.
 *
 * @author Parker Hawke - Choco
 */
public final class ArrowStateManager {

    private final Map<UUID, AlchemicalArrowEntity> entities = new HashMap<>(16, 0.80F);

    /**
     * Add an {@link AlchemicalArrowEntity} to the state manager.
     *
     * @param arrow the arrow to add
     */
    public void add(@NotNull AlchemicalArrowEntity arrow) {
        Preconditions.checkArgument(arrow != null, "Arrow entity cannot be null");
        Preconditions.checkNotNull(arrow.getArrow(), "Arrow entity holds null Bukkit arrow");

        this.entities.put(arrow.getArrow().getUniqueId(), arrow);
    }

    /**
     * Remove an {@link AlchemicalArrowEntity} from the state manager.
     *
     * @param arrow the arrow to remove
     */
    public void remove(@NotNull AlchemicalArrowEntity arrow) {
        Preconditions.checkArgument(arrow != null, "Arrow entity cannot be null");
        Preconditions.checkNotNull(arrow.getArrow(), "Arrow entity holds null Bukkit arrow");
        this.entities.remove(arrow.getArrow().getUniqueId());
    }

    /**
     * Remove an {@link Arrow} (and therefore its associated {@link AlchemicalArrowEntity}, if
     * one exists) from the state manager.
     *
     * @param arrow the arrow to remove
     */
    public void remove(@NotNull Arrow arrow) {
        Preconditions.checkArgument(arrow != null, "Arrow entity cannot be null");
        this.entities.remove(arrow.getUniqueId());
    }

    /**
     * Get the {@link AlchemicalArrowEntity} associated with the provided {@link Arrow}.
     *
     * @param arrow the Bukkit arrow from which to get an alchemical arrow
     *
     * @return an instance of the alchemical arrow entity. null if not stated
     */
    @Nullable
    public AlchemicalArrowEntity get(@Nullable Arrow arrow) {
        return (arrow != null) ? get(arrow.getUniqueId()) : null;
    }

    /**
     * Get the {@link AlchemicalArrowEntity} associated with the provided {@link UUID}.
     *
     * @param uuid the UUID of the arrow from which to get an alchemical arrow
     *
     * @return an instance of the alchemical arrow entity. null if none
     */
    @Nullable
    public AlchemicalArrowEntity get(@Nullable UUID uuid) {
        return (uuid != null) ? entities.get(uuid) : null;
    }

    /**
     * Get a copied collection of all {@link AlchemicalArrowEntity} states in the manager.
     * Changes made to the returned collection will not affect the state manager.
     *
     * @return all actively stated arrows
     */
    @NotNull
    public Collection<AlchemicalArrowEntity> getArrows() {
        return new ArrayList<>(entities.values());
    }

    /**
     * Get an unmodifiable collection of all {@link AlchemicalArrowEntity} states in the
     * specified world.
     *
     * @param world the world from which to query arrows
     *
     * @return all actively stated arrows in the specified world
     */
    @NotNull
    public Collection<AlchemicalArrowEntity> getArrows(@NotNull World world) {
        Preconditions.checkArgument(world != null, "Cannot get arrows in null world");
        return entities.values().stream().filter(e -> e.getWorld() == world).collect(Collectors.toList());
    }

    /**
     * Get an unmodifiable collection of all {@link AlchemicalArrowEntity} states of the
     * specified arrow entity type.
     *
     * @param <T> the arrow entity type
     * @param type the type of arrows to get
     *
     * @return all actively stated arrows of the specified type
     *
     * @see #getArrowsOfType(Class)
     */
    @NotNull
    public <T extends AlchemicalArrowEntity> Collection<T> getArrows(@NotNull Class<T> type) {
        Preconditions.checkArgument(type != null, "Cannot get arrows of null type");
        return entities.values().stream().filter(type::isInstance).map(type::cast).collect(Collectors.toList());
    }

    /**
     * Get an unmodifiable collection of all {@link AlchemicalArrowEntity} states of the
     * specified {@link AlchemicalArrow} type.
     *
     * @param type the type of arrows to get
     *
     * @return all actively stated arrows of the specified type
     *
     * @see #getArrows(Class)
     */
    @NotNull
    public Collection<AlchemicalArrowEntity> getArrowsOfType(@NotNull Class<? extends AlchemicalArrow> type) {
        Preconditions.checkArgument(type != null, "Cannot get arrows of null type");
        return entities.values().stream().filter(e -> type.isInstance(e.getImplementation())).collect(Collectors.toList());
    }

    /**
     * Clears the state manager.
     */
    public void clear() {
        this.entities.clear();
    }

}
