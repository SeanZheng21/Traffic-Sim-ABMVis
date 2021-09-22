package AbmModel;

import java.util.Objects;

/**
 * Abstract Node concept that represents an acceptable node for Abm
 * A node represents a point in the network
 * Mandatory concept
 */
public abstract class Node implements MandatorySimModel {

    /**
     * the unique id of a node
     */
    protected long id;
    /**
     * the x coordinate of a node
     */
    protected double x;
    /**
     * the x coordinate of a node
     */
    protected double y;

    /**
     * the main type of a node
     */
    protected String type;

    /**
     * all the types of the node
     */
    protected final Type types;

    /**
     * Constructor
     * @param id id of the node
     * @param x x location
     * @param y y location
     * @param type types of the node
     * @param types types of the node
     */
    public Node(int id, double x, double y, String type, Type types) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.type = type;
        this.types = types;
    }

    public long getId() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Type getTypes() {
        return types;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return id == node.id;
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
