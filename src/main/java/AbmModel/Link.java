package AbmModel;

import java.util.Objects;
import java.util.Set;

/**
 * Abstract Link concept that represents an acceptable link for Abm
 * A link represents a path or a road between two nodes
 * Mandatory concept
 */
public abstract class Link implements MandatorySimModel {

    /**
     * Number of vehicule at the same time in the Link
     */
    protected double capacity;

    /**
     *  Number of lanes of the Link
     */
    protected double permlanes;

    /**
     *  maximum legal speed on the Link
     */
    protected double freespeed;

    /**
     *  Modes of travelling allowed on the link
     */
    protected String modes;

    /**
     * for the type COLLECTOR
     */
    protected String type;

    /**
     * Modes of travelling allowed on the link
     */
    protected Set<String> modesSet;

    /**
     * the unique id of a link
     */
    protected long id;

    /**
     * the source node's id
     */
    protected long from;

    /**
     * the source node's object
     */
    protected Node fromeObject;

    /**
     * the destination node's id
     */
    protected long to;

    /**
     * the destination node's object
     */
    protected Node toObject;

    /**
     * the length of a link,
     * can be greater than the distance between from and to
     */
    private double length;

    /**
     * all the types of Link
     */
    protected final Type types;

    /**
     * Constructor
     * @param capacity vehicle capacity of this link
     * @param permlanes number of lanes of this link
     * @param freespeed the free speed of this link
     * @param modes the transport mode of this link
     * @param type the types of this link of this link
     * @param modesSet the transport mode of this link
     * @param id the id of this link
     * @param from the id of the beginning node of this link
     * @param fromeObject the node object at the beginning of this link
     * @param to the id of the end node of this link
     * @param toObject the node object at the end of this link
     * @param length the length of this node
     * @param types the types of this link of this link
     */
    public Link(double capacity, double permlanes, double freespeed, String modes, String type, Set<String> modesSet, int id, int from, Node fromeObject, int to, Node toObject, double length, Type types) {
        this.capacity = capacity;
        this.permlanes = permlanes;
        this.freespeed = freespeed;
        this.modes = modes;
        this.type = type;
        this.modesSet = modesSet;
        this.id = id;
        this.from = from;
        this.fromeObject = fromeObject;
        this.to = to;
        this.toObject = toObject;
        this.length = length;
        this.types = types;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public double getPermlanes() {
        return permlanes;
    }

    public void setPermlanes(double permlanes) {
        this.permlanes = permlanes;
    }

    public double getFreespeed() {
        return freespeed;
    }

    public void setFreespeed(double freespeed) {
        this.freespeed = freespeed;
    }

    public String getModes() {
        return modes;
    }

    public void setModes(String modes) {
        this.modes = modes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<String> getModesSet() {
        return modesSet;
    }

    public void setModesSet(Set<String> modesSet) {
        this.modesSet = modesSet;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public Node getFromeObject() {
        return fromeObject;
    }

    public void setFromeObject(Node fromeObject) {
        this.fromeObject = fromeObject;
    }

    public long getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public Node getToObject() {
        return toObject;
    }

    public void setToObject(Node toObject) {
        this.toObject = toObject;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public Type getTypes() {
        return types;
    }

    @Override
    public String toString() {
        return "Link{" +
                "capacity=" + capacity +
                ", permlanes=" + permlanes +
                ", freespeed=" + freespeed +
                ", modes='" + modes + '\'' +
                ", type='" + type + '\'' +
                ", id=" + id +
                ", from=" + from +
                ", to=" + to +
                ", length=" + length +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return id == link.id;
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
