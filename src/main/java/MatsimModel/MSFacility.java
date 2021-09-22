package MatsimModel;

import AbmModel.Facility;

/**
 * Implementation facility concept that represents an acceptable facility for Matsim simulation results
 * A facility represents a point of interest in the network
 * Optional concept implementation
 *
 * WHAT THE PROCESSOR SHOULD DO FOR THIS CLASS :
 * Nothing special for now...
 */
public class MSFacility extends Facility {

    /**
     * Constructor
     * @param id id of th facility
     * @param x X location
     * @param y Y location
     * @param linkID id of the link where the facility is
     */
    public MSFacility(int id, double x, double y, int linkID) {
        super(id, x, y, linkID, new MSType());
        this.types.setInstance(this);
    }

    /**
     * default constructor
     */
    public MSFacility() {
        super(-1, 0.0, 0.0, -1, new MSType());
        this.types.setInstance(this);
    }

}
