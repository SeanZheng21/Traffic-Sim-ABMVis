package AbmModel;

import java.util.*;


/**
 * Abstract network concept that represents an acceptable event for Abm
 * A network represents a network of nodes and links
 * Mandatory concept
 */
public abstract class Network implements MandatorySimModel {

    /**
     * name of the network if exists
     */
    protected String name;

    /**
     * type of the network if exists
     */
    protected String type;

    /**
     * Map of all nodes sorted by id
     */
    protected Map<Long, Node> nodes;

    /**
     * Map of all links sorted by id
     */
    protected Map<Long, Link> links;

    /**
     * all the types of Network
     */
    protected Type types;


    /**
     * Constructor
     * @param name the name of the network
     * @param type the types of the network
     * @param nodes the node in the network
     * @param links the links in the network
     * @param types the types of the network
     */
    public Network(String name, String type, Map<Long, Node> nodes, Map<Long, Link> links, Type types) {
        this.name = name;
        this.type = type;
        this.nodes = nodes;
        this.links = links;
        this.types = types;
    }

    /**
     * Constructor
     * @param name the name of the network
     * @param type the types of the network
     */
    public Network(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<Long, Node> getNodes() {
        return nodes;
    }

    public void setNodes(Map<Long, Node> nodes) {
        this.nodes = nodes;
    }

    public Type getTypes() {
        return types;
    }

    public void setNodesCollection(Collection<Node> nodes) {
        Map<Long, Node> resMap = new HashMap<>();
        for (Node n: nodes)
            resMap.put(n.id, n);
        this.nodes = resMap;
    }

    public Map<Long, Link> getLinks() {
        return links;
    }

    public void setLinks(Map<Long, Link> links) {
        this.links = links;
    }

    public void setLinksCollection(Collection<Link> links) {
        Map<Long, Link> resMap = new HashMap<>();
        for (Link n: links)
            resMap.put(n.id, n);
        this.links = resMap;
    }

    /**
     * Get a Node from his id
     * @param id The id of the Node
     * @return The Node if it exists or an optional empty if not
     */
    public Node getNode(long id) {
        return nodes.get(id);
    }

    /**
     * Get a Link from his id
     * @param id The id of the Link
     * @return The Link if it exists or an optional empty if not
     */
    public Link getLink(long id) {
        return links.get(id);
    }

    /**
     * Add a Node to the Map
     * @param node The Node to add
     */
    public void addNode(Node node) {
        nodes.put(node.getId(), node);
    }

    /**
     * Add a Link to the Map
     * @param link The link to add
     */
    public void addLink(Link link) {
        links.put(link.getId(), link);
    }

    @Override
    public String toString() {
        return "Network{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    @Override
    public void addTypeEntry(String type, String value) {}
}
