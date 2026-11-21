package dk.sunepoulsen.tes.flows;

import lombok.RequiredArgsConstructor;

/** Base flow step that stores its timer name. */
@RequiredArgsConstructor
public abstract class AbstractFlowStep implements FlowStep {

    /** Name of the timer associated with this step. */
    private final String timerName;

    /** {@inheritDoc} */
    @Override
    public String timerName() {
        return timerName;
    }

}
