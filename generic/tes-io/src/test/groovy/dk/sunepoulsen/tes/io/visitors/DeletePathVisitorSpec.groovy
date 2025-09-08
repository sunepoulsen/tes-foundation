package dk.sunepoulsen.tes.io.visitors

import com.google.common.jimfs.Configuration
import com.google.common.jimfs.Jimfs
import spock.lang.Specification

import java.nio.file.FileSystem
import java.nio.file.Files
import java.nio.file.Path

class DeletePathVisitorSpec extends Specification {

    private FileSystem fileSystem
    private Path directory

    void setup() {
        this.fileSystem = Jimfs.newFileSystem(Configuration.forCurrentPlatform())
        this.directory = fileSystem.getPath('directory')
    }

    void cleanup() {
        fileSystem.close()
    }

    void "Test of delete directory that does not exists"() {
        when:
            Files.walkFileTree(directory, new DeletePathVisitor())

        then:
            Files.notExists(directory)
    }

    void "Test of delete directory that already exists"() {
        given:
            Files.createDirectories(directory)

        when:
            Files.walkFileTree(directory, new DeletePathVisitor())

        then:
            Files.notExists(directory)
    }

    void "Test of delete directory that already exists and contains files"() {
        given:
            Files.createDirectories(directory)
            List<Path> paths = [directory]
            createFiles(3, directory, paths)

        when:
            Files.walkFileTree(directory, new DeletePathVisitor())

        then:
            paths.size() == 4
            paths.each {
                Files.notExists(it)
            }
    }

    void "Test of delete directory that already exists and contains sub directories with files"() {
        given:
            Files.createDirectories(directory)
            List<Path> paths = [directory]
            createDirectoryWithFiles(3, 3, directory, 2, paths)

        when:
            Files.walkFileTree(directory, new DeletePathVisitor())

        then:
            paths.size() == 49
            paths.each {
                Files.notExists(it)
            }
    }

    private void createDirectoryWithFiles(int numberOfDirectories, int numberOfFiles, Path parentPath, int levels, List<Path> result) {
        if (levels < 1) {
            return
        }

        (1..numberOfDirectories).each {
            Path dir = fileSystem.getPath(parentPath.toString(), "dir${it}")
            Files.createDirectories(dir)
            result.add(dir)

            createFiles(numberOfFiles, dir, result)
            createDirectoryWithFiles(numberOfDirectories, numberOfFiles, dir, levels - 1, result)
        }
    }

    private void createFiles(int n, Path parentPath, List<Path> result) {
        (1..n).each {
            Path file = fileSystem.getPath(parentPath.toString(), "file${it}.txt")
            Files.write(file, "file${it} content".bytes)
            result.add(file)
        }
    }

}
