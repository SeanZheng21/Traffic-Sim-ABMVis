package UIAbmModel;

import AbmModel.*;
import MainGUI.Controller;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.FXCollections;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * UI Vehicle to be displayed in the canvas
 */
public class UIVehicle implements UIMandatorySimModel, Drawable {

    /**
     * default Pixel Size divided by 2
     */
    private static int defaultSize = 7;

    /**
     * vehicle in the static model corresponding to this vehicle
     */
    private Vehicle vehicle;

    /**
     * The color to draw the vehicle
     */
    private Color color;

    /**
     *
     */
    private double size;

    /**
     * true if this vehicle needs to be displayed
     */
    private boolean isDisplayed;

    /**
     * X position of the vehicle
     */
    private SimpleDoubleProperty x;

    /**
     * Y position of the vehicle
     */
    private SimpleDoubleProperty y;

    /**
     * true if the vehicle is moving
     */
    private boolean isMoving;

    /**
     * Contains all the person on the vehicle
     */
    private SimpleSetProperty<UIPerson> onBoardPersons;

    /**
     * if the vehicle is moving it represents the Link where the vehicle is
     */
    private SimpleObjectProperty<Optional<UILink>> trafficLink;

    /**
     * if the vehicle is not moving it represents the Facility where the vehicle is
     */
    private SimpleObjectProperty<Optional<UIFacility>> restFacility;


    /**
     * @param vehicle the static vehicle corresponding to the UIVehicle
     */
    public UIVehicle(Vehicle vehicle) {
        if (vehicle == null) throw new IllegalArgumentException();
        this.vehicle = vehicle;
        this.color = Color.GREEN;
        this.size = 1;
        this.isDisplayed = true;
        this.x = new SimpleDoubleProperty(0);
        this.y = new SimpleDoubleProperty(0);
        this.isMoving = false;
        this.onBoardPersons = new SimpleSetProperty<>(FXCollections.observableSet(new HashSet<>()));
        this.trafficLink = new SimpleObjectProperty<>(Optional.empty());
        this.restFacility = new SimpleObjectProperty<>(Optional.empty());
    }

    /**
     * getter
     * @return getter
     */
    public double getX() {
        return x.get();
    }

    /**
     * getter
     * @return getter
     */
    public SimpleDoubleProperty getXProperty() {
        return x;
    }

    /**
     * getter
     * @return getter
     */
    public double getY() {
        return y.get();
    }

    /**
     * getter
     * @return getter
     */
    public SimpleDoubleProperty getYProperty() {
        return y;
    }

    /**
     * Set the uivehicle visible for the visualization
     */
    public void display() {
        isDisplayed = true;
    }

    /**
     * Set the uivehicle hidden for the visualization
     */
    public void hide() {
        isDisplayed = false;
    }

    /**
     * this function computes the position of the vehicle :
     * at the beginning it takes the position of the first event
     * at the end it takes the position of the last event
     * if there isn't any event, then the vehicle is just hidden from the visualisation
     * the position is computed only if the vehicle is displayed on the visualisation
     * @param time current time
     */
    public void calculatePosition(double time) {
        if(isDisplayed) {
            // The position is computed only if the vehicle is displayed
            Event lastEvent = null;
            Event followingEvent = null;
            double lastEventDelta = Integer.MAX_VALUE;
            double followingEventDelta = Integer.MAX_VALUE;
            for(Event event : vehicle.getRelatedEvents()) {
                if(0 <= time - event.getTime() && time - event.getTime() < lastEventDelta) {
                    // get the latest event
                    if(lastEvent == null || lastEvent.getTime() != event.getTime() || lastEvent.getId() < event.getId()) {
                        // If several events occur at the same time, keep the one with the greater ID
                        lastEvent = event;
                        lastEventDelta = time - event.getTime();
                    }
                } else if(0 <= event.getTime() - time && event.getTime() - time < followingEventDelta) {
                    // get the nearest following event
                    if(followingEvent == null || followingEvent.getTime() != event.getTime() || followingEvent.getId() > event.getId()) {
                        // If several events occur at the same time, keep the one with the lowest ID
                        followingEvent = event;
                        followingEventDelta = event.getTime() - time;
                    }
                }
            }
            if(lastEvent != null && followingEvent!= null) {
                if (lastEvent.getTime() == time){
                    x.setValue(lastEvent.getX());
                    y.setValue(lastEvent.getY());
                } else {
                    // if the current time is between two events
                    double ratio = lastEventDelta / (lastEventDelta + followingEventDelta);
                    x.set(lastEvent.getX() + ((followingEvent.getX() - lastEvent.getX()) * ratio));
                    y.set(lastEvent.getY() + ((followingEvent.getY() - lastEvent.getY()) * ratio));
                }
            } else if(lastEvent != null) {
                // if the current time is after the last event
                x.set(lastEvent.getX());
                y.set(lastEvent.getY());
            } else if(followingEvent != null) {
                // if the current time is before the first event
                x.set(followingEvent.getX());
                y.set(followingEvent.getY());
            } else {
                // the vehicle isn't related to any event
                isDisplayed = false;
            }
        }
    }

    /**
     * this function allows to add a person on the vehicle
     * @param person the UIPerson to add in the Vehicle
     */
    public void addPerson(UIPerson person) {
        if (person == null) throw new IllegalArgumentException();
        this.onBoardPersons.add(person);
    }

    /**
     * this function allows to remove a person on the vehicle
     * @param person the UIPerson to put out of the Vehicle
     */
    public void removePerson(UIPerson person) {
        if (person == null) throw new IllegalArgumentException();
        this.onBoardPersons.remove(person);
    }

    /**
     * this function is used when the vehicle enter in a new Link when it moves
     * @param link the UILink in which the car enters
     */
    public void enterLink(UILink link) {
        if (link == null) throw new IllegalArgumentException();
        this.trafficLink.set(Optional.of(link));
    }

    /**
     *
     */
    public void leaveLink() {
        this.trafficLink.set(Optional.empty());
    }

    /**
     * @param facility the UIFacility in which the car enters
     */
    public void enterFacility(UIFacility facility) {
        if (facility == null) throw new IllegalArgumentException();
        this.restFacility.set(Optional.of(facility));
    }

    /**
     *
     */
    public void leaveFacility() {
        this.restFacility.set(Optional.empty());
    }

    @Override
    public void draw(Pane p, Controller controller) {
        if(this.isDisplayed) {
            Rectangle r = new Rectangle(DrawingPositionCalculator.getX(this.x.get()) - (defaultSize * size),
                    DrawingPositionCalculator.getY(this.y.get()) - (defaultSize * size),
                    defaultSize * size * 2 + 1, defaultSize * size * 2 + 1);
            r.setFill(this.color);
            r.setOnMouseClicked(e -> controller.getObserverController().vehicleClicked(e, this));
            p.getChildren().add(r);
        }
    }

    @Override
    public Type getType() {
        return this.vehicle.getTypes();
    }

    /**
     * getter
     * @return getter
     */
    public Vehicle getVehicle() {
        return vehicle;
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

    /**
     * moving setter
     * @param moving true if moving
     */
    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    /**
     * getter
     * @return getter
     */
    public Optional<UILink> getTrafficLink() {
        return trafficLink.get();
    }

    /**
     * getter
     * @return getter
     */
    public SimpleObjectProperty<Optional<UILink>> getTrafficLinkProperty() {
        return trafficLink;
    }

    /**
     * getter
     * @return getter
     */
    public Optional<UIFacility> getRestFacility() {
        return restFacility.get();
    }

    /**
     * getter
     * @return getter
     */
    public SimpleObjectProperty<Optional<UIFacility>> getRestFacilityProperty() {
        return restFacility;
    }

    /**
     * getter
     * @return getter
     */
    public Set<UIPerson> getOnBoardPersons() {
        return onBoardPersons.get();
    }

    /**
     * getter
     * @return getter
     */
    public SimpleSetProperty<UIPerson> getOnBoardPersonsProperty() {
        return onBoardPersons;
    }

    /**
     * getter
     * @return getter
     */
    public boolean isDisplayed() {
        return isDisplayed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UIVehicle uiVehicle = (UIVehicle) o;
        return vehicle.equals(uiVehicle.vehicle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicle);
    }
}
