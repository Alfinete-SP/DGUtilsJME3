/*
 *
 */
package dgnUtils.math;

import com.jme3.math.Vector3f;

/**
 * This class represents the cosmos as a 3D grid in long (64-bit) values.  The long values are kilometer units.  
 * The Position object has a second double grid whose units are in meters.
 * 
 * 1. The sun is the center.  Its location is always (0L, 0L, 0L).
 * 
 * 2. A second grid of doubles is super imposed on the grid of longs.  This gives the graduations
 *     of the km unit into meters and millimeters etc.  This position is the camera position, or
 *     the position of game assets, or the players.
 * 
 * 3. The scene graph is represented by floats and its center (origin) is always the camera.
 * 
 * 4. Each long grid unit-value is 1 km.  So the full grid is 18E18 Km wide (18E18 / 9.461E12 km) = 1.94E6 LY across
 * 
 * 5. Any unit-value double variables used in the class represent kilometer units.
 * 
 * 
 * @author David Nicholson
 * 03/08/2017
 */
public class SpaceGrid_Old_01 {
    
    private Vector3L gridLong;
    private Vector3d gridDouble;
    
    private double fullD, pontoD, wholeD;
    private long   fullL;
    
    private final double multiplier = 1000.0;

    
    /**
     * Sets an absolute position from a double precision Vector3d.  
     * 
     * ||======================================||
     * ||  Double unit value is in kilometers  ||  
     * ||                                      ||
     * ||  LONG unit values are in kilometers  ||
     * ||======================================||
     * 
     * @param positionInKilometers 
     */
    public SpaceGrid_Old_01(Vector3d positionInKilometers){
        gridLong = new Vector3L();
        gridDouble = new Vector3d();
        
        divideUpDoubleIntoLongAndDouble(positionInKilometers.getX());
        gridLong.setX(fullL);
        gridDouble.setX(fullD);
        
        divideUpDoubleIntoLongAndDouble(positionInKilometers.getY());
        gridLong.setY(fullL);
        gridDouble.setY(fullD);
        
        divideUpDoubleIntoLongAndDouble(positionInKilometers.getZ());
        gridLong.setZ(fullL);
        gridDouble.setZ(fullD);
    }
    
    /**
     * Creates a space grid postion at the origin.  Unit is kilometers.
     */
    public SpaceGrid_Old_01(){
        gridLong = new Vector3L();
        gridDouble = new Vector3d();
    }
    
    /*
    Methods to write:
    
    #. Abs distance between two objects.
        returns: double
    
    #. Within Range.
        Checks if another SpaceGridPosition obj is within a certain range.
        param: double of range check, SpaceGridPosition of required object. 
        returns: boolean
    
    #. Movement per frame.
        params: tpf, speed
        returns: SpaceGridPosition obj
    */
    
    /**
     * Private method:  
     * 
     * Divides up whole values and decimal values to different variables.
     * 
     * @param doubleInKm 
     */   
    private void divideUpDoubleIntoLongAndDouble(double doubleInKm){
        fullL  = (long)doubleInKm;       // integer value of X (1024 given)
        wholeD = (double)fullL;          // a double of the value 1024
        pontoD = doubleInKm - wholeD;    // decimal value of X as a double (64 bits) = 1024.345 - 1024
        fullD  = pontoD * multiplier;    // changes the decimal km value to meters.  Multiplier value set in field declarations to 1000.0
    }
    

    /**
     * Sets whole kilometer values (long) to the grid.  Useful for setting positions of
     * assets.  Decimal value of km is set to zero.
     * 
     * @param wholeKmPos 
     */
    public void setPositionInWholeKilometers(Vector3L wholeKmPos) {
        gridLong = wholeKmPos;
        //gridDouble = new Vector3f(0f, 0f, 0f);
        gridDouble = new Vector3d(0d, 0d, 0d);
    }

    /**
     * Sets the decimal part of the kilometer value to whatever grid position
     * is in the current object.  Unit value is in meters.
     * 
     * @param offsetInMeters 
     */
    public void setVector3f_meters(Vector3d offsetInMeters) {
        gridDouble = offsetInMeters;
    }
    
    /**
     * Gives a String representation of the current Position object's internal values.
     * 
     * @return 
     */
    @Override
    public String toString(){
        String vecL, vecD;
        vecL = gridLong.toString();
        vecD = gridDouble.toString();
        return vecL + "  +  " + vecD;
    }

    /**
     * Says if the internal values of two Position objects are exactly the same.
     * 
     * @param anotherPositionObj
     * @return isTheSame: boolean 
     */
    public boolean isSameAs(SpaceGrid_Old_01 anotherPositionObj) {
        boolean isSameLongX,  isSameLongY,  isSameLongZ, 
                isSameFloatX, isSameFloatY, isSameFloatZ,
                isSameSpaceGridPosition;
        
        isSameSpaceGridPosition = false;
        
        isSameLongX = gridLong.getX() == anotherPositionObj.getGridLong().getX();
        isSameLongY = gridLong.getY() == anotherPositionObj.getGridLong().getY();
        isSameLongZ = gridLong.getZ() == anotherPositionObj.getGridLong().getZ();
        
        isSameFloatX = gridDouble.getX() == anotherPositionObj.getGridDouble().getX();
        isSameFloatY = gridDouble.getY() == anotherPositionObj.getGridDouble().getY();
        isSameFloatZ = gridDouble.getZ() == anotherPositionObj.getGridDouble().getZ();
        
        isSameSpaceGridPosition 
                = isSameLongX
                && isSameLongY
                && isSameLongZ
                && isSameFloatX
                && isSameFloatY
                && isSameFloatZ;
        
        return isSameSpaceGridPosition;
    }

    /**
     * Returns the Vector3L of the current Position object.  This gives whole 
     * kilometer position data and no data regarding the kilometer offset in meters.
     * 
     * @return vector of whole kilometer values: Vector3L
     */
    public Vector3L getGridLong() {
        return gridLong;
    }

    /**
     * Overwrites the whole kilometer position data with a new Vector3L object.
     * It does not overwrite kilometer offset in meters.
     * 
     * @param gridLong 
     */
    public void setGridLong(Vector3L gridLong) {
        this.gridLong = gridLong;
    }

    /**
     * Returns the kilometer offset in meters.
     * 
     * @return 
     */
    public Vector3d getGridDouble() {
        return gridDouble;
    }

    /**
     * Overwrites the kilometer offset value in meters.  Does not affect the whole kilometer
     * value.
     * 
     * @param gridDouble 
     */
    public void setGridDouble(Vector3d gridDouble) {
        this.gridDouble = gridDouble;
    }

    /**
     * Overwrites whole kilometer values to the X axis of the current object.
     * 
     * @param kilometerValue: double 
     */
    public void setCompleteX(double kilometerValue) {
        divideUpDoubleIntoLongAndDouble(kilometerValue);
        gridLong.setX(fullL);
        gridDouble.setX(fullD);
    }

    /**
     * Overwrites whole kilometer values to the Y axis of the current object.
     * 
     * @param kilometerValue: double 
     */
    public void setCompleteY(double kilometerValue) {
        divideUpDoubleIntoLongAndDouble(kilometerValue);
        gridLong.setY(fullL);
        gridDouble.setY(fullD);
    }

    /**
     * Overwrites whole kilometer values to the Z axis of the current object.
     * 
     * @param kilometerValue: double 
     */
    public void setCompleteZ(double kilometerValue) {
        divideUpDoubleIntoLongAndDouble(kilometerValue);
        gridLong.setZ(fullL);
        gridDouble.setZ(fullD);
    }

    /**
     * Get distance between two SpacePosition objects.
     * 
     * @param sPosition2
     * @return distance : double
     */
    public double distanceBetween(SpaceGrid_Old_01 sPosition2) {
        double  distance;
        Vector3d v3d1, v3d2, v3dr; 

        v3d1 = getVector3d();
        v3d2 = sPosition2.getVector3d();
        v3dr = v3d1.subtract(v3d2);
        distance = v3dr.getModulus();
                
        return distance;
    }
    
    /**
     * Gets the distance of this position from the origin.  I'm not sure what this would
     * be used for, but however ...
     * 
     * @return distance from origin
     */
    public double getModulus(){
        double distance;
        Vector3d distance3D = getVector3d();
        distance = distance3D.getModulus();
        //System.out.println("Vector3d values are ... " + distance3D.toString());
        //System.out.println("Distance (in method) is ... = " + distance );
        return distance;
    }

    /**
     * Converts to a Vector3d representation of the Position object.
     * 
     * @return result : Vector3d
     */
    public Vector3d getVector3d() {
        Vector3d result = new Vector3d();
        
        fullL = gridLong.getX();     // in km
        fullD = gridDouble.getX();   // in meters
        pontoD = fullD / multiplier; // from meters to km conversion
        fullD = fullL + pontoD;      // realocates fullD to new value in meters
        result.setX(fullD);
        
        fullL = gridLong.getY();
        fullD = gridDouble.getY();
        pontoD = fullD / multiplier;
        fullD = fullL + pontoD;
        result.setY(fullD);
        
        fullL = gridLong.getZ();
        fullD = gridDouble.getZ();
        pontoD = fullD / multiplier;
        fullD = fullL + pontoD;
        result.setZ(fullD);
        
        return result;
    }

    /**
     * Gives a resulting Vector3d to arrive at another SpaceGridPosition location
     * which is taken as a parameter.
     * 
     * This is to give the delta X, Y, and Z values to another position.  
     * 
     * @param sgp2
     * @return result Vector3d
     */
    public Vector3d getVector3dTo(SpaceGrid_Old_01 sgp2) {
       double resultX, resultY, resultZ ;
       
       Vector3L vecLong2 = sgp2.getGridLong();
       Vector3d vecDoub2 = sgp2.getGridDouble();
       
       double vec2FullX, vec2FullY, vec2FullZ, thisVecFullX, thisVecFullY, thisVecFullZ;
       
       vec2FullX = vecLong2.getX() + vecDoub2.getX();
       vec2FullY = vecLong2.getY() + vecDoub2.getY();
       vec2FullZ = vecLong2.getZ() + vecDoub2.getZ();
       
       thisVecFullX = gridLong.getX() + gridDouble.getX();
       thisVecFullY = gridLong.getY() + gridDouble.getY();
       thisVecFullZ = gridLong.getZ() + gridDouble.getZ();
       
       resultX = vec2FullX - thisVecFullX;
       resultY = vec2FullY - thisVecFullY;
       resultZ = vec2FullZ - thisVecFullZ;
       
       Vector3d result = new Vector3d(resultX, resultY, resultZ);
       return result;
    }
    
    /**
     * Used to get the new camera position relative to a certain space object.
     * 
     * @param position1
     * @param position2
     * @return 
     */
    public static Vector3f subtractAndReturnDistanceInMeters_V3f(
            SpaceGrid_Old_01 position1,
            SpaceGrid_Old_01 position2){
        
        Vector3d v3d_1 = position1.getVector3d();
        Vector3d v3d_2 = position2.getVector3d();
        
        Vector3d vecS = v3d_1.subtract(v3d_2);
        //System.out.println("Vector3d in km = " + vecS);
            
        Vector3f returnVec;
        double x1, y1, z1;
        x1 = vecS.getX() * 1000.0;
        y1 = vecS.getY() * 1000.0;
        z1 = vecS.getZ() * 1000.0;
        returnVec = new Vector3f((float) x1, (float) y1, (float) z1 );
        //System.out.println("Vector3f returnVec = " + returnVec.toString());
        return returnVec;
    }
    
}
