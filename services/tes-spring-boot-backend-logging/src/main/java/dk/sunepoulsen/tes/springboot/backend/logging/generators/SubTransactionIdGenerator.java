package dk.sunepoulsen.tes.springboot.backend.logging.generators;

import dk.sunepoulsen.tes.data.generators.DataGenerator;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Generates sequential sub-transaction IDs for a transaction.
 */
public class SubTransactionIdGenerator implements DataGenerator<String> {

    /** Transaction ID used as the prefix of generated IDs. */
    private final String transactionId;
    /** Next sequence number to append to the transaction ID. */
    private final AtomicInteger counter;

    /**
     * Creates a generator whose sequence starts at one.
     *
     * @param transactionId transaction ID used as the prefix
     */
    public SubTransactionIdGenerator(String transactionId) {
        this.transactionId = transactionId;
        this.counter = new AtomicInteger(1);
    }

    @Override
    public String generate() {
        return transactionId + ':' + counter.getAndIncrement();
    }

}
