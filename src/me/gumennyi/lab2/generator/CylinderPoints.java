package me.gumennyi.lab2.generator;

import me.gumennyi.lab2.types.Point3D;

import java.util.stream.DoubleStream;

public class CylinderPoints implements PointsGenerator {

    private final int r;
    private final int h;
    private final int hParts;
    private final int vParts;

    public CylinderPoints(int r, int h, int hParts, int vParts) {
        this.r = r;
        this.h = h;
        this.hParts = hParts;
        this.vParts = vParts;
    }

    public CylinderPoints(int r, int h) {
        this.r = r;
        this.h = h;
        vParts = 10;
        hParts = 100;
    }

    @Override
    public Point3D[][] generatePoints() {
        double step = 1. / vParts;

        return DoubleStream.iterate(0, (v) -> v <= 1, (v) -> v + step)
                .mapToObj(this::generatePointsLayer)
                .toArray(Point3D[][]::new);
    }

//    private double getRad(double u) {
//        return 2 * Math.PI * u;
//    }

    private Point3D[] generatePointsLayer(double v) {
        double step = 1. / hParts;

        return DoubleStream.iterate(-1, y -> y <= 1, y -> y + step)
                .mapToObj(y -> getPoint(y, v))
                .toArray(Point3D[]::new);
//
//        return DoubleStream.iterate(0, (u) -> u <= 1, (u) -> u + step)
//                           .mapToObj(u -> getPoint(u, v))
//                           .toArray(Point3D[]::new);
    }

    private Point3D getPoint(double y, double v) {
        return new Point3D(r * y * y, r * y, h * v);
    }
}
