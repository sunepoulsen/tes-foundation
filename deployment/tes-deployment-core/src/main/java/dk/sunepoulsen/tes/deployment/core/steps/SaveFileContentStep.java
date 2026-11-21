package dk.sunepoulsen.tes.deployment.core.steps;

import dk.sunepoulsen.tes.deployment.core.data.DeployFileContent;
import dk.sunepoulsen.tes.deployment.core.function.AtomicDataSupplier;
import dk.sunepoulsen.tes.flows.FlowStepResult;
import dk.sunepoulsen.tes.flows.exceptions.FlowStepException;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

/** Saves supplied file content in a directory. */
@Getter
@Setter
public class SaveFileContentStep extends AbstractDeployStep {

    /** Receives the path of the saved file. */
    private final AtomicDataSupplier<Path> createdPath;
    /** Supplies the filename and content to save. */
    private AtomicDataSupplier<? extends DeployFileContent> fileContent;
    /** Supplies the destination directory. */
    private AtomicDataSupplier<Path> directory;

    /**
     * Creates a file saving step.
     * @param key step and timer key
     */
    public SaveFileContentStep(String key) {
        super(key);
        this.createdPath = new AtomicDataSupplier<>();
    }

    /**
     * Creates the directory if needed and replaces the destination file with the supplied content.
     * @return the successful step result
     * @throws FlowStepException if a supplied value is absent or an I/O operation fails
     */
    @Override
    public FlowStepResult execute() {
        final Path directoryPath = directory.get("Directory has not been set");
        Path filePath = null;
        final FileSystem fileSystem = directoryPath.getFileSystem();

        try {
            Files.createDirectories(directoryPath);

            final DeployFileContent fc = fileContent.get("File content has not been set");
            filePath = fileSystem.getPath(directoryPath.toAbsolutePath().toString(), fc.getFilename());

            Files.deleteIfExists(filePath);
            Files.createFile(filePath);
            Files.write(filePath, fc.getContent());

            createdPath.set(filePath);
            return FlowStepResult.OK;
        } catch (FileAlreadyExistsException ex) {
            String message = ex.getMessage();
            if (filePath != null) {
                message = "Unable to create file with new content because the file '" + filePath + "' already exists";
            }

            throw new FlowStepException(message, ex);
        } catch (IOException ex) {
            throw new FlowStepException(ex.getMessage(), ex);
        }
    }
}