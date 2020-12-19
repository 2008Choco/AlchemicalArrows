package wtf.choco.arrows.persistence;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;

/**
 * A utility class defining constants of all types of {@link PersistentDataType PersistentDataTypes}
 * defined by AlchemicalArrows.
 *
 * @author Parker Hawke - Choco
 */
public final class AAPersistentDataTypes {

    /**
     * A {@link NamespacedKey} PersistentDataType.
     */
    public static final PersistentDataType<String, NamespacedKey> NAMESPACED_KEY = new PersistentDataTypeNamespacedKey();

}
