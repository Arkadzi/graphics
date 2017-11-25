package me.gumennyi.lab2.graphics;


import me.gumennyi.lab2.types.Point2D;
import me.gumennyi.lab2.types.Polygon;

import java.awt.*;

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
    public void drawPolygon(Polygon<Point2D> polygon, Color color, Color lineColor) {
        graphics.setColor(color);
        Point2D a = polygon.getA();
        Point2D b = polygon.getB();
        Point2D c = polygon.getC();
        graphics.fillPolygon(new int[] {a.getX(), b.getX(), c.getX()},
                new int[] {a.getY(), b.getY(), c.getY()}, 3);
        if (lineColor != null) {
            graphics.setColor(lineColor);
            line(a,b);
            line(a,c);
            line(b,c);
        }
        graphics.setColor(Color.white);
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
