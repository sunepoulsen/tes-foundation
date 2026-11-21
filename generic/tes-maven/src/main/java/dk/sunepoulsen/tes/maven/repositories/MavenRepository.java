package dk.sunepoulsen.tes.maven.repositories;

import dk.sunepoulsen.tes.maven.exceptions.MavenRepositoryException;
import dk.sunepoulsen.tes.maven.model.MavenArtifact;

import java.util.concurrent.CompletableFuture;

/**
 * Represents a Maven repository on an external host.
 */
public interface MavenRepository {

    /**
     * Loads the metadata for an artifact.
     * @param artifact artifact whose metadata is requested
     * @return a future containing the metadata text
     * @throws MavenRepositoryException if the request cannot be prepared or initiated
     */
    CompletableFuture<String> loadMetadata(MavenArtifact artifact) throws MavenRepositoryException;
}
