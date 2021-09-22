package AbmModel;

import java.util.*;

/**
 * Abstract PersonLeavesVehicle event concept that represents an acceptable PersonLeavesVehicle event for Abm
 * This type of event represent a person leaving a vehicle
 * Mandatory concept
 */
public abstract class PersonLeavesVehicle extends BoardingEvent implements MandatorySimModel {

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
    public PersonLeavesVehicle(int id, double time, String type, double x, double y, int person, int vehicle, int link, int facility, Map<String, Set<String>> types, Vehicle vehicleObject, Person personObject) {
        super(id, time, type, x, y, person, vehicle, link, facility, types, vehicleObject, personObject);
    }
}