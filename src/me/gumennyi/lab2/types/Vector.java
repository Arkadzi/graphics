package me.gumennyi.lab2.types;

public class Vector {
    public final double x;
    public final double y;
    public final double z;

    public Vector(Point3D a, Point3D b) {
        x = b.x - a.x;
        y = b.y - a.y;
        z = b.z - a.z;
    }

    private Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector crossProduct(Vector otherVector) {
        return new Vector(y * otherVector.z - z * otherVector.y,
                          z * otherVector.x - x * otherVector.z,
                          x * otherVector.y - y * otherVector.x);
    }

    public Vector normal(Vector otherVector) {
        Vector productVector = crossProduct(otherVector);
        return productVector.divide(productVector.length());
    }

    public Vector divide(double val) {
        return new Vector(x / val, y / val, z / val);
    }

    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }
}
