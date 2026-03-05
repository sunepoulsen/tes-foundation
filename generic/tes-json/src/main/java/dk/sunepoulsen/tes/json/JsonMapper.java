package dk.sunepoulsen.tes.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import dk.sunepoulsen.tes.json.exceptions.DecodeJsonException;
import dk.sunepoulsen.tes.json.exceptions.EncodeJsonException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.SerializationFeature;

import static tools.jackson.databind.PropertyNamingStrategies.SNAKE_CASE;

/**
 * Class to encode and decode objects to and from JSON strings.
 */
@Slf4j
@Getter
public class JsonMapper {
    private final ObjectMapper objectMapper;

    /**
     * Constructs a default instance of <code>JsonMapper</code> with
     * a default instance of <code>ObjectMapper</code>
     * <p>
     * See JsonMapper.springBootObjectMapper()
     */
    public JsonMapper() {
        this(springBootObjectMapper());
    }

    /**
     * Constructs an instance with an <code>ObjectMapper</code> to be used
     * for decoding and encoding of JSON.
     *
     * @param objectMapper The <code>ObjectMapper</code> to use.
     */
    public JsonMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Encode an <code>Object</code> as a JSON string.
     *
     * @param value The object to encode.
     * @return The encoded JSON string.
     * @throws EncodeJsonException in case of an encoding error. The exception
     *                             will contain the original exception from <code>com.fasterxml.jackson.core</code>
     */
    public String encode(Object value) {
        try {
            log.trace("Attempt to encode to json of object: {}", value);
            String result = this.objectMapper.writeValueAsString(value);

            log.trace("Encoded result of object: {}", result);
            return result;
        } catch (JacksonException ex) {
            throw new EncodeJsonException(ex.getMessage(), ex);
        }
    }

    /**
     * Decode a JSON string to an <code>Object</code>.
     *
     * @param <T>   The return type of the generated result.
     * @param json  The JSON string to decode.
     * @param clazz The class type to decode the JSON string to.
     * @return The decoded <code>Object</code> as type <code>&lt;T&gt;</code>
     * @throws DecodeJsonException in case of a decoding error. The exception
     *                             will contain the original exception from <code>com.fasterxml.jackson.core</code>
     */
    public <T> T decode(String json, Class<T> clazz) {
        try {
            log.trace("Attempt to decode from json to object of type {}: {}", clazz.getName(), json);
            T result = this.objectMapper.readValue(json, clazz);

            log.trace("Decoded result of json: {}", result);
            return result;
        } catch (JacksonException ex) {
            throw new DecodeJsonException(ex.getMessage(), ex);
        }
    }

    /**
     * Static function to encode an <code>Object</code> as a JSON string.
     * <p>
     * This function will use a new instance of <code>JsonMapper</code> to
     * encode the object. It's a shortcut for
     *
     * <p>
     * <code>
     * new JsonMapper().encode(value);
     * </code>
     * </p>
     *
     * @param value The object to encode.
     * @return The encoded JSON string.
     * @throws EncodeJsonException in case of an encoding error. The exception
     *                             will contain the original exception from <code>com.fasterxml.jackson.core</code>
     */
    public static String encodeAsJson(Object value) {
        return new JsonMapper().encode(value);
    }

    /**
     * Static function to decode a JSON string to an <code>Object</code>.
     * <p>
     * This function will use a new instance of <code>JsonMapper</code> to
     * decode the JSON string.
     * <p>
     * It's a shortcut for
     *
     * <p>
     * <code>
     * new JsonMapper().decode(json, clazz);
     * </code>
     * </p>
     *
     * @param <T>   The return type of the generated result.
     * @param json  The JSON string to decode.
     * @param clazz The class type to decode the JSON string to.
     * @return The decoded <code>Object</code> as type <code>&lt;T&gt;</code>
     * @throws DecodeJsonException in case of a decoding error. The exception
     *                             will contain the original exception from <code>com.fasterxml.jackson.core</code>
     */
    public static <T> T decodeJson(String json, Class<T> clazz) {
        return new JsonMapper().decode(json, clazz);
    }

    /**
     * Constructs an <code>ObjectMapper</code> in a similarly way as
     * used by Spring Boot.
     * <p>
     * The returned instance is configured with the following features:
     * <ul>
     *     <li>Read/write <code>java.time</code> date/time types as Strings</li>
     * </ul>
     *
     * @return A fully configured <code>ObjectMapper</code>
     */
    public static ObjectMapper springBootObjectMapper() {
        return tools.jackson.databind.json.JsonMapper.builder()
            // modules (JavaTime, JDK8, etc.)
            .findAndAddModules()

            // snake_case globally
            //.propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)

            // NON_NULL globally – Jackson 3 style
            //.changeDefaultPropertyInclusion(incl ->
            //    incl.withValueInclusion(JsonInclude.Include.NON_NULL)
            //        .withContentInclusion(JsonInclude.Include.NON_NULL))

            // ignore unknown properties
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

            // optional: dates as ISO strings
            //.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)

            .build();
    }
}
