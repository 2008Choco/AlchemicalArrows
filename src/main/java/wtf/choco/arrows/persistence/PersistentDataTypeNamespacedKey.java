package wtf.choco.arrows.persistence;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

import org.jetbrains.annotations.NotNull;

/**
 * A {@link PersistentDataType} implementation for {@link NamespacedKey NamespacedKeys}.
 *
 * @author Parker Hawke - Choco
 */
public final class PersistentDataTypeNamespacedKey implements PersistentDataType<String, NamespacedKey> {

    PersistentDataTypeNamespacedKey() { }

    @Override
    @NotNull
    @SuppressWarnings("deprecation")
    public NamespacedKey fromPrimitive(@NotNull String string, @NotNull PersistentDataAdapterContext context) {
        String[] split = string.split(":");
        return split.length >= 2 ? new NamespacedKey(split[0], split[1]) : NamespacedKey.minecraft(split[0]);
    }

    @Override
    @NotNull
    public String toPrimitive(@NotNull NamespacedKey key, @NotNull PersistentDataAdapterContext context) {
        return key.toString();
    }

    @Override
    @NotNull
    public Class<NamespacedKey> getComplexType() {
        return NamespacedKey.class;
    }

    @Override
    @NotNull
    public Class<String> getPrimitiveType() {
        return String.class;
    }

}
