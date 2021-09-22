package MatsimModel;

import AbmModel.ActEnd;
import AbmModel.Facility;
import AbmModel.Person;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Implementation ActEnd event concept that represents an acceptable ActEnd event for Abm
 * This type of event represent the end of an activity
 * Mandatory concept implementation
 */
public class MSActEnd extends ActEnd {

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
    public MSActEnd(int id, double time, String type, double x, double y, int person, int vehicle, int link, int facility, Map<String, Set<String>> types, Person personObject, Facility facilityObject) {
        super(id, time, type, x, y, person, vehicle, link, facility, types, personObject, facilityObject);
    }

    /**
     * default constructor
     */
    public MSActEnd() {
        super(-1, 0.0, "", 0.0, 0.0, -1, -1, -1, -1, new HashMap<>(), null, null);
    }

    /**
     * copy constructor from MSActEnd
     * @param msActEnd Event
     */
    public MSActEnd(MSActEnd msActEnd) {
        super(msActEnd.getId(), msActEnd.getTime(), msActEnd.getType(), msActEnd.getX(), msActEnd.getY(), msActEnd.getPerson(), msActEnd.getVehicle(), msActEnd.getLink(), msActEnd.getFacility(), msActEnd.getTypes(), msActEnd.getPersonObject(), msActEnd.getFacilityObject());
    }

    /**
     * copy constructor from MSActEvent
     * @param msActEvent Event
     */
    public MSActEnd(MSActEvent msActEvent) {
        super(msActEvent.getId(), msActEvent.getTime(), msActEvent.getType(), msActEvent.getX(), msActEvent.getY(), msActEvent.getPerson(), msActEvent.getVehicle(), msActEvent.getLink(), msActEvent.getFacility(), msActEvent.getTypes(), msActEvent.getPersonObject(), msActEvent.getFacilityObject());
    }

    /**
     * copy constructor from MSEvent
     * @param msEvent Event
     */
    public MSActEnd(MSEvent msEvent) {
        super(msEvent.getId(), msEvent.getTime(), msEvent.getType(), msEvent.getX(), msEvent.getY(), msEvent.getPerson(), msEvent.getVehicle(), msEvent.getLink(), msEvent.getFacility(), msEvent.getTypes(), null, null);
    }
}
