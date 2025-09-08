package dk.sunepoulsen.tes.flows;

public interface FlowStep {

    String timerName();
    FlowStepResult execute();
}
