package dk.sunepoulsen.tes.data.generators;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Random;

/**
 * Factory class to create test data generators for date and times.
 */
public class TimeGenerators {
    private TimeGenerators() {
    }

    public static DataGenerator<LocalDate> dateGenerator(LocalDate fromDate, LocalDate toDate) {
        return dateGenerator(fromDate, toDate, ChronoUnit.DAYS);
    }

    public static DataGenerator<LocalDate> dateGenerator(LocalDate fromDate, LocalDate toDate, TemporalUnit temporalUnit) {
        Random random = new Random();
        long units = fromDate.until(toDate, temporalUnit);
        return Generators.supplierGenerator(() -> fromDate.plus(random.nextLong(units), temporalUnit));
    }

    public static DataGenerator<LocalTime> timeGenerator(LocalTime fromTime, LocalTime toTime) {
        return timeGenerator(fromTime, toTime, ChronoUnit.SECONDS);
    }

    public static DataGenerator<LocalTime> timeGenerator(LocalTime fromTime, LocalTime toTime, TemporalUnit temporalUnit) {
        Random random = new Random();
        long units = fromTime.until(toTime, temporalUnit);
        return Generators.supplierGenerator(() -> fromTime.plus(random.nextLong(units), temporalUnit));
    }

    public static DataGenerator<LocalDateTime> dateTimeGenerator(LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        return dateTimeGenerator(fromDateTime, toDateTime, ChronoUnit.SECONDS);
    }

    public static DataGenerator<LocalDateTime> dateTimeGenerator(LocalDateTime fromDateTime, LocalDateTime toDateTime, TemporalUnit temporalUnit) {
        Random random = new Random();
        long units = fromDateTime.until(toDateTime, temporalUnit);
        return Generators.supplierGenerator(() -> fromDateTime.plus(random.nextLong(units), temporalUnit));
    }

    public static DataGenerator<ZonedDateTime> zonedDateTimeGenerator(ZonedDateTime fromZonedDateTime, ZonedDateTime toZonedDateTime) {
        return zonedDateTimeGenerator(fromZonedDateTime, toZonedDateTime, ChronoUnit.SECONDS);
    }

    public static DataGenerator<ZonedDateTime> zonedDateTimeGenerator(ZonedDateTime fromZonedDateTime, ZonedDateTime toZonedDateTime, TemporalUnit temporalUnit) {
        Random random = new Random();
        long units = fromZonedDateTime.until(toZonedDateTime, temporalUnit);
        return Generators.supplierGenerator(() -> fromZonedDateTime.plus(random.nextLong(units), temporalUnit));
    }

    public static DataGenerator<LocalDate> currentDateGenerator() {
        return Generators.fixedGenerator(LocalDate.now());
    }

    public static DataGenerator<LocalDate> currentDateGenerator(ZoneId zoneId) {
        return Generators.fixedGenerator(LocalDate.now(zoneId));
    }

    public static DataGenerator<LocalDate> currentDateGenerator(Clock clock) {
        return Generators.fixedGenerator(LocalDate.now(clock));
    }

    public static DataGenerator<LocalTime> currentTimeGenerator() {
        return Generators.fixedGenerator(LocalTime.now());
    }

    public static DataGenerator<LocalTime> currentTimeGenerator(ZoneId zoneId) {
        return Generators.fixedGenerator(LocalTime.now(zoneId));
    }

    public static DataGenerator<LocalTime> currentTimeGenerator(Clock clock) {
        return Generators.fixedGenerator(LocalTime.now(clock));
    }

    public static DataGenerator<LocalDateTime> currentDateTimeGenerator() {
        return Generators.fixedGenerator(LocalDateTime.now());
    }

    public static DataGenerator<LocalDateTime> currentDateTimeGenerator(ZoneId zoneId) {
        return Generators.fixedGenerator(LocalDateTime.now(zoneId));
    }

    public static DataGenerator<LocalDateTime> currentDateTimeGenerator(Clock clock) {
        return Generators.fixedGenerator(LocalDateTime.now(clock));
    }

    public static DataGenerator<ZonedDateTime> currentZonedDateTimeGenerator() {
        return Generators.fixedGenerator(ZonedDateTime.now());
    }

    public static DataGenerator<ZonedDateTime> currentZonedDateTimeGenerator(ZoneId zoneId) {
        return Generators.fixedGenerator(ZonedDateTime.now(zoneId));
    }

    public static DataGenerator<ZonedDateTime> currentZonedDateTimeGenerator(Clock clock) {
        return Generators.fixedGenerator(ZonedDateTime.now(clock));
    }

}
