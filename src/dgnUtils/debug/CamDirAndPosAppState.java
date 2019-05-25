/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dgnUtils.debug;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.math.Vector4f;
import com.jme3.scene.Node;
import dgnUtils.number.SpatialPositionAndDirectionNumberUtils;
import dgnUtils.rotator.Rotator;
import tonegod.gui.controls.text.Label;
import tonegod.gui.controls.windows.Panel;
import tonegod.gui.core.Screen;

/**
 * This app state puts out a label on the screen which shows the camera position
 * and direction with specially formatted data.<p>
 * 
 * If the distance values are greater than +/- 999 meters, then the formatted output
 * will be +++.++ or ---.-- respectively.  If a value is between 0 and +1, the outputted
 * value will be +000.00 and if between negative one and zero, the output will be
 * -000.00 <p>
 * 
 * A second constructor is provided if you want to reposition the panel.  This constructor
 * takes a Vector2f, which is the (x,y) position of the panel.  (0,0) is top right,  
 * I think.
 
 * 
 * @author David Niicholson
 */
public class CamDirAndPosAppState extends AbstractAppState {
    
    //boolean log = true;
    boolean log = false;

    public CamDirAndPosAppState(SimpleApplication app1){
        //vitrine = vitrine1;
        position = new Vector2f(20f, 20f);
        rootNode = app1.getRootNode();
        guiNode = app1.getGuiNode();
        assetManager = app1.getAssetManager();
        app = app1;
        numberUtils = new SpatialPositionAndDirectionNumberUtils();  //vitrine.getNumberUtils();
        configureGui();
    }
    
    public CamDirAndPosAppState(SimpleApplication app1, Vector2f position1){
        //vitrine = vitrine1;
        position = position1;
        rootNode = app1.getRootNode();
        guiNode = app1.getGuiNode();
        assetManager = app1.getAssetManager();
        app = app1;
        numberUtils = new SpatialPositionAndDirectionNumberUtils();  //vitrine.getNumberUtils();
        configureGui();
    }
            
    Vector2f position;
    AssetManager assetManager;
    Node rootNode, guiNode;
    Rotator canopyRotator;
    SimpleApplication app;
    
    Screen screen;
    Label label00, label01;
    Panel background;
    
    /*
     * Main GUI setup.
     */
    private void configureGui(){
        if (log){System.out.println(               "\n" +
                "===================================\n" +
                "DebugOverlayAppState logging is ON.\n" +
                "===================================\n"
                );}
        
        if(log){System.out.println("app instance is : " + app.toString());}
        //BitmapFont font = assetManager.loadFont("Interface/Fonts/Monospaced.fnt");
        //BitmapFont font = assetManager.loadFont("Interface/Fonts/Arial.fnt");
        //BitmapFont font = assetManager.loadFont("Interface/Fonts/Default.fnt");
        //BitmapFont font = assetManager.loadFont("tonegod/gui/style/def/Fonts/tonegodGUI.fnt");
        //BitmapFont font = assetManager.loadFont("tonegod/gui/style/def/Fonts/base-font-white.fnt");
        screen = new Screen(app, "tonegod/gui/style/def/style_map.gui.xml");
        //screen = new Screen(app, "tonegod/gui/style/def/style_map.gui.xml", width, height);

        float screenWidth = screen.getWidth();
        float screenHeight = screen.getHeight();
        float width, height, indent;
        
        //width = screenWidth / 3f;
        //height = screenHeight / 3f;
        width  = 370f;
        height = 100f;
        indent = 100f;
        
        background = new Panel(
                screen,                                       // Screen
                "background",                                 // ID
                position,                                     // position  Vector2f
                new Vector2f(width, height),                  // size
                new Vector4f(10f, 10f, 10f, 10f),             // resize borders
                "tonegod/gui/style/def/Window/panel_x.png");  // image

        label00 = new Label(
                screen,                                             // Element 
                "label00",                                          // ID
                new Vector2f(15f, 15f),                             // position
                new Vector2f(width - 30, 20f),                      // size
                //new Vector4f(5f, 5f, 5f, 5f),                       // borders
                Vector4f.ZERO,
                //"tonegod/gui/style/def/TextField/text_field_x.png"  // image
                null                                                // image  
                );
        
        label00.setFont("Interface/Fonts/Monospaced.fnt");
        label00.setFontSize(16f);
        label00.setFontColor(ColorRGBA.White);
        label00.setText("Cam dir: ");
        background.addChild(label00);
        
        
        label01 = new Label(
                screen,                                       // Element 
                "label01",                                    // ID
                new Vector2f(15, 60f),                        // position
                new Vector2f(width - 30, 20),                  // size
                Vector4f.ZERO,
                //new Vector4f(20f, 20f, 20f, 20f),             // borders
                null
                //"tonegod/gui/style/def/Window/panel_x.png"    // image
                );
        label01.setFont("Interface/Fonts/Monospaced.fnt");
        label01.setFontSize(16f);
        label01.setFontColor(ColorRGBA.White);
        label01.setText("Rel Pos: ");
        background.addChild(label01);
        screen.addElement(background);
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        guiNode.addControl(screen);
    }
    
    long time, startTime;
    Quaternion camRotation;
    Vector3f camDirV3f, camPos;
    float camXDir, camYDir, camZDir, camXPos, camYPos, camZPos;
    String camDirStr, camXDirStr, camYDirStr, camZDirStr;
    String camPosStr, camXPosStr, camYPosStr, camZPosStr;
    SpatialPositionAndDirectionNumberUtils numberUtils;
    
    @Override
    public void update(float tpf) {
        camDirV3f = app.getCamera().getDirection();
        camXDir = camDirV3f.x;
        camYDir = camDirV3f.y;
        camZDir = camDirV3f.z;
        camXDirStr = numberUtils.getCameraDirectionFormattedString(camXDir);
        camYDirStr = numberUtils.getCameraDirectionFormattedString(camYDir);
        camZDirStr = numberUtils.getCameraDirectionFormattedString(camZDir);
        
        camDirStr = "Cam dir: (x)" + camXDirStr + " (y)" + camYDirStr + " (z)" + camZDirStr ;
        label00.setText(camDirStr);
        
        camPos = app.getCamera().getLocation();
        camXPosStr = numberUtils.getCamerPositionFormattedString(camPos.x);
        camYPosStr = numberUtils.getCamerPositionFormattedString(camPos.y);
        camZPosStr = numberUtils.getCamerPositionFormattedString(camPos.z);
        camPosStr = "Cam Pos: " + camXPosStr + ", " + camYPosStr + ", " + camZPosStr;
        label01.setText(camPosStr);
    }
    
    @Override
    public void cleanup() {
        super.cleanup();
        //TODO: clean up what you initialized in the initialize method,
        //e.g. remove all spatials from rootNode
        //this is called on the OpenGL thread after the AppState has been detached
        guiNode.removeControl(screen);
    }
}


