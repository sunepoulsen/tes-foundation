package dk.sunepoulsen.tes.statistics;

import java.util.Collection;

public class Statistics {
    private Statistics() {
    }

    public static <T extends Number> double mean(Collection<T> collection) {
        return collection.stream()
            .mapToDouble(Number::doubleValue)
            .average().orElseThrow();
    }

    public static <T extends Number> double standardDeviation(Collection<T> collection) {
        double mean = mean(collection);

        return collection.stream()
            .mapToDouble(Number::doubleValue)
            .map(x -> Math.pow(x - mean, 2))
            .average().orElseThrow();
    }
}
