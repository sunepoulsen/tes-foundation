package dk.sunepoulsen.tes.maven.repositories;

import dk.sunepoulsen.tes.maven.exceptions.MavenRepositoryException;
import dk.sunepoulsen.tes.maven.model.MavenArtifact;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.concurrent.CompletableFuture;

/**
 * Represents a Maven repository on an external host.
 */
@Slf4j
public class LocalMavenRepository implements MavenRepository {

    /** Layout used to locate artifacts within the local repository. */
    private final MavenRepositoryArtifactLayout artifactLayout;

    /**
     * Creates a local repository using the given artifact layout.
     *
     * @param artifactLayout layout used to resolve artifact paths
     */
    public LocalMavenRepository(MavenRepositoryArtifactLayout artifactLayout) {
        this.artifactLayout = artifactLayout;
    }

    /**
     * Reads local metadata as UTF-8 from the user's Maven repository.
     *
     * @param artifact artifact whose metadata is read
     * @return a completed future containing the metadata, or a future completed exceptionally on an I/O failure
     */
    public CompletableFuture<String> loadMetadata(MavenArtifact artifact) throws MavenRepositoryException {
        var localRepositoryRootPath = System.getProperty( "user.home" ) + File.separator + ".m2/repository";
        var artifactPath = localRepositoryRootPath + File.separator + this.artifactLayout.artifactUriPath(artifact);
        var artifactMetadataFilename = artifactPath + File.separator + "maven-metadata-local.xml";
        var filePath = FileSystems.getDefault().getPath(artifactMetadataFilename);

        var future = new CompletableFuture<String>();
        try {
            future.complete(Files.readString(filePath, StandardCharsets.UTF_8));
        }
        catch (IOException ex) {
            future.completeExceptionally(ex);
        }

        return future;
    }

}
