package MatsimModel;

import AbmModel.BoardingEvent;
import AbmModel.Person;
import AbmModel.Vehicle;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Implementation BoardingEvent concept that represents an acceptable BoardingEvent for Abm
 * A BoardingEvent is an event that prepare a mouvement
 * Mandatory concept implementation
 */
@Deprecated
public class MSBoardingEvent extends BoardingEvent {

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
    public MSBoardingEvent(int id, double time, String type, double x, double y, int person, int vehicle, int link, int facility, Map<String, Set<String>> types, Vehicle vehicleObject, Person personObject) {
        super(id, time, type, x, y, person, vehicle, link, facility, types, vehicleObject, personObject);
    }

    /**
     * default constructor
     */
    public MSBoardingEvent() {
        super(-1, 0.0, "", 0.0, 0.0, -1, -1, -1, -1, new HashMap<>(), null, null);
    }

    /**
     * copy constructor from MSBoardingEvent
     * @param msBoardingEvent Event
     */
    public MSBoardingEvent(MSBoardingEvent msBoardingEvent) {
        super(msBoardingEvent.getId(), msBoardingEvent.getTime(), msBoardingEvent.getType(), msBoardingEvent.getX(), msBoardingEvent.getY(), msBoardingEvent.getPerson(), msBoardingEvent.getVehicle(), msBoardingEvent.getLink(), msBoardingEvent.getFacility(), msBoardingEvent.getTypes(), msBoardingEvent.getVehicleObject(), msBoardingEvent.getPersonObject());
    }

    /**
     * copy constructor from MSLegEvent
     * @param msLegEvent Event
     */
    public MSBoardingEvent(MSLegEvent msLegEvent) {
        super(msLegEvent.getId(), msLegEvent.getTime(), msLegEvent.getType(), msLegEvent.getX(), msLegEvent.getY(), msLegEvent.getPerson(), msLegEvent.getVehicle(), msLegEvent.getLink(), msLegEvent.getFacility(), msLegEvent.getTypes(), msLegEvent.getVehicleObject(), null);
    }

    /**
     * copy constructor from MSEvent
     * @param msEvent Event
     */
    public MSBoardingEvent(MSEvent msEvent) {
        super(msEvent.getId(), msEvent.getTime(), msEvent.getType(), msEvent.getX(), msEvent.getY(), msEvent.getPerson(), msEvent.getVehicle(), msEvent.getLink(), msEvent.getFacility(), msEvent.getTypes(), null, null);
    }
}
