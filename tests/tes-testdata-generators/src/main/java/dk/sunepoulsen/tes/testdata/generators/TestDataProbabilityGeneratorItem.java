package dk.sunepoulsen.tes.testdata.generators;

public record TestDataProbabilityGeneratorItem<T>(double probability, TestDataGenerator<T> generator) {
}
