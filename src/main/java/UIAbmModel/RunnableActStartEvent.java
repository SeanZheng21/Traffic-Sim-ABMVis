package UIAbmModel;

import AbmModel.ActStart;

/**
 * This class reprents the start of an activity
 */
public class RunnableActStartEvent extends RunnableActEvent {

    /**
     * constructor for the Runnable Act Start event
     * @param event the related event in the static model
     * @param person the person who finished the activity
     * @param facility the facility in which the activity taking place
     */
    public RunnableActStartEvent(ActStart event, UIPerson person, UIFacility facility) {
        super(event, person, facility);
    }

    /**
     * this function allows the person to enter the facility 
     * @throws IllegalCallerException if the execute function has been called twice
     */
    @Override
    public void execute() {
        if (this.isExecuteCalled()) throw new IllegalCallerException();
        if (this.facility != null) {
            this.person.enterFacility(this.facility);
            this.facility.addPerson(this.person);
        }
        this.changeExecuteCalled();
    }

    /**
     * this function allows the person to leave the facility
     * @throws IllegalCallerException if the undo function has been called twice or the execute function has never been called
     */
    @Override
    public void undo() {
        if (! this.isExecuteCalled()) throw new IllegalCallerException();
        if (this.facility != null) {
            this.person.leaveFacility();
            this.facility.remove(this.person);
        }
        this.changeExecuteCalled();
    }

}