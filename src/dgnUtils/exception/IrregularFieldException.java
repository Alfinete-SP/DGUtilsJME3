/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dgnUtils.exception;

/**
 *
 * @author David Niicholson
 */
public class IrregularFieldException extends Exception {

    String errorMsg;
    
    public IrregularFieldException() {
        java.awt.Toolkit.getDefaultToolkit().beep();
    }
    
    public IrregularFieldException(String s){
        errorMsg = s;
    }
    
    public String getMessage(){
        return errorMsg;
    }
    
    @Override
    public String toString(){
        return errorMsg;
    }
    
}
