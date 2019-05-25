/** =========
 *  WORKINGS:
 *  =========
 *
 *  Vector3L maps units in KILOMETERS.  The Vector3L object is an object that
 *      has three long varibles to map X, Y, and Z coordinates in space (in
 *      kilometers).
 *
 *  Vector3d maps units in METERS.  1000 units is a kilometer.  The Vector3d
 *      object is an object that has three double precision variables that
 *      map coordinates in space (in meters).
 *
 *
 *  ASSETS are mapped exclusively with Vector3L units ... and 
        the Vector3d value is (0.0, 0.0, 0.0)
 *
    Camera is mapped to a Vector3L object for its position in space.  
        Its offset is mapped with the Vector3d in a one-to-one
        fashion to the camera's jme3 Vector3f position 
        since the jme camera is a vector3f whose unit 
        is meters ... the same as the Vector3d in this object.
        Once the camera Vector3L position is established ... and 
        a normalize() has been called on the camera
        position, then ASSET positions can be setup with 
        reference to the camera's Vector3L position by passing the
        camera's Vector3L position to the assets concerned and 
        calling adaptToNewReferencePoint(Vector3L v3L).  This way the 
        asset's Vector3d will be a direct reference to the camera since 
        they will both have the same Vector3L reference vector postion.

        As the camera moves, its Vector3d position will start to get 
        far from its Vector3L reference point.  An arbitrary maximum
        distance value will have to be set up.  Not done yet.  Once 
        the camera's Vector3d is greater than this value, a normalize() can
        be called on the camera, and an adaptToNewReferencePoint(Vector3L v3L) 
        can be called on ASSETS within a certain playable range (say 
        upto 1000 km) ... and in space, this will not be a lot of assets, unless 
        there are a lot of ships or something like that.
 *
 *  ===================
 *  GENERAL DISCUSSION:
 *  ===================
 *
 *  This class intends to map the solar system in Kilometers using 
    a Vector3L instance.  By doing this, the expanse that
 *  can be mapped out is 1.9 light years.  This is more than enough space.
 * 
 *  Mapped to the Vector3L position (kilometers), is an offset 
    position in meters using a Vector3d
 *
 *
 *  Functions implemented:
 *  ======================
 * #. Asset and camera mapping to Vector3d and Vector3L positions.
 * #. Add / Subtract of Space Positions from asset-to-asset or 
 *      from asset-to-camera.
 * #. normalize() ... updates the Vector3L such that all Vector3d values are
 *      less than 1000 meters, but maintaining the same spacial position.
 * #. adaptToNewReferencePoint(Vector3L v3L) ... can take the Vector3d position
 *      of the camera and map all surrounding objects to that same position
 *      making the Vector3d component of each asset completely relative to the 
 *      camera's position in meters, thus allowing direct transferal of values
 *      to the scene graph via a direct conversion to a Vector3f.
 * #. move(Vector3f) ... moves the camera position.
 *
 *
 * 
 */

package dgnUtils.math;

/**
 *
 * @author david
 */
public class SpaceGrid {
    
    private boolean isUpdated;  // flipped to true on any numerial updates to the object.
    private Vector3L gridV3L;
    private Vector3d gridV3d;
    
    public SpaceGrid(){
        gridV3L = new Vector3L();
        gridV3d = new Vector3d();
        isUpdated = false;  // false because all values for the object here are zero.
    }

    public SpaceGrid(Vector3L v3L, Vector3d v3d) {
        gridV3L = v3L;
        gridV3d = v3d;
        isUpdated = true;  // true because the V3d object can have values greater than 1000.
    }

    public Vector3L getVector3L() {
        return gridV3L;
    }

    public Vector3d getVector3d() {
        return gridV3d;
    }
    
    /**
     * Returns a string representation of the object.
     * 
     * This output is used to confirm tests, so changing the output 
     * will cause the tests to fail.
     * 
     * @return 
     */
    @Override
    public String toString(){
        return "(Vector3L) " + gridV3L.toString() + " [km]  " + gridV3d.toString() + " [m]";
    }
    /**
     * Returns a Vector3d instance whose value is in meters.  
     * 
     * The measurement is between the current grid position and the supplied grid position.
     * 
     * 
     * @param aSpaceGrid2Position
     * @return
     */
    public Vector3d getDistanceInMetersFrom(SpaceGrid aSpaceGrid2Position) {
        Vector3L subtractedV3L = gridV3L.subtract(aSpaceGrid2Position.getVector3L());
        Vector3d subtractedV3d = gridV3d.subtract(aSpaceGrid2Position.getVector3d());
        
        Vector3d fromVec3L = subtractedV3L.convertToVector3d();
        Vector3d multV3d = fromVec3L.scale(1000.0);
        
        Vector3d result = subtractedV3d.add(multV3d);
        
        return result;
    }
    
    /**
     * If the Vector3d component is very large, this method will reduce the Vector3d values to under 1000 
     * and increase the Vector3L values accordingly.
     * 
     * Acts only on this instance of SpaceGrid2.
     * 
     * @return 
     */
    public void normalize(){
        if (isUpdated) {
            //boolean log = true;
            boolean log = false;
            
            double vectorComponent, xyzD = 0, addedD = 0;
            long xyzL = 0;
            int numDivisions = 0;
            String axisString = "";
            
            vectorComponent = gridV3d.getX();
            if (vectorComponent > 1000.0 || vectorComponent < -1000.0) {
                numDivisions = (int) (vectorComponent / 1000.0);
                axisString = "X-axis";
                
                // add value to V3L y component
                xyzL = gridV3L.getX() + numDivisions;
                gridV3L.setX(xyzL);
                
                // subtract value * 1000 from V3d y component
                addedD = 1000.0 * numDivisions;
                xyzD = gridV3d.getX() - addedD;
                gridV3d.setX(xyzD);
                
                if (log) {normalizeLog(axisString, vectorComponent, numDivisions, xyzL, xyzD, addedD);}
            }
            
            vectorComponent = gridV3d.getY();
            if (vectorComponent > 1000.0 || vectorComponent < -1000.0) {
                numDivisions = (int) (vectorComponent / 1000.0);
                axisString = "Y-axis";
                
                // add value to V3L y component
                xyzL = gridV3L.getY() + numDivisions;
                gridV3L.setY(xyzL);
                
                // subtract value * 1000 from V3d y component
                addedD = 1000.0 * numDivisions;
                xyzD = gridV3d.getY() - addedD;
                gridV3d.setY(xyzD);
                
                if (log) {normalizeLog(axisString, vectorComponent, numDivisions, xyzL, xyzD, addedD);}
            }
            
            vectorComponent = gridV3d.getZ();
            if (vectorComponent > 1000.0 || vectorComponent < -1000.0) {
                numDivisions = (int) (vectorComponent / 1000.0);
                axisString = "Z-axis";
                
                // add value to V3L z component
                xyzL = gridV3L.getZ() + numDivisions;
                gridV3L.setZ(xyzL);
                
                // subtract value * 1000 from V3d z component
                addedD = 1000.0 * numDivisions;
                xyzD = gridV3d.getZ() - addedD;
                gridV3d.setZ(xyzD);
                
                if (log) {normalizeLog(axisString, vectorComponent, numDivisions, xyzL, xyzD, addedD);}
                
            }
            
            isUpdated = false;  // once all processing has been done, the object is un-updated waiting for future updates to process.
            
        } // end if isUpdated
    } // end method
    
    private void normalizeLog(String axisString, double vectorComponent, double numDivisions, long xyzL, double xyzD, double addedD){
        System.out.println(axisString);
        System.out.println("vectorComponent = " + vectorComponent);
        System.out.println("numDivisions = " + numDivisions);
        System.out.println("xyzL = " + xyzL);
        System.out.println("xyzD = " + xyzD);
        System.out.println("addedD = " + addedD);
        System.out.println("");
    }
    
    public boolean getIsUpdated(){
        return isUpdated;
    }

    public SpaceGrid add(SpaceGrid sg2B) {
        Vector3L v3L = sg2B.getVector3L();
        Vector3L totalV3L = gridV3L.add(v3L);
        
        Vector3d v3d = sg2B.getVector3d();
        Vector3d totalV3d = gridV3d.add(v3d);
        
        SpaceGrid result = new SpaceGrid(totalV3L, totalV3d);
        return result;
    }

    public SpaceGrid subtract(SpaceGrid sg2B) {
        Vector3L v3L = sg2B.getVector3L();
        Vector3L totalV3L = gridV3L.subtract(v3L);
        
        Vector3d v3d = sg2B.getVector3d();
        Vector3d totalV3d = gridV3d.subtract(v3d);
        
        SpaceGrid result = new SpaceGrid(totalV3L, totalV3d);
        return result;
    }

    public boolean isInsideRangeLimits(SpaceGrid sg2A, double sensorRange) {
        SpaceGrid sg1 = this.subtract(sg2A);
        
//        System.out.println(sg1.toString());
        
        Vector3L v3L1 = sg1.getVector3L();
        Vector3d v3d1 = v3L1.convertToVector3d();
        Vector3d v3d2 = v3d1.scale(1000.0);
        
        Vector3d v3d3 = sg1.getVector3d();
        Vector3d added = v3d2.add(v3d3);
        
//        System.out.println("added = " + added.toString());
        
        return added.withinDistance(sensorRange);
    }

    public void adaptToNewReferencePoint(Vector3L referencePoint) {
        Vector3L v3L1 = gridV3L.subtract(referencePoint);
        Vector3d v3d1 = v3L1.convertToVector3d().scale(1000);

        gridV3L = referencePoint;
        gridV3d = gridV3d.add(v3d1);
        isUpdated = true;
    }
}  // end class
