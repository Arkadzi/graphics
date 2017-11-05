package io.github.yuliya.graphics.lab2.panels;

import io.github.yuliya.graphics.lab2.figures.Drawing;
import io.github.yuliya.graphics.lab2.figures.WireframeDrawing;
import io.github.yuliya.graphics.lab2.generator.PortionPoints;
import io.github.yuliya.graphics.lab2.graphics.Graphics;
import io.github.yuliya.graphics.lab2.types.Point3D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.Arrays;
import java.util.function.Function;

public class PortionPanel implements GraphicPanels {
    private static final int SIZE = 500;
    private int fiAngle = 220;
    private int thetaAngle = 60;
    private GraphicCanvas canvas;
    private JPanel panel;
    private int edges = 10;
    private String line1Str = "0,0,0;0,0.5,1;0,1,0";
    private String line2Str = "1,0,0;1,0.5,0;1,1,0";
    private Point3D[] line1 = parseBezierPoints(line1Str);
    private Point3D[] line2 = parseBezierPoints(line2Str);
    private boolean showNormals;

    public PortionPanel() {
        canvas = new GraphicCanvas(newCylinderFunction(), SIZE, SIZE);
        init();
    }

    public JPanel getPanel() {
        return panel;
    }

    private void init() {
        panel = new JPanel(new BorderLayout());
        panel.add(canvas);
        panel.add(createControls(), BorderLayout.SOUTH);
    }

    private Component createControls() {
        JPanel controlPanel = new JPanel(new GridLayout(6, 2));

        setNavigationButtons(controlPanel);
        setEdgesSlider(controlPanel);
        setFi1Text(controlPanel);
        setFi2Text(controlPanel);
        setNormalsShowCheckBox(controlPanel);

        return controlPanel;
    }

    private void setNormalsShowCheckBox(JPanel controlPanel) {
        JCheckBox normalsCheckBox = new JCheckBox("Показать нормали");
        normalsCheckBox.addItemListener(e -> {
            showNormals = e.getStateChange() == ItemEvent.SELECTED;
            repaint();
        });
        controlPanel.add(normalsCheckBox);
    }

    private void setFi1Text(JPanel controlPanel) {
            JTextField bezierText = new JTextField(line1Str);
            bezierText.addActionListener(e -> {
                line1Str = bezierText.getText();
                line1 = parseBezierPoints(line1Str);
                repaint();
            });

            controlPanel.add(new Label("Fi 1"));
            controlPanel.add(bezierText);
    }

    private void setFi2Text(JPanel controlPanel) {
            JTextField bezierText = new JTextField(line2Str);
            bezierText.addActionListener(e -> {
                line2Str = bezierText.getText();
                line2 = parseBezierPoints(line2Str);
                repaint();
            });

            controlPanel.add(new Label("Fi 2"));
            controlPanel.add(bezierText);
    }

    private Point3D[] parseBezierPoints(String line) {
        Function<String, Point3D> stringToPoint = (str) -> {
            String[] xyChunks = str.split(",");
            return new Point3D(Float.parseFloat(xyChunks[0]), Float.parseFloat(xyChunks[1]), Float.parseFloat(xyChunks[2]));
        };
        return Arrays.stream(line.split(";"))
                .map(stringToPoint)
                .toArray(Point3D[]::new);
    }

    private void setEdgesSlider(JPanel controlPanel) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 2, 50, edges);

        slider.setValue(edges);
        slider.addChangeListener(e -> {
            edges = ((JSlider) e.getSource()).getValue();
            repaint();
        });
        controlPanel.add(new Label("Сетка"));
        controlPanel.add(slider);
    }

    private void repaint() {
        canvas.setDrawingFunction(newCylinderFunction());
        canvas.repaint();
    }

    private void setNavigationButtons(JPanel controlPanel) {
        JButton leftButton = new JButton("Повернуть влево");
        leftButton.addActionListener(e -> {
            fiAngle += 10;
            repaint();
        });

        JButton rightButton = new JButton("Повернуть вправо");
        rightButton.addActionListener(e -> {
            fiAngle -= 10;
            repaint();
        });

        JButton upButton = new JButton("Повернуть вверх");
        upButton.addActionListener(e -> {
            thetaAngle -= 10;
            repaint();
        });

        JButton downButton = new JButton("Повернуть вниз");
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
                new PortionPoints(
                        line1,
                        line2,
                        edges, edges), fiAngle,
                thetaAngle, showNormals, false, true);
    }

}
