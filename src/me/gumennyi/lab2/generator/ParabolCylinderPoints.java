package me.gumennyi.lab2.generator;

import me.gumennyi.lab2.types.Point3D;

import java.util.Random;
import java.util.stream.DoubleStream;

public class ParabolCylinderPoints implements PointsGenerator {

    public static final int DISTORTION_COEF = 20;
    private final int hParts;
    private final int vParts;
    private boolean randomize;
    private Random random = new Random();
    private Point3D[][] point3DS;

    public ParabolCylinderPoints(int grid, boolean randomize) {
        this.hParts = grid;
        this.vParts = grid;
        this.randomize = randomize;
        double step = 1./vParts;
        point3DS = DoubleStream.iterate(-1, (v) -> v <= 1, (v) -> v + step)
                .mapToObj(this::generatePointsLayer)
                .toArray(Point3D[][]::new);
    }

    @Override
    public Point3D[][] generatePoints(int scale) {
        Point3D[][] scaledPoints = new Point3D[this.point3DS.length][this.point3DS[0].length];
        for (int i = 0; i < point3DS.length; i++) {
            for (int j = 0; j < point3DS[0].length; j++) {
                Point3D point3D = point3DS[i][j];
                scaledPoints[i][j] = new Point3D(point3D.x * scale, point3D.y * scale, point3D.z * scale);
            }
        }
        return scaledPoints;
    }

    private Point3D[] generatePointsLayer(double v) {
        double step = 1. / hParts;

        return DoubleStream.iterate(-1, y -> y <= 1, y -> y + step)
                .mapToObj(y -> getPoint(y, v))
                .toArray(Point3D[]::new);
    }

    private Point3D getPoint(double y, double v) {
        double x = y * y + 0.5;
        if (randomize) {
            x += (random.nextDouble() / DISTORTION_COEF * x * 2) - (random.nextDouble() / DISTORTION_COEF * x * 2);
            y += (random.nextDouble() / DISTORTION_COEF * y * 2) - (random.nextDouble() / DISTORTION_COEF * y * 2);
        }
        return new Point3D(x, y, v);
    }
}
