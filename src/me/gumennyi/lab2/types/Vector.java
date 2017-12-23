package me.gumennyi.lab2.types;

import me.gumennyi.lab2.graphics.Utils;

public class Vector {
    public final double x;
    public final double y;
    public final double z;

    public Vector(Point3D a, Point3D b) {
        x = b.x - a.x;
        y = b.y - a.y;
        z = b.z - a.z;
    }

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector crossProduct(Vector otherVector) {
        return new Vector(y * otherVector.z - z * otherVector.y,
                          z * otherVector.x - x * otherVector.z,
                          x * otherVector.y - y * otherVector.x);
    }

    public double angle(Vector vector) {
        double v = dot(vector) / length() / vector.length();
        if (Math.abs(1 - v) < 0.00001) {
            v = 1;
        }
        return Math.toDegrees(Math.acos(v));
    }

    public double dot(Vector vector) {
        return x * vector.x + y * vector.y + z * vector.z;
    }

    public Vector normal(Vector otherVector) {
        Vector productVector = crossProduct(otherVector);
        return productVector.divide(productVector.length());
    }


    @Override
    public String toString() {
        return "Vector{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    public Vector divide(double val) {
        return new Vector(x / val, y / val, z / val);
    }

    public Vector mul(double val) {
        return new Vector(x * val, y * val, z * val);
    }

    public Vector add(Vector v) {
        return new Vector(v.x + x, v.y + y, v.z + z);
    }

    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public Vector substract(Vector v) {
        return new Vector(-v.x + x, -v.y + y, -v.z + z);
    }
}
