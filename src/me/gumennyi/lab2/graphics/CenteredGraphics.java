package me.gumennyi.lab2.graphics;

import me.gumennyi.lab2.types.Point2D;
import me.gumennyi.lab2.types.Polygon;

import java.awt.*;


public class CenteredGraphics implements Graphics {
    private final Point2D center;
    private final Graphics graphics;

    public CenteredGraphics(Graphics graphics, int height, int width) {
        this.graphics = graphics;
        center = new Point2D(width / 2, height / 2);
    }

    @Override
    public void line(Point2D a, Point2D b) {
        Point2D aCentered = toCentered(a);
        Point2D bCentered = toCentered(b);
        graphics.line(aCentered, bCentered);
    }


    @Override
    public void drawPolygon(Polygon<Point2D> polygon, Color color, Color lineColor) {
        graphics.drawPolygon(new Polygon<>(toCentered(polygon.getA()), toCentered(polygon.getB()), toCentered(polygon.getC())), color, lineColor);
    }

    @Override
    public void line(Point2D point) {
        Point2D centered = toCentered(point);
        graphics.line(center, centered);
    }

    @Override
    public void line(Point2D point, Color color) {
        Point2D centered = toCentered(point);
        graphics.line(center, centered, color);
    }

    @Override
    public void line(Point2D a, Point2D b, Color color) {
        Point2D aCentered = toCentered(a);
        Point2D bCentered = toCentered(b);
        graphics.line(aCentered, bCentered, color);
    }


    private Point2D toCentered(Point2D point) {
        return new Point2D(center.x + point.x, center.y - point.y);
    }
}
