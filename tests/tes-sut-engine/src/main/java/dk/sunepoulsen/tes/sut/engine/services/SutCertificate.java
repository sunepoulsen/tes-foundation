package dk.sunepoulsen.tes.sut.engine.services;

import dk.sunepoulsen.tes.deployment.core.data.DeployCertificate;
import dk.sunepoulsen.tes.deployment.core.function.AtomicDataSupplier;
import lombok.Builder;
import lombok.Data;

/** Identifies a certificate supplied for a system under test. */
@Data
@Builder
public class SutCertificate {
    /** Certificate identifier. */
    private String key;
    /** Supplier of deployment certificate data. */
    private AtomicDataSupplier<DeployCertificate> certificate;
}
