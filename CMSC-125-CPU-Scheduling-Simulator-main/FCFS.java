import java.util.Queue;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FCFS {
	Map<String, int[]> processValues = new HashMap<>();
	
	public void insertProcesses(String processID, int arrivalTime, int burstTime) {
		processValues.put(processID, new int[]{arrivalTime, burstTime});
	}
	
	public void FCFSAlgo() {
		// convert hashmap to list and sort by arrivalTime
		List<Map.Entry<String, int[]>> sortedProcess = new ArrayList<>(processValues.entrySet());
		sortedProcess.sort(Comparator.comparingInt(entry -> entry.getValue()[0])); // sort by arrival time
		
		// create a queue data structure and store sorted processes there
		Queue<Map.Entry<String, int[]>> processQueue =  new LinkedList<>();
		processQueue.addAll(sortedProcess);
		
		System.out.println("Processes inside process queue\n");
		displayProcessQueue(processQueue);
		
		int currentTime = 0;
		int completionTime = 0;
		int turnAroundtime = 0;
		int waitingTime = 0;
		
		System.out.println("\nProcesses Execution Order (FCFS):");
		System.out.println("Process | Arrival Time | Burst Time | Completion Time | Turnaround Time | Waiting Time |");
		
		Map.Entry<String, int[]> removedProcess = removeProcess(processQueue);
		while(removedProcess != null) {
			String processID = removedProcess.getKey();
			int arrivalTime = removedProcess.getValue()[0];
			int burstTime = removedProcess.getValue()[1];
			
			if (currentTime < arrivalTime) {
				currentTime = arrivalTime;
			}
			
			currentTime = currentTime + burstTime;
			completionTime = currentTime;
			turnAroundtime = completionTime - arrivalTime;
			waitingTime = turnAroundtime - burstTime;
				
			System.out.printf("%7s | %12d | %10d | %15d | %15d | %12d |\n", processID, arrivalTime, burstTime, completionTime, turnAroundtime, waitingTime);
			removedProcess = removeProcess(processQueue);
		}
	}
	
	public void displayProcessQueue(Queue<Map.Entry<String, int[]>> processQueue) {
		if (processQueue.isEmpty()) {
			System.out.println("The process queue is empty");
			return;
		}
		
		for (Map.Entry<String, int[]> process : processQueue) {
			String processID = process.getKey();
			int[] values = process.getValue();
			
			int arrivalTime = values[0];
			int burstTime = values[1];
			
			System.out.println("Process: " + processID + " -> " + "[" + arrivalTime + ", " + burstTime + "]");
		}
	}

	public Map.Entry<String, int[]> removeProcess(Queue<Map.Entry<String, int[]>> processQueue) {
		if (processQueue.isEmpty()) {
			System.out.println("The process queue is already empty");
			return null;
		}
		
		Map.Entry<String, int[]> removedProcess = processQueue.poll();
		
		return removedProcess;
	}
	
	public static void main(String[] args) {
		FCFS process = new FCFS();
		
		process.insertProcesses("P1", 0, 2);
		process.insertProcesses("P2", 1, 2);
		process.insertProcesses("P3", 5, 3);
		process.insertProcesses("P4", 6, 4);
		
		process.FCFSAlgo();
	}
}