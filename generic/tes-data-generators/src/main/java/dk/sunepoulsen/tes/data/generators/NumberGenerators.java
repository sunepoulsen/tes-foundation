package dk.sunepoulsen.tes.data.generators;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;

/**
 * Factory class to create test data generators for numbers.
 */
public class NumberGenerators {
    private NumberGenerators() {
    }

    public static DataGenerator<Integer> integerGenerator() {
        Random random = new Random();
        return Generators.supplierGenerator(random::nextInt);
    }

    public static DataGenerator<Integer> integerGenerator(int bound) {
        Random random = new Random();
        return Generators.supplierGenerator(() -> random.nextInt(bound));
    }

    public static DataGenerator<Integer> integerGenerator(int origin, int bound) {
        Random random = new Random();
        return Generators.supplierGenerator(() -> random.nextInt(origin, bound));
    }

    public static DataGenerator<Long> longGenerator() {
        Random random = new Random();
        return Generators.supplierGenerator(random::nextLong);
    }

    public static DataGenerator<Long> longGenerator(long bound) {
        Random random = new Random();
        return Generators.supplierGenerator(() -> random.nextLong(bound));
    }

    public static DataGenerator<Long> longGenerator(long origin, long bound) {
        Random random = new Random();
        return Generators.supplierGenerator(() -> random.nextLong(origin, bound));
    }

    public static DataGenerator<Double> doubleGenerator() {
        Random random = new Random();
        return Generators.supplierGenerator(random::nextDouble);
    }

    public static DataGenerator<Double> doubleGenerator(double bound) {
        Random random = new Random();
        return Generators.supplierGenerator(() -> random.nextDouble(bound));
    }

    public static DataGenerator<Double> doubleGenerator(double origin, double bound) {
        Random random = new Random();
        return Generators.supplierGenerator(() -> random.nextDouble(origin, bound));
    }

    public static DataGenerator<Double> gaussianGenerator() {
        Random random = new Random();
        return Generators.supplierGenerator(random::nextGaussian);
    }

    public static DataGenerator<Double> gaussianGenerator(double mean, double stddev) {
        Random random = new Random();
        return Generators.supplierGenerator(() -> random.nextGaussian(mean, stddev));
    }

    public static DataGenerator<Float> floatGenerator() {
        Random random = new Random();
        return Generators.supplierGenerator(random::nextFloat);
    }

    public static DataGenerator<Float> floatGenerator(float bound) {
        Random random = new Random();
        return Generators.supplierGenerator(() -> random.nextFloat(bound));
    }

    public static DataGenerator<Float> floatGenerator(float origin, float bound) {
        Random random = new Random();
        return Generators.supplierGenerator(() -> random.nextFloat(origin, bound));
    }

    public static DataGenerator<BigInteger> bigIntegerGenerator() {
        return bigIntegerGenerator(longGenerator());
    }

    public static DataGenerator<BigInteger> bigIntegerGenerator(DataGenerator<Long> generator) {
        return Generators.supplierGenerator(() -> BigInteger.valueOf(generator.generate()));
    }

    public static DataGenerator<BigDecimal> bigDecimalGenerator() {
        return bigDecimalGenerator(doubleGenerator());
    }

    public static DataGenerator<BigDecimal> bigDecimalGenerator(DataGenerator<Double> generator) {
        return Generators.supplierGenerator(() -> BigDecimal.valueOf(generator.generate()));
    }

}
