package io.github.yuliya.graphics.lab2.graphics;

import io.github.yuliya.graphics.lab2.types.Point2D;

import java.awt.*;

public interface Graphics {
    void line(Point2D pointA, Point2D pointB);
    void line(Point2D point);
    void line(Point2D point, Color color);
    void line(Point2D pointA, Point2D point2D, Color color);
    void drawText(Point2D point, String text);
}