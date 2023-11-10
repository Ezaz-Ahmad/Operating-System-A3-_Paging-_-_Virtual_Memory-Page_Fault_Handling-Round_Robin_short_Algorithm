import java.util.ArrayList;

//created this GCLOCK class to handle page replacement for the Fixed algorithm.
public class GCLOCK {
    private int current_Pindex;
    private ArrayList<Page> Frames;
    public GCLOCK (){
        Frames = new ArrayList<>();
        current_Pindex = 0;
    }
    //  useing tis method to replace the current frame with a new page. If the page's reference counter is 0, then it can replace it.
    public void replaceCurrFrame(Page p){
        while(true){
            if(Frames.get(current_Pindex).getReferenceCounter() == 0){

                Frames.set(current_Pindex, p);

                moveCurrPointer();
                return;
            }
            // If the page can't be replaced, It decrease its reference counter and move on.
            Frames.get(current_Pindex).decReferenceCounter();
            moveCurrPointer();
        }
    }

    // checking if the required page for a process is already in the buffer.
    public boolean checkBuffer(Process p){

        for(int i = 0; i < Frames.size(); i++){

            if(Frames.get(i).getProcessName().equals(p.getProcessName()) && Frames.get(i).getpageInstruction() == p.getCurrentInstruction()){

                // If the page has not been referenced before, then marking it.
                if(!Frames.get(i).getFirstRef()){

                    Frames.get(i).swapFirstRef();
                }
                else{
                    // Otherwise,  increase its reference counter.
                    Frames.get(i).incReferenceCounter();
                }

                return true;
            }
        }
        return false;  // The required page is not in the buffer.
    }

    // created this to move the pointer to the next frame in a circular fashion.
    public void moveCurrPointer(){

        if(current_Pindex + 1 == Frames.size()){

            current_Pindex = 0;  // If it reaches the end, It set it back to the start.
        }
        else{

            current_Pindex++;  // Otherwise, just move to the next frame.
        }
    }

    // method to get the current size of our buffer.
    public int getBufferSize(){
        return Frames.size();
    }

    // Adding a new frame to the buffer.
    public void addNewFrame(Page p){
        Frames.add(p);
    }
}
