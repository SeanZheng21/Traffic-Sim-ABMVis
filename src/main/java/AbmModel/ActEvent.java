package AbmModel;

import java.util.*;

/**
 * Abstract ActEvent concept that represents an acceptable ActEvent for Abm
 * A ActEvent is related to a person performing an activity in a specific location
 * Mandatory concept
 */
public abstract class ActEvent extends Event implements MandatorySimModel {

    /**
     * The person actor of the event
     */
    protected Person personObject;

    /**
     * The targeted facility or location of the event, in which the person will perform his activity
     */
    protected Facility facilityObject;

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
    public ActEvent(int id, double time, String type, double x, double y, int person, int vehicle, int link, int facility, Map<String, Set<String>> types, Person personObject, Facility facilityObject) {
        super(id, time, type, x, y, person, vehicle, link, facility, types);
        this.personObject = personObject;
        this.facilityObject = facilityObject;
    }

    public Person getPersonObject() {
        return personObject;
    }

    public void setPersonObject(Person personObject) {
        this.personObject = personObject;
    }

    public Facility getFacilityObject() {
        return facilityObject;
    }

    public void setFacilityObject(Facility facilityObject) {
        this.facilityObject = facilityObject;
    }
}