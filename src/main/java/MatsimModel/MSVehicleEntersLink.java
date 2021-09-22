package MatsimModel;

import AbmModel.Link;
import AbmModel.Vehicle;
import AbmModel.VehicleEntersLink;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Implementation VehicleEntersLink event concept that represents an acceptable VehicleEntersLink event for Abm
 * This type of event represent a vehicle entering into a link
 * Mandatory concept implementation
 */
public class MSVehicleEntersLink extends VehicleEntersLink {

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
    public MSVehicleEntersLink(int id, double time, String type, double x, double y, int person, int vehicle, int link, int facility, Map<String, Set<String>> types, Vehicle vehicleObject, Link linkObject) {
        super(id, time, type, x, y, person, vehicle, link, facility, types, vehicleObject, linkObject);
    }
    /**
     * default constructor
     */
    public MSVehicleEntersLink() {
        super(-1, 0.0, "", 0.0, 0.0, -1, -1, -1, -1, new HashMap<>(), null, null);
    }

    /**
     * copy constructor from MSVehicleEntersLink
     * @param msVehicleEntersLink Event
     */
    public MSVehicleEntersLink(MSVehicleEntersLink msVehicleEntersLink) {
        super(msVehicleEntersLink.getId(), msVehicleEntersLink.getTime(), msVehicleEntersLink.getType(), msVehicleEntersLink.getX(), msVehicleEntersLink.getY(), msVehicleEntersLink.getPerson(), msVehicleEntersLink.getVehicle(), msVehicleEntersLink.getLink(), msVehicleEntersLink.getFacility(), msVehicleEntersLink.getTypes(), msVehicleEntersLink.getVehicleObject(), msVehicleEntersLink.getLinkObject());
    }

    /**
     * copy constructor from MSTrafficEvent
     * @param msTrafficEvent Event
     */
    public MSVehicleEntersLink(MSTrafficEvent msTrafficEvent) {
        super(msTrafficEvent.getId(), msTrafficEvent.getTime(), msTrafficEvent.getType(), msTrafficEvent.getX(), msTrafficEvent.getY(), msTrafficEvent.getPerson(), msTrafficEvent.getVehicle(), msTrafficEvent.getLink(), msTrafficEvent.getFacility(), msTrafficEvent.getTypes(), msTrafficEvent.getVehicleObject(), msTrafficEvent.getLinkObject());
    }

    /**
     * copy constructor from MSLegEvent
     * @param msLegEvent Event
     */
    public MSVehicleEntersLink(MSLegEvent msLegEvent) {
        super(msLegEvent.getId(), msLegEvent.getTime(), msLegEvent.getType(), msLegEvent.getX(), msLegEvent.getY(), msLegEvent.getPerson(), msLegEvent.getVehicle(), msLegEvent.getLink(), msLegEvent.getFacility(), msLegEvent.getTypes(), msLegEvent.getVehicleObject(), null);
    }

    /**
     * copy constructor from MSEvent
     * @param msEvent Event
     */
    public MSVehicleEntersLink(MSEvent msEvent) {
        super(msEvent.getId(), msEvent.getTime(), msEvent.getType(), msEvent.getX(), msEvent.getY(), msEvent.getPerson(), msEvent.getVehicle(), msEvent.getLink(), msEvent.getFacility(), msEvent.getTypes(), null, null);
    }
}
