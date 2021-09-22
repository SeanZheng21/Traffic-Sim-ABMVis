package MainGUI;

import UIAbmModel.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * this class is the controller for a named set of Filter
 */
public class FiltersDetailsController {

    /**
     * Titled Where the name will be displayed
     */
    @FXML public Labeled titledLabeled;

    /**
     * the super Controller
     */
    private FilterViewController superController;

    /**
     * Pane where the information of the filters will be displayed
     */
    @FXML public Pane paneFilter;

    /**
     * the checkbox checked if the filters are used
     */
    @FXML public CheckBox cb;

    /**
     * filtersList of all the filters in this pane
     */
    private List<Filter> filterList;

    //=====================================================
    //initialisation
    //=====================================================

    /**
     * Initialize function FXML function
     */
    @FXML
    public void initialize() {
        this.cb.setVisible(false);
    }

    //=====================================================
    //setters
    //=====================================================

    /**
     * setter for the super controller
     * @param superController the superController to call functions
     */
    public void setSuperController(FilterViewController superController) {
        this.superController = superController;
    }

    /**
     * add menu item to the pane when right clicked
     */
    public void setRemovable(){
        this.cb.setVisible(true);
        ContextMenu menu = new ContextMenu();
        MenuItem close_menu_item = new MenuItem("Remove");
        close_menu_item.setUserData(titledLabeled);
        close_menu_item.setOnAction(actionEvent -> this.superController.closeFilterDetails(actionEvent));
        menu.getItems().add(close_menu_item);

        MenuItem take_down_menu_item = new MenuItem("Take Down");
        take_down_menu_item.setUserData(titledLabeled);
        take_down_menu_item.setOnAction(actionEvent -> this.superController.takeDownFilterDetails(actionEvent));
        menu.getItems().add(take_down_menu_item);

        titledLabeled.setContextMenu(menu);
    }

    /**
     * function to change the name of the titled pane
     * @param name the name of this pane
     */
    public void setName(String name){
        titledLabeled.setText(name);
    }

    /**
     * setter for the filtersList of filters
     * @param filtersList the filters list for this window
     * @throws IOException if the addFilter function throws an exception
     */
    public void setFilters(List<Filter> filtersList) throws IOException {
        this.filterList = filtersList;
        for (Filter f: this.filterList){
            this.addFilter(f);
        }
    }

    //=====================================================
    //functions
    //=====================================================

    /**
     * function to add the information for one filter
     * @param f the filter to add
     * @throws IOException if the Loader couldn't load the file
     */
    @Deprecated
    private void addFiltre(Filter f) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/filtreDetail.fxml"));
        Parent detail = loader.load();
        FilterDetailController fController = loader.getController();
        fController.setF(f);
        f.activeProperty().bindBidirectional(this.cb.selectedProperty());
        this.cb.selectedProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                     if(oldValue ^ newValue) {
                         superController.filtersChanged();
                     }
                }
        );
        paneFilter.getChildren().add(detail);
    }

    /**
     * function to add the information for one filter
     * @param f the filter to add
     * @throws IOException if the Loader couldn't load the file
     */
    private void addFilter(Filter f) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/filtreDetail.fxml"));
        Parent detail = loader.load();
        FilterDetailController fController = loader.getController();
        fController.displayF(f);
        f.activeProperty().bindBidirectional(this.cb.selectedProperty());
        this.cb.selectedProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                    if(oldValue ^ newValue) {
                        superController.filtersChanged();
                    }
                }
        );
        paneFilter.getChildren().add(detail);
    }

    /**
     * function to applied all the filters
     * @param scenario the scenario on which the filter will be applied
     */
    public void appliedFilters(UIScenario scenario) {
        for (Filter f: this.filterList) {
            for(UILink link: scenario.getUilinks()){
                f.applied(link);
            }
            for(UIFacility facility: scenario.getUifacilities()){
                f.applied(facility);
            }
            for(UIVehicle vehicle: scenario.getUivehicles()){
                f.applied(vehicle);
            }
            for(UIPerson person: scenario.getUipersons()){
                f.applied(person);
            }
            for(UINode node: scenario.getUinodes()){
                f.applied(node);
            }
        }
    }

    //=====================================================
    //FXML functions
    //=====================================================
}
