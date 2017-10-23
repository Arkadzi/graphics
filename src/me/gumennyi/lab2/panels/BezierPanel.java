package me.gumennyi.lab2.panels;

import me.gumennyi.lab2.figures.BezierFlat;
import me.gumennyi.lab2.figures.Drawing;
import me.gumennyi.lab2.figures.WireframeDrawing;
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
    private final GraphicCanvas flatCanvas;
    private JPanel panel;
    private GraphicCanvas canvas;
    private int fiAngle = 220;
    private int thetaAngle = 60;
    private int edges = 10;
    private int scale = 30;
    private boolean showNormals;
    private String bezierPoints = "1,1;5,7;8,2;10,10;15,12";
    private List<Point2D> sourcePoints;

    public BezierPanel() {
        parseBezierPoints();
        canvas = new GraphicCanvas(newBezierFunction(), SIZE, SIZE);
        flatCanvas = new GraphicCanvas(newFlatBezierFunction(), SIZE, SIZE);
        init();
    }

    private void init() {
        panel = new JPanel(new GridLayout(2,2));
        panel.add(canvas);

        panel.add(createFlatPanel());
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
                        if (edges > 2) {
                            edges -= 2;
                            repaint();
                        }
                        break;
                    case 102:
                        edges += 2;
                        repaint();
                        break;
                    case 78:
                        showNormals = !showNormals;
                        repaint();
                        break;
                }
                System.out.println("Pressed " + e.getKeyCode());
            }
        });

        panel.setFocusable(true);
    }

    private Component createFlatPanel() {
        JPanel panel = new JPanel();
        panel.add(flatCanvas);
        return panel;
    }

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
            return new Point2D(Integer.parseInt(xyChunks[0]), Integer.parseInt(xyChunks[1]));
        };
        sourcePoints = Arrays.stream(bezierPoints.split(";"))
                .map(stringToPoint)
                .collect(Collectors.toList());
    }

    private void repaint() {
        canvas.setDrawingFunction(newBezierFunction());
        canvas.repaint();

        flatCanvas.setDrawingFunction(newFlatBezierFunction());
        flatCanvas.repaint();
    }

    public JPanel getPanel() {
        return panel;
    }

    private Function<me.gumennyi.lab2.graphics.Graphics, Drawing> newBezierFunction() {
        return graphics -> new WireframeDrawing(graphics,
                new BezierPoints(sourcePoints, scale, scale, edges, edges),
                fiAngle, thetaAngle, showNormals, true, false);
    }

    private Function<Graphics, Drawing> newFlatBezierFunction() {
        return graphics -> new BezierFlat(sourcePoints, graphics, edges, scale, scale);
    }
}
