package AbmModel;

import java.util.*;

/**
 * Abstract ActEnd event concept that represents an acceptable ActEnd event for Abm
 * This type of event represent the end of an activity
 * Mandatory concept
 */
public abstract class ActEnd extends ActEvent implements MandatorySimModel {

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
    public ActEnd(int id, double time, String type, double x, double y, int person, int vehicle, int link, int facility, Map<String, Set<String>> types, Person personObject, Facility facilityObject) {
        super(id, time, type, x, y, person, vehicle, link, facility, types, personObject, facilityObject);
    }

}