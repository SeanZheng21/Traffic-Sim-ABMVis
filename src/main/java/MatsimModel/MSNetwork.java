package MatsimModel;

import AbmModel.Network;

import java.util.*;

/**
 * Implementation network concept that represents an acceptable network for Matsim simulation results
 * A network represents a network of nodes and links
 * Mandatory concept implementation
 *
 * WHAT THE PROCESSOR SHOULD DO FOR THIS CLASS :
 * fill the nodes and links Maps with all the Node and Link objects in the scenario
 * addNode and addLink method already exist to do this easily
 */
public class MSNetwork extends Network {

    /**
     * Constructor
     * @param name the name of the network
     * @param type the types of the network
     */
    public MSNetwork(String name, String type) {
        super(name, type, new HashMap<>(), new HashMap<>(), new MSType());
        this.types.setInstance(this);
    }

    /**
     * default constructor
     */
    public MSNetwork() {
        super("", "", new HashMap<>(), new HashMap<>(), new MSType());
        this.types.setInstance(this);
    }
}
