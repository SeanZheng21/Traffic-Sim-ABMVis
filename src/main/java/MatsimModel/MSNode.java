package MatsimModel;

import AbmModel.Node;

/**
 * Implementation node concept that represents an acceptable node for Matsim simulation results
 * A node represents a point in the network
 * Mandatory concept implementation
 *
 * WHAT THE PROCESSOR SHOULD DO FOR THIS CLASS :
 * for now nothing special...
 */
public class MSNode extends Node {

    /**
     * Constructor
     * @param id id of the node
     * @param x x location
     * @param y y location
     * @param type types of the node
     */
    public MSNode(int id, double x, double y, String type) {
        super(id, x, y, type, new MSType());
        this.types.setInstance(this);
    }

    /**
     * default constructor
     */
    public MSNode() {
        super(-1, 0.0, 0.0, "", new MSType());
        this.types.setInstance(this);
    }
}
