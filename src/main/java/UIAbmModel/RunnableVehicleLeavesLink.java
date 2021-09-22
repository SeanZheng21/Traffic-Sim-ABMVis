package UIAbmModel;

import AbmModel.VehicleLeavesLink;

/**
 * This represent the action: The vehicle leaves in the link
 */
public class RunnableVehicleLeavesLink extends RunnableTrafficEvent {

    /**
     * @param event the static event corresponding to this event
     * @param vehicle the UIVehicle corresponding to this event
     * @param link the UILink corresponding to this event
     */
    public RunnableVehicleLeavesLink(VehicleLeavesLink event, UIVehicle vehicle, UILink link) {
        super(event, vehicle, link);
    }

    /**
     * this function allows the vehicle to leave the link
     * @throws IllegalCallerException if the execute function has been called twice
     */
    @Override
    public void execute() {
        if (this.isExecuteCalled()) throw new IllegalCallerException();
        this.vehicle.leaveLink();
        this.link.removeVehicle(this.vehicle);
        for (UIPerson p: this.vehicle.getOnBoardPersons()) {
            this.link.removePerson(p);
            p.leaveLink();
        }
        this.changeExecuteCalled();
    }

    /**
     * this function allows the vehicle to enter the link and the person on the vehicle to enter the link
     * @throws IllegalCallerException if the undo function has been called twice or the execute function has never been called
     */
    @Override
    public void undo() {
        if (! this.isExecuteCalled()) throw new IllegalCallerException();
        this.vehicle.enterLink(link);
        this.link.addVehicle(this.vehicle);
        for (UIPerson p: this.vehicle.getOnBoardPersons()) {
            this.link.addPerson(p);
            p.enterLink(this.link);
        }
        this.changeExecuteCalled();
    }

}