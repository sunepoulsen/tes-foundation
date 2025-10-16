package dk.sunepoulsen.tes.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
public class ProcessExecutor {

    private final Consumer<String> stdOutConsumer;
    private final Consumer<String> stdErrorConsumer;

    public ProcessExecutor() {
        this.stdOutConsumer = log::info;
        this.stdErrorConsumer = log::error;
    }

    public int execute(String... command) throws Exception {
        ProcessBuilder pb = new ProcessBuilder(command);

        log.info("Executing command: {}", String.join(" ", command));
        Process p = pb.start();

        try( ExecutorService ex = Executors.newFixedThreadPool(2) ) {
            ex.submit(streamCollect(p.getInputStream(), stdOutConsumer));
            ex.submit(streamCollect(p.getErrorStream(), stdErrorConsumer));

            int exit = p.waitFor();
            ex.shutdown();
            ex.awaitTermination(5, TimeUnit.SECONDS);
            return exit;
        }
    }

    private static Runnable streamCollect(InputStream is, Consumer<String> consumer) {
        return () -> {
            try (BufferedReader r = new BufferedReader(new InputStreamReader(is))) {
                String line;
                while ((line = r.readLine()) != null) {
                    consumer.accept(line);
                }
            } catch (IOException e) {
                log.warn("Error reading process stream", e);
            }
        };
    }

}
