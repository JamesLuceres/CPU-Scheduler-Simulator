package schedify;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

class GanttChartPanel extends JPanel {
    private ArrayList<String> ganttChartLabels;
    private ArrayList<Integer> ganttChartTimes;
    private int currentTime;
    private Font font, archivoblack;
    private HashMap<String, Color> processColors;

    public GanttChartPanel(ArrayList<String> labels, ArrayList<Integer> times) {
        this.ganttChartLabels = labels;
        this.ganttChartTimes = times;
        this.currentTime = 0;
        this.archivoblack = importFont("archivoblack");
        this.processColors = new HashMap<>();
    }

    public void setCurrentTime(int time) {
        this.currentTime = time;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int x = 10;

        // set font
        if (archivoblack != null) {
            g.setFont(archivoblack.deriveFont(Font.BOLD, 12f));
        }
        
        for (int i = 0; i < ganttChartLabels.size(); i++) {
            String process = ganttChartLabels.get(i);
            
            // assign a unique color if the process is not "Idle"
            if (!process.equals("Idle")) {
                processColors.putIfAbsent(process, getRandomColor());
                g.setColor(processColors.get(process));
            } else {
                g.setColor(Color.LIGHT_GRAY);
            }
            
            // draw process block
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
    
    private Color getRandomColor() {
        Random rand = new Random();
        int r, g, b;
        do {
            r = rand.nextInt(156) + 100;
            g = rand.nextInt(156) + 100;
            b = rand.nextInt(156) + 100;
        } while ((r + g + b) / 3 < 170);
        
        return new Color(r, g, b);
    }
    
    public Font importFont(String filePath){
        try {
            InputStream i = getClass().getResourceAsStream("/font/" + filePath + ".ttf");
            try {
                font = Font.createFont(Font.TRUETYPE_FONT, i);
            } catch (FontFormatException ex) {
                System.out.println("no font");
            }
            
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
            
        } catch (IOException ex) {
           ex.printStackTrace();
        }
        return font;
    }
    
}
