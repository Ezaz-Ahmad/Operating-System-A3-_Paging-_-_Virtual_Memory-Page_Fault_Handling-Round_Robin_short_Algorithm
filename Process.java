import java.util.ArrayList;
import java.util.Queue;
// Represents a single process that's executed by the CPU.
public class Process implements Comparable<Process>{
    private int curr_Inst;
    private String processName;
    private String current_Status;
    private int timeFinished;
    private int nextEvent;
    private Queue<Integer> proc_Instructions;
    private ArrayList<Integer> P_Faults;
    // Initializes a process with its name and instructions.
    public Process(String _processName, Queue<Integer> _pageInstructions){
        proc_Instructions = _pageInstructions;
        P_Faults = new ArrayList<>();
        processName = _processName;
        current_Status = "ready";
        curr_Inst = 0;
        timeFinished = 0;
        nextEvent = 0;
    }

    public String getProcessStatus(){
        return current_Status;
    }

    public void addNewPageFault(int time){
        P_Faults.add(time);
    }

    public int getPageFaultCount(){
        return P_Faults.size();
    }

    public String getProcessName(){
        return processName;
    }
    public int getTimeFinished(){
        return timeFinished;
    }
    public void setFinishedTime(int _timeFinished){
        timeFinished = _timeFinished;
    }
    public int getCurrentInstruction(){
        return curr_Inst;
    }
    public int getNextEvent(){
        return nextEvent;
    }
    public void setNextEvent(int time){
        nextEvent = time;
    }
    public void nextInstruction(){
        curr_Inst = proc_Instructions.remove();
    }

    public boolean isFinished(){
        if(proc_Instructions.isEmpty()){
            return true;
        }
        return false;
    }

    public void swapStatus(){
        if(current_Status.equals("ready")){
            current_Status = "blocked";
        }
        else{
            current_Status = "ready";
        }
    }

    public String getFaultTimes(){
        String out = "{";
        for(int i = 0; i < P_Faults.size(); i++){
            if((i + 1) != P_Faults.size()){
                out += Integer.toString(P_Faults.get(i)) + ", ";
            }
            else{
                out += Integer.toString(P_Faults.get(i));
            }
        }
        out += "}";
        return out;
    }
    public String getProcessID(){
        String[] temp = processName.split("\\.");
        String filename = temp[temp.length - 2];
        return Character.toString(filename.charAt(filename.length() - 1));
    }
    @Override
    public int compareTo(Process process) {
        if(getProcessID().compareTo(process.getProcessID()) > 0){
            return 1;
        }
        return -1;
    }

}
