package dk.sunepoulsen.tes.statistics

import spock.lang.Specification

class StatisticsSpec extends Specification {

    void "Test mean value of collection"() {
        expect:
            Statistics.mean([]) as Double == 0.0
            Statistics.mean([2.0, 4.0, 4.0, 4.0, 5.0, 5.0, 7.0, 9.0]) as Double == 5.0
    }

    void "Test standard deviation value of collection"() {
        expect:
            Statistics.standardDeviation([]) as Double == 0.0
            Statistics.standardDeviation([2.0, 4.0, 4.0, 4.0, 5.0, 5.0, 7.0, 9.0]) as Double == 2.0
    }

}
