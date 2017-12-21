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

    public static <T extends Point> Polygon<T>[] convert(T[][] cylinderPoints, boolean reverse, boolean isContinuous) {

        List<Polygon> polygons = new ArrayList<>();
        for (int i = 0; i < cylinderPoints.length - 1; i++) {
            for (int j = 0; j < cylinderPoints[i].length - 1; j++) {
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


    public static Vector getReflectedVector(Vector normal, Vector origin) {
        Vector reflected = origin.substract(normal.mul(2).mul(origin.dot(normal)));
        return reflected.divide(reflected.length());
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

    public static Point3D intersect(Point3D orig, Vector direction, Polygon<Point3D> T) {

        Vector v0 = new Vector(T.getA().x, T.getA().y, T.getA().z);
//        Vector origv = new Vector(orig.x, orig.y, orig.z);
//
        Vector v0v1 = new Vector(T.getA(), T.getB());
        Vector v0v2 = new Vector(T.getA(), T.getC());
        Vector pvec = direction.crossProduct(v0v2);
        Vector origv = new Vector(orig.x, orig.y, orig.z);
        double det = v0v1.dot(pvec);
        if (Math.abs(det) < 0.001) return null;
        double invDet = 1. / det;
        Vector tvec = origv.substract(v0);
        double u = tvec.dot(pvec) * invDet;
        if (u < 0 || u > 1) return null;
        Vector qvec = tvec.crossProduct(v0v1);
        double v = direction.dot(qvec) * invDet;
        if (v < 0 || u + v > 1) return null;
        double t = v0v2.dot(qvec) * invDet;
        return new Point3D(
                orig.x + t * direction.x,
                orig.y + t * direction.y,
                orig.z + t * direction.z);

//        Vector v0 = new Vector(T.getA().x, T.getA().y, T.getA().z);
//        Vector v1 = new Vector(T.getB().x, T.getB().y, T.getB().z);
//        Vector v2 = new Vector(T.getC().x, T.getC().y, T.getC().z);
//        Vector origv = new Vector(orig.x, orig.y, orig.z);
//
//        Vector v0v1 = new Vector(T.getA(), T.getB());
//        Vector v0v2 = new Vector(T.getA(), T.getC());
//
//        Vector n = v0v1.crossProduct(v0v2);
////        n = n.divide(-n.length());
//        System.out.println(getNormal(T) + " " + n.divide(n.length()));
//        double NdotRayDirection = n.dot(direction);
//        if (Math.abs(NdotRayDirection) < 0.001) {
//            return null;
//        }
//        double d = n.dot(v0);
//        double t = (n.dot(origv) + d) / NdotRayDirection;
//        if (t < 0) return null;
//
//        Vector c;
//        Vector p = origv.add(direction.mul(t));
//        Vector edge0 = v1.substract(v0);
//        Vector vp0 = p.substract(v0);
//        c = edge0.crossProduct(vp0);
//        if (n.dot(c) < 0) return null;
//        Vector edge1 = v2.substract(v1);
//        Vector vp1 = p.substract(v1);
//        c = edge1.crossProduct(vp1);
//        if (n.dot(c) < 0) return null;
//        Vector edge2 = v0.substract(v2);
//        Vector vp2 = p.substract(v2);
//        c = edge2.crossProduct(vp2);
//        if (n.dot(c) < 0) return null;
//
//
//
//        return new Point3D(p.x, p.y, p.z);
//

//        Vector u, v, n;
//        Vector dir, w0;
//        double r, a, b;
//
//        u = new Vector(T.getB().getX(), T.getB().getY(), T.getB().getZ());
//        Vector pointOne = new Vector(T.getA().getX(), T.getA().getY(), T.getA().getZ());
//        u = u.substract(pointOne);
//        v = new Vector(T.getC().getX(), T.getC().getY(), T.getC().getZ());
//        v = v.substract(pointOne);
//        n = u.normal(v);
//
//        if (n.length() == 0) {
//            return null;
//        }
//
//        dir = new Vector(direction.x, direction.y, direction.z);
//        w0 = new Vector(orig.x, orig.y, orig.z);
//        w0 = w0.substract(pointOne);
//        a = -(new Vector(n.x, n.y, n.z).dot(w0));
//        b = new Vector(n.x, n.y, n.z).dot(dir);
//
//        if (Math.abs(b) < 0.0001) {
//            return null;
//        }
//
//        r = a / b;
//        if (r < 0.0) {
//            return null;
//        }
//
//        Point3D point3D = new Point3D(orig.x + r * dir.x, orig.y + r * dir.y, orig.z + r * orig.z);
//        double    uu, uv, vv, wu, wv, D;
//        uu = u.dot(u);
//        uv = u.dot(v);
//        vv = v.dot(v);
//        Vector w = new Vector(point3D.x - pointOne.x, point3D.y - pointOne.y, point3D.z - pointOne.z);
//        wu = w.dot(u);
//        wv = w.dot(v);
//        D = uv * uv - uu * vv;
//
//        // get and test parametric coords
//        double s, t;
//        s = (uv * wv - vv * wu) / D;
//        if (s < 0.0 || s > 1.0)         // I is outside T
//            return null;
//        t = (uv * wu - uu * wv) / D;
//        if (t < 0.0 || (s + t) > 1.0)  // I is outside T
//            return null;
//
//        return point3D;
    }
}
