/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dgnUtils;

import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import dgnUtils.debug.SpatialDirAndPosAppState;

/**
 *
 * @author David Niicholson
 */
public class SpatialDirAndPosRunner extends SimpleApplication {

    public static void main(String[] args){
        SpatialDirAndPosRunner app = new SpatialDirAndPosRunner();
        app.start();
    }
    
    
    public SpatialDirAndPosRunner(){
        //vitrine = new Vitrine();
    }
    
    
    @Override
    public void simpleInitApp() {
        // Create Spatial - load dice.
        addLights();
        addDice();
        
        SpatialDirAndPosAppState debugState;
        //debugState = new SpatialDirAndPosAppState(this, dice, new Vector2f(100f ,100f));
        debugState = new SpatialDirAndPosAppState(this, dice);
        
        stateManager.attach(debugState);
    }
    
    Spatial dice;
    
    private void addDice() {
        dice = assetManager.loadModel("Models/dice/dice.j3o");
//        dice.rotate(0.0f, 3.142f/4, 0f);
//        dice.rotate(0.1f, 0f, 0f);
        //Material m = assetManager.loadMaterial("Materials/dice.j3m");
        //Material m = new Material (assetManager, "Materials/dice.j3m");
        //dice.setMaterial(m);
        rootNode.attachChild(dice);
        
        //m.setColor("Color", ColorRGBA.Blue);
    }
    
        private void addLights() {
        DirectionalLight dLight = new DirectionalLight();
        dLight.setDirection(new Vector3f(0.3f, 0f, -1f));
        dLight.setColor(ColorRGBA.Gray);
        rootNode.addLight(dLight);
        
        AmbientLight aLight = new AmbientLight();
        aLight.setColor(new ColorRGBA(0.4f,0.4f,0.4f,1.0f));
        rootNode.addLight(aLight);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //dice.rotate(0f, 0f, 1f * tpf);
    }
    
}
