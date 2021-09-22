package AbmModel;

import java.util.*;

/**
 * Abstract Facility concept that represents an acceptable Facility for Abm
 * A facility represents a point of interest in the network
 * Optional concept
 */
public abstract class Facility implements OptionalSimModel {

    /**
     * the unique id of the facility
     */
    protected int id;

    /**
     * the x coordinate of the facility
     */
    protected double x;

    /**
     * the y coordinate of the facility
     */
    protected double y;

    /**
     * the id of the link where the facility is located
     */
    protected int linkID;

    /**
     * all the types of the facility
     */
    protected final Type types;

    /**
     * Constructor
     * @param id id of th facility
     * @param x X location
     * @param y Y location
     * @param linkID id of the link where the facility is
     * @param types types of the facility
     */
    public Facility(int id, double x, double y, int linkID, Type types) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.linkID = linkID;
        this.types = types;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getLinkID() {
        return linkID;
    }
    
    public void setLinkID(int linkID) {
        this.linkID = linkID;
    }

    public Type getTypes() {
        return types;
    }

    @Override
    public String toString() {
        return "Facility{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", linkID=" + linkID +
                ", types=" + types +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Facility facility = (Facility) o;
        return id == facility.id;
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