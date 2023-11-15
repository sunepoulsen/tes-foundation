package dk.sunepoulsen.tes.jmeter;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.GenericContainer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Properties;
import java.util.stream.Stream;

/**
 * Class to setup and execute a JMeter plan against a docker container.
 */
@Slf4j
public class JMeterExecutor {
    public static final String JMETER_EXECUTABLE = "/usr/local/bin/jmeter";
    public static final File STATISTIC_RESULT_FILE = new File("build/test-results/jmeter/report-html/statistics.json");
    public static final File WORKING_DIR = new File("build/test-results/jmeter");

    Integer containerPort;

    public <T extends GenericContainer<T>> void prepareExecutor(GenericContainer<T> container) throws IOException {
        findExternalPorts(container);
        ensureWorkingDirectory();
        createPropertyFile();
    }

    public boolean runTests() throws IOException, InterruptedException {
        var processBuilder = new ProcessBuilder();
        processBuilder.command(JMETER_EXECUTABLE, "-n", "-t", "../../../src/test/resources/stress-test.jmx", "-p", "stress-test.properties", "-l", "results.jtl", "-e", "-o", "report-html");
        processBuilder.directory(WORKING_DIR);

        log.info("Executing {}", String.join(" ", processBuilder.command()));
        var process = processBuilder.start();

        process.waitFor();
        var output = new String(process.getInputStream().readAllBytes());

        log.info("Standard output:\n{}", output);
        log.info("Exit code: {}", process.exitValue());

        return process.exitValue() == 0;

    }

    private <T extends GenericContainer<T>> void findExternalPorts(GenericContainer<T> container) {
        containerPort = container.getMappedPort(8080);
        log.info("Container {} is accessible on port ${}", container.getDockerImageName(), containerPort);
    }

    private void ensureWorkingDirectory() throws IOException {
        if (WORKING_DIR.exists()) {
            try (Stream<Path> walk = Files.walk(WORKING_DIR.toPath())) {
                walk.sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);

            }
        }

        WORKING_DIR.mkdirs();
    }

    private void createPropertyFile() throws IOException {
        String propertyFilename = WORKING_DIR.getAbsolutePath() + "/stress-test.properties";

        try {
            var propertyFile = "/user.properties";
            var profile = System.getProperty("stress.test.profile");
            if (profile != null && !profile.isEmpty()) {
                propertyFile = String.format("/user-%s.properties", profile);
            }

            var properties = new Properties();
            properties.load(getClass().getResourceAsStream(propertyFile));

            properties.put("service.port", containerPort.toString());

            log.info("Using {} property file with the stress test", propertyFile);
            try (var outputStream = new FileOutputStream(propertyFilename)) {
                properties.store(outputStream, "");
            }
        }
        catch( IOException ex ) {
            log.error("Unable to create property file for the {}", propertyFilename, ex);
            throw ex;
        }
    }
}
