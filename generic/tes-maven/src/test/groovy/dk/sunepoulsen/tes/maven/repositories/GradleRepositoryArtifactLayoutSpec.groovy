package dk.sunepoulsen.tes.maven.repositories

import dk.sunepoulsen.tes.maven.model.MavenArtifact
import spock.lang.Specification
import spock.lang.Unroll

class GradleRepositoryArtifactLayoutSpec extends Specification {

    void "Argument is null"() {
        given:
            MavenRepositoryArtifactLayout sut = new GradleRepositoryArtifactLayout()

        when:
            sut.artifactUriPath(null)

        then:
            thrown(IllegalArgumentException)
    }

    @Unroll
    void "Calculate path without error: #_testcase"() {
        given:
            MavenRepositoryArtifactLayout sut = new GradleRepositoryArtifactLayout()

        when:
            String result = sut.artifactUriPath(new MavenArtifact(
                groupId: _groupId,
                artifactId: _artifactId,
                version: _version
            ))

        then:
            result == _expected

        where:
            _testcase                              | _groupId      | _artifactId         | _version | _expected
            'Without dots'                         | 'group'       | 'artifact'          | '1.0.0'  | 'group/artifact'
            'GroupId contains dots'                | 'org.company' | 'artifact'          | '1.0.0'  | 'org/company/artifact'
            'ArtifactId contains dots'             | 'group'       | 'part1.part2.part3' | '1.0.0'  | 'group/part1.part2.part3'
            'GroupId and ArtifactId contains dots' | 'org.company' | 'part1.part2.part3' | '1.0.0'  | 'org/company/part1.part2.part3'
    }

    @Unroll
    void "Calculate path throws error: #_testcase"() {
        given:
            MavenRepositoryArtifactLayout sut = new GradleRepositoryArtifactLayout()

        when:
            sut.artifactUriPath(new MavenArtifact(
                groupId: _groupId,
                artifactId: _artifactId,
                version: _version
            ))

        then:
            thrown(IllegalArgumentException)

        where:
            _testcase            | _groupId | _artifactId | _version
            'GroupId is null'    | null     | 'artifact'  | '1.0.0'
            'ArtifactId is null' | 'group'  | null        | '1.0.0'
    }
}
