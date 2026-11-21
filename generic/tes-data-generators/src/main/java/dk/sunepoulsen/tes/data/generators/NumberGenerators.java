package dk.sunepoulsen.tes.data.generators;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Factory class to create test data generators for numbers.
 * Bounded generators validate their bounds when a value is generated and throw
 * {@link IllegalArgumentException} for invalid bounds.
 */
public class NumberGenerators {
    private NumberGenerators() {
    }

    /** @return a generator of random integers across the full integer range */
    public static DataGenerator<Integer> integerGenerator() {
        SecureRandom random = new SecureRandom();
        return Generators.supplierGenerator(random::nextInt);
    }

    /** @param bound exclusive upper bound, which must be positive
     * @return a generator of integers from zero to the bound */
    public static DataGenerator<Integer> integerGenerator(int bound) {
        SecureRandom random = new SecureRandom();
        return Generators.supplierGenerator(() -> random.nextInt(bound));
    }

    /** @param origin inclusive lower bound
     * @param bound exclusive upper bound, greater than the origin
     * @return a generator of integers in the range */
    public static DataGenerator<Integer> integerGenerator(int origin, int bound) {
        SecureRandom random = new SecureRandom();
        return Generators.supplierGenerator(() -> random.nextInt(origin, bound));
    }

    /** @return a generator of random longs across the full long range */
    public static DataGenerator<Long> longGenerator() {
        SecureRandom random = new SecureRandom();
        return Generators.supplierGenerator(random::nextLong);
    }

    /** @param bound exclusive upper bound, which must be positive
     * @return a generator of longs from zero to the bound */
    public static DataGenerator<Long> longGenerator(long bound) {
        SecureRandom random = new SecureRandom();
        return Generators.supplierGenerator(() -> random.nextLong(bound));
    }

    /** @param origin inclusive lower bound
     * @param bound exclusive upper bound, greater than the origin
     * @return a generator of longs in the range */
    public static DataGenerator<Long> longGenerator(long origin, long bound) {
        SecureRandom random = new SecureRandom();
        return Generators.supplierGenerator(() -> random.nextLong(origin, bound));
    }

    /** @return a generator of random doubles in the range [0, 1) */
    public static DataGenerator<Double> doubleGenerator() {
        SecureRandom random = new SecureRandom();
        return Generators.supplierGenerator(random::nextDouble);
    }

    /** @param bound exclusive upper bound, which must be positive and finite
     * @return a generator of doubles from zero to the bound */
    public static DataGenerator<Double> doubleGenerator(double bound) {
        SecureRandom random = new SecureRandom();
        return Generators.supplierGenerator(() -> random.nextDouble(bound));
    }

    /** @param origin inclusive finite lower bound
     * @param bound exclusive finite upper bound, greater than the origin
     * @return a generator of doubles in the range */
    public static DataGenerator<Double> doubleGenerator(double origin, double bound) {
        SecureRandom random = new SecureRandom();
        return Generators.supplierGenerator(() -> random.nextDouble(origin, bound));
    }

    /** @return a generator of normally distributed doubles with mean zero and standard deviation one */
    public static DataGenerator<Double> gaussianGenerator() {
        SecureRandom random = new SecureRandom();
        return Generators.supplierGenerator(random::nextGaussian);
    }

    /** @param mean mean of the distribution
     * @param stddev nonnegative standard deviation
     * @return a generator of normally distributed doubles */
    public static DataGenerator<Double> gaussianGenerator(double mean, double stddev) {
        SecureRandom random = new SecureRandom();
        return Generators.supplierGenerator(() -> random.nextGaussian(mean, stddev));
    }

    /** @return a generator of random floats in the range [0, 1) */
    public static DataGenerator<Float> floatGenerator() {
        SecureRandom random = new SecureRandom();
        return Generators.supplierGenerator(random::nextFloat);
    }

    /** @param bound exclusive upper bound, which must be positive and finite
     * @return a generator of floats from zero to the bound */
    public static DataGenerator<Float> floatGenerator(float bound) {
        SecureRandom random = new SecureRandom();
        return Generators.supplierGenerator(() -> random.nextFloat(bound));
    }

    /** @param origin inclusive finite lower bound
     * @param bound exclusive finite upper bound, greater than the origin
     * @return a generator of floats in the range */
    public static DataGenerator<Float> floatGenerator(float origin, float bound) {
        SecureRandom random = new SecureRandom();
        return Generators.supplierGenerator(() -> random.nextFloat(origin, bound));
    }

    /** @return a generator of big integers converted from random longs */
    public static DataGenerator<BigInteger> bigIntegerGenerator() {
        return bigIntegerGenerator(longGenerator());
    }

    /** @param generator source of long values
     * @return a generator converting those values to big integers */
    public static DataGenerator<BigInteger> bigIntegerGenerator(DataGenerator<Long> generator) {
        return Generators.supplierGenerator(() -> BigInteger.valueOf(generator.generate()));
    }

    /** @return a generator of big decimals converted from random doubles in [0, 1) */
    public static DataGenerator<BigDecimal> bigDecimalGenerator() {
        return bigDecimalGenerator(doubleGenerator());
    }

    /** @param generator source of double values
     * @return a generator converting those values to big decimals */
    public static DataGenerator<BigDecimal> bigDecimalGenerator(DataGenerator<Double> generator) {
        return Generators.supplierGenerator(() -> BigDecimal.valueOf(generator.generate()));
    }

}
