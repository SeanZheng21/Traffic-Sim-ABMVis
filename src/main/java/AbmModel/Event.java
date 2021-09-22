package AbmModel;

import java.util.Map;
import java.util.Set;

/**
 * Abstract event concept that represents an acceptable event for Abm
 * An event is the smallest unit that can be represented in a simulation
 * Mandatory concept
 */
public abstract class Event implements MandatorySimModel {

    /**
     * static counter to set the event id if it isn't specified
     */
    protected static int counter;

    /**
     * the unique id of the event
     */
    protected int id;

    /**
     * the starting time of the event in double
     */
    protected double time;

    /**
     * the type string for the inheritance relation of the event
     */
    protected String type;

    /**
     * x coordinate of the location of the event
     */
    protected double x;

    /**
     * y coordinate of the location of the event
     */
    protected double y;

    /**
     * the id of the person concerned by the event if the actor is a person
     */
    protected int person;

    /**
     * the id of the vehicle concerned by the event if the actor is a vehicle
     */
    protected int vehicle;

    /**
     * the id of the link concerned by the event if the target is a link
     */
    protected int link;

    /**
     * the id of the facility concerned by the event if the target is a facility
     */
    protected int facility;

    /**
     * true if the event is valid for the visualization according to its timestamp and type compared to others
     * a invalid event doesn't mean that the event is inconsistent, it could be a global event,
     * but it means that the event can't be used for direct visualization purposes
     */
    protected boolean valid;

    /**
     * Relative position in the link for "vehicle enters traffic" and "vehicle leaves traffic" type events
     * In order to know the exact position of a traffic event (where the vehicle enters or leaves a link)
     */
    protected double relativePosition;

    /**
     * Map storing all the type of the event by category
     */
    protected Map<String, Set<String>> types;

    /**
     * Constructor
     * @param id the id of this event
     * @param time the time when the event append
     * @param type the type of this Event "actEvent"
     * @param x X event location
     * @param y Y event location
     * @param person person related to this event
     * @param vehicle vehicle related to this event
     * @param link link related to this event
     * @param facility facility related to this event
     * @param types type of the event not useful
     */
    public Event(int id, double time, String type, double x, double y, int person, int vehicle, int link, int facility, Map<String, Set<String>> types) {
        if(id == -1) {
            this.id = counter;
            counter++;
        } else {
            this.id = id;
            if(counter <= id) {
                counter = id + 1;
            }
        }
        this.time = time;
        this.type = type;
        this.x = x;
        this.y = y;
        this.person = person;
        this.vehicle = vehicle;
        this.link = link;
        this.facility = facility;
        this.types = types;
        this.valid = true;
        this.relativePosition = 0.0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getPerson() {
        return person;
    }

    public void setPerson(int person) {
        this.person = person;
    }

    public int getVehicle() {
        return vehicle;
    }

    public void setVehicle(int vehicle) {
        this.vehicle = vehicle;
    }

    public int getLink() {
        return link;
    }

    public void setLink(int link) {
        this.link = link;
    }

    public int getFacility() {
        return facility;
    }

    public void setFacility(int facility) {
        this.facility = facility;
    }

    public Map<String, Set<String>> getTypes() {
        return types;
    }

    public void setTypes(Map<String, Set<String>> types) {
        this.types = types;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public double getRelativePosition() {
        return relativePosition;
    }

    public void setRelativePosition(double relativePosition) {
        this.relativePosition = relativePosition;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", time=" + time +
                ", type='" + type + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", person=" + person +
                ", vehicle=" + vehicle +
                ", link=" + link +
                ", facility=" + facility +
                ", valid=" + valid +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id == event.id;
    }

    @Override
    public void addTypeEntry(String type, String value) {}

    /**
     * id counter is reset to 0
     */
    public static void resetID() {
        counter = 0;
    }

}
