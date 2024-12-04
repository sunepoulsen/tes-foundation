package dk.sunepoulsen.tes.maven.repositories;

import dk.sunepoulsen.tes.maven.model.MavenArtifact;

public interface MavenRepositoryArtifactLayout {
    String artifactUriPath(MavenArtifact artifact);
}
