/*

 */
package dgnUtils.number;

import dgnUtils.text.StringToScreen;
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
public class StringToScreenTest {
    
    public StringToScreenTest() {
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
    public void testSetAndGetArraySize() {
        int arraySize1 = 0;
        StringToScreen sts = new StringToScreen();
        sts.setArraySize(arraySize1);
        assertEquals(arraySize1, sts.getArraySize());
    }

    @Test
    public void testInstanceNotNull() {
        StringToScreen sts = new StringToScreen();
        assertNotNull(sts);
    }
    
    @Test
    public void testArrayCreated() {
        StringToScreen sts = new StringToScreen();
        assertTrue(sts.getArraySize() == 1000);
        assertNotNull(sts.getStringArray());
        assertTrue(sts.getStringArray().length == 1000);
        assertTrue(sts.getStringArray()[0] == null);
    }
    
    @Test
    public void testSetString() {
        StringToScreen sts = new StringToScreen();
        assertTrue(sts.getArrayPointer() == 0);  // Check that the lastIndex pointer is 0;
        sts.setString("test string 001");
        assertTrue(sts.getArrayPointer() == 1);
        assertTrue(sts.getStringArray()[0].equals("test string 001"));
        assertTrue(sts.getStringArray()[1] == null);
    }
    
    @Test
    public void testArrayExtension() {
        StringToScreen stc = new StringToScreen();
        assertTrue(stc.getStringArray().length == 1000);
        stc.setArrayPointer(999);
        assertTrue(stc.getArrayPointer() == 999);
        stc.setString("test string 001");
        assertTrue(stc.getArrayPointer() == 1000);
        stc.setString("test string 002");
        assertTrue(stc.getStringArray()[1000].equals("test string 002"));
        assertTrue(stc.getStringArray().length == 2000);
    }
    
    @Test
    public void testConstructorWithNegativeValues() {
        StringToScreen sts = new StringToScreen(-10, -25);
        assertTrue(sts.getArraySize() == 10);
        assertTrue(sts.getSizeIncrement() == 10);
    }
    
    @Test
    public void testOutputToScreen() {
        StringToScreen sts = new StringToScreen(20, 20);
        assertTrue(sts.getArraySize() == 20);
        assertTrue(sts.getSizeIncrement() == 20);    
        assertTrue(sts.getStringArray().length == 20);
        String str, s1;
        for (int i = 0; i < 42; i++){
            if(i < 10){
                s1 = "0" + String.valueOf(i);
            } else {
                s1 = String.valueOf(i);  
            }    
            str = "test string " + s1;
            sts.setString(str);
        }
        
        assertTrue(sts.getStringArray()[5].equals("test string 05"));
        assertTrue(sts.getStringArray().length == 60);
        assertTrue(sts.getArrayPointer() == 42);
        assertTrue(sts.getArraySize() == 60);
        
        sts.getStringArray()[0]  = "===============================STRING-TO-SCREEN-TEST=======================================";
        sts.setString(             "===============================STRING-TO-SCREEN-TEST=======================================");
        
        /* outputToScreen dumps the whole string array to the screen.  Uncomment
        the following line to see the screen dump, otherwise leave it commented out.  */
        //sts.outputToScreen();
    }

}
