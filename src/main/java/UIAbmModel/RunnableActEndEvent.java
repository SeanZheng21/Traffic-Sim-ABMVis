package UIAbmModel;

import AbmModel.Event;

/**
 * This class represents the end of an activity
 */
public class RunnableActEndEvent extends RunnableActEvent {

    /**
     * Runnable Act end Event Constructor
     * @param event the related event in the static model
     * @param person the person who finished the activity
     * @param facility the facility in which the activity taking place
     */
    public RunnableActEndEvent(Event event, UIPerson person, UIFacility facility) {
        super(event, person, facility);
    }

    /**
     * this function allows the person to leave the facility
     * @throws IllegalCallerException if the execute function has been called twice
     */
    @Override
    public void execute() {
        if (this.isExecuteCalled()) throw new IllegalCallerException();
        if(this.facility != null) {
            this.facility.remove(this.person);
            this.person.leaveFacility();
        }
        this.changeExecuteCalled();
    }

    /**
     * this function allows the person to enter the facility
     * @throws IllegalCallerException if the undo function has been called twice or the execute function has never been called
     */
    @Override
    public void undo() {
        if (! this.isExecuteCalled()) throw new IllegalCallerException();
        if(this.facility != null) {
            this.person.enterFacility(this.facility);
            this.facility.addPerson(this.person);
        }
        this.changeExecuteCalled();
    }
}