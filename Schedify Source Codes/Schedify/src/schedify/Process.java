package schedify;

public class Process {
    String id;
    int arrivalTime, burstTime, remainingTime, priority;
    int completionTime, turnaroundTime, waitingTime;
    int avgTT, avgWT;
    
    public Process(String id, int arrivalTime, int burstTime, int priority){
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
        this.priority = priority;
    }
    
}
