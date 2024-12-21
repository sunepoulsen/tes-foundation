package dk.sunepoulsen.tes.testdata.generators;

import java.util.function.Supplier;

public class TestDataSupplier<T> implements TestDataGenerator<T> {

    private final Supplier<T> supplier;

    public TestDataSupplier(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @Override
    public T generate() {
        return supplier.get();
    }
}
