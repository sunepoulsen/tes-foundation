package dk.sunepoulsen.tes.rest.integrations.generators;

import dk.sunepoulsen.tes.data.generators.DataGenerator;

import java.util.UUID;

public interface TransactionIdsGenerator {

    DataGenerator<UUID> operationId();
    DataGenerator<String> transactionId();

}
