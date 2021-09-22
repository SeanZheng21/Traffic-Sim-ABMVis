package AbmModel;

import java.util.*;

/**
 * A relation is a set of link with a name to filter them in th GUI
 * for example a relation could be the Route 66 withe the name Route 66 and with all the links which compose this road
 */
public abstract class Relation implements OptionalSimModel {

    /**
     * the name of the relation
     */
    protected final String name;

    /**
     * Set of all the links in the relation
     */
    protected final Set<Link> links;

    /**
     * all the types of Relation
     */
    protected final Type types;

    /**
     * Default constructor
     * @param types type of the relation
     */
    public Relation(Type types) {
        this.name = "";
        this.links = new HashSet<>();
        this.types = types;
    }

    /**
     * Constructor
     * @param name names of the relation
     * @param types type of the relation
     */
    public Relation(String name, Type types) {
        this.name = name;
        this.links = new HashSet<>();
        this.types = types;
    }

    public String getName() {
        return name;
    }

    public Set<Link> getLinks() {
        return links;
    }

    /**
     * add links in the relation
     * @param l the link to add
     */
    public void addLink(Link l) {
        this.links.add(l);
    }

    public Type getTypes() {
        return types;
    }

    @Override
    public void addTypeEntry(String type, String value) {}
}