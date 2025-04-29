package scheduling;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;

public class PriorityPreemptive {
    private ArrayList<Process> processList;
    private JTextArea outputArea;
    private GanttChartPanel ganttChartPanel;
    private DefaultTableModel tableModel;
    private ArrayList<String> ganttChartLabels;
    private ArrayList<Integer> ganttChartTimes;
    private int time = 0;
    private Process currentProcess = null;

    public PriorityPreemptive(ArrayList<Process> processList, JTextArea outputArea, GanttChartPanel ganttChartPanel, DefaultTableModel tableModel, ArrayList<String> ganttChartLabels, ArrayList<Integer> ganttChartTimes) {
        this.processList = processList;
        this.outputArea = outputArea;
        this.ganttChartPanel = ganttChartPanel;
        this.tableModel = tableModel;
        this.ganttChartLabels = ganttChartLabels;
        this.ganttChartTimes = ganttChartTimes;
    }

    public void startSimulation() {
        outputArea.append("Starting Priority preemptive Simulation \n");
        
        processList.sort(Comparator.comparingInt(p -> p.arrivalTime));
        Queue<Process> readyQueue = new LinkedList<>();
        
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // add new processes to the ready queue when they arrive
                while (!processList.isEmpty() && processList.get(0).arrivalTime == time) {
                    Process arrivingProcess = processList.get(0);
                    readyQueue.offer(arrivingProcess);
                    processList.remove(0);
                }
                
                // check if the next process has higher priority
                if (!readyQueue.isEmpty()) {
                    Process highestPriorityProcess = Collections.min(readyQueue, Comparator.comparingInt(p -> p.priority));
                    
                    if (currentProcess == null || highestPriorityProcess.priority < currentProcess.priority) {
                        if (currentProcess != null && currentProcess.remainingTime > 0) {
                            // pre-empt the current process and put it back in the queue
                            readyQueue.offer(currentProcess);
                        }
                        
                        // get new process
                        currentProcess = highestPriorityProcess;
                        readyQueue.remove(currentProcess);
                        
                        if (ganttChartLabels.isEmpty() || !ganttChartLabels.get(ganttChartLabels.size() - 1).equals(currentProcess.id)) {
                            ganttChartLabels.add(currentProcess.id);
                            ganttChartTimes.add(time);
                        }
                    }
                }
                
                // process execution logic
                if (currentProcess != null) {
                    outputArea.append("Time " + time + ": Process " + currentProcess.id + " executing\n");
                    currentProcess.remainingTime--;
					
                    // Process completion logic
                    if (currentProcess.remainingTime == 0) {
                        currentProcess.completionTime = time + 1;
                        currentProcess.turnaroundTime = currentProcess.completionTime - currentProcess.arrivalTime;
                        currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;

                        tableModel.addRow(new Object[]{
                            currentProcess.id, currentProcess.arrivalTime, currentProcess.burstTime,
                            currentProcess.priority, currentProcess.completionTime,
                            currentProcess.turnaroundTime, currentProcess.waitingTime
                        });

                        currentProcess = null;
                    }
                } else {
                    outputArea.append("Time " + time + ": Idle\n");
                    if (ganttChartLabels.isEmpty() || !ganttChartLabels.get(ganttChartLabels.size() - 1).equals("Idle")) {
                        ganttChartLabels.add("Idle");
                        ganttChartTimes.add(time);
                    }
                }
                
                time++;
                ganttChartPanel.setCurrentTime(time);
                ganttChartPanel.repaint();
                
                if (processList.isEmpty() && readyQueue.isEmpty() && currentProcess == null) {
                    ((Timer) e.getSource()).stop();
                    outputArea.append("Simulation complete!\n");
                }
            }
        });
        timer.start();
    }
}
