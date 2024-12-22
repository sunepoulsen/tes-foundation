package dk.sunepoulsen.tes.testdata.generators;

import lombok.Getter;

import java.util.List;
import java.util.Random;

/**
 * Generator to produce test data based on probability of other test data generators.
 * <p>
 *     This test data generator generates its test data from other test data generators based on
 *     a probability associated to each test data generator.
 * <p>
 *     The use case of this test data generator is to produce different set of values based on probabilities.
 *     One example is an optional test data generator where 20% of the values should be null. Another use case
 *     is for incomes where 10% of the incomes should be very high, 20% very low and the rest should be more
 *     normal incomes.
 *
 * @param <T> Data type of the generated values.
 */
public class TestDataProbabilityGenerator<T> implements TestDataGenerator<T> {
    private final List<TestDataProbabilityGeneratorItem<T>> generators;
    private final Random random;

    public TestDataProbabilityGenerator(List<TestDataProbabilityGeneratorItem<T>> generators) {
        this.generators = generators;
        this.random = new Random();
    }

    @Override
    public T generate() {
        validateProbabilities();
        double probability = random.nextDouble(0.0, 100.0);

        double minProbability = 0.0;
        for (TestDataProbabilityGeneratorItem<T> item : generators) {
            double maxProbability = minProbability + item.probability();
            if (minProbability < probability && probability < maxProbability) {
                return item.generator().generate();
            } else {
                minProbability = maxProbability;
            }
        }

        throw new IllegalArgumentException("Probabilities are out of range");
    }

    private void validateProbabilities() {
        double sum = generators.stream()
            .mapToDouble(TestDataProbabilityGeneratorItem::probability)
            .sum();

        if (sum != 100.0) {
            throw new IllegalArgumentException("Probabilities not sum to 100");
        }
    }
}
