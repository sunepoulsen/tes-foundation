package dk.sunepoulsen.tes.lang;

public class SystemEnvironmentProbe {
    public static void main(String[] args) {
        try {
            System.out.print(SystemEnvironment.readVariable(args[0]));
        } catch (IllegalStateException exception) {
            System.err.print(exception.getMessage());
            System.exit(1);
        }
    }
}