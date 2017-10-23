package me.gumennyi.lab2.interactive;

import me.gumennyi.lab2.generator.ParabolCylinderPoints;
import me.gumennyi.lab2.generator.PointsGenerator;


public class ParabolCylinderScatterFrame extends ScatterFrame {

    public ParabolCylinderScatterFrame(String title) {
        super(title);
    }

    public static void main(String[] args) {
        new ParabolCylinderScatterFrame("Parabol Cylinder");
    }

    @Override
    protected PointsGenerator getPointsGenerator() {
        return new ParabolCylinderPoints(3, 400);
    }
}
