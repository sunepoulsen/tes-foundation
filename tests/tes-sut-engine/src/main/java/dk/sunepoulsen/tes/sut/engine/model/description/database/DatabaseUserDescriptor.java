package dk.sunepoulsen.tes.sut.engine.model.description.database;

import dk.sunepoulsen.tes.data.generators.DataGenerator;
import lombok.Data;

@Data
public class DatabaseUserDescriptor {

    private DataGenerator<String> username;
    private DataGenerator<String> password;
    private DatabaseUserGrantDescriptor userGrant;

}
