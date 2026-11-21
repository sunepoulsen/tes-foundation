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

/** Executes commands and forwards their output streams to consumers. */
@Slf4j
@RequiredArgsConstructor
public class ProcessExecutor {

    /** Consumer of standard output lines. */
    private final Consumer<String> stdOutConsumer;
    /** Consumer of standard error lines. */
    private final Consumer<String> stdErrorConsumer;
    /** Optional configuration applied to the process builder before starting. */
    private final Consumer<ProcessBuilder> decoratorConsumer;

    /** Creates an executor that logs output and leaves the process builder unchanged. */
    public ProcessExecutor() {
        this(processBuilder -> {});
    }

    /**
     * Creates an executor that logs output and decorates the process builder.
     *
     * @param decoratorConsumer builder configuration, or null for none
     */
    public ProcessExecutor(Consumer<ProcessBuilder> decoratorConsumer) {
        this.stdOutConsumer = log::info;
        this.stdErrorConsumer = log::error;
        this.decoratorConsumer = decoratorConsumer;
    }

    /**
     * Starts the command, forwards its output, and waits for it to exit.
     *
     * @param command executable and arguments
     * @return process exit code
     * @throws Exception if starting or waiting for the process fails
     */
    public int execute(String... command) throws Exception {
        ProcessBuilder pb = new ProcessBuilder(command);
        if (decoratorConsumer != null) {
            decoratorConsumer.accept(pb);
        }

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
