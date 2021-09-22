package UIAbmModel;

import AbmModel.Node;
import MatsimModel.MSNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class UINodeTest {

    UINode n;

    @BeforeEach
    void setUp() {
        Node n2 = new MSNode(3, 1, 100, "test");
        this.n = new UINode(n2);
    }

    @Test
    void UINodeNull() {
        assertThrows(IllegalArgumentException.class, () -> new UINode(null));
    }

    @Test
    void getNode() {
        assertEquals(3, this.n.getNode().getId());
        assertEquals("test", this.n.getNode().getType());
    }

    @Test
    void setColor() {
        assertThrows(IllegalArgumentException.class, () -> this.n.setColor(null));
    }

    @Test
    void hashCodeTest(){
        Node n2 = new MSNode(3, 1, 100, "test");
        UINode node = new UINode(n2);
        assertEquals(this.n.hashCode(), node.hashCode());
        assertEquals(this.n,node);
    }
}