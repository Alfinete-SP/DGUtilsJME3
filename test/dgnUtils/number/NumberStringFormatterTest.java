/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dgnUtils.number;

import dgnUtils.exception.NegativeDecimalPlacesException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author David
 */
public class NumberStringFormatterTest {
    
    private boolean log = false;
    
    public NumberStringFormatterTest() {
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
     * Test of formatThousands method, of class NumberStringFormatter.
     */
    @Test
    public void testFormatThousands() {
        NumberStringFormatter formatter = new NumberStringFormatter();
        formatter.setLog(false);
        String str, expResult, result;
        
        str = "12345";
        expResult = "12,345";
        result = formatter.formatThousands(str);
        assertEquals(expResult, result);
    }

    @Test
    public void numberFormat_millionsComma_places4And8() {
        NumberStringFormatter formatter = new NumberStringFormatter();
        formatter.setLog(false);
        String str, expResult, result;
    
        str = "1234567";
        expResult = "1,234,567";
        result = formatter.formatThousands(str);
        assertEquals(expResult, result);
    }
    
    /**
     *
     */
    @Test
    public void numberFormat_millionsComma_noCommas() {
        NumberStringFormatter formatter = new NumberStringFormatter();
        formatter.setLog(false);
        String str, expResult, result;
        str = "123";
        expResult = "123";
        result = formatter.formatThousands(str);
        assertEquals(expResult, result);
        
    }
    
    /**
     * Format thousands with a point instead of a comma.
     */
    @Test
    public void testThousandsWithPoints() {
        NumberStringFormatter formatter;
        formatter = new NumberStringFormatter();
        formatter.setLog(false);
        
        formatter.setCommaThousandsSeparatorToPoint();
        
        String str, expected, result;
        str = "12345";
        expected = "12.345";
        
        result = formatter.formatThousands(str);
        assertEquals(result, expected);
        
    }
    
    /**
     *  The method should return a result with three decimal places.
     */
    @Test
    public void testDecimalPointAndThousandsFormatting() throws NegativeDecimalPlacesException {
        NumberStringFormatter formatter;
        formatter = new NumberStringFormatter();
        formatter.setLog(false);
        
        String str, expected, result;
        str = "9876123.456789";
        expected = "9,876,123.456";
        
        int numDecPlaces = 3;
        
        result = formatter.formatDecimalPlaces(str, numDecPlaces);
        //System.out.println("testDecimalPointAndThousandsFormatting test");
        //System.out.println("str = " + str + "     expected = " + expected + "     result = " + result);
        assertEquals(expected , result);
    }    
    
    /**
     *  The method should return a result with three decimal places.
     */
    @Test
    public void testDecimalPointFormatting01() throws NegativeDecimalPlacesException {
        NumberStringFormatter formatter = new NumberStringFormatter();
        formatter.setLog(false);
        String str, expected, result;
        str = "123.456789";
        expected = "123.456";
        result = formatter.formatDecimalPlaces(str, 3);
        assertEquals(expected , result);
    }
    
    /**
     *  The method should return a result with three decimal places.
     */
    @Test
    public void testDecimalPointFormatting02() throws NegativeDecimalPlacesException {
        NumberStringFormatter formatter = new NumberStringFormatter();
        formatter.setLog(false);
        String str, expected, result;
        str = "123";
        expected = "123.000";
        result = formatter.formatDecimalPlaces(str, 3);
        assertEquals(expected , result);
    }
        
    /**
     *  The method should return a result with three decimal places.
     */
    @Test
    public void testDecimalPointFormatting03() throws NegativeDecimalPlacesException {
        NumberStringFormatter formatter = new NumberStringFormatter();
        formatter.setLog(false);
        String str, expected, result;
        str = "1.4";
        expected = "1.400";
        result = formatter.formatDecimalPlaces(str, 3);
        assertEquals(expected , result);
    }
    
    /**
     *  The method should return a result with no decimal place.
     */
    @Test
    public void testDecimalPointFormatting04() throws NegativeDecimalPlacesException {
        NumberStringFormatter formatter = new NumberStringFormatter();
        formatter.setLog(false);
        String str, expected, result;
        str = "1.4";
        expected = "1";
        result = formatter.formatDecimalPlaces(str, 0);
        assertEquals(expected , result);
    }
    
    /**
     *
     */
    @Test (expected = NegativeDecimalPlacesException.class)
    public void testExceptionForNegativeDecimalPlaces() throws NegativeDecimalPlacesException{
        NumberStringFormatter formatter;
        formatter = new NumberStringFormatter();
        formatter.setLog(false);
        String str, expected, result;
        str = "123.456789";
        expected = "123.456";
        result = formatter.formatDecimalPlaces(str, -3);
        assertEquals(expected , result);
    }
    
    /**
     *
     */
    @Test
    public void checkDecimalPointPositionIsNotCorrect_BeforeDecimalImpl() {
        String testStr = "123.456"; // dec pl pos is 3.  Positions = 3, 2, 1, 0. (array indexes)
        String expectedStr = "error";
        char[] test = testStr.toCharArray(); 
        char[] expected = expectedStr.toCharArray();
        int decPos = 2;  // Inserting a 3 here will make the test fail.
        
        NumberStringFormatter formatter = new NumberStringFormatter();
        char[] returned = formatter.getCharArrayBeforeDecimalPoint(test, decPos);
        
        assertArrayEquals(returned, expected); 
    }

    @Test
    public void checkDecimalPointPositionIsNotCorrect_AfterDecimalImpl() {
        String testStr = "123.4567"; // dec pl pos is 3.  Positions = 3, 2, 1, 0. (array indexes)
        String expectedStr = "error";
        char[] test = testStr.toCharArray(); 
        char[] expected = expectedStr.toCharArray();
        int decPos = 2;  // Inserting a 3 here will make the test fail.
        
        NumberStringFormatter formatter = new NumberStringFormatter();
        char[] returned = formatter.getCharArrayBeforeDecimalPoint(test, decPos);
        
        assertArrayEquals(returned, expected); 
    }
    
    /**
     *
     */
    @Test
    public void getCharArray_Before_DecimalPointPosition() {
        this.log = false;
        String testStr = "123.4567"; // dec pl pos is 3.  Positions = 0, 1, 2, 3, 4 (array indexes)
        String expectedStr = "123";
        char[] test = testStr.toCharArray(); 
        char[] expected = expectedStr.toCharArray();
        int decPos = 3;  
        
        NumberStringFormatter formatter = new NumberStringFormatter();
        formatter.setLog(false);
        
        if(log){ for(int i = 0; i < 3; i++){ System.out.println("char = " + test[i]);  }  }
        
        char[] returned = formatter.getCharArrayBeforeDecimalPoint(test, decPos);
        
        assertArrayEquals(returned, expected); 
        
        this.log = false;
    }
    
    /**
     *
     */
    @Test
    public void getCharArray_After_DecimalPointPosition() {
        this.log = false;
        NumberStringFormatter formatter = new NumberStringFormatter();
        formatter.setLog(false);
        
        String testStr = "123.4567"; // dec pl pos is 3.  Positions = 0, 1, 2, 3, 4 (array indexes)
        String expectedStr = "4567";
        char[] test = testStr.toCharArray(); 
        char[] expected = expectedStr.toCharArray();
        int decPos = 3;  
        
        char[] returned = formatter.getCharArrayAfterDecimalPoint(test, decPos);

        if(log){ for(int i = decPos + 1; i < test.length;     i++){ System.out.println("char = " + test[i]);  }  
                 for(int i = 0;          i < returned.length; i++){ System.out.println("char = " + returned[i]);  }   }
        
        assertArrayEquals(returned, expected); 
        
        this.log = false;
    }
    
    /**
     * Takes a char array and adds zeros if the number of places is insufficient.
     */
    @Test
    public void addZerosToDecimalValue_1_SameSizeNumbers() {
        this.log = false;
        String testStr = "123"; //  Positions = 0, 1, 2, 3, 4 (array indexes)
        String expectedStr = "123";
        char[] test = testStr.toCharArray(); 
        char[] expected = expectedStr.toCharArray();        
        int numDecPl = 3;
                
        NumberStringFormatter formatter = new NumberStringFormatter();
        formatter.setLog(true);
        char[] result = formatter.manipulateDecimalPortionOfNumber(test, numDecPl);
        
        assertArrayEquals(expected, result);
    }
    
    /**
     * Takes a char array and adds zeros if the number of places is insufficient.
     */
    @Test
    public void addZerosToDecimalValue_2_ExpectedHasMoreDigits() {
        this.log = false;
        String testStr = "123"; // Positions = 0, 1, 2, 3, 4 (array indexes)
        String expectedStr = "123000";
        char[] test = testStr.toCharArray(); 
        char[] expected = expectedStr.toCharArray();        
        int numDecPl = 6;
                
        NumberStringFormatter formatter = new NumberStringFormatter();
        formatter.setLog(true);
        char[] result = formatter.manipulateDecimalPortionOfNumber(test, numDecPl);
        
        assertArrayEquals(expected, result);
    }
    
    /**
     * Takes a char array and removes excess decimal places.
     */
    @Test
    public void addZerosToDecimalValue_3_ExpectedHasLessDigits() {
        this.log = false;
        String testStr = "123456"; // Positions = 0, 1, 2, 3, 4 (array indexes)
        String expectedStr = "123";
        char[] test = testStr.toCharArray(); 
        char[] expected = expectedStr.toCharArray();        
        int numDecPl = 3;
                
        NumberStringFormatter formatter = new NumberStringFormatter();
        formatter.setLog(true);
        char[] result = formatter.manipulateDecimalPortionOfNumber(test, numDecPl);
        
        assertArrayEquals(expected, result);
    }
    
    /**
     *
     */
    @Test
    public void formatThousandsAsCharArray() {
        this.log = false;
        String testStr = "123456789"; // Positions = 0, 1, 2, 3, 4 (array indexes)
        String expectedStr = "123,456,789";
        char[] test = testStr.toCharArray(); 
        char[] expected = expectedStr.toCharArray();        
        int numDecPl = 3;
                
        NumberStringFormatter formatter = new NumberStringFormatter();
        formatter.setLog(false);
        char[] result = formatter.formatThousandsAsCharArray(test);
        String resultStr = new String(result);
        
        assertEquals(expectedStr, resultStr);  // Strings are easy to compare.
        assertArrayEquals(expected, result);   // arrays are not so easy to compare and see final result.     
        
    }
    
    
    /**
     *
     */
    @Test
    public void t01_ReformatScientificToDecimal() {
        this.log = false;
        
        NumberStringFormatter formatter = new NumberStringFormatter();
        formatter.setLog(false);
        
        String testStr = "1.5E-5"; 
        String expectedStr = "0.000015";
        
        int decimalPlaces = 6;
        String returned = "";

        try {
            //returned = formatter.forTestingOnly_FormatScientificNumbers(testStr, "7867");
            returned = formatter.formatDecimalPlaces(testStr, decimalPlaces);
        } catch (Exception e) {
            String reason = "Negative for decimal places was used.";
            System.out.println(reason);
            fail(reason);
        }
        
        assertEquals(expectedStr, returned);
    }
    
    /**
     *  Maybe there should be a method that will trim trailing zeros if a boolean is set. 
     */
    @Test
    public void checkTrailingZerosAreRemoved() { // check that 1.0E-4 gives 0.0001
        //boolean log1 = true;
        boolean log1 = false;
        
        NumberStringFormatter formatter = new NumberStringFormatter();
        
        formatter.setLog(log1);
        this.log = log1;
        
        String testStr = "1.0E-4"; 
        String expectedStr = "0.0001";
        
        int decimalPlaces = 4;
        String returned = "";

        try {
            //returned = formatter.forTestingOnly_FormatScientificNumbers(testStr, "7867");
            returned = formatter.formatDecimalPlaces(testStr, decimalPlaces);
        } catch (Exception e) {
            String reason = "Negative for decimal places was used.";
            System.out.println(reason);
            fail(reason);
        }
        
        assertEquals(expectedStr, returned);        
    }


} // end class
