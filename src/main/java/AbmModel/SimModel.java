package AbmModel;

/**
 * Package AbmModel contains the interfaces and abstract classes
 * that provide users an API for simulation models
 *
 * The interface of acceptable simulation model for ABM
 * All data structures that ABM can deal with should implement this API
 */
public interface SimModel {

    /**
     * Add value(s) assigned to the specified type for the actual SimModel instance
     * @param type The String type
     * @param value The String value (it can be several values separated by comma in one String)
     */
    void addTypeEntry(String type, String value);

}
