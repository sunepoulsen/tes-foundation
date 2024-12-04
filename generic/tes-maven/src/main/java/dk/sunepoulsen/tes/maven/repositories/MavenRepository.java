package dk.sunepoulsen.tes.maven.repositories;

import dk.sunepoulsen.tes.maven.exceptions.MavenRepositoryException;
import dk.sunepoulsen.tes.maven.model.MavenArtifact;

import java.util.concurrent.CompletableFuture;

/**
 * Represents a Maven repository on an external host.
 */
public interface MavenRepository {

    CompletableFuture<String> loadMetadata(MavenArtifact artifact) throws MavenRepositoryException;
}
