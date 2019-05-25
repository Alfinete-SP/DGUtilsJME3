/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dgnUtils.move;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;

/**
 * This is an App State that will allow the user to click on a spatial and move it
 * to a desired location.  This is useful for setting out complex geometry, for the
 * likes of a dashboard, cockpit controls or similar complexities.
 * 
 * To use: 
 * 1. Activate the app state.  A key press can be setup to do this.
 * 2. Click on the geometry to be moved.  A wireframe box will encompass the geometry
 * acting as the cursor.
 * 3. Use the F12 key to bring up the menu and cycle through its options.
 * 4. Use the ENTER key to select the desired option.
 * 
 * Note: all key bindings are removed by this app state.
 * 
 * @author David Niicholson
 */
public class SpatialMoveAS extends AbstractAppState {
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        //TODO: initialize your AppState, e.g. attach spatials to rootNode
        //this is called on the OpenGL thread after the AppState has been attached
    }
    
    @Override
    public void update(float tpf) {
        //TODO: implement behavior during runtime
    }
    
    @Override
    public void cleanup() {
        //TODO: clean up what you initialized in the initialize method,
        //e.g. remove all spatials from rootNode
        //this is called on the OpenGL thread after the AppState has been detached
    }
}
