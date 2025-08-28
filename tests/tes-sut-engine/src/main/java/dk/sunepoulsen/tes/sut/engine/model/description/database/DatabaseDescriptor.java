package dk.sunepoulsen.tes.sut.engine.model.description.database;

import lombok.Data;

import java.util.List;

@Data
public class DatabaseDescriptor {

    private String name;
    private DatabaseUserDescriptor owner;
    private List<DatabaseSchemaDescriptor> schemas;

}
