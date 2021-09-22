package UIAbmModel;

import AbmModel.LegEvent;

/**
 * this class represent The leg events in the dynamic model
 */
public abstract class RunnableLegEvent extends RunnableEvent {

    /**
     * Vehicle on which the event need to be applied
     */
    UIVehicle vehicle;

    /**
     * 
     * @param event the static event corresponding to this event
     * @param vehicle the UIVehicle corresponding to this event
     */
    public RunnableLegEvent(LegEvent event, UIVehicle vehicle) {
        super(event);
        if (vehicle == null) throw new IllegalArgumentException();
        if (vehicle.getVehicle().getId() != event.getVehicle()) throw new IllegalArgumentException();
        this.vehicle = vehicle;
    }

}