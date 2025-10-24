package dk.sunepoulsen.tes.flows;

import java.util.List;

public interface FlowReport {
    void printReport(List<FlowStepExecutor> steps);
}
