package dk.sunepoulsen.tes.deployment.core.function;

import dk.sunepoulsen.tes.flows.exceptions.FlowStepException;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 * Supplies a mutable value, which may be absent.
 * @param <T> type of the stored value
 */
public class AtomicDataSupplier<T> implements Supplier<Optional<T>> {
    /** Holds the current value. */
    private final AtomicReference<T> reference;

    /** Creates a supplier with no value. */
    public AtomicDataSupplier() {
        this.reference = new AtomicReference<>();
    }

    /**
     * Creates a supplier with the given value.
     * @param value initial value, or null for no value
     */
    public AtomicDataSupplier(T value) {
        this();
        set(value);
    }

    /** @return the current value, or an empty optional if it is null */
    @Override
    public synchronized Optional<T> get() {
        return Optional.ofNullable(reference.get());
    }

    /**
     * Returns the current value or fails if it is absent.
     * @param message message for the exception if no value is present
     * @return the current value
     * @throws FlowStepException if the value is absent
     */
    public synchronized T get(String message) {
        return get().orElseThrow(() ->
            new FlowStepException(message)
        );
    }

    /**
     * Replaces the current value.
     * @param value new value, or null to clear it
     */
    public synchronized void set(T value) {
        this.reference.set(value);
    }

}
