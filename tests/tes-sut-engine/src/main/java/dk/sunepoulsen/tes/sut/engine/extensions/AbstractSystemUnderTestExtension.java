package dk.sunepoulsen.tes.sut.engine.extensions;

import dk.sunepoulsen.tes.flows.FlowStep;
import dk.sunepoulsen.tes.flows.SequenceFlow;
import dk.sunepoulsen.tes.sut.engine.system.SystemUnderTestDeployment;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.spockframework.runtime.extension.IGlobalExtension;

import java.util.List;
import java.util.Optional;

public abstract class AbstractSystemUnderTestExtension implements IGlobalExtension {
    private final MeterRegistry meterRegistry;
    private static SystemUnderTestDeployment systemUnderTestDeployment;

    protected AbstractSystemUnderTestExtension() {
        this.meterRegistry = new SimpleMeterRegistry();
    }

    protected abstract List<FlowStep> configureDeploySteps(SystemUnderTestDeployment systemUnderTestDeployment);

    public static Optional<SystemUnderTestDeployment> systemUnderTestDeployment() {
        return Optional.ofNullable(systemUnderTestDeployment);
    }

    private static void setSystemUnderTestDeployment(SystemUnderTestDeployment newSystemUnderTestDeployment) {
        systemUnderTestDeployment = newSystemUnderTestDeployment;
    }

    @Override
    public void start() {
        IGlobalExtension.super.start();

        setSystemUnderTestDeployment(new SystemUnderTestDeployment());

        List<FlowStep> steps = configureDeploySteps(systemUnderTestDeployment);
        SequenceFlow deployFlow = new SequenceFlow(meterRegistry, "SystemUnderTestExtension.deploy", steps);
        deployFlow.execute();
    }

    @Override
    public void stop() {
        IGlobalExtension.super.stop();

        systemUnderTestDeployment().ifPresent(instance -> {
            instance.undeploy(meterRegistry, "SystemUnderTestExtension.undeploy");
            setSystemUnderTestDeployment(null);
        });
    }

}
