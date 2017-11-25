package me.gumennyi.lab2.graphics;


import me.gumennyi.lab2.types.Point2D;
import me.gumennyi.lab2.types.Polygon;

import java.awt.*;

public interface Graphics {
    void line(Point2D pointA, Point2D pointB);
    void line(Point2D point);
    void drawPolygon(Polygon<Point2D> polygon, Color color, Color lineColor);
    void line(Point2D point, Color color);
    void line(Point2D pointA, Point2D point2D, Color color);
}
