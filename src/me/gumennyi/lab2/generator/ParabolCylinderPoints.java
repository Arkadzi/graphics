package me.gumennyi.lab2.generator;

import me.gumennyi.lab2.types.Point3D;

import java.util.Random;
import java.util.stream.DoubleStream;

public class ParabolCylinderPoints implements PointsGenerator {

    private final int r;
    private final int h;
    private final int hParts;
    private final int vParts;
    private Random random = new Random();

    public ParabolCylinderPoints(int r, int h, int hParts, int vParts) {
        this.r = r;
        this.h = h;
        this.hParts = hParts;
        this.vParts = vParts;
    }

    public ParabolCylinderPoints(int r, int h) {
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

    private Point3D[] generatePointsLayer(double v) {
        double step = 1. / hParts;

        return DoubleStream.iterate(-1, y -> y <= 1, y -> y + step)
                .mapToObj(y -> getPoint(y, v))
                .toArray(Point3D[]::new);
    }

    private Point3D getPoint(double y, double v) {
        double x = r * y * y + r / 2;
        double y1 = r * y;
        x += (random.nextDouble() / 10 * x * 2) - (random.nextDouble() / 10 * x * 2);
        y1 += (random.nextDouble() / 10 * y1 * 2) - (random.nextDouble() / 10 * y1 * 2);
        return new Point3D(x, y1, h * v);
    }
}
