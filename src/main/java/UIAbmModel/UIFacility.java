package UIAbmModel;

import AbmModel.Facility;
import AbmModel.Type;
import MainGUI.Controller;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.FXCollections;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.*;

/**
 * This class represents the facilities
 */
public class UIFacility implements UIOptionalSimModel, Drawable {

    /**
     * default Pixel Size divided by 2
     */
    private static int defaultSize = 5;

    /**
     * facility in the static model corresponding to this facility
     */
    private Facility facility;

    /**
     * The color to draw the facility
     */
    private Color color;

    /**
     *
     */
    private double size;

    /**
     * true if this facility needs to be displayed
     */
    private boolean isDisplayed;

    /**
     * all the person inside the facility
     */
    private SimpleSetProperty<UIPerson> insidePersons;

    /**
     * all the vehicle inside the facility
     */
    private SimpleSetProperty<UIVehicle> insideVehicles;

    /**
     * @param facility the static facility corresponding to the UIFacility
     */
    public UIFacility(Facility facility) {
        if (facility == null) throw new IllegalArgumentException();
        this.facility = facility;
        this.color = Color.BROWN;
        this.size = 1;
        this.isDisplayed = true;
        this.insidePersons = new SimpleSetProperty<>(FXCollections.observableSet(new HashSet<>()));
        this.insideVehicles = new SimpleSetProperty<>(FXCollections.observableSet(new HashSet<>()));
    }

    /**
     * @param person the UIPerson to  add in the facility
     */
    public void addPerson(UIPerson person) {
        if (person == null) throw new IllegalArgumentException();
        this.insidePersons.add(person);
    }

    /**
     * @param vehicle the UIVehicle to add in the facility
     */
    public void addVehicle(UIVehicle vehicle) {
        if (vehicle == null) throw new IllegalArgumentException();
        this.insideVehicles.add(vehicle);
    }

    /**
     * @param person the UIPerson to remove of the facility
     */
    public void remove(UIPerson person) {
        if (person == null) throw new IllegalArgumentException();
        this.insidePersons.remove(person);
    }

    /**
     * @param vehicle the UIVehicle to remove of the facility
     */
    public void removeVehicle(UIVehicle vehicle) {
        if (vehicle == null) throw new IllegalArgumentException();
        this.insideVehicles.remove(vehicle);
    }

    @Override
    public void draw(Pane p, Controller controller) {
        if(this.isDisplayed) {
            Rectangle r = new Rectangle(DrawingPositionCalculator.getX(this.facility.getX()) - (defaultSize * size),
                    DrawingPositionCalculator.getY(this.facility.getY()) - (defaultSize * size),
                    defaultSize * size * 2 + 1, defaultSize * size * 2 + 1);
            r.setFill(this.color);
            r.setOnMouseClicked(e -> controller.getObserverController().facilityClicked(e, this));
            p.getChildren().add(r);
        }
    }

    @Override
    public Type getType() {
        return this.facility.getTypes();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UIFacility that = (UIFacility) o;
        return facility.equals(that.facility);
    }

    @Override
    public int hashCode() {
        return Objects.hash(facility);
    }

    /**
     * facility getter
     * @return the static facility
     */
    public Facility getFacility() {
        return facility;
    }

    /**
     * set of all the persons inside the facility
     * @return set of all the persons inside the facility
     */
    public Set<UIPerson> getInsidePersons() {
        return insidePersons.get();
    }

    /**
     * set property of all the persons inside the facility
     * @return set property of all the persons inside the facility
     */
    public SimpleSetProperty<UIPerson> getInsidePersonsProperty() {
        return insidePersons;
    }

    /**
     * set of all the vehicles inside the facility
     * @return set of all the vehicles inside the facility
     */
    public Set<UIVehicle> getInsideVehicles() {
        return insideVehicles.get();
    }

    /**
     * set property of all the vehicles inside the facility
     * @return set property of all the vehicles inside the facility
     */
    public SimpleSetProperty<UIVehicle> getInsideVehiclesProperty() {
        return insideVehicles;
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
}