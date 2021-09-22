package UIAbmModel;

import AbmModel.Event;

import java.util.*;

/**
 * this class allows run the static events
 */
public abstract class RunnableEvent implements UIMandatorySimModel {

    /**
     * This variable is true if the execute function has been called and not undo
     */
    private boolean executeCalled;

    /**
     * Event in the static model corresponding to this event
     */
    protected Event event;

    /**
     * @param event the static event corresponding to this event
     */
    public RunnableEvent(Event event) {
        if (event == null) throw new IllegalArgumentException();
        this.event = event;
        this.executeCalled = false;
    }

    /**
     * execution of the event
     */
    public abstract void execute();

    /**
     * Undo of the event
     */
    public abstract void undo();

    /**
     * the function to verify if the execute function has been called
     * @return true if the execute function has been called
     */
    public boolean isExecuteCalled() {
        return executeCalled;
    }

    /**
     * the function to change the execution state
     */
    public void changeExecuteCalled(){
        this.executeCalled = ! this.executeCalled;
    }

    /**
     * event getter
     * @return the getter for the static event
     */
    public Event getEvent() {
        return event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RunnableEvent that = (RunnableEvent) o;
        return event.equals(that.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(event);
    }
}