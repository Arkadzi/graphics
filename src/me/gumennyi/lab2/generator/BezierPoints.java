package me.gumennyi.lab2.generator;

import me.gumennyi.lab2.types.Point2D;
import me.gumennyi.lab2.types.Point3D;

import java.util.List;
import java.util.Random;
import java.util.stream.DoubleStream;

public class BezierPoints implements PointsGenerator {
    private final int r;
    private final int h;
    private final int hParts;
    private final int vParts;
    private final BezierFunction bezierFunction;
    private final Random random = new Random();
    private Point3D[][] point3DS;


    public BezierPoints(List<Point2D> sourcePoints, int r, int h, int hParts, int vParts) {
        this.r = r;
        this.h = h;
        this.hParts = hParts;
        this.vParts = vParts;
        bezierFunction = new BezierFunction(sourcePoints, 1. / vParts);
    }

    public BezierPoints(List<Point2D> sourcePoints, int r, int h) {
        this(sourcePoints, r, h, 100, 10);
    }

    @Override
    public Point3D[][] generatePoints() {
        if (point3DS == null) {
            double step = 1. / vParts;

            point3DS = DoubleStream.iterate(0, (v) -> v <= 1, (v) -> v + step)
                    .mapToObj(this::generatePointsLayer)
                    .toArray(Point3D[][]::new);
        }
        return point3DS;
    }

    private double getRad(double u) {
        return 2 * Math.PI * u;
    }

    private Point3D[] generatePointsLayer(double v) {
        double step = 1. / hParts;
        Point2D point2D = bezierFunction.calculateBezier(v);

        return DoubleStream.iterate(0, (u) -> u <= 1, (u) -> u + step)
                           .mapToObj(u -> getPoint(u, v, point2D))
                           .toArray(Point3D[]::new);
    }

    private Point3D getPoint(double u, double v, Point2D point2D) {
        double x = r * Math.cos(getRad(u)) * point2D.y;
        x += (random.nextDouble() / 10 * x * 2) - (random.nextDouble() / 10 * x * 2);
        double y = r * Math.sin(getRad(u)) * point2D.y;
        y += (random.nextDouble() / 10 * y * 2) - (random.nextDouble() / 10 * y * 2);
        double z = h * point2D.x;
        z += (random.nextDouble() / 10 * h * 2) - (random.nextDouble() / 10 * h * 2);
        return new Point3D(x, y, z);
    }


}
