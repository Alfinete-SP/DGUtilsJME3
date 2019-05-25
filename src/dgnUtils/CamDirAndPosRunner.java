/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dgnUtils;

import com.jme3.app.SimpleApplication;
import com.jme3.math.Vector2f;
import dgnUtils.debug.CamDirAndPosAppState;

/**
 *
 * @author David Niicholson
 */
public class CamDirAndPosRunner extends SimpleApplication {

    public static void main(String[] args){
        CamDirAndPosRunner app = new CamDirAndPosRunner();
        app.start();
    }
    
    
    public CamDirAndPosRunner(){
        //vitrine = new Vitrine();
    }
    
    
    @Override
    public void simpleInitApp() {
        CamDirAndPosAppState debugState;
        debugState = new CamDirAndPosAppState(this, new Vector2f(100f ,100f));
        
        stateManager.attach(debugState);
    }
    
}
