package schedify;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class UserInputPagePriority extends Panels implements ActionListener{
    public JPanel panel, header, inputPanel1, inputPanel2, buttonPanel;
    public JLabel logoLabel, errorLabel;
    public JScrollPane inputScrollPane;
    public JButton backButton, clearButton, addButton, runButton;
    public Color gray, black, transparent, white;
    public JTextField inputField, processIDField, burstField, arrivalField, priorityField;
    public JComboBox priorityBox;
    
    public boolean validInput;
    public String id, arrivalTime, burstTime, priority;
    public int processCount=0;
    public int clickCount=0;    
    
    private Simulator simulator;
    StartPage start = new StartPage();
    public int algo = start.algo;
    
    public UserInputPagePriority(Simulator simulator){
        this.simulator = simulator;
    }
    
    @Override
    public void showUIComponents(){
        
        archivoblack = importFont("archivoblack");
        archivonarrow = importFont("archivonarrow");
        setLayout(new FlowLayout());
        
        gray = Color.LIGHT_GRAY;
        black = Color.BLACK;
        transparent = new Color(0,0,0,0);
        white = Color.WHITE;
        
        header = new JPanel();
        header.setBorder(BorderFactory.createEmptyBorder(10,60,0,40));
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
        
        inputPanel1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0,0));
        inputPanel1.setPreferredSize(new Dimension(800, 230));
        inputPanel1.setOpaque(false);
        inputPanel1.setBorder(BorderFactory.createEmptyBorder(40,0,   10,0));
        
        inputPanel1.add(createPanel(gray, black, "PROCESS ID"));
        inputPanel1.add(createPanel(gray, black, "ARRIVAL TIME"));
        inputPanel1.add(createPanel(gray, black, "BURST TIME"));
        inputPanel1.add(createPanel(gray, black, "PRIORITY NUMBER"));
        inputPanel1.add(createPanel(gray, black, "CHOOSE PRIORITY"));
        
        processIDField = createInputField();
        burstField = createInputField();
        arrivalField = createInputField();
        priorityField = createInputField();
        
        String[] priorityLevel = {"Low #, High Priority", "High #, High Priority"};
        
        priorityBox = new JComboBox(priorityLevel);
        priorityBox.setPreferredSize(new Dimension(160, 40));
        priorityBox.setFocusable(true);
        priorityBox.setFont(archivoblack.deriveFont(12f));
        priorityBox.setBackground(Color.WHITE);
        priorityBox.setForeground(new Color(4, 3, 93));
        priorityBox.setBorder(null);
        priorityBox.addActionListener(this);
        
        inputPanel1.add(processIDField);
        inputPanel1.add(arrivalField);
        inputPanel1.add(burstField);
        inputPanel1.add(priorityField);
        inputPanel1.add(priorityBox);
        
        clearButton = createButton("CLEAR");
        addButton = createButton("ADD");
        runButton = createButton("RUN"); 
        runButton.setEnabled(false);
        
        clearButton.addActionListener(this);
        addButton.addActionListener(this);
        runButton.addActionListener(this); 
        
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 60, 0));
        buttonPanel.setPreferredSize(new Dimension (800, 70));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
        
        buttonPanel.add(clearButton);
        buttonPanel.add(addButton);
        buttonPanel.add(runButton);
        
        errorLabel = new JLabel("");
        errorLabel.setFont(archivonarrow.deriveFont(20f));
        errorLabel.setForeground(Color.WHITE);
        
        inputPanel1.add(buttonPanel);
        inputPanel1.add(errorLabel);
        
        inputPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        inputPanel2.setPreferredSize(new Dimension(800, 840));
        inputPanel2.setBackground(new Color(4, 3, 93));
        
        inputPanel2.add(createPanel(gray, black, "PROCESS ID"));
        inputPanel2.add(createPanel(gray, black, "ARRIVAL TIME"));
        inputPanel2.add(createPanel(gray, black, "BURST TIME"));
        inputPanel2.add(createPanel(gray, black, "PRIORITY NUMBER"));
        
        inputScrollPane = new JScrollPane(inputPanel2);
        inputScrollPane.setPreferredSize(new Dimension(820, 320));
        inputScrollPane.setBackground(new Color(4, 3, 93, 10));
        inputScrollPane.setFocusable(false);  
        inputScrollPane.setBorder(null);
        
        add(header);
        add(inputPanel1);
        add(inputScrollPane);
        
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
    
    public JPanel createPanel(Color background, Color foreground, String label){
        JPanel jpanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        JLabel jlabel = new JLabel();
        jpanel.setPreferredSize(new Dimension (160, 40));
        jpanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        jpanel.setBackground(background);
        jlabel.setForeground(foreground);
        jlabel.setText(label);
        jlabel.setVerticalAlignment(SwingConstants.CENTER);
        jlabel.setFont(archivoblack.deriveFont(14f));
        jpanel.add(jlabel, BorderLayout.CENTER);
        
        return jpanel;
    }
    
    public JTextField createInputField(){
        JTextField input = new JTextField();
        input.setPreferredSize(new Dimension(160,40));
        input.setBorder(BorderFactory.createLineBorder(Color.white, 1));
        input.setOpaque(false);
        input.setFont(archivoblack.deriveFont(14f));
        input.setForeground(Color.white);
        input.setHorizontalAlignment(SwingConstants.CENTER);
        input.setEditable(true);
        
        return input;
    }
    
    public Process createProcess(){
        id = processIDField.getText().toUpperCase();
        int AT = Integer.parseInt(arrivalField.getText());
        int BT = Integer.parseInt(burstField.getText());
        int P = Integer.parseInt(priorityField.getText());
        
        Process process = new Process(id, AT, BT, P);
        
        return process;
    }
    
    public boolean isNumber(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    
    public boolean validateInput(){
        
        try {
            String id = processIDField.getText();
            String arrivalTime = arrivalField.getText();
            String burstTime = burstField.getText();
            String priority = priorityField.getText();
            
            if (!(id.isEmpty() || id.isBlank()) && !(arrivalTime.isEmpty() || arrivalTime.isBlank()) && !(burstTime.isEmpty() || burstTime.isBlank()) && (Integer.parseInt(burstTime)>0)
                && !(priority.isEmpty() || priority.isBlank()) && (id.startsWith("P") || id.startsWith("p")) && Character.isDigit(id.charAt(1)) 
                && id.length()==2 && isNumber(arrivalTime) && isNumber(burstTime) && isNumber(priority)) {
            
                validInput = true;
            }
            else {
                validInput = false;
            }    
            
        } catch (Exception e) {
            
        }
        return validInput;
    }
    
    public boolean uniqueID(String id){
        ArrayList<Process> localProcessList = simulator.getProcesses();
        
        boolean valid = true;        
        for (Process i : localProcessList){
            if (id.toUpperCase().equals(i.id)){
                valid = false;
            }
        }
        
        return valid;
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
    
    public void clearOutputRow() {
        inputPanel2.removeAll();
        inputPanel2.add(createPanel(gray, black, "PROCESS ID"));
        inputPanel2.add(createPanel(gray, black, "ARRIVAL TIME"));
        inputPanel2.add(createPanel(gray, black, "BURST TIME"));
        inputPanel2.add(createPanel(gray, black, "PRIORITY NUMBER"));
        
        addButton.setEnabled(true);
        runButton.setEnabled(false);
        processCount = 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()== addButton && validateInput() && uniqueID(processIDField.getText())){
            inputPanel2.removeAll();
            repaint();
            inputPanel2.revalidate();
            inputPanel2.add(createPanel(gray, black, "PROCESS ID"));
            inputPanel2.add(createPanel(gray, black, "ARRIVAL TIME"));
            inputPanel2.add(createPanel(gray, black, "BURST TIME"));
            inputPanel2.add(createPanel(gray, black, "PRIORITY NUMBER"));
            repaint();
            errorLabel.setText("");
            
            Process process = createProcess();
            simulator.addProcess(process);
            processCount++;
            processIDField.setText("");
            arrivalField.setText("");
            burstField.setText("");
            priorityField.setText("");
            
            
            if (processCount >=3 && processCount <= 20){
                runButton.setEnabled(true);
            }
            
            System.out.println("Process Count: " + processCount + "\n");
            System.out.println("Clicked");
            
            for (int i=1; i<=processCount; i++){
                repaint();
                inputPanel2.add(outputRow(i-1));
                inputPanel2.repaint();
                inputPanel2.repaint();
                repaint();
            }
            repaint();
            
        }
        else if (e.getSource()==addButton && (!validateInput() || !uniqueID(processIDField.getText()))){
            errorLabel.setText("Invalid. Check your inputs.");
            repaint(); 
        }
        else if (e.getSource()==runButton){
            
        }
        else if (e.getSource()==clearButton){
            simulator.clearProcessList();
            inputPanel2.removeAll();
            repaint();
            inputPanel2.revalidate();
            inputPanel2.add(createPanel(gray, black, "PROCESS ID"));
            inputPanel2.add(createPanel(gray, black, "ARRIVAL TIME"));
            inputPanel2.add(createPanel(gray, black, "BURST TIME"));
            inputPanel2.add(createPanel(gray, black, "PRIORITY NUMBER"));
            repaint();
            processCount = 0;
            errorLabel.setText("");
            runButton.setEnabled(false);
        }
        else if (e.getSource()==priorityBox){
            simulator.setPriorityLevel(priorityBox.getSelectedIndex());
            // 0 = low #, high priority
            // 1 = high #, high priority
            System.out.println("PrioLevel: " + simulator.getPriorityLevel());
        }
    }
    
}