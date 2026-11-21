package dk.sunepoulsen.tes.data.generators;

import java.security.SecureRandom;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

/**
 * Factory class to create test data generators for date and times.
 * Range generators require a positive number of whole units between their bounds;
 * otherwise generation throws {@link IllegalArgumentException}.
 */
public class TimeGenerators {
    private TimeGenerators() {
    }

    /**
     * Creates a generator of dates at day intervals, with the upper bound excluded.
     * @param fromDate inclusive start
     * @param toDate exclusive end
     * @return the date generator
     */
    public static DataGenerator<LocalDate> dateGenerator(LocalDate fromDate, LocalDate toDate) {
        return dateGenerator(fromDate, toDate, ChronoUnit.DAYS);
    }

    /**
     * Creates a generator of dates at the specified unit intervals.
     * @param fromDate inclusive start
     * @param toDate end used to calculate the exclusive number of units
     * @param temporalUnit unit used for offsets
     * @return the date generator
     */
    public static DataGenerator<LocalDate> dateGenerator(LocalDate fromDate, LocalDate toDate, TemporalUnit temporalUnit) {
        SecureRandom random = new SecureRandom();
        long units = fromDate.until(toDate, temporalUnit);
        return Generators.supplierGenerator(() -> fromDate.plus(random.nextLong(units), temporalUnit));
    }

    /**
     * Creates a generator of times at second intervals, with the upper bound excluded.
     * @param fromTime inclusive start
     * @param toTime exclusive end
     * @return the time generator
     */
    public static DataGenerator<LocalTime> timeGenerator(LocalTime fromTime, LocalTime toTime) {
        return timeGenerator(fromTime, toTime, ChronoUnit.SECONDS);
    }

    /**
     * Creates a generator of times at the specified unit intervals.
     * @param fromTime inclusive start
     * @param toTime end used to calculate the exclusive number of units
     * @param temporalUnit unit used for offsets
     * @return the time generator
     */
    public static DataGenerator<LocalTime> timeGenerator(LocalTime fromTime, LocalTime toTime, TemporalUnit temporalUnit) {
        SecureRandom random = new SecureRandom();
        long units = fromTime.until(toTime, temporalUnit);
        return Generators.supplierGenerator(() -> fromTime.plus(random.nextLong(units), temporalUnit));
    }

    /**
     * Creates a generator of date-times at second intervals, with the upper bound excluded.
     * @param fromDateTime inclusive start
     * @param toDateTime exclusive end
     * @return the date-time generator
     */
    public static DataGenerator<LocalDateTime> dateTimeGenerator(LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        return dateTimeGenerator(fromDateTime, toDateTime, ChronoUnit.SECONDS);
    }

    /**
     * Creates a generator of date-times at the specified unit intervals.
     * @param fromDateTime inclusive start
     * @param toDateTime end used to calculate the exclusive number of units
     * @param temporalUnit unit used for offsets
     * @return the date-time generator
     */
    public static DataGenerator<LocalDateTime> dateTimeGenerator(LocalDateTime fromDateTime, LocalDateTime toDateTime, TemporalUnit temporalUnit) {
        SecureRandom random = new SecureRandom();
        long units = fromDateTime.until(toDateTime, temporalUnit);
        return Generators.supplierGenerator(() -> fromDateTime.plus(random.nextLong(units), temporalUnit));
    }

    /**
     * Creates a generator of zoned date-times at second intervals, with the upper bound excluded.
     * @param fromZonedDateTime inclusive start
     * @param toZonedDateTime exclusive end
     * @return the zoned date-time generator
     */
    public static DataGenerator<ZonedDateTime> zonedDateTimeGenerator(ZonedDateTime fromZonedDateTime, ZonedDateTime toZonedDateTime) {
        return zonedDateTimeGenerator(fromZonedDateTime, toZonedDateTime, ChronoUnit.SECONDS);
    }

    /**
     * Creates a generator of zoned date-times at the specified unit intervals.
     * @param fromZonedDateTime inclusive start
     * @param toZonedDateTime end used to calculate the exclusive number of units
     * @param temporalUnit unit used for offsets
     * @return the zoned date-time generator
     */
    public static DataGenerator<ZonedDateTime> zonedDateTimeGenerator(ZonedDateTime fromZonedDateTime, ZonedDateTime toZonedDateTime, TemporalUnit temporalUnit) {
        SecureRandom random = new SecureRandom();
        long units = fromZonedDateTime.until(toZonedDateTime, temporalUnit);
        return Generators.supplierGenerator(() -> fromZonedDateTime.plus(random.nextLong(units), temporalUnit));
    }

    /** @return a generator fixed to the date at creation in the default time zone */
    public static DataGenerator<LocalDate> currentDateGenerator() {
        return Generators.fixedGenerator(LocalDate.now());
    }

    /** @param zoneId time zone used to obtain the date
     * @return a generator fixed to the date at creation */
    public static DataGenerator<LocalDate> currentDateGenerator(ZoneId zoneId) {
        return Generators.fixedGenerator(LocalDate.now(zoneId));
    }

    /** @param clock clock used to obtain the date
     * @return a generator fixed to the date at creation */
    public static DataGenerator<LocalDate> currentDateGenerator(Clock clock) {
        return Generators.fixedGenerator(LocalDate.now(clock));
    }

    /** @return a generator fixed to the time at creation in the default time zone */
    public static DataGenerator<LocalTime> currentTimeGenerator() {
        return Generators.fixedGenerator(LocalTime.now());
    }

    /** @param zoneId time zone used to obtain the time
     * @return a generator fixed to the time at creation */
    public static DataGenerator<LocalTime> currentTimeGenerator(ZoneId zoneId) {
        return Generators.fixedGenerator(LocalTime.now(zoneId));
    }

    /** @param clock clock used to obtain the time
     * @return a generator fixed to the time at creation */
    public static DataGenerator<LocalTime> currentTimeGenerator(Clock clock) {
        return Generators.fixedGenerator(LocalTime.now(clock));
    }

    /** @return a generator fixed to the date-time at creation in the default time zone */
    public static DataGenerator<LocalDateTime> currentDateTimeGenerator() {
        return Generators.fixedGenerator(LocalDateTime.now());
    }

    /** @param zoneId time zone used to obtain the date-time
     * @return a generator fixed to the date-time at creation */
    public static DataGenerator<LocalDateTime> currentDateTimeGenerator(ZoneId zoneId) {
        return Generators.fixedGenerator(LocalDateTime.now(zoneId));
    }

    /** @param clock clock used to obtain the date-time
     * @return a generator fixed to the date-time at creation */
    public static DataGenerator<LocalDateTime> currentDateTimeGenerator(Clock clock) {
        return Generators.fixedGenerator(LocalDateTime.now(clock));
    }

    /** @return a generator fixed to the zoned date-time at creation in the default time zone */
    public static DataGenerator<ZonedDateTime> currentZonedDateTimeGenerator() {
        return Generators.fixedGenerator(ZonedDateTime.now());
    }

    /** @param zoneId time zone used to obtain the date-time
     * @return a generator fixed to the zoned date-time at creation */
    public static DataGenerator<ZonedDateTime> currentZonedDateTimeGenerator(ZoneId zoneId) {
        return Generators.fixedGenerator(ZonedDateTime.now(zoneId));
    }

    /** @param clock clock used to obtain the date-time
     * @return a generator fixed to the zoned date-time at creation */
    public static DataGenerator<ZonedDateTime> currentZonedDateTimeGenerator(Clock clock) {
        return Generators.fixedGenerator(ZonedDateTime.now(clock));
    }

}
