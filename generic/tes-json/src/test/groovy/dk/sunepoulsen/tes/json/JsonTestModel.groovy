package dk.sunepoulsen.tes.json

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZonedDateTime

@JsonInclude( JsonInclude.Include.NON_NULL )
@JsonNaming( PropertyNamingStrategies.SnakeCaseStrategy.class )
@JsonIgnoreProperties( ignoreUnknown = true )
class JsonTestModel {
    String text
    Boolean bool
    Integer intValue
    Long longValue
    Double doubleValue
    BigInteger bigInteger
    BigDecimal bigDecimal
    LocalDate localDate
    LocalTime localTime
    LocalDateTime localDateTime
    ZonedDateTime zonedDateTime

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        JsonTestModel that = (JsonTestModel) o

        if (bigDecimal != that.bigDecimal) return false
        if (bigInteger != that.bigInteger) return false
        if (bool != that.bool) return false
        if (doubleValue != that.doubleValue) return false
        if (intValue != that.intValue) return false
        if (localDate != that.localDate) return false
        if (localDateTime != that.localDateTime) return false
        if (localTime != that.localTime) return false
        if (longValue != that.longValue) return false
        if (text != that.text) return false
        if (zonedDateTime != that.zonedDateTime) return false

        return true
    }

    int hashCode() {
        int result
        result = (text != null ? text.hashCode() : 0)
        result = 31 * result + (bool != null ? bool.hashCode() : 0)
        result = 31 * result + (intValue != null ? intValue.hashCode() : 0)
        result = 31 * result + (longValue != null ? longValue.hashCode() : 0)
        result = 31 * result + (doubleValue != null ? doubleValue.hashCode() : 0)
        result = 31 * result + (bigInteger != null ? bigInteger.hashCode() : 0)
        result = 31 * result + (bigDecimal != null ? bigDecimal.hashCode() : 0)
        result = 31 * result + (localDate != null ? localDate.hashCode() : 0)
        result = 31 * result + (localTime != null ? localTime.hashCode() : 0)
        result = 31 * result + (localDateTime != null ? localDateTime.hashCode() : 0)
        result = 31 * result + (zonedDateTime != null ? zonedDateTime.hashCode() : 0)
        return result
    }
}
