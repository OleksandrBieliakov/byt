package part2.exercise02;

public class ConfigurationException extends Exception {

    private static final long serialVersionUID = 1L;

    public ConfigurationException() {
        super();
    }

    public ConfigurationException(String message) {
        super(message);
    }

    public ConfigurationException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ConfigurationException(Throwable throwable) {
        super(throwable);
    }

}
