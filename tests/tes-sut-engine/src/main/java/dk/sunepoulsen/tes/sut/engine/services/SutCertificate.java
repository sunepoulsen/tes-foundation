package dk.sunepoulsen.tes.sut.engine.services;

import dk.sunepoulsen.tes.deployment.core.data.DeployCertificate;
import dk.sunepoulsen.tes.deployment.core.function.AtomicDataSupplier;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SutCertificate {
    private String key;
    private AtomicDataSupplier<DeployCertificate> certificate;
}
