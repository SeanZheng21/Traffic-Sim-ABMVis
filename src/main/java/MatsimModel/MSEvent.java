package MatsimModel;

import AbmModel.Event;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Implementation event concept that represents an acceptable event for Matsim simulation results
 * An event is the smallest unit that can be represented in a simulation
 * Mandatory concept implementation
 *
 * WHAT THE PROCESSOR SHOULD DO FOR THIS MODEL OBJECT :
 * <ul>
 *     <li>Create the related MSEvent object with the correct class type according to the type attribute as follow :</li>
 *     <ul>
 *         <li>“entered link”, “vehicle enters traffic” → VehiculeEntersLink</li>
 *         <li>“left link”, “vehicle leaves traffic” → VehiculeLeavesLink</li>
 *         <li>“PersonEntersVehicle”, “departure” → PersonEntersVehicle</li>
 *         <li>“PersonLeavesVehicle”, “arrival” → PersonLeavesVehicle</li>
 *         <li>“actstart” → ActStart</li>
 *         <li>“actend” → ActEnd</li>
 *     </ul>
 *     <li>Then, fill the "classObject" attributes with the correct model objects according to the related classID attributes
 *     For instance, vehicleObject must be fill with the correct Vehicle object from the model, thanks to the vehicle attribute
 *     which contain the ID of the expected object</li>
 * </ul>
 */

public class MSEvent extends Event {

    /**
     * Constructor
     * @param id the id of this event
     * @param time the time when the event append
     * @param type the type of this Event "actEvent"
     * @param x X event location
     * @param y Y event location
     * @param person person related to this event
     * @param vehicle vehicle related to this event
     * @param link link related to this event
     * @param facility facility related to this event
     * @param types type of the event not useful
     */
    public MSEvent(int id, double time, String type, double x, double y, int person, int vehicle, int link, int facility, Map<String, Set<String>> types) {
        super(id, time, type, x, y, person, vehicle, link, facility, types);
    }

    /**
     * default constructor
     */
    public MSEvent() {
        super(-1, 0.0, "", 0.0, 0.0, -1, -1, -1, -1, new HashMap<>());
    }

    /**
     * copy constructor
     * @param msEvent Event
     */
    public MSEvent(MSEvent msEvent) {
        super(msEvent.getId(), msEvent.getTime(), msEvent.getType(), msEvent.getX(), msEvent.getY(), msEvent.getPerson(), msEvent.getVehicle(), msEvent.getLink(), msEvent.getFacility(), msEvent.getTypes());
    }
}