package io.github.solomkinmv.graphics.lab2;

import io.github.solomkinmv.graphics.lab2.panels.BezierPanel;
import io.github.solomkinmv.graphics.lab2.panels.CylinderPanel;
import io.github.solomkinmv.graphics.lab2.panels.GraphicPanels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Application {
    public static void main(String[] args) {

        bezier();
        cylinder();
    }

    private static void cylinder() {
        createFrame(new CylinderPanel());
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
