package dk.sunepoulsen.tes.deployment.core.steps;

import dk.sunepoulsen.tes.deployment.core.function.AtomicDataSupplier;
import dk.sunepoulsen.tes.flows.FlowStepResult;
import dk.sunepoulsen.tes.flows.exceptions.FlowStepException;
import dk.sunepoulsen.tes.io.visitors.DeletePathVisitor;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/** Deletes a directory tree. */
@Getter
@Setter
public class DeleteDirectoryStep extends AbstractDeployStep {

    /** Supplies the directory to delete. */
    private AtomicDataSupplier<Path> directory;

    /**
     * Creates a directory deletion step.
     * @param key step and timer key
     */
    public DeleteDirectoryStep(String key) {
        super(key);
    }

    /**
     * Walks and deletes the supplied directory tree.
     * @return the successful step result
     * @throws FlowStepException if the directory is absent or an I/O operation fails
     */
    @Override
    public FlowStepResult execute() {
        try {
            Path path = directory.get("Directory has not been set");
            Files.walkFileTree(path, new DeletePathVisitor());

            return FlowStepResult.OK;
        } catch (IOException ex) {
            throw new FlowStepException(ex.getMessage(), ex);
        }
    }

}