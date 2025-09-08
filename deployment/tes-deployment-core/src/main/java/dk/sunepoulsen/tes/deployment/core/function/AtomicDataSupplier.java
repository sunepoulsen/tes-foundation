package dk.sunepoulsen.tes.deployment.core.function;

import dk.sunepoulsen.tes.flows.exceptions.FlowStepException;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class AtomicDataSupplier<T> implements Supplier<Optional<T>> {
    private final AtomicReference<T> reference;

    public AtomicDataSupplier() {
        this.reference = new AtomicReference<>();
    }

    public AtomicDataSupplier(T value) {
        this();
        set(value);
    }

    @Override
    public synchronized Optional<T> get() {
        return Optional.ofNullable(reference.get());
    }

    public synchronized T get(String message) {
        return get().orElseThrow(() ->
            new FlowStepException(message)
        );
    }

    public synchronized void set(T value) {
        this.reference.set(value);
    }

}
