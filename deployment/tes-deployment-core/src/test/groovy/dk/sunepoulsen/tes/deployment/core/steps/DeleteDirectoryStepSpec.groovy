package dk.sunepoulsen.tes.deployment.core.steps

import com.google.common.jimfs.Configuration
import com.google.common.jimfs.Jimfs
import dk.sunepoulsen.tes.deployment.core.function.AtomicDataSupplier
import spock.lang.Specification

import java.nio.file.FileSystem
import java.nio.file.Files
import java.nio.file.Path

class DeleteDirectoryStepSpec extends Specification {

    private FileSystem fileSystem
    private Path directory
    private DeleteDirectoryStep sut

    void setup() {
        this.fileSystem = Jimfs.newFileSystem(Configuration.forCurrentPlatform())
        this.directory = fileSystem.getPath('directory')

        this.sut = new DeleteDirectoryStep('step')
        this.sut.directory = new AtomicDataSupplier<>(directory)
    }

    void cleanup() {
        fileSystem.close()
    }

    void "Test timer name"() {
        expect:
            sut.timerName() == 'step'
    }

    void "Test of delete directory that already exists"() {
        given:
            Files.createDirectories(directory)

        when:
            sut.execute()

        then:
            Files.notExists(directory)
    }

    void "Test of delete directory that does not exist"() {
        when:
            sut.execute()

        then:
            Files.notExists(directory)
    }

}
