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

@Getter
@Setter
public class SaveFileContentStep extends AbstractDeployStep {

    private AtomicDataSupplier<DeployFileContent> fileContent;
    private AtomicDataSupplier<Path> directory;

    public SaveFileContentStep(String key) {
        super(key);
    }

    @Override
    public FlowStepResult execute() {
        final Path directoryPath = directory.get("Directory has not been set");
        Path filePath = null;
        final FileSystem fileSystem = directoryPath.getFileSystem();

        try {
            Files.createDirectories(directoryPath);

            final DeployFileContent fc = fileContent.get("File content has not been set");
            filePath = fileSystem.getPath(directoryPath.toAbsolutePath().toString(), fc.getFilename());

            Files.createFile(filePath);
            Files.write(filePath, fc.getContent());

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