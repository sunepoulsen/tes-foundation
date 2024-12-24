package dk.sunepoulsen.tes.sut.engine.model.description;

import lombok.Data;

import java.util.List;

@Data
public class SystemUnderTestDescription {

    private String profile;
    private List<ServiceDescriptor> services;

}
