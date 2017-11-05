package io.github.yuliya.graphics.lab2.figures;

import io.github.yuliya.graphics.lab2.generator.BezierFunction;
import io.github.yuliya.graphics.lab2.graphics.Graphics;
import io.github.yuliya.graphics.lab2.types.Point2D;

import java.util.List;

public class BezierFlat implements Drawing {

    private final Graphics graphics;
    private final int radius;
    private final int height;
    private final BezierFunction bezierFunction;

    public BezierFlat(List<Point2D> sourcePoints, Graphics graphics, int vParts, int radius, int height) {
        this.graphics = graphics;
        this.radius = radius;
        this.height = height;
        bezierFunction = new BezierFunction(sourcePoints, 1. / vParts);
    }

    @Override
    public void draw() {
        drawFigure();
    }

    private void drawFigure() {
        Point2D[] bezierPoints = bezierFunction.calculateBezier();

        for (int i = 1; i < bezierPoints.length; i++) {
            graphics.line(scalePoint(bezierPoints[i - 1]), scalePoint(bezierPoints[i]));
        }
    }

    private Point2D scalePoint(Point2D point2D) {
        return new Point2D(point2D.x * height, point2D.y * radius);
    }
}
