package scheduling;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.*;
import javax.swing.Timer;

public class RoundRobin {
    private ArrayList<Process> processList;
    private JTextArea outputArea;
    private GanttChartPanel ganttChartPanel;
    private DefaultTableModel tableModel;
    private ArrayList<String> ganttChartLabels;
    private ArrayList<Integer> ganttChartTimes;
    private int time = 0;
    private int quantumCounter = 0;
    private Process currentProcess = null;

    public RoundRobin(ArrayList<Process> processList, JTextArea outputArea, GanttChartPanel ganttChartPanel,
                      DefaultTableModel tableModel, ArrayList<String> ganttChartLabels, ArrayList<Integer> ganttChartTimes) {
        this.processList = processList;
        this.outputArea = outputArea;
        this.ganttChartPanel = ganttChartPanel;
        this.tableModel = tableModel;
        this.ganttChartLabels = ganttChartLabels;
        this.ganttChartTimes = ganttChartTimes;
    }

    public void startSimulation(int quantumTime) {
        outputArea.append("Starting Round Robin Simulation with Quantum Time: " + quantumTime + "\n");

        processList.sort(Comparator.comparingInt(p -> p.arrivalTime));
        Queue<Process> readyQueue = new LinkedList<>();

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
		// add new processes to the ready queue when they arrive
                while (!processList.isEmpty() && processList.get(0).arrivalTime == time) {
                    readyQueue.offer(processList.get(0));
                    processList.remove(0);
                }
                
		// check if there's a current process still executing
                if (currentProcess == null || quantumCounter == quantumTime || currentProcess.remainingTime == 0) {
                    // Add the finished process back to the queue if it's incomplete
                    if (currentProcess != null && currentProcess.remainingTime > 0) {
                        readyQueue.offer(currentProcess);
                    }

                    // get a new process only if needed
                    if (!readyQueue.isEmpty()) {
                        currentProcess = readyQueue.poll();
                        quantumCounter = 0;

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
                    quantumCounter++;
					
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
                        quantumCounter = 0;
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
