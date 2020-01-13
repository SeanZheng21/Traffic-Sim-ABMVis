package model;

import parser.OptionalParser;

public abstract class Household implements OptionalSimModel {
    private String type;
    private double x, y;

    public Household(String type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
