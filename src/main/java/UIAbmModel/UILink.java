package UIAbmModel;

import AbmModel.Link;
import AbmModel.Node;
import AbmModel.Type;
import MainGUI.Controller;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.FXCollections;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


/**
 * UI Link to be displayed in the canvas
 */
public class UILink implements UIMandatorySimModel, Drawable {

    /**
     * Link in the static model corresponding to this Link
     */
    private Link link;

    /**
     * The color to draw the Link
     */
    private Color color;

    /**
     * true if this link needs to be displayed
     */
    private boolean isDisplayed;

    /**
     * the size of the object
     */
    private double size;

    /**
     * persons in vehicle on the link
     */
    private SimpleSetProperty<UIPerson> circulatingPersons;

    /**
     * vehicle on the link
     */
    private SimpleSetProperty<UIVehicle> circulatingVehicles;



    /**
     * @param link the static link corresponding to the UILink
     */
    public UILink(Link link) {
        if (link == null) throw new IllegalArgumentException();
        this.link = link;
        this.circulatingPersons = new SimpleSetProperty<>(FXCollections.observableSet(new HashSet<>()));
        this.circulatingVehicles = new SimpleSetProperty<>(FXCollections.observableSet(new HashSet<>()));
        this.isDisplayed = true;
        this.size = 1;
        this.color = Color.BLUE;
    }

    /**
     * this function allows to add a person present on the link
     * @param person the UIPerson to add in the link
     */
    public void addPerson(UIPerson person) {
        if (person == null) throw new IllegalArgumentException();
        this.circulatingPersons.add(person);
    }

    /**
     * this function allows to add a vehicle present on the link
     * @param vehicle the UIVehicle to add in the link
     */
    public void addVehicle(UIVehicle vehicle) {
        if (vehicle == null) throw new IllegalArgumentException();
        this.circulatingVehicles.add(vehicle);
    }

    /**
     * this function allows to remove a person present on the link
     * @param person the UIPerson to remove out of the link
     */
    public void removePerson(UIPerson person) {
        if (person == null) throw new IllegalArgumentException();
        this.circulatingPersons.remove(person);
    }

    /**
     * this function allows to add a vehicle present on the link
     * @param vehicle the UIVehicle to remove out of the link
     */
    public void removeVehicle(UIVehicle vehicle) {
        if (vehicle == null) throw new IllegalArgumentException();
        this.circulatingVehicles.remove(vehicle);
    }

    @Override
    public void setColor(Color color) {
        if (color == null) throw new IllegalArgumentException();
        this.color = color;
    }

    @Override
    public void setDisplayed(boolean displayed) {
        isDisplayed = displayed;
    }

    @Override
    public void setSize(double size) {
        this.size = size;
    }

    /**
     * getter
     * @return getter
     */
    public Link getLink() {
        return link;
    }

    @Override
    public void draw(Pane p, Controller controller) {
        if(this.isDisplayed) {
            Node from = this.link.getFromeObject();
            Node to = this.link.getToObject();
            Line l = new Line(DrawingPositionCalculator.getX(from.getX()), DrawingPositionCalculator.getY(from.getY()),
                    DrawingPositionCalculator.getX(to.getX()), DrawingPositionCalculator.getY(to.getY()));
            l.setStroke(this.color);
            l.setStrokeWidth(size);
            l.setOnMouseClicked(e -> controller.getObserverController().linkClicked(e, this));
            p.getChildren().add(l);
        }
    }

    @Override
    public Type getType() {
        return this.link.getTypes();
    }

    /**
     * getter
     * @return the circulating persons
     */
    public Set<UIPerson> getCirculatingPersons() {
        return circulatingPersons.get();
    }

    /**
     * getter
     * @return the circulating persons property
     */
    public SimpleSetProperty<UIPerson> getCirculatingPersonsProperty() {
        return circulatingPersons;
    }

    /**
     * getter
     * @return the circulating vehicles
     */
    public Set<UIVehicle> getCirculatingVehicles() {
        return circulatingVehicles.get();
    }

    /**
     * getter
     * @return the circulating vehicles property
     */
    public SimpleSetProperty<UIVehicle> getCirculatingVehiclesProperty() {
        return circulatingVehicles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UILink uiLink = (UILink) o;
        return link.equals(uiLink.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link);
    }
}
