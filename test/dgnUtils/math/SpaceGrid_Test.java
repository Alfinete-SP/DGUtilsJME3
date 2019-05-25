/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dgnUtils.math;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author david
 */
public class SpaceGrid_Test {
    
    public SpaceGrid_Test() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * Test_0082
     * 
     * Test Empty Constructors
     *
     */
    @Test
    public void testEmptyConstructor() {
        SpaceGrid sg2 = new SpaceGrid();
        //System.out.println("sg2 toString ... " + sg2.toString());
        
        boolean isSameString;
        String expected, result;
        expected = "(Vector3L) X = 0, Y = 0, Z = 0 [km]  (Vector3d)  X: 0.0, Y: 0.0, Z: 0.0 [m]";
        result = sg2.toString();
        isSameString = result.equals(expected);
        assertTrue(isSameString);
    }
    
    /**
     * Test_0083
     *
     */
    @Test
    public void testConstructorsWithGivenVectors() {
        Vector3L v3L = new Vector3L(12991, -443, 9);
        Vector3d v3d = new Vector3d(44.9933678, -0.3, -9944321.24);
        
        SpaceGrid sg2 = new SpaceGrid(v3L, v3d);
        //System.out.println("sg2 toString ... " + sg2.toString());
        
        boolean isSameString;
        String expected, result;
        expected = "(Vector3L) X = 12991, Y = -443, Z = 9 [km]  (Vector3d)  X: 44.9933678, Y: -0.3, Z: -9944321.24 [m]";
        result = sg2.toString();
        isSameString = result.equals(expected);
        assertTrue(isSameString);
    }
    
    /**
     * Test_0084
     *
     */
    @Test
    public void getVector3dDistanceBetweenTwoPositions() {
        // Configure first object:
        Vector3L v3LA = new Vector3L(12991, -443, 9);
        Vector3d v3dA = new Vector3d(44.9933678, -0.3, -9944321.24);
        
        SpaceGrid sg2A = new SpaceGrid(v3LA, v3dA);
        
        // Configure second object:
        Vector3L v3LB = new Vector3L(12991, -443, 909);
        Vector3d v3dB = new Vector3d(44.9933678, -10.3, -9944321.24);
        
        SpaceGrid sg2B = new SpaceGrid(v3LB, v3dB);
        
        /*
        PARAMETER CHECKING DATA:
        ========================
        Vector3d returns values in meters.
        Distance between = x = 0m, y = 10m , z = -900_000m 
        
        Vector3d expectedV3d = new Vector3d(0.0, 10.0, -900_000.0);
        System.out.println("resultV3d to string = " + expectedV3d.toString());
        */
        String expected, result;
        expected = "(Vector3d)  X: 0.0, Y: 10.0, Z: -900000.0";
        
        Vector3d resultV3d = sg2A.getDistanceInMetersFrom(sg2B);
        //System.out.println("result to String = " + resultV3d.toString());
        result = resultV3d.toString();
        
        assertTrue(result.equals(expected));
    }
    
    /**
     * Test_0085
     *
     */
    @Test
    public void withinDistance_UsingSquaresOnly() {
        // converts Long vector to double vector.  Adds itself to the object's double vector then takes the square.
        // takes a double for range, returns a boolean to say if it is inside this range.
        
        double sensorRange = 50_000.0; // meters
        
        // Configure first object:
        Vector3L v3LA = new Vector3L(12991, -443, 9);
        Vector3d v3dA = new Vector3d(44.9933678, -10.3, -9944321.24);
        
        SpaceGrid sg2A = new SpaceGrid(v3LA, v3dA);
        
        // Configure second object:
        Vector3L v3LB = new Vector3L(13021, -443, 49);
        Vector3d v3dB = new Vector3d(44.9933678, -10.3, -9944322.24);
        
        SpaceGrid sg2B = new SpaceGrid(v3LB, v3dB);
        
        // should be inside distance.
        
        boolean isInsideRange = sg2B.isInsideRangeLimits(sg2A, sensorRange);
        
        assertTrue(isInsideRange);
    }
    
    /**
     * Test_0086
     *
     */
    @Test
    public void addTwoPositions() {
        
        // Configure first object:
        Vector3L v3LA = new Vector3L(12991, -443, -9);
        Vector3d v3dA = new Vector3d(44.9933678, -0.3, -9944321.24);
        
        SpaceGrid sg2A = new SpaceGrid(v3LA, v3dA);
        
        // Configure second object:
        Vector3L v3LB = new Vector3L(-991, -443776, 9091);
        Vector3d v3dB = new Vector3d(449933.678, -1066.36, 994.432124);
        
        SpaceGrid sg2B = new SpaceGrid(v3LB, v3dB);
        
        // APPLY FUNCTION:
        SpaceGrid sg2Result = sg2A.add(sg2B);
        
        //**********************************************************
        //System.out.println("sg2Result = " + sg2Result.toString());
        
        boolean b1, b2, b3, b4;
        
        b1 = sg2Result.getIsUpdated();
        
        b2 = sg2Result.toString().equals("(Vector3L) X = 12000, Y = -444219, Z = 9082 [km]  (Vector3d)  X: 449978.67136780004, Y: -1066.6599999999999, Z: -9943326.807876 [m]");
        
        // APPLY FUNCTION NORMALIZE
        sg2Result.normalize();
        
        //*************************************************************
        //System.out.println("sg2Result = " + sg2Result.toString());
        
        b3 = sg2Result.toString().equals("(Vector3L) X = 12449, Y = -444220, Z = -861 [km]  (Vector3d)  X: 978.6713678000378, Y: -66.65999999999985, Z: -326.80787600018084 [m]");
        
        b4 = !(sg2Result.getIsUpdated());
        
        assertTrue(b1 && b2 && b3 && b4);
        
    }
    
    /**
     * Test_0087
     *
     */
    @Test
    public void subtractTwoPositions() {
        // Configure first object:
        Vector3L v3LA = new Vector3L(12991, -443, -9);
        Vector3d v3dA = new Vector3d(44.9933678, -0.3, -9944321.24);
        
        SpaceGrid sg2A = new SpaceGrid(v3LA, v3dA);
//        System.out.println(sg2A.toString());
        
        // Configure second object:
        Vector3L v3LB = new Vector3L(-991, -443776, 9091);
        Vector3d v3dB = new Vector3d(449933.678, -1066.36, 994.432124);
        
        SpaceGrid sg2B = new SpaceGrid(v3LB, v3dB);
//        System.out.println(sg2B.toString());
        
        // APPLY FUNCTION:
        SpaceGrid sg2Result = sg2A.subtract(sg2B);
        
        String expected, result;
//        expected = sg2Result.toString();
//        System.out.println(expected);
        
        expected = "(Vector3L) X = 13982, Y = 443333, Z = -9100 [km]  (Vector3d)  X: -449888.6846322, Y: 1066.06, Z: -9945315.672124 [m]";
        
        boolean b1, b2, b3, b4;
        
        b1 = sg2Result.getIsUpdated();
        
        b2 = sg2Result.toString().equals(expected);
        
        // APPLY FUNCTION NORMALIZE
        sg2Result.normalize();
        
        //*************************************************************
        //System.out.println("sg2Result = " + sg2Result.toString());
        
        expected = "(Vector3L) X = 13533, Y = 443334, Z = -19045 [km]  (Vector3d)  X: -888.684632199991, Y: 66.05999999999995, Z: -315.6721240002662 [m]";
        
        b3 = sg2Result.toString().equals(expected);
        
        b4 = !(sg2Result.getIsUpdated());
        
        assertTrue(b1 && b2 && b3 && b4);
        
        
        
    }
    
    /**
     * Test_0088
     * 
     * Fix a Vector3L position and the Vector3d position adjusts automatically.
     */
    @Test
    public void cameraUpdating() {
        // Configure first object:
        Vector3L v3LA = new Vector3L(12991, -443, -9);
        Vector3d v3dA = new Vector3d(44.9933678, -0.3, -994.432124);
        
        SpaceGrid sg2A = new SpaceGrid(v3LA, v3dA);
//System.out.println("sg2A = " + sg2A.toString());
        
        // set a fixed Vector3L as the reference point.
        Vector3L referencePoint = new Vector3L(12990, -442, -12);
        
        // FUNCTION APPLIED ...
        sg2A.adaptToNewReferencePoint(referencePoint);
//System.out.println("sg2A = " + sg2A.toString());

        String result = sg2A.toString();
        String expected = "(Vector3L) X = 12990, Y = -442, Z = -12 [km]  (Vector3d)  X: 1044.9933678, Y: -1000.3, Z: 2005.567876 [m]";
        assertTrue(result.equals(expected));
    }
    
    /**
     * Test_0089
     *
     */
    @Test
    public void testNormalizing_V3dNegativeZ() {
        // Configure first object:
        Vector3L v3LA = new Vector3L(12991, -443, 9);
        Vector3d v3dA = new Vector3d(44.9933678, -0.3, -9944321.24);
        
        SpaceGrid sg2A = new SpaceGrid(v3LA, v3dA);
        //System.out.println("sg2A = " + sg2A.toString());
        
        String expected, result;
        expected = "(Vector3L) X = 12991, Y = -443, Z = -9935 [km]  (Vector3d)  X: 44.9933678, Y: -0.3, Z: -321.2400000002235 [m]"; //expectedSG2.toString();
        
        // FUNCTION APPLIED HERE:
        sg2A.normalize();
        
        result = sg2A.toString();
        //System.out.println("result values   = " + result);
        
        boolean b1, b2, bFinal;
        
        b1 = sg2A.getIsUpdated();
        
        b2 = (result.equals(expected));
        
        assertTrue(!b1 && b2);
        
    }

    /**
     * Test_0090
     *
     */
    @Test
    public void testNormalizing_V3dPositiveZ() {
        // Configure first object:
        Vector3L v3LA = new Vector3L(12991, -443, 9);
        Vector3d v3dA = new Vector3d(44.9933678, -0.3, 9944321.24);
        
        SpaceGrid sg2A = new SpaceGrid(v3LA, v3dA);
        
        //System.out.println("sg2A = " + sg2A.toString());
        
        String expected, result;
        expected = "(Vector3L) X = 12991, Y = -443, Z = 9953 [km]  (Vector3d)  X: 44.9933678, Y: -0.3, Z: 321.2400000002235 [m]"; //expectedSG2.toString();
        
        // FUNCTION APPLIED HERE:
        sg2A.normalize();
        
        result = sg2A.toString();
        
        //System.out.println("result values   = " + result);
        
        boolean b1, b2, bFinal;
        
        b1 = sg2A.getIsUpdated();
        
        b2 = (result.equals(expected));
        
        assertTrue(!b1 && b2);
        
    }
   
    /**
     * Test_0091
     *
     */
    @Test
    public void testNormalizing_V3dPositiveY() {
        // Configure first object:
        Vector3L v3LA = new Vector3L(12991, -443, 9);
        Vector3d v3dA = new Vector3d(44.9933678, 33568.0101, 994.24);
        
        SpaceGrid sg2A = new SpaceGrid(v3LA, v3dA);
        
        //System.out.println("sg2A            = " + sg2A.toString());
        
        String expected, result;
        expected = "(Vector3L) X = 12991, Y = -410, Z = 9 [km]  (Vector3d)  X: 44.9933678, Y: 568.0100999999995, Z: 994.24 [m]"; //expectedSG2.toString();
        
        // FUNCTION APPLIED HERE:
        sg2A.normalize();
        
        result = sg2A.toString();
        
        //System.out.println("result values   = " + result);
        
        boolean b1, b2, bFinal;
        
        b1 = sg2A.getIsUpdated();
        
        b2 = (result.equals(expected));
        
        assertTrue(!b1 && b2);
        
    }
    
    /**
     * Test_0092
     *
     */
    @Test
    public void testNormalizing_V3dNegativeY() {
        // Configure first object:
        Vector3L v3LA = new Vector3L(12991, -443, 9);
        Vector3d v3dA = new Vector3d(44.9933678, -33568.0101, 994.24);
        
        SpaceGrid sg2A = new SpaceGrid(v3LA, v3dA);
        
        // FOR DEBUG - USE THIS SOUT BELOW
        //System.out.println("sg2A            = " + sg2A.toString());
        
        String expected, result;
        expected = "(Vector3L) X = 12991, Y = -476, Z = 9 [km]  (Vector3d)  X: 44.9933678, Y: -568.0100999999995, Z: 994.24 [m]"; //expectedSG2.toString();
        
        // FUNCTION APPLIED HERE:
        sg2A.normalize();
        
        result = sg2A.toString();
        
        // FOR DEBUG - USE THIS SOUT BELOW
        //System.out.println("result values   = " + result);
        
        boolean b1, b2, bFinal;
        
        b1 = sg2A.getIsUpdated();
        
        b2 = (result.equals(expected));
        
        assertTrue(!b1 && b2);
        
    }
    
    /**
     * Test_0093
     *
     */
    @Test
    public void testNormalizing_V3dPositiveX() {
        // Configure first object:
        Vector3L v3LA = new Vector3L(12991, -443, 9);
        Vector3d v3dA = new Vector3d(4444444.99336, -338.0101, 994.24);
        
        SpaceGrid sg2A = new SpaceGrid(v3LA, v3dA);
        
        //System.out.println("sg2A            = " + sg2A.toString());
        
        String expected, result;
        expected = "(Vector3L) X = 17435, Y = -443, Z = 9 [km]  (Vector3d)  X: 444.9933599997312, Y: -338.0101, Z: 994.24 [m]" ; //expectedSG2.toString();
        
        // FUNCTION APPLIED HERE:
        sg2A.normalize();
        
        result = sg2A.toString();
        
        //System.out.println("result values   = " + result);
        
        boolean b1, b2, bFinal;
        
        b1 = sg2A.getIsUpdated();
        
        b2 = (result.equals(expected));
        
        assertTrue(!b1 && b2);
        
    }
    
    /**
     * Test_0094
     *
     */
    @Test
    public void testNormalizing_V3dNegativeX() {
        // Configure first object:
        Vector3L v3LA = new Vector3L(12991, -443, 9);
        Vector3d v3dA = new Vector3d(-4444444.99336, -338.0101, 994.24);
        
        SpaceGrid sg2A = new SpaceGrid(v3LA, v3dA);
        
        //System.out.println("sg2A            = " + sg2A.toString());
        
        
        String expected, result;
        expected = "(Vector3L) X = 8547, Y = -443, Z = 9 [km]  (Vector3d)  X: -444.9933599997312, Y: -338.0101, Z: 994.24 [m]" ; //expectedSG2.toString();
        
        // FUNCTION APPLIED HERE:
        sg2A.normalize();
        
        result = sg2A.toString();
        
        //System.out.println("result values   = " + result);
        
        boolean b1, b2, bFinal;
        
        b1 = sg2A.getIsUpdated();
        
        b2 = (result.equals(expected));
        
        assertTrue(!b1 && b2);
        
    }
    
    /**
     * Test_0095
     *
     */
    @Test
    public void testNormalizing_NoNormalizing() {
        // Configure first object:
        Vector3L v3LA = new Vector3L(12991, -443, 9);
        Vector3d v3dA = new Vector3d(-444.99336, -368.0101, 994.24);
        
        SpaceGrid sg2A = new SpaceGrid(v3LA, v3dA);
        
        //System.out.println("sg2A            = " + sg2A.toString());
        
        String expected, result;
        expected = "(Vector3L) X = 12991, Y = -443, Z = 9 [km]  (Vector3d)  X: -444.99336, Y: -368.0101, Z: 994.24 [m]" ; //expectedSG2.toString();
        
        // FUNCTION APPLIED HERE:
        sg2A.normalize();
        
        result = sg2A.toString();
        
        //System.out.println("result values   = " + result);
        
        boolean b1, b2, bFinal;
        
        b1 = sg2A.getIsUpdated();
        
        b2 = (result.equals(expected));
        
        assertTrue(!b1 && b2);
        
    }
    
    /**
     * Test_0096
     * 
     * Move the asset to a new location by adding a Vector3f
     *
     */
    @Test
    public void moveTheAsset() {
        System.out.println("test 0096 skipped.");
    }
    
    /**
     * Test_0097
     * 
     * When the camera's position is above a certain distance with reference
     * to the current fixed Vector3L position (say 10_000 m), then the position
     * vectors can be normalized, and a new reference Vector3L position established
     * for the camera and all associated assets.
     */
    @Test
    public void checkToSeeIfNewReferencePositionIsRequired() {
        System.out.println("test 0097 skipped.");
    }
    
}
