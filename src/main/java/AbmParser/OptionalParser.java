package AbmParser;

import AbmModel.OptionalSimModel;
import AbmModel.SimModel;

import java.util.Set;

/**
 * The abstract optional parser concept that processes a file that contains sim info to one type instances of AbmModel
 * Parser should be accessed by the parser configurer and only
 * A user should never instantiate a parser manually
 * A parser should be created by a configurer according to its configuration instructions
 */
public abstract class OptionalParser extends Parser {

    /**
     * Creates an optional parser, called by the parser configurer
     * @param inputFile The implementation model class'es name
     * @param modelName The implementation model class'es name
     * @param path The path to look in the input file for instances
     * @param superModeName The abstract model class'es name
     * @param types Set of attribute names that can be considered as type
     */
    public OptionalParser(String inputFile, String modelName, String path, String superModeName, Set<String> types) {
        super(inputFile, modelName, path, superModeName, types);
    }

    /**
     * Default constructor for creating new instances
     */
    public OptionalParser() {
        super();
    }

    /**
     * Get the concrete class that the parser produces
     * returns null if the class is not defined or can't be found
     * @return the concrete class that the parser produces
     */
    @Override
    Class<? extends SimModel> getModelClass() {
        try {
            //noinspection unchecked
            return (Class<? extends SimModel>) Class.forName(getModelName());
        } catch (ClassNotFoundException e) {
            System.err.println("Unknown model class name: " + getModelName());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Abstract method that launches a configured parser's parsing process
     * to produce optional model data, called by the parser configurer
     * @return the set of instances found under the input path
     */
    @Override
    public abstract Set<OptionalSimModel> parse() throws ParserException;

}

