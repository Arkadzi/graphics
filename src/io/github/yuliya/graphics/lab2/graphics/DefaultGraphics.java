package io.github.yuliya.graphics.lab2.graphics;

import io.github.yuliya.graphics.lab2.types.Point2D;

import java.awt.*;

public class DefaultGraphics implements Graphics {

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
    public void line(Point2D point, Color color) {
        graphics.setColor(color);
        graphics.drawLine(0, 0, point.getX(), point.getY());
        graphics.setColor(Color.black);
    }

    @Override
    public void line(Point2D pointA, Point2D pointB, Color color) {
        graphics.setColor(color);
        line(pointA, pointB);
        graphics.setColor(Color.black);
    }

    @Override
    public void drawText(Point2D point, String text) {
        int x = (int) point.x;
        int y = (int) point.y;
        graphics.drawChars(text.toCharArray(), 0, text.length(), x, y);
    }
}
