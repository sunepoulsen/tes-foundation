package dk.sunepoulsen.tes.data.generators;

/**
 * Associates a probability weight with a data generator.
 *
 * @param <T> type of the generated values
 * @param probability weight used when selecting the generator
 * @param generator generator associated with the weight
 */
public record DataProbabilityGeneratorItem<T>(double probability, DataGenerator<T> generator) {
}
