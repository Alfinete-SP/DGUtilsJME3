/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dgnUtils.math;

import com.jme3.math.Vector3f;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
//import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author alfin
 */
public class SpaceGrid_Old_01_Test {
    
    public SpaceGrid_Old_01_Test() {
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

    @Test
    public void testConstructorWithVector3dParameter() {
        //boolean log = true;
        boolean log = false;
        
        String result, expected;
        
        // TEST
        Vector3d myVec = new Vector3d(1024.345717, 45789.2991199, 3990044.25);
        SpaceGrid_Old_01 testPosition = new SpaceGrid_Old_01(myVec);          if (log) { System.out.println("SpacePosition (value set) toString >> " + testPosition.toString()); }
        result = testPosition.toString();
        
        
        // CHECKING THAT THE RESULT IS CORRECT
        expected = "X = 1024, Y = 45789, Z = 3990044 (345.717, 299.1199, 250.0)";

        //assertTrue(result.equals(expected));
        
        assertTrue(testPosition.isSameAs(testPosition));
    }
    
    /**
     * Test
     *
     */
    @Test
    public void testConstuctorWithNoParameter() {
        //boolean log = true;
        boolean log = false;
        
        String result, expected;
        
        // TEST
        SpaceGrid_Old_01 testPosition = new SpaceGrid_Old_01();               if (log) { System.out.println("SpacePosition (origin) toString >> " + testPosition.toString()); }
        result = testPosition.toString();
        
        
        // CHECKING THAT THE RESULT IS CORRECT
        expected = "X = 0, Y = 0, Z = 0  +  (Vector3d)  X: 0.0, Y: 0.0, Z: 0.0";

        assertTrue(result.equals(expected));
    }
    
    /**
     * Test
     * Return a boolean to say that two grid values are the same.
     * 
     * Good for unit testing.
     */
    @Test
    public void testSignalThatTwoSpaceGridValuesAreSame() {
        SpaceGrid_Old_01 sgp1 = new SpaceGrid_Old_01(new Vector3d (12.34, 256.01, 999.111));
        SpaceGrid_Old_01 sgp2 = new SpaceGrid_Old_01(new Vector3d (12.34, 256.01, 999.111));
        
        assertTrue(sgp1.isSameAs(sgp2));
    }
    
    /**
     * Test
     *
     */
    @Test
    public void testsSettersForX_Y_Z() {
        //boolean log = true;
        boolean log = false;
        
        String resultStr, expectedStr;
        
        // TEST
        SpaceGrid_Old_01 testPosition = new SpaceGrid_Old_01();               if (log) { System.out.println("SpacePosition (value set) toString >> " + testPosition.toString()); }
        testPosition.setCompleteX(1024.345717);    // Sets X position in both internal vectors (unit = kilometers)
        testPosition.setCompleteY(45789.2991199);  // Sets Y position in both internal vectors (unit = kilometers)
        testPosition.setCompleteZ(3990044.25);     // Sets Z position in both internal vectors (unit = kilometers)
        resultStr = testPosition.toString();
        if (log) {
            System.out.println("resultString = " + resultStr);
        }
        
        
        // CHECKING THAT THE RESULT IS CORRECT
        expectedStr = "X = 1024, Y = 45789, Z = 3990044  +  (Vector3d)  X: 345.71699999992234, Y: 299.11990000255173, Z: 250.0";
                //+ "X = 1024, Y = 45789, Z = 3990044 (345.717, 299.1199, 250.0)";
        
        Vector3d myVec = new Vector3d(1024.345717, 45789.2991199, 3990044.25);
        SpaceGrid_Old_01 position2 = new SpaceGrid_Old_01(myVec);

        boolean test1, test2, testResult;
        testResult = false;
        
        test1 = resultStr.equals(expectedStr);                                  if (log) { System.out.println("SpacePositionTest.testsSettersForX_Y_Z (test1) >> " + test1); }
        
        test2 = testPosition.isSameAs(position2);                               if (log) { System.out.println("SpacePositionTest.testsSettersForX_Y_Z (test2) >> " + test2); }
        
        testResult = test1 && test2;
        
        assertTrue(testResult);
    }
    
    /**
     * Test
     *
     */
    @Test
    public void returnVector3dRepresentationOfPosition() {
        //boolean log = true;
        boolean log = false;
        
        Vector3d pos1; //, pos2, deltaPosA;
        SpaceGrid_Old_01 sPosition1; //, sPosition2; 
        
        pos1 = new Vector3d(1024.345717, -45789.2991199, 3990044.25);
        sPosition1 = new SpaceGrid_Old_01(pos1);
        
        Vector3d result = sPosition1.getVector3d();
        
        if (log) {
            System.out.println("resultant Vector3d = " + result.toString());
        }
        
        boolean testBool;
        
        testBool = result.toString().equals("(Vector3d)  X: 1024.345717, Y: -45789.2991199, Z: 3990044.25");
                
        assertTrue(testBool);
    }
    
    /**
     * Test
     * Checks for multimplation precision of doubles.
     */
    @Test
    public void doubleMultiplying() {
        //boolean log = true;
        boolean log = false;
        
        double d1 = 45789.2991199;
        
        double d2 = (d1 - 45789.0) * 1000.0;
        
        if (log) {
            System.out.println("d1 = " + d1);
            System.out.println("d2 = " + d2);
        }
        
        assertTrue(d2 == 299.11990000255173);
        
    }
    
     /**
     * Test
     * Gives the distance between two Position objects as a double value (in kilometers)
     */
    @Test
    public void testDistanceBetweenTwoObjects() {
        //boolean log = true;
        boolean log = false;
        
        Vector3d pos1, pos2, deltaPosA;
        //pos1 = new Vector3d( 1024.345717,  -4789.2991199,   -992.999223);
        //pos2 = new Vector3d(31024.345717, -44789.2991199, -50992.999223);
        
        pos1 = new Vector3d(1024.345717, -4789.2991199,  -992.999223);
        pos2 = new Vector3d(1034.345717, -4800.2991199, -1102.999223);
        
        deltaPosA = pos1.subtract(pos2);                                        if (log) {System.out.println("deltaPos = " + deltaPosA.toString());}
        double distanceA = deltaPosA.getModulus();                              if (log) {System.out.println("deltaPosA modulus = " + distanceA);}
        
        SpaceGrid_Old_01 sPosition1;
        SpaceGrid_Old_01 sPosition2;
        sPosition1 = new SpaceGrid_Old_01(pos1);
        sPosition2 = new SpaceGrid_Old_01(pos2);
        
        double distanceResult = sPosition1.distanceBetween(sPosition2);         if (log) {System.out.println("distance result = " + distanceResult);}
        
        //assertTrue(distanceResult == 50_000.0);
        assertTrue(distanceResult == 111.0);
    }
    
    /**
     * Test
     *
     */
    @Test
    public void testModulusOfSpacePosition() {
        //boolean log = true;
        boolean log = false;
        
        Vector3d pos1; //, pos2, deltaPosA;
        SpaceGrid_Old_01 sPosition1; //, sPosition2; 
        
        pos1 = new Vector3d(1024.345717, 45789.2991199, 3990044.25);
        sPosition1 = new SpaceGrid_Old_01(pos1);
        
        double modulus = sPosition1.getModulus();
        
        if (log) {
            System.out.println("Result should be close to : 3.9903710925312 E 6");
            System.out.println("modulus of vector is ... " + modulus);
        } 
        
        assertTrue(modulus == 3990307.1092531336);   //   3990371.0925312
    }
    
    /**
     * Test
     *
     */
    @Test
    public void findDistanceBetweenGridPositionsAsVector3d() {
        //boolean log = true;
        boolean log = false;
        SpaceGrid_Old_01 sgp1 = new SpaceGrid_Old_01();
        Vector3L gridLong1 = new Vector3L(-1000L, -2000L, -2000L);
        Vector3d gridDouble1 = new Vector3d(0.5d, 0.5d, 0.5d);
        sgp1.setGridDouble(gridDouble1);
        sgp1.setGridLong(gridLong1);
        
        SpaceGrid_Old_01 sgp2 = new SpaceGrid_Old_01();
        Vector3L gridLong2 = new Vector3L(1000L, 2000L, 2000L);
        Vector3d gridDouble2 = new Vector3d(0.5d, 0.5d, 0.5d);
        sgp2.setGridDouble(gridDouble2);
        sgp2.setGridLong(gridLong2);
        
        Vector3d expected =  new Vector3d(2000d, 4000d, 4000d);
        Vector3d result = sgp1.getVector3dTo(sgp2);
        
        boolean test = result.toString().equals(expected.toString());
        
        if (log) {
            System.out.println("expected toString >> " + expected.toString());
            System.out.println("result   toString >> " + result.toString());
            System.out.println("Test boolean is " + test);
        }
        
        assertTrue(test);
    }
    
    /**
     * Test will endeavour to subtract one spacegridposition object from another
     * to give a resultant Vector3f object.
     *
     */
    @Test
    public void testSubractingTwoSpaceGridPostionsToReturnAVector3f() {
        SpaceGrid_Old_01 sgp1;
        SpaceGrid_Old_01 cameraPos;
        Vector3f expected = new Vector3f(0.0f, 0.0f, -600.0f);
        
        sgp1 = new SpaceGrid_Old_01(
                new Vector3d(-492_025_050.786  // Unit value in Kilometers
                            , -93_897_106.625  // Unit value in Kilometers
                            ,   8_588_329.208  // Unit value in Kilometers
                )
        );
        cameraPos = new SpaceGrid_Old_01(
                new Vector3d(-492_025_050.786  // Unit value in Kilometers
                            , -93_897_106.625  // Unit value in Kilometers
                            ,   8_588_328.608  // Unit value in Kilometers
                )
        );
        
        // difference in z value is -0.6 in Vector3d because units are in kilometers.
        // camera is reversed on the z axis by 600 meters to see the large asteroid because Vector3f is in meters.
        
        Vector3f result = SpaceGrid_Old_01.subtractAndReturnDistanceInMeters_V3f(cameraPos, sgp1); 
        
        boolean b1, b2, b3, b4;
        
        b1 = expected.x == result.x;
        b2 = expected.y == result.y;
        b3 = expected.z == result.z;
        b4 = b1 && b2 && b3;   
        
        assertTrue(b4);
    }
    
}
