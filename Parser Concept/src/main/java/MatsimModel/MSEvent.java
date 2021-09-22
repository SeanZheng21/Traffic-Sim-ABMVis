package MatsimModel;


import AbmModel.Event;

public class MSEvent extends Event {
    public MSEvent(double time, String type) {
        super(time, type);
    }
    public MSEvent() {
        super(0.0, "");
    }
}
