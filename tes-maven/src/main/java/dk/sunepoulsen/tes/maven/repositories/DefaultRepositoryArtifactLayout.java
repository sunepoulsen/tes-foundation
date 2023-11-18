package dk.sunepoulsen.tes.maven.repositories;

import dk.sunepoulsen.tes.maven.model.MavenArtifact;

public class DefaultRepositoryArtifactLayout implements MavenRepositoryArtifactLayout {

    @Override
    public String artifactUriPath(MavenArtifact artifact) {
        if (artifact == null) {
            throw new IllegalArgumentException("artifact may not be null");
        }

        if (artifact.getGroupId() == null) {
            throw new IllegalArgumentException("artifact.groupId may not be null");
        }

        if (artifact.getArtifactId() == null) {
            throw new IllegalArgumentException("artifact.groupId may not be null");
        }

        return String.format("%s/%s",
                artifact.getGroupId().replace(".", "/"),
                artifact.getArtifactId().replace(".", "/"));
    }

}
