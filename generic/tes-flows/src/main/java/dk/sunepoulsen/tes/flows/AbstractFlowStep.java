package dk.sunepoulsen.tes.flows;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractFlowStep implements FlowStep {

    private final String timerName;

    @Override
    public String timerName() {
        return timerName;
    }

}
