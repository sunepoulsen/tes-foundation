package dk.sunepoulsen.tes.springboot.rest.logic;

import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.function.BooleanSupplier;

/**
 * {@code Waits} has the capability to wait for other operations to complete based on a provided
 * condition for that operation.
 * <p>
 *     It defines some standard durations that is used to sleep the current thread when we are
 *     waiting for the condition to be meet. The durations are:
 *     <ul>
 *         <li>
 *             <b>Duration</b>: The maximal duration to wait for the operation to complete.
 *             See {@code DEFAULT_DURATION}
 *         </li>
 *         <li>
 *             <b>Sleep</b>: The duration to sleep the current thread before the condition is
 *             checked again. See {@code DEFAULT_SLEEP_DURATION}
 *         </li>
 *     </ul>
 */
@Slf4j
public class Waits {

    public static final Duration DEFAULT_DURATION = Duration.ofSeconds(30);
    public static final Duration DEFAULT_SLEEP_DURATION = Duration.ofMillis(200);

    private final Duration duration;
    private final Duration sleepDuration;

    /**
     * Constructs {@code Waits} with standard durations.
     */
    public Waits() {
        this(DEFAULT_DURATION, DEFAULT_SLEEP_DURATION);
    }

    /**
     * Constructs {@code Waits} with custom durations.
     *
     * @param duration Total duration to wait for.
     * @param sleepDuration Sleep duration between each iteration.
     */
    public Waits(Duration duration, Duration sleepDuration) {
        this.duration = duration;
        this.sleepDuration = sleepDuration;
    }

    /**
     * Waits for the condition to be {@code true}
     *
     * @param condition Condition to meet with true
     *
     * @return {@code true} if the condition was meet, {@code false} otherwise.
     *
     * @throws InterruptedException In case the sleep was interrupted.
     */
    public boolean wait(BooleanSupplier condition ) throws InterruptedException {
        long start = System.currentTimeMillis();
        long end = start;

        while( (end - start < duration.toMillis()) && !condition.getAsBoolean() ) {
            Thread.sleep( sleepDuration.toMillis() );
            end = System.currentTimeMillis();
        }

        return end - start < duration.toMillis();
    }

    /**
     * Waits for the condition to be {@code true}
     *
     * @param condition Condition to meet with true
     *
     * @return {@code true} if the condition was meet, {@code false} otherwise.
     *
     * @throws InterruptedException In case the sleep was interrupted.
     */
    public static boolean waitFor(BooleanSupplier condition ) throws InterruptedException {
        return waitFor(DEFAULT_DURATION, DEFAULT_SLEEP_DURATION, condition);
    }

    /**
     * Waits for the condition to be {@code true}
     *
     * @param duration Total duration to wait for.
     * @param sleep Sleep duration between each iteration.
     * @param condition Condition to meet with true
     *
     * @return {@code true} if the condition was meet, {@code false} otherwise.
     *
     * @throws InterruptedException In case the sleep was interrupted.
     */
    public static boolean waitFor(Duration duration, Duration sleep, BooleanSupplier condition ) throws InterruptedException {
        return new Waits(duration, sleep).wait(condition);
    }

}
