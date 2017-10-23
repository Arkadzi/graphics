package io.github.solomkinmv.graphics.lab2.graphics;

import io.github.solomkinmv.graphics.lab2.types.Point2D;
import io.github.solomkinmv.graphics.lab2.types.Point3D;

import java.awt.*;

public interface Graphics {
    void line(Point2D pointA, Point2D pointB);
    void line(Point2D point);
    void line(Point2D point, Color color);
    void line(Point2D pointA, Point2D point2D, Color color);
}
