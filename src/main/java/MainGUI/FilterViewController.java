package MainGUI;

import UIAbmModel.UIScenario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * this class is the controller for the filter part of the application
 */
public class FilterViewController {

    /**
     * the pane where the sets of filters will be added
     */
    @FXML public Pane filterPane;

    /**
     * List of all the subControllers
     */
    private List<FiltersDetailsController> filtersDetailsControllers;

    /**
     * the super Controller
     */
    private Controller superController;

    //=====================================================
    //initialisation
    //=====================================================

    /**
     * Initialize function FXML function
     */
    @FXML
    public void initialize() {
        filtersDetailsControllers = new ArrayList<>();
    }

    //=====================================================
    //setters
    //=====================================================

    /**
     * setter for the super controller
     * @param superController the superController to call functions
     */
    public void setSuperController(Controller superController) {
        this.superController = superController;
    }

    /**
     * setter for the super controller
     * @param superControlleur the superController to call functions
     */
    @Deprecated
    public void setSuperControlleur(Controller superControlleur) {
        this.superController = superControlleur;
    }

    //=====================================================
    //functions
    //=====================================================

    /**
     * this function is used to add something in the filters tab
     * @param filterList the list of filters to add a new tab
     * @param name the name of the new tab
     * @throws IOException if the Loader couldn't load the file
     */
    @Deprecated
    public void addFilters(List<Filter> filterList, String name) throws IOException {
        FXMLLoader loader1 = new FXMLLoader(getClass().getClassLoader().getResource("views/filtresDetails.fxml"));
        Parent detail1 = loader1.load();
        filterPane.getChildren().add(detail1);
        FiltersDetailsController fController = loader1.getController();
        fController.setSuperController(this);
        this.filtersDetailsControllers.add(fController);
        fController.setFilters(filterList);
        fController.setName(name);
    }

    /**
     * this function is used to add something in the filters tab
     * @param filterList the list of filters to add a new tab
     * @param name the name of the new tab
     * @param isDefault true if it's default filters
     * @throws IOException if the Loader couldn't load the file
     */
    public void addFilters(List<Filter> filterList, String name, boolean isDefault) throws IOException {
        FXMLLoader loader1 = new FXMLLoader(getClass().getClassLoader().getResource("views/filtresDetails.fxml"));
        Parent detail1 = loader1.load();
        filterPane.getChildren().add(detail1);
        FiltersDetailsController fController = loader1.getController();
        fController.setSuperController(this);
        this.filtersDetailsControllers.add(fController);
        fController.setFilters(filterList);
        fController.setName(name);
        if(!isDefault) {
            fController.setRemovable();
        }
    }

    /**
     * this function is used to applied the filters on the scenario
     * @param scenario the scenario on which the filter will be applied
     */
    public void appliedFilters(UIScenario scenario) {
        for (FiltersDetailsController filtersDetailsController : this.filtersDetailsControllers) {
            filtersDetailsController.appliedFilters(scenario);
        }
    }

    /**
     * this function will be called by a subController when the checkBox Status will change
     */
    public void filtersChanged(){
        superController.appliedFilters();
    }

    /**
     * clear all filters, even the default ones (to load a new project)
     */
    public void resetFilters() {
        while(this.filtersDetailsControllers.size() != 0){
            this.filtersDetailsControllers.remove(0);
            this.filterPane.getChildren().remove(0);
        }
    }

    /**
     * function to remove all the filters
     */
    public void deleteAllFilters(){
        while(this.filtersDetailsControllers.size() != 1){
            this.filtersDetailsControllers.remove(1);
            this.filterPane.getChildren().remove(1);
        }
        this.filtersChanged();
    }

    /**
     * function to delete all the non checked Filters
     */
    public void deleteAllNonCheckedFilters() {
        for(int i = this.filtersDetailsControllers.size()-1; i>=1; i--) {
            if (!this.filtersDetailsControllers.get(i).cb.isSelected()) {
                this.filtersDetailsControllers.remove(1);
                this.filterPane.getChildren().remove(1);
            }
        }
        this.filtersChanged();
    }

    /**
     * function to close the one Filter view
     * @param event button pressed event
     */
    public void closeFilterDetails(ActionEvent event) {
        int i = this.filterPane.getChildren().indexOf(((MenuItem)event.getTarget()).getUserData());
        this.filtersDetailsControllers.remove(i);
        this.filterPane.getChildren().remove(i);
        this.filtersChanged();
    }

    /**
     * function to take down one filter view
     * @param event button pressed event
     */
    public void takeDownFilterDetails(ActionEvent event) {
        int i = this.filterPane.getChildren().indexOf(((MenuItem)event.getTarget()).getUserData());
        if (i != 0 && i < this.filtersDetailsControllers.size() - 1) {
            FiltersDetailsController fController1 = this.filtersDetailsControllers.get(i);
            Node fPane1 = this.filterPane.getChildren().get(i);
            FiltersDetailsController fController2 = this.filtersDetailsControllers.get(i + 1);
            Node fPane2 = this.filterPane.getChildren().get(i + 1);

            this.filterPane.getChildren().remove(i,i+2);
            this.filtersDetailsControllers.remove(i+1);
            this.filtersDetailsControllers.remove(i);

            this.filtersDetailsControllers.add(i, fController2);
            this.filterPane.getChildren().add(i, fPane2);
            this.filtersDetailsControllers.add(i + 1, fController1);
            this.filterPane.getChildren().add(i + 1, fPane1);
            this.filtersChanged();
        }
    }

    //=====================================================
    //FXML functions
    //=====================================================
}
