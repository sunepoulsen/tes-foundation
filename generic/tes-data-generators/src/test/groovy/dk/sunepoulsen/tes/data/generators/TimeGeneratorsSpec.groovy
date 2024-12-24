package dk.sunepoulsen.tes.data.generators

import spock.lang.Specification
import spock.lang.Unroll

import java.time.Clock
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

class TimeGeneratorsSpec extends Specification {

    private static final int ENTRIES = 10000

    private static final LocalDate LOCAL_DATE_START_YEAR = LocalDate.of(2024, 1, 1)
    private static final LocalDate LOCAL_DATE_END_YEAR = LocalDate.of(2024, 12, 31)
    private static final LocalTime LOCAL_TIME_START = LocalTime.of(0, 0, 0)
    private static final LocalTime LOCAL_TIME_END = LocalTime.of(23, 59, 59)
    private static final LocalDateTime LOCAL_DATE_TIME_START_YEAR = LocalDateTime.of(LOCAL_DATE_START_YEAR, LOCAL_TIME_START)
    private static final LocalDateTime LOCAL_DATE_TIME_END_YEAR = LocalDateTime.of(LOCAL_DATE_END_YEAR, LOCAL_TIME_END)
    private static final ZonedDateTime ZONED_DATE_TIME_START_YEAR = ZonedDateTime.of(LOCAL_DATE_TIME_START_YEAR, ZoneId.of('UTC'))
    private static final ZonedDateTime ZONED_DATE_TIME_END_YEAR = ZonedDateTime.of(LOCAL_DATE_TIME_END_YEAR, ZoneId.of('UTC'))
    private static final Clock CLOCK = Clock.fixed(LocalDateTime.of(2024, 3, 7, 12, 35, 47).toInstant(ZoneOffset.of('Z')), ZoneId.of('UTC'))

    @Unroll
    void "Test of LocalDate generators: #_testcase"() {
        when:
            List<LocalDate> result = (1..ENTRIES).collect {
                _generator.generate()
            }

        then:
            result.size() == ENTRIES
            result.stream().anyMatch {!it.isEqual(result.first) }
            result.each {
                assert it.isEqual(_min) || it.isAfter(_min)
                assert it.isBefore(_max)
            }

        where:
            _testcase                           | _generator                                                                                  | _min                  | _max
            'LocalDate with interval'           | TimeGenerators.dateGenerator(LOCAL_DATE_START_YEAR, LOCAL_DATE_END_YEAR)                    | LOCAL_DATE_START_YEAR | LOCAL_DATE_END_YEAR
            'LocalDate with interval of months' | TimeGenerators.dateGenerator(LOCAL_DATE_START_YEAR, LOCAL_DATE_END_YEAR, ChronoUnit.MONTHS) | LOCAL_DATE_START_YEAR | LOCAL_DATE_END_YEAR
    }

    @Unroll
    void "Test of current LocalDate generators: #_testcase"() {
        when:
            List<LocalDate> result = (1..ENTRIES).collect {
                _generator.generate()
            }

        then:
            result.size() == ENTRIES
            result.stream().allMatch {it.isEqual(result.first) }
            result.each {
                assert it.isEqual(_expected)
            }

        where:
            _testcase      | _generator                                            | _expected
            'No time zone' | TimeGenerators.currentDateGenerator()                 | LocalDate.now()
            'Time zone'    | TimeGenerators.currentDateGenerator(ZoneId.of('UTC')) | LocalDate.now(ZoneId.of('UTC'))
            'Clock'        | TimeGenerators.currentDateGenerator(CLOCK)            | LocalDate.now(CLOCK)
    }

    @Unroll
    void "Test of LocalTime generators: #_testcase"() {
        when:
            List<LocalTime> result = (1..ENTRIES).collect {
                _generator.generate()
            }

        then:
            result.size() == ENTRIES
            result.stream().anyMatch {it != result.first }
            result.each {
                assert it == _min || it.isAfter(_min)
                assert it.isBefore(_max)
            }

        where:
            _testcase                          | _generator                                                                       | _min             | _max
            'LocalTime with interval'          | TimeGenerators.timeGenerator(LOCAL_TIME_START, LOCAL_TIME_END)                   | LOCAL_TIME_START | LOCAL_TIME_END
            'LocalTime with interval of hours' | TimeGenerators.timeGenerator(LOCAL_TIME_START, LOCAL_TIME_END, ChronoUnit.HOURS) | LOCAL_TIME_START | LOCAL_TIME_END
    }

    @Unroll
    void "Test of current LocalTime generators: #_testcase"() {
        when:
            List<LocalTime> result = (1..ENTRIES).collect {
                _generator.generate()
            }

        then:
            result.size() == ENTRIES
            result.stream().allMatch {it == result.first }
            result.each {
                assert it == _min || it.isAfter(_min)
                assert it.isBefore(_max)
            }

        where:
            _testcase      | _generator                                            | _min                                            | _max
            'No time zone' | TimeGenerators.currentTimeGenerator()                 | LocalTime.now().minusSeconds(1)                 | LocalTime.now().plusSeconds(1)
            'Time zone'    | TimeGenerators.currentTimeGenerator(ZoneId.of('UTC')) | LocalTime.now(ZoneId.of('UTC')).minusSeconds(1) | LocalTime.now(ZoneId.of('UTC')).plusSeconds(1)
            'Clock'        | TimeGenerators.currentTimeGenerator(CLOCK)            | LocalTime.now(CLOCK)                            | LocalTime.now(CLOCK).plusSeconds(1)
    }

    @Unroll
    void "Test of LocalDateTime generators: #_testcase"() {
        when:
            List<LocalDateTime> result = (1..ENTRIES).collect {
                _generator.generate()
            }

        then:
            result.size() == ENTRIES
            result.stream().anyMatch {it != result.first }
            result.each {
                assert it == _min || it.isAfter(_min)
                assert it.isBefore(_max)
            }

        where:
            _testcase                              | _generator                                                                                               | _min                       | _max
            'LocalDateTime with interval'          | TimeGenerators.dateTimeGenerator(LOCAL_DATE_TIME_START_YEAR, LOCAL_DATE_TIME_END_YEAR)                   | LOCAL_DATE_TIME_START_YEAR | LOCAL_DATE_TIME_END_YEAR
            'LocalDateTime with interval of hours' | TimeGenerators.dateTimeGenerator(LOCAL_DATE_TIME_START_YEAR, LOCAL_DATE_TIME_END_YEAR, ChronoUnit.HOURS) | LOCAL_DATE_TIME_START_YEAR | LOCAL_DATE_TIME_END_YEAR
    }

    @Unroll
    void "Test of current LocalDateTime generators: #_testcase"() {
        when:
            List<LocalDateTime> result = (1..ENTRIES).collect {
                _generator.generate()
            }

        then:
            result.size() == ENTRIES
            result.stream().allMatch {it == result.first }
            result.each {
                assert it == _min || it.isAfter(_min)
                assert it.isBefore(_max)
            }

        where:
            _testcase      | _generator                                                | _min                                                | _max
            'No time zone' | TimeGenerators.currentDateTimeGenerator()                 | LocalDateTime.now().minusSeconds(1)                 | LocalDateTime.now().plusSeconds(1)
            'Time zone'    | TimeGenerators.currentDateTimeGenerator(ZoneId.of('UTC')) | LocalDateTime.now(ZoneId.of('UTC')).minusSeconds(1) | LocalDateTime.now(ZoneId.of('UTC')).plusSeconds(1)
            'Clock'        | TimeGenerators.currentDateTimeGenerator(CLOCK)            | LocalDateTime.now(CLOCK)                            | LocalDateTime.now(CLOCK).plusSeconds(1)
    }

    @Unroll
    void "Test of ZonedDateTime generators: #_testcase"() {
        when:
            List<ZonedDateTime> result = (1..ENTRIES).collect {
                _generator.generate()
            }

        then:
            result.size() == ENTRIES
            result.stream().anyMatch {it != result.first }
            result.each {
                assert it == _min || it.isAfter(_min)
                assert it.isBefore(_max)
            }

        where:
            _testcase                              | _generator                                                                                                    | _min                       | _max
            'ZonedDateTime with interval'          | TimeGenerators.zonedDateTimeGenerator(ZONED_DATE_TIME_START_YEAR, ZONED_DATE_TIME_END_YEAR)                   | ZONED_DATE_TIME_START_YEAR | ZONED_DATE_TIME_END_YEAR
            'ZonedDateTime with interval of hours' | TimeGenerators.zonedDateTimeGenerator(ZONED_DATE_TIME_START_YEAR, ZONED_DATE_TIME_END_YEAR, ChronoUnit.HOURS) | ZONED_DATE_TIME_START_YEAR | ZONED_DATE_TIME_END_YEAR
    }

    @Unroll
    void "Test of current ZonedDateTime generators: #_testcase"() {
        when:
            List<ZonedDateTime> result = (1..ENTRIES).collect {
                _generator.generate()
            }

        then:
            result.size() == ENTRIES
            result.stream().allMatch {it == result.first }
            result.each {
                assert it == _min || it.isAfter(_min)
                assert it.isBefore(_max)
            }

        where:
            _testcase      | _generator                                                     | _min                                                | _max
            'No time zone' | TimeGenerators.currentZonedDateTimeGenerator()                 | ZonedDateTime.now().minusSeconds(1)                 | ZonedDateTime.now().plusSeconds(1)
            'Time zone'    | TimeGenerators.currentZonedDateTimeGenerator(ZoneId.of('UTC')) | ZonedDateTime.now(ZoneId.of('UTC')).minusSeconds(1) | ZonedDateTime.now(ZoneId.of('UTC')).plusSeconds(1)
            'Clock'        | TimeGenerators.currentZonedDateTimeGenerator(CLOCK)            | ZonedDateTime.now(CLOCK)                            | ZonedDateTime.now(CLOCK).plusSeconds(1)
    }

}
