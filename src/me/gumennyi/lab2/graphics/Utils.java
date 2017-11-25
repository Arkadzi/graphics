package me.gumennyi.lab2.graphics;

import me.gumennyi.lab2.types.Point;
import me.gumennyi.lab2.types.Point3D;
import me.gumennyi.lab2.types.Polygon;
import me.gumennyi.lab2.types.Vector;

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
                    polygons.add(new Polygon<>(point2, point1, point3));
                    polygons.add(new Polygon<>(point1, point4, point3));
                } else {
                    polygons.add(new Polygon<>(point1, point2, point3));
                    polygons.add(new Polygon<>(point4, point1, point3));
                }
            }
        }
        return polygons.toArray(new Polygon[polygons.size()]);
    }

    public static Vector getNormal(Polygon<Point3D> polygon) {
        Vector v1 = new Vector(polygon.getA(), polygon.getB());
        Vector v2 = new Vector(polygon.getA(), polygon.getC());
        return v1.normal(v2);
    }
}
