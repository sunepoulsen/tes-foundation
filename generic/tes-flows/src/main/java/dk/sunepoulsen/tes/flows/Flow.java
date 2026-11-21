package dk.sunepoulsen.tes.flows;

/** A flow that can be executed and reported. */
public interface Flow {
    /** Executes the flow. */
    void execute();
    /** Prints a report of the flow. */
    void printReport();
}
