package me.gumennyi.lab2.graphics;


import me.gumennyi.lab2.types.Point2D;
import me.gumennyi.lab2.types.Polygon;
import org.ejml.simple.SimpleMatrix;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DefaultGraphics implements me.gumennyi.lab2.graphics.Graphics {

    private final java.awt.Graphics graphics;

    public DefaultGraphics(java.awt.Graphics graphics) {
        this.graphics = graphics;
    }

    @Override
    public void line(Point2D pointA, Point2D pointB) {
        graphics.drawLine(pointA.getX(), pointA.getY(), pointB.getX(), pointB.getY());
    }

    @Override
    public void line(Point2D point) {
        graphics.drawLine(0, 0, point.getX(), point.getY());
    }

    @Override
    public void drawPolygon(Polygon<Point2D> polygon, double lambert, BufferedImage image, Color lineColor) {
//
        int r = (int) (200 * lambert);
        graphics.setColor(new Color(polygon.isTop() ? r : 0, polygon.isTop() ? 0 : r, 0));
//        Point2D a = polygon.getA();
//        Point2D b = polygon.getB();
//        Point2D c = polygon.getC();
//        graphics.fillPolygon(new int[] {a.getX(), b.getX(), c.getX()},
//                new int[] {a.getY(), b.getY(), c.getY()}, 3);
//        if (lineColor != null) {
//            graphics.setColor(lineColor);
//            line(a,b);
//            line(a,c);
//            line(b,c);
//        }
        SimpleMatrix polygonMatrix = getPolygonMatrix(polygon);
        Polygon<Point2D> rasterPolygon = getRasterPolygon(polygon, image);
        SimpleMatrix rasterMatrix = getPolygonMatrix(rasterPolygon);
        SimpleMatrix transformMatrix = rasterMatrix.mult(polygonMatrix.invert());
        for (int x = (int) Utils.minX(polygon); x < Utils.maxX(polygon); x++) {
            for (int y = (int) Utils.minY(polygon); y < Utils.maxY(polygon); y++) {
                if (Utils.containsPoint(polygon, x, y)) {
//                    graphics.setColor(new Color(rgb));
//                        graphics.drawRect(x, y, 1, 1);

                    SimpleMatrix rasterCoords = new SimpleMatrix(new double[][]{{x}, {y}, {1}});
                    SimpleMatrix result = transformMatrix.mult(rasterCoords);
//                    System.out.println(result);
                    int x1 = (int) result.get(0, 0);
                    int y1 = (int) result.get(1, 0);
                    try {
                        System.out.println(x1 + " d " + y1);
                        int rgb = image.getRGB(x1, y1);
                        Color c = new Color(rgb);
                        c = new Color((int) (c.getRed() * lambert), (int) (c.getGreen() * lambert), (int) (c.getBlue() *lambert));
                        graphics.setColor(c);
                        graphics.drawRect(x, y, 1, 1);
                    } catch (Exception e) {
                        System.out.println(x1 + " " + y1);
                    }

                }
            }
        }
//        if (!polygon.isTop()) {
////            System.out.println("_______________________");
////            System.out.println(polygon);
//            int yStart = rasterPolygon.getA().getY();
//            int yEnd = rasterPolygon.getC().getY();
//            int xStart = rasterPolygon.getA().getX();
//            int xEnd = rasterPolygon.getC().getX();
//            for (int i = yStart; i < yEnd; i++) {
//                int i1 = xStart + ((xEnd - xStart) * i) / (yEnd - yStart);
//                for (int j = xStart; j < i1; j++) {
//                    SimpleMatrix rasterCoords = new SimpleMatrix(new double[][]{{j}, {i}, {1}});
//                    SimpleMatrix result = transformMatrix.mult(rasterCoords);
//
//                    int x = (int) result.get(0, 0);
//                    int y = (int) result.get(1, 0);
//                    try {
//                        int rgb = image.getRGB(j, i);
//                        System.out.println(x + ' ' + y);
//                        graphics.setColor(new Color(rgb));
//                        graphics.drawRect(x, y, 1, 1);
//                    } catch (Exception e) {
//                        System.out.println(i + " " + j);
//                    }
////                    graphics.setColor(new Color((int) (200 * lambert), 0, 0));
//                }
//            }
//        }

    }

    private Polygon<Point2D> getRasterPolygon(Polygon<Point2D> polygon, BufferedImage image) {
        int xStart = polygon.gethIndex() * image.getWidth() / polygon.gethTotal();
        int xEnd = (polygon.gethIndex() + 1) * image.getWidth() / polygon.gethTotal();
        int yStart = polygon.getvIndex() * image.getHeight() / polygon.getvTotal();
        int yEnd = (polygon.getvIndex() + 1) * image.getHeight() / polygon.getvTotal();
        Point2D point1 = new Point2D(xStart, yStart);
        Point2D point2 = new Point2D(xStart, yEnd);
        Point2D point3 = new Point2D(xEnd, yEnd);
        Point2D point4 = new Point2D(xEnd, yStart);
        return polygon.isTop()
                ? new Polygon<>(point1, point2, point3)
                : new Polygon<>(point4, point1, point3);
    }

    private SimpleMatrix getPolygonMatrix(Polygon<Point2D> polygon) {
        return new SimpleMatrix(new double[][]{
                {polygon.getA().getX(), polygon.getB().getX(), polygon.getC().getX()},
                {polygon.getA().getY(), polygon.getB().getY(), polygon.getC().getY()},
                {1, 1, 1}
        });
    }

    @Override
    public void line(Point2D point, Color color) {
        graphics.setColor(color);
        graphics.drawLine(0, 0, point.getX(), point.getY());
        graphics.setColor(Color.white);
    }

    @Override
    public void line(Point2D pointA, Point2D pointB, Color color) {
        graphics.setColor(color);
        line(pointA, pointB);
        graphics.setColor(Color.white);
    }
}
