package UIAbmModel;

import AbmModel.LegEvent;

/**
 * This class represents the traffic events
 */
public abstract class RunnableTrafficEvent extends RunnableLegEvent {

    /**
     * the link on which the event need to be applied
     */
    UILink link;

    /**
     * 
     * @param event the static event corresponding to this event
     * @param vehicle the UIVehicle corresponding to this event
     * @param link the UILink corresponding to this event
     */
    public RunnableTrafficEvent(LegEvent event, UIVehicle vehicle, UILink link) {
        super(event, vehicle);
        if (link == null) throw new IllegalArgumentException();
        if (link.getLink().getId() != event.getLink()) throw new IllegalArgumentException();
        this.link = link;
    }
}