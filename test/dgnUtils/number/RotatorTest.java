/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dgnUtils.number;

import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import dgnUtils.rotator.Rotator;
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
public class RotatorTest {
    
    public RotatorTest() {
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
    public void testRotateThruShortestAngle(){
        float tpf = .005f;
        float finalAngle = -175f * FastMath.PI / 180;
        float initialAngle = 0f;
        
        Node node = new Node();
        Vector3f zAxis = Vector3f.UNIT_Z;
        
        Quaternion initialRot = new Quaternion();
        initialRot.fromAngleAxis(initialAngle, zAxis);
        
        Quaternion finalRot = new Quaternion();
        finalRot.fromAngleAxis(finalAngle, zAxis);
        
        node.setLocalRotation(initialRot);
        Rotator rotator = new Rotator(node, zAxis);
        
        // get printouts 
        rotator.rotateThruShortestAngleFromCurrent(finalAngle, 1f);
        
        int d = rotator.getDir();
        assertTrue (d == -1);
        
        rotator.rotate(tpf);
        assertFalse(rotator.getRotationFinished());
        
    }
}