package dk.sunepoulsen.tes.testdata.generators

import dk.sunepoulsen.tes.statistics.Statistics
import spock.lang.Specification
import spock.lang.Unroll

class NumberGeneratorsSpec extends Specification {

    private static final int ENTRIES = 10000

    @Unroll
    void "Test NumberGenerator generators: #_testcase"() {
        when:
            def result = (1..ENTRIES).collect {
                _generator.generate()
            }

        then:
            result.size() == ENTRIES
            result.each {
                assert it >= _min && it < _max
            }
            Statistics.standardDeviation(result) > 0.0

        where:
            _testcase                  | _generator                                                                        | _min              | _max
            'Integer - No bounds'      | NumberGenerators.integerGenerator()                                               | Integer.MIN_VALUE | Integer.MAX_VALUE
            'Integer - With bound'     | NumberGenerators.integerGenerator(1_000_000)                                      | 0                 | 1_000_000
            'Integer - With interval'  | NumberGenerators.integerGenerator(50_000, 100_000)                                | 50_000            | 100_000
            'Long - No bounds'         | NumberGenerators.longGenerator()                                                  | Long.MIN_VALUE    | Long.MAX_VALUE
            'Long - With bound'        | NumberGenerators.longGenerator(1_000_000L)                                        | 0L                | 1_000_000L
            'Long - With interval'     | NumberGenerators.longGenerator(50_000L, 100_000L)                                 | 50_000L           | 100_000L
            'Double - No bounds'       | NumberGenerators.doubleGenerator()                                                | 0.0               | 1.0
            'Double - With bound'      | NumberGenerators.doubleGenerator(100_000.0)                                       | 0.0               | 100_000.0
            'Double - With interval'   | NumberGenerators.doubleGenerator(50_000.0, 100_000.0)                             | 50_000.0          | 100_000.0
            'Float - No bounds'        | NumberGenerators.floatGenerator()                                                 | 0.0               | 1.0
            'Float - With bound'       | NumberGenerators.floatGenerator(100_000.0)                                        | 0.0               | 100_000.0
            'Float - With interval'    | NumberGenerators.floatGenerator(50_000.0, 100_000.0)                              | 50_000.0          | 100_000.0
            'BigInteger - No bounds'   | NumberGenerators.bigIntegerGenerator()                                            | Long.MIN_VALUE    | Long.MAX_VALUE
            'BigInteger - With bounds' | NumberGenerators.bigIntegerGenerator(NumberGenerators.longGenerator(100_000L))    | 0L                | 100_000L
            'BigDecimal - No bounds'   | NumberGenerators.bigDecimalGenerator()                                            | 0.0               | 1.0
            'BigDecimal - With bounds' | NumberGenerators.bigDecimalGenerator(NumberGenerators.doubleGenerator(100_000.0)) | 0.0               | 100_000.0
    }

    @Unroll
    void "Test NumberGenerator generators of gaussian: #_testcase"() {
        when:
            def result = (1..ENTRIES).collect {
                _generator.generate()
            }

        then:
            result.size() == ENTRIES
            double mean = Statistics.mean(result)
            mean >= _mean - _stddev
            mean < _mean + _stddev

        where:
            _testcase                          | _generator                                             | _mean    | _stddev
            'No bounds'                        | NumberGenerators.gaussianGenerator()                   | 0.0      | 1.0
            'With mean and standard deviation' | NumberGenerators.gaussianGenerator(50_000.0, 10_000.0) | 50_000.0 | 10_000.0
    }
}
