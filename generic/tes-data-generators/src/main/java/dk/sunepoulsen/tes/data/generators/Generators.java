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

    /** @param <T> generated value type
     * @param supplier source invoked for each value
     * @return a generator backed by the supplier */
    public static <T> DataGenerator<T> supplierGenerator(Supplier<T> supplier) {
        return new DataSupplier<>(supplier);
    }

    /** @param <T> generated value type
     * @param value value to return
     * @return a generator returning the fixed value */
    public static <T> FixedDataGenerator<T> fixedGenerator(T value) {
        return new FixedDataGenerator<>(value);
    }

    /** @param <T> generated value type
     * @param dataGenerator source used to obtain the fixed value
     * @return a generator returning that value */
    public static <T> FixedDataGenerator<T> fixedGenerator(DataGenerator<T> dataGenerator) {
        return new FixedDataGenerator<>(dataGenerator);
    }

    /** @param <T> enum type
     * @param supplier enum class
     * @return a generator selecting enum constant names */
    public static <T extends Enum<T>> DataGenerator<String> enumGenerator(Class<T> supplier) {
        List<String> values = Arrays.stream(supplier.getEnumConstants())
            .map(Enum::name)
            .toList();
        DataGenerator<Integer> selectorGenerator = NumberGenerators.integerGenerator(0, values.size());

        return supplierGenerator(() -> values.get(selectorGenerator.generate()));
    }

    /** @param lengthGenerator source of text lengths
     * @return a text generator using all configured character sets */
    public static DataGenerator<String> textGenerator(DataGenerator<Integer> lengthGenerator) {
        return textGenerator(CharacterGenerator.createAll(), lengthGenerator);
    }

    /** @param characters character sets used by the character generator
     * @param lengthGenerator source of text lengths
     * @return a text generator */
    public static DataGenerator<String> textGenerator(List<String> characters, DataGenerator<Integer> lengthGenerator) {
        return textGenerator(new CharacterGenerator(characters), lengthGenerator);
    }

    /** @param characterGenerator source of characters
     * @param lengthGenerator source of text lengths
     * @return a generator joining the requested number of characters */
    public static DataGenerator<String> textGenerator(CharacterGenerator characterGenerator, DataGenerator<Integer> lengthGenerator) {
        return new DataSupplier<>(() ->
            IntStream.range(0, lengthGenerator.generate())
                .mapToObj(value -> characterGenerator.generate().toString())
                .collect(Collectors.joining())
        );
    }

    /** @return an alphanumeric password generator with the default length range */
    public static DataGenerator<String> passwordGenerator() {
        return passwordGenerator(CharacterGenerator.ALL_ALPHA_DIGITS);
    }

    /** @param characters characters available for selection
     * @return a password generator with the default length range */
    public static DataGenerator<String> passwordGenerator(String characters) {
        return passwordGenerator(characters, NumberGenerators.integerGenerator(PASSWORD_MIN_LENGTH, PASSWORD_MAX_LENGTH + 1));
    }

    /** @param characters characters available for selection
     * @param lengthGenerator source of password lengths
     * @return a password generator */
    public static DataGenerator<String> passwordGenerator(String characters, DataGenerator<Integer> lengthGenerator) {
        return textGenerator(List.of(characters), lengthGenerator);
    }

    /** @return a generator of random UUIDs */
    public static DataGenerator<UUID> uuidGenerator() {
        return supplierGenerator(UUID::randomUUID);
    }

}
