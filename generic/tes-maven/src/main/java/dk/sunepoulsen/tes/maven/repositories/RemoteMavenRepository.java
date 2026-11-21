package dk.sunepoulsen.tes.maven.repositories;

import dk.sunepoulsen.tes.maven.exceptions.MavenRepositoryException;
import dk.sunepoulsen.tes.maven.model.MavenArtifact;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

/**
 * Represents a Maven repository on an external host.
 */
@Slf4j
public class RemoteMavenRepository implements MavenRepository {

    /** Base URI of the repository. */
    private final String baseUri;
    /** Layout used to locate artifacts. */
    private final MavenRepositoryArtifactLayout artifactLayout;

    /** Client used for asynchronous metadata requests. */
    private final HttpClient httpClient;

    /**
     * Creates a repository client.
     *
     * @param baseUri repository base URI
     * @param artifactLayout layout used to locate artifacts
     */
    public RemoteMavenRepository(URI baseUri, MavenRepositoryArtifactLayout artifactLayout) {
        this.baseUri = baseUri.toString();
        this.artifactLayout = artifactLayout;

        this.httpClient = buildHttpClient();
    }

    public CompletableFuture<String> loadMetadata(MavenArtifact artifact) throws MavenRepositoryException {
        try {
            HttpRequest httpRequest;
            httpRequest = HttpRequest.newBuilder()
                    .GET()
                    .uri(new URI(baseUri + "/" + artifactLayout.artifactUriPath(artifact) + "/maven-metadata.xml"))
                    .timeout(Duration.ofSeconds(30))
                    .build();

            log.debug("Call GET {}", httpRequest.uri());
            return httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                    .thenApply(this::verifyResponseAndExtractBody);
        } catch (URISyntaxException ex) {
            throw new MavenRepositoryException("Unable to parse URL: " + ex.getMessage(), ex);
        }
    }

    /**
     * Extracts the response body if the HTTP status is successful.
     *
     * @param response HTTP response to inspect
     * @return response body for a 2xx status
     * @throws MavenRepositoryException if the status is not 2xx
     */
    public String verifyResponseAndExtractBody(HttpResponse<String> response) {
        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            return response.body();
        }

        throw new MavenRepositoryException("Http Call returned response: " + response.body());
    }

    private HttpClient buildHttpClient() {
        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .connectTimeout(Duration.ofSeconds(30))
                .build();
    }

}
