package AbmModel;

import java.util.ArrayList;
import java.util.List;

public abstract class Network implements MandatorySimModel {
    private List<Node> nodes;
    private List<Link> links;

    public Network() {
        nodes = new ArrayList<>();
        links = new ArrayList<>();
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    @Override
    public String toString() {
        return "Network{" +
                "nodes=" + nodes +
                ", links=" + links +
                '}';
    }
}
