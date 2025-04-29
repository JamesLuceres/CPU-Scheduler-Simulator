import java.util.Queue;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RoundRobin {
	Map<String, int[]> processValues = new HashMap<>();
	Map<String, Integer> completionTimes = new HashMap<>();
	Map<String, Integer> originalBurstTimes = new HashMap<>();
	List<Map.Entry<String, int[]>> sortedProcess;
		
		
	public void insertProcesses(String processID, int arrivalTime, int burstTime) {
		processValues.put(processID, new int[]{arrivalTime, burstTime});
	}
	
	public void RoundRobinAlgo() {
		// convert hashmap to list and sort by arrivalTime
		sortedProcess = new ArrayList<>(processValues.entrySet());
		sortedProcess.sort(Comparator.comparingInt(entry -> entry.getValue()[0])); // sort by arrival time
		
		Queue<Map.Entry<String, int[]>> processQueue = new LinkedList<>(); // ready queue
		
		int currentTime = 0;
		int quantumTime = 3;
		int i = 0;
		
		// store original burst times
		for (Map.Entry<String, int[]> entry : sortedProcess) {
			originalBurstTimes.put(entry.getKey(), entry.getValue()[1]);
		}
		
		// start execution when the first process arrives
		while (i < sortedProcess.size() && processQueue.isEmpty()) {
			currentTime = sortedProcess.get(i).getValue()[0];
			while (i < sortedProcess.size() && sortedProcess.get(i).getValue()[0] == currentTime) {
				processQueue.offer(sortedProcess.get(i));
				i++;
			}
		}
		
		while (!processQueue.isEmpty()) {
			Map.Entry<String, int[]> currentProcess = processQueue.poll();
			
			String processID = currentProcess.getKey();
			int[] processValues = currentProcess.getValue();
			int arrivalTime = processValues[0];
			int burstTime = processValues[1];
			
			// modify current time
			if (currentTime < arrivalTime) {
				currentTime = arrivalTime;
			}
			
			// execute process for quantum time or until completion
			int executionTime = Math.min(burstTime, quantumTime);
			currentTime = currentTime + executionTime;
			burstTime = burstTime - executionTime;
			
			processValues[1] = burstTime;
			
			//System.out.println("Process: " + processID + " -> " + "Remaining Burst Time: " + burstTime + " | Current Time: " + currentTime);
			
			if (burstTime == 0) {
				completionTimes.put(processID, currentTime);
			}
			
			// add new processes that arrived during execution
			while (i < sortedProcess.size() && sortedProcess.get(i).getValue()[0] <= currentTime) {
				processQueue.offer(sortedProcess.get(i));
				i++;
			}
			
			// if process is not finished, add it back to queue
			if (burstTime > 0) {
				processQueue.offer(currentProcess);
			}
		}
	}
	
	public void displayTable() {
		System.out.println("\nProcesses Execution Order (Round Robin):");
		System.out.println("Process | Arrival Time | Burst Time | Completion Time | Turnaround Time | Waiting Time |");
		
		for (Map.Entry<String, int[]> entry : sortedProcess) {
			String processID = entry.getKey();
			int arrivalTime = entry.getValue()[0];
			int burstTime = originalBurstTimes.get(processID);
			int completionTime = completionTimes.get(processID);
			int turnAroundTime = completionTime - arrivalTime;
			int waitingTime = turnAroundTime - burstTime;
				
			System.out.printf("%7s | %12d | %10d | %15d | %15d | %12d |\n", processID, arrivalTime, burstTime, completionTime, turnAroundTime, waitingTime);
		}
	}
	
	public static void main(String[] args) {
		RoundRobin process = new RoundRobin();
		
		process.insertProcesses("P1", 0, 8);
		process.insertProcesses("P2", 5, 2);
		process.insertProcesses("P3", 1, 7);
		process.insertProcesses("P4", 6, 3);
		process.insertProcesses("P5", 8, 5);
		
		process.RoundRobinAlgo();
		process.displayTable();
	}
}