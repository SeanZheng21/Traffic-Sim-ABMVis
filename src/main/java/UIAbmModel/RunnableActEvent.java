package UIAbmModel;

import AbmModel.Event;

/**
 * This class represent the Event which involved activity
 */
public abstract class RunnableActEvent extends RunnableEvent {

    /**
     * the involved person
     */
    protected UIPerson person;

    /**
     * the involved facility
     */
    protected UIFacility facility;

    /**
     * constructor for the Runnable Act event
     * @param event the related event in the static model
     * @param person the person who finished the activity
     * @param facility the facility in which the activity taking place
     */
    public RunnableActEvent(Event event, UIPerson person, UIFacility facility) {
        super(event);
        if (person == null) throw new IllegalArgumentException();
        if (facility != null && facility.getFacility().getId() != event.getFacility()) throw new IllegalArgumentException();
        if (person.getPerson().getId() != event.getPerson()) throw new IllegalArgumentException();
        this.person = person;
        this.facility = facility;
    }
}