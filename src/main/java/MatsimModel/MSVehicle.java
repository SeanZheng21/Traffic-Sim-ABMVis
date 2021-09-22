package MatsimModel;

import AbmModel.Event;
import AbmModel.Vehicle;

import java.util.*;

/**
 * Implementation vehicle concept that represents an acceptable vehicle for Matsim simulation results
 * A vehicle is a transport
 * Mandatory concept implementation
 *
 * WHAT THE PROCESSOR SHOULD DO FOR THIS CLASS :
 * Fill the "relatedEvents" list with all the MSEvents in which the vehicle (this) is involved
 * They have to be in the right order according to the time
 */
public class MSVehicle extends Vehicle {

    /**
     * Constructor
     * @param id id of the vehicle
     * @param relatedEvents List of events related to this vehicle
     */
    public MSVehicle(int id, List<Event> relatedEvents) {
        super(id, relatedEvents, new MSType());
        this.types.setInstance(this);
    }

    /**
     * default constructor
     */
    public MSVehicle() {
        super(-1, new ArrayList<>(), new MSType());
        this.types.setInstance(this);
    }
}
