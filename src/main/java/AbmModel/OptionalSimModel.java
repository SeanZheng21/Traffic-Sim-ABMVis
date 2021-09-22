package AbmModel;

/**
 * The interface of acceptable optional simulation model for ABM
 * All optional data structures that ABM can deal with should implement this API
 *
 * An optional model is not an essential concept for a simulation
 * It brings additional information to the scenario and they can be ignored by choice
 */
public interface OptionalSimModel extends SimModel {
}
