package dk.sunepoulsen.tes.data.generators;

public record DataProbabilityGeneratorItem<T>(double probability, DataGenerator<T> generator) {
}
