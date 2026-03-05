package dk.sunepoulsen.tes.json

import dk.sunepoulsen.tes.json.exceptions.DecodeJsonException
import dk.sunepoulsen.tes.json.exceptions.EncodeJsonException
import spock.lang.Specification
import spock.lang.Unroll
import tools.jackson.core.JacksonException
import tools.jackson.databind.ObjectMapper

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
            new JsonTestModel(text: 'string')                                 | 'String'        | '{"bigDecimal":null,"bigInteger":null,"bool":null,"doubleValue":null,"intValue":null,"localDate":null,"localDateTime":null,"localTime":null,"longValue":null,"text":"string","zonedDateTime":null}'
            new JsonTestModel(bool: true)                                     | 'Boolean'       | '{"bigDecimal":null,"bigInteger":null,"bool":true,"doubleValue":null,"intValue":null,"localDate":null,"localDateTime":null,"localTime":null,"longValue":null,"text":null,"zonedDateTime":null}'
            new JsonTestModel(intValue: 45)                                   | 'Integer'       | '{"bigDecimal":null,"bigInteger":null,"bool":null,"doubleValue":null,"intValue":45,"localDate":null,"localDateTime":null,"localTime":null,"longValue":null,"text":null,"zonedDateTime":null}'
            new JsonTestModel(longValue: 45L)                                 | 'Long'          | '{"bigDecimal":null,"bigInteger":null,"bool":null,"doubleValue":null,"intValue":null,"localDate":null,"localDateTime":null,"localTime":null,"longValue":45,"text":null,"zonedDateTime":null}'
            new JsonTestModel(doubleValue: 3.257488563254)                    | 'Double'        | '{"bigDecimal":null,"bigInteger":null,"bool":null,"doubleValue":3.257488563254,"intValue":null,"localDate":null,"localDateTime":null,"localTime":null,"longValue":null,"text":null,"zonedDateTime":null}'
            new JsonTestModel(bigInteger: BigInteger.valueOf(45L))            | 'BigInteger'    | '{"bigDecimal":null,"bigInteger":45,"bool":null,"doubleValue":null,"intValue":null,"localDate":null,"localDateTime":null,"localTime":null,"longValue":null,"text":null,"zonedDateTime":null}'
            new JsonTestModel(bigDecimal: BigDecimal.valueOf(3.257488563254)) | 'BigDecimal'    | '{"bigDecimal":3.257488563254,"bigInteger":null,"bool":null,"doubleValue":null,"intValue":null,"localDate":null,"localDateTime":null,"localTime":null,"longValue":null,"text":null,"zonedDateTime":null}'
            new JsonTestModel(localDate: LOCAL_DATE)                          | 'LocalDate'     | '{"bigDecimal":null,"bigInteger":null,"bool":null,"doubleValue":null,"intValue":null,"localDate":"2021-01-20","localDateTime":null,"localTime":null,"longValue":null,"text":null,"zonedDateTime":null}'
            new JsonTestModel(localTime: LOCAL_TIME)                          | 'LocalTime'     | '{"bigDecimal":null,"bigInteger":null,"bool":null,"doubleValue":null,"intValue":null,"localDate":null,"localDateTime":null,"localTime":"12:04:12","longValue":null,"text":null,"zonedDateTime":null}'
            new JsonTestModel(localDateTime: LOCAL_DATE_TIME)                 | 'LocalDateTime' | '{"bigDecimal":null,"bigInteger":null,"bool":null,"doubleValue":null,"intValue":null,"localDate":null,"localDateTime":"2021-01-20T12:04:12","localTime":null,"longValue":null,"text":null,"zonedDateTime":null}'
            new JsonTestModel(zonedDateTime: ZONED_DATE_TIME)                 | 'ZonedDateTime' | '{"bigDecimal":null,"bigInteger":null,"bool":null,"doubleValue":null,"intValue":null,"localDate":null,"localDateTime":null,"localTime":null,"longValue":null,"text":null,"zonedDateTime":"2021-01-20T12:04:12Z"}'
    }

    @Unroll
    void "Decode #_datatype from json"() {
        given: 'JsonUtils'
            JsonMapper jsonUtils = new JsonMapper()

        expect:
            jsonUtils.decode(_value, JsonTestModel) == _expected

        where:
            _value                                     | _datatype       | _expected
            '{"text":"string"}'                        | 'String'        | new JsonTestModel(text: 'string')
            '{"bool":true}'                            | 'Boolean'       | new JsonTestModel(bool: true)
            '{"intValue":45}'                          | 'Integer'       | new JsonTestModel(intValue: 45)
            '{"longValue":45}'                         | 'Long'          | new JsonTestModel(longValue: 45L)
            '{"doubleValue":3.257488563254}'           | 'Double'        | new JsonTestModel(doubleValue: 3.257488563254)
            '{"bigInteger":45}'                        | 'BigInteger'    | new JsonTestModel(bigInteger: BigInteger.valueOf(45L))
            '{"bigDecimal":3.257488563254}'            | 'BigDecimal'    | new JsonTestModel(bigDecimal: BigDecimal.valueOf(3.257488563254))
            '{"localDate":"2021-01-20"}'               | 'LocalDate'     | new JsonTestModel(localDate: LOCAL_DATE)
            '{"localTime":"12:04:12"}'                 | 'LocalTime'     | new JsonTestModel(localTime: LOCAL_TIME)
            '{"localDateTime":"2021-01-20T12:04:12"}'  | 'LocalDateTime' | new JsonTestModel(localDateTime: LOCAL_DATE_TIME)
            '{"zonedDateTime":"2021-01-20T12:04:12Z"}' | 'ZonedDateTime' | new JsonTestModel(zonedDateTime: ZONED_DATE_TIME)
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
                throw new JacksonException('message')
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
