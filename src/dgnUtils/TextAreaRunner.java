package dgnUtils;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.font.Rectangle;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import dgnUtils.text.TextArea;

/**
 * test
 * @author David Nicholson
 */
public class TextAreaRunner extends SimpleApplication {

    String[] labelStrings;
    TextArea textArea;
    Node label;
    
    
    public static void main(String[] args) {
        TextAreaRunner app = new TextAreaRunner();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        flyCam.setMoveSpeed(10f);
        labelStrings = new String[]{
            "    There was an interesting story once of a man who went for a walk with his dog.", 
            "    He took his dog to the park and there he found a note on one of the park benches.", 
            "    He took a look at the note and, funnily enough, it was addressed to him.", 
            "    Who could have written this note to me, he thought ... some one must be stalking me and I don't know it.", 
            "    He put the note in the top pocket of his shirt and walked off with his dog, pensive about the note he had just found."};
        //setupLabel();
        //setupMultiLineLabel_NoInput();
        setupMultiLineLabel_StringArrayInput();
    }

    
    private void setupMultiLineLabel_StringArrayInput(){
        
        textArea = new TextArea(this, labelStrings, 20f);
        //Vector3f offset = new Vector3f(-10f, 10f, 0f);
        //Vector3f offset = new Vector3f(-20f, -20f, 0f);
        //mll.centerLabelAtPosition(offset);
        
        //FIX THIS TO BE MORE EXACT IN ITS POSITION.
        textArea.add1x1DebugBoxToOrigin(1f, ColorRGBA.Yellow);
        
        
        textArea.addBackground(ColorRGBA.Blue, 1.0f);
        label = textArea.getLabelNode();
        
//        Vector3f center = textArea.getTextCenter();
//        textArea.centerLabelAtPosition(center.negateLocal());
        
        //mll.centerLabelAtTopLeft();
        //mll.centerLabelAtBottomLeft();
        //mll.centerLabelAtTopRight();
        //mll.centerLabelAtBottomRight();
        
        //mll.centerLabelAtTopCenter();
        //mll.centerLabelAtBottomCenter();
        //mll.centerLabelAtCenterRight();
        //mll.centerLabelAtCenterLeft();
        
        textArea.centerLabelAtCenterCenter();
        
        textArea.append("    ");
        textArea.append("    The story continues ....");
        
        textArea.centerLabelAtCenterCenter();
        
        textArea.dumpTextToConsole();
        
        label.move(0f, 0f, -30f);
        rootNode.attachChild(label);
        
        textArea.dumpToTextFile("textDump.txt");
//        textArea.setWidth(200);
//        textArea.setTextSize(10);
//        guiNode.attachChild(label);
    }
    
    private void setupMultiLineLabel_NoInput(){
        textArea = new TextArea(this);
        Node label = textArea.getLabelNode();
        
        textArea.addBackground(ColorRGBA.Blue, 2.0f);
        textArea.add1x1DebugBoxToOrigin(1f, ColorRGBA.Yellow);
        //textArea.centerLabelAtCenterCenter();
        //textArea.centerLabelAtTopLeft();
        textArea.centerLabelAtBottomRight();
        System.out.println("Text height = " + textArea.getTextHeightOfLabel());
        
//        label.setLocalTranslation(0f, 0f, -30f);
//        
//        textArea.setStringArray(labelStrings);
//        label = textArea.getLabelNode();
//        
//        textArea.setWidth(10);
//        label = textArea.getLabelNode();
//        
        textArea.setFont(guiFont);
        label = textArea.getLabelNode();

        textArea.setBackgroundColor(ColorRGBA.Orange);
        
        textArea.setBackgroundMargin(0.5f);
        
        textArea.append("... A new line ...");
        
        label.setLocalTranslation(0f, 0f, -30f);
        
        //System.out.println("bak req: " + textArea.getB);
        rootNode.attachChild(label);
    }
    
    
    
    
    
    
    
    
    int counter = 0;
    boolean mllDestroyed = false;
    
    @Override
    public void simpleUpdate(float tpf) {
//        counter++;
//        
//        if (counter > 5000 && !mllDestroyed) {
//            System.out.println(textArea.toString());
//            rootNode.detachChild(label);
//            textArea.destroyInstance();
//            textArea = null;
//            mllDestroyed = true;
//            
//            System.out.println(textArea.toString());
//            counter = 0;
//        }
//        
//        if (counter > 5000 && mllDestroyed){
//            System.out.println(textArea.toString());    
//        }
    }
    
    
    
    
    /////////////////////////////////////////////////////////////////////////////
    
    private void setupLabel(){
        Node boxNode = new Node();
        
        float qWidth = 5f;
        float qHeight = 6.5f;
        float padding = 0.2f;
        
        Quad q1 = new Quad(qWidth, qHeight);
        Geometry sign = new Geometry("Sign", q1);
       
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Green);
        sign.setMaterial(mat);

        boxNode.setLocalTranslation(-2.5f, -2.5f, -15f);
        boxNode.attachChild(sign);
        
        BitmapFont arialFont = assetManager.loadFont("Interface/Fonts/Arial.fnt");
        
        BitmapText hudText = new BitmapText(arialFont, false);
        hudText.setColor(new ColorRGBA(0.2f, 0.2f, 0.9f, 1f));                 // font color
        hudText.setText("This is a nice sized quad with blue writing.");            // the text
        
        float textX, textY, textW, textH;
        textX = padding;
        textY = qHeight;
        //textY = 0f;
        textW = qWidth - padding;
        //textW = 0.1f;
        //textH = qHeight;       // does not seem to have any effect.
        textH = 0.1f;

        Rectangle textLimits = new Rectangle (textX, textY, textW, textH);   // params are: (x, y, width, height)
        hudText.setBox(textLimits);
        hudText.setSize(1f);
        hudText.setAlpha(1f);
        hudText.setAlignment(BitmapFont.Align.Left);
        hudText.setVerticalAlignment(BitmapFont.VAlign.Top);
        hudText.setLocalTranslation(0f, 0f, 0.1f); // position
        System.out.println("fontSize: " + arialFont.getCharSet().getRenderedSize());
        System.out.println("Line count = " + hudText.getLineCount());
        
        BitmapText clonedText = hudText.clone();
//        clonedText.setText("New text for the cloned object.");
//        boxNode.attachChild(clonedText);
        
//        hudText.setText("Whatever happened to the old text?");
//        textLimits = new Rectangle (textX, textY, textW, textH);   // params are: (x, y, width, height)
//        hudText.setBox(textLimits);
//        hudText.setSize(1f);
//        hudText.setAlpha(1f);
//        hudText.setAlignment(BitmapFont.Align.Left);
//        hudText.setVerticalAlignment(BitmapFont.VAlign.Top);
//        hudText.setLocalTranslation(0f, 0f, 0.1f); // position
        boxNode.attachChild(hudText);
        rootNode.attachChild(boxNode);
    }

}

        //BitmapFont font = new BitmapFont();     
        //sign.setLocalTranslation(-2.50f, -2.50f, -15f);
//        BitmapText hudText = new BitmapText(myFont, false);
//        hudText.setText("You can write any string here");
//        guiNode.attachChild(hudText);
        //hudText.setSize(guiFont.getCharSet().getRenderedSize() / 10);      // font size
        //hudText.setColor(ColorRGBA.Blue);
        //Rectangle textLimits = new Rectangle (0f, 0f, 5f, 0.3f);