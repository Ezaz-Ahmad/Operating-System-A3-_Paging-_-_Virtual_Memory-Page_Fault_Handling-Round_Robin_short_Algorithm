import java.util.ArrayList;

// Represents the CPU scheduling with variable page replacement strategy.
public class Variable extends CPU{

    private int Frames;
    private GCLOCK M_Memory;
    public Variable(int _frames, int _timeQuantum, ArrayList<Process> _processList, String _algoName)
    {
        super(_timeQuantum, _algoName, _processList);
        M_Memory = new GCLOCK();      // Initialize the memory management system.
        Frames = _frames;              // Set the available frames.
    }

    @Override
    protected void primeReadyQueue()
    {
        for(int i = 0; i < proc_List.size(); i++){
            ready_Queue.add(proc_List.get(i));
        }
    }

    // Loads the current process into memory.
    @Override
    protected void loadIntoMemory()
    {
        Page page = new Page(current_Process.getCurrentInstruction(), current_Process.getProcessName());
        if(Frames != 0){
            M_Memory.addNewFrame(page);
            Frames--;
        }
        else{

            M_Memory.replaceCurrFrame(page);
        }
    }

    @Override
    protected boolean checkMemory()
    {
        return M_Memory.checkBuffer(current_Process);
    }
    @Override
    protected void removeFromMemory() {
        return;
    }
}
