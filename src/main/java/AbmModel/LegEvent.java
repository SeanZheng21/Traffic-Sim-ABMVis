package AbmModel;

import java.util.*;

/**
 * Abstract LegEvent concept that represents an acceptable LegEvent for Abm
 * A LegEvent is related to an event of movement
 * Mandatory concept
 */
public abstract class LegEvent extends Event implements MandatorySimModel {

    /**
     * The vehicle object, actor or target of the event
     */
    protected Vehicle vehicleObject;

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
     */
    public LegEvent(int id, double time, String type, double x, double y, int person, int vehicle, int link, int facility, Map<String, Set<String>> types, Vehicle vehicleObject) {
        super(id, time, type, x, y, person, vehicle, link, facility, types);
        this.vehicleObject = vehicleObject;
    }

    public Vehicle getVehicleObject() {
        return vehicleObject;
    }

    public void setVehicleObject(Vehicle vehicleObject) {
        this.vehicleObject = vehicleObject;
    }
}