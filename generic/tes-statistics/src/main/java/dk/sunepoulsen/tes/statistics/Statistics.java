package dk.sunepoulsen.tes.statistics;

import java.util.Collection;

public class Statistics {
    private Statistics() {
    }

    public static <T extends Number> double mean(Collection<T> collection) {
        if (collection.isEmpty()) {
            return 0.0;
        }

        return collection.stream()
            .mapToDouble(Number::doubleValue)
            .average().orElseThrow();
    }

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
