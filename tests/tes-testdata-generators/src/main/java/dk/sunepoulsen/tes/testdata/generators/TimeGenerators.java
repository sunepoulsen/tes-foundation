package dk.sunepoulsen.tes.testdata.generators;

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

    public static TestDataGenerator<LocalDate> dateGenerator(LocalDate fromDate, LocalDate toDate) {
        return dateGenerator(fromDate, toDate, ChronoUnit.DAYS);
    }

    public static TestDataGenerator<LocalDate> dateGenerator(LocalDate fromDate, LocalDate toDate, TemporalUnit temporalUnit) {
        Random random = new Random();
        long units = fromDate.until(toDate, temporalUnit);
        return Generators.supplierGenerator(() -> fromDate.plus(random.nextLong(units), temporalUnit));
    }

    public static TestDataGenerator<LocalTime> timeGenerator(LocalTime fromTime, LocalTime toTime) {
        return timeGenerator(fromTime, toTime, ChronoUnit.SECONDS);
    }

    public static TestDataGenerator<LocalTime> timeGenerator(LocalTime fromTime, LocalTime toTime, TemporalUnit temporalUnit) {
        Random random = new Random();
        long units = fromTime.until(toTime, temporalUnit);
        return Generators.supplierGenerator(() -> fromTime.plus(random.nextLong(units), temporalUnit));
    }

    public static TestDataGenerator<LocalDateTime> dateTimeGenerator(LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        return dateTimeGenerator(fromDateTime, toDateTime, ChronoUnit.SECONDS);
    }

    public static TestDataGenerator<LocalDateTime> dateTimeGenerator(LocalDateTime fromDateTime, LocalDateTime toDateTime, TemporalUnit temporalUnit) {
        Random random = new Random();
        long units = fromDateTime.until(toDateTime, temporalUnit);
        return Generators.supplierGenerator(() -> fromDateTime.plus(random.nextLong(units), temporalUnit));
    }

    public static TestDataGenerator<ZonedDateTime> zonedDateTimeGenerator(ZonedDateTime fromZonedDateTime, ZonedDateTime toZonedDateTime) {
        return zonedDateTimeGenerator(fromZonedDateTime, toZonedDateTime, ChronoUnit.SECONDS);
    }

    public static TestDataGenerator<ZonedDateTime> zonedDateTimeGenerator(ZonedDateTime fromZonedDateTime, ZonedDateTime toZonedDateTime, TemporalUnit temporalUnit) {
        Random random = new Random();
        long units = fromZonedDateTime.until(toZonedDateTime, temporalUnit);
        return Generators.supplierGenerator(() -> fromZonedDateTime.plus(random.nextLong(units), temporalUnit));
    }

    public static TestDataGenerator<LocalDate> currentDateGenerator() {
        return Generators.fixedGenerator(LocalDate.now());
    }

    public static TestDataGenerator<LocalDate> currentDateGenerator(ZoneId zoneId) {
        return Generators.fixedGenerator(LocalDate.now(zoneId));
    }

    public static TestDataGenerator<LocalDate> currentDateGenerator(Clock clock) {
        return Generators.fixedGenerator(LocalDate.now(clock));
    }

    public static TestDataGenerator<LocalTime> currentTimeGenerator() {
        return Generators.fixedGenerator(LocalTime.now());
    }

    public static TestDataGenerator<LocalTime> currentTimeGenerator(ZoneId zoneId) {
        return Generators.fixedGenerator(LocalTime.now(zoneId));
    }

    public static TestDataGenerator<LocalTime> currentTimeGenerator(Clock clock) {
        return Generators.fixedGenerator(LocalTime.now(clock));
    }

    public static TestDataGenerator<LocalDateTime> currentDateTimeGenerator() {
        return Generators.fixedGenerator(LocalDateTime.now());
    }

    public static TestDataGenerator<LocalDateTime> currentDateTimeGenerator(ZoneId zoneId) {
        return Generators.fixedGenerator(LocalDateTime.now(zoneId));
    }

    public static TestDataGenerator<LocalDateTime> currentDateTimeGenerator(Clock clock) {
        return Generators.fixedGenerator(LocalDateTime.now(clock));
    }

    public static TestDataGenerator<ZonedDateTime> currentZonedDateTimeGenerator() {
        return Generators.fixedGenerator(ZonedDateTime.now());
    }

    public static TestDataGenerator<ZonedDateTime> currentZonedDateTimeGenerator(ZoneId zoneId) {
        return Generators.fixedGenerator(ZonedDateTime.now(zoneId));
    }

    public static TestDataGenerator<ZonedDateTime> currentZonedDateTimeGenerator(Clock clock) {
        return Generators.fixedGenerator(ZonedDateTime.now(clock));
    }

}
