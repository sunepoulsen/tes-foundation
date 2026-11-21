package dk.sunepoulsen.tes.gatling.scenarios;

import dk.sunepoulsen.tes.data.generators.DataGenerator;
import dk.sunepoulsen.tes.json.JsonMapper;
import dk.sunepoulsen.tes.json.data.generators.JsonDataGenerator;
import io.gatling.javaapi.core.Body;

import static io.gatling.javaapi.core.CoreDsl.StringBody;

/** Factory methods for JSON Gatling request bodies. */
public class GatlingBodies {

    private GatlingBodies() {
    }

    /**
     * Creates a body that generates JSON from the supplied data generator for each session.
     * @param <T> type of generated data
     * @param dataGenerator source of data to serialize
     * @return a Gatling string body
     */
    public static <T> Body jsonBody(DataGenerator<T> dataGenerator) {
        return StringBody(session ->
            new JsonDataGenerator<>(dataGenerator).generate()
        );
    }

    /**
     * Creates a body that serializes a session property as JSON.
     * @param propertyName name of the session property
     * @return a Gatling string body
     */
    public static Body jsonSessionPropertyBody(final String propertyName) {
        return StringBody(session ->
            JsonMapper.encodeAsJson(session.get(propertyName))
        );
    }

}
