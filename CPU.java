import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.Queue;

// This is an abstract class representing a CPU scheduling algorithm.
// Since it's abstract, other classes will extend it to provide specific implementations.
public abstract class CPU {
    protected int current_Time;  // Current simulation time
    protected int time_Quantum;  // Time quantum for the scheduling
    protected Process current_Process;  // Currently running process
    protected String algoName;  // Algorithm name
    protected PriorityQueue<Process> finished_List;  // List of processes that have completed
    protected Queue<Process> ready_Queue;  // List of processes ready to be executed
    protected Queue<Process> Blocked_que;  // List of processes that are blocked
    protected ArrayList<Process> proc_List;  // All the processes to be considered
    // Constructor to initialize the CPU attributes
    public CPU (int _timeQuantum, String _algoName, ArrayList<Process> _processList){
        current_Time = 0;
        algoName = _algoName;
        time_Quantum = _timeQuantum;
        proc_List = _processList;
        ready_Queue = new LinkedList<>();
        Blocked_que = new LinkedList<>();
        finished_List = new PriorityQueue<>();
    }

    // This method checks if any blocked process can be moved to the ready queue
    public void check_Blocked_Queue(){
        while(true){
            if(!Blocked_que.isEmpty()){
                if(Blocked_que.peek().getNextEvent() == current_Time){
                    ready_Queue.add(Blocked_que.remove());
                }
                else{
                    return;
                }
            }
            else{
                return;
            }
        }
    }

    // Main execution loop for the scheduling algorithm
    public void run(){

        primeReadyQueue();  // Priming the ready queue initially

        // Continue running until all processes are finished
        while(finished_List.size() != proc_List.size()){

            // If no process is ready, advance time to next event or return if all processes are complete
            if(ready_Queue.isEmpty()){
                if(Blocked_que.isEmpty()){
                    return;
                }
                current_Time = Blocked_que.peek().getNextEvent();
                check_Blocked_Queue();
            }

            current_Process = ready_Queue.remove();

            if(current_Process.getProcessStatus().equals("ready")){
                current_Process.nextInstruction();
            }
            else{
                current_Process.swapStatus();
            }

            for(int i = 0; i < time_Quantum; i++){

                if(!checkMemory()){
                    loadIntoMemory();
                    current_Process.swapStatus();
                    current_Process.setNextEvent(current_Time + 6);
                    current_Process.addNewPageFault(current_Time);
                    Blocked_que.add(current_Process);
                    break;
                }
                current_Time++;

                if(!Blocked_que.isEmpty()){
                    check_Blocked_Queue();
                }

                if(current_Process.isFinished()){
                    current_Process.setFinishedTime(current_Time);
                    finished_List.add(current_Process);
                    removeFromMemory();
                    break;
                }
                else{
                    if(i == time_Quantum - 1){
                        ready_Queue.add(current_Process);
                    }
                    else{
                        current_Process.nextInstruction();
                    }
                }
            }
        }
    }

    // Method to print the simulation report
    public String get_Sim_Report(){
        String out =  "CLOCK - " + algoName + " Replacement:\n";
        out += "PID  Process Name      Turnaround Time  # Faults  Fault Times\n";
        while(!finished_List.isEmpty()){
            Process temp = finished_List.remove();
            out += String.format("%-4s %-17s %-16d %-9d %-4s", temp.getProcessID(), temp.getProcessName(), temp.getTimeFinished(), temp.getPageFaultCount(), temp.getFaultTimes()) + "\n";
        }
        return out;
    }

    // these Abstract methods, the subclasses will provide specific implementations for these
    protected abstract void primeReadyQueue();

    protected abstract boolean checkMemory();

    protected abstract void loadIntoMemory();

    protected abstract void removeFromMemory();
}
