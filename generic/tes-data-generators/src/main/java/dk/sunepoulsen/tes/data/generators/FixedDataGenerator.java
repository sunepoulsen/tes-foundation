package dk.sunepoulsen.tes.data.generators;

public class FixedDataGenerator<T> implements DataGenerator<T> {

    private final T value;

    public FixedDataGenerator(T value) {
        this.value = value;
    }

    public FixedDataGenerator(DataGenerator<T> dataGenerator) {
        this(dataGenerator.generate());
    }

    @Override
    public T generate() {
        return this.value;
    }
}
