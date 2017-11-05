package io.github.yuliya.graphics.lab2.generator;

import io.github.yuliya.graphics.lab2.types.Point2D;

import java.util.ArrayList;
import java.util.List;

public class BezierFunction {

    private final List<Point2D> sourcePoints;
    private final double vStep;

    public BezierFunction(List<Point2D> sourcePoints, double vStep) {
        this.sourcePoints = sourcePoints;
        this.vStep = vStep;
    }

    public Point2D[] calculateBezier() {
        ArrayList<Point2D> points = new ArrayList<Point2D>();
        for (double v = 0; v <= 1; v += vStep) {
            Point2D point2D = calculateBezier(v);
            points.add(point2D);
        }
        return points.toArray(new Point2D[points.size()]);
    }

    public Point2D calculateBezier(double t) {
        double x = 0;
        double y = 0;

        int n = sourcePoints.size() - 1;
        for (int i = 0; i <= n; i++) {
            x += fact(n) / (fact(i) * fact(n - i)) * sourcePoints.get(i).getX() * Math.pow(t, i) * Math.pow(1 - t, n - i);
            y += fact(n) / (fact(i) * fact(n - i)) * sourcePoints.get(i).getY() * Math.pow(t, i) * Math.pow(1 - t, n - i);
        }
        return new Point2D(x, y);
    }

    private double fact(double arg) {
        if (arg < 0) throw new RuntimeException("negative argument.");
        if (arg == 0) return 1;

        double rezult = 1;
        for (int i = 1; i <= arg; i++)
            rezult *= i;
        return rezult;
    }
}
