package AbmModel;

public abstract class Link implements MandatorySimModel {
    private int id, from, to;
    private double length;

    public Link(int id, int from, int to, double length) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.length = length;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "Link{" +
                "id=" + id +
                ", from=" + from +
                ", to=" + to +
                ", length=" + length +
                '}';
    }
}
