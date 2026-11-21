package dk.sunepoulsen.tes.statistics;

import java.util.Collection;

/** Statistical calculations for collections of numbers. */
public class Statistics {
    private Statistics() {
    }

    /**
     * Calculates the arithmetic mean of the values converted to doubles.
     * @param <T> number type
     * @param collection values to average
     * @return the mean, or zero for an empty collection
     */
    public static <T extends Number> double mean(Collection<T> collection) {
        if (collection.isEmpty()) {
            return 0.0;
        }

        return collection.stream()
            .mapToDouble(Number::doubleValue)
            .average().orElseThrow();
    }

    /**
     * Calculates the population standard deviation of the values converted to doubles.
     * @param <T> number type
     * @param collection values to measure
     * @return the standard deviation, or zero for an empty collection
     */
    public static <T extends Number> double standardDeviation(Collection<T> collection) {
        if (collection.isEmpty()) {
            return 0.0;
        }

        double mean = mean(collection);

        double variance =  collection.parallelStream()
            .mapToDouble(Number::doubleValue)
            .map(x -> Math.pow(x - mean, 2))
            .average()
            .orElseThrow();

        return Math.sqrt(variance);
    }
}
