package io.github.yuliya.graphics.lab2.interactive;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ExitOnClose extends WindowAdapter {

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

}
