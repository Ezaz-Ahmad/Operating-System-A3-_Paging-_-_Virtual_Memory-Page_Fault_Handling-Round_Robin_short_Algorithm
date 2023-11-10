import java.util.ArrayList;
import java.util.HashMap;
public class Fixed extends CPU{
    private HashMap<String, GCLOCK> main_Mem;
    private int number_of_Alloc_frames;
    public Fixed(int _frames, int _timeQuantum, ArrayList<Process> _processList, String _algoName){
        super(_timeQuantum, _algoName, _processList);
        // Initializing main memory as an empty hashmap
        main_Mem = new HashMap<>();
        // Calculate the number of frames each process is allocated in this "Fixed" scheme
        number_of_Alloc_frames = _frames / proc_List.size();
    }
    // Load a page into memory, using the GCLOCK algorithm
    @Override
    protected void loadIntoMemory() {
        GCLOCK temp = main_Mem.get(current_Process.getProcessName());
        Page page = new Page(current_Process.getCurrentInstruction(), current_Process.getProcessName());
        if(temp.getBufferSize() != number_of_Alloc_frames){
            temp.addNewFrame(page);
        }
        else{
            temp.replaceCurrFrame(page);
        }
    }
    // Initializeingg the ready queue with processes and set up initial memory space for each process
    @Override
    protected void primeReadyQueue() {
        for(int i = 0; i < proc_List.size(); i++){
            ready_Queue.add(proc_List.get(i));
            main_Mem.put(proc_List.get(i).getProcessName(), new GCLOCK());
        }
    }
    // Removeingg a process from memory, essentially freeing up its allocated space
    @Override
    protected boolean checkMemory() {
        // If the next instruction is in memory for the current process, return true
        if(main_Mem.get(current_Process.getProcessName()).checkBuffer(current_Process)){
            return true;
        }
        // Otherwise, return false, indicating a page fault
        return false;
    }
    @Override
    protected void removeFromMemory() {
        main_Mem.remove(current_Process.getProcessName());
    }
}
