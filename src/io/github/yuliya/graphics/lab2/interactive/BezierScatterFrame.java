package io.github.yuliya.graphics.lab2.interactive;

import io.github.yuliya.graphics.lab2.generator.BezierPoints;
import io.github.yuliya.graphics.lab2.generator.PointsGenerator;
import io.github.yuliya.graphics.lab2.types.Point2D;

import java.util.Arrays;
import java.util.List;


public class BezierScatterFrame extends ScatterFrame {

    private final static List<Point2D> sourcePoints = Arrays.asList(new Point2D(1, 1),
                                                                    new Point2D(5, 7),
                                                                    new Point2D(7, 2));

    public BezierScatterFrame(String title) {
        super(title);
    }

    public static void main(String[] args) {
        new BezierScatterFrame("Bezier Scatter");
    }

    @Override
    protected PointsGenerator getPointsGenerator() {
        return new BezierPoints(sourcePoints, 3, 3, 100, 100);
    }
}
