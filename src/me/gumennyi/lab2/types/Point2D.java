package me.gumennyi.lab2.types;

public class Point2D extends Point {
    public final double x;
    public final double y;

    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return (int) Math.round(x);
    }

    public int getY() {
        return (int) Math.round(y);
    }

    @Override
    public String toString() {
        return x +
                " " + y;
    }
}
