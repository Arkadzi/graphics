package me.gumennyi.lab2.types;

public class Intersection {
    private Point3D origin;
    private Polygon<Point3D> polygon;
    private Vector direction;
    private Point3D intersectionPoint;

    public Intersection(Point3D origin, Vector direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public void setIntersection(Polygon<Point3D> polygon, Point3D intersectionPoint) {
        double abs = Math.abs(new Vector(origin, intersectionPoint).length());
        double abs1 = this.intersectionPoint != null ? Math.abs(new Vector(origin, this.intersectionPoint).length()) : 0;
        if (this.intersectionPoint == null ||
                abs < abs1) {
            this.intersectionPoint = intersectionPoint;
            this.polygon = polygon;
        }
    }

    public Point3D getOrigin() {
        return origin;
    }

    public Polygon<Point3D> getPolygon() {
        return polygon;
    }

    public Vector getDirection() {
        return direction;
    }

    public Point3D getIntersectionPoint() {
        return intersectionPoint;
    }
}
