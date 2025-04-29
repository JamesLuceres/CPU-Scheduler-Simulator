package schedify;

import java.util.ArrayList;

public class Simulator {
    
    private final ArrayList<Process> processList = new ArrayList<>();
    private int quantumTime;
    private int priorityLevel = 0;
    
    public void addProcess(Process p) {
        processList.add(p);
    }
    
    public ArrayList<Process> getProcesses() {
        return processList;
    }
    
    public void clearProcessList() {
        processList.clear();
    }
    
    public void setQuantumTime(int qt) {
        quantumTime = qt;
    }
    
    public int getQuantumTime() {
        return quantumTime;
    }
    
    public void setPriorityLevel(int pt){
        priorityLevel = pt;
    }
    
    public int getPriorityLevel(){
        return priorityLevel;
    }
}
