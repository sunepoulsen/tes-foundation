package dk.sunepoulsen.tes.flows;

import lombok.extern.slf4j.Slf4j;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Slf4j
public class LogFlowReport implements FlowReport {

    @Override
    public void printReport(List<FlowStepExecutor> steps) {
        log.info("");
        log.info("Flow Report of flow with {} steps", steps.size());
        log.info("================================================");
        log.info("");

        NumberFormat numberFormatter = NumberFormat.getInstance(Locale.of("en", "DK"));
        numberFormatter.setMinimumFractionDigits(3);
        numberFormatter.setMaximumFractionDigits(3);

        steps.forEach(step -> {
            String statusText = step.getStatus().toString();
            if (step.getStatus().equals(FlowStepExecutorStatus.FINISHED)) {
                statusText = step.getFlowStepResult().toString();
            }

            if (step.getStatus().equals(FlowStepExecutorStatus.FINISHED)) {
                log.info("Step {} with status {}. Duration: {} ms",
                    step.getFlowStep().timerName(), statusText,
                    numberFormatter.format(step.getTimer().totalTime(TimeUnit.MILLISECONDS)));
            } else {
                log.info("Step {} with status {}.", step.getFlowStep().timerName(), statusText);
            }
        });

        if (!steps.isEmpty()) {
            log.info("");
        }

        double flowTotalTime = steps.stream()
            .mapToDouble(step -> step.getTimer().totalTime(TimeUnit.MILLISECONDS))
            .sum();
        log.info("Executed {} steps in {} ms.", steps.size(), numberFormatter.format(flowTotalTime));
        log.info("");
    }

}
