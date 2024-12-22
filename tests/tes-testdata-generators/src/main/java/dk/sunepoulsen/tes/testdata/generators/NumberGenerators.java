package dk.sunepoulsen.tes.testdata.generators;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;

/**
 * Factory class to create test data generators for numbers.
 */
public class NumberGenerators {
    private NumberGenerators() {
    }

    public static TestDataGenerator<Integer> integerGenerator() {
        Random random = new Random();
        return Generators.supplierGenerator(random::nextInt);
    }

    public static TestDataGenerator<Integer> integerGenerator(int bound) {
        Random random = new Random();
        return Generators.supplierGenerator(() -> random.nextInt(bound));
    }

    public static TestDataGenerator<Integer> integerGenerator(int origin, int bound) {
        Random random = new Random();
        return Generators.supplierGenerator(() -> random.nextInt(origin, bound));
    }

    public static TestDataGenerator<Long> longGenerator() {
        Random random = new Random();
        return Generators.supplierGenerator(random::nextLong);
    }

    public static TestDataGenerator<Long> longGenerator(long bound) {
        Random random = new Random();
        return Generators.supplierGenerator(() -> random.nextLong(bound));
    }

    public static TestDataGenerator<Long> longGenerator(long origin, long bound) {
        Random random = new Random();
        return Generators.supplierGenerator(() -> random.nextLong(origin, bound));
    }

    public static TestDataGenerator<Double> doubleGenerator() {
        Random random = new Random();
        return Generators.supplierGenerator(random::nextDouble);
    }

    public static TestDataGenerator<Double> doubleGenerator(double bound) {
        Random random = new Random();
        return Generators.supplierGenerator(() -> random.nextDouble(bound));
    }

    public static TestDataGenerator<Double> doubleGenerator(double origin, double bound) {
        Random random = new Random();
        return Generators.supplierGenerator(() -> random.nextDouble(origin, bound));
    }

    public static TestDataGenerator<Double> gaussianGenerator() {
        Random random = new Random();
        return Generators.supplierGenerator(random::nextGaussian);
    }

    public static TestDataGenerator<Double> gaussianGenerator(double mean, double stddev) {
        Random random = new Random();
        return Generators.supplierGenerator(() -> random.nextGaussian(mean, stddev));
    }

    public static TestDataGenerator<Float> floatGenerator() {
        Random random = new Random();
        return Generators.supplierGenerator(random::nextFloat);
    }

    public static TestDataGenerator<Float> floatGenerator(float bound) {
        Random random = new Random();
        return Generators.supplierGenerator(() -> random.nextFloat(bound));
    }

    public static TestDataGenerator<Float> floatGenerator(float origin, float bound) {
        Random random = new Random();
        return Generators.supplierGenerator(() -> random.nextFloat(origin, bound));
    }

    public static TestDataGenerator<BigInteger> bigIntegerGenerator() {
        return bigIntegerGenerator(longGenerator());
    }

    public static TestDataGenerator<BigInteger> bigIntegerGenerator(TestDataGenerator<Long> generator) {
        return Generators.supplierGenerator(() -> BigInteger.valueOf(generator.generate()));
    }

    public static TestDataGenerator<BigDecimal> bigDecimalGenerator() {
        return bigDecimalGenerator(doubleGenerator());
    }

    public static TestDataGenerator<BigDecimal> bigDecimalGenerator(TestDataGenerator<Double> generator) {
        return Generators.supplierGenerator(() -> BigDecimal.valueOf(generator.generate()));
    }

}
