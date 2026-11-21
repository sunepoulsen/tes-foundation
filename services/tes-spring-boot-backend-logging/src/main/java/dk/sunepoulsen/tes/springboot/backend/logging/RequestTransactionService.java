package dk.sunepoulsen.tes.springboot.backend.logging;

import dk.sunepoulsen.tes.data.generators.DataGenerator;
import dk.sunepoulsen.tes.data.generators.FixedDataGenerator;
import dk.sunepoulsen.tes.springboot.backend.logging.exceptions.RequestTransactionInvalidException;
import dk.sunepoulsen.tes.springboot.backend.logging.generators.SubTransactionIdGenerator;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.UUID;

/**
 * Request-scoped storage for transaction identifier generators.
 */
@Component
@RequestScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RequestTransactionService implements RequestTransaction {

    /** Message used when identifiers are requested before initialization or after invalidation. */
    private static final String INSTANCE_NOT_INITIALIZED_EXCEPTION_MESSAGE = "RequestTransactionService has not been initialized";

    /** Whether the transaction has been initialized and not invalidated. */
    private boolean initialized;
    /** Generator of the operation ID. */
    private FixedDataGenerator<UUID> operationId;
    /** Generator of the transaction ID. */
    private FixedDataGenerator<String> transactionId;
    /** Generator of IDs for sub-transactions. */
    private SubTransactionIdGenerator subTransactionIdGenerator;

    /** Creates an initially invalid request transaction. */
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
