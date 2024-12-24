package dk.sunepoulsen.tes.sut.engine.model.description.database;

import lombok.Data;

import java.util.List;

@Data
public class DatabaseUserGrantDescriptor {

    private List<String> tables;
    private List<String> sequences;

}
