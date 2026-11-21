package dk.sunepoulsen.tes.flows;

/** Lifecycle status of a flow step executor. */
public enum FlowStepExecutorStatus {
    READY,
    IGNORED,
    RUNNING,
    FINISHED
}
