package io.github.yuliya.graphics.lab2.generator;

import io.github.yuliya.graphics.lab2.types.Point2D;
import io.github.yuliya.graphics.lab2.types.Point3D;

import java.util.ArrayList;
import java.util.List;

public class BezierPoints implements PointsGenerator {
    private final int r;
    private final int h;
    private final int hParts;
    private final int vParts;
    private final BezierFunction bezierFunction;

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
        double step = 1. / vParts;

        ArrayList<Point3D[]> points = new ArrayList<>();
        for (double v = 0; v <= 1; v += step) {
            Point3D[] pointsLayer = generatePointsLayer(v);
            points.add(pointsLayer);
        }
        return points.toArray(new Point3D[points.size()][]);
    }

    private double getRad(double u) {
        return 2 * Math.PI * u;
    }

    private Point3D[] generatePointsLayer(double v) {
        double step = 1. / hParts;
        Point2D point2D = bezierFunction.calculateBezier(v);

        ArrayList<Point3D> points = new ArrayList<>();
        for (double u = 0; u <= 1; u += step) {
            points.add(getPoint(u, v, point2D));
        }
        return points.toArray(new Point3D[points.size()]);
    }

    private Point3D getPoint(double u, double v, Point2D point2D) {
        return new Point3D(r * Math.cos(getRad(u)) * point2D.y, r * Math.sin(getRad(u)) * point2D.y, h * point2D.x);
    }


}
