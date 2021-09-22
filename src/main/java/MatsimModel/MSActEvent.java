package MatsimModel;

import AbmModel.ActEvent;
import AbmModel.Facility;
import AbmModel.Person;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Implementation ActEvent concept that represents an acceptable ActEvent for Abm
 * A ActEvent is related to a person performing an activity in a specific location
 * Mandatory concept implementation
 */
@Deprecated
public class MSActEvent extends ActEvent {

    /**
     * Constructor
     * @param id the id of this event
     * @param time the time when the event append
     * @param type the type of this Event "actEvent"
     * @param x X event location
     * @param y Y event location
     * @param person person related to this event
     * @param vehicle vehicle related to this event -1
     * @param link link related to this event -1
     * @param facility facility related to this event
     * @param types type of the event not useful
     * @param personObject the person object related to this event
     * @param facilityObject the facility object related to this event
     */
    public MSActEvent(int id, double time, String type, double x, double y, int person, int vehicle, int link, int facility, Map<String, Set<String>> types, Person personObject, Facility facilityObject) {
        super(id, time, type, x, y, person, vehicle, link, facility, types, personObject, facilityObject);
    }

    /**
     * default constructor
     */
    public MSActEvent() {
        super(-1, 0.0, "", 0.0, 0.0, -1, -1, -1, -1, new HashMap<>(), null, null);
    }

    /**
     * copy constructor from MSActEvent
     * @param msActEvent Event
     */
    public MSActEvent(MSActEvent msActEvent) {
        super(msActEvent.getId(), msActEvent.getTime(), msActEvent.getType(), msActEvent.getX(), msActEvent.getY(), msActEvent.getPerson(), msActEvent.getVehicle(), msActEvent.getLink(), msActEvent.getFacility(), msActEvent.getTypes(), msActEvent.getPersonObject(), msActEvent.getFacilityObject());
    }

    /**
     * copy constructor from MSEvent
     * @param msEvent Event
     */
    public MSActEvent(MSEvent msEvent) {
        super(msEvent.getId(), msEvent.getTime(), msEvent.getType(), msEvent.getX(), msEvent.getY(), msEvent.getPerson(), msEvent.getVehicle(), msEvent.getLink(), msEvent.getFacility(), msEvent.getTypes(), null, null);
    }
}
