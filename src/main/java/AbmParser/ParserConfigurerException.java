package AbmParser;

/**
 * Exceptions handling for the ParserConfigurer
 */
public class ParserConfigurerException extends Exception {

    /**
     * Default constructor
     */
    public ParserConfigurerException() {
    }

    /**
     * Constructor with message
     * @param message The string message
     */
    public ParserConfigurerException(String message) {
        super(message);
    }
}
