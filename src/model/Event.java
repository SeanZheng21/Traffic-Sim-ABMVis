package model;

public class Event implements MandatorySimModel {
    private double time;
    private String type;

    public Event(double time, String type) {
        this.time = time;
        this.type = type;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
