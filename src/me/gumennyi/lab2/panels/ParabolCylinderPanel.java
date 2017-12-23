package me.gumennyi.lab2.panels;

import me.gumennyi.lab2.draw.Drawing;
import me.gumennyi.lab2.draw.WireframeDrawing;
import me.gumennyi.lab2.generator.ParabolCylinderPoints;
import me.gumennyi.lab2.graphics.Graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.function.Function;

public class ParabolCylinderPanel implements GraphicPanels {
    private static final int SIZE = 700;
    private int fiAngle = 220;
    private int thetaAngle = 60;
    private GraphicCanvas canvas;
    private JPanel panel;
    private int edges = 10;
    private int scale = 300;
    private boolean showNormals;
    private Color lineColor;
    private int lightShiftFi = 0;
    private int lightShiftTheta = 0;
    private double factor;
    private boolean randomize;
    private ParabolCylinderPoints pointsGenerator;


    public ParabolCylinderPanel() {
        canvas = new GraphicCanvas(newCylinderFunction(), SIZE, SIZE);
        init();
    }

    public JPanel getPanel() {
        return panel;
    }

    private void init() {
        generatePointsGenerator();
        panel = new JPanel(new BorderLayout());
        panel.add(canvas);
        panel.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println(e.getKeyCode());
                switch (e.getKeyCode()) {
                    case 37:
                        fiAngle += 10;
                        repaint();
                        break;
                    case 38:
                        thetaAngle -= 10;
                        repaint();
                        break;
                    case 39:
                        fiAngle -= 10;
                        repaint();
                        break;
                    case 40:
                        thetaAngle += 10;
                        repaint();
                        break;
                    case 107:
                        scale += 50;
                        repaint();
                        break;
                    case 109:
                        if (scale > 50) {
                            scale -= 50;
                            repaint();
                        }
                        break;
                    case 100:
                        if (edges > 2) {
                            edges -= 2;
                            generatePointsGenerator();
                            repaint();
                        }
                        break;
                    case 102:
                        edges += 2;
                        generatePointsGenerator();
                        repaint();
                        break;
                    case 78:
                        showNormals = !showNormals;
                        repaint();
                        break;
                    case 82:
                        randomize = !randomize;
                        generatePointsGenerator();
                        repaint();
                        break;
                    case 76:
                        lineColor = lineColor == null ? Color.black : null;
                        repaint();
                        break;
                    case 65:
                        lightShiftFi -= 5;
                        repaint();
                        break;
                    case 68:
                        lightShiftFi += 5;
                        repaint();
                        break;
                    case 87:
                        lightShiftTheta += 5;
                        repaint();
                        break;
                    case 83:
                        lightShiftTheta -= 5;
                        repaint();
                        break;
                    case 70:
                        if (factor > -0.9) {
                            factor -= 0.1;
                            repaint();
                        }
                        break;
                    case 71:
                        if (factor < 1) {
                            factor += 0.1;
                            repaint();
                        }
                        break;
                }
//                System.out.println("Pressed " + e.getKeyCode());
            }
        });

        panel.setFocusable(true);
    }

    private void repaint() {
        canvas.setDrawingFunction(newCylinderFunction());
        canvas.setBackground(Color.black);
        canvas.repaint();
    }

    private Function<Graphics, Drawing> newCylinderFunction() {
        return graphics -> {
            return new WireframeDrawing(graphics,
                    scale, pointsGenerator, fiAngle,
                    thetaAngle, showNormals, false, true, lineColor, lightShiftFi, lightShiftTheta, factor);
        };
    }

    private void generatePointsGenerator() {
        pointsGenerator = new ParabolCylinderPoints(edges, randomize);
    }

}
