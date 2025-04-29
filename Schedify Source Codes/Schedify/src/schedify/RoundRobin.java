package schedify;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

class RoundRobin extends Panels {
    private ArrayList<Process> processList;
    private GanttChartPanel ganttChartPanel;
    private ArrayList<String> ganttChartLabels;
    private ArrayList<Integer> ganttChartTimes;
    private ArrayList<Integer> turnAroundTimes;
    private ArrayList<Integer> waitingTimes;
    private ArrayList<Process> completedProcess;
    private int time = 0;
    private int quantumCounter = 0;
    private Process currentProcess = null;
    private JPanel outputPanel, panel, cpuPanel, readyQueuePanel;
    public Color transparent, white, gray, black;
    public Timer timer;
    
    public RoundRobin(ArrayList<Process> processList, GanttChartPanel ganttChartPanel, ArrayList<String> ganttChartLabels, ArrayList<Integer> ganttChartTimes, JPanel outputPanel, JPanel cpuPanel, JPanel readyQueuePanel, Timer timer) {
        this.processList = processList;
        this.ganttChartPanel = ganttChartPanel;
        this.ganttChartLabels = ganttChartLabels;
        this.ganttChartTimes = ganttChartTimes;
        this.outputPanel = outputPanel;
        this.cpuPanel = cpuPanel;
        this.readyQueuePanel = readyQueuePanel;
        this.timer = timer;
        
        turnAroundTimes = new ArrayList<>();
        waitingTimes = new ArrayList<>();
        completedProcess = new ArrayList<>();
        
        gray = Color.GRAY;
        black = Color.BLACK;
        archivoblack = importFont("archivoblack");
    }
    
    public void startSimulation(int quantumTime) {
        System.out.println("Starting Round Robin Simulation with Quantum Time: " + quantumTime + "\n");

        processList.sort(Comparator.comparingInt(p -> p.arrivalTime));
        Queue<Process> readyQueue = new LinkedList<>();

        timer = new Timer(250, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
		// add new processes to the ready queue when they arrive
                updateCPUPanel(currentProcess);
                while (!processList.isEmpty() && processList.get(0).arrivalTime == time) {
                    readyQueue.offer(processList.get(0));
                    processList.remove(0);
                    updateReadyQueuePanel(readyQueue);
                }
                
		// check if there's a current process still executing
                if (currentProcess == null || quantumCounter == quantumTime || currentProcess.remainingTime == 0) {
                    // Add the finished process back to the queue if it's incomplete
                    if (currentProcess != null && currentProcess.remainingTime > 0) {
                        readyQueue.offer(currentProcess);
                        updateReadyQueuePanel(readyQueue);
                    }

                    // get a new process only if needed
                    if (!readyQueue.isEmpty()) {
                        currentProcess = readyQueue.poll();
                        quantumCounter = 0;
                        updateReadyQueuePanel(readyQueue);

                        if (ganttChartLabels.isEmpty() || !ganttChartLabels.get(ganttChartLabels.size() - 1).equals(currentProcess.id)) {
                            ganttChartLabels.add(currentProcess.id);
                            ganttChartTimes.add(time);
                        }
                    }
                }

		// process execution logic
                if (currentProcess != null) {
                    System.out.println("Time " + time + ": Process " + currentProcess.id + " executing\n");
                    updateCPUPanel(currentProcess);
                    currentProcess.remainingTime--;
                    quantumCounter++;
					
                    // Process completion logic
                    if (currentProcess.remainingTime == 0) {
                        currentProcess.completionTime = time + 1;
                        currentProcess.turnaroundTime = currentProcess.completionTime - currentProcess.arrivalTime;
                        currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;
                        
                        turnAroundTimes.add(currentProcess.turnaroundTime);
                        waitingTimes.add(currentProcess.waitingTime);
                        completedProcess.add(currentProcess);
                        
                        // add output in table
                        outputPanel.add(outputProcess(
                                currentProcess.id, 
                                currentProcess.arrivalTime, 
                                currentProcess.burstTime, 
                                currentProcess.priority, 
                                currentProcess.completionTime, 
                                currentProcess.turnaroundTime, 
                                currentProcess.waitingTime,
                                currentProcess.avgTT,
                                currentProcess.avgWT));
                        outputPanel.revalidate();
                        outputPanel.repaint();

                        currentProcess = null;
                        quantumCounter = 0;
                    }
                } else {
                    System.out.println("Time " + time + ": Idle\n");
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
                    System.out.println("Simulation complete!\n");
                    updateReadyQueuePanel(readyQueue);
                    updateCPUPanel(currentProcess);
                    calculateAvg();
                }
            }
        });
        timer.start();
    }
    
    public JPanel outputProcess(String processID, int arrivalTime, int burstTime, int priority, int completionTime, int turnaroundTime, int waitingTime, double avgTT, double avgWT){
        transparent = new Color(0,0,0,0);
        white = Color.WHITE;
        
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0,0));
        panel.setPreferredSize(new Dimension(930, 40));
        panel.setOpaque(false);
            
            // for debugging
            System.out.println("Process ID: " + processID);
            System.out.println("Arrival Time: " + arrivalTime);
            System.out.println("Burst Time: " + burstTime);
            
            panel.add(createPanel(transparent, white, processID));
            panel.add(createPanel(transparent, white, String.valueOf(arrivalTime)));
            panel.add(createPanel(transparent, white, String.valueOf(burstTime)));
            panel.add(createPanel(transparent, white, String.valueOf(priority)));
            panel.add(createPanel(transparent, white, String.valueOf(completionTime)));
            panel.add(createPanel(transparent, white, String.valueOf(turnaroundTime)));
            panel.add(createPanel(transparent, white, String.valueOf(waitingTime)));
            panel.add(createPanel(transparent, white, String.format("%.2f", avgTT)));
            panel.add(createPanel(transparent, white, String.format("%.2f", avgWT)));
        
        return panel;
    }
    
    public JPanel createPanel(Color background, Color foreground, String label){
        JPanel jpanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        JLabel jlabel = new JLabel();
        jpanel.setPreferredSize(new Dimension (103, 40));
        jpanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        jpanel.setBackground(background);
        jlabel.setForeground(foreground);
        jlabel.setText(label);
        jlabel.setVerticalAlignment(SwingConstants.CENTER);
        jlabel.setFont(archivoblack.deriveFont(10f));
        jpanel.add(jlabel, BorderLayout.CENTER);
        
        return jpanel;
    }
    
    private void calculateAvg() {
        if (turnAroundTimes.isEmpty() || waitingTimes.isEmpty()) {
            return;
        }
        
        int totalTurnAroundTime = 0;
        int totalWaitingTime = 0;
        int numProcesses = turnAroundTimes.size();
        
        for (int i = 0; i < numProcesses; i++) {
            totalTurnAroundTime += turnAroundTimes.get(i);
            totalWaitingTime += waitingTimes.get(i);
        }
        
        // calculate average
        double avgTurnAroundTime = (double) totalTurnAroundTime / numProcesses;
        double avgWaitingTime = (double) totalWaitingTime / numProcesses;
        
        //for debugging
        System.out.println("Average Turnaround Time: " + avgTurnAroundTime);
        System.out.println("Average Waiting Time: " + avgWaitingTime);
        
        // display avgs
        outputPanel.removeAll();
        outputPanel.revalidate();
        outputPanel.add(createPanel(gray, black, "PROCESS ID"));
        outputPanel.add(createPanel(gray, black, "ARRIVAL TIME"));
        outputPanel.add(createPanel(gray, black, "BURST TIME")); 
        outputPanel.add(createPanel(gray, black, "PRIORITY")); 
        outputPanel.add(createPanel(gray, black, "<html><body style='text-align:center;'>COMPLETION<br>TIME</body></html>")); 
        outputPanel.add(createPanel(gray, black, "<html><body style='text-align:center;'>TURNAROUND<br>TIME (TT)</body></html>")); 
        outputPanel.add(createPanel(gray, black, "<html><body style='text-align:center;'>WAITING<br>TIME (WT)</body></html>")); 
        outputPanel.add(createPanel(gray, black, "AVG. TT")); 
        outputPanel.add(createPanel(gray, black, "AVG. WT")); 
        for (Process process : completedProcess) {
            outputPanel.add(outputProcess(
                process.id, 
                process.arrivalTime, 
                process.burstTime, 
                process.priority, 
                process.completionTime, 
                process.turnaroundTime, 
                process.waitingTime,
                avgTurnAroundTime,
                avgWaitingTime));
            outputPanel.revalidate();
            outputPanel.repaint();
        }
    }
    
    public void updateReadyQueuePanel(Queue<Process> readyQueue) {
        readyQueuePanel.removeAll();
        
        JLabel queueTitle = new JLabel("Ready Queue: ");
        queueTitle.setFont(archivoblack.deriveFont(20f));
        queueTitle.setForeground(Color.WHITE);
        readyQueuePanel.add(queueTitle);
        
        // Display all processes currently in the ready queue
        if (!readyQueue.isEmpty()) {
            for (Process process : readyQueue) {
                JLabel processLabel = new JLabel("" + process.id);
                processLabel.setFont(archivoblack.deriveFont(20f));
                processLabel.setForeground(Color.YELLOW);
                readyQueuePanel.add(processLabel);
            }
        } else {
            JLabel processLabel = new JLabel("No processes in ready queue");
            processLabel.setFont(archivoblack.deriveFont(20f));
            processLabel.setForeground(Color.YELLOW);
            readyQueuePanel.add(processLabel);
        }
        
        readyQueuePanel.revalidate();
        readyQueuePanel.repaint();
    }
    
    public void updateCPUPanel(Process process) {
        cpuPanel.removeAll();
        
        JLabel title = new JLabel("CPU: ");
        title.setFont(archivoblack.deriveFont(20f));
        title.setForeground(Color.WHITE);
        cpuPanel.add(title);
        
        if (currentProcess != null) {
            JLabel processLabel = new JLabel("Process " + process.id + " is currently executing");
            processLabel.setFont(archivoblack.deriveFont(20f));
            processLabel.setForeground(Color.YELLOW);
            cpuPanel.add(processLabel);
        } else {
            JLabel processLabel = new JLabel("Idle");
            processLabel.setFont(archivoblack.deriveFont(20f));
            processLabel.setForeground(Color.YELLOW);
            cpuPanel.add(processLabel);
        }
        
        cpuPanel.revalidate();
        cpuPanel.repaint();
    }
    
    public void stopSimulation() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
            System.out.println("Simulation stopped.");
        }
    }
    
    public void clearOutputPanel() {
        readyQueuePanel.removeAll();
        readyQueuePanel.revalidate();
        readyQueuePanel.repaint();
        
        JLabel queueTitle = new JLabel("Ready Queue: ");
        queueTitle.setFont(archivoblack.deriveFont(20f));
        queueTitle.setForeground(Color.WHITE);
        readyQueuePanel.add(queueTitle);
        
        JLabel processLabel1 = new JLabel("No processes in ready queue");
        processLabel1.setFont(archivoblack.deriveFont(20f));
        processLabel1.setForeground(Color.YELLOW);
        readyQueuePanel.add(processLabel1);
        
        cpuPanel.removeAll();
        cpuPanel.revalidate();
        cpuPanel.repaint();
        
        JLabel title = new JLabel("CPU: ");
        title.setFont(archivoblack.deriveFont(20f));
        title.setForeground(Color.WHITE);
        cpuPanel.add(title);
        
        JLabel processLabel2 = new JLabel("Idle");
        processLabel2.setFont(archivoblack.deriveFont(20f));
        processLabel2.setForeground(Color.YELLOW);
        cpuPanel.add(processLabel2);
        
        outputPanel.removeAll();
        outputPanel.revalidate();
        outputPanel.repaint();
        
        outputPanel.add(createPanel(gray, black, "PROCESS ID"));
        outputPanel.add(createPanel(gray, black, "ARRIVAL TIME"));
        outputPanel.add(createPanel(gray, black, "BURST TIME")); 
        outputPanel.add(createPanel(gray, black, "PRIORITY")); 
        outputPanel.add(createPanel(gray, black, "<html><body style='text-align:center;'>COMPLETION<br>TIME</body></html>")); 
        outputPanel.add(createPanel(gray, black, "<html><body style='text-align:center;'>TURNAROUND<br>TIME (TT)</body></html>")); 
        outputPanel.add(createPanel(gray, black, "<html><body style='text-align:center;'>WAITING<br>TIME (WT)</body></html>")); 
        outputPanel.add(createPanel(gray, black, "AVG. TT")); 
        outputPanel.add(createPanel(gray, black, "AVG. WT"));
    }
}
