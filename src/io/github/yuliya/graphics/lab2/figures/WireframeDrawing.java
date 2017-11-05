package io.github.yuliya.graphics.lab2.figures;

import io.github.yuliya.graphics.lab2.generator.PointsGenerator;
import io.github.yuliya.graphics.lab2.graphics.Graphics;
import io.github.yuliya.graphics.lab2.graphics.IsometricTransformer;
import io.github.yuliya.graphics.lab2.types.Point2D;
import io.github.yuliya.graphics.lab2.types.Point3D;
import io.github.yuliya.graphics.lab2.types.Vector;

import java.awt.*;

public class WireframeDrawing implements Drawing {

    private static final int AXIS_LENGTH = 250;
    private static final int NORMAL_LENGTH = 40;
    private final PointsGenerator pointsGenerator;
    private final Graphics graphics;
    private final IsometricTransformer isometricTransformer;
    private final boolean showNormal;
    private boolean isContinuous;
    private boolean reverse;

    public WireframeDrawing(Graphics graphics, PointsGenerator pointsGenerator, int fiAngle, int thetaAngle, boolean showNormal, boolean isContinuous, boolean reverse) {
        this.graphics = graphics;
        this.pointsGenerator = pointsGenerator;
        this.isContinuous = isContinuous;
        this.reverse = reverse;
        this.isometricTransformer = new IsometricTransformer(fiAngle, thetaAngle);
        this.showNormal = showNormal;
    }


    @Override
    public void draw() {
        drawAxis();
        drawFigure();
    }

    private void drawFigure() {
        Point3D[][] cylinderPoints = pointsGenerator.generatePoints();

        for (int i = 0; i < cylinderPoints.length; i++) {
            for (int j = 0; j < cylinderPoints[i].length; j++) {
                Point3D point3D = cylinderPoints[i][j];
                Point2D transoformedPoint = isometricTransformer.transform(point3D);
                int jMax = cylinderPoints[i].length;

                boolean canDrawLine = isContinuous || j + 1 < jMax;
                if (canDrawLine)
                    graphics.line(transoformedPoint, isometricTransformer.transform(cylinderPoints[i][(j + 1) % jMax]));
                int iMax = cylinderPoints.length - 1;
                boolean hasTopPoint = i < iMax;
                if (hasTopPoint) {
                    graphics.line(transoformedPoint, isometricTransformer.transform(cylinderPoints[i + 1][j]));
                    if (canDrawLine)
                        graphics.line(transoformedPoint,
                                isometricTransformer.transform(cylinderPoints[i + 1][(j + 1) % jMax]));
                    if (showNormal) {
                        if (canDrawLine) {
                            if (reverse) {
                                drawNormal(cylinderPoints[i + 1][j], point3D, cylinderPoints[i + 1][(j + 1) % jMax]);
                                drawNormal(point3D, cylinderPoints[i][(j + 1) % jMax], cylinderPoints[i + 1][(j + 1) % jMax]);
                            } else {
                                drawNormal(point3D, cylinderPoints[i + 1][j], cylinderPoints[i + 1][(j + 1) % jMax]);
                                drawNormal(cylinderPoints[i][(j + 1) % jMax], point3D, cylinderPoints[i + 1][(j + 1) % jMax]);
                            }
                        }
                    }
                }
            }
        }
    }

    private void drawNormal(Point3D a, Point3D b, Point3D c) {
        Vector v1 = new Vector(a, b);
        Vector v2 = new Vector(a, c);

        Vector n = v1.normal(v2);
        Point3D center = findCenter(a, b, c);
        Point3D vp = new Point3D(center.x - n.x * NORMAL_LENGTH, center.y - n.y * NORMAL_LENGTH, center.z - n.z * NORMAL_LENGTH);
        graphics.line(isometricTransformer.transform(center), isometricTransformer.transform(vp), Color.blue);
    }

    private Point3D findCenter(Point3D a, Point3D b, Point3D c) {
        Point3D m = new Point3D((b.x + c.x) / 2, (b.y + c.y) / 2, (b.z + c.z) / 2);
        return new Point3D((a.x + 2 * m.x) / 3, (a.y + 2 * m.y) / 3, (a.z + 2 * m.z) / 3);
    }

    private void drawAxis() {
        Point3D ox = new Point3D(AXIS_LENGTH, 0, 0);
        Point3D oy = new Point3D(0, AXIS_LENGTH, 0);
        Point3D oz = new Point3D(0, 0, AXIS_LENGTH);

        Point2D x = isometricTransformer.transform(ox);
        Point2D y = isometricTransformer.transform(oy);
        Point2D z = isometricTransformer.transform(oz);
        graphics.drawText(x, "X");
        graphics.drawText(y, "Y");
        graphics.drawText(z, "Z");
        graphics.line(x, Color.red);
        graphics.line(y, Color.red);
        graphics.line(z, Color.red);
    }
}
