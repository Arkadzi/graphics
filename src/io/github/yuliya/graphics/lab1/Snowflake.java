package io.github.yuliya.graphics.lab1;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Snowflake extends Canvas {
    private static int size = 800, margin = 200;

    public static void main(String[] args) {
        final Frame fr = new Frame();
        fr.setSize(size, size);
        fr.add(new Snowflake());
        fr.setVisible(true);
        fr.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                fr.dispose();
            }
        });

    }

    public void paint(Graphics g) {
        setBackground(Color.white);
        g.setColor(Color.black);

        drawKochSnowflake(g,
                          new Point(margin, margin),
                          size - 2 * margin,
                          4);
    }

    private void drawKochSnowflake(Graphics g, Point a, int len, int n) {
        Point p0 = a;
        Point p1 = new Point(p0.x + len, p0.y);
        Point p2 = new Point(p1.x, p1.y + len);
        Point p3 = new Point(p2.x - len, p2.y);

        drawKochLine(g, p0, p1, n);
        drawKochLine(g, p1, p2, n);
        drawKochLine(g, p2, p3, n);
        drawKochLine(g, p3, p0, n);
    }

    private void drawKochLine(Graphics g, Point a, Point b, int n) {
        assert n >= 0;

        if (n == 0) {
            g.drawLine(a.x, a.y, b.x, b.y);
            return;
        }

        boolean horizontal = a.y == b.y;
        if (!horizontal && a.y < b.y) {
            Point c = a;
            a = b;
            b = c;
        }

        double length = horizontal ? b.x - a.x : a.y - b.y;

        int partLen = (int) (length / 4);

        Point p0 = a;
        Point p1 = horizontal ? new Point(p0.x + partLen, p0.y) : new Point(p0.x, p0.y - partLen);
        Point p2 = horizontal ? new Point(p1.x, p1.y - partLen) : new Point(p1.x - partLen, p1.y);
        Point p3 = horizontal ? new Point(p2.x + partLen, p2.y) : new Point(p2.x, p2.y - partLen);
        Point p4 = horizontal ? new Point(p3.x, p3.y + partLen) : new Point(p3.x + partLen, p3.y);
        Point p5 = horizontal ? new Point(p4.x, p4.y + partLen) : new Point(p4.x + partLen, p4.y);
        Point p6 = horizontal ? new Point(p5.x + partLen, p5.y) : new Point(p5.x, p5.y - partLen);
        Point p7 = horizontal ? new Point(p6.x, p6.y - partLen) : new Point(p6.x - partLen, p6.y);
        Point p8 = b;

        n--;
        drawKochLine(g, p0, p1, n);
        drawKochLine(g, p1, p2, n);
        drawKochLine(g, p2, p3, n);
        drawKochLine(g, p3, p4, n);
        drawKochLine(g, p4, p5, n);
        drawKochLine(g, p5, p6, n);
        drawKochLine(g, p6, p7, n);
        drawKochLine(g, p7, p8, n);
    }
}