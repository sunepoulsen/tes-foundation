package dk.sunepoulsen.tes.data.generators;

/**
 * Returns the same value on every generation.
 *
 * @param <T> type of the generated value
 */
public class FixedDataGenerator<T> implements DataGenerator<T> {

    /** Value returned by the generator. */
    private final T value;

    /**
     * Creates a generator for a fixed value.
     *
     * @param value value to return
     */
    public FixedDataGenerator(T value) {
        this.value = value;
    }

    /**
     * Creates a generator using one value generated at construction time.
     *
     * @param dataGenerator generator supplying the fixed value
     */
    public FixedDataGenerator(DataGenerator<T> dataGenerator) {
        this(dataGenerator.generate());
    }

    /** {@inheritDoc} */
    @Override
    public T generate() {
        return this.value;
    }
}
