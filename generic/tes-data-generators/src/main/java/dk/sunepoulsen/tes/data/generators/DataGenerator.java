package dk.sunepoulsen.tes.data.generators;

/**
 * Represents a test data generator.
 * <p>
 *     A test data generator can generate a single value. There is no requirement that a new or distinct result
 *     be returned each time the generator is invoked.
 * </p>
 *
 * @param <T> The data type of the generated value.
 */
public interface DataGenerator<T> {
    /**
     * Generates and returns a single value.
     */
    T generate();
}
