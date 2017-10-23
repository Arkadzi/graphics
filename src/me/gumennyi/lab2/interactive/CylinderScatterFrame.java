package me.gumennyi.lab2.interactive;

import me.gumennyi.lab2.generator.CylinderPoints;
import me.gumennyi.lab2.generator.PointsGenerator;


public class CylinderScatterFrame extends ScatterFrame {

    public CylinderScatterFrame(String title) {
        super(title);
    }

    public static void main(String[] args) {
        new CylinderScatterFrame("Cylinder");
    }

    @Override
    protected PointsGenerator getPointsGenerator() {
        return new CylinderPoints(3, 400);
    }
}
