/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dgnUtils.number;

/**
 * This class was written to format numbers used in output for the camera position
 * and direction.  There may be cases where other elements require 
 * similar formatting.
 *
 * @author David Niicholson
 */
public class SpatialPositionAndDirectionNumberUtils {

    /**
     * Potential problems.  Large numbers being truncated.  i.e. 3000000 becomes 30000
     * 
     * @param num
     * @param requiredLength
     * @return 
     */
    public String setNumberOfDigits(float num, int requiredLength) {
        int lengthToComplete;
        char[] initialArray = null, array0 = null;
        String numberString = null, tempStr = null;
        
        tempStr = String.valueOf(num);

        int tempStringlength = tempStr.length();
        if (tempStringlength < requiredLength) {
            initialArray = tempStr.toCharArray();
            lengthToComplete = requiredLength - initialArray.length;
            array0 = new char[requiredLength];
            for (int i = 0; i < tempStringlength; i++) {
                array0[i] = initialArray[i];
            }
            for (int j = tempStringlength; j < requiredLength; j++) {
                array0[j] = '0';
            }
            numberString = new String(array0);
        } else {
            numberString = tempStr.substring(0, requiredLength);
        }
        return numberString;
    }

    private boolean log = false;
    
    /**
     * Values cannot be greater than 1.0 or less than -1.0
     * 
     * @param num
     * @param length
     * @return String formatted as 0.056+ or 0.345-
     */
    public String getCameraDirectionFormattedString(float num) {
        String result, tempStr;
        result = null;
        
        float absValue;
        if (num > -1 && num < 1) { // number is between -1 and +1.
            if (num < 0){ // number is negative
                absValue = num * -1; 
                if (absValue < 0.001f) {
                    result = "0.000-";
                    return result;
                }
            } else{
                absValue = num;
            }
            if (absValue < 0.001f) {
                result = "0.000+";
                return result;
            }
        } 
        
        if(num < -1f){
            result = " < 1-";
            return result;
        }
        
        if(num > 1f){
            result = " > 1+";
            return result;
        }
        
        char[] initialArray, array0, array1;
        
        array0 = new char[6];
        
        tempStr = String.valueOf(num);
        if(log){System.out.println("tempStr = " + tempStr);}
        initialArray = tempStr.toCharArray();
        
        if(initialArray[0] == '-'){
            if(log){System.out.println("string starts with negative.");}
            if (initialArray.length < 7) { 
                if(log){System.out.println("string has less than seven digits.");}
                if(log){System.out.println("initialArray.length() is " + initialArray.length);}
                // copy array data and fill rest with zeros.
                for (int i = 1; i < initialArray.length; i++) {
                    if(log){System.out.println("index1 is : " + i);}
                    array0[i-1] = initialArray[i]; // in array0 we want idx 0, in initial we want idx 2
                }
                for (int i = initialArray.length; i < array0.length + 1; i++) {
                    array0[i - 1] = '0';
                }
                array0[array0.length - 1] = '-';
            } else {
                if(log){System.out.println("string has more than six digits.");}
                for (int i = 0; i < array0.length; i++) {
                    array0[i] = initialArray[i+1];
                }
                array0[array0.length - 1] = '-';
            }
        } else { // number is positive, does not have a negative sign at idx 0.
            if(log){System.out.println("string does not start with negative.");}
            if (initialArray.length < 7) { 
                if(log){System.out.println("string has less than seven digits.");}
                if(log){System.out.println("initialArray.length() is " + initialArray.length);}
                // copy array data and fill rest with zeros.
                for (int i = 0; i < initialArray.length; i++) {
                    if(log){System.out.println("index1 is : " + i);}
                    array0[i] = initialArray[i]; // in array0 we want idx 0, in initial we want idx 2
                }
                for (int i = initialArray.length; i < array0.length; i++) {
                    array0[i] = '0';
                }
                array0[array0.length - 1] = '+';
            } else {
                if(log){System.out.println("string has more than six digits.");}
                for (int i = 0; i < array0.length; i++) {
                    array0[i] = initialArray[i];
                }
                array0[array0.length - 1] = '+';
            }
        }
        
        result = new String(array0);
                
        return result;
        
    }
    
    public void setLog(boolean log1){
        log = log1;
    }

    public String getCamerPositionFormattedString(float position) {
        String finalStr = null, tempStr ;
        
        if (position > 9999f) {
            finalStr = "++++.+++";
            return finalStr;
        }
        
        if (position < -9999f){
            finalStr = "----.---";
            return finalStr;
        }
        
        tempStr = String.valueOf(position);
        
        char[] initialArray;
        char[] beforeDecimal, afterDecimal;
        initialArray = tempStr.toCharArray();
        
        char signChar;
        if (position < 0) {
            signChar = '-';
            initialArray[0] = '0';
        } else {
            signChar = '+';
        }
        
        int decPointPos = 0;
        for (int i = 0; i < initialArray.length; i++) {
            if (initialArray[i] == '.') {
                decPointPos = i;
                if(log){System.out.println("decPointPos = " + i);}
                break;
            }
        }
        // GET TWO PARTIAL ARRAYS.
        beforeDecimal = new char[decPointPos];
        for (int i = 0; i < decPointPos ; i++) {
            beforeDecimal[i] = initialArray[i];
        }
        if (log) { System.out.println("beforeDecimal = " + new String(beforeDecimal)); }
        
        int p1 = 0;
        afterDecimal = new char[2];
        for (int i = 0; i < 2; i++) {
            afterDecimal[i] = '0';
        }
        for (int i = 0; i < 2; i++) {
            p1 = i + decPointPos + 1;
            if (p1 + 1 > initialArray.length  ) {
                break;
            }
            afterDecimal[i] = initialArray[p1];
        }
        if (log) {System.out.println("p1 = " + p1 + "  initialArray.length() = " + initialArray.length ); }
        
        if (log) { System.out.println("afterDecimal = " + new String(afterDecimal)); }
        // FIT CHARS INTO FINAL ARRAY IN THE RIGHT POSITION.
        char[] finalArray = new char[8];;
        for (int i = 0; i < 8; i++) {
            finalArray[i] = '0';
        }
        if (log) {System.out.println("finalArray (all zeros) = " + new String(finalArray));}
        for (int i = 0; i < 4; i++) {
            p1 = beforeDecimal.length - i - 1;
            finalArray[3 - i] = beforeDecimal[p1];
            if (p1 == 0) {
                break;
            }
        }
        if (log) { System.out.println("finalArray (initial digits) = " + new String(finalArray)); }
        finalArray[4] = '.';
        for (int i = 0; i < 2; i++) {
            finalArray[i + 5] = afterDecimal[i];
        }
        finalArray[7] = signChar;
        
        finalStr = new String(finalArray);
        if (log) {System.out.println("finalStr = " + finalStr); }
        return finalStr;
    }
    
}



        
        
        
        