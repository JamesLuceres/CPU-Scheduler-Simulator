import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class PriorityNonPreemptive {
    Map<String, int[]> processValues = new HashMap<>();
    Map<String, Integer> completionTimes = new HashMap<>();
    Map<String, Integer> originalBurstTimes = new HashMap<>();
    List<Map.Entry<String, int[]>> sortedProcesses;

    public void insertProcesses(String processID, int arrivalTime, int burstTime, int priority) {
        processValues.put(processID, new int[]{arrivalTime, burstTime, priority});
    }

    public void PriorityNonPreemptiveAlgo() {
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
            
            currentTime += burstTime;
            completionTimes.put(processID, currentTime);
        }
    }

    public void displayTable() {
        System.out.println("\nProcesses Execution Order (Priority Non-preemptive):");
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
        PriorityNonPreemptive process = new PriorityNonPreemptive();
        
        process.insertProcesses("P1", 0, 11, 2);
        process.insertProcesses("P2", 5, 28, 0);
        process.insertProcesses("P3", 12, 2, 3);
        process.insertProcesses("P4", 2, 10, 1);
        process.insertProcesses("P5", 9, 16, 4);

        process.PriorityNonPreemptiveAlgo();
        process.displayTable();
    }
}