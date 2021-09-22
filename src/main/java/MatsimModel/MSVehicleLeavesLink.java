package MatsimModel;

import AbmModel.Link;
import AbmModel.Vehicle;
import AbmModel.VehicleLeavesLink;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Implementation VehicleLeavesLink event concept that represents an acceptable VehicleLeavesLink event for Abm
 * This type of event represent a vehicle leaving a link
 * Mandatory concept implementation
 */
public class MSVehicleLeavesLink extends VehicleLeavesLink {

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
    public MSVehicleLeavesLink(int id, double time, String type, double x, double y, int person, int vehicle, int link, int facility, Map<String, Set<String>> types, Vehicle vehicleObject, Link linkObject) {
        super(id, time, type, x, y, person, vehicle, link, facility, types, vehicleObject, linkObject);
    }

    /**
     * default constructor
     */
    public MSVehicleLeavesLink() {
        super(-1, 0.0, "", 0.0, 0.0, -1, -1, -1, -1, new HashMap<>(), null, null);
    }

    /**
     * copy constructor from MSVehicleLeavesLink
     * @param msVehicleLeavesLink Event
     */
    public MSVehicleLeavesLink(MSVehicleLeavesLink msVehicleLeavesLink) {
        super(msVehicleLeavesLink.getId(), msVehicleLeavesLink.getTime(), msVehicleLeavesLink.getType(), msVehicleLeavesLink.getX(), msVehicleLeavesLink.getY(), msVehicleLeavesLink.getPerson(), msVehicleLeavesLink.getVehicle(), msVehicleLeavesLink.getLink(), msVehicleLeavesLink.getFacility(), msVehicleLeavesLink.getTypes(), msVehicleLeavesLink.getVehicleObject(), msVehicleLeavesLink.getLinkObject());
    }

    /**
     * copy constructor from MSTrafficEvent
     * @param msTrafficEvent Event
     */
    public MSVehicleLeavesLink(MSTrafficEvent msTrafficEvent) {
        super(msTrafficEvent.getId(), msTrafficEvent.getTime(), msTrafficEvent.getType(), msTrafficEvent.getX(), msTrafficEvent.getY(), msTrafficEvent.getPerson(), msTrafficEvent.getVehicle(), msTrafficEvent.getLink(), msTrafficEvent.getFacility(), msTrafficEvent.getTypes(), msTrafficEvent.getVehicleObject(), msTrafficEvent.getLinkObject());
    }

    /**
     * copy constructor from MSLegEvent
     * @param msLegEvent Event
     */
    public MSVehicleLeavesLink(MSLegEvent msLegEvent) {
        super(msLegEvent.getId(), msLegEvent.getTime(), msLegEvent.getType(), msLegEvent.getX(), msLegEvent.getY(), msLegEvent.getPerson(), msLegEvent.getVehicle(), msLegEvent.getLink(), msLegEvent.getFacility(), msLegEvent.getTypes(), msLegEvent.getVehicleObject(), null);
    }

    /**
     * copy constructor from MSEvent
     * @param msEvent Event
     */
    public MSVehicleLeavesLink(MSEvent msEvent) {
        super(msEvent.getId(), msEvent.getTime(), msEvent.getType(), msEvent.getX(), msEvent.getY(), msEvent.getPerson(), msEvent.getVehicle(), msEvent.getLink(), msEvent.getFacility(), msEvent.getTypes(), null, null);
    }
}
