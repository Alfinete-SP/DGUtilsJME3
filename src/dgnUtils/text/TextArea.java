/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dgnUtils.text;

import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.Rectangle;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * <H1>A Text Area class for the jME3 rootNode. </H1>
 * 
 * This is for creating a multi line label for putting onto the root node.  
 * It has not been created for the guiNode, nor has is been tested for such
 * a place. <p>
 * 
 * One suggested usage is to form a jME3 Text Area object that can be put in front
 * of the camera and scrolled with appropriate key presses or mouse actions.  Keypresses
 * and mouse actions are not included.  <p>
 * 
 * The TextArea object might be interesting
 * for gathering runtime information from the application, which can then be saved
 * to disk as a TXT file or dumped to the console.<p>
 * 
 * The bitmap text itself is created on the .getLabelNode() call.  This is avoid a lot of
 * processing when the TextArea instance is created.  <p>
 * 
 * You can call .getLabelNode() without adding the returned node to the rootNode.
 * If the label has already been added, the updated label information should appear
 * automatically.<br>  
 * 
 * <font face = "courier new" size = 2>
 * i.e. ta = textArea.getLabelNode(); // where the node "ta" has been previously added to the rootNode.
 * </font>
 * <p>
 * 
 * 
 * <H1>Features:</H1>
 * <ol>
 * <li>The text can be displayed with or without a background quad.  <p>
 * 
 * <li>It can also be displayed with a debug box, having the color and size of your 
 * choosing, set at the origin of the label node.  This is handy for the correct
 * placement of your label in your scene graph. <p>
 * 
 * <li>The origin of the TextArea can be changed by setting a Vector3f offset.  
 * It can also be set at pre-defined places such as TOP LEFT / BOTTOM RIGHT / 
 * CENTER CENTER ... etc. <p>
 * 
 * <li>It has a destroy() method to destroy all class variable and make them available
 * for garbage collection.  The label object should then also be nulled in the calling
 * code. <p>
 * 
 * <li>The Append function allows you to add more strings to the textArea object.  
 * The existing text is automatically updated with the new line.  The background
 * quad is also resized to accomodate the new size of the text area. <p>
 * 
 * <li>Save text buffer to disk as a text file.  Use the .dumpToTextFile(String s) method, 
 * where s is the file name.  <p>
 * 
 * <li>Dump text to the console using the .dumpTextToConsole() method.  <p>
 * 
 * <li>Getters and Setters for all major fields. <p>
 * </ol>
 * 
 * 
 * <H2>TO DO:</H2>
 * <ol> <li>Add a texture to the background quad.  </ol>
 * 
 *
 * 
 * @author David [Alfinete] Niicholson
 */
public class TextArea {

    private Node internalNode, labelNode;
    private AssetManager assetManager;
    
    private ColorRGBA textColor ;
    private float textSize, textAlpha;
    private BitmapFont.Align hAlignment;
    private BitmapFont.VAlign vAlignment;
    private Rectangle textLimits;    
    
    private String[] stringArray;
    private BitmapText[] bmText;
    private BitmapFont font;
    private float width, height; //, padding;
    private boolean backgroundAttached = false;
    
    private boolean backgroundRequired;
    private ColorRGBA backgroundColor;
    private float backgroundMargin;

    private Geometry sign;    
    private Geometry debugBox;
    
    private float yPositionOfNextRow = 0f;
    private float backgroundHeight;
    private float backgroundWidth;

    private int stringArrayIdx;
    
    private boolean labelHasBeenCreated = false;
    private boolean debugBoxCreated = false;

    private Vector3f labelOffset = new Vector3f(0f, 0f, 0f);
    private Vector3f debugBoxOffset = new Vector3f(0f, 0f, 0f);
    

    /**
     * A simple constructor that will exhibit three lines of text.  This is to help setup 
     * the label in the scene graph, in the right position or for adjusting the origin
     * and so on.  This is not for production code.  Obviously.  However, .setTextArray()
     * can be called to set the text you want initially, then use .append() to add further
     * lines.
     * 
     * @param app -- an instance of Application or SimpleApplication.
     */
    public TextArea(Application app){
        labelNode = new Node();
        internalNode = new Node();
        assetManager = app.getAssetManager();
        //stringArray = new String[]{"Hello World ..."};
        stringArray = new String[]{
            "Please create a String[] containing the text to exhibit ...", 
            "Use the method .setTextArray to put your text into the object ...", 
            "... then call the method .getLabelNode() to produce the text and return the node "
                + "to be added to the scene graph (i.e. rootNode.)"};
        
        width = 20f;
        height = 0f;
        //padding = 0f;
        textColor = ColorRGBA.White;
        textSize = 1f;
        textAlpha = 1f;
        hAlignment = BitmapFont.Align.Left;
        vAlignment = BitmapFont.VAlign.Top;
        setupTextLimits();    // configures the text bounding box for word wrap.
    }
    
    /**
     * This constructor takes a string array for the text to exhibit, 
     * and a float to establish the width
     * of the text.  The most important parameter is the width, since the length
     * is calculated in the class from the number of lines in the output and the
     * width of those lines.
     * 
     * @param app - an instance of Application or SimpleApplication.
     * @param stringArray1 - lines of text to view in the label.
     * @param width1  - width of the label.
     */
    public TextArea(Application app, String[] stringArray1, float width1) {
        labelNode = new Node();
        internalNode = new Node();
        assetManager = app.getAssetManager();
        stringArray = stringArray1;
        width = width1;
        height = 0f;
        //padding = padding1;
        
        textColor = ColorRGBA.White;
        textSize = 1f;
        textAlpha = 1f;
        hAlignment = BitmapFont.Align.Left;
        vAlignment = BitmapFont.VAlign.Top;
        setupTextLimits();    // configures the text bounding box for word wrap.
    }
    

    
    private void createBackground(){
        if (backgroundAttached) {
            internalNode.detachChild(sign);
            backgroundAttached = false;
        }
        
        backgroundHeight = yPositionOfNextRow + backgroundMargin + backgroundMargin ;
        backgroundWidth  = width + backgroundMargin + backgroundMargin;
        
        Quad q1 = new Quad(backgroundWidth, backgroundHeight);
        
        sign = new Geometry("Sign", q1);
       
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", backgroundColor);
        
        float yOffset = height - yPositionOfNextRow - backgroundMargin;
        sign.setLocalTranslation(-backgroundMargin, yOffset, 0f);
        
        sign.setMaterial(mat);

        if (!backgroundAttached) {
            internalNode.attachChild(sign);
            backgroundAttached = true;
        }
    }
    
    /**
     * This method signals to the class that a background quad is required behind
     * the text exhibited.  The quad is created and place on the node when the 
     * .updateLabelNode() method is called. <p>
     * 
     * The size of the quad is calculated internally.  The input parameters are
     * for color of the quad and for the size of the margin around the text.
     * The width of the quad will be [textWidth + margin + margin].  <p>
     * 
     * Small discrepancies have been seen in the symetry of the quad placing.  This
     * may be improved in future updates. <p>
     * 
     * 
     * @param color1
     * @param margin1 
     */
    public void addBackground(ColorRGBA color1, float margin1){
        backgroundRequired = true;
        backgroundColor = color1;
        backgroundMargin = margin1;
        /* If the call to .getLabelNode() is made before the call to addBackground(),
         * then the boolean "backgroundRequired" will be false and the background will not
         * get created.  The boolean "labelHasBeenCreated" is set when the label is created and
         * thus sets the background to render even if it is called later.
         */
        if (labelHasBeenCreated) {
            createBackground();
        }
        
    }
    
    
    
    /**
     * If a background quad has been previously added behind the text, this method
     * will remove that quad.
     */
    public void removeBackground(){
        backgroundRequired = false;
        if(backgroundAttached){
            internalNode.detachChild(internalNode);
        }
        backgroundAttached = false;
    }
    
    private void printLabelNodeAdvice(){
        System.out.println("");
        System.out.println("     In the TextArea class, the .getLabelNode() method creates the text.");
        System.out.println("     >> better to call node-modifying methods after node has been created.");
        System.out.println("        i.e call .getLabelNode() before other methods.  It may avoid NPEs.");
        System.out.println("     >> Default center of the label is TOP LEFT.");
        System.out.println("     >> This message will be removed once the code is at release stage.");
        System.out.println("");
    }
    
    private boolean printWarnings = true;
    
    /**
     * If set to false will turn off console warnings associated with the class.
     * 
     * @param set [false = warnings off]
     */
    public void setWarning(boolean set){
        printWarnings = set;
    }
    
    /**
     * <H6>This is the main method where the label is created and added to the node.
     * Calling this method will cause the label to update its text, if it hasn't done
     * so already.</H6>
     * 
     * @return Node labelNode
     */
    public Node getLabelNode(){
        if (printWarnings) {
            printLabelNodeAdvice();
        }
        //createTestLabel();
        createLabel();
        labelHasBeenCreated = true;
        
        if (backgroundRequired) {
            createBackground();
        }
        
        
        labelNode.attachChild(internalNode);
        return labelNode;
    }

    /** configures the text bounding box for word wrap.  */
    private void setupTextLimits(){
        // parameters are: x, y, width, heigh.
        textLimits = new Rectangle(0f, height - yPositionOfNextRow, width, height); 
        //textLimits = new Rectangle(padding, height - yPositionOfNextRow, width - padding, height); 
    }
    

    /**
     * As of current jME3 version 3.0.10 -- the BitmapText object cannot have its
     * text changed once it has been configured.  This means that to create a new
     * text output, you must generate a new BitmapText object all together.  Probably
     * a good idea to see the old one to null for GC.
     */
    private void createLabel() {
        font = assetManager.loadFont("Interface/Fonts/Arial.fnt");
        
        int arrayLength = stringArray.length;
        
        bmText = new BitmapText[arrayLength];
        
        for (int i = 0; i < arrayLength; i++) {
            
            stringArrayIdx = i;
            
            bmText[i] = new BitmapText(font, false);
            setupBitmapText(bmText[i], stringArray[i]);
            
            yPositionOfNextRow += bmText[i].getLineHeight() * bmText[i].getLineCount();
            
            textLineHeight = bmText[i].getLineHeight();
            
            setupTextLimits();
            internalNode.attachChild(bmText[i]);
        }
    }
    
    private float textLineHeight;
    
    /**
     * This method is looped through to create each line of the label.  
     * 
     * @param bitmapText
     * @param text 
     */
    private void setupBitmapText(BitmapText bitmapText, String text) {
        bitmapText.setText(text);
        bitmapText.setColor(textColor);                                        // font color
        
        setupTextLimits();    // configures the text bounding box for word wrap.

        bitmapText.setBox(textLimits);
        bitmapText.setSize(textSize);
        bitmapText.setAlpha(textAlpha);
        bitmapText.setAlignment(hAlignment);
        bitmapText.setVerticalAlignment(vAlignment);
        bitmapText.setLocalTranslation(0f, 0f, 0.1f); // position in front of the background quad.
    }
    
    
        
    /**
     * A method to test label creation using the BitmapText class.
     */
    private void createTestLabel(){
        BitmapFont arialFont = assetManager.loadFont("Interface/Fonts/Arial.fnt");
        
        BitmapText hudText = new BitmapText(arialFont, false);
        hudText.setText("This is a blue quad with white writing.");           // the text
        
        hudText.setColor(textColor);                                          // font color
        setupTextLimits();
        hudText.setBox(textLimits);
        hudText.setSize(textSize);
        hudText.setAlpha(textAlpha);
        hudText.setAlignment(BitmapFont.Align.Center);
        //hudText.setAlignment(BitmapFont.Align.Left);
        hudText.setVerticalAlignment(BitmapFont.VAlign.Top);
        hudText.setLocalTranslation(0f, 0f, 0.1f); // position
        System.out.println("fontSize: " + arialFont.getCharSet().getRenderedSize());
        System.out.println("Line count = " + hudText.getLineCount());
        
        yPositionOfNextRow += hudText.getLineHeight() * hudText.getLineCount();
        
        internalNode.attachChild(hudText);
        
    }
    
    /**
     * This method adds a colored box at the origin of the labelNode.  The origin
     * is at the CENTER of the box, not at one of its corners.  This can 
     * be useful for positioning the label properly in the scene graph.
     * 
     * @param size - using a value of 1.0f will give you a box where all sides measure 1 unit.
     * @param color - the color of the box.  i.e. ColorRGBA.Yellow
     */
    public void add1x1DebugBoxToOrigin(float size, ColorRGBA color){
        float eachDir = size / 2;
        Box b = new Box(eachDir, eachDir, eachDir); // extends 0.5 units in EACH direction.
        debugBox = new Geometry("box", b);
        
        Material m = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        m.setColor("Color", color);
        
        debugBox.setMaterial(m);
        internalNode.attachChild(debugBox);
        debugBox.setLocalTranslation(debugBoxOffset);
        
        debugBoxCreated = true;
    }
    
    
    /**
     * This method removes the debug colored box from the origin of the label.
     */
    public void remove1x1DebugBoxFromOrigin(){
        if (debugBoxCreated) {
            internalNode.detachChild(debugBox);
        }
        debugBox = null;
        debugBoxCreated = false;
    }
    
    /**
     * Destroys all of the internal objects in this class, setting them all to null.
     * This is to facilitate any GC operations on this class.
     */
    public void destroyInstance(){
        internalNode.detachAllChildren();
        assetManager = null;
        textColor = null;
        hAlignment = null;
        vAlignment = null;
        textLimits = null;
        stringArray = null;
        bmText = null;
        font = null;
        backgroundColor = null;
        debugBox = null;
        
        internalNode = null;
        labelNode = null;
    }
    
    

    /**
     * Centers the label at a certain position given by the offset vector.
     * It also offsets the debug box so that it's position represents the origin.
     * 
     * @param offset1 
     */
    public void centerLabelAtPosition(Vector3f offset1){
        internalNode.setLocalTranslation(offset1);
        debugBoxOffset = offset1.negate();
        
        if (debugBoxCreated) {
            debugBox.setLocalTranslation(offset1.negateLocal());
        }
    }
    
    /**
     * Returns the width of the text in world units if on the rootNode, or
     * in pixels if on the guiNode.  However, this class was not originally
     * written for use on the guiNode.
     * 
     * @return width as a float
     */
    public float getTextWidth(){
        return width;
    }
    
    /**
     * Returns the height of the current text, including all lines.  This value
     * is calculated internally as lines are created internally from the 
     * string array or added via append().
     * 
     * There is no setter for this value.
     * 
     * @return height of text as a float
     */
    public float getTextHeight(){
        return yPositionOfNextRow;
    }
    
    /**
     * Returns a Vector3f of the center position of the text.  The Z value will
     * always be zero, since the label has pracitally no depth.  So the valid 
     * values are only X and Y.
     * 
     * Label depth is always 0.1 world units.
     * 
     * @return Vector3f where only X and Y are valid units, Z is always zero.
     */
    public Vector3f getTextCenter(){
        return new Vector3f(width / 2 , -yPositionOfNextRow / 2, 0f);
    }
    
    /**
     * Centers the label at the top left corner.
     */
    public void centerLabelAtTopLeft(){
        labelOffset = new Vector3f(0f, 0f, 0f);
        centerLabelAtPosition(labelOffset);
    }
    
    /**
     * Centers the label at the top right corner.
     */
    public void centerLabelAtTopRight(){
        labelOffset = new Vector3f(-width, 0f, 0f);
        centerLabelAtPosition(labelOffset);
    }
    
    /**
     * Centers the label at the bottom left corner.
     */
    public void centerLabelAtBottomLeft(){
        labelOffset = new Vector3f(0f, yPositionOfNextRow, 0f);
        centerLabelAtPosition(labelOffset);
    }
    
    /**
     * Centers the label at the bottom right corner.
     */    
    public void centerLabelAtBottomRight(){
        labelOffset = new Vector3f(-width, yPositionOfNextRow, 0f);
        centerLabelAtPosition(labelOffset);
    }

    /**
     * Centers the label at the center of the top edge.
     */    
    public void centerLabelAtTopCenter() {
        labelOffset = new Vector3f(-width / 2 , 0f , 0f);
        centerLabelAtPosition(labelOffset);
    }
    
    /**
     * Centers the label at the center of the bottom edge.
     */
    public void centerLabelAtBottomCenter() {
        labelOffset = new Vector3f(-width / 2 , yPositionOfNextRow , 0f);
        centerLabelAtPosition(labelOffset);
    }
    
    /**
     * Centers the label at the center of the right edge.
     */
    public void centerLabelAtCenterRight() {
        labelOffset = new Vector3f(-width , yPositionOfNextRow / 2 , 0f);
        centerLabelAtPosition(labelOffset);        
    }
    
    /**
     * Centers the label at the center of the left edge.
     */
    public void centerLabelAtCenterLeft() {
        labelOffset = new Vector3f(0f , yPositionOfNextRow / 2 , 0f);
        centerLabelAtPosition(labelOffset);    
    }
    
    /**
     * Centers the label at dead center.
     */
    public void centerLabelAtCenterCenter() {
        labelOffset = new Vector3f(-width / 2 , yPositionOfNextRow / 2 , 0f);
        centerLabelAtPosition(labelOffset);        
    }

    /**
     * Returns the ColorRGBA used for the background color.
     * 
     * @return color
     */
    public ColorRGBA getTextColor() {
        return textColor;
    }

    /**
     * Sets the color of the background.  It is a ColorRGBA object.
     * 
     * @param textColor 
     */
    public void setTextColor(ColorRGBA textColor) {
        this.textColor = textColor;
    }

    /**
     * Returns the size of the text.  The default value is 1.0f.  
     * 
     * If the value returned is greater than one, 
     * then the text has been magnified.  If less than
     * one, then it has had it's size attenuated.
     * 
     * @return textSize float
     */
    public float getTextSize() {
        return textSize;
    }

    /**
     * The default value is 1.0f.  Values larger
     * than 1 will produce a bigger text.  Smaller values, a smaller text.  Values
     * less than zero will probably throw an exception.
     * 
     * @param textSize float
     */
    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    /**
     * The transperancy used to render the text.  1 is fully opaque.  Zero is
     * fully transparent.
     * 
     * @return float textAlpha
     */
    public float getTextAlpha() {
        return textAlpha;
    }

    /**
     * Sets the transparency of the text.  1 is fully opaque.  Zero is
     * fully transparent.
     * 
     * @param textAlpha float
     */
    public void setTextAlpha(float textAlpha) {
        this.textAlpha = textAlpha;
    }

    /**
     * Returns the horizontal alignment of the text.  Values can be:
     * left, right or center.
     * 
     * @return hAlignment
     */
    public BitmapFont.Align getHAlignment() {
        return hAlignment;
    }

    /**
     * Specifies the horizontal alignment of the text.  Values can be:
     * left, right or center.
     * 
     * @param hAlignment 
     */
    public void setHAlignment(BitmapFont.Align hAlignment) {
        this.hAlignment = hAlignment;
    }

    /**
     * Returns the string array of all the text displayed by the object.
     * 
     * @return String[] stringArray.
     */
    public String[] getStringArray() {
        return stringArray;
    }

    /**
     * Sets the text for this object to display.  Each string in the array
     * is represented on its own line.  Each line is wrapped according to 
     * necessity governed by the stipulated text width.
     * 
     * <H6>To get the new text label:</H6>
     * The method <b>.getLabelNode()</b> needs to be called
     * again because all internal nodes will have been reset. 
     * Tbis is done to get rid of the old label data from memory
     * so that the new one can be constructed with the new text.
     * 
     * @param stringArray 
     */
    public void setStringArray(String[] stringArray) {
        this.stringArray = stringArray;
        labelHasBeenCreated = false;
        labelNode = new Node();
        internalNode = new Node();
        yPositionOfNextRow = 0f;
        setupTextLimits();
    }

    /**
     * Retrieves the bitmap font object used in this label.
     * 
     * @return font
     */
    public BitmapFont getFont() {
        return font;
    }

    /**
     * Rebuilds the label using a new font.
     * 
     * <H6>To get the new text label:</H6>
     * The method <b>.getLabelNode()</b> needs to be called
     * again because all internal nodes are reset, having their constructors
     * called again.
     * 
     * @param font 
     */
    public void setFont(BitmapFont font) {
        this.font = font;
        labelHasBeenCreated = false;
        if (backgroundAttached) {
            createBackground();
        }
        labelNode = new Node();
        internalNode = new Node();
        yPositionOfNextRow = 0f;
        setupTextLimits();
    }

    /**
     * Sets a new width to the text.
     * 
     * <H6>To get the new text label:</H6>
     * The method <b>.getLabelNode()</b> needs to be called
     * again because all internal nodes are reset, having their constructors
     * called again.
     * 
     * @param width 
     */
    public void setWidth(float width) {
        this.width = width;
        labelHasBeenCreated = false;
        labelNode = new Node();
        internalNode = new Node();
        yPositionOfNextRow = 0f;
        setupTextLimits();
    }

    /**
     * Returns the color used in the background quad.
     * 
     * @return ColorRGBA color
     */
    public ColorRGBA getBackgroundColor() {
        return backgroundColor;
    }

    /** 
     * Sets a new color to the background quad.  
     * 
     * @param backgroundColor 
     */
    public void setBackgroundColor(ColorRGBA backgroundColor) {
        this.backgroundColor = backgroundColor;
        createBackground();
    }

    /**
     * Returns the size of the margin around the text.
     * 
     * @return float margin
     */
    public float getBackgroundMargin() {
        return backgroundMargin;
    }

    /**
     * Sets a new margin size around the text.
     * 
     * @param backgroundMargin 
     */
    public void setBackgroundMargin(float backgroundMargin) {
        this.backgroundMargin = backgroundMargin;
        createBackground();
    }

    /**
     * Returns the calculated height of the text.  Does not take the background 
     * into account.
     * 
     * @return float height
     */
    public float getTextHeightOfLabel() {
        return yPositionOfNextRow;
    }
    
    /**
     * Returns the width of the text.
     * 
     * @return float width
     */
    public float getBackgroundWidth(){
        return backgroundWidth;
    }

    /**
     * Returns the height of the background quad.
     * 
     * @return float height
     */
    public float getBackgroundHeight() {
        return backgroundHeight;
    }

    /**
     * Returns the height of one line of text.  It will be the height of the
     * last line of text added to the label, whether it be via String array
     * or via the append() method.
     * 
     * @return float height
     */
    public float getTextHeightOfOneLine() {
        return textLineHeight;
    }

    /**
     * Appends a new string to the last line of the label.  The update is automatic.
     * No other methods need to be called.
     * 
     * @param str 
     */
    public void append(String str){
        // INCREASE THE ARRAY SIZE
        String[] tempArray;
        if (stringArrayIdx == stringArray.length - 1) { // i.e. at last array position already.
            //INCREASE BY TEN AND COPY CONTENTS
            tempArray = new String[stringArray.length + 10];
            for (int i = 0; i < stringArray.length; i++) {
                tempArray[i] = stringArray[i];
            }
            stringArray = null;
            stringArray = tempArray;
            tempArray = null;
        }
            
        // ADD NEW STRING TO ARRAY
        stringArray[stringArrayIdx] = str;
        stringArrayIdx++;
        
        // NEW BITMAP TEXT
        BitmapText bmt = new BitmapText(font, false);
        setupBitmapText(bmt, str);

        yPositionOfNextRow += bmt.getLineHeight() * bmt.getLineCount();

        textLineHeight = bmt.getLineHeight();

        setupTextLimits();
        internalNode.attachChild(bmt);        
        
        createBackground();
    }

    /**
     * Dumps the text contained in the text area object to the console.
     */
    public void dumpTextToConsole() {
        // PRINT OUT NEW STRING ARRAY
        for (int i = 0; i < stringArray.length; i++) {
            if (stringArray[i] == null) {
                continue;
            }
            System.out.println(stringArray[i]);
        }
    }
    
    /**
     * Dumps the text contained in the text area object to a file.  The file name
     * can be specified in the parameter. <p>
     * 
     * In the case the parameter is an empty string, or is null, the output file
     * will be called textDump.txt <p>
     * 
     * The file will be written to the project's root diretory. i.e. the
     * directory that the project is in. <p>
     * 
     * @param fileName 
     */
    public void dumpToTextFile(String fileName){
        if (fileName.equals("")){
            fileName = "textDump.txt";
        }
        if (fileName == null) {
            fileName = "textDump.txt";
        }
        PrintWriter out = null;
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
            for (int i = 0; i < stringArray.length; i++) {
                if(stringArray[i] == null){
                    continue;
                }
                out.write(stringArray[i]);
                out.write("\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(TextArea.class.getName()).log(Level.SEVERE, "Error writing to file: " + fileName, ex);
        } finally {
            if (out != null) { out.close(); }
        }
    }
    
}
