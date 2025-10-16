package dk.sunepoulsen.tes.deployment.core.steps

import com.google.common.jimfs.Configuration
import com.google.common.jimfs.Jimfs
import dk.sunepoulsen.tes.data.generators.DataGenerator
import dk.sunepoulsen.tes.data.generators.Generators
import dk.sunepoulsen.tes.deployment.core.data.DeployFileContent
import dk.sunepoulsen.tes.deployment.core.function.AtomicDataSupplier
import dk.sunepoulsen.tes.flows.exceptions.FlowStepException

import java.nio.charset.StandardCharsets
import java.nio.file.FileSystem
import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Path

class SaveFileContentStepSpec extends Specification {

    private FileSystem fileSystem
    private Path directory
    private DeployFileContent fileContent
    private SaveFileContentStep sut

    void setup() {
        this.fileSystem = Jimfs.newFileSystem(Configuration.forCurrentPlatform())
        this.directory = fileSystem.getPath('directory')
        this.fileContent = new DeployFileContent(
            'filename.txt',
            'File content'.getBytes(StandardCharsets.UTF_8)
        )

        this.sut = new SaveFileContentStep('step')
        this.sut.directory = new AtomicDataSupplier<>(directory)
        this.sut.fileContent = new AtomicDataSupplier<>(fileContent)
    }

    void cleanup() {
        fileSystem.close()
    }

    void "Test timer name"() {
        expect:
            sut.timerName() == 'step'
    }

    void "Test of store DeployFileContent where directory and file does not exist"() {
        when:
            sut.execute()

        then:
            Files.exists(directory)
            Files.exists(sut.createdPath.get().get())
            Files.readString(sut.createdPath.get().get()) == fileContent.getContentAsString()
    }

    void "Test of store DeployFileContent where only directory already exists"() {
        given:
            Files.createDirectories(directory)

        when:
            sut.execute()

        then:
            Files.exists(directory)
            Files.exists(sut.createdPath.get().get())
            Files.readString(sut.createdPath.get().get()) == fileContent.getContentAsString()
    }

    void "Test of store DeployFileContent where directory and file already exists"() {
        given:
            Files.createDirectories(directory)

        and:
            Path filePath = fileSystem.getPath(directory.toString(), fileContent.filename)
            Files.write(filePath, 'Some other content'.bytes)

        when:
            sut.execute()

        then:
            Files.exists(directory)
            Files.exists(sut.createdPath.get().get())
            Files.readString(sut.createdPath.get().get()) == fileContent.getContentAsString()
    }

}
