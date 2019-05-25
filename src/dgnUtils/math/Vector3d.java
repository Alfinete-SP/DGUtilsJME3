/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dgnUtils.math;

import com.jme3.math.Vector3f;

/**
 * Vector3d is a vector object where X, Y and Z are double precision.  
 * If a vector object is needed with float precision, then use Vector3f.
 * 
 * @author David Nicholson
 * 30/09/2015
 */
public class Vector3d {

    // CLASS VARIABLES:
    private double x;
    private double y;
    private double z;
    
    private double modulus;
    private double xNorm;
    private double yNorm;
    private double zNorm;

    private double[] modulusData;
    
    private boolean vectorDataCalculated;
    private int tracker = 0;
    
    /**
     * Creates a Vector3d object where x, y and z are all set to 0.0
     */
    public Vector3d(){
        x = 0.0;
        y = 0.0;
        z = 0.0;
        vectorDataCalculated = false;  // this boolean says if the method getModulusAndVectorData() has been called.                                                                                                                 
        modulusData = new double[7];   // initialize modulusData array.
        
    }
    
    /**
     * Creates a Vector3d object where the X, Y and Z values are specified.
     * 
     * @param x1 double precision X value.
     * @param y1 double precision Y value.
     * @param z1 double precision Z value. 
     */
    public Vector3d(double x1, double y1, double z1) {
        x = x1;
        y = y1;
        z = z1;
        vectorDataCalculated = false;  // this boolean says if the method getModulusAndVectorData() has been called.                                                                                                                 
        modulusData = new double[7];   // initialize modulusData array;
    }

    /**
     * Returns the X parameter of the Vector.
     * 
     * @return X as a double
     */
    public double getX() {
        return x;
    }

    /**
     * Set the X parameter of the Vector.
     * @param x (double)
     */
    public void setX(double x) {
        this.x = x;
        vectorDataCalculated = false;  // this boolean says if the method getModulusAndVectorData() has been called.                                                                                                                 
    }

    /**
     * Returns the Y parameter of the Vector.
     * 
     * @return Y as a double
     */    
    public double getY() {
        return y;
    }

    /**
     * Set the Y parameter of the Vector.
     * 
     * @param y (double)
     */
    public void setY(double y) {
        this.y = y;
        vectorDataCalculated = false;  // this boolean says if the method getModulusAndVectorData() has been called.                                                                                                                 
    }
    
    /**
     * Returns the Z parameter of the Vector.
     * 
     * @return Z as a double
     */
    public double getZ() {
        return z;
    }

    /**
     * Set the Z parameter of the Vector.
     * 
     * @param z (double)
     */
    public void setZ(double z) {
        this.z = z;
        vectorDataCalculated = false;  // this boolean says if the method getModulusAndVectorData() has been called.                                                                                                                 
    }
    
    /**
     * Converts the Vector3d into a Vector3f.  Precision can be lost.
     * 
     * @return Vector3f convertedVector3f
     */
    public Vector3f getVector3f(){
        float xf = (float)x;
        float yf = (float)y;
        float zf = (float)z;
        return new Vector3f(xf, yf, zf);
    }

    /**
     * Scales a Vector3d up or down according to the double precision value passed in.  <br>
     * Any value greater than 1.0 will scale up. <br>
     * Any value less than 1.0 will scale down. <br>
     * 
     * @param zoomFactor a double precision value.
     * @return Vector3d a new scaled Vectore3d object.
     */
    public Vector3d scale(double zoomFactor) {
        return new Vector3d(x * zoomFactor, y * zoomFactor, z * zoomFactor);
    }
    
    /**
     * Returns a String representation of the Vector3d object.  The output will be something like:  <br>
     * (Vector3d)  X: 0.00401, Y: 1.3962, Z: 6.9998
     * 
     * @return String
     */
    @Override
    public String toString(){
        return "(Vector3d)  X: " + x + ", Y: " + y + ", Z: " + z;
    }

    /**
     * Subrtracts the supplied Vector3d from the one in the current instance "this".
     * <br> <br>
     * Formula used is: Vector3d(result) = Vector3d(this) - Vector3d(supplied)
     * <br>
     * 
     * @param subtractedVector3d an instance of a Vector3d
     * @return Vector3d - a new Vector3d which is the difference between the current
     * vector3d instance and the supplice Vector3d.
     */
    public Vector3d subtract(Vector3d subtractedVector3d) {
        double subX = subtractedVector3d.getX();
        double subY = subtractedVector3d.getY();
        double subZ = subtractedVector3d.getZ();
        
        double resultX = this.x - subX;
        double resultY = this.y - subY;
        double resultZ = this.z - subZ;
        
        return new Vector3d (resultX, resultY, resultZ);
    }
 
    /**
     * Returns an array of doubles that contains data concerning the
     * current vector's modulus, x value, y value, z value, x value normalized,
     * y value normalized and z value normalized.  
     * <p>
     * The reasoning is to avoid multiple
     * calls performing square root operations to get multiple values.  Here, only
     * <u><b>ONE</b></u> square root operation is done to get ALL required vector paramater values.
     * <p>  
     * Values returned are in reference to the Vector3d referenced by "this".  
     * i.e. the current Vector3d instance.
     * <p>
     * VALUES ARE:
     * <p>
     * At index [0] is the modulus. <br>  
     * At index [1] is the x value.<br>  
     * At index [2] is the y value.<br>  
     * At index [3] is the z value.<br>  
     * At index [4] is the x value normalized.<br>  
     * At index [5] is the y value normalized.<br>  
     * At index [6] is the z value normalized.<br>  
     * <br>
     * Maximum index value is 6.
     * 
     * @return array of seven doubles.
     */
    public double[] getModulusAndVectorData() {
        if (vectorDataCalculated) {
            tracker = 444554;         // tracker shows program flow to test methods.
            return modulusData;
        }
        double modulusSquared = (x * x) + (y * y) + (z * z);
        modulus = Math.sqrt(modulusSquared);
        xNorm = x / modulus;
        yNorm = y / modulus;
        zNorm = z / modulus;
        
        modulusData = new double[]{modulus, x, y, z, xNorm, yNorm, zNorm};
        
        vectorDataCalculated = true;  // this boolean says if the method getModulusAndVectorData() has been called.                                                                                                                 
        tracker = 9091;               // tracker shows program flow to test methods.
        return modulusData;
    }
    

    
    /**
     * Prints out the Vector3d data in the double array as returned from the
     * getModulusAndVectorData() method.
     * <p>
     * Fields printed are: <br>
     * 1. Modulus <br>
     * 2. X (full value)<br>
     * 3. Y (full value) <br>
     * 4. Z (full value) <br>
     * 5. X normalized <br>
     * 6. Y normalized <br>
     * 7. Z normalized <br>
     *  <br>
     * @param vectorNamedInstance a name that you want to call the vector being parametized.  This name will
     * come out in the printout.  If set to null, then the String "noname" will be used.
     * @return String vectorData
     */
    public String printOutVectorData(String vectorNamedInstance){
        if (vectorDataCalculated == false) { getModulusAndVectorData(); }
        if (vectorNamedInstance == null) {
            vectorNamedInstance = "noname";
        }
        return "Vector3d Data for " + vectorNamedInstance 
                + " : modulus = " + modulus 
                + ", x = " + x 
                + ", y = " + y
                + ", z = " + z
                + ", xNorm = " + xNorm
                + ", yNorm = " + yNorm
                + ", zNorm = " + zNorm;
    }

    /**
     * Adds the Vector3d supplied to the current Vector3d instance (this).
     * 
     * @param addedVector
     * @return Vector3d resultantVector3d
     */
    public Vector3d add(Vector3d addedVector) {
        double xResult = x + addedVector.getX();
        double yResult = y + addedVector.getY();
        double zResult = z + addedVector.getZ();
        
        return new Vector3d(xResult, yResult, zResult);
    }

    
    /**
     * For testing purposes.  Not intended for production code.
     * <p>
     * There are no setters for this class variable.
     * <p>
     * The var only has package scope.
     */
    boolean isVectorDataCalculated() {
        return vectorDataCalculated;
    }
    
    /**
     * For testing purposes.  Not intended for production code.
     * <p>
     * There are no setters for this class variable.
     * <p>
     * The var only has package scope.
     */
    int getTrackerValue(){
        return tracker;
    }
    
    public static String vectorArrayToString(Vector3d[] testArray) {
        boolean log = false;
        String output, component;
        output = new String("");
        component = new String("");
        Vector3d v;
        for (int i = 0; i < testArray.length; i++) {
            v = testArray[i];
            component = v.toString() + "\n";
            output = output + component;
        }
        if (log) { System.out.println("Vector Array : \n" + output); }
        return output;
    }

    public double getKilometerModulus() {
        double m = this.getModulusAndVectorData()[0];
        double kms = m / 1000D;
        return kms;
    }

    public boolean withinDistance(double d) {
        double modSq = (x * x) + (y * y) + (z * z);
        double dSq = d * d;
        boolean isWithinDistance = modSq < dSq;
        return isWithinDistance;
    }
    
    public double getModulus(){
        return this.getModulusAndVectorData()[0];
    }
    
    
}

