package io.github.solomkinmv.graphics.lab2.panels;

import io.github.solomkinmv.graphics.lab2.figures.Drawing;
import io.github.solomkinmv.graphics.lab2.figures.WireframeDrawing;
import io.github.solomkinmv.graphics.lab2.generator.CylinderPoints;
import io.github.solomkinmv.graphics.lab2.graphics.Graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.function.Function;

public class CylinderPanel implements GraphicPanels {
    private static final int SIZE = 600;
    private int fiAngle = 220;
    private int thetaAngle = 60;
    private GraphicCanvas canvas;
    private JPanel panel;
    private int edges = 10;
    private int radius = 200;
    private int height = 300;
    private boolean showNormals;

    public CylinderPanel() {
        canvas = new GraphicCanvas(newCylinderFunction(), SIZE, SIZE);
        init();
    }

    public JPanel getPanel() {
        return panel;
    }

    private void init() {
        panel = new JPanel();
        panel.add(canvas);
        panel.add(createControls());
    }

    private Component createControls() {
        JPanel controlPanel = new JPanel(new GridLayout(6, 2));

        setNavigationButtons(controlPanel);
        setEdgesSlider(controlPanel);
        setRadiusSpinner(controlPanel);
        setHeightSpinner(controlPanel);
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

    private void setRadiusSpinner(JPanel controlPanel) {
        SpinnerModel spinnerModel = new SpinnerNumberModel(200, //initial value
                                                           20, //min
                                                           300, //max
                                                           20);//step
        JSpinner spinner = new JSpinner(spinnerModel);
        spinner.addChangeListener(e -> {
            radius = ((Number) ((JSpinner) e.getSource()).getValue()).intValue();
            repaint();
        });
        controlPanel.add(new Label("Radius"));
        controlPanel.add(spinner);
    }

    private void setHeightSpinner(JPanel controlPanel) {
        SpinnerModel spinnerModel = new SpinnerNumberModel(200, //initial value
                                                           20, //min
                                                           300, //max
                                                           20);//step
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

    private void repaint() {
        canvas.setDrawingFunction(newCylinderFunction());
        canvas.repaint();
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

    private Function<Graphics, Drawing> newCylinderFunction() {
        return graphics -> new WireframeDrawing(graphics,
                                                new CylinderPoints(radius, height, edges, edges), fiAngle,
                                                thetaAngle, showNormals, false);
    }

}
