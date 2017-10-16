package io.github.solomkinmv.graphics.lab2;

import io.github.solomkinmv.graphics.lab2.figures.Cylinder;
import io.github.solomkinmv.graphics.lab2.interactive.ExitOnClose;

import java.awt.*;

public class Application {
    private static final int SIZE = 1000;

    public static void main(String[] args) {
        cylinder();
    }

    private static void cylinder() {
        final Frame fr = new Frame();
        fr.addWindowFocusListener(new ExitOnClose());
        fr.setSize(SIZE, SIZE);
        fr.add(new GraphicCanvas(Cylinder::new, SIZE, SIZE));
        fr.setVisible(true);
    }
}
