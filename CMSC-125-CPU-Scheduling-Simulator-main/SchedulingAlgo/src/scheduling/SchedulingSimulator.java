package scheduling;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;

public class SchedulingSimulator extends JPanel {
	
    private ArrayList<Process> processList = new ArrayList<>();
    private JTextArea outputArea;
    private GanttChartPanel ganttChartPanel;
    private JTable processTable;
    private DefaultTableModel tableModel;
    private ArrayList<String> ganttChartLabels = new ArrayList<>();
    private ArrayList<Integer> ganttChartTimes = new ArrayList<>();
    private int currentTime = 0;
	
    public SchedulingSimulator() {
        setLayout(new BorderLayout());
		
        // panel for user inputs
        JPanel inputPanel = new JPanel(new GridLayout(10, 2));
		
        JTextField txtProcessID = new JTextField();
        JTextField txtArrivalTime = new JTextField();
        JTextField txtBurstTime = new JTextField();
        JTextField txtQuantumTime = new JTextField();
        JTextField txtPriority = new JTextField();
		
        JComboBox<String> algorithmSelection = new JComboBox<>(new String[]{
            "First Come First Serve",
            "Round Robin",
            "Priority (Non-preemptive)",
            "Priority (Preemptive)",
            "Shortest Job First (Preemptive)",
            "Shortest Job First (Non-preemptive)"
        });
		
        JButton btnAddProcess = new JButton("Add Process");
        JButton btnStartSimulation = new JButton("Start Simulation");
		
        inputPanel.add(new JLabel("Process ID:"));
        inputPanel.add(txtProcessID);
        inputPanel.add(new JLabel("Arrival Time:"));
        inputPanel.add(txtArrivalTime);
        inputPanel.add(new JLabel("Burst Time:"));
        inputPanel.add(txtBurstTime);
        inputPanel.add(new JLabel("Quantum Time (if Round Robin):"));
        inputPanel.add(txtQuantumTime);
        inputPanel.add(new JLabel("Priority (if Priority-based):"));
        inputPanel.add(txtPriority);
        inputPanel.add(new JLabel("Select Algorithm:"));
        inputPanel.add(algorithmSelection);
        inputPanel.add(btnAddProcess);
        inputPanel.add(btnStartSimulation);
		
        add(inputPanel, BorderLayout.NORTH);
		
        outputArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);
		
        ganttChartPanel = new GanttChartPanel(ganttChartLabels, ganttChartTimes);
        ganttChartPanel.setPreferredSize(new Dimension(600, 100));
        add(ganttChartPanel, BorderLayout.SOUTH);
		
        String[] columnNames = {"Process ID", "Arrival Time", "Burst Time", "Priority", "Completion Time", "Turnaround Time", "Waiting Time"};
        tableModel = new DefaultTableModel(columnNames, 0);
        processTable = new JTable(tableModel);
        add(new JScrollPane(processTable), BorderLayout.EAST);
		
        btnAddProcess.addActionListener(e -> {
        String processID = txtProcessID.getText().trim();
        int arrivalTime = Integer.parseInt(txtArrivalTime.getText().trim());
        int burstTime = Integer.parseInt(txtBurstTime.getText().trim());
        int priority = txtPriority.getText().trim().isEmpty() ? 0 : Integer.parseInt(txtPriority.getText().trim());

        processList.add(new Process(processID, arrivalTime, burstTime, priority));
        outputArea.append("Process Added: " + processID + " | Arrival: " + arrivalTime
                + " | Burst: " + burstTime + " | Priority: " + priority + "\n");
        });
		
        btnStartSimulation.addActionListener(e -> startSimulation(algorithmSelection.getSelectedItem().toString()));
    }
	
    private void startSimulation(String algorithm) {
        if (processList.isEmpty()) {
            outputArea.append("No processes to simulate.\n");
            return;
        }

        switch (algorithm) {
            case "First Come First Serve":
                new FCFS(processList, outputArea, ganttChartPanel, tableModel, ganttChartLabels, ganttChartTimes).startSimulation();
                break;
            case "Round Robin":
                int quantumTime = Integer.parseInt(JOptionPane.showInputDialog("Enter Quantum Time: "));
                new RoundRobin(processList, outputArea, ganttChartPanel, tableModel, ganttChartLabels, ganttChartTimes).startSimulation(quantumTime);
                break;
            case "Priority (Non-preemptive)":
                new PriorityNonPreemptive(processList, outputArea, ganttChartPanel, tableModel, ganttChartLabels, ganttChartTimes).startSimulation();
                break;
            case "Priority (Preemptive)":
                new PriorityPreemptive(processList, outputArea, ganttChartPanel, tableModel, ganttChartLabels, ganttChartTimes).startSimulation();
                break;
            case "Shortest Job First (Preemptive)":
                new SJFPreemptive(processList, outputArea, ganttChartPanel, tableModel, ganttChartLabels, ganttChartTimes).startSimulation();
                break;
            case "Shortest Job First (Non-preemptive)":
                new SJFNonPreemptive(processList, outputArea, ganttChartPanel, tableModel, ganttChartLabels, ganttChartTimes).startSimulation();
                break;
        }
    }
}