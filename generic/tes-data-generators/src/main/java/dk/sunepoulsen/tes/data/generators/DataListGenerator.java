package dk.sunepoulsen.tes.data.generators;

import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

public class DataListGenerator<T> implements DataGenerator<List<T>> {
    private final DataGenerator<Integer> itemSizeGenerator;
    private final Function<Integer, T> transformer;

    public DataListGenerator(DataGenerator<Integer> itemSizeGenerator, Function<Integer, T> transformer) {
        this.itemSizeGenerator = itemSizeGenerator;
        this.transformer = transformer;
    }

    @Override
    public List<T> generate() {
        return IntStream.range(0, this.itemSizeGenerator.generate())
            .mapToObj(transformer::apply)
            .toList();
    }
}
