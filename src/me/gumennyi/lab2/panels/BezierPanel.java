package me.gumennyi.lab2.panels;

import me.gumennyi.lab2.draw.BezierFlat;
import me.gumennyi.lab2.draw.Drawing;
import me.gumennyi.lab2.draw.WireframeDrawing;
import me.gumennyi.lab2.generator.BezierPoints;
import me.gumennyi.lab2.graphics.Graphics;
import me.gumennyi.lab2.types.Point2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BezierPanel implements GraphicPanels {
    private static final int SIZE = 500;
    //    private final GraphicCanvas flatCanvas;
    private JPanel panel;
    private GraphicCanvas canvas;
    private int fiAngle = 20;
    private int thetaAngle = -260;
    private int lightShiftFi = 0;
    private int lightShiftTheta = 0;
    private int edges = 20;
    private int scale = 30;
    private boolean showNormals;
    private String bezierPoints = "-5,3;0,6;5,2";
    private List<Point2D> sourcePoints;
    private Color lineColor;
    private double factor;
    private BezierPoints pointsGenerator;

    public BezierPanel() {
        parseBezierPoints();
        canvas = new GraphicCanvas(newFunction(), SIZE, SIZE);
//        flatCanvas = new GraphicCanvas(newFlatBezierFutenction(), SIZE, SIZE);
        init();
    }

    private void init() {
        panel = new JPanel(new GridLayout(2, 2));
        panel.add(canvas);

//        panel.add(createFlatPanel());
        panel.add(createControls());
        panel.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
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
                        scale += 10;
                        repaint();
                        break;
                    case 109:
                        if (scale > 10) {
                            scale -= 10;
                            repaint();
                        }
                        break;
                    case 100:
                        if (edges > 10) {
                            edges -= 10;
                            repaint();
                        }
                        break;
                    case 102:
                        edges += 10;
                        repaint();
                        break;
                    case 78:
                        showNormals = !showNormals;
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

//    private Component createFlatPanel() {
//        JPanel panel = new JPanel();
//        panel.add(flatCanvas);
//        return panel;
//    }

    private Component createControls() {
        JPanel controlPanel = new JPanel(new GridLayout(7, 2));

        setBezierPointsEditor(controlPanel);

        return controlPanel;
    }

    private void setBezierPointsEditor(JPanel controlPanel) {
        JTextField bezierText = new JTextField(bezierPoints);
        bezierText.addActionListener(e -> {
            bezierPoints = bezierText.getText();
            parseBezierPoints();
            repaint();
        });

        controlPanel.add(new Label("Points"));
        controlPanel.add(bezierText);
    }

    private void parseBezierPoints() {
        Function<String, Point2D> stringToPoint = (str) -> {
            String[] xyChunks = str.split(",");
            return new Point2D(Float.parseFloat(xyChunks[0]), Float.parseFloat(xyChunks[1]));
        };
        sourcePoints = Arrays.stream(bezierPoints.split(";"))
                .map(stringToPoint)
                .collect(Collectors.toList());
    }

    private void repaint() {
        canvas.setDrawingFunction(newFunction());
        canvas.repaint();

//        flatCanvas.setDrawingFunction(newFlatBezierFunction());
//        flatCanvas.repaint();
    }

    public JPanel getPanel() {
        return panel;
    }

    private Function<me.gumennyi.lab2.graphics.Graphics, Drawing> newFunction() {
        pointsGenerator = new BezierPoints(sourcePoints, scale, scale, edges, edges);
        return graphics -> {
            return new WireframeDrawing(graphics,
                    pointsGenerator,
                    fiAngle, thetaAngle, showNormals, true, true, lineColor, lightShiftFi, lightShiftTheta, factor);
        };
    }

    private Function<Graphics, Drawing> newFlatBezierFunction() {
        return graphics -> new BezierFlat(sourcePoints, graphics, edges, scale, scale);
    }
}
