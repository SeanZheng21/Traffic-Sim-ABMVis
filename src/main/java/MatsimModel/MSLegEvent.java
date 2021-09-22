package MatsimModel;

import AbmModel.LegEvent;
import AbmModel.Vehicle;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Implementation LegEvent concept that represents an acceptable LegEvent for Abm
 * A LegEvent is related to an event of movement
 * Mandatory concept implementation
 */
@Deprecated
public class MSLegEvent extends LegEvent {

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
    public MSLegEvent(int id, double time, String type, double x, double y, int person, int vehicle, int link, int facility, Map<String, Set<String>> types, Vehicle vehicleObject) {
        super(id, time, type, x, y, person, vehicle, link, facility, types, vehicleObject);
    }

    /**
     * default constructor
     */
    public MSLegEvent() {
        super(-1, 0.0, "", 0.0, 0.0, -1, -1, -1, -1, new HashMap<>(), null);
    }

    /**
     * copy constructor from MSLegEvent
     * @param msLegEvent Event
     */
    public MSLegEvent(MSLegEvent msLegEvent) {
        super(msLegEvent.getId(), msLegEvent.getTime(), msLegEvent.getType(), msLegEvent.getX(), msLegEvent.getY(), msLegEvent.getPerson(), msLegEvent.getVehicle(), msLegEvent.getLink(), msLegEvent.getFacility(), msLegEvent.getTypes(), msLegEvent.getVehicleObject());
    }

    /**
     * copy constructor from MSEvent
     * @param msEvent Event
     */
    public MSLegEvent(MSEvent msEvent) {
        super(msEvent.getId(), msEvent.getTime(), msEvent.getType(), msEvent.getX(), msEvent.getY(), msEvent.getPerson(), msEvent.getVehicle(), msEvent.getLink(), msEvent.getFacility(), msEvent.getTypes(), null);
    }
}
