package AbmModel;

import java.util.*;

/**
 * Abstract Vehicule concept that represents an acceptable vehicle for Abm
 * A vehicle is a transport
 * Mandatory concept
 */
public abstract class Vehicle implements MandatorySimModel {

    /**
     * the unique id of a vehicle
     */
    protected int id;

    /**
     * Related events of the vehicle
     */
    protected List<Event> relatedEvents;

    /**
     * all the types of the vehicle
     */
    protected final Type types;

    /**
     * Constructor
     * @param id id of the vehicle
     * @param relatedEvents List of events related to this vehicle
     * @param types the types of this vehicle
     */
    public Vehicle(int id, List<Event> relatedEvents, Type types) {
        this.id = id;
        this.relatedEvents = relatedEvents;
        this.types = types;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Event> getRelatedEvents() {
        return relatedEvents;
    }

    /**
     * function to add another related Event
     * @param evt the event to add
     */
    public void appendRelatedEvents(Event evt) {
        this.relatedEvents.add(evt);
    }

    public void setRelatedEvents(List<Event> relatedEvents) {
        this.relatedEvents = relatedEvents;
    }

    public Type getTypes() {
        return types;
    }

    /**
     * Get the nearest VehicleEntersLink event from the specified timestamp
     * @param time The timestamp
     * @return The nearest event, null if there isn't any event after the timestamp
     */
    public VehicleEntersLink getNearestLinkEntering(double time) {
        VehicleEntersLink nearestEvent = null;
        double delta = Integer.MAX_VALUE;
        for(Event e : relatedEvents) {
            if(e.isValid() && e instanceof VehicleEntersLink) {
                if(0 <= (e.getTime() - time) && (e.getTime() - time) < delta) {
                    nearestEvent = (VehicleEntersLink) e;
                    delta = e.getTime() - time;
                }
            }
        }
        return nearestEvent;
    }

    /**
     * Get the latest VehicleLeavesLink event from the specified timestamp
     * @param time The timestamp
     * @return The latest event, null if there isn't any event prior to the timestamp
     */
    public VehicleLeavesLink getLatestLinkLeaving(double time) {
        VehicleLeavesLink latestEvent = null;
        double delta = Integer.MAX_VALUE;
        for(Event e : relatedEvents) {
            if(e.isValid() && e instanceof VehicleLeavesLink) {
                if(0 <= (time - e.getTime()) && (time - e.getTime()) < delta) {
                    latestEvent = (VehicleLeavesLink) e;
                    delta = time - e.getTime();
                }
            }
        }
        return latestEvent;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return id == vehicle.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public void addTypeEntry(String type, String value) {
        types.addEntry(type, value);
    }
}