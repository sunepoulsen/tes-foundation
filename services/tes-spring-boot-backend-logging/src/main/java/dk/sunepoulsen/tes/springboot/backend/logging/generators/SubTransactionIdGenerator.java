package dk.sunepoulsen.tes.springboot.backend.logging.generators;

import dk.sunepoulsen.tes.data.generators.DataGenerator;

import java.util.concurrent.atomic.AtomicInteger;

public class SubTransactionIdGenerator implements DataGenerator<String> {

    private final String transactionId;
    private final AtomicInteger counter;

    public SubTransactionIdGenerator(String transactionId) {
        this.transactionId = transactionId;
        this.counter = new AtomicInteger(1);
    }

    @Override
    public String generate() {
        return transactionId + ':' + counter.getAndIncrement();
    }

}
