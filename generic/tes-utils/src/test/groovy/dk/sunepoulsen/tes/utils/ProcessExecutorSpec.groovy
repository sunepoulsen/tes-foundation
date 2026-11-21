package dk.sunepoulsen.tes.utils

import spock.lang.Specification

import java.util.function.Consumer

class ProcessExecutorSpec extends Specification {

    void "Collect stdout and stderr lines and return the exit code"() {
        given:
            List<String> stdout = []
            List<String> stderr = []
            ProcessExecutor executor = new ProcessExecutor(stdout.&add as Consumer<String>, stderr.&add as Consumer<String>, null)

        when:
            int exit = executor.execute('sh', '-c', 'printf "first\nsecond\n"; printf "error one\nerror two\n" >&2; exit 7')

        then:
            exit == 7
            stdout == ['first', 'second']
            stderr == ['error one', 'error two']
    }

    void "Decorate the process builder before starting the process"() {
        given:
            List<String> stdout = []
            List<String> stderr = []
            List<String> command = []
            Consumer<ProcessBuilder> decorator = { ProcessBuilder builder ->
                command.addAll(builder.command())
                builder.environment().put('TES_PROCESS_EXECUTOR_TEST', 'decorated')
            } as Consumer<ProcessBuilder>
            ProcessExecutor executor = new ProcessExecutor(stdout.&add as Consumer<String>, stderr.&add as Consumer<String>, decorator)

        when:
            int exit = executor.execute('sh', '-c', 'printf "%s\n" "$TES_PROCESS_EXECUTOR_TEST"')

        then:
            exit == 0
            command == ['sh', '-c', 'printf "%s\n" "$TES_PROCESS_EXECUTOR_TEST"']
            stdout == ['decorated']
            stderr.empty
    }

    void "Default and decorator-only constructors execute commands"() {
        given:
            boolean decorated = false
            ProcessExecutor executor = withDecorator ? new ProcessExecutor({ ProcessBuilder builder -> decorated = true } as Consumer<ProcessBuilder>) : new ProcessExecutor()

        expect:
            executor.execute('sh', '-c', 'exit 0') == 0
            decorated == withDecorator

        where:
            withDecorator << [false, true]
    }

    void "Propagate process startup failures"() {
        when:
            new ProcessExecutor().execute('/nonexistent/tes-process-executor-command')

        then:
            thrown(IOException)
    }

    void "Propagate decorator failures"() {
        given:
            RuntimeException failure = new RuntimeException('decoration failed')
            ProcessExecutor executor = new ProcessExecutor({ ProcessBuilder builder -> throw failure } as Consumer<ProcessBuilder>)

        when:
            executor.execute('sh', '-c', 'exit 0')

        then:
            RuntimeException thrownException = thrown()
            thrownException.is(failure)
    }
}