/*
 * NOT IMPLEMENTED YET:
 * - outputToDisk()  ... used to output the string array to disk with/without output to screen.
 * 
 * 
 */
package dgnUtils.text;

/**
 *
 * @author David
 * 
 * 2013-04-18
 * 
 * This class holds strings for output so that outputs to screen can be given in order of execution.
 * 
 * It can be run at the end of the program (once it has been shutdown),
 * or for the end of a test that is being run.  
 * 
 * The outputToScreen method could be called in the
 * "@AfterClass" method.
 * 
 */
public class StringToScreen {

    /**
     * Default Constructor:  <br>
     * arraySize and sizeIncrement are set to 1000 each.
     */
    public StringToScreen(){
        arraySize = 1000;
        sizeIncrement = 1000;
        stringArray = new String[arraySize];  // intialize string array to hold strings for output.
    }
    
    /**
     * Constructor:  <br>
     * Takes an arraySize integer and a sizeIncrement integer.<br>
     * Any value passed that is less than 1 will be automatically set to 10.
     * 
     * @param arraySize1
     * @param sizeIncrement1 
     */
    public StringToScreen(int arraySize1, int sizeIncrement1){
        arraySize = arraySize1;
        sizeIncrement = sizeIncrement1;
        if(sizeIncrement < 1){
            sizeIncrement = 10;
        }
        if (arraySize < 1){
            arraySize = 10;
        }
        stringArray = new String[arraySize];  // intialize string array to hold strings for output.
        
    }
    
    /*
     * Sets a new string into the string array and checks that the array index is not out of bounds.
     * If the array index is out of bounds, then the array size is incremented.
     */
    public void setString(String str1){
        if(arrayPointer > arraySize - 1){  // checks if the next position to write to is not out of bounds.  The arrayPointer must be less than the arraySize.                                                 
            increaseArraySize();
        }
        stringArray[arrayPointer] = str1;
        arrayPointer++;
    }
    
    /**
     * Outputs all strings to the screen.
     */
    public void outputToScreen(){
        for (int i = 0; i < arrayPointer; i++){
            System.out.println(stringArray[i]);
        }
    }
    
    
    
    private void increaseArraySize(){
        arraySize = arraySize + sizeIncrement;
        String[] tempStringArray = new String[arraySize];
        System.arraycopy(stringArray, 0, tempStringArray, 0, stringArray.length);
        stringArray = tempStringArray;
        tempStringArray = null;
    }
    
    
    
    
    
    
    /**
     * Only used for jUnit testing purposes.  Should not be used in production code.
     */
    public void setArraySize(int arraySize1){
        arraySize = arraySize1;
    }
    
    /**
     * Only used for jUnit testing purposes.  Should not be used in production code.
     */
    public int getArraySize(){
        return arraySize;
    }
            
    
    /**
     * Only used for jUnit testing purposes.  Should not be used in production code.
     */
    public String[] getStringArray() {
        return stringArray;
    }
    
    
    /**
     * Only used for jUnit testing purposes.  Should not be used in production code.
     */
    public void setStringArray(String[] stringArray1) {
        stringArray = stringArray1;
    }
    
    
    /**
     * Only used for jUnit testing purposes.  Should not be used in production code.
     */
    public int getSizeIncrement() {
        return sizeIncrement;
    }
    
    
    /**
     * Only used for jUnit testing purposes.  Should not be used in production code.
     */
    public void setSizeIncrement(int sizeIncrement1) {
        sizeIncrement = sizeIncrement1;
    }
    
    
    /**
     * Only used for jUnit testing purposes.  Should not be used in production 
     * code.  Array pointer is the next array position to write to.
     */
    public int getArrayPointer() {
        return arrayPointer;
    }
    
    
    /**
     * Only used for jUnit testing purposes.  Should not be used in production 
     * code.  Array pointer is the next array position to write to.
     */
    public void setArrayPointer(int arrayPointer1) {
        arrayPointer = arrayPointer1;
    }
    
    
    /**
     * Only used for jUnit testing purposes.  Should not be used in production code.
     */
    public boolean isOutputToDisk() {
        return outputToDisk;
    }
    
    
    /**
     * Only used for jUnit testing purposes.  Should not be used in production code.
     */
    public void setOutputToDisk(boolean outputToDisk1) {
        outputToDisk = outputToDisk1;
    }
    
    /*  Class Variables:  */
    private String[] stringArray;          // Can be set to 1000 pointers then increased if necessary.
    int arraySize = 0;                     // Sets the size of the array holding the strings for output.  Set in constructor.
    private int sizeIncrement = 0;         // Sets the amount to increase the string array should it fill.  Set in constructor.
    private int arrayPointer = 0;          // Holds the next array index to write to.
    private boolean outputToDisk = false;  // If this is set to true, the output will also go to a file on disk.
    /*  End of Class Vars   */
} // END OF CLASS
