package io.github.yuliya.graphics.lab2.generator;

import io.github.yuliya.graphics.lab2.types.Point3D;

import java.util.function.Function;

public class PortionPoints implements PointsGenerator {
    private final Point3D[] line1;
    private final Point3D[] line2;
    private final int hParts;
    private final int vParts;

    public PortionPoints(Point3D[] line1, Point3D[] line2, int hParts, int vParts) {
        this.line1 = line1;
        this.line2 = line2;
        this.hParts = hParts;
        this.vParts = vParts;
    }

    @Override
    public Point3D[][] generatePoints() {
        Point3D[][] points = new Point3D[vParts + 1][hParts + 1];
        double uStep = 1. / vParts;
        double vStep = 1. / hParts;
        int i = 0;
        for (double u = 0; u <= 1 + 0.001; u += uStep, i++) {
            int j = 0;
            for (double v = 0; v <= 1 + 0.001; v += vStep, j++) {
                points[i][j] = new Point3D(
                        200 * fiCoord(u, v, point3D -> point3D.x),
                        200 * fiCoord(u, v, point3D -> point3D.y),
                        200 * fiCoord(u, v, point3D -> point3D.z));
            }
        }
        return points;
    }

    private double fiCoord(double u, double v, Function<Point3D, Double> coord) {
        return fiLine(new double[]{coord.apply(line1[0]), coord.apply(line1[1]), coord.apply(line1[2])}, v) * (1 - u)
                + fiLine(new double[]{coord.apply(line2[0]), coord.apply(line2[1]), coord.apply(line2[2])}, v) * u;
    }

//    Function<Point3D, Double> coord() {
//        ret
//    }

    private double fiLine(double[] coords, double v) {
        return (coords[0] * (1 - v) * (1 - v)) + (2 * coords[1] * (1 - v) * v) + (coords[2] * v * v);
    }
}
