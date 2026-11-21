package dk.sunepoulsen.tes.maven.repositories;

import dk.sunepoulsen.tes.maven.model.MavenArtifact;

/** Resolves the repository path for an artifact. */
public interface MavenRepositoryArtifactLayout {
    /**
     * Computes the artifact's repository-relative path.
     * @param artifact artifact to locate
     * @return the artifact path
     */
    String artifactUriPath(MavenArtifact artifact);
}
