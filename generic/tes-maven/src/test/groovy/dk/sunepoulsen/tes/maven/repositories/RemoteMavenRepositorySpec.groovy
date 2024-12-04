package dk.sunepoulsen.tes.maven.repositories

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import dk.sunepoulsen.tes.maven.exceptions.MavenRepositoryException
import dk.sunepoulsen.tes.maven.model.MavenArtifact
import spock.lang.Specification
import spock.lang.Unroll

import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException

import static com.github.tomakehurst.wiremock.client.WireMock.*

class RemoteMavenRepositorySpec extends Specification {

    private static WireMockServer wireMockServer
    private MavenRepositoryArtifactLayout mavenRepositoryArtifactLayout
    private RemoteMavenRepository sut

    void setupSpec() {
        wireMockServer = new WireMockServer(WireMockConfiguration.options().dynamicPort())
        wireMockServer.start()
    }

    void cleanupSpec() {
        wireMockServer.stop()
    }

    void setup() {
        this.mavenRepositoryArtifactLayout = Mock(MavenRepositoryArtifactLayout)
        this.sut = new RemoteMavenRepository(
            new URI("http://localhost:${wireMockServer.port()}/maven2"),
            this.mavenRepositoryArtifactLayout
        )

        wireMockServer.resetAll()
    }

    @Unroll
    void "Load metadata successfully: #_testcase"() {
        given:
            wireMockServer.stubFor(get(urlEqualTo('/maven2/artifact-path/maven-metadata.xml'))
                .willReturn(aResponse()
                    .withStatus(_responseCode)
                    .withBody('xml-metadata')
                ))

            MavenArtifact artifact = new MavenArtifact(
                groupId: 'groupId',
                artifactId: 'artifactId',
                version: 'version'
            )

        when:
            CompletableFuture<String> result = sut.loadMetadata(artifact)

        then:
            noExceptionThrown()
            result.get() == 'xml-metadata'

            1 * this.mavenRepositoryArtifactLayout.artifactUriPath(artifact) >> 'artifact-path'

        where:
            _testcase | _responseCode
            'Response code 200' | 200
            'Response code 201' | 201
            'Response code 299' | 299

    }

    @Unroll
    void "Load metadata with failure: #_testcase"() {
        given:
            wireMockServer.stubFor(get(urlEqualTo('/maven2/artifact-path/maven-metadata.xml'))
                .willReturn(aResponse()
                    .withStatus(_responseCode)
                    .withBody('bad-request')
                ))

            MavenArtifact artifact = new MavenArtifact(
                groupId: 'groupId',
                artifactId: 'artifactId',
                version: 'version'
            )

        when:
            sut.loadMetadata(artifact).get()

        then:
            ExecutionException executionException = thrown(ExecutionException)
            executionException.cause.class == MavenRepositoryException

            1 * this.mavenRepositoryArtifactLayout.artifactUriPath(artifact) >> 'artifact-path'

        where:
            _testcase | _responseCode
            'Response code 300' | 300
            'Response code 400' | 400
    }
}
