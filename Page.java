public class Page {
    private int referenceCounter;
    private boolean firstRef;
    private String processName;
    private int pageInstruction;
    public Page(int _pageInstruction, String _processName){
        pageInstruction = _pageInstruction;
        referenceCounter = 0;
        processName = _processName;
    }
    // Getter method to retrieve the instruction of the page.
    public int getpageInstruction(){
        return pageInstruction;
    }
    // Getter method to retrieve the current reference counter value.
    public int getReferenceCounter(){
        return referenceCounter;
    }
    // Getter method to retrieve the name of the process associated with this page.
    public String getProcessName(){
        return processName;
    }
    // Getter method to check if this page has been referenced before.
    public boolean getFirstRef(){
        return firstRef;
    }
    // Decreases the reference counter by 1. Useful in algorithms like GCLOCK.
    public void decReferenceCounter(){
        referenceCounter--;
    }
    // Increases the reference counter by 1.
    public void incReferenceCounter(){
        referenceCounter++;
    }
    public void swapFirstRef(){
        firstRef = true;
    }
}
