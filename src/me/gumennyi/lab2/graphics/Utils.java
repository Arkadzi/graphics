package me.gumennyi.lab2.graphics;

import me.gumennyi.lab2.types.*;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static double degToRad(double deg) {
        return deg * Math.PI / 180;
    }

    public static double radToDeg(double rad) {
        return rad * 180 / Math.PI;
    }

    public static <T extends Point> Polygon<T>[] convert(T[][] cylinderPoints, boolean reverse) {

        List<Polygon> polygons = new ArrayList<>();
        for (int i = 0; i < cylinderPoints.length - 1; i++) {
            for (int j = 0; j < cylinderPoints[i].length; j++) {
                int jMax = cylinderPoints[i].length;
                T point1 = cylinderPoints[i][j];
                T point2 = cylinderPoints[i + 1][j];
                T point3 = cylinderPoints[i + 1][(j + 1) % jMax];
                T point4 = cylinderPoints[i][(j + 1) % jMax];
                if (reverse) {
                    polygons.add(new Polygon<>(point2, point1, point3, i, j, cylinderPoints.length - 1, cylinderPoints[i].length, true));
                    polygons.add(new Polygon<>(point1, point4, point3, i, j, cylinderPoints.length - 1, cylinderPoints[i].length, false));
                } else {
                    polygons.add(new Polygon<>(point1, point2, point3, i, j, cylinderPoints.length - 1, cylinderPoints[i].length, true));
                    polygons.add(new Polygon<>(point4, point1, point3, i, j, cylinderPoints.length - 1, cylinderPoints[i].length, false));
                }
            }
         //   System.out.println(cylinderPoints[i].length);

        }
        return polygons.toArray(new Polygon[polygons.size()]);
    }

    public static Vector getNormal(Polygon<Point3D> polygon) {
        Vector v1 = new Vector(polygon.getA(), polygon.getB());
        Vector v2 = new Vector(polygon.getA(), polygon.getC());
        return v1.normal(v2);
    }


    public static double area(Polygon<Point2D> polygon) {

        Point2D v1 = polygon.getA();
        Point2D v2 = polygon.getB();
        Point2D v3 = polygon.getC();
        return (v1.y - v3.y) * (v2.x - v3.x) + (v2.y - v3.y) * (v3.x - v1.x);
    }

    public static double minX(Polygon<Point2D> polygon) {
        return Math.min(polygon.getA().x, Math.min(polygon.getB().x, polygon.getC().x));
    }

    public static double maxX(Polygon<Point2D> polygon) {
        return Math.max(polygon.getA().x, Math.max(polygon.getB().x, polygon.getC().x));
    }

    public static double minY(Polygon<Point2D> polygon) {
        return Math.min(polygon.getA().y, Math.min(polygon.getB().y, polygon.getC().y));
    }

    public static double maxY(Polygon<Point2D> polygon) {
        return Math.max(polygon.getA().y, Math.max(polygon.getB().y, polygon.getC().y));
    }

    public static boolean containsPoint(Polygon<Point2D> polygon, double x, double y) {
        double triangleArea = area(polygon);

        Point2D v1 = polygon.getA();
        Point2D v2 = polygon.getB();
        Point2D v3 = polygon.getC();
        double b1 = ((y - v3.y) * (v2.x - v3.x) + (v2.y - v3.y) * (v3.x - x)) / triangleArea;
        double b2 = ((y - v1.y) * (v3.x - v1.x) + (v3.y - v1.y) * (v1.x - x)) / triangleArea;
        double b3 = ((y - v2.y) * (v1.x - v2.x) + (v1.y - v2.y) * (v2.x - x)) / triangleArea;

        return b1 >= 0 && b1 <= 1 && b2 >= 0 && b2 <= 1 && b3 >= 0 && b3 <= 1;
    }
}
