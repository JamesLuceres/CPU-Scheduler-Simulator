import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class PriorityPreemptive {
    Map<String, int[]> processValues = new HashMap<>();
    Map<String, Integer> completionTimes = new HashMap<>();
    Map<String, Integer> originalBurstTimes = new HashMap<>();
    List<Map.Entry<String, int[]>> sortedProcesses;

    public void insertProcesses(String processID, int arrivalTime, int burstTime, int priority) {
        processValues.put(processID, new int[]{arrivalTime, burstTime, priority});
    }

    public void PriorityPreemptiveAlgo() {
        sortedProcesses = new ArrayList<>(processValues.entrySet());
        sortedProcesses.sort(Comparator.comparingInt(entry -> entry.getValue()[0])); // Sort by arrival time

        PriorityQueue<Map.Entry<String, int[]>> processQueue = new PriorityQueue<>(
            Comparator.comparingInt((Map.Entry<String, int[]> entry) -> entry.getValue()[2]) // Sort by priority (lower value = higher priority)
            .thenComparingInt(entry -> entry.getValue()[0]) // Tie-breaker: earlier arrival time
        );
        
        int currentTime = 0;
        int i = 0;
        
        for (Map.Entry<String, int[]> entry : sortedProcesses) {
            originalBurstTimes.put(entry.getKey(), entry.getValue()[1]);
        }
        
        while (i < sortedProcesses.size() || !processQueue.isEmpty()) {
            while (i < sortedProcesses.size() && sortedProcesses.get(i).getValue()[0] <= currentTime) {
                processQueue.offer(sortedProcesses.get(i));
                i++;
            }
            
            if (processQueue.isEmpty()) {
                currentTime = sortedProcesses.get(i).getValue()[0];
                continue;
            }
            
            Map.Entry<String, int[]> currentProcess = processQueue.poll();
            String processID = currentProcess.getKey();
            int[] processValues = currentProcess.getValue();
            int burstTime = processValues[1];
            
            currentTime++;
            burstTime--;
            processValues[1] = burstTime;
            
            while (i < sortedProcesses.size() && sortedProcesses.get(i).getValue()[0] == currentTime) {
                processQueue.offer(sortedProcesses.get(i));
                i++;
            }
            
            if (burstTime == 0) {
                completionTimes.put(processID, currentTime);
            } else {
                processQueue.offer(currentProcess);
            }
        }
    }

    public void displayTable() {
        System.out.println("\nProcesses Execution Order (Priority Preemptive):");
        System.out.println("Process | Arrival Time | Burst Time | Priority | Completion Time | Turnaround Time | Waiting Time |");
        
        for (Map.Entry<String, int[]> entry : sortedProcesses) {
            String processID = entry.getKey();
            int arrivalTime = entry.getValue()[0];
            int burstTime = originalBurstTimes.get(processID);
            int priority = entry.getValue()[2];
            int completionTime = completionTimes.get(processID);
            int turnAroundTime = completionTime - arrivalTime;
            int waitingTime = turnAroundTime - burstTime;
            
            System.out.printf("%7s | %12d | %10d | %8d | %15d | %15d | %12d |\n", processID, arrivalTime, burstTime, priority, completionTime, turnAroundTime, waitingTime);
        }
    }

    public static void main(String[] args) {
        PriorityPreemptive process = new PriorityPreemptive();
        
        process.insertProcesses("P1", 0, 7, 2);
        process.insertProcesses("P2", 1, 5, 1);
        process.insertProcesses("P3", 3, 4, 3);
        process.insertProcesses("P4", 4, 2, 4);
        
        process.PriorityPreemptiveAlgo();
        process.displayTable();
    }
}