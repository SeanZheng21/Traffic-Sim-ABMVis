package MatsimModel;

import AbmModel.Household;

public class MSHousehold extends Household {
    public MSHousehold(String type) {
        super(type, 0, 0);
    }
    public MSHousehold() {
        super("", 0, 0);
    }

}
