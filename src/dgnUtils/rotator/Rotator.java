/*
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dgnUtils.rotator;

import dgnUtils.exception.IrregularFieldException;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * The direction of the rotation:         <br>
 *    -1 is clockwise.                    <br>
 *    +1 is counter clockwise.            <br><br>
 * 
 * This class is intended to rotate a single spatial around a single axis.  It is
 * intended for uses such as rotating engines or instrument dials, things which are
 * fixed in one place but rotate back and forth.  
 * <br><br>
 * Items that rotate on many axes at once should probably use the Rotate3D class.
 * <br><br>
 * Rotater takes a Spatial and a Vector3f (the rotation axis) when it is created.  
 * Whenever the rotate() method is called, the spatial held in the object will 
 * be rotated on the axis provided.  This avoids calling 
 * the rotator.rotate() against the wrong object or on the wrong axis. 
 * <br><br>
 * To rotate the object, just call rotate(tpf) once all the fields have been set up.
 * <br><br>
 * @author David Niicholson  /  August 2014
 */
public class Rotator {
    
    private boolean log = false;
    //private boolean log = true;
    
    private boolean rotationFinished;
    private int dir, originalDir;
    
    private float startDirection;
    private float finishDirection;
    private float spatialDirection;
    private float speed;
    private float compare_neg, compare_pos; // used for comparing the shortest distance to rotate through.                                                                                                
    private float fullTurn = 2 * FastMath.PI;
    private float tempDir;    
    
    private Spatial spatial = null;
    private Vector3f axis = null;
    private Quaternion qRotation = new Quaternion();
    private String thisInstanceName = null;
    
    
    /**
     * Basic constructor which sets startDirection = 0f, 
     * finishDirectin = 0f, speed = 1f, and dir = 1f.  
     * The Spatial and the axis are set to the object in the constructor to avoid
     * error where you could call the object against the wrong spatial or wrong axis.
     * 
     * @param Spatial s  
     */
    public Rotator(Spatial s, Vector3f axis1){
        startDirection = 0f;
        finishDirection = 0f;
        speed = 1f;
        dir = 1;
        spatial = s;
        axis = axis1;
        rotationFinished = false;
        try {
            checkLegitimateDirection();
        } catch (IrregularFieldException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        recalibrateDirectionLimits();
        if (log) {
            System.out.println("Axis =   x:" + axis.x + "   y:" + axis.y + "   z:" + axis.z);
        }
    }
    
    // CONSTRUCTOR 2
    /**
     * A complete construction taking in all parameters to setup the object 
     * ready to call rotate().
     * 
     * @param Spatial s
     * @param float startDirection
     * @param float finishDirection
     * @param float speed
     * @param float direction 
     */
    public Rotator(Spatial s, Vector3f axis1, float startDirection, float finishDirection, float speed, int direction){
        this.spatial = s;
        this.axis = axis1;
        this.startDirection = startDirection;
        this.finishDirection = finishDirection;
        this.speed = speed;
        this.dir = direction;
        this.rotationFinished = false;
        try {
            checkLegitimateDirection();
        } catch (IrregularFieldException e) {
            System.out.println("ERROR: " + e.getMessage());
        }        
        recalibrateDirectionLimits();        
    }
    
    public void rotateThruShortestAngleFromCurrent(float finishDir, float speed1){ // dir will be be calculated.  Not needed to be passed in.                                                                                               
        rotationFinished = false;
        originalDir = dir;
        speed = speed1;
        startDirection = spatialDirection;
        finishDirection = finishDir;
        if (log) {
            System.out.println("startDir = " + startDirection + "   finishDir = " + finishDirection);
        }

        // Test angle when direction = -1
        dir = -1;
        recalibrateDirectionLimits();
        compare_neg = startDirection - finishDirection;
        
        // Test angle when direction = +1
        dir = 1;
        recalibrateDirectionLimits();
        compare_pos = finishDirection - startDirection;
        
        if(compare_pos == compare_neg ){
            dir = originalDir;
        }
        
        if(compare_pos < compare_neg){
            dir = 1;
            recalibrateDirectionLimits();
        } else {
            dir = -1;
            recalibrateDirectionLimits();
        }
        
        if (log) {
            System.out.println(" compare_neg = " + compare_neg + "   compare_pos = " + compare_pos + "   dir = " + dir + "   originalDir = " + originalDir);
            System.out.println("startDir = " + startDirection + "   finishDir = " + finishDirection);
        }
        
        
        
    }

    /**
     * Sets the initial direction (in radians) that the spatial is pointing 
     * before any rotations take place.
     * 
     * @param float startDirection 
     */
    public void setStartDirection(float startDirection) {
        this.startDirection = startDirection;
        rotationFinished = false;
        recalibrateDirectionLimits();        
    }

    /**
     * Sets the final direction (in radians) that the spatial will point once all the
     * rotations have occured.  This is the limit switch to stop the rotation.
     * 
     * @param float finishDirection 
     */
    public void setFinishDirection(float finishDirection) {
        this.finishDirection = finishDirection;
        rotationFinished = false;
        recalibrateDirectionLimits();        
    }
    
    /**
     * A convenience method to setup a new rotation to a new direction without
     * reinitializing the object.  The rotation will start either from its current
     * position while in rotation or its stopped position.
     * 
     * Speed and direction are required to avoid logical errors.
     * 
     * @param float nextDir
     * @param float speed
     * @param float dir 
     */
    public void setNextDirection(float nextDir, float speed, int dir){
        startDirection = finishDirection;
        finishDirection = nextDir;
        this.speed = speed; 
        this.dir = dir;
        rotationFinished = false;
        try {
            checkLegitimateDirection();
        } catch (IrregularFieldException e) {
            System.out.println("ERROR: " + e.getMessage());
        }        
        recalibrateDirectionLimits();        
    }

    /**
     * Sets the direction of the rotation: <br>
     * -1 is clockwise.                    <br>
     * +1 is counter clockwise.            <br><br>
     * 
     * @param float i 
     */
    public void setDir(int i) {
        this.dir = i;
        try {
            checkLegitimateDirection();
        } catch (IrregularFieldException e) {
            System.out.println("ERROR: " + e.getMessage());
        }        
        rotationFinished = false;
        recalibrateDirectionLimits();        
    }

    /**
     * Sets the rotation speed.  This value is multiplied by tpf (time per frame)
     * and ensures a smooth rotation speed. <br><br>
     * A value of 1 will guarantee a rotation of one radian per second.
     * 
     * @param float i 
     */
    public void setSpeed(float i) {
        this.speed = i;
    }

    /**
     * This is the method that does the rotating of the spatial.  <br><br>
     * 
     * It should be called in the update loop of the the main App class, 
     * or in a control, or in an appstate instance.
     * 
     * @param float tpf (generated by the jMonkeyEngine App instance.)
     */
    public void rotate(float tpf) {
        if(rotationFinished){
            return;
        } else {
            spatialDirection = spatialDirection + (dir * speed * tpf);
            qRotation.fromAngleAxis(spatialDirection, axis);
            spatial.setLocalRotation(qRotation);
            
            if(log){
                tempAngle = spatial.getLocalRotation().toAngleAxis(tempVector);
                System.out.println("Rotator: angle =  " + tempAngle + " .  axis = " + tempVector);
            }
        }
        if(spatialDirection > finishDirection && dir == 1 ){ // values will increase
            rotationFinished = true;
        }
        if(spatialDirection < finishDirection && dir == -1 ){ // values will decrease
            rotationFinished = true;
        }
    }
    
    Vector3f tempVector = new Vector3f();
    float    tempAngle = 0f;
    
    public boolean getRotationFinished(){
        return rotationFinished;
    }
    


    public void setLog(boolean b){
        log = b;
    }

    private void recalibrateDirectionLimits() {
        if(log){System.out.println("startDir = " + startDirection + "  finishDir =  " + finishDirection);}
        if(dir == 1){ // rotate anti clockwise, values will increase.
            if(startDirection > finishDirection){ // startDirection must be smaller.
                startDirection -= fullTurn;
            } 
        }
        
        if(dir == -1){  // rotate clockwise, values will decrease.
            if(startDirection < finishDirection){ // startDirection must be greater.
                startDirection += fullTurn;
            }
        }
    }
    
    
    public void setNameOfThisInstance(String s){
        thisInstanceName = s;
        if (s == null){
            thisInstanceName = "[no name]";
        }
    }
    
    private void checkLegitimateDirection() throws IrregularFieldException{
        if(dir == 1 || dir == -1){
            //return;  //do nothing
        } else {
            dir = 1;
            throw new IrregularFieldException("Direction int must be 1 or -1 in Rotator instance.   Instance name is: " + thisInstanceName);
        }
    }

    public int getDir() {
        return dir;
    }

}
