package me.gumennyi.lab2.interactive;

import com.orsoncharts.Chart3DPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DemoPanel extends JPanel {

    private List<Chart3DPanel> chartPanels;

    public DemoPanel(LayoutManager layout) {
        super(layout);
        this.chartPanels = new ArrayList<Chart3DPanel>();
    }

    public Chart3DPanel getChartPanel() {
        if (this.chartPanels.isEmpty()) {
            return null;
        }
        return this.chartPanels.get(0);
    }

    public void setChartPanel(Chart3DPanel panel) {
        this.chartPanels.clear();
        this.chartPanels.add(panel);
    }

    public List<Chart3DPanel> getChartPanels() {
        return this.chartPanels;
    }

    public void addChartPanel(Chart3DPanel panel) {
        this.chartPanels.add(panel);
    }
}
