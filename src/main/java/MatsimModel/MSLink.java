package MatsimModel;


import AbmModel.Link;

public class MSLink extends Link {
    public MSLink(int id, int from, int to, double length) {
        super(id, from, to, length);
    }
    public MSLink() {
        super(0, 0, 0, 0.0);
    }
}
