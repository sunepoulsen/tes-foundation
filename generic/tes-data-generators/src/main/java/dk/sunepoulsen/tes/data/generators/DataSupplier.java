package dk.sunepoulsen.tes.data.generators;

import java.util.function.Supplier;

public class DataSupplier<T> implements DataGenerator<T> {

    private final Supplier<T> supplier;

    public DataSupplier(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @Override
    public T generate() {
        return supplier.get();
    }
}
