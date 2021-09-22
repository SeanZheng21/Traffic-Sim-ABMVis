package UIAbmModel;

import AbmModel.Event;
import AbmModel.Person;
import AbmModel.PersonEntersVehicle;
import AbmModel.Type;
import MainGUI.Controller;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.*;

/**
 * UI Person to be displayed in the canvas
 */
public class UIPerson implements UIMandatorySimModel, Drawable {

    /**
     * default Pixel Size divided by 2
     */
    private static int defaultSize = 5;

    /**
     * Person in the static model corresponding to this Person
     */
    private Person person;

    /**
     * The color to draw the person
     */
    private Color color;

    /**
     *
     */
    private double size;

    /**
     * true if this person needs to be displayed
     */
    private boolean isDisplayed;

    /**
     * X position of the person
     */
    private SimpleDoubleProperty x;

    /**
     * Y position of the person
     */
    private SimpleDoubleProperty y;

    /**
     * link where the person is
     */
    private SimpleObjectProperty<Optional<UILink>> linkLocation;

    /**
     * vehicle the person is in
     */
    private SimpleObjectProperty<Optional<UIVehicle>> vehicleOnBoard;

    /**
     * facility the person is in
     */
    private SimpleObjectProperty<Optional<UIFacility>> facilityLocation;

    /**
     * @param person the static person corresponding to the UIPerson
     */
    public UIPerson(Person person) {
        if (person == null) throw new IllegalArgumentException();
        this.person = person;
        this.color = Color.RED;
        this.size = 1;
        this.isDisplayed = true;
        this.x = new SimpleDoubleProperty(0);
        this.y = new SimpleDoubleProperty(0);
        this.linkLocation = new SimpleObjectProperty<>(Optional.empty());
        this.vehicleOnBoard = new SimpleObjectProperty<>(Optional.empty());
        this.facilityLocation = new SimpleObjectProperty<>(Optional.empty());
    }

    /**
     * get the X position
     * @return X position
     */
    public double getX() {
        return x.get();
    }

    /**
     * get the X Property
     * @return X Property
     */
    public SimpleDoubleProperty getXProperty() {
        return x;
    }

    /**
     * get the Y position
     * @return Y position
     */
    public double getY() {
        return y.get();
    }

    /**
     * get the Y Property
     * @return Y Property
     */
    public SimpleDoubleProperty getYProperty() {
        return y;
    }

    /**
     * Set the uiperson visible for the visualization
     */
    public void display() {
        isDisplayed = true;
    }

    /**
     * Set the uiperson hidden for the visualization
     */
    public void hide() {
        isDisplayed = false;
    }

    /**
     * this function computes the position of the person :
     * at the beginning it takes the position of the first event
     * at the end it takes the position of the last event
     * after being entered in a vehicle, it takes the position of this vehicle until the person leaves it
     * if there isn't any event, then the person is just hidden from the visualisation
     * the position is computed only if the person is displayed on the visualisation
     * @param time current time
     */
    public void calculatePosition(double time) {
        if(isDisplayed) {
            // The position is computed only if the person is displayed
            Event lastEvent = null;
            Event followingEvent = null;
            double lastEventDelta = Integer.MAX_VALUE;
            double followingEventDelta = Integer.MAX_VALUE;
            for(Event event : person.getRelatedEvents()) {
                if (0 <= time - event.getTime() && time - event.getTime() <= lastEventDelta) {
                    // get the latest event
                    if(lastEvent == null || lastEvent.getTime() != event.getTime() || lastEvent.getId() < event.getId()) {
                        // If several events occur at the same time, keep the one with the greater ID
                        lastEvent = event;
                        lastEventDelta = time - event.getTime();
                    }
                } else if (0 <= event.getTime() - time && event.getTime() - time < followingEventDelta) {
                    // get the nearest following event
                    if(followingEvent == null || followingEvent.getTime() != event.getTime() || followingEvent.getId() > event.getId()) {
                        // If several events occur at the same time, keep the one with the lowest ID
                        followingEvent = event;
                        followingEventDelta = event.getTime() - time;
                    }
                }
            }
            if(lastEvent instanceof PersonEntersVehicle) {
                // if the person is in a vehicule we compute its position instead
                if(vehicleOnBoard.get().isPresent()) {
                    vehicleOnBoard.get().get().calculatePosition(time);
                    x.set(vehicleOnBoard.get().get().getX());
                    y.set(vehicleOnBoard.get().get().getY());
                } else if (lastEvent.getTime() == time){
                    x.setValue(lastEvent.getX());
                    y.setValue(lastEvent.getY());
                } else {
                    isDisplayed = false;
                    throw new RuntimeException("The vehicle is not supposed to be empty");
                }
            } else if(lastEvent != null) {
                // if the current time isn't before the first event
                x.set(lastEvent.getX());
                y.set(lastEvent.getY());
            } else if(followingEvent != null) {
                // if the current time is before the first event
                x.set(followingEvent.getX());
                y.set(followingEvent.getY());
            } else {
                // the person isn't related to any event
                isDisplayed = false;
            }
        }
    }

    /**
     * this function is used when the person enter in a new Link
     * @param link the UILink in which the person enters
     */
    public void enterLink(UILink link) {
        if (link == null) throw new IllegalArgumentException();
        this.linkLocation.set(Optional.of(link));
    }

    /**
     * this function is used when the person leave the Link
     */
    public void leaveLink() {
        this.linkLocation.set(Optional.empty());
    }

    /**
     * this function is used when the person enter in a new vehicle
     * @param vehicle the UIVehicle in which the person enters
     */
    public void getInVehicle(UIVehicle vehicle) {
        if (vehicle == null) throw new IllegalArgumentException();
        this.vehicleOnBoard.set(Optional.of(vehicle));
    }

    /**
     * this function is used when the person leave the vehicle
     */
    public void getOutVehicle() {
        this.vehicleOnBoard.set(Optional.empty());
    }

    /**
     * this function is used when the person enter in a new facility
     * @param facility the UIFacility in which the person enters
     */
    public void enterFacility(UIFacility facility) {
        if (facility == null) throw new IllegalArgumentException();
        this.facilityLocation.set(Optional.of(facility));
    }

    /**
     * this function is used when the person leave the facility
     */
    public void leaveFacility() {
        this.facilityLocation.set(Optional.empty());
    }

    @Override
    public void draw(Pane p, Controller controller) {
        if(this.isDisplayed) {
            Rectangle r = new Rectangle(DrawingPositionCalculator.getX(this.x.get()) - (defaultSize * size),
                    DrawingPositionCalculator.getY(this.y.get()) - (defaultSize * size),
                    defaultSize * size * 2 + 1, defaultSize * size * 2 + 1);
            r.setFill(this.color);
            r.setOnMouseClicked(e -> controller.getObserverController().personClicked(e, this));
            p.getChildren().add(r);
        }
    }

    @Override
    public Type getType() {
        return this.person.getTypes();
    }

    /**
     * to know the link on which is the person
     * @return an optional with the link on which is the person
     */
    public Optional<UILink> getLinkLocation() {
        return linkLocation.get();
    }

    /**
     * to know the link property on which is the person
     * @return an optional with the link property on which is the person
     */
    public SimpleObjectProperty<Optional<UILink>> getLinkProperty() {
        return linkLocation;
    }

    /**
     * to know the vehicle on which is the person
     * @return an optional with the vehicle on which is the person
     */
    public Optional<UIVehicle> getVehicleOnBoard() {
        return vehicleOnBoard.get();
    }

    /**
     * to know the vehicle property on which is the person
     * @return an optional with the vehicle property on which is the person
     */
    public SimpleObjectProperty<Optional<UIVehicle>> getVehicleProperty() {
        return vehicleOnBoard;
    }

    /**
     * person getter
     * @return the static person
     */
    public Person getPerson() {
        return person;
    }

    /**
     * to know the facility in which is the person
     * @return an optional with the facility in which is the person
     */
    public Optional<UIFacility> getFacilityLocation() {
        return facilityLocation.get();
    }

    /**
     * to know the facility property in which is the person
     * @return an optional with the facility property in which is the person
     */
    public SimpleObjectProperty<Optional<UIFacility>> getFacilityProperty() {
        return facilityLocation;
    }

    /**
     * isDisplayed getter
     * @return isDisplayed
     */
    public boolean isDisplayed() {
        return isDisplayed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UIPerson uiPerson = (UIPerson) o;
        return person.equals(uiPerson.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person);
    }

    @Override
    public void setColor(Color color) {
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