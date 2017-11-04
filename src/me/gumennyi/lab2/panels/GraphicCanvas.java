package me.gumennyi.lab2.panels;

import me.gumennyi.lab2.draw.Drawing;
import me.gumennyi.lab2.graphics.CenteredGraphics;
import me.gumennyi.lab2.graphics.DefaultGraphics;

import java.awt.*;
import java.util.function.Function;

public class GraphicCanvas extends Canvas {
    private final int height;
    private final int width;
    private Function<me.gumennyi.lab2.graphics.Graphics, Drawing> drawingFunction;

    public GraphicCanvas(Function<me.gumennyi.lab2.graphics.Graphics, Drawing> drawingFunction, int height, int width) {
        this.drawingFunction = drawingFunction;
        this.height = height;
        this.width = width;
        setSize(width, height);
    }

    public void setDrawingFunction(Function<me.gumennyi.lab2.graphics.Graphics, Drawing> drawingFunction) {
        this.drawingFunction = drawingFunction;
    }

    @Override
    public void paint(java.awt.Graphics g) {
        setBackground(Color.black);
        g.setColor(Color.white);

        drawingFunction.apply(
                new CenteredGraphics(new DefaultGraphics(g),
                                     height,
                                     width))
                       .draw();
    }
}