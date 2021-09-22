package MainGUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import AbmModel.Node;
import AbmModel.Type;
import UIAbmModel.UIFacility;
import UIAbmModel.UILink;
import UIAbmModel.UINode;
import UIAbmModel.UIPerson;
import UIAbmModel.UIVehicle;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

/**
 * Controller for the details display
 */
public class ObserverController {

    /**
     * The graphical node where observations are displayed.
     */
    @FXML
    private VBox observations;

    /**
     * The list of elements that are observed, to avoid observing the same several times.
     */
    private List<Object> observed_elements;

    /**
     * The Controller's initilize function.
     */
    @FXML
    public void initialize() {
        observed_elements = new ArrayList<>();
    }

    /**
     * handler for right click > Close on observations. Will remove the graphical component from observations and the associated element from observed_element.
     * @param event the event which calls the function
     */
    private void handlCloseObservation(ActionEvent event) {
        observations.getChildren().remove(((MenuItem) event.getTarget()).getUserData());
        observed_elements.remove(((TitledPane) ((MenuItem) event.getTarget()).getUserData()).getUserData());
    }
    
    /**
     * Type object to List[Label] converter. A produced Label is as follow : "TypeName : Value1, Value2, etc."
     * @param type The type object
     * @return Labels list
     */
    private List<Label> typesToLabels(Type type) {
        List<Label> result = new ArrayList<Label>();
        for(String key : type.getTypes()){
            Label label = new Label();
            label.setText(key.substring(0, 1).toUpperCase() + key.substring(1) + " : " + String.join(", ", type.getValues(key)));
            result.add(label);
        }
        return result;
    }

    /**
     * Called when user clicks on a UIFacility element. Inserts a graphical element in observations VBox to display relevant attributes. Inserts a reference to the element in observed_elements.
     * @param mouseEvent The mouse event trigger
     * @param facility The selected facility
     */
    public void facilityClicked(MouseEvent mouseEvent, UIFacility facility) {
        if(this.observed_elements.contains(facility)){
            return;
        } else {
            observed_elements.add(facility);
        }
        TitledPane titledPane = new TitledPane();
        titledPane.setUserData(facility);
        AnchorPane anchorPane = new AnchorPane();
        Label label_inside_persons = new Label();
        Label label_inside_vehicles = new Label();
        label_inside_persons.textProperty().bindBidirectional(facility.getInsidePersonsProperty(), new StringConverter<>() {
            @Override
            public String toString(ObservableSet<UIPerson> set_persons) {
                if (set_persons.isEmpty()) {
                    return "Aucune personne présente";
                }
                String res = "Personnes présentes : \n";
                for (UIPerson person : set_persons) {
                    res += " - Personne " + person.getPerson().getId() + "\n";
                }
                return res;
            }

            @Override
            public ObservableSet<UIPerson> fromString(String string) {

                return null;
            }
        });
        label_inside_vehicles.textProperty().bindBidirectional(facility.getInsideVehiclesProperty(), new StringConverter<>() {
            @Override
            public String toString(ObservableSet<UIVehicle> set_vehicles) {
                if (set_vehicles.isEmpty()) {
                    return "Aucun véhicule présent";
                }
                String res = "Véhicules présents : \n";
                for(UIVehicle vehicle : set_vehicles){
                    res += " - Véhicule "+vehicle.getVehicle().getId()+"\n";
                }
                return res;
            }

            @Override
            public ObservableSet<UIVehicle> fromString(String string) {

                return null;
            }
        });
        VBox vBox = new VBox();
        vBox.getChildren().addAll(typesToLabels(facility.getType()));
        vBox.getChildren().add(label_inside_persons);
        vBox.getChildren().add(label_inside_vehicles);
        anchorPane.getChildren().add(vBox);
        titledPane.setContent(anchorPane);
        titledPane.setText("Bâtiment "+facility.getFacility().getId());
        MenuItem close_menu_item = new MenuItem("Fermer");
        close_menu_item.setUserData(titledPane);
        titledPane.setContextMenu(new ContextMenu(close_menu_item));
        close_menu_item.setOnAction(event -> {
            this.handlCloseObservation(event);
            label_inside_vehicles.textProperty().unbindBidirectional(facility.getInsideVehiclesProperty());
            label_inside_persons.textProperty().unbindBidirectional(facility.getInsidePersonsProperty());
        });
        observations.getChildren().add(titledPane);
    }

    /**
     * Called when user clicks on a UILink element. Inserts a graphical element in observations VBox to display relevant attributes. Inserts a reference to the element in observed_elements.
     * @param mouseEvent The mouse event trigger
     * @param link The selected link
     */
    public void linkClicked(MouseEvent mouseEvent, UILink link) {
        if(this.observed_elements.contains(link)){
            return;
        } else {
            observed_elements.add(link);
        }
        TitledPane titledPane = new TitledPane();
        titledPane.setUserData(link);
        AnchorPane anchorPane = new AnchorPane();
        Label label_circulating_persons = new Label();
        Label label_circulating_vehicles = new Label();
        label_circulating_persons.textProperty().bindBidirectional(link.getCirculatingPersonsProperty(), new StringConverter<>() {
            @Override
            public String toString(ObservableSet<UIPerson> set_persons) {
                if (set_persons.isEmpty()) {
                    return "Aucune personne présente";
                }
                String res = "Personnes présentes : \n";
                for (UIPerson person : set_persons) {
                    res += " - Personne " + person.getPerson().getId() + "\n";
                }
                return res;
            }

            @Override
            public ObservableSet<UIPerson> fromString(String string) {

                return null;
            }
        });
        label_circulating_vehicles.textProperty().bindBidirectional(link.getCirculatingVehiclesProperty(), new StringConverter<>() {
            @Override
            public String toString(ObservableSet<UIVehicle> set_vehicles) {
                if (set_vehicles.isEmpty()) {
                    return "Aucun véhicule présent";
                }
                String res = "Véhicules présents : \n";
                for(UIVehicle vehicle : set_vehicles){
                    res += " - Véhicule "+vehicle.getVehicle().getId()+"\n";
                }
                return res;
            }

            @Override
            public ObservableSet<UIVehicle> fromString(String string) {

                return null;
            }
        });
        VBox vBox = new VBox();
        vBox.getChildren().addAll(typesToLabels(link.getType()));
        vBox.getChildren().add(label_circulating_persons);
        vBox.getChildren().add(label_circulating_vehicles);
        anchorPane.getChildren().add(vBox);
        titledPane.setContent(anchorPane);
        titledPane.setText("Lien "+link.getLink().getId());
        MenuItem close_menu_item = new MenuItem("Fermer");
        close_menu_item.setUserData(titledPane);
        titledPane.setContextMenu(new ContextMenu(close_menu_item));
        close_menu_item.setOnAction((event)-> {
            this.handlCloseObservation(event);
            label_circulating_vehicles.textProperty().unbindBidirectional(link.getCirculatingVehiclesProperty());
            label_circulating_persons.textProperty().unbindBidirectional(link.getCirculatingPersonsProperty());
        });
        observations.getChildren().add(titledPane);
    }

    /**
     * Called when user clicks on a UINode element. Inserts a graphical element in observations VBox to display relevant attributes. Inserts a reference to the element in observed_elements.
     * @param mouseEvent The mouse event trigger
     * @param node The selected node
     */
    public void nodeClicked(MouseEvent mouseEvent, UINode node) {
        if(this.observed_elements.contains(node)){
            return;
        } else {
            observed_elements.add(node);
        }
        TitledPane titledPane = new TitledPane();
        titledPane.setUserData(node);
        AnchorPane anchorPane = new AnchorPane();
        Label label_x = new Label();
        Label label_y = new Label();
        label_x.textProperty().bindBidirectional(node.getNodeProperty(), new StringConverter<>() {
            @Override
            public String toString(Node node) {
                return "x : " + node.getX();
            }

            @Override
            public Node fromString(String string) {

                return null;
            }
        });
        label_y.textProperty().bindBidirectional(node.getNodeProperty(), new StringConverter<>() {
            @Override
            public String toString(Node node) {
                return "y : " + node.getY();
            }

            @Override
            public Node fromString(String string) {

                return null;
            }
        });
        VBox vBox = new VBox();
        vBox.getChildren().addAll(typesToLabels(node.getType()));
        vBox.getChildren().add(label_x);
        vBox.getChildren().add(label_y);
        anchorPane.getChildren().add(vBox);
        titledPane.setContent(anchorPane);
        titledPane.setText("Noeud "+node.getNode().getId());
        MenuItem close_menu_item = new MenuItem("Fermer");
        close_menu_item.setUserData(titledPane);
        titledPane.setContextMenu(new ContextMenu(close_menu_item));
        close_menu_item.setOnAction(this::handlCloseObservation);
        observations.getChildren().add(titledPane);
    }

    /**
     * Called when user clicks on a UIPerson element. Inserts a graphical element in observations VBox to display relevant attributes. Inserts a reference to the element in observed_elements.
     * @param mouseEvent The mouse event trigger
     * @param person The selected person
     */
    public void personClicked(MouseEvent mouseEvent, UIPerson person) {
        if(this.observed_elements.contains(person)){
            return;
        } else {
            observed_elements.add(person);
        }
        TitledPane titledPane = new TitledPane();
        titledPane.setUserData(person);
        AnchorPane anchorPane = new AnchorPane();
        Label label_x = new Label();
        Label label_y = new Label();
        Label label_vehicle = new Label();
        Label label_facility = new Label();
        Label label_link = new Label();
        label_x.textProperty().bind(person.getXProperty().asString("x : %.2f"));
        label_y.textProperty().bind(person.getYProperty().asString("y : %.2f"));
        label_vehicle.textProperty().bindBidirectional(person.getVehicleProperty(), new StringConverter<>() {
            @Override
            public String toString(Optional<UIVehicle> opt_v) {
                if(opt_v.isPresent()){
                    return "Véhicule : "+opt_v.get().getVehicle().getId();
                } else {
                    return "Véhicule : aucun";
                }
            }

            @Override
            public Optional<UIVehicle> fromString(String string) {

                return Optional.empty();
            }
        });
        label_facility.textProperty().bindBidirectional(person.getFacilityProperty(), new StringConverter<>() {
            @Override
            public String toString(Optional<UIFacility> opt_f) {
                if (opt_f.isPresent()) {
                    return "Bâtiment : " + opt_f.get().getFacility().getId();
                } else {
                    return "Bâtiment : aucun";
                }
            }

            @Override
            public Optional<UIFacility> fromString(String string) {
                return Optional.empty();
            }
        });
        label_link.textProperty().bindBidirectional(person.getLinkProperty(), new StringConverter<>() {
            @Override
            public String toString(Optional<UILink> opt_l) {
                if (opt_l.isPresent()) {
                    return "Lien : " + opt_l.get().getLink().getId();
                } else {
                    return "Lien : aucun";
                }
            }

            @Override
            public Optional<UILink> fromString(String string) {
                return Optional.empty();
            }
        });
        VBox vBox = new VBox();
        vBox.getChildren().addAll(typesToLabels(person.getType()));
        vBox.getChildren().add(label_x);
        vBox.getChildren().add(label_y);
        vBox.getChildren().add(label_vehicle);
        vBox.getChildren().add(label_facility);
        vBox.getChildren().add(label_link);
        anchorPane.getChildren().add(vBox);

        titledPane.setContent(anchorPane);
        titledPane.setText("Personne "+person.getPerson().getId());
        MenuItem close_menu_item = new MenuItem("Fermer");
        close_menu_item.setUserData(titledPane);
        titledPane.setContextMenu(new ContextMenu(close_menu_item));
        close_menu_item.setOnAction(this::handlCloseObservation);
        observations.getChildren().add(titledPane);
    }

    /**
     * Called when user clicks on a UIVehicle element. Inserts a graphical element in observations VBox to display relevant attributes. Inserts a reference to the element in observed_elements.
     * @param mouseEvent The mouse event trigger
     * @param vehicle The selected vehicle
     */
    public void vehicleClicked(MouseEvent mouseEvent, UIVehicle vehicle) {
        if(this.observed_elements.contains(vehicle)){
            return;
        } else {
            observed_elements.add(vehicle);
        }
        TitledPane titledPane = new TitledPane();
        titledPane.setUserData(vehicle);
        AnchorPane anchorPane = new AnchorPane();
        Label label_x = new Label();
        Label label_y = new Label();
        Label label_facility = new Label();
        Label label_link = new Label();
        Label label_inside_persons = new Label();
        label_inside_persons.textProperty().bindBidirectional(vehicle.getOnBoardPersonsProperty(), new StringConverter<>() {
            @Override
            public String toString(ObservableSet<UIPerson> set_persons) {
                if (set_persons.isEmpty()) {
                    return "Aucune personne présente";
                }
                String res = "Personnes présentes : \n";
                for (UIPerson person : set_persons) {
                    res += " - Personne " + person.getPerson().getId() + "\n";
                }
                return res;
            }

            @Override
            public ObservableSet<UIPerson> fromString(String string) {

                return null;
            }
        });
        VBox vBox = new VBox();
        vBox.getChildren().addAll(typesToLabels(vehicle.getType()));
        label_x.textProperty().bind(vehicle.getXProperty().asString("x : %.2f"));
        label_y.textProperty().bind(vehicle.getYProperty().asString("y : %.2f"));
        label_facility.textProperty().bindBidirectional(vehicle.getRestFacilityProperty(), new StringConverter<>() {
            @Override
            public String toString(Optional<UIFacility> opt_f) {
                if(opt_f.isPresent()){
                    return "Bâtiment actuel : "+opt_f.get().getFacility().getId();
                } else {
                    return "Bâtiment actuel : aucun";
                }
            }

            @Override
            public Optional<UIFacility> fromString(String string) {
                return Optional.empty();
            }
        });
        label_link.textProperty().bindBidirectional(vehicle.getTrafficLinkProperty(), new StringConverter<>() {
            @Override
            public String toString(Optional<UILink> opt_l) {
                if (opt_l.isPresent()) {
                    return "Lien actuel : " + opt_l.get().getLink().getId();
                } else {
                    return "Lien actuel : aucun";
                }
            }

            @Override
            public Optional<UILink> fromString(String string) {
                return Optional.empty();
            }
        });
        vBox.getChildren().add(label_x);
        vBox.getChildren().add(label_y);
        vBox.getChildren().add(label_facility);
        vBox.getChildren().add(label_link);
        vBox.getChildren().add(label_inside_persons);
        anchorPane.getChildren().add(vBox);

        titledPane.setContent(anchorPane);
        titledPane.setText("Véhicule "+vehicle.getVehicle().getId());
        MenuItem close_menu_item = new MenuItem("Fermer");
        close_menu_item.setUserData(titledPane);
        titledPane.setContextMenu(new ContextMenu(close_menu_item));
        close_menu_item.setOnAction(event -> {
            this.handlCloseObservation(event);
            label_inside_persons.textProperty().unbindBidirectional(vehicle.getOnBoardPersonsProperty());
        });
        observations.getChildren().add(titledPane);
    }
}