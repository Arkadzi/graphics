package io.github.solomkinmv.graphics.lab2.panels;

import io.github.solomkinmv.graphics.lab2.figures.Drawing;
import io.github.solomkinmv.graphics.lab2.graphics.CenteredGraphics;
import io.github.solomkinmv.graphics.lab2.graphics.DefaultGraphics;
import io.github.solomkinmv.graphics.lab2.graphics.Graphics;

import java.awt.*;
import java.util.function.Function;

public class GraphicCanvas extends Canvas {
    private final int height;
    private final int width;
    private Function<Graphics, Drawing> drawingFunction;

    public GraphicCanvas(Function<Graphics, Drawing> drawingFunction, int height, int width) {
        this.drawingFunction = drawingFunction;
        this.height = height;
        this.width = width;
        setSize(width, height);
    }

    public void setDrawingFunction(Function<Graphics, Drawing> drawingFunction) {
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