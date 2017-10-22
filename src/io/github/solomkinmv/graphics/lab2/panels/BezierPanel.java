package io.github.solomkinmv.graphics.lab2.panels;

import io.github.solomkinmv.graphics.lab2.figures.BezierFlat;
import io.github.solomkinmv.graphics.lab2.figures.Drawing;
import io.github.solomkinmv.graphics.lab2.figures.WireframeDrawing;
import io.github.solomkinmv.graphics.lab2.generator.BezierPoints;
import io.github.solomkinmv.graphics.lab2.graphics.Graphics;
import io.github.solomkinmv.graphics.lab2.types.Point2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BezierPanel implements GraphicPanels {
    private static final int SIZE = 600;
    private final GraphicCanvas flatCanvas;
    private JPanel panel;
    private GraphicCanvas canvas;
    private int fiAngle = 220;
    private int thetaAngle = 60;
    private int edges = 10;
    private int radius = 30;
    private int height = 30;
    private boolean showNormals;
    private String bezierPoints = "1,1;5,7;8,2;10,10;15,12";
    private List<Point2D> sourcePoints;

    public BezierPanel() {
        parseBezierPoints();
        canvas = new GraphicCanvas(newBezierFunction(), SIZE, SIZE);
        flatCanvas = new GraphicCanvas(newFlatBezierFunction(), SIZE / 2, SIZE / 2);
        init();
    }

    private void init() {
        panel = new JPanel(new BorderLayout());
        panel.add(canvas, BorderLayout.CENTER);

        JPanel sidePanel = new JPanel(new BorderLayout());
        sidePanel.add(createFlatPanel(), BorderLayout.NORTH);
        sidePanel.add(createControls(), BorderLayout.SOUTH);
        panel.add(sidePanel, BorderLayout.EAST);
    }

    private Component createFlatPanel() {
        JPanel panel = new JPanel();
        panel.add(flatCanvas);
        return panel;
    }

    private Component createControls() {
        JPanel controlPanel = new JPanel(new GridLayout(7, 2));

        setNavigationButtons(controlPanel);
        setEdgesSlider(controlPanel);
        setRadiusSpinner(controlPanel);
        setHeightSpinner(controlPanel);
        setBezierPointsEditor(controlPanel);
        setNormalsShowCheckBox(controlPanel);

        return controlPanel;
    }

    private void setNormalsShowCheckBox(JPanel controlPanel) {
        JCheckBox normalsCheckBox = new JCheckBox("Show normals");
        normalsCheckBox.addItemListener(e -> {
            showNormals = e.getStateChange() == ItemEvent.SELECTED;
            repaint();
        });
        controlPanel.add(normalsCheckBox);
    }

    private void setBezierPointsEditor(JPanel controlPanel) {
        JTextField bezierText = new JTextField(bezierPoints);
        bezierText.addActionListener(e -> {
            bezierPoints = bezierText.getText();
            parseBezierPoints();
            repaint();
        });

        controlPanel.add(new Label("Bezier points"));
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

    private void setRadiusSpinner(JPanel controlPanel) {
        SpinnerModel spinnerModel = new SpinnerNumberModel(radius, //initial value
                                                           5, //min
                                                           300, //max
                                                           10);//step
        JSpinner spinner = new JSpinner(spinnerModel);
        spinner.addChangeListener(e -> {
            radius = ((Number) ((JSpinner) e.getSource()).getValue()).intValue();
            repaint();
        });
        controlPanel.add(new Label("Radius"));
        controlPanel.add(spinner);
    }

    private void setHeightSpinner(JPanel controlPanel) {
        SpinnerModel spinnerModel = new SpinnerNumberModel(height, //initial value
                                                           5, //min
                                                           300, //max
                                                           10);//step
        JSpinner spinner = new JSpinner(spinnerModel);
        spinner.addChangeListener(e -> {
            height = ((Number) ((JSpinner) e.getSource()).getValue()).intValue();
            repaint();
        });
        controlPanel.add(new Label("Height"));
        controlPanel.add(spinner);
    }

    private void setEdgesSlider(JPanel controlPanel) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 2, 50, edges);

        slider.setValue(edges);
        slider.addChangeListener(e -> {
            edges = ((JSlider) e.getSource()).getValue();
            repaint();
        });
        controlPanel.add(new Label("edges"));
        controlPanel.add(slider);
    }

    private void setNavigationButtons(JPanel controlPanel) {
        JButton leftButton = new JButton("left");
        leftButton.addActionListener(e -> {
            fiAngle += 10;
            repaint();
        });

        JButton rightButton = new JButton("right");
        rightButton.addActionListener(e -> {
            fiAngle -= 10;
            repaint();
        });

        JButton upButton = new JButton("up");
        upButton.addActionListener(e -> {
            thetaAngle -= 10;
            repaint();
        });

        JButton downButton = new JButton("down");
        downButton.addActionListener(e -> {
            thetaAngle += 10;
            repaint();
        });

        controlPanel.add(leftButton);
        controlPanel.add(rightButton);
        controlPanel.add(upButton);
        controlPanel.add(downButton);
    }

    public JPanel getPanel() {
        return panel;
    }

    private Function<Graphics, Drawing> newBezierFunction() {
        return graphics -> new WireframeDrawing(graphics,
                                                new BezierPoints(sourcePoints, radius, height, edges, edges),
                                                fiAngle, thetaAngle, showNormals, true);
    }

    private Function<Graphics, Drawing> newFlatBezierFunction() {
        return graphics -> new BezierFlat(sourcePoints, graphics, edges, radius, height);
    }
}
