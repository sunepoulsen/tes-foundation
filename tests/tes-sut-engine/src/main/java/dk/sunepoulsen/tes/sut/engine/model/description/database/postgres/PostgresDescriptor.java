package dk.sunepoulsen.tes.sut.engine.model.description.database.postgres;

import dk.sunepoulsen.tes.sut.engine.model.description.ImageDescriptor;
import dk.sunepoulsen.tes.sut.engine.model.description.ServiceDescriptor;
import dk.sunepoulsen.tes.sut.engine.model.description.database.DatabaseDescriptor;
import dk.sunepoulsen.tes.data.generators.DataGenerator;
import lombok.Data;

import java.util.List;

@Data
public class PostgresDescriptor implements ServiceDescriptor {

    private String id;
    private ImageDescriptor image;
    private DataGenerator<List<String>> networkAliases;
    private DataGenerator<String> adminPassword;
    private List<DatabaseDescriptor> databaseDescriptor;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public ImageDescriptor getImage() {
        return image;
    }

    @Override
    public DataGenerator<List<String>> networkAliases() {
        return networkAliases;
    }
}
