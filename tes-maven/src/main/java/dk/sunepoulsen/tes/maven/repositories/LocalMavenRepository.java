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

    private final MavenRepositoryArtifactLayout artifactLayout;

    public LocalMavenRepository(MavenRepositoryArtifactLayout artifactLayout) {
        this.artifactLayout = artifactLayout;
    }

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
