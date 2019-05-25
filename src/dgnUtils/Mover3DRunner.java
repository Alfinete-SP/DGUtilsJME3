/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dgnUtils;

import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import dgnUtils.move.Mover3D;

/**
 *
 * @author David Niicholson
 */
public class Mover3DRunner extends SimpleApplication{
    
    Spatial dice;
    Vector3f finalPlace;
    float moveSpeed;
    
    public Mover3DRunner(){

    }
    
    public static void main(String[] args) {
        Mover3DRunner app = new Mover3DRunner();
        app.start();
    }
    
    @Override
    public void simpleInitApp() {
        removeAppStates();
        addLights();
        addDice();
        adjustCameraPos();
        setUpMoverObject();
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

    Mover3D diceMover;
    
    @Override
    public void simpleUpdate(float tpf) {
        diceMover.move(tpf);
    }

    private void addDice() {
        dice = assetManager.loadModel("Models/dice/dice.j3o");
        dice.rotate(0.0f, 3.142f/4, 0f);
        dice.rotate(0.1f, 0f, 0f);
        //Material m = assetManager.loadMaterial("Materials/dice.j3m");
        //Material m = new Material (assetManager, "Materials/dice.j3m");
        //dice.setMaterial(m);
        rootNode.attachChild(dice);
        
        //m.setColor("Color", ColorRGBA.Blue);
    }

    private void removeAppStates() {
        stateManager.detach(stateManager.getState(FlyCamAppState.class));
    }

    Vector3f camPosition;
    private void adjustCameraPos() {
        camPosition = new Vector3f(0f, -2f, 15f);
        cam.setLocation(camPosition);
    }

    private void setUpMoverObject() {
        finalPlace = new Vector3f(2f, -5f, 5f);
        moveSpeed = 1;
        
        //diceMover = new Mover3D(this, dice, finalPlace, moveSpeed, Mover3D.MOVE_THRU_SHORTEST); 
        //diceMover = new Mover3D(this, dice, finalPlace, moveSpeed, Mover3D.MOVE_X_THEN_Y_THEN_Z);
        //diceMover = new Mover3D(this, dice, finalPlace, moveSpeed, Mover3D.MOVE_X_THEN_Z_THEN_Y);
        //diceMover = new Mover3D(this, dice, finalPlace, moveSpeed, Mover3D.MOVE_Y_THEN_X_THEN_Z);
        //diceMover = new Mover3D(this, dice, finalPlace, moveSpeed, Mover3D.MOVE_Y_THEN_Z_THEN_X);
        //diceMover = new Mover3D(this, dice, finalPlace, moveSpeed, Mover3D.MOVE_Z_THEN_X_THEN_Y);
        diceMover = new Mover3D(this, dice, finalPlace, moveSpeed, Mover3D.MOVE_Z_THEN_Y_THEN_X);
    }
    
}
