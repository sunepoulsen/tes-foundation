package dk.sunepoulsen.tes.json

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import dk.sunepoulsen.tes.json.exceptions.DecodeJsonException
import dk.sunepoulsen.tes.json.exceptions.EncodeJsonException
import spock.lang.Specification
import spock.lang.Unroll

import java.time.*

class JsonMapperSpec extends Specification {
    private static final LocalDate LOCAL_DATE = LocalDate.of(2021, 1, 20)
    private static final LocalTime LOCAL_TIME = LocalTime.of(12, 4, 12)
    private static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.of(2021, 1, 20, 12, 4, 12)
    private static final ZonedDateTime ZONED_DATE_TIME = ZonedDateTime.of(LOCAL_DATE_TIME, ZoneId.of('Z'))

    @Unroll
    void "Encode #_datatype as json"() {
        given: 'JsonUtils'
            JsonMapper jsonUtils = new JsonMapper()

        and: 'Date & Time values'

        expect:
            jsonUtils.encode(_value) == _expected

        where:
            _value                                                            | _datatype       | _expected
            new JsonTestModel(text: 'string')                                 | 'String'        | '{"text":"string"}'
            new JsonTestModel(bool: true)                                     | 'Boolean'       | '{"bool":true}'
            new JsonTestModel(intValue: 45)                                   | 'Integer'       | '{"int_value":45}'
            new JsonTestModel(longValue: 45L)                                 | 'Long'          | '{"long_value":45}'
            new JsonTestModel(doubleValue: 3.257488563254)                    | 'Double'        | '{"double_value":3.257488563254}'
            new JsonTestModel(bigInteger: BigInteger.valueOf(45L))            | 'BigInteger'    | '{"big_integer":45}'
            new JsonTestModel(bigDecimal: BigDecimal.valueOf(3.257488563254)) | 'BigDecimal'    | '{"big_decimal":3.257488563254}'
            new JsonTestModel(localDate: LOCAL_DATE)                          | 'LocalDate'     | '{"local_date":"2021-01-20"}'
            new JsonTestModel(localTime: LOCAL_TIME)                          | 'LocalTime'     | '{"local_time":"12:04:12"}'
            new JsonTestModel(localDateTime: LOCAL_DATE_TIME)                 | 'LocalDateTime' | '{"local_date_time":"2021-01-20T12:04:12"}'
            new JsonTestModel(zonedDateTime: ZONED_DATE_TIME)                 | 'ZonedDateTime' | '{"zoned_date_time":"2021-01-20T12:04:12Z"}'
    }

    @Unroll
    void "Decode #_datatype from json"() {
        given: 'JsonUtils'
            JsonMapper jsonUtils = new JsonMapper()

        expect:
            jsonUtils.decode(_value, JsonTestModel) == _expected

        where:
            _value                                       | _datatype       | _expected
            '{"text":"string"}'                          | 'String'        | new JsonTestModel(text: 'string')
            '{"bool":true}'                              | 'Boolean'       | new JsonTestModel(bool: true)
            '{"int_value":45}'                           | 'Integer'       | new JsonTestModel(intValue: 45)
            '{"long_value":45}'                          | 'Long'          | new JsonTestModel(longValue: 45L)
            '{"double_value":3.257488563254}'            | 'Double'        | new JsonTestModel(doubleValue: 3.257488563254)
            '{"big_integer":45}'                         | 'BigInteger'    | new JsonTestModel(bigInteger: BigInteger.valueOf(45L))
            '{"big_decimal":3.257488563254}'             | 'BigDecimal'    | new JsonTestModel(bigDecimal: BigDecimal.valueOf(3.257488563254))
            '{"local_date":"2021-01-20"}'                | 'LocalDate'     | new JsonTestModel(localDate: LOCAL_DATE)
            '{"local_time":"12:04:12"}'                  | 'LocalTime'     | new JsonTestModel(localTime: LOCAL_TIME)
            '{"local_date_time":"2021-01-20T12:04:12"}'  | 'LocalDateTime' | new JsonTestModel(localDateTime: LOCAL_DATE_TIME)
            '{"zoned_date_time":"2021-01-20T12:04:12Z"}' | 'ZonedDateTime' | new JsonTestModel(zonedDateTime: ZONED_DATE_TIME)
    }

    void "Encode from json with exception"() {
        given: 'JsonUtils'
            ObjectMapper mockObjectMapper = Mock(ObjectMapper)
            JsonMapper jsonUtils = new JsonMapper(mockObjectMapper)

        when:
            jsonUtils.encode(new JsonTestModel())

        then:
            thrown(EncodeJsonException)
            1 * mockObjectMapper.writeValueAsString(_) >> {
                throw new JsonProcessingException('message')
            }
    }

    void "Decode from json with exception"() {
        given: 'JsonUtils'
            JsonMapper jsonUtils = new JsonMapper()

        when:
            jsonUtils.decode('{this is not valid json...', JsonTestModel)

        then:
            thrown(DecodeJsonException)
    }

    void "Encode static shortcut"() {
        expect:
            JsonMapper.encodeAsJson([prop: 'string']) == '{"prop":"string"}'
    }

    void "Decode static shortcut"() {
        expect:
            JsonMapper.decodeJson('{"text":"string"}', JsonTestModel) == new JsonTestModel(text: 'string')
    }
}
