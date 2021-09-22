package AbmModel;

import java.util.*;

/**
 * Abstract BoardingEvent concept that represents an acceptable BoardingEvent for Abm
 * BoardingEvent are event that prepare a movement
 * Mandatory concept
 */
public abstract class BoardingEvent extends LegEvent implements MandatorySimModel {

    /**
     * The person object, actor of the event
     * In the event, the person will enter or leave the targeted vehicle
     */
    protected Person personObject;

    /**
     * Constructor
     * @param id the id of this event
     * @param time the time when the event append
     * @param type the type of this Event "actEvent"
     * @param x X event location
     * @param y Y event location
     * @param person person related to this event
     * @param vehicle vehicle related to this event
     * @param link link related to this event -1
     * @param facility facility related to this event -1
     * @param types type of the event not useful
     * @param personObject the person object related to this event
     * @param vehicleObject the vehicle object related to this event
     */
    public BoardingEvent(int id, double time, String type, double x, double y, int person, int vehicle, int link, int facility, Map<String, Set<String>> types, Vehicle vehicleObject, Person personObject) {
        super(id, time, type, x, y, person, vehicle, link, facility, types, vehicleObject);
        this.personObject = personObject;
    }

    public Person getPersonObject() {
        return personObject;
    }

    public void setPersonObject(Person personObject) {
        this.personObject = personObject;
    }
}