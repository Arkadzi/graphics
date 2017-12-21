package me.gumennyi.lab2;

import me.gumennyi.lab2.panels.BezierPanel;
import me.gumennyi.lab2.panels.ParabolCylinderPanel;
import me.gumennyi.lab2.panels.GraphicPanels;
import me.gumennyi.lab2.types.Vector;
import org.ejml.simple.SimpleMatrix;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Application {
    public static void main(String[] args) {
//        SimpleMatrix matrix = new SimpleMatrix(new double[][]{{1, 2, 3}, {1, 2, 3}, {1, 2, 3}});
//        SimpleMatrix matrix1 = new SimpleMatrix(new double[][]{{1}, {2}, {3}});
//        double v = matrix1.get(1, 0);
//        System.out.println(v);
//        SimpleMatrix mult = matrix1.mult(matrix);
//        System.out.println(mult);
//        bezier();
        cylinder();
    }

    private static void cylinder() {
        createFrame(new ParabolCylinderPanel());
    }

    private static void bezier() {
        createFrame(new BezierPanel());
    }

    private static void createFrame(GraphicPanels graphicPanels) {
        Frame frame = new Frame();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                frame.dispose();
            }
        });
        frame.add(graphicPanels.getPanel());
        try {
            UIManager.setLookAndFeel(
                    UIManager.getInstalledLookAndFeels()[1].getClassName());
//            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            SwingUtilities.updateComponentTreeUI(frame);
//            frame.pack();
        } catch (Exception e) {
            e.printStackTrace();
        }
        frame.pack();
        frame.setVisible(true);
    }
}
