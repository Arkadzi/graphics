package me.gumennyi.lab2.types;

import me.gumennyi.lab2.graphics.IsometricTransformer;

public class Square {
    private Polygon<Point3D> polygon;
    private Polygon<Point2D> polygon2d;
    private double minX;
    private double minY;
    private double maxX;
    private double maxY;

    public Square(Polygon<Point3D> polygon, IsometricTransformer isometricTransformer) {
        this.polygon = polygon;
        Point2D a = isometricTransformer.transform(polygon.getA());
        Point2D b = isometricTransformer.transform(polygon.getB());
        Point2D c = isometricTransformer.transform(polygon.getC());
        polygon2d = new Polygon<>(a,b,c);
        minX = Math.min(Math.min(a.x, b.x), c.x);
        minY = Math.min(Math.min(a.y, b.y), c.y);
        maxY = Math.max(Math.max(a.y, b.y), c.y);
        maxX = Math.max(Math.max(a.x, b.x), c.x);
    }

    public Polygon<Point3D> getPolygon() {
        return polygon;
    }

    public Polygon<Point2D> getPolygon2d() {
        return polygon2d;
    }

    public double getMinX() {
        return minX;
    }

    public double getMinY() {
        return minY;
    }

    public double getMaxX() {
        return maxX;
    }

    public double getMaxY() {
        return maxY;
    }
}
