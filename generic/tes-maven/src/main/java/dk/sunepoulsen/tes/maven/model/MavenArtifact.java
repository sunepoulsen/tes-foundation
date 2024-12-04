package dk.sunepoulsen.tes.maven.model;

import lombok.Data;

/**
 * Representation of an artifact in Maven
 */
@Data
public class MavenArtifact {
    /**
     * Group id of the artifact with dot notation.
     */
    private String groupId;

    /**
     * Artifact id of the artifact with dot notation.
     */
    private String artifactId;

    /**
     * Version of the artifact.
     */
    private String version;
}
