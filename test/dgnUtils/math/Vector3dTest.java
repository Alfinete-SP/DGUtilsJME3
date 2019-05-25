/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dgnUtils.math;

import com.jme3.math.Vector3f;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
//import org.junit.Ignore;

/**
 *
 * @author alfin
 */
public class Vector3dTest {
   
    boolean log;
    
    public Vector3dTest() {
        log = false;
        //log = true;
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
     *
     */
    @Test
    public void testVector3dCreation() {
        Vector3d v = new Vector3d();
        assertTrue(v.getX() == 0.0);
        assertTrue(v.getY() == 0.0);
        assertTrue(v.getZ() == 0.0);
        assertFalse(v.isVectorDataCalculated());
    }
    
    /**
     *
     */
    @Test
    public void testVector3dCreationWithParams() {
        Vector3d v = new Vector3d(2.002, 3.003, 4.004);
        assertTrue(v.getX() == 2.002);
        assertTrue(v.getY() == 3.003);
        assertTrue(v.getZ() == 4.004);
        assertFalse(v.isVectorDataCalculated());
    }
    
    /**
     * If the method "printoutVectorData()" is called, it will check that the 
     * boolean is set that says that the "getModulusAndVectorData()" method
     * has been run.
     */
    @Test
    public void booleanCheck01_true_GettingVectorData(){
        Vector3d v = new Vector3d(2.0, 3.0, 6.0);
        double[] vectorData = v.getModulusAndVectorData();
        assertTrue(v.isVectorDataCalculated());
        
        double modulus = vectorData[0];
        if (log) {
            System.out.println("Boolean Check 01 : modulus of Vector3d("+ vectorData[1] + ", " + vectorData[2] + ", " + vectorData[3] + ") = " + modulus);
        }
    }
    
    /**
     * check boolean ... must be false when a new X value is set.
     */
    @Test
    public void booleanCheck02_false_modifiedX() {
        Vector3d v = new Vector3d(2.0, 3.0, 6.0);
        double[] vectorData = v.getModulusAndVectorData();
        v.setX(5.0);
        assertFalse(v.isVectorDataCalculated());

        vectorData = v.getModulusAndVectorData();
        double modulus = vectorData[0];
        if (log) {
            System.out.println("Boolean Check 02 : modulus of Vector3d("+ vectorData[1] + ", " + vectorData[2] + ", " + vectorData[3] + ") = " + modulus);
        }
    }

    
    /**
     * check boolean ... must be false when a new Y value is set.
     */
    @Test
    public void booleanCheck03_false_modifiedY() {
        Vector3d v = new Vector3d(2.0, 3.0, 6.0);
        double[] vectorData = v.getModulusAndVectorData();
        v.setY(5.0);
        assertFalse(v.isVectorDataCalculated());

        vectorData = v.getModulusAndVectorData();
        double modulus = vectorData[0];
        if (log) {
            System.out.println("Boolean Check 03 : modulus of Vector3d("+ vectorData[1] + ", " + vectorData[2] + ", " + vectorData[3] + ") = " + modulus);
        }
    }    
    
    /**
     * check boolean ... must be false when a new Z value is set.
     */
    @Test
    public void booleanCheck04_false_modifiedZ() {
        Vector3d v = new Vector3d(2.0, 3.0, 6.0);
        double[] vectorData = v.getModulusAndVectorData();
        v.setZ(5.0);
        assertFalse(v.isVectorDataCalculated());

        vectorData = v.getModulusAndVectorData();
        double modulus = vectorData[0];
        if (log) {
            System.out.println("Boolean Check 04 : modulus of Vector3d("+ vectorData[1] + ", " + vectorData[2] + ", " + vectorData[3] + ") = " + modulus);
        }
    }    
    
    /**
     * Checking that a Vector3f is returned with the same parameters as the current
     * instance of the Vector3d.
     */
    @Test
    public void testConversionToVector3f() {
        Vector3d v3d = new Vector3d(2.0, 3.0, 6.0);
        Vector3f v3f = v3d.getVector3f();
        assertTrue(v3f.x == 2.0f & 
                   v3f.y == 3.0f & 
                   v3f.z == 6.0f);
    }
    
    /**
     * Checks that the first call to getModulusAndVectorData() will perform the
     * square root.  The boolean that says the operation has been done will be set.
     */
    @Test
    public void testFirstCallTo_getModulusAndVectorData() {
        Vector3d v = new Vector3d(2.0, 3.0, 6.0);
        double[] vectorData = v.getModulusAndVectorData();
        assertTrue(v.getTrackerValue() == 9091);
    }
    
    /**
     * Checks that the second call to getModulusAndVectorData() will not perform the
     * square root, since the boolean that says the operation has been done has been set.
     */
    @Test
    public void testSecondCallTo_getModulusAndVectorData() {
        Vector3d v = new Vector3d(2.0, 3.0, 6.0);
        double[] vectorData = v.getModulusAndVectorData();
        
        // second call
        vectorData = v.getModulusAndVectorData();
        assertTrue(v.getTrackerValue() == 444554);
    }
    
    /**
     * When null is passed into the method printOutVectorData(null), the resulting
     * string will call the vector "noname".
     */
    @Test
    public void testNonameVectorNaming() {
        //log = true;
        Vector3d v = new Vector3d(2.0, 3.0, 6.0);
        double[] vectorData = v.getModulusAndVectorData();
        String outputStr = v.printOutVectorData(null);
        String result = outputStr.substring(18, 24);
        if (log) {
            System.out.println("" + outputStr);
            System.out.println("" + result);
        }
        assertTrue(result.equals("noname"));
    }
    
    /**
     * Created for unit testing of Vector3d arrays, to assert that two arrays
     * are equal.  The output of this method is a composite string and these
     * strings can be compared.
     * 
     * An array of Vector3d elements creates a composite string of 
     * Vector3d.toString() components, and this composite string can
     * be compared to another string to verify that the arrays are identical.
     * 
     */
    @Test
    public void testCreationOfStringOfAnArrayOfVector3dObjects() {
        boolean log = false;
        Vector3d[] testArray = new Vector3d [] 
            { new Vector3d(25.6, 4567.998, 0.001)
            , new Vector3d(0.0 , 111.111,  110)        
            , new Vector3d(695.25, 3232.001, 4.659)    
            };
        String arrayString = Vector3d.vectorArrayToString(testArray);
        String expected =   "(Vector3d)  X: 25.6, Y: 4567.998, Z: 0.001\n" +
                            "(Vector3d)  X: 0.0, Y: 111.111, Z: 110.0\n" +
                            "(Vector3d)  X: 695.25, Y: 3232.001, Z: 4.659\n";
        if (log) {
            System.out.println("arrayString = \n" + arrayString);
            System.out.println("Length of arrayString = " + arrayString.length());
            System.out.println("Length of expected    = " + expected.length());
        }
        //assertTrue(arrayString.length() == expected.length());
        assertTrue(arrayString.equals(expected));
    }
      
    /**
     * Return a distance in kilometers.  This takes it for granted that the 
     * unit distance of the double value represents meters.
     */
    @Test
    public void getKilometerModulus() {
        Vector3d v = new Vector3d(3_546_768D, 114_001_199_234D, 5_757_575_700_933D);
        double result = v.getKilometerModulus();
        //System.out.println("Kilometers = " + result);
        assertTrue(5_758_704_214.093_089 == result);  // result is km and not meters.
    }
    
    /**
     * Test that a position repesented by a Vector3d is within a certain distance.
     * That is to say ...
     * The modulus of the vector is LESS THAN the distance queried.
     */
    @Test
    public void withinDistance() {
        Vector3d v; 
        v = new Vector3d(3_546_768D, 114_001_199_234D, 5_757_575_700_933D);
        boolean result1 = v.withinDistance(5_758_704_214_094D);
        
        v = new Vector3d(1D, 1D, 1D); 
        boolean result2 = v.withinDistance(2D);
        
        assertTrue(result1 && result2);
    }
    
    /**
     * Tests subtracting one Vector3d object from another.
     *
     */
    @Test
    public void testAddingVector3d() {
        //boolean log = true;
        boolean log = false;
        
        Vector3d pos1, pos2, deltaPos;
        pos1 = new Vector3d(10000.0, 100.0, 0.25);
        pos2 = new Vector3d(100000.0, -10.0, -0.01);
        deltaPos = pos1.subtract(pos2);
        
        if (log) {
            System.out.println("deltaPos = " + deltaPos.toString());
        }
        
        assertTrue(deltaPos.toString().equals("(Vector3d)  X: -90000.0, Y: 110.0, Z: 0.26"));
    }
    
    /**
     * Test
     *
     */
    @Test
    public void getModulus() {
        boolean log = false;
        
        Vector3d test = new Vector3d (1.0, 1.0, 1.0);
        double modulus = test.getModulus();
        if (log) { System.out.println("modulus is ... " + modulus); }
        
        assertTrue (modulus == 1.7320508075688772);
    }
}
  