package dk.sunepoulsen.tes.json.data.generators;

import dk.sunepoulsen.tes.data.generators.DataGenerator;
import dk.sunepoulsen.tes.json.JsonMapper;

public class JsonDataGenerator<T> implements DataGenerator<String> {

    private final DataGenerator<T> dataGenerator;

    public JsonDataGenerator(DataGenerator<T> dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    @Override
    public String generate() {
        return JsonMapper.encodeAsJson(this.dataGenerator.generate());
    }
}
