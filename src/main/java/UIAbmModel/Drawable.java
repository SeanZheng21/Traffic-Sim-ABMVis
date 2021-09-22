package UIAbmModel;

import javafx.scene.layout.Pane;
import AbmModel.Type;
import MainGUI.Controller;
import javafx.scene.paint.Color;

/**
 * The Function related to the drawable Object
 */
public interface Drawable {

    /**
     * the function to allows the object to draw itself
     * @param p the pane on which draw
     * @param controller the mainController
     */
    void draw(Pane p, Controller controller);

    /**
     * getter for the type Object
     * @return the type Object
     */
    Type getType();

    /**
     * size setter
     * @param size setter to allow the changes of size
     */
    void setSize(double size);

    /**
     * display setter
     * @param displayed true if the object is not hidden
     */
    void setDisplayed(boolean displayed);

    /**
     * color setter
     * @param color the new color
     */
    void setColor(Color color);

}