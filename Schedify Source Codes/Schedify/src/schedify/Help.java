package schedify;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;


public class Help extends Panels{
    
    public JPanel header, footer, instructionsPanel;
    public JLabel titleLabel, logoLabel;
    public JButton backButton;
    public BufferedImage bg, img, logo;
    public Icon logoIcon;
    public Font archivoblack, archivonarrow, font;
    public String instructions;
    public JTextPane instructionsText;
    private boolean componentsInitialized = false; 

    public Help() {
        if (!componentsInitialized) {
            showUIComponents();
            componentsInitialized = true;
        }
    }
    
    @Override
    public final void showUIComponents(){ 
        archivoblack = importFont("archivoblack");
        archivonarrow = importFont("archivonarrow");
        
        setLayout(new BorderLayout());
        
        header = new JPanel(new FlowLayout(FlowLayout.LEADING, 0,0));
        header.setPreferredSize(new Dimension(950, 150));
        header.setBackground(new Color(0,0,0,0));
        
        titleLabel = new JLabel("Help");
        titleLabel.setFont(archivoblack.deriveFont(70f));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 130, 0, 135));
        
        logo = getImg("/img/logo_small.png");
        logoIcon = new ImageIcon(logo);
        
        logoLabel = new JLabel();
        logoLabel.setIcon(logoIcon);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(55, 180, 0, 0));
        
        header.add(titleLabel);
        header.add(logoLabel);
        
        footer = new JPanel(new BorderLayout());
        footer.setPreferredSize(new Dimension(950, 90));
        footer.setBackground(new Color(0,0,0,0));
        footer.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 80));
        
        backButton = createButton("BACK");
        
        footer.add(backButton, BorderLayout.EAST);
        
        instructions = "Supported Scheduling Algorithms:\n "
                + "   1. First Come, First Served (FCFS)\n"
                + "   2. Round Robin (RR)\n"
                + "   3. Shortest Job First (SJF) – Non-Preemptive\n"
                + "   4. Shortest Job First (SJF) – Preemptive (Shortest Remaining Time First, SRTF)\n"
                + "   5. Priority Scheduling – Non-Preemptive\n"
                + "   6. Priority Scheduling – Preemptive\n\n"
                + "How to Use the Simulator:\n"
                + "   1. Select Type of Input (Random, User-Input, Text File).\n"
                + "   2. Select a Scheduling Algorithm.\n"
                + "   3. Provide the necessary information: Process ID, Arrival Time, Burst Time, and Priority (if applicable).\n"
                + "   4. Run the simulation.\n"
                + "   5. View the Gantt Chart to observe the scheduling process.\n"
                + "   6. Analyze results: Waiting Time, Turnaround Time, Completion Time, AVG Waiting Time, and AVG Turnaround Time\n";
        
        instructionsText = new JTextPane();
        instructionsText.setText(instructions);
        instructionsText.setPreferredSize(new Dimension(620, 500));
        instructionsText.setForeground(Color.WHITE);
        instructionsText.setBackground(new Color(0,0,0,0));
        instructionsText.setFont(archivonarrow.deriveFont(19f));
        instructionsText.setEditable(false);
        instructionsText.setOpaque(false);
        instructionsText.setFocusable(false);
        
        instructionsPanel = new JPanel(new BorderLayout());
        instructionsPanel.add(instructionsText, BorderLayout.WEST);
        instructionsPanel.setBackground(new Color(0,0,0,0));
        instructionsPanel.setBorder(BorderFactory.createEmptyBorder(30,130,0,0));
        
                
        add(header, BorderLayout.NORTH);
        add(instructionsPanel);
        add(footer, BorderLayout.SOUTH);
    }
   
}
