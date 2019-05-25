/*

TODO:  Adapt the class to accept primative type inputs.

 */

package dgnUtils.number;

import dgnUtils.exception.NegativeDecimalPlacesException;

/**
 * This class formats number STRINGS so that they are
 * appropriate to display in a tableview (or any other field). </br>
 * Formats include: </br>
 * <ul>
 *  <li> float and double values with specific number of digits after the decimal point. </li>
 *  <li> Thousands, Millions and Billions separators.  </li>
 * </ul>
 * 
 * Note:  This class currently only works with String inputs.  It does not work
 * with primative-type inputs.
 * 
 * @author David
 */
public class NumberStringFormatter {

    // CLASS VARIABLES
    
    private boolean log;
    private boolean commaThousandsSeparator;
    private boolean decimalPointSeparator;
    private boolean decimalPointNotFound;
    
    private String formattedStr;
    
    private int initialLength, finalLength, numberOfThousandsSeparators;
    private int decimalPointPos;    
    
    private char [] initialArray;
    private char [] finalArray;
    
    
    //Constructor
    public NumberStringFormatter(){
        log = false;
        commaThousandsSeparator = true;
        decimalPointSeparator = true;
    }
    
    public void setLog(boolean logIsOn){
        log = logIsOn;
    }
    
    public void setCommaThousandsSeparatorToComma(){
        commaThousandsSeparator = true;
    }
            
    public void setCommaThousandsSeparatorToPoint(){
        commaThousandsSeparator = false;
    }
    
    public void setDecimalPointSeparatorToPoint(){
        decimalPointSeparator = true;
    }

    public void setDecimalPointSeparatorToComma(){
        decimalPointSeparator = false;
    }
    
    public String formatThousands(String str){                                  if (log) {System.out.println("str = " + str);}
    
        char[] charArrayIn = str.toCharArray();
        char[] charArrayOut = formatThousandsAsCharArray(charArrayIn);

        formattedStr = new String(charArrayOut);                                if (log) {System.out.println("Formatted String = " + formattedStr); System.out.println("");}
        return formattedStr;
    }

    /**
     * Formats to a certain number of decimal places.  Where places don't exist in original, then zeros
     * will be added.  Numbers before the decimal place will be formatted with thousands separators.
     * </br></br>
     * This method only accepts decimal points (i.e. '.') as decimal separators.  String with 
     * commas for decimal separators will not be recognized as numbers with a decimal portion.
     * </br></br>
     * @param str  - Number string passed in to be processed.
     * @param decimalPlaces - Number of decimal places required in the returned string.  Values outside the
     * alloted number of decimal places will be TRUNCATED.  There is currently no rounding of decimal values.
     * @return A string formatted with thousands separators and a set number of decimal places.
     * @throws NegativeDecimalPlacesException for decimal place values less than zero.
     */
    public String formatDecimalPlaces(String str, int decimalPlaces) // decimal places required in the returned string.
            throws NegativeDecimalPlacesException {
        
// Initialize Class Variables
        
        formattedStr = "";
        initialLength= 0;
        finalLength = 0;
        numberOfThousandsSeparators = 0;
        decimalPointPos = 0;
        finalArray = new char[] {'z', 'z'};
        initialArray = new char[] {'y', 'y'};
        char[] beforeDecimalArray = new char[] {'x', 'x'};
        char[] afterDecimalArray = new char[] {'w', 'w'};
        char[] correctDecPlacesArray = new char[] {'v', 'v'};
        

// 1. Get passed in string to a character array called initialArray[].

        initialArray = str.toCharArray();
        initialLength = str.length();                                           if(log){System.out.println("initialLength = " + initialLength);}
        
// 1A. Check to see that passed in number of the decimal point position is not negative.        
        if (decimalPlaces < 0) {
            throw new NegativeDecimalPlacesException();
        }

// 1B.  Check for E.  Branches out into a subroutine.
        
        formatScientificNumbers();
        
// 2. Find the decimal point position in that array (if any).
        decimalPointNotFound = true;
        for (int j = 0; j < initialLength; j++) {
            if (initialArray[j] == '.') {
                decimalPointPos = j;
                decimalPointNotFound = false;
                break;
            }
        }                                                                       if(log){System.out.println("decimalPointFound = " + !decimalPointNotFound); System.out.println("decimalPointPos = " + decimalPointPos);}
        
// 3. Separate the initialArray into two arrays. 1) Before the decimal point.  2) After the decimal point.

// 3.1 Get char array after decimal point.
        if(decimalPointNotFound){
            afterDecimalArray = new char[]{};
        } else {
            afterDecimalArray = getCharArrayAfterDecimalPoint(initialArray, decimalPointPos);
        }
        
        
// 3.2 Get char array before decimal point.  
        if(decimalPointNotFound){
            beforeDecimalArray = initialArray;
        } else {
            beforeDecimalArray = getCharArrayBeforeDecimalPoint(initialArray, decimalPointPos);
        }
            
        char[] formatedArrayBeforeDecimal = formatThousandsAsCharArray(beforeDecimalArray);
       
// 4.  Get the afterDecimalArray manipulated to correct number of decimal places.       
        if(decimalPlaces != 0){
            correctDecPlacesArray  = manipulateDecimalPortionOfNumber(afterDecimalArray, decimalPlaces);
        }
        
// 5.  Join Arrays
        if(decimalPlaces == 0){
            finalArray = formatedArrayBeforeDecimal;
            formattedStr = new String(finalArray);
            return formattedStr;
        }
        
        finalLength = formatedArrayBeforeDecimal.length + 1 + correctDecPlacesArray.length;
        finalArray = new char[finalLength];
        
        int j = 0; int k = 0;
        while(k < formatedArrayBeforeDecimal.length){        
            finalArray[j] = formatedArrayBeforeDecimal[k];  
            j++; k++;
        }
        finalArray[j] = getDecimalSeparator();
        j++; k = 0;
        while (k < correctDecPlacesArray.length){
            finalArray[j] = correctDecPlacesArray[k];
            j++; k++;
        }
        
        formattedStr = new String(finalArray);                                  if(log){System.out.println("formattedStr = " + formattedStr); System.out.println("");}
        return formattedStr;
    } // end method
    
    /**
     * For internal use.
     * This method is not private for jUnit testing purposes.
     */
    public char[] getCharArrayBeforeDecimalPoint(char[] initial, int decPointPos){
        char[] beforeDecimal;
        String errorStr = "error";
        char[] error = errorStr.toCharArray();
        
        // check decimal point position is correct
        if(initial[decPointPos] != '.'){
            return error;
        }
        
        // start at dec pl pos and work up (incr idx).
        beforeDecimal = new char[decPointPos];
        
        int j = 0;
        while(j < decPointPos){
            beforeDecimal[j] = initial[j];                                      if(log){System.out.println("iterator = " + j + "   char = " + initial[j]);}
            j++;
        }
        
        return beforeDecimal;
    }

    /**
     * For internal use.
     * This method is not private for jUnit testing purposes.
     */
    public char[] getCharArrayAfterDecimalPoint(char[] initial, int decPos) {
        char[] afterDecimal = null;
        String errorStr = "error";
        char[] error = errorStr.toCharArray();
        
        // check decimal point position is correct
        if(initial[decPos] != '.'){                                             if(log){System.out.println("ERROR.  Decimal point not found in the right position.");}
            return error;
        }
        
        int newLength = initial.length - decPos - 1;
        int oldLength = initial.length;
        
        afterDecimal = new char[newLength];                                     if(log){System.out.println("afterDecimal Array Length = " + newLength);}
        
        int j = 0;
        while(j < newLength){
            afterDecimal[j] = initial[decPos + j + 1];                          if(log){System.out.println("iterator = " + j + "   char = " + initial[decPos + j + 1]);}
            j++;
        }

        return afterDecimal;
    }

    /**
     * For internal use.
     * This method is not private for jUnit testing purposes.
     */
    public char[] manipulateDecimalPortionOfNumber(char[] initial, int numDecPl) {
        char[] result = new char[numDecPl];
        
        for(int i = 0; i < numDecPl; i++ ){result[i] = 'x';}

        if (initial.length == numDecPl){
            return initial;
        }
        
        if (initial.length < numDecPl){
            int j = 0;
            while(j < initial.length){
                result[j] = initial[j];
                j++;
            }
            while(j < numDecPl){
                result[j] = '0';
                j++;
            }
            return result;
        }
        
        if (initial.length > numDecPl){
            int j = 0;
            while(j < numDecPl){
                result[j] = initial[j];
                j++;
            }
            return result;
        }
        return result;
    } // end method

    private char getDecimalSeparator() {
        if (decimalPointSeparator){
            return '.';
        } else {
            return ',';
        }
    }
    
    private char getThousandsSeparator(){
        char thousandsSeparator = 'x';
        if (commaThousandsSeparator) {
            thousandsSeparator = ',';
        } else {
            thousandsSeparator = '.';
        }
        return thousandsSeparator;
    }
    
    /**
     * Formats a char[] representing a number into one formatted with
     * thousands separators.
     * </br></br>
     * For internal use.
     * This method is not private for jUnit testing purposes.
     */
    public char[] formatThousandsAsCharArray(char[] test) {
        initialArray = test;
        initialLength = test.length;                                            if (log) {System.out.println("initialLength = " + initialLength);}

        if (initialLength < 4) {                                                if (log) {System.out.println("Formatted String = " + new String(test)); System.out.println("");}
            return test;
        }
        
// Truth Table:
// 3 -> 0,         (length - 1) / 3
// 4, 5, 6 -> 1,        
// 7, 8, 9 -> 2
        
        numberOfThousandsSeparators = (initialLength - 1) / 3;                  if (log) {System.out.println("number of separators = " + numberOfThousandsSeparators);}
        
        finalLength = initialLength + numberOfThousandsSeparators;              if (log) {System.out.println("finalLength = " + finalLength);}
        
        finalArray = new char[finalLength];
        for (int i = 0; i < finalLength; i++) {
            finalArray[i] = ' ';
        }                                                                       if (log) {System.out.println("comma separator = " + getThousandsSeparator()); System.out.println("");        }

// Back fill the array.
        int j = finalLength - 1; int k = initialLength - 1 ; int l = 0;         // 11, 9, 0
        while (j > -1){                                                         if(log){System.out.println(j + " " + k + " " + l);}
            if(l > 2){
                finalArray[j] = getThousandsSeparator();
                j--; l = 0;
            }
            finalArray[j] = initialArray[k];
            j--; k--; l++;
        }
        
        return finalArray;
    } // end method
    
    public String forTestingOnly_FormatScientificNumbers(String number, String key){
        initialArray = number.toCharArray(); 
        initialLength = number.length();
        decimalNumberStr = "Not Assigned.";
        
        if (key.equals("7867")) {
            formatScientificNumbers();
        }
        if (log) {
            System.out.println("decimalNumberStr = " + decimalNumberStr);
        }
        return decimalNumberStr;
    }

    private String decimalNumberStr;
    
    private void formatScientificNumbers() {
        // already set: initialArray = str.toCharArray(); initialLength = str.length();   
        
// 1. Check for E.
        boolean has_E = false;
        boolean isNegativePower = false;
        int powerValueStartIdx = 0;
        int ePosition = 0;
        int powerValueLength = 0;
        
        for (int i = 0; i < initialLength; i++) {
            if (initialArray[i] == 'E'){
                ePosition = i;
                has_E = true;
                break;
            }
        }
        
        if (log) {
            System.out.println("001.   ePosition is = " + ePosition + ".  has_E = " + has_E + "\n");
        }
        
        if (!has_E) {
            return;
        }
        
// Get digits only        
        char[] digitsOnly = new char[ePosition - 1];
        int digitsOnlyCounter = 0;
        
        for (int i = 0; i < ePosition; i++) {
            if (initialArray[i] != '.') {
                digitsOnly[digitsOnlyCounter] = initialArray[i];
                digitsOnlyCounter++;
            }
        }
        
        if (log) {
            System.out.println("Digits only = " + new String(digitsOnly) + "\n");
        }

// See if power is negative, get start idx of power, get number of power digits.        
        if (initialArray[ePosition + 1] == '-') {
            isNegativePower = true;
            powerValueStartIdx = ePosition + 2;
        } else {
            isNegativePower = false;
            powerValueStartIdx = ePosition + 1;
        }
        
        powerValueLength = initialLength - powerValueStartIdx;
        
        if (log) {
            System.out.println("002.\nMethod values: \n     isNegativePower =  " + isNegativePower + "\n     ePosition = " + ePosition + "\n     powerValueLength = " + powerValueLength + "\n");
        }
                
// Get power abs value.
        
        char[] powerValue = new char[powerValueLength];
        int pvIdx = 0;
        int powerInt;
        String powerStr;
        
        for (int i = powerValueStartIdx; i < initialLength; i++) {
            powerValue[pvIdx] = initialArray[i];
            pvIdx++;
        }
        
        powerStr = new String(powerValue);
        powerInt = Integer.parseInt(powerStr);
        
        if (log) {
            System.out.println("003. power abs value = " + powerInt + "\n");
        }
        
        char[] zeros;
        
        if (isNegativePower) {
            int zerosLen = powerInt + 1;
            
// Set the value digits at the end of the array.            
            
            char[] output = new char[powerInt + 1 + digitsOnly.length];
            for (int i = 0; i < output.length; i++) {
                output[i] = '0';
            }
            
            output[1] = '.';

            for (int i = 0; i < digitsOnly.length; i++) {
                output[powerInt + 1 + i] = digitsOnly[i];
            }
            
            initialArray = output;
            initialLength = initialArray.length;
            
            decimalNumberStr = new String (output);
            
            if (log) {
                System.out.println("004. output array = " + decimalNumberStr +"\n");
            }
        } // end if
    } // end method
    
} // end class
