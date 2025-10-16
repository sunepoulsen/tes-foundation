package dk.sunepoulsen.tes.sut.engine.providers

import dk.sunepoulsen.tes.deployment.core.data.DeployCertificate
import dk.sunepoulsen.tes.rest.integrations.config.DefaultClientConfig
import dk.sunepoulsen.tes.rest.integrations.config.TechEasySolutionsClientConfig
import dk.sunepoulsen.tes.security.net.ssl.SSLContextFactory
import dk.sunepoulsen.tes.sut.engine.extensions.AbstractSystemUnderTestExtension
import dk.sunepoulsen.tes.sut.engine.services.SutCertificate
import dk.sunepoulsen.tes.sut.engine.system.SystemUnderTestDeployment

trait SystemUnderTestProvider {

    SystemUnderTestDeployment sut() {
        return AbstractSystemUnderTestExtension.systemUnderTestDeployment().orElseThrow(() ->
            new IllegalStateException('System Under Test Deployment is not available')
        )
    }

    TechEasySolutionsClientConfig clientConfig() {
        SutCertificate sutCertificate = sut().findCertificate(AbstractSystemUnderTestExtension.SUT_CERTIFICATE_NAME).orElseThrow(() ->
            new IllegalStateException("Certificate '${AbstractSystemUnderTestExtension.SUT_CERTIFICATE_NAME}' is not available")
        )
        DeployCertificate deployCertificate = sutCertificate.certificate.get("Certificate has not been set")

        return new DefaultClientConfig(
            SSLContextFactory.createSSLContext(deployCertificate.contentAsInputStream, deployCertificate.password)
        )
    }

}
