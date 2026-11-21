package dk.sunepoulsen.tes.data.generators;

import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * Generates a list by transforming each index from zero up to the generated size.
 *
 * @param <T> type of the generated elements
 */
public class DataListGenerator<T> implements DataGenerator<List<T>> {
    /** Generates the upper bound for the list indices. */
    private final DataGenerator<Integer> itemSizeGenerator;
    /** Transforms each index into a list element. */
    private final Function<Integer, T> transformer;

    /**
     * Creates a list generator.
     *
     * @param itemSizeGenerator generator of the list size
     * @param transformer function applied to each index
     */
    public DataListGenerator(DataGenerator<Integer> itemSizeGenerator, Function<Integer, T> transformer) {
        this.itemSizeGenerator = itemSizeGenerator;
        this.transformer = transformer;
    }

    /** {@inheritDoc} */
    @Override
    public List<T> generate() {
        return IntStream.range(0, this.itemSizeGenerator.generate())
            .mapToObj(transformer::apply)
            .toList();
    }
}
