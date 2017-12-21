package me.gumennyi.lab2.draw;

import me.gumennyi.lab2.generator.PointsGenerator;
import me.gumennyi.lab2.graphics.Graphics;
import me.gumennyi.lab2.graphics.IsometricTransformer;
import me.gumennyi.lab2.graphics.Utils;
import me.gumennyi.lab2.types.Point2D;
import me.gumennyi.lab2.types.Point3D;
import me.gumennyi.lab2.types.Polygon;
import me.gumennyi.lab2.types.Vector;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WireframeDrawing implements Drawing {

    private static final int AXIS_LENGTH = 600;
    private static final int NORMAL_LENGTH = 40;
    private final PointsGenerator pointsGenerator;
    private final me.gumennyi.lab2.graphics.Graphics graphics;
    private final IsometricTransformer isometricTransformer;
    private final boolean showNormal;
    private final Vector cameraVector;
    private final Vector lightVector;
    private BufferedImage image;
    private boolean isContinuous;
    private boolean reverse;
    private Color lineColor;
    private double factor;

    public WireframeDrawing(Graphics graphics, PointsGenerator pointsGenerator, int fiAngle, int thetaAngle, boolean showNormal, boolean isContinuous, boolean reverse, Color lineColor, int lightShiftFi, int lightShiftTheta, double factor) {
        try {
            image = ImageIO.read(new File("Jupiter.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.graphics = graphics;
        this.pointsGenerator = pointsGenerator;
        this.isContinuous = isContinuous;
        this.reverse = reverse;
        this.lineColor = lineColor;
        this.factor = factor;
        this.isometricTransformer = new IsometricTransformer(fiAngle, thetaAngle);
        cameraVector = getCameraPoint(fiAngle, thetaAngle);
        lightVector = getCameraPoint(fiAngle - lightShiftFi, thetaAngle - lightShiftTheta);


//        System.out.println(fiAngle + " " + thetaAngle + " " + cameraVector);
        this.showNormal = showNormal;
    }


    private Vector getCameraPoint(int fiAngle, int thetaAngle) {
        int s = 1;
        double fiDeg = Utils.degToRad(fiAngle);
        double thetaDeg = Utils.degToRad(180 - thetaAngle % 180);
        double x = s * Math.sin(fiDeg) * Math.sin(thetaDeg);
        double y = s * Math.cos(fiDeg) * Math.sin(thetaDeg);
        Point3D point3D = new Point3D(
                x,
                y,
                s * Math.cos(thetaDeg)
        );

        Vector vector = new Vector(new Point3D(0, 0, 0), point3D);
//        System.out.println(vector);
        return vector;
    }


    @Override
    public void draw() {
        drawAxis();
        drawFigure(pointsGenerator, reverse);
    }

    private void drawFigure(PointsGenerator pointsGenerator, boolean reverse) {
        Point3D[][] cylinderPoints = pointsGenerator.generatePoints();
        Polygon<Point3D>[] convert = Utils.convert(cylinderPoints, reverse, isContinuous);
        Vector v = cameraVector;

        Vector l = lightVector.divide(lightVector.length());
        Point3D orig = new Point3D(-500 * lightVector.x, -500 * lightVector.y, -500 * lightVector.z);
        Point3D destination = new Point3D(0, 0, 0);
        Vector vector = new Vector(orig, destination);
        vector = vector.divide(vector.length());
        Point3D intersect = null;
        for (Polygon<Point3D> polygon : convert) {
            Point2D a = isometricTransformer.transform(polygon.getA());
            Point2D b = isometricTransformer.transform(polygon.getB());
            Point2D c = isometricTransformer.transform(polygon.getC());

            Vector normal = Utils.getNormal(polygon);
            double angle = normal.angle(cameraVector);

            if (angle < 90) {
                double lambert = Math.max(normal.dot(lightVector) + factor, 0) / (1 + factor);
                Vector r = normal.divide(0.5).divide(1 / normal.dot(v)).substract(v);

                double diffCoef = Math.max(normal.dot(l), 0);
                double specCoef = Math.pow(Math.max(l.dot(r), 0), 10);


                Polygon<Point2D> polygon2D = new Polygon<>(a, b, c, polygon.getvIndex(), polygon.gethIndex(), polygon.getvTotal(), polygon.gethTotal(), polygon.isTop());

                Point3D intersect1 = null;
                if (normal.angle(vector) < 90) {
                    intersect1 = Utils.intersect(orig, vector, polygon);
                    if (intersect1 != null) {
                        System.out.println(intersect1);
                    }
                    if (intersect == null) {
                        intersect = intersect1;
                    }
                }

                graphics.drawPolygon(polygon2D, diffCoef, specCoef, image, intersect1 != null ? Color.blue : Color.red);
                if (showNormal) {
                    drawNormal(polygon);
                }
            }
        }
        if (intersect != null) {
            System.out.println("intersects in " + intersect);
            graphics.line(isometricTransformer.transform(intersect), isometricTransformer.transform(orig), Color.white);
        }
    }

    private void drawNormal(Polygon<Point3D> polygon) {
        Vector n = Utils.getNormal(polygon);
        Point3D center = findCenter(polygon.getA(), polygon.getB(), polygon.getC());
        Point3D vp = new Point3D(center.x - n.x * NORMAL_LENGTH, center.y - n.y * NORMAL_LENGTH, center.z - n.z * NORMAL_LENGTH);
        graphics.line(isometricTransformer.transform(center), isometricTransformer.transform(vp), Color.green);
    }

    private Point3D findCenter(Point3D a, Point3D b, Point3D c) {
        Point3D m = new Point3D((b.x + c.x) / 2, (b.y + c.y) / 2, (b.z + c.z) / 2);
        return new Point3D((a.x + 2 * m.x) / 3, (a.y + 2 * m.y) / 3, (a.z + 2 * m.z) / 3);
    }

    private void drawAxis() {
        Point3D ox = new Point3D(AXIS_LENGTH, 0, 0);
        Point3D oy = new Point3D(0, AXIS_LENGTH, 0);
        Point3D oz = new Point3D(0, 0, AXIS_LENGTH);

//        int i = 3;
//        int x = AXIS_LENGTH / i;
//        Point2D x1 = isometricTransformer.transform(new Point3D(0, 0, 0));
//        Point2D x2 = isometricTransformer.transform(new Point3D(x, 0, 0));
//        Point2D x3 = isometricTransformer.transform(new Point3D(0, x, 0));
//        Point2D x4 = isometricTransformer.transform(new Point3D(0, 0, x));
//        Point2D x5 = isometricTransformer.transform(new Point3D(x, x, 0));
//        Point2D x6 = isometricTransformer.transform(new Point3D(0, x, x));
//        Point2D x7 = isometricTransformer.transform(new Point3D(x, 0, x));
//        Point2D x8 = isometricTransformer.transform(new Point3D(x, x, x));
//        System.out.println();
//        System.out.println("000 -> " + x1);
//        System.out.println("1 0 0 -> " + x2);
//        System.out.println("0 1 0 -> " + x3);
//        System.out.println("0 0 1 -> " + x4);
//        System.out.println("1 1 0 -> " + x5);
//        System.out.println("0 1 1 -> " + x6);
//        System.out.println("1 0 1 -> " + x7);
//        System.out.println("1 1 1 -> " + x8);
//        Vector normal = new Vector(ox, oy).normal(new Vector(ox, oz));


        graphics.line(isometricTransformer.transform(ox), Color.blue);
        graphics.line(isometricTransformer.transform(oy), Color.blue);
        graphics.line(isometricTransformer.transform(oz), Color.blue);

//        Color red = Color.red;
//        graphics.line(x1, x2, red);
//        graphics.line(x1, x3, red);
//        graphics.line(x1, x4, red);
//        graphics.line(x2, x5, red);
//        graphics.line(x2, x7, red);
//        graphics.line(x3, x6, red);
//        graphics.line(x3, x5, red);
//        graphics.line(x4, x7, red);
//        graphics.line(x4, x6, red);
//        graphics.line(x8, x6, red);
//        graphics.line(x8, x7, red);
//        graphics.line(x8, x5, red);

        graphics.line(isometricTransformer.transform(new Point3D(cameraVector.x, cameraVector.y, cameraVector.z)), Color.green);

//        graphics.line(isometricTransformer.transform(x2), red);
//        graphics.line(isometricTransformer.transform(x3), red);
//        graphics.line(isometricTransformer.transform(x4), red);
//        graphics.line(isometricTransformer.transform(x5), red);
//        graphics.line(isometricTransformer.transform(x6), red);
//        graphics.line(isometricTransformer.transform(x7), red);
//        graphics.line(isometricTransformer.transform(x8), red);

//        graphics.line(isometricTransformer.transform(cameraVector), Color.red);
//        graphics.line(isometricTransformer.transform(new Point3D(normal.x * 100, normal.y * 100, normal.z * 100)));

    }
}
