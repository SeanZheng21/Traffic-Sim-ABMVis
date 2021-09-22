package MatsimModel;

import AbmModel.Link;
import AbmModel.Node;

import java.util.HashSet;
import java.util.Set;


/**
 * Implementation link concept that represents an acceptable link for Matsim simulation results
 * A link represents a path or a road between two nodes
 * Mandatory concept implementation
 *
 * WHAT THE PROCESSOR SHOULD DO FOR THIS CLASS :
 * toObject and fromObject attributes should be linked to their actual Node object
 * according to the id specified in to and from attributes
 */
public class MSLink extends Link {

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
     */
    public MSLink(double capacity, double permlanes, double freespeed, String modes, String type, Set<String> modesSet, int id, int from, Node fromeObject, int to, Node toObject, double length) {
        super(capacity, permlanes, freespeed, modes, type, modesSet, id, from, fromeObject, to, toObject, length, new MSType());
        this.types.setInstance(this);
    }

    /**
     * dafault constructor
     */
    public MSLink() {
        super(0.0, 0.0, 0.0, "", "", new HashSet<>(), -1, -1, null, -1, null, 0.0, new MSType());
        this.types.setInstance(this);
    }
}
