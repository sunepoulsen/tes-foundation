package dk.sunepoulsen.tes.rest.integrations.generators;

import dk.sunepoulsen.tes.data.generators.DataGenerator;
import dk.sunepoulsen.tes.data.generators.DataSupplier;
import dk.sunepoulsen.tes.data.generators.Generators;

import java.util.UUID;

/**
 * Constructs request ids based on an UUID and a sequence number.
 * <p>
 *     The sequence number is increased every time the request id
 *     is generated.
 * </p>
 * <p>
 *     The format of the request id is: <code>&lt;uuid&gt;-&lt;sequence number&gt;</code>
 * </p>
 */
public class DefaultTransactionIdsGenerator implements TransactionIdsGenerator {

    @Override
    public DataGenerator<UUID> operationId() {
        return Generators.uuidGenerator();
    }

    @Override
    public DataGenerator<String> transactionId() {
        return new DataSupplier<>(() -> UUID.randomUUID().toString());
    }

}
