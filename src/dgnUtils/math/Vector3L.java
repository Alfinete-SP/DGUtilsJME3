/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dgnUtils.math;

//import java.math.BigInteger;

/**
 * This class (Vector3L) is intended to be used as an absolute position in 3D
 * space.  It is not intended for 3D maths.  Any 3D maths should be done with
 * Vector3d, which uses double values.
 * 
 * There are no methods in this class to multiply or scale the vector contained
 * in this instance.
 * 
 * @author David Nicholson.
 * 
 * 2017-02-22
 * 
 */
public class Vector3L {
    
    private long x, y, z;
    private boolean overSizedX, overSizedY, overSizedZ, overSizedAny;
    //private BigInteger xBI, yBI, zBI;

    // CONSTRUCTOR ONE
    public Vector3L(){
        x = 0L;
        y = 0L;
        z = 0L;
    }

    // CONSTRUCTOR TWO
    public Vector3L(long lx, long ly, long lz) {
        x = lx;
        y = ly;
        z = lz;
    }
    
    /**
     * In a few cases, the Vector3d input may have values that are larger than 
     * the long primitive can represent,  i.e. values larger than 2^63.
     * In this case the conversion is carried out as normal, but booleans are set in the 
     * following way:
     * 1. overSizedAny will set if any of the x, y or z values are too big.
     * 2. overSizedX for x, overSizedY for y and overSizedZ for z.
     * 
     * The programmer will have to work out what to do in case the booleans are set.
     * Maybe throw an exception or find a work around.
     * 
     * 
     * @param v1 a Vector3d instance.
     * @return a new Vector3L instance.
     */
    public static Vector3L toVector3L(Vector3d v1) {
        double xD, yD, zD, maxValue;
        long xL, yL, zL;
        boolean overX, overY, overZ, overAny;
        overX = false;
        overY = false;
        overZ = false;
        overAny = false;
        
        xD = v1.getX();
        yD = v1.getY();
        zD = v1.getZ();
        
        maxValue = (double) Long.MAX_VALUE;
        
        if (xD > maxValue) {
            xD = maxValue;
            overX = true;
            overAny = true;
            
        }
        if (yD > maxValue) {
            yD = maxValue;
            overY = true;
            overAny = true;
        }
        if (zD > maxValue) {
            zD = maxValue;
            overZ = true;
            overAny = true;
        }
        
        xL = (long) xD;
        yL = (long) yD;
        zL = (long) zD;
        
        Vector3L vR; 
        vR = new Vector3L(xL, yL, zL);
        vR.setOverSizedX(overX);
        vR.setOverSizedY(overY);
        vR.setOverSizedZ(overZ);
        vR.setOverSizedAny(overAny);
        
        return vR;
    }
    
    /**
     * returns an equivalent Vector3d instance from a Vector3L instance.
     * 
     * @param v1 a Vector3L instance.
     * @return a Vector3d instance.
     */
    public static Vector3d toVector3d(Vector3L v1) {
        long xL, yL, zL;
        double xD, yD, zD;
        Vector3d result;
        
        xL = v1.getX();
        yL = v1.getY();
        zL = v1.getZ();
        
        xD = (double) xL;
        yD = (double) yL;
        zD = (double) zL;
        
        result = new Vector3d(xD, yD, zD);
        return result;
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    public long getZ() {
        return z;
    }

    /**
     * New vector instance is returned.  The opperand vectors are not affected.
     * @param v2 - a Vector3L instance to add to this instance vector.
     * @return a new Vector3L instance.
     */
    public Vector3L add(Vector3L v2) {
        long x2, y2, z2, xR, yR, zR;
        x2 = v2.getX();
        y2 = v2.getY();
        z2 = v2.getZ();
        xR = x + x2;
        yR = y + y2;
        zR = z + z2;       
        Vector3L result = new Vector3L(xR, yR, zR);
        return result;
    }

    /**
     * New vector instance is returned.  The opperand vectors are not affected.
     * @param v2 - a Vector3L instance to subtract from this instance vector.
     * @return a new Vector3L instance. 
     */
    public Vector3L subtract(Vector3L v2) {
        long x2, y2, z2, xR, yR, zR;
        x2 = v2.getX();
        y2 = v2.getY();
        z2 = v2.getZ();
        xR = x - x2;
        yR = y - y2;
        zR = z - z2;       
        Vector3L result = new Vector3L(xR, yR, zR);
        return result;
    }
    
    
    /**
     * scales a Vector3L instance by a double value and returns a NEW Vector3L instance.
     * 
     * Will set overSized booleans for the new instance if calculated values
     * go above Long.MAX_SIZE or below Long.MIN_SIZE.
     * 
     * Programmers should check these
     * when oversized calculations can happen 
     * and deal with this appropriately. <br>
     * i.e. <br>
     * <textarea readonly rows="5" cols= "50">
     * 
     * if (v1.overSizedAny == true){
     *      doSomething();
     *  }
     * </textarea>
     * 
     * @param scaleFactor
     * @return 
     */
    public Vector3L scale(double scaleFactor) {
        Vector3L result;
        double xD1, yD1, zD1, xDR, yDR, zDR, maxValue, minValue;
        long   xLR, yLR, zLR;
        boolean overX, overY, overZ, overAny;
        overX = false;
        overY = false;
        overZ = false;
        overAny = false;
        
        xD1 = (double)x;
        yD1 = (double)y;
        zD1 = (double)z;
        
        xDR = xD1 * scaleFactor;
        yDR = yD1 * scaleFactor;
        zDR = zD1 * scaleFactor;
        
         maxValue = (double) Long.MAX_VALUE;
         minValue = (double) Long.MIN_VALUE;
        
        if (xDR > maxValue) {
            xDR = maxValue;
            overX = true;
            overAny = true;
            
        }
        if (yDR > maxValue) {
            yDR = maxValue;
            overY = true;
            overAny = true;
        }
        if (zDR > maxValue) {
            zDR = maxValue;
            overZ = true;
            overAny = true;
        }
        
        if (xDR < minValue) {
            xDR = minValue;
            overX = true;
            overAny = true;
        }
        
        if (yDR < minValue) {
            yDR = minValue;
            overY = true;
            overAny = true;
        }
        
        if (zDR < minValue) {
            zDR = minValue;
            overZ = true;
            overAny = true;
        }
        
        xLR = (long) xDR;
        yLR = (long) yDR;
        zLR = (long) zDR;
        
        result = new Vector3L(xLR, yLR, zLR);
        result.setOverSizedX(overX);
        result.setOverSizedY(overY);
        result.setOverSizedZ(overZ);
        result.setOverSizedAny(overAny);
        
        return result;
    }    
    
    
    /**
     * String representation of Vector.  Useful for comparing vectors
     * for unit testing purposes. 
     * 
     * @return String representation of the vector.
     */
    @Override
    public String toString(){
        String str = "X = " + x + ", Y = " + y + ", Z = " + z;
        return str;
    }
    
    /**
     * returns a totally separate Vector3L clone of the Vector3L instance supplied.
     * 
     * @return a Vector3L instance.
     */
    public Vector3L cloneVector(){
        long xR, yR, zR;
        xR = x;
        yR = y;
        zR = z;
        Vector3L vR = new Vector3L(xR, yR, zR);
        return vR;
    }
    
    /**
     * getter for overSized boolean.
     * 
     * @return 
     */    
    public boolean getOverSizedX(){
        return overSizedX;
    }
    
    /**
     * getter for overSized boolean.
     * 
     * @return 
     */
    public boolean getOverSizedY(){
        return overSizedY;
    }
    
    /**
     * getter for overSized boolean.
     * 
     * @return 
     */
    public boolean getOverSizedZ(){
        return overSizedZ;
    }
    
    /**
     * getter for overSized boolean.
     * 
     * @return 
     */
    public boolean getOverSizedAny(){
        return overSizedAny;
    }

    private void setOverSizedX(boolean overX) {
        overSizedX = overX;
    }

    private void setOverSizedY(boolean overY) {
        overSizedY = overY;
    }

    private void setOverSizedZ(boolean overZ) {
        overSizedZ = overZ;
    }
    
    private void setOverSizedAny(boolean overAny) {
        overSizedAny = overAny;
    }

    public void setX(long x1) {
        x = x1;
    }

    public void setY(long y1) {
        y = y1;
    }
    
    public void setZ(long z1) {
        z = z1;
    }

    public Vector3d convertToVector3d() {
        return new Vector3d((double)x, (double)y, (double)z);
    }

    
}


// =======================================
// VECTORS CANNOT BE MULTIPLIED LIKE THIS.
// =======================================    
//    /**
//     * A new BigInteger array is returned.  It has three elements.
//     * idx 0 is the X value,
//     * idx 1 is the Y value,
//     * idx 2 is the Z value.
//     * 
//     * The opperand vectors are not affected.
//     * @param v2 - a Vector3L instance to multiply this instance vector by.
//     * @return a new Vector3L instance. 
//     * 
//     * Note:  This method is very slow when compared to the method that uses doubles.
//     */
//    public BigInteger[] multiplyAsBigInt(Vector3L v2) {
//        BigInteger x1, y1, z1, x2, y2, z2, longMax, xBI, yBI, zBI;
//        long xR, yR, zR;
//        x1 = BigInteger.valueOf(x);
//        y1 = BigInteger.valueOf(y);
//        z1 = BigInteger.valueOf(z);
//        
//        x2 = BigInteger.valueOf(v2.getX());
//        y2 = BigInteger.valueOf(v2.getY());
//        z2 = BigInteger.valueOf(v2.getZ());
//        
//        xBI = x1.multiply(x2);
//        yBI = y1.multiply(y2);
//        zBI = z1.multiply(z2);
//        
//        BigInteger[] result = new BigInteger[]{xBI, yBI, zBI};
//        
//        return result;
//    }
//
//    /**
//     * A new double array is returned.
//     * idx 0 is the X value,
//     * idx 1 is the Y value,
//     * idx 2 is the Z value.
//     * 
//     * The opperand vectors are not affected.
//     * @param v2
//     * @return 
//     */
//    public double[] multiplyAsDouble(Vector3L v2) {
//        double x1, y1, z1, x2, y2, z2, xD, yD, zD;
//        
//        x1 = (double) x;
//        y1 = (double) y;
//        z1 = (double) z;
//        
//        x2 = (double) v2.getX();
//        y2 = (double) v2.getY();
//        z2 = (double) v2.getZ();
//        
//        xD = x1 * x2;
//        yD = y1 * y2;
//        zD = z1 * z2;
//        
//        double [] result = new double []{xD, yD, zD};
//        
//        return result;
//    }
//
//

