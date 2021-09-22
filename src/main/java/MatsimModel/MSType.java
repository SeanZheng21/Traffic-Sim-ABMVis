package MatsimModel;

import AbmModel.SimModel;
import AbmModel.Type;
import AbmModel.TypeCollector;

/**
 * Implementation of the type object
 */
public class MSType extends Type {

    /**
     * linked typeCollector
     */
    protected static TypeCollector collector = new MSTypeCollector();

    /**
     * Constructor
     * @param instance the instance related to this type Object
     */
    public MSType(SimModel instance) {
        super(instance);
    }

    /**
     * Constructor
     */
    public MSType() {
        super();
    }
}
