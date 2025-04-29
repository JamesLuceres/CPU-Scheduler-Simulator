package schedify;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class RandomInputPageRR extends Panels implements ActionListener{
    
    private JPanel header, processPanel, buttonPanel, panel, randomPanel;
    private JLabel logoLabel;
    public JButton backButton, randomButton, clearButton, runButton;
    public JScrollPane processScrollPane;
    public int r, processCount, quantum;
    public Color gray, black, transparent, white;
    
    private Simulator simulator;
    
    public RandomInputPageRR(Simulator simulator){
        this.simulator = simulator;
    }
    
    @Override
    public void showUIComponents(){
        
        gray = Color.LIGHT_GRAY;
        black = Color.BLACK;
        transparent = new Color(0,0,0,0);
        white = Color.WHITE;
        
        header = new JPanel();
        header.setBorder(BorderFactory.createEmptyBorder(10,60,0,50));
        header.setOpaque(false);
        header.setPreferredSize(new Dimension(950, 100));
        
        logo = getImg("/img/logo_small.png");
        logoIcon = new ImageIcon(logo);
        
        logoLabel = new JLabel();
        logoLabel.setIcon(logoIcon);
        logoLabel.setBorder(BorderFactory.createEmptyBorder(0, 200, 0, 120));
        
        backButton = createButton("BACK");
        backButton.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
        header.add(logoLabel);
        header.add(backButton);   
        
        randomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0,0));
        randomPanel.setPreferredSize(new Dimension(440, 80));
        randomPanel.setOpaque(false);
        
        randomPanel.add(createPanel(gray, black, white, "NO. OF PROCESS", 200));      
        randomPanel.add(createPanel(transparent, transparent, transparent, "", 40));     
        randomPanel.add(createPanel(gray, black, white, "QUANTUM NUMBER", 200));
        
        randomPanel.add(createPanel(transparent, transparent, white, "", 200));  
        randomPanel.add(createPanel(transparent, transparent, transparent, "", 40));     
        randomPanel.add(createPanel(transparent, transparent, white, "", 200));
        
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setPreferredSize(new Dimension (800, 100));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20,0,40,0));
        
        clearButton = createButton("CLEAR");
        randomButton = createButton("RANDOM INPUT");
        randomButton.setPreferredSize(new Dimension(300,40));
        runButton = createButton("RUN"); 
        runButton.setEnabled(false);
        
        clearButton.addActionListener(this);
        randomButton.addActionListener(this);
        runButton.addActionListener(this); 
        
        buttonPanel.add(clearButton);
        buttonPanel.add(randomButton);
        buttonPanel.add(runButton);
        
        processPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        processPanel.setPreferredSize(new Dimension(720, 840));
        processPanel.setBackground(new Color(4, 3, 93));
        
        processPanel.add(createPanel(gray, black, "PROCESS ID"));
        processPanel.add(createPanel(gray, black, "ARRIVAL TIME"));
        processPanel.add(createPanel(gray, black, "BURST TIME"));        
        
        processScrollPane = new JScrollPane(processPanel);
        processScrollPane.setPreferredSize(new Dimension(740, 360));
        processScrollPane.setBackground(new Color(4, 3, 93, 10));
        processScrollPane.setFocusable(true);
        processScrollPane.setBorder(null);
        
        add(header);
        add(randomPanel);
        add(buttonPanel);
        add(processScrollPane);
    }
    
    public int generateRandom(){
        Random rand = new Random(); 
        r = rand.nextInt(3, 21);
        return r;
    }
    
    public int generateRandom(int a, int b){
        Random rand = new Random();
        r = rand.nextInt(a, b);
        return r;
    }
    
    @Override
    public JButton createButton(String text) {
        archivoblack = importFont("archivoblack");
        JButton button = new JButton(text);
        button.setBackground(new Color(118,130,138));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(200, 40));
        button.setFont(archivoblack.deriveFont(Font.BOLD, 20));
        button.setFocusable(false);
        button.setBorderPainted(false);
        return button;
    }
    
    public JPanel createPanel(Color background, Color foreground, Color border, String label, int width){
        JPanel jpanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        JLabel jlabel = new JLabel();
        jpanel.setPreferredSize(new Dimension (width, 40));
        jpanel.setBorder(BorderFactory.createLineBorder(border, 1));
        jpanel.setBackground(background);
        jlabel.setForeground(foreground);
        jlabel.setText(label);
        jlabel.setVerticalAlignment(SwingConstants.CENTER);
        jlabel.setFont(archivoblack.deriveFont(14f));
        jpanel.add(jlabel, BorderLayout.CENTER);
        
        return jpanel;
    }
    
    public JPanel createPanel(Color background, Color foreground, String label){
        JPanel jpanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        JLabel jlabel = new JLabel();
        jpanel.setPreferredSize(new Dimension (240, 40));
        jpanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        jpanel.setBackground(background);
        jlabel.setForeground(foreground);
        jlabel.setText(label);
        jlabel.setVerticalAlignment(SwingConstants.CENTER);
        jlabel.setFont(archivoblack.deriveFont(14f));
        jpanel.add(jlabel, BorderLayout.CENTER);
        
        return jpanel;
    }
    
    public Process createProcess(int i){
        String id = "P" + i;
        int AT = generateRandom(0, 31);
        int BT = generateRandom(1, 31);
        int P = 1;
        
        Process process = new Process(id, AT, BT, P);
        
        return process;
    }
    
    public JPanel outputRow(int processCount){
        ArrayList<Process> localProcessList = simulator.getProcesses();
        
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0,0));
        panel.setPreferredSize(new Dimension(720, 40));
        panel.setOpaque(false);
            
            System.out.println("Process ID: " + localProcessList.get(processCount).id);
            System.out.println("Arrival Time: " + localProcessList.get(processCount).arrivalTime);
            System.out.println("Burst Time: " + localProcessList.get(processCount).burstTime);
            
            panel.add(createPanel(transparent, white, localProcessList.get(processCount).id));
            panel.add(createPanel(transparent, white, String.valueOf(localProcessList.get(processCount).arrivalTime)));
            panel.add(createPanel(transparent, white, String.valueOf(localProcessList.get(processCount).burstTime)));
        
        return panel;
    } 
    
    public void clearOutputRow() {
        processPanel.removeAll();
        processPanel.add(createPanel(gray, black, "PROCESS ID"));
        processPanel.add(createPanel(gray, black, "ARRIVAL TIME"));
        processPanel.add(createPanel(gray, black, "BURST TIME"));
        
        randomButton.setEnabled(true);
        runButton.setEnabled(false);
        processCount = 0;
        quantum = 0;
        
        randomPanel.removeAll();
        repaint();
        randomPanel.revalidate();
    
        randomPanel.add(createPanel(gray, black, white, "NO. OF PROCESS", 200));      
        randomPanel.add(createPanel(transparent, transparent, transparent, "", 40));     
        randomPanel.add(createPanel(gray, black, white, "QUANTUM NUMBER", 200));
    
        randomPanel.add(createPanel(transparent, transparent, white, "", 200));  
        randomPanel.add(createPanel(transparent, transparent, transparent, "", 40));     
        randomPanel.add(createPanel(transparent, transparent, white, "", 200));
    
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==randomButton){
                       
            processCount = generateRandom();
            quantum = generateRandom(1,11);
            simulator.setQuantumTime(quantum);
            
            processPanel.removeAll();
            repaint();
            processPanel.revalidate();
            processPanel.add(createPanel(gray, black, "PROCESS ID"));
            processPanel.add(createPanel(gray, black, "ARRIVAL TIME"));
            processPanel.add(createPanel(gray, black, "BURST TIME"));
 
            repaint();
            
            randomPanel.removeAll();
            repaint();
            randomPanel.revalidate();
            randomPanel.add(createPanel(gray, black, white, "NO. OF PROCESS", 200));      
            randomPanel.add(createPanel(transparent, transparent, transparent, "", 40));     
            randomPanel.add(createPanel(gray, black, white, "QUANTUM NUMBER", 200));
            
            randomPanel.add(createPanel(transparent, white, white, String.valueOf(processCount), 200));  
            randomPanel.add(createPanel(transparent, transparent, transparent, "", 40));     
            randomPanel.add(createPanel(transparent, white, white, String.valueOf(quantum), 200));
            randomPanel.repaint();
           
            repaint();
            
            System.out.println("Process: " + processCount);
            System.out.println("Quantum Number: " + quantum);
            
            for (int i=1; i<=processCount; i++){
                repaint();
                Process process = createProcess(i);
                simulator.addProcess(process);
                processPanel.add(outputRow(i-1));
                repaint();
            }
            repaint();
            randomButton.setEnabled(false);
            runButton.setEnabled(true);
        }
        else if (e.getSource()==clearButton){
            simulator.clearProcessList();
            processPanel.removeAll();
            repaint();
            processPanel.revalidate();
            processPanel.add(createPanel(gray, black, "PROCESS ID"));
            processPanel.add(createPanel(gray, black, "ARRIVAL TIME"));
            processPanel.add(createPanel(gray, black, "BURST TIME"));

            repaint();
            
            randomPanel.removeAll();
            repaint();
            randomPanel.revalidate();
            randomPanel.add(createPanel(gray, black, white, "NO. OF PROCESS", 200));      
            randomPanel.add(createPanel(transparent, transparent, transparent, "", 40));     
            randomPanel.add(createPanel(gray, black, white, "QUANTUM NUMBER", 200));
            
            randomPanel.add(createPanel(transparent, transparent, white, "", 200));  
            randomPanel.add(createPanel(transparent, transparent, transparent, "", 40));     
            randomPanel.add(createPanel(transparent, transparent, white, "", 200));
            
            repaint();
            
            processCount = 0;
            quantum = 0;
            randomButton.setEnabled(true);
            runButton.setEnabled(false);
        }
    }
}
