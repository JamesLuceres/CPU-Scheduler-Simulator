package schedify;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class TextInputPagePriority extends Panels implements ActionListener{
    
    private JPanel header, processPanel, buttonPanel, panel;
    private JLabel logoLabel;
    public JButton backButton, textButton, clearButton, runButton;
    public JComboBox priorityBox;
    public JScrollPane processScrollPane;
    public int processCount;
    public Color gray, black, transparent, white;
    public File textFile;
    
    public ArrayList<String> processDetails = new ArrayList<>();
    
    private Simulator simulator;
    
    public TextInputPagePriority(Simulator simulator){
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
        
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setPreferredSize(new Dimension (800, 100));
        buttonPanel.setOpaque(false);
        
        clearButton = createButton("CLEAR");
        textButton = createButton("TXT FILE");
        textButton.setPreferredSize(new Dimension(297,40));
        runButton = createButton("RUN"); 
        
        clearButton.addActionListener(this);
        textButton.addActionListener(this);
        runButton.addActionListener(this); 
        
        String[] priorityLevel = {"Low #, High Priority", "High #, High Priority"};
        
        priorityBox = new JComboBox(priorityLevel);
        priorityBox.setPreferredSize(new Dimension(160, 40));
        priorityBox.setFocusable(true);
        priorityBox.setFont(archivoblack.deriveFont(12f));
        priorityBox.setBackground(Color.WHITE);
        priorityBox.setForeground(new Color(4, 3, 93));
        priorityBox.setBorder(null);
        priorityBox.addActionListener(this);
        
        
        buttonPanel.add(clearButton);
        buttonPanel.add(textButton);
        buttonPanel.add(runButton);
        buttonPanel.add(priorityBox);
        
        processPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        processPanel.setPreferredSize(new Dimension(800, 840));
        processPanel.setBackground(new Color(4, 3, 93));
        
        processPanel.add(createPanel(gray, black, "PROCESS ID"));
        processPanel.add(createPanel(gray, black, "ARRIVAL TIME"));
        processPanel.add(createPanel(gray, black, "BURST TIME"));
        processPanel.add(createPanel(gray, black, "PRIORITY NUMBER"));
        
        processScrollPane = new JScrollPane(processPanel);
        processScrollPane.setPreferredSize(new Dimension(820, 440));
        processScrollPane.setBackground(new Color(4, 3, 93, 10));
        processScrollPane.setFocusable(true);
        processScrollPane.setBorder(null);
        
        add(header);
        add(buttonPanel);
        add(processScrollPane);
    }
    
    @Override
    public JButton createButton(String text) {
        archivoblack = importFont("archivoblack");
        JButton button = new JButton(text);
        button.setBackground(new Color(118,130,138));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(147, 40));
        button.setFont(archivoblack.deriveFont(Font.BOLD, 20));
        button.setFocusable(false);
        button.setBorderPainted(false);
        return button;
    }
    
    public JPanel createPanel(Color background, Color foreground, String label){
        JPanel jpanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        JLabel jlabel = new JLabel();
        jpanel.setPreferredSize(new Dimension (200, 40));
        jpanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        jpanel.setBackground(background);
        jlabel.setForeground(foreground);
        jlabel.setText(label);
        jlabel.setVerticalAlignment(SwingConstants.CENTER);
        jlabel.setFont(archivoblack.deriveFont(14f));
        jpanel.add(jlabel, BorderLayout.CENTER);
        
        return jpanel;
    }
    
    public JPanel outputRow(int processCount){
        ArrayList<Process> localProcessList = simulator.getProcesses();
        
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0,0));
        panel.setPreferredSize(new Dimension(800, 40));
        panel.setOpaque(false);
            
            System.out.println("Process ID: " + localProcessList.get(processCount).id);
            System.out.println("Arrival Time: " + localProcessList.get(processCount).arrivalTime);
            System.out.println("Burst Time: " + localProcessList.get(processCount).burstTime);
            System.out.println("Priority: " + localProcessList.get(processCount).priority + "\n");
            
            panel.add(createPanel(transparent, white, localProcessList.get(processCount).id));
            panel.add(createPanel(transparent, white, String.valueOf(localProcessList.get(processCount).arrivalTime)));
            panel.add(createPanel(transparent, white, String.valueOf(localProcessList.get(processCount).burstTime)));
            panel.add(createPanel(transparent, white, String.valueOf(localProcessList.get(processCount).priority)));
        
        return panel;
    }
    
    public File uploadFile() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            System.out.println("File selected: " + selectedFile.getAbsolutePath());
            return selectedFile;
        } else {
            System.out.println("File selection cancelled.");
            return null;
        }
    }
    
     public void readFile(File file) {
         
        if (file == null) {
            System.out.println("No file to read.");
            return;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                processDetails.add(line.trim());
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        
    }
     
    public void createProcess(){
        for (String i : processDetails){
            System.out.println(i);
            StringTokenizer st = new StringTokenizer(i, ",");
            if (st.hasMoreTokens()) {
                processCount++;
                String processID = st.nextToken().trim();
                int arrivalTime = Integer.parseInt(st.nextToken().trim());
                int burstTime = Integer.parseInt(st.nextToken().trim());
                int priority = Integer.parseInt(st.nextToken().trim());
                
                Process process = new Process(processID, arrivalTime, burstTime, priority);
                simulator.addProcess(process);
            }
            
        }
    }  
    
    public void clearOutputRow() {
        processPanel.removeAll();
        processPanel.add(createPanel(gray, black, "PROCESS ID"));
        processPanel.add(createPanel(gray, black, "ARRIVAL TIME"));
        processPanel.add(createPanel(gray, black, "BURST TIME"));
        processPanel.add(createPanel(gray, black, "PRIORITY NUMBER"));
        
        textButton.setEnabled(true);
        runButton.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==textButton){
            processDetails.clear();
            processCount = 0;
       
            textFile = uploadFile();
            if (textFile == null) {
                return;
            }
            System.out.println("Successfully uploaded: " + textFile);
            readFile(textFile);
            
            createProcess();
            
            processPanel.removeAll();
            repaint();
            processPanel.revalidate();
            processPanel.add(createPanel(gray, black, "PROCESS ID"));
            processPanel.add(createPanel(gray, black, "ARRIVAL TIME"));
            processPanel.add(createPanel(gray, black, "BURST TIME"));
            processPanel.add(createPanel(gray, black, "PRIORITY NUMBER"));
            repaint();
            System.out.println("simulator.processList: " + processCount);
            for (int i=1; i<=processCount; i++){
                repaint();
                //simulator.processList.add(createProcess(i));
                processPanel.add(outputRow(i-1));
                repaint();
            }
            repaint();
            textButton.setEnabled(false);      
            runButton.setEnabled(true);
        }
        else if (e.getSource()==clearButton){
            processDetails.clear();
            simulator.clearProcessList();
            processPanel.removeAll();
            repaint();
            processPanel.revalidate();
            processPanel.add(createPanel(gray, black, "PROCESS ID"));
            processPanel.add(createPanel(gray, black, "ARRIVAL TIME"));
            processPanel.add(createPanel(gray, black, "BURST TIME"));
            processPanel.add(createPanel(gray, black, "PRIORITY NUMBER"));
            repaint();
            processCount = 0;
            textButton.setEnabled(true);
            runButton.setEnabled(false);
        }
        else if (e.getSource()==priorityBox){
            simulator.setPriorityLevel(priorityBox.getSelectedIndex());
        }
    }
}
