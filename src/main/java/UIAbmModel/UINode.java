package UIAbmModel;

import AbmModel.Node;
import AbmModel.Type;
import MainGUI.Controller;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Objects;


/**
 * UI Node to be displayed in the canvas
 */
public class UINode implements UIMandatorySimModel, Drawable {

    /**
     * default Pixel Size divided by 2
     */
    private static int defaultSize = 1;

    /**
     * Node in the static model corresponding to this Node
     */
    private SimpleObjectProperty<Node> node;

    /**
     * The color to draw the Node
     */
    private Color color;

    /**
     *
     */
    private double size;

    /**
     * true if this Node needs to be displayed
     */
    private boolean isDisplayed;


    /**
     * Default color BLACK
     * @param node the static node corresponding to the UINode
     */
    public UINode(Node node) {
        if (node == null) throw new IllegalArgumentException();
        this.node = new SimpleObjectProperty<>(node);
        this.color = Color.BLACK;
        this.size = 1;
        this.isDisplayed = true;
    }

    @Override
    public void draw(Pane p, Controller controller) {
        if(this.isDisplayed) {
            Rectangle r = new Rectangle(DrawingPositionCalculator.getX(this.node.get().getX()) - (defaultSize * size),
                    DrawingPositionCalculator.getY(this.node.get().getY()) - (defaultSize * size),
                    defaultSize * size * 2 + 1, defaultSize * size * 2 + 1);
            r.setFill(this.color);
            r.setOnMouseClicked(e -> controller.getObserverController().nodeClicked(e, this));
            p.getChildren().add(r);
        }
    }

    @Override
    public Type getType() {
        return this.node.get().getTypes();
    }

    /**
     * getter
     * @return getter
     */
    public Node getNode() {
        return node.get();
    }

    /**
     * getter
     * @return getter
     */
    public SimpleObjectProperty<Node> getNodeProperty() {
        return node;
    }

    @Override
    public void setColor(Color color) {
        if (color == null) throw new IllegalArgumentException();
        this.color = color;
    }

    @Override
    public void setSize(double size) {
        this.size = size;
    }

    @Override
    public void setDisplayed(boolean displayed) {
        isDisplayed = displayed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UINode uiNode = (UINode) o;
        return node.get().equals(uiNode.node.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(node.get());
    }
}
