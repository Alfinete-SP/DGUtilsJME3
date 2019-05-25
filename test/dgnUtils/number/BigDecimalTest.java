/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dgnUtils.number;

import java.math.BigDecimal;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author David
 */
public class BigDecimalTest {
    
    public BigDecimalTest() {
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
    public void testBigDecimalStringFormatting() {
        boolean log = false;
        
        BigDecimal bigDecimal;
        
        String str, expected, result;
        str = "12345";
        expected = "12345";   // The real result wanted was "12,345".  But BigDecimal does not do this formatting.
        bigDecimal = new BigDecimal(str);
        
        result = bigDecimal.toPlainString();                                    if (log) {System.out.println("result = " + result);}
        
        assertEquals(expected, result);
        
    }
}
