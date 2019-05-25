/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dgnUtils.exception;

/**
 *
 * @author David
 */
public class NegativeDecimalPlacesException extends Exception {
    
    @Override
    public String getMessage(){
        return "Use of negative integer for decimal places is not permitted.";
    }
}
