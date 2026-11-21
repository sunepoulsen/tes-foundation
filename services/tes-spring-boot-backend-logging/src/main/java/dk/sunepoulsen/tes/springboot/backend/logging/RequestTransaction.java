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

    /**
     * Initializes the request transaction with its identifier generators.
     *
     * @param operationId generator of the operation ID
     * @param transactionId generator of the transaction ID
     */
    void initialize(FixedDataGenerator<UUID> operationId, FixedDataGenerator<String> transactionId);

    /** Invalidates the request transaction. */
    void invalidate();

    /**
     * Checks whether the request transaction is valid.
     *
     * @return whether the transaction is valid
     */
    boolean isValid();

    /**
     * Returns the operation ID generator.
     *
     * @return the operation ID generator
     * @throws dk.sunepoulsen.tes.springboot.backend.logging.exceptions.RequestTransactionInvalidException if the transaction is invalid
     */
    FixedDataGenerator<UUID> operationId();

    /**
     * Returns the transaction ID generator.
     *
     * @return the transaction ID generator
     * @throws dk.sunepoulsen.tes.springboot.backend.logging.exceptions.RequestTransactionInvalidException if the transaction is invalid
     */
    FixedDataGenerator<String> transactionId();

    /**
     * Returns a generator of sub-transaction IDs.
     *
     * @return the sub-transaction ID generator
     * @throws dk.sunepoulsen.tes.springboot.backend.logging.exceptions.RequestTransactionInvalidException if the transaction is invalid
     */
    DataGenerator<String> subTransactionId();
}
