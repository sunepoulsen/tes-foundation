package dk.sunepoulsen.tes.testdata.generators;

import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

public class TestDataListGenerator<T> implements TestDataGenerator<List<T>> {
    private final TestDataGenerator<Integer> itemSizeGenerator;
    private final Function<Integer, T> transformer;

    public TestDataListGenerator(TestDataGenerator<Integer> itemSizeGenerator, Function<Integer, T> transformer) {
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
