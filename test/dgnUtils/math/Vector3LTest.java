/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dgnUtils.math;

//import java.math.BigInteger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
//import org.junit.Ignore;

/**
 *
 * @author David Nicholson
 *
 * 2017-02-22
 * 
 */
public class Vector3LTest {
    
    public Vector3LTest() {
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
     * Test new Instance
     *
     */
    @Test
    public void newInstance() {
        Vector3L v = new Vector3L();
        assertFalse(v == null);
    }
    
    /**
     * Test first constructor
     * All values for x, y and z are zero.
     */
    @Test
    public void firstConstructor() {
        Vector3L v = new Vector3L();
        boolean sameX = v.getX() == 0L;
        boolean sameY = v.getY() == 0L;
        boolean sameZ = v.getZ() == 0L;
        assertTrue(sameX && sameY && sameZ);
    }
    
    
    /**
     * Second constructor passes in values as needed.
     */
    @Test
    public void secondConstructor() {
        Vector3L v = new Vector3L(5_768_989L, 44_261L, 111_777_999_222L);
        boolean sameX = v.getX() == 5_768_989L;
        boolean sameY = v.getY() == 44_261L;
        boolean sameZ = v.getZ() == 111_777_999_222L;
        assertTrue(sameX && sameY && sameZ);
    }
    
    /**
     * Test adding two long vectors.
     */
    @Test
    public void add() {
        Vector3L v1 = new Vector3L(5_768_989L, 44_261L, 111_777_999_222L);
        Vector3L v2 = new Vector3L(-9_494_001_882L, 111_222_333_444L, 6L);
        Vector3L vR = v1.add(v2);
        boolean sameX = vR.getX() == -9_488_232_893L;
        boolean sameY = vR.getY() == 111_222_377_705L;
        boolean sameZ = vR.getZ() == 111_777_999_228L;
        assertTrue(sameX && sameY && sameZ);
    }
    
    /**
     * Test subtracting two long vectors.
     */
    @Test
    public void subtract() {
        Vector3L v1 = new Vector3L(5_768_989L, 44_261_999_010L, 222L);
        Vector3L v2 = new Vector3L(-9_494_001_882L, 111_222_333_444L, -6L);
        Vector3L vR = v1.subtract(v2);
        boolean sameX = vR.getX() == 9_499_770_871L;
        boolean sameY = vR.getY() == -66_960_334_434L;
        boolean sameZ = vR.getZ() == 228L;
        assertTrue(sameX && sameY && sameZ);
    }
    
    /**
     * Test that a cloned vector has the same values as the original.
     */
    @Test
    public void cloneVector() {
        Vector3L v1, vR;
        v1 = new Vector3L(5_768_989L, 44_261_999_010L, 222L);
        vR = v1.cloneVector();
        boolean sameStr = vR.toString().equals(v1.toString());
        assertTrue(sameStr);
    }
    
    /**
     * Test conversion of a double vector to long vector.
     * 
     * If the vector conversion goes OVERSIZE, the oversized boolean
     * will be set for the axis that has gone over Long.MAX_SIZE
     */
    @Test
    public void convertDoubleVectorToLongVector() {
        Vector3L vExpected, vResult;
        Vector3d v1;
        boolean result[];
        result = new boolean[]{true, true, true, true, true, true, true, true}; // max idx = 7
        boolean finalResult = true;
        
        vExpected = new Vector3L(5_768_989L, 44_261_999_019L, 222L);
        v1 = new Vector3d(5_768_989D, 44_261_999_019D, 222D);
        
        vResult = Vector3L.toVector3L(v1);
        
        result[0] = vExpected.toString().equals(vResult.toString());
        
        // check oversize booleans
        
        result[1] = !vResult.getOverSizedAny(); // should be false.
        
        
        // check oversized X
        v1 = new Vector3d(50_768_989_000_000_000_000D, 44_261_999_019D, 222D);
        vResult = Vector3L.toVector3L(v1);
        result[2] = vResult.getOverSizedAny(); // should be true.
        result[3] = vResult.getOverSizedX(); // should be true.
        
        // check oversized Y
        v1 = new Vector3d(50_768_989D, 44_261_999_019_000_000_000_000D, 222D);
        vResult = Vector3L.toVector3L(v1);
        result[4] = vResult.getOverSizedAny(); // should be true.
        result[5] = vResult.getOverSizedY(); // should be true.
        
        // check oversized Z
        v1 = new Vector3d(50_768_989D, 44_261_999_019D, 222_000_000_000_000_000_000D);
        vResult = Vector3L.toVector3L(v1);
        result[6] = vResult.getOverSizedAny(); // should be true.
        result[7] = vResult.getOverSizedZ(); // should be true.
        
        
        
        // RESULT CHECKING
        
        for (int i = 0; i < result.length; i++) {
            if (result[i] == false) {
                System.out.println("False idx is: " + i);
                finalResult = false;
            }
        }
        assertTrue(finalResult);
    }
    
    /**
     * Test conversion of a Long vector to a Double.
     */
    @Test
    public void convertLongVectorToDouble() {
        Vector3d vExpected, vResult;
        Vector3L v1;
        boolean finalResult;
        
        v1 = new Vector3L(5_768_989L, 44_261_999_019L, 222L);
        vExpected = new Vector3d (5_768_989D, 44_261_999_019D, 222D);
        vResult = Vector3L.toVector3d(v1);
        
        finalResult = vExpected.toString().equals(vResult.toString());
        
        assertTrue(finalResult);
    }
    
    /**
     * Test that the vector can be scaled up or down.
     * Vectors that lie outside the Long.MaxSize will 
     * have their overSized booleans set.  Programmers should check these
     * when oversized calculations can happen and deal with this appropriately.
     * i.e. <br> <font="monospaced">
     *  if(v1.overSizedAny == true){<br>
     *      doSomething();<br>
     *  }<br>
     * <br></font>
     * Problem:  Any method that multiplies Long values by a constant can quickly
     * take the Long value out of its bounds.  Of course, the same thing can happen
     * to integers ... and even more quickly.
     */
    @Test
    public void scaleVector() {
        //boolean log = true;
        boolean log = false;
        Vector3L v1, vR0, vR2, vR1;
        double scaleFactor;
        
        boolean result[];
        result = new boolean[10]; // max idx = 9
        for (int i = 0; i < result.length; i++) {
            result[i] = true;
        }
        
        boolean finalResult = true;
        boolean sameX, sameY, sameZ;
        
        // test scale factor of 45 million.
        
        v1 = new Vector3L(25L, 50L, 7_000L);
        scaleFactor = 45_000_000D;
        vR0 = v1.scale(scaleFactor);
        
        sameX = vR0.getX() == 1_125_000_000L;
        sameY = vR0.getY() == 2_250_000_000L;
        sameZ = vR0.getZ() == 315_000_000_000L;
        
        result[0] = sameX && sameY && sameZ;
        result[1] = !vR0.getOverSizedAny();
        
        // test scale factor of 1E18
        
        v1 = new Vector3L(-9_494_001_882L, 222_333_444L,  -6L);
        scaleFactor = 1_000_000_000_000_000D;
        vR1 = v1.scale(scaleFactor);
        
        sameX = vR1.getX() == Long.MIN_VALUE;
        sameY = vR1.getY() == Long.MAX_VALUE;
        sameZ = vR1.getZ() == -6_000_000_000_000_000L;
        
        result[2] = sameX && sameY && sameZ;
        result[3] = vR1.getOverSizedAny();
        result[4] = vR1.getOverSizedX();
        result[5] = vR1.getOverSizedY();
        result[6] = !vR1.getOverSizedZ();
        
        // scale down with a double less than one.
        
        v1 = new Vector3L(-9_494_001_882L, 222_333_444L,  -6L);
        scaleFactor = 0.000_001D;
        vR2 = v1.scale(scaleFactor);
        
        sameX = vR2.getX() == -9_494L;
        sameY = vR2.getY() == 222L;
        sameZ = vR2.getZ() == 0L;
        result[7] = sameX && sameY && sameZ;
        result[8] = !vR2.getOverSizedAny(); // none of the vectors are oversized.
        
        if (log) {
            System.out.println("TEST 1: vR0 is: " + vR0.toString());
            System.out.println("TEST 2: vR1 is: " + vR1.toString());
            System.out.println("TEST 3: vR2 is: " + vR2.toString());
        }
        
        // RESULT CHECKING
        
        for (int i = 0; i < result.length; i++) {
            if (result[i] == false) {
                System.out.println("False idx is: " + i);
                finalResult = false;
            }
        }
        
        assertTrue (finalResult);
    }
    
    /**
     * Test X
     */
    @Test
    public void foreach() {
        boolean log = false;
        
        int[] intarray = new int[10];
        int x = 0;
        for (int i : intarray) {
            i = x;
            x++;
        }
        
        if (log) {
            for (int i : intarray) {
                System.out.println("i = " + i);
            }
            System.out.println("x = " + x);
        }

    }
    
}

// ==================================================================
// VECTORS SHOULD NOT BE MULTIPLIED LIKE THIS.  TOO EASY TO OVERFLOW.
// ==================================================================
//    /**
//     * Test multiplying two long vectors.  Uses BigInteger.
//     */
//    @Test
//    public void multiply() {
//        //boolean log = true;
//        boolean log = false;
//        
//        long nanoTimeInit, nanoTime1, nanoTime2;
//        
//        Vector3L v1 = new Vector3L(       768_989L,  44_261_999L, 222L);
//        Vector3L v2 = new Vector3L(-9_494_001_882L, 222_333_444L,  -6L);
//        
//        nanoTimeInit = System.nanoTime();
//        
//        BigInteger[] vR = v1.multiplyAsBigInt(v2);
//        
//        nanoTime1 = System.nanoTime();
//        
//        boolean sameX = 0 == (vR[0].compareTo(BigInteger.valueOf(-7_300_783_013_237_298L)));
//        boolean sameY = 0 == (vR[1].compareTo(BigInteger.valueOf( 9_840_922_675_994_556L)));
//        boolean sameZ = 0 == (vR[2].compareTo(BigInteger.valueOf(-1_332L)));
//        
//        nanoTime2 = System.nanoTime(); 
//        
//        if (log) {
//            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//            System.out.println("          COMPARISON USING BIGINTEGER");
//            System.out.println("");
//            String sX, sY, sZ ;
//            sX = vR[0].toString();
//            sY = vR[1].toString();
//            sZ = vR[2].toString();
//            System.out.println("sX = " + sX);
//            System.out.println("sY = " + sY);
//            System.out.println("sZ = " + sZ);
//            System.out.println("");
//            System.out.println("sameX = " + sameX);
//            System.out.println("sameY = " + sameY);
//            System.out.println("sameZ = " + sameZ);
//            System.out.println("");
//            System.out.println("Vector manipulation time was: " +  (nanoTime1 - nanoTimeInit));
//            System.out.println("");
//            System.out.println("Vector comparison time was: " + (nanoTime2 - nanoTime1));
//            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//            System.out.println("");
//        }
//        
//        assertTrue(sameX && sameY && sameZ);
//    }
//    
//    
//    /**
//     * Test X
//     */
//    @Test
//    public void multiplyAsDouble() {
//        //boolean log = true;
//        boolean log = false;
//        
//        long nanoTimeInit, nanoTime1, nanoTime2;
//        
//        Vector3L v1 = new Vector3L(       768_989L,  44_261_999L, 222L);
//        Vector3L v2 = new Vector3L(-9_494_001_882L, 222_333_444L,  -6L);
//        
//        nanoTimeInit = System.nanoTime();
//        
//        double[] vR = v1.multiplyAsDouble(v2);
//        
//        nanoTime1 = System.nanoTime();
//        
//        boolean sameX = vR[0] == -7_300_783_013_237_298D;
//        boolean sameY = vR[1] ==  9_840_922_675_994_556D;
//        boolean sameZ = vR[2] == -1_332D ;
//        
//        nanoTime2 = System.nanoTime(); 
//        
//        if (log) {
//            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//            System.out.println("          COMPARISON USING DOUBLES  ");
//            System.out.println("");
//            System.out.println("sX = " + vR[0]);
//            System.out.println("sY = " + vR[1]);
//            System.out.println("sZ = " + vR[2]);
//            System.out.println("");
//            System.out.println("sameX = " + sameX);
//            System.out.println("sameY = " + sameY);
//            System.out.println("sameZ = " + sameZ);
//            System.out.println("");
//            System.out.println("Vector manipulation time was: " +  (nanoTime1 - nanoTimeInit));
//            System.out.println("");
//            System.out.println("Vector comparison time was: " + (nanoTime2 - nanoTime1));
//            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//            System.out.println("");
//        }
//        
//        assertTrue(sameX && sameY && sameZ);
//    }
//    
//    

