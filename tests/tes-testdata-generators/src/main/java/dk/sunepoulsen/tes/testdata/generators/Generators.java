package dk.sunepoulsen.tes.testdata.generators;

import java.util.Arrays;
import java.util.List;
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

    public static <T> TestDataGenerator<T> supplierGenerator(Supplier<T> supplier) {
        return new TestDataSupplier<>(supplier);
    }

    public static <T> TestDataGenerator<T> fixedGenerator(T value) {
        return supplierGenerator(() -> value);
    }

    public static <T extends Enum<T>> TestDataGenerator<String> enumGenerator(Class<T> supplier) {
        List<String> values = Arrays.stream(supplier.getEnumConstants())
            .map(Enum::name)
            .toList();
        TestDataGenerator<Integer> selectorGenerator = NumberGenerators.integerGenerator(0, values.size());

        return supplierGenerator(() -> values.get(selectorGenerator.generate()));
    }

    public static TestDataGenerator<String> textGenerator(TestDataGenerator<Integer> lengthGenerator) {
        return textGenerator(CharacterGenerator.createAll(), lengthGenerator);
    }

    public static TestDataGenerator<String> textGenerator(List<String> characters, TestDataGenerator<Integer> lengthGenerator) {
        return textGenerator(new CharacterGenerator(characters), lengthGenerator);
    }

    public static TestDataGenerator<String> textGenerator(CharacterGenerator characterGenerator, TestDataGenerator<Integer> lengthGenerator) {
        return new TestDataSupplier<>(() ->
            IntStream.range(0, lengthGenerator.generate())
                .mapToObj(value -> characterGenerator.generate().toString())
                .collect(Collectors.joining())
        );
    }

    public static TestDataGenerator<String> passwordGenerator() {
        return passwordGenerator(NumberGenerators.integerGenerator(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH + 1));
    }

    public static TestDataGenerator<String> passwordGenerator(TestDataGenerator<Integer> lengthGenerator) {
        return textGenerator(lengthGenerator);
    }

}
