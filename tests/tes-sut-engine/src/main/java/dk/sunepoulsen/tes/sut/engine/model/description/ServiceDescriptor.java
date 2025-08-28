package dk.sunepoulsen.tes.sut.engine.model.description;

import dk.sunepoulsen.tes.data.generators.DataGenerator;

import java.util.List;

public interface ServiceDescriptor {

    String getId();
    ImageDescriptor getImage();
    DataGenerator<List<String>> networkAliases();

}
