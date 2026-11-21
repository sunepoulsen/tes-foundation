package dk.sunepoulsen.tes.rest.integrations.generators;

import dk.sunepoulsen.tes.data.generators.DataGenerator;

import java.util.UUID;

/** Supplies generators for operation and transaction identifiers. */
public interface TransactionIdsGenerator {

    /**
     * Returns an operation identifier generator.
     * @return the operation identifier generator
     */
    DataGenerator<UUID> operationId();
    /**
     * Returns a transaction identifier generator.
     * @return the transaction identifier generator
     */
    DataGenerator<String> transactionId();

}
