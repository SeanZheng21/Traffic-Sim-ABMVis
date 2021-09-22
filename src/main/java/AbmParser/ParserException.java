package AbmParser;

/**
 * Exceptions handling for the parser
 */
public class ParserException extends Exception {

    /**
     * Default constructor
     */
    public ParserException() {
    }

    /**
     * Constructor with message
     * @param message The string message
     */
    public ParserException(String message) {
        super(message);
    }


}
