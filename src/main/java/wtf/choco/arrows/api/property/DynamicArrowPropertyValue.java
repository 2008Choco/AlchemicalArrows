package wtf.choco.arrows.api.property;

import java.util.function.Supplier;

/**
 * Represents an {@link ArrowPropertyValue} that gets recomputed every time it
 * is fetched. Useful for configuration-based values.
 *
 * @author Parker Hawke
 */
public class DynamicArrowPropertyValue extends SimpleArrowProperty {

    private final Supplier<Object> valueSupplier;

    /**
     * Construct a new arrow property value with the value supplier.
     *
     * @param valueSupplier the value supplier
     */
    public DynamicArrowPropertyValue(Supplier<Object> valueSupplier) {
        super(valueSupplier.get());

        this.valueSupplier = valueSupplier;
    }

    @Override
    public Object getValue() {
        return valueSupplier.get();
    }

}
