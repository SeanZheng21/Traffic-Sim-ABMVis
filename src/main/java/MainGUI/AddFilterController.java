package MainGUI;

import AbmModel.SimModel;
import AbmModel.TypeCollector;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * this class is the controller for the add Filter pane
 * It's purpose is to allows the user to add is own Filter
 */
public class AddFilterController {

    /**
     * This filed is the name for the filter
     */
    @FXML public TextField nameField;

    /**
     * this pane is where the lines should be added
     */
    @FXML private Pane linePane;

    /**
     * superController to call some function
     */
    private Controller superController;

    /**
     * list of all the possible value to filter on
     */
    public static SimpleListProperty<String> possibility;

    /**
     * list of all the lines subControllers
     */
    private List<AddOneFilterController> subControllers;


    //=====================================================
    //initialisation
    //=====================================================

    /**
     * Initialize this pane and the possible values
     * @throws IOException if the subFunction addFiltersChoice throws the exception
     */
    @FXML
    public void initialize() throws IOException {
        this.subControllers = new ArrayList<>();
        AddFilterController.possibility = new SimpleListProperty<>(FXCollections.observableArrayList());
        for (Class<? extends SimModel> c : TypeCollector.getClasses()) {
            for(String type : TypeCollector.getTypes(c)) {
                for(String value : TypeCollector.getValues(c, type)) {
                    String name = c.getSimpleName()+"/"+type+"/"+value;
                    possibility.add(name);
                }
            }
        }

        this.addFiltersChoice();

    }

    //=====================================================
    //setters
    //=====================================================

    /**
     * setter for the superController
     * @param superController the super Controller Object to call some functions
     */
    public void setSuperController(Controller superController) {
        this.superController = superController;
    }

    //=====================================================
    //functions
    //=====================================================

    /**
     * this function add another line to create an other filter
     * @throws IOException if the FXML loader cant load the file
     */
    public void addFiltersChoice() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/AddOneFilter.fxml"));
        Parent detail = loader.load();
        AddOneFilterController aController = loader.getController();
        aController.setIndex(this.subControllers.size());
        aController.setSuperController(this);
        this.subControllers.add(aController);
        linePane.getChildren().add(detail);
    }

    /**
     * this method is called by a subController to remove itself
     * @param i the index of the line to remove
     */
    public void removeLine(int i) {
        this.subControllers.remove(i);
        this.linePane.getChildren().remove(i);
        for (int j=0; j < this.subControllers.size(); j++) {
            this.subControllers.get(j).setIndex(j);
        }
    }


    //=====================================================
    //FXML functions
    //=====================================================

    /**
     * this function is called when the person approve he's filter it create the filters
     * and it close the Window
     * @param actionEvent the Action Event which call the function
     */
    @FXML
    public void submitButtonPressed(ActionEvent actionEvent) {
        List<Filter> filters = new ArrayList<>();
        for (AddOneFilterController ac : this.subControllers) {
            Filter f = ac.getFilter();
            if (f != null){
                filters.add(f);
            }
        }
        String name = this.nameField.getText();
        this.superController.addFilters(filters,name);
        final Node source = (Node) actionEvent.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

    }

    /**
     * action done when the user push the button to insert another line
     * @throws IOException if the sub function addFiltersChoice throws the exception
     */
    @FXML
    public void addFilterMenu() throws IOException {
        this.addFiltersChoice();
    }
}
