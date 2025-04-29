package scheduling;

import javax.swing.*;
import java.awt.*;
import java.util.*;

class GanttChartPanel extends JPanel {
    private ArrayList<String> ganttChartLabels;
    private ArrayList<Integer> ganttChartTimes;
    private int currentTime;

    public GanttChartPanel(ArrayList<String> labels, ArrayList<Integer> times) {
        this.ganttChartLabels = labels;
        this.ganttChartTimes = times;
        this.currentTime = 0;
    }

    public void setCurrentTime(int time) {
        this.currentTime = time;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 10;

        for (int i = 0; i < ganttChartLabels.size(); i++) {
            String process = ganttChartLabels.get(i);
            g.setColor(process.equals("Idle") ? Color.LIGHT_GRAY : Color.GREEN);
            g.fillRect(x, 10, 50, 40);

            g.setColor(Color.BLACK);
            g.drawRect(x, 10, 50, 40);
            g.drawString(process, x + 15, 35);
            g.drawString(String.valueOf(ganttChartTimes.get(i)), x, 60);

            if (i == ganttChartLabels.size() - 1) {
                g.drawString(String.valueOf(currentTime), x + 50, 60);
            }

            x += 50;
        }

        g.drawString("Time: " + currentTime, 10, 80);
    }
}
