package AbmModel;

import java.util.*;

/**
 * Abstract VehicleLeavesLink event concept that represents an acceptable VehicleLeavesLink event for Abm
 * This type of event represent a vehicle leaving a link
 * Mandatory concept
 */
public abstract class VehicleLeavesLink extends TrafficEvent implements MandatorySimModel {

    /**
     * Constructor
     * @param id the id of this event
     * @param time the time when the event append
     * @param type the type of this Event "actEvent"
     * @param x X event location
     * @param y Y event location
     * @param person person related to this event -1
     * @param vehicle vehicle related to this event
     * @param link link related to this event
     * @param facility facility related to this event -1
     * @param types type of the event not useful
     * @param vehicleObject the vehicle object related to this event
     * @param linkObject the link object related to this event
     */
    public VehicleLeavesLink(int id, double time, String type, double x, double y, int person, int vehicle, int link, int facility, Map<String, Set<String>> types, Vehicle vehicleObject, Link linkObject) {
        super(id, time, type, x, y, person, vehicle, link, facility, types, vehicleObject, linkObject);
    }

}