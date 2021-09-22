package AbmParser;

import AbmModel.SimModel;
import java.util.Set;

/**
 * The abstract parser concept that processes a file that contains sim info to one type instances of AbmModel
 * All acceptable parser implementations should extends this abstract class.
 * A parser is instantiated by the parser configurer according to the configuration file.
 * Parser should be accessed by the parser configurer and only
 * A user should never instantiate a parser manually
 * A parser should be created by a configurer according to its configuration instructions
 */
public abstract class Parser {
    /**
     * The path of the file to work on for the parser.
     * It can be a relative path or an absolute path.
     */
    private String inputFile;
    /**
     * The implementation model class's name, without its package name
     * This modelName should be concrete and there has to be a default constructor
     */
    private String modelName;
    /**
     * The path to look in the input file for instances.
     * For XML files, it can be the XPath to the instances' tag.
     * This attribute is not directly used by the parser configurer,
     * but is necessary for all parsers to indicate the path to instances.
     */
    private String path;
    /**
     * The abstract model class's name, without its package name
     * This superModeName should be abstract and there has to be getters and setters for abstract attributes.
     * This is used for polymorphism in the Scenario storage.
     * It should be a valid AbmModel class.
     */
    private String superModeName;
    /**
     * All attribute values that are qualified as type for the parsed model
     * These attributes have to be added with the addTypeEntry SimModel method
     * It will be then added to the Type object of the model instance
     * and in TypeCollector single instance of the current visualization
     */
    private Set<String> types;

    /**
     * Creates a parser, called by the parser configurer
     * @param inputName The implementation model class'es name
     * @param modelName The implementation model class'es name
     * @param path The path to look in the input file for instances
     * @param superModeName The abstract model class'es name
     * @param types Set of attribute names that can be considered as type
     */
    Parser(String inputName, String modelName, String path, String superModeName, Set<String> types) {
        this.inputFile = inputName;
        this.modelName = modelName;
        this.path = path;
        this.superModeName = superModeName;
        this.types = types;
    }

    /**
     * Default constructor for creating new instances
     */
    Parser() {
        inputFile = "";
        modelName = "";
        path = "";
        superModeName = "";
    }

    /**
     * Get the concrete class that the parser produces
     * This helps the scenario's storage for RTTI (Run Time Type Identification)
     * returns null if the class is not defined or can't be found
     * @return the concrete class that the parser produces
     */
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
     * to produce model data, called by the parser configurer
     * @return the set of instances found under the input path
     */
    abstract Set<? extends SimModel> parse() throws ParserException;

    public String getInputFile() {
        return inputFile;
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSuperModeName() {
        return superModeName;
    }

    public void setSuperModeName(String superModeName) {
        this.superModeName = superModeName;
    }

    public Set<String> getTypes() {
        return types;
    }

    public void setTypes(Set<String> types) {
        this.types = types;
    }
}
