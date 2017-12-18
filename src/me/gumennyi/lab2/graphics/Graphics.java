package me.gumennyi.lab2.graphics;


import me.gumennyi.lab2.types.Point2D;
import me.gumennyi.lab2.types.Polygon;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface Graphics {
    void line(Point2D pointA, Point2D pointB);
    void line(Point2D point);
    void line(Point2D point, Color color);
    void line(Point2D pointA, Point2D point2D, Color color);

    void drawPolygon(Polygon<Point2D> polygon2D, double diffCoef, double specCoef, BufferedImage image, Color lineColor);

//    void drawPolygon();
}
