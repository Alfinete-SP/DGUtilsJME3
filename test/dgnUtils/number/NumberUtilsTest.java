/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dgnUtils.number;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author David Niicholson
 */
public class NumberUtilsTest {
    
    public NumberUtilsTest() {
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
     * Should take in a float and return a string with value tuncated to
     * specified decimal places.
     */
    @Test
    public void testFixValueToCertainDecimalPlaces() {
        SpatialPositionAndDirectionNumberUtils ft = new SpatialPositionAndDirectionNumberUtils();
        ft.setLog(false);
        float num = -0.123456789f;
        int length = 5;
        String result, expected;
        expected = "-0.12";
        result = ft.setNumberOfDigits(num, length);
        assertEquals(result, expected);
    }
    
    /**
     * test that short number strings can be used.
     */
    @Test
    public void testFillTrailingZeros1() {
        SpatialPositionAndDirectionNumberUtils ft = new SpatialPositionAndDirectionNumberUtils();
        ft.setLog(false);
        float num = 0.1f;
        int length = 5;
        String result, expected;
        expected = "0.100";
        result = ft.setNumberOfDigits(num, length);
        assertEquals(result, expected);
    }
    
    /**
     * test that short number strings can be used.
     */
    @Test
    public void testFillTrailingZeros2() {
        SpatialPositionAndDirectionNumberUtils ft = new SpatialPositionAndDirectionNumberUtils();
        ft.setLog(false);
        float num = 0.95f;
        int length = 5;
        String result, expected;
        expected = "0.950";
        result = ft.setNumberOfDigits(num, length);
        assertEquals(result, expected);
    }
    
    /**
     * test a format especially for the camera direction.
     * It should give a String formated with three dec places and 
     * a sign after the output.
     * 
     * i.e.  0.230+  or 0.145-
     */
    @Test
    public void test01CameraNumberFormattedString() {
        SpatialPositionAndDirectionNumberUtils ft = new SpatialPositionAndDirectionNumberUtils();
        ft.setLog(false);
        float num = -1.0f;
        String result, expected;
        expected = "1.000-";
        result = ft.getCameraDirectionFormattedString(num);
        assertEquals(expected, result);
    }
    
    @Test
    public void test02CameraNumberFormattedString() {
        SpatialPositionAndDirectionNumberUtils ft = new SpatialPositionAndDirectionNumberUtils();
        ft.setLog(false);
        float num = 1.0f;
        String result, expected;
        expected = "1.000+";
        result = ft.getCameraDirectionFormattedString(num);
        assertEquals(expected, result);
    }
    
    @Test
    public void test03CameraNumberFormattedString() {
        SpatialPositionAndDirectionNumberUtils ft = new SpatialPositionAndDirectionNumberUtils();
        ft.setLog(false);
        float num = -0.012345f;
        String result, expected;
        expected = "0.012-";
        result = ft.getCameraDirectionFormattedString(num);
        assertEquals(expected, result);
    }
    
    @Test
    public void test04CameraNumberFormattedString() {
        SpatialPositionAndDirectionNumberUtils ft = new SpatialPositionAndDirectionNumberUtils();
        ft.setLog(false);
        float num = 0.012345f;
        String result, expected;
        expected = "0.012+";
        result = ft.getCameraDirectionFormattedString(num);
        assertEquals(expected, result);
    }
    
    /**
     * Test that values greater than 1.0 or less than 1.0 are not accepted.
     */
    @Test
    public void testValueAcceptedNotLessThanNegativeOne() {
        SpatialPositionAndDirectionNumberUtils ft = new SpatialPositionAndDirectionNumberUtils();
        ft.setLog(false);
        float num = -1.1f;
        String result, expected;
        expected = " < 1-";
        result = ft.getCameraDirectionFormattedString(num);
        assertEquals(expected, result);
    }
    
     /**
     * Test that values greater than 1.0 or less than 1.0 are not accepted.
     */
    @Test
    public void testValueAcceptedNotGreaterThanOne() {
        SpatialPositionAndDirectionNumberUtils ft = new SpatialPositionAndDirectionNumberUtils();
        ft.setLog(false);
        float num = 1.1f;
        String result, expected;
        expected = " > 1+";
        result = ft.getCameraDirectionFormattedString(num);
        assertEquals(expected, result);
    }
    
    /**
     * Absolute value of numbers less than 0.001 should be zero.
     */
    @Test
    public void test01ExtreemlySmallNumbers() {
        SpatialPositionAndDirectionNumberUtils ft = new SpatialPositionAndDirectionNumberUtils();
        ft.setLog(false);
        float num = 0.0009f;
        String result, expected;
        expected = "0.000+";
        result = ft.getCameraDirectionFormattedString(num);
        assertEquals(expected, result);
    }
    
    @Test
    public void test02ExtreemlySmallNumbers() {
        SpatialPositionAndDirectionNumberUtils ft = new SpatialPositionAndDirectionNumberUtils();
        ft.setLog(false);
        float num = -0.0009f;
        String result, expected;
        expected = "0.000-";
        result = ft.getCameraDirectionFormattedString(num);
        assertEquals(expected, result);
    }
    
    /**
     * test formatting for camera position.
     */
    @Test
    public void test01CamPosFormatting() {
        SpatialPositionAndDirectionNumberUtils ft = new SpatialPositionAndDirectionNumberUtils();
        ft.setLog(false);
        float num = 123.456789f;
        String result, expected;
        expected = "0123.45+";  // two decimal places is mm precison in the game.
        result = ft.getCamerPositionFormattedString(num);
        assertEquals(expected, result);
    }
    
    @Test
    public void test02CamPos() {
        SpatialPositionAndDirectionNumberUtils ft = new SpatialPositionAndDirectionNumberUtils();
        ft.setLog(false);
        float num = -123.456789f;
        String result, expected;
        expected = "0123.45-";  // two decimal places is mm precison in the game.
        result = ft.getCamerPositionFormattedString(num);
        assertEquals(expected, result);
        
    }
    
    @Test
    public void test03CamPos() {
        SpatialPositionAndDirectionNumberUtils ft = new SpatialPositionAndDirectionNumberUtils();
        ft.setLog(false);
        float num = 3.0f;
        String result, expected;
        expected = "0003.00+";  // two decimal places is mm precison in the game.
        result = ft.getCamerPositionFormattedString(num);
        assertEquals(expected, result);
        
    }
    
    @Test
    public void test04CamPos() {
        SpatialPositionAndDirectionNumberUtils ft = new SpatialPositionAndDirectionNumberUtils();
        ft.setLog(false);
        float num = -3.0f;
        String result, expected;
        expected = "0003.00-";  // two decimal places is mm precison in the game.
        result = ft.getCamerPositionFormattedString(num);
        assertEquals(expected, result);
        
    }
    
    @Test
    public void test05CamPos() {
        SpatialPositionAndDirectionNumberUtils ft = new SpatialPositionAndDirectionNumberUtils();
        ft.setLog(false);
        float num = 1234567.0f;
        String result, expected;
        expected = "++++.+++";  // two decimal places is mm precison in the game.
        result = ft.getCamerPositionFormattedString(num);
        assertEquals(expected, result);
        
    }
    
    @Test
    public void test06CamPos() {
        SpatialPositionAndDirectionNumberUtils ft = new SpatialPositionAndDirectionNumberUtils();
        ft.setLog(false);
        float num = -1234567.0f;
        String result, expected;
        expected = "----.---";  // two decimal places is mm precison in the game.
        result = ft.getCamerPositionFormattedString(num);
        assertEquals(expected, result);
        
    }
}