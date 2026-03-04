package dk.sunepoulsen.tes.springboot.backend.logging;

import dk.sunepoulsen.tes.data.generators.DataGenerator;
import dk.sunepoulsen.tes.data.generators.FixedDataGenerator;
import dk.sunepoulsen.tes.springboot.backend.logging.exceptions.RequestTransactionInvalidException;
import dk.sunepoulsen.tes.springboot.backend.logging.generators.SubTransactionIdGenerator;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.UUID;

@Component
@RequestScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RequestTransactionService implements RequestTransaction {

    private static final String INSTANCE_NOT_INITIALIZED_EXCEPTION_MESSAGE = "RequestTransactionService has not been initialized";

    private boolean initialized;
    private FixedDataGenerator<UUID> operationId;
    private FixedDataGenerator<String> transactionId;
    private SubTransactionIdGenerator subTransactionIdGenerator;

    public RequestTransactionService() {
        this.initialized = false;
    }

    @Override
    public void initialize(final FixedDataGenerator<UUID> operationId, final FixedDataGenerator<String> transactionId) {
        this.initialized = true;
        this.operationId = operationId;
        this.transactionId = transactionId;
        this.subTransactionIdGenerator = new SubTransactionIdGenerator(transactionId.generate());
    }

    @Override
    public void invalidate() {
        this.initialized = false;
        this.operationId = null;
        this.transactionId = null;
    }

    @Override
    public boolean isValid() {
        return initialized;
    }

    @Override
    public FixedDataGenerator<UUID> operationId() {
        if (!isValid()) {
            throw new RequestTransactionInvalidException(INSTANCE_NOT_INITIALIZED_EXCEPTION_MESSAGE);
        }

        return this.operationId;
    }

    @Override
    public FixedDataGenerator<String> transactionId() {
        if (!isValid()) {
            throw new RequestTransactionInvalidException(INSTANCE_NOT_INITIALIZED_EXCEPTION_MESSAGE);
        }

        return this.transactionId;
    }

    @Override
    public DataGenerator<String> subTransactionId() {
        if (!isValid()) {
            throw new RequestTransactionInvalidException(INSTANCE_NOT_INITIALIZED_EXCEPTION_MESSAGE);
        }

        return this.subTransactionIdGenerator;
    }
}
