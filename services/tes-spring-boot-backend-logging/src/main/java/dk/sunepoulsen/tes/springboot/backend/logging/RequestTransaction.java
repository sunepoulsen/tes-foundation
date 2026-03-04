package dk.sunepoulsen.tes.springboot.backend.logging;

import dk.sunepoulsen.tes.data.generators.DataGenerator;
import dk.sunepoulsen.tes.data.generators.FixedDataGenerator;

import java.util.UUID;

/**
 * Interface to represent transactions id's from the current request.
 */
public interface RequestTransaction {

    String OPERATION_ID_HEADER_NAME = "X-OPERATION-ID";
    String TRANSACTION_ID_HEADER_NAME = "X-TRANSACTION-ID";
    String OPERATION_ID_MDC_NAME = OPERATION_ID_HEADER_NAME;
    String TRANSACTION_ID_MDC_NAME = TRANSACTION_ID_HEADER_NAME;

    void initialize(FixedDataGenerator<UUID> operationId, FixedDataGenerator<String> transactionId);
    void invalidate();
    boolean isValid();

    FixedDataGenerator<UUID> operationId();
    FixedDataGenerator<String> transactionId();
    DataGenerator<String> subTransactionId();
}
