package MainGUI;

import UIAbmModel.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.util.HashMap;

/**
 * this class is the controller for a one line Filter in the add Tab
 */
public class AddOneFilterController {

    /**
     * is displayed checkbox
     */
    @FXML public CheckBox checkbox;

    /**
     * the size of the new Element
     */
    @FXML public TextField size;

    /**
     * the size HBox
     */
    @FXML public HBox sizeBox;

    /**
     * the color Box
     */
    @FXML public HBox colorBox;

    /**
     * this choice box allows the user to choose which type to filter
     */
    @FXML private ChoiceBox<String> choiceBox;

    /**
     * this is the color picker to choose the color of the filtered object
     */
    @FXML private ColorPicker colorPicker;

    /**
     * index of this line in the super Controller
     */
    private int index;

    /**
     * SuperController to call some function
     */
    private AddFilterController superController;

    /**
     * Knowing the name of the class this map allows to found the class object for the Drawable Corresponding class
     */
    private final HashMap<String,Class<? extends Drawable>> translation = new HashMap<>();

    //=====================================================
    //initialisation
    //=====================================================

    /**
     * Initialize function FXML function
     */
    @FXML
    public void initialize() {
        this.choiceBox.setItems(AddFilterController.possibility);
        this.initializeTranslation();
        this.colorBox.visibleProperty().bind(this.checkbox.selectedProperty().not());
        this.sizeBox.visibleProperty().bind(this.checkbox.selectedProperty().not());
    }

    /**
     * Initialize the translation Map for example an MSVehicle is UIVehicle...
     */
    @Deprecated
    private void initializeTraduction() {
        this.translation.put("MSVehicle", UIVehicle.class);
        this.translation.put("MSPerson", UIPerson.class);
        this.translation.put("MSLink", UILink.class);
        this.translation.put("MSNode", UINode.class);
        this.translation.put("MSFacility", UIFacility.class);
    }

    /**
     * Initialize the translation Map for example an MSVehicle is UIVehicle...
     */
    private void initializeTranslation() {
        this.translation.put("MSVehicle", UIVehicle.class);
        this.translation.put("MSPerson", UIPerson.class);
        this.translation.put("MSLink", UILink.class);
        this.translation.put("MSNode", UINode.class);
        this.translation.put("MSFacility", UIFacility.class);
    }

    //=====================================================
    //setters
    //=====================================================

    /**
     * setter for the index
     * @param index the index in the superController List
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * setter for the super controller
     * @param superController the super Controller Object to call some functions
     */
    public void setSuperController(AddFilterController superController) {
        this.superController = superController;
    }

    //=====================================================
    //functions
    //=====================================================

    /**
     * this function allows to create Filter from the line information
     * if the choiceBox is empty it return null
     * @return a Filter or null if empty choiceBox
     */
    public Filter getFilter(){
        if (this.choiceBox.getSelectionModel().getSelectedItem() != null) {
            String[] attributes = this.choiceBox.getSelectionModel().getSelectedItem().split("/");
            Class<? extends Drawable> aClass = this.translation.get(attributes[0]);
            String type = attributes[1];
            String value = attributes[2];
            boolean isDisplayed = ! this.checkbox.isSelected();
            Color color = isDisplayed?this.colorPicker.getValue(): Color.WHITE;
            double sizeValue;
            try {
                sizeValue = Double.valueOf(this.size.getText());
            } catch (NumberFormatException e){
                sizeValue = 1;
            }
            return new Filter(aClass, type, value, color, sizeValue, isDisplayed);
        }
        return null;
    }



    //=====================================================
    //FXML functions
    //=====================================================

    /**
     * this function allows the line to removes itself
     */
    @FXML
    public void removeLine() {
        this.superController.removeLine(this.index);
    }
}
