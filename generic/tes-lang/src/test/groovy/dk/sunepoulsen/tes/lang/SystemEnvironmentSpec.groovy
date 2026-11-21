package dk.sunepoulsen.tes.lang

import spock.lang.Specification

import java.util.concurrent.TimeUnit

class SystemEnvironmentSpec extends Specification {

    void "Put Java home into the supplied environment"() {
        given:
            Map<String, String> environment = [JAVA_HOME: 'old', OTHER: 'unchanged']

        when:
            SystemEnvironment.putJavaHome(environment)

        then:
            environment == [JAVA_HOME: System.getProperty('java.home'), OTHER: 'unchanged']
    }

    void "Read a variable from a controlled process environment"() {
        given:
            String name = 'TES_SYSTEM_ENVIRONMENT_TEST'
            String classpath = [SystemEnvironmentProbe, SystemEnvironment].collect { Class<?> type ->
                new File(type.protectionDomain.codeSource.location.toURI()).path
            }.join(File.pathSeparator)
            ProcessBuilder builder = new ProcessBuilder(new File(System.getProperty('java.home'), 'bin/java').path,
                '-cp', classpath, SystemEnvironmentProbe.name, name)
            builder.environment().remove(name)
            if (value != null) {
                builder.environment().put(name, value)
            }

        when:
            Process process = builder.start()
            boolean finished = process.waitFor(5, TimeUnit.SECONDS)

        then:
            finished
            process.exitValue() == exitCode
            process.inputStream.text == output
            process.errorStream.text == error

        cleanup:
            process?.destroyForcibly()

        where:
            value       | exitCode | output       | error
            '  value  ' | 0        | '  value  '  | ''
            null        | 1        | ''           | 'Missing TES_SYSTEM_ENVIRONMENT_TEST'
            ''          | 1        | ''           | 'Missing TES_SYSTEM_ENVIRONMENT_TEST'
            ' \t '      | 1        | ''           | 'Missing TES_SYSTEM_ENVIRONMENT_TEST'
    }
}