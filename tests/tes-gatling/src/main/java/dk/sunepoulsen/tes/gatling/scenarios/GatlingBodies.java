package dk.sunepoulsen.tes.gatling.scenarios;

import dk.sunepoulsen.tes.data.generators.DataGenerator;
import dk.sunepoulsen.tes.json.JsonMapper;
import dk.sunepoulsen.tes.json.data.generators.JsonDataGenerator;
import io.gatling.javaapi.core.Body;

import static io.gatling.javaapi.core.CoreDsl.StringBody;

public class GatlingBodies {

    private GatlingBodies() {
    }

    public static <T> Body jsonBody(DataGenerator<T> dataGenerator) {
        return StringBody(session ->
            new JsonDataGenerator<>(dataGenerator).generate()
        );
    }

    public static Body jsonSessionPropertyBody(final String propertyName) {
        return StringBody(session ->
            JsonMapper.encodeAsJson(session.get(propertyName))
        );
    }

}
