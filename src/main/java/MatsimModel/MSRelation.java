package MatsimModel;

import AbmModel.Relation;

/**
 * Relation implementation
 */
public class MSRelation extends Relation {

    /**
     * Constructor
     */
    public MSRelation() {
        super(new MSType());
        this.types.setInstance(this);
    }

    /**
     * Constructor
     * @param name names of the relation
     */
    public MSRelation(String name) {
        super(name, new MSType());
        this.types.setInstance(this);
    }
}
