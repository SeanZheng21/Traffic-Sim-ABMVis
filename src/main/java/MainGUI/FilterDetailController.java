package MainGUI;

import javafx.fxml.FXML;
import javafx.scene.control.Labeled;
import javafx.scene.shape.Shape;

import java.io.IOException;

/**
 * this class is the controller
 * to saw the details for one Filter in the left Pane
 */
public class FilterDetailController {

    /**
     *  this is the labeled Object where put the value of the filter
     */
    @FXML public Labeled value;

    /**
     *  this is the labeled Object where put the className of the filter
     */
    @FXML public Labeled aClass;

    /**
     * shape Object to show the color of the filter
     */
    @FXML public Shape shape;

    //=====================================================
    //initialisation
    //=====================================================
    //=====================================================
    //setters
    //=====================================================

    /**
     * function to set the filter
     * @param f the filter of this view
     */
    @Deprecated
    public void setF(Filter f) {
        this.shape.setFill(f.getColor());
        this.aClass.setText(f.getAClass().getSimpleName());
        this.value.setText(f.getValue());
    }

    /**
     * function to set the filter
     * @param f the filter of this view
     */
    public void displayF(Filter f) {
        this.shape.setFill(f.getColor());
        this.aClass.setText(f.getAClass().getSimpleName());
        this.value.setText(f.getValue());
    }

    //=====================================================
    //functions
    //=====================================================

    //=====================================================
    //FXML functions
    //=====================================================

}
