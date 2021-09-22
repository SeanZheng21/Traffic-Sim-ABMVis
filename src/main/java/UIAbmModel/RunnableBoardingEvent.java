package UIAbmModel;

import AbmModel.LegEvent;

/**
 * This class represents the boarding events
 */
public abstract class RunnableBoardingEvent extends RunnableLegEvent {

    /**
     * person who interact the vehicle
     */
    UIPerson person;

    /**
     *
     * @param event the static Event
     * @param vehicle the UIVehicle corresponding to this event
     * @param person the UIPerson corresponding to this event
     */
    public RunnableBoardingEvent(LegEvent event, UIVehicle vehicle, UIPerson person) {
        super(event, vehicle);
        if (person == null) throw new IllegalArgumentException();
        if (person.getPerson().getId() != event.getPerson()) throw new IllegalArgumentException();
        this.person = person;
    }
}