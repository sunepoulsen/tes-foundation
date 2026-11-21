package dk.sunepoulsen.tes.json.data.generators;

import dk.sunepoulsen.tes.data.generators.DataGenerator;
import dk.sunepoulsen.tes.json.JsonMapper;

/** Generates JSON by encoding values from another generator.
 * @param <T> type of the generated values */
public class JsonDataGenerator<T> implements DataGenerator<String> {

    /** Generator supplying values to encode. */
    private final DataGenerator<T> dataGenerator;

    /** @param dataGenerator generator supplying values to encode */
    public JsonDataGenerator(DataGenerator<T> dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    @Override
    public String generate() {
        return JsonMapper.encodeAsJson(this.dataGenerator.generate());
    }
}
