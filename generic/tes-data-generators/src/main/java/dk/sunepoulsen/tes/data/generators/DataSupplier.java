package dk.sunepoulsen.tes.data.generators;

import java.util.function.Supplier;

/**
 * Adapts a supplier to a data generator.
 *
 * @param <T> type of the generated values
 */
public class DataSupplier<T> implements DataGenerator<T> {

    /** Supplier invoked for each generated value. */
    private final Supplier<T> supplier;

    /**
     * Creates a generator backed by a supplier.
     *
     * @param supplier supplier of generated values
     */
    public DataSupplier(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    /** {@inheritDoc} */
    @Override
    public T generate() {
        return supplier.get();
    }
}
