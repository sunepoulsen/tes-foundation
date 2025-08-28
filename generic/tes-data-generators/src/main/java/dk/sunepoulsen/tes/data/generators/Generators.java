package dk.sunepoulsen.tes.data.generators;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Factory class to create test data generators
 */
public class Generators {
    public static final int PASSWORD_MIN_LENGTH = 48;
    public static final int PASSWORD_MAX_LENGTH = 64;

    private Generators() {
    }

    public static <T> DataGenerator<T> supplierGenerator(Supplier<T> supplier) {
        return new DataSupplier<>(supplier);
    }

    public static <T> DataGenerator<T> fixedGenerator(T value) {
        return supplierGenerator(() -> value);
    }

    public static <T> DataGenerator<T> fixedGenerator(DataGenerator<T> generator) {
        return fixedGenerator(generator.generate());
    }

    public static <T extends Enum<T>> DataGenerator<String> enumGenerator(Class<T> supplier) {
        List<String> values = Arrays.stream(supplier.getEnumConstants())
            .map(Enum::name)
            .toList();
        DataGenerator<Integer> selectorGenerator = NumberGenerators.integerGenerator(0, values.size());

        return supplierGenerator(() -> values.get(selectorGenerator.generate()));
    }

    public static DataGenerator<String> textGenerator(DataGenerator<Integer> lengthGenerator) {
        return textGenerator(CharacterGenerator.createAll(), lengthGenerator);
    }

    public static DataGenerator<String> textGenerator(List<String> characters, DataGenerator<Integer> lengthGenerator) {
        return textGenerator(new CharacterGenerator(characters), lengthGenerator);
    }

    public static DataGenerator<String> textGenerator(CharacterGenerator characterGenerator, DataGenerator<Integer> lengthGenerator) {
        return new DataSupplier<>(() ->
            IntStream.range(0, lengthGenerator.generate())
                .mapToObj(value -> characterGenerator.generate().toString())
                .collect(Collectors.joining())
        );
    }

    public static DataGenerator<String> passwordGenerator() {
        return passwordGenerator(NumberGenerators.integerGenerator(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH + 1));
    }

    public static DataGenerator<String> passwordGenerator(DataGenerator<Integer> lengthGenerator) {
        return textGenerator(lengthGenerator);
    }

    public static DataGenerator<UUID> uuidGenerator() {
        return supplierGenerator(UUID::randomUUID);
    }

}
