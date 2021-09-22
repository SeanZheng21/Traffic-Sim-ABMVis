package UIAbmModel;

import AbmModel.PersonLeavesVehicle;

/**
 * This class represent one event which is the person leaves the vehicle
 */
public class RunnablePersonLeavesVehicle extends RunnableBoardingEvent {

    /**
     * @param event the static event corresponding to this event
     * @param vehicle the UIVehicle corresponding to this event
     * @param person the UIPerson corresponding to this event
     */
    public RunnablePersonLeavesVehicle(PersonLeavesVehicle event, UIVehicle vehicle, UIPerson person) {
        super(event, vehicle, person);
    }

    /**
     * this function allows the person to leave the vehicle
     * @throws IllegalCallerException if the execute function has been called twice
     */
    @Override
    public void execute() {
        if (this.isExecuteCalled()) throw new IllegalCallerException();
        person.getOutVehicle();
        this.vehicle.removePerson(this.person);
        this.changeExecuteCalled();
    }

    /**
     * this function allows the person to enter the vehicle
     * @throws IllegalCallerException if the undo function has been called twice or the execute function has never been called
     */
    @Override
    public void undo() {
        if (! this.isExecuteCalled()) throw new IllegalCallerException();
        person.getInVehicle(vehicle);
        this.vehicle.addPerson(this.person);
        this.changeExecuteCalled();
    }

}