/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dgnUtils.move;

import com.jme3.app.Application;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * <p>Moves a spatial gradually (frame by frame) to an absolute world coordinate.
 * 
 * <p>Input Vector3f must be the final resting place in absolute world coordinates.
 * <p>float speed is in m/s.
 *
 * @author David Niicholson
 */
public class Mover3D {
    
    public static final byte MOVE_THRU_SHORTEST = 0;
    public static final byte MOVE_X_THEN_Y_THEN_Z = 1;
    public static final byte MOVE_X_THEN_Z_THEN_Y = 2;
    public static final byte MOVE_Y_THEN_X_THEN_Z = 3;
    public static final byte MOVE_Y_THEN_Z_THEN_X = 4;
    public static final byte MOVE_Z_THEN_X_THEN_Y = 5;
    public static final byte MOVE_Z_THEN_Y_THEN_X = 6;
    
    Application app;
    Spatial spatial2Move;
    Vector3f finalPlaceVec, currentPosVec, distanceToGoVec, incrementalVec, dirVec, speedVec, stepVec;
    float speed2Move, dif, absDelta, stepX, stepY, stepZ;
    byte moveType;
    boolean doMove = false;
    boolean doMoveX = false, doMoveY = false, doMoveZ = false;
    
    
    public Mover3D(Application app1, Spatial spatial2Move1, Vector3f finalPlace1, float speed, byte moveType1){
        if (moveType1 < 0) {
            throw new UnsupportedOperationException("Move type cannot be less than zero.  Check that you have used one of the Mover class final variables supplied in the class.");
        } else if (moveType1 > 6){
            throw new UnsupportedOperationException("Move type cannot be greater than six.  Check that you have used one of the Mover class final variables supplied in the class.");
        }
        
        app = app1;
        spatial2Move = spatial2Move1;
        finalPlaceVec = finalPlace1;
        speed2Move = speed;
        moveType = moveType1;
        doMove = true;
        switch (moveType) {
            case 1:
                doMoveX = true;
                break;
            case 2:
                doMoveX = true;
                break;
            case 3:
                doMoveY = true;
                break;
            case 4:
                doMoveY = true;
                break;
            case 5:
                doMoveZ = true;
                break;
            case 6:
                doMoveZ = true;
                break;                
            default:
//                throw new AssertionError();
        }
        
    }
    
    float tpf;
    
    public void move(float tpf1){
        if (!doMove) {
            return;
        }
        
        tpf = tpf1;
        dif = speed2Move * tpf;
        
        if (currentPosVec == null) {
            currentPosVec = spatial2Move.getLocalTranslation();
        }
        distanceToGoVec = finalPlaceVec.subtract(currentPosVec);   
        
        if (distanceToGoVec.x < 0) {
            stepX = -1f * dif;
        } else {
            stepX = 1f * dif;
        }
        
        if (distanceToGoVec.y < 0) {
            stepY = -1f * dif;
        } else {
            stepY = 1f * dif;
        }
        
        if (distanceToGoVec.z < 0) {
            stepZ = -1f * dif;
        } else {
            stepZ = 1f * dif;
        }
        
        dif = speed2Move * tpf;

        
        switch (moveType) {
            case 0:
                moveThruShortest();
                break;
            case 1:
                move_X_Y_Z();
                break;
            case 2:
                move_X_Z_Y();
                break;            
            case 3:
                move_Y_X_Z();
                break;
            case 4:
                move_Y_Z_X();
                break;
            case 5:
                move_Z_X_Y();
                break;
            case 6:
                move_Z_Y_X();
                break;
            default:
                throw new AssertionError();
        }
        
    }

    // Moves in absolute world coordinates / not offset coordinates.
    private void moveThruShortest() {
        //System.out.println("Mover3D called ...");
        
        // get world coordinates of spatial
        if (currentPosVec == null) {
            //currentPosVec = spatial2Move.getWorldTranslation();
            currentPosVec = spatial2Move.getLocalTranslation();
        }
        
        // get vector to destination.
        distanceToGoVec = finalPlaceVec.subtract(currentPosVec);
        
        // if arrived at vector pos : return
        if(distanceToGoVec.length() < 0.1f){
            doMove = false;
        }
        
        // multiply
        dirVec = distanceToGoVec.normalize();
        speedVec = dirVec.mult(speed2Move);
        stepVec = speedVec.mult(tpf);
        
        
        //currentPosVec = currentPosVec.add(stepVec);
        currentPosVec.addLocal(stepVec);
        spatial2Move.setLocalTranslation(currentPosVec);
    }
    


    private void move_X_Y_Z() {
//        dif = speed2Move * tpf;
//        
//        if (currentPosVec == null) {
//            currentPosVec = spatial2Move.getLocalTranslation();
//        }
//        distanceToGoVec = finalPlaceVec.subtract(currentPosVec);   
//        
//        if (distanceToGoVec.x < 0) {
//            stepX = -1f * dif;
//        } else {
//            stepX = 1f * dif;
//        }
//        
//        if (distanceToGoVec.y < 0) {
//            stepY = -1f * dif;
//        } else {
//            stepY = 1f * dif;
//        }
//        
//        if (distanceToGoVec.z < 0) {
//            stepZ = -1f * dif;
//        } else {
//            stepZ = 1f * dif;
//        }
//        
//        dif = speed2Move * tpf;
//        
        if(doMoveX){
            absDelta = FastMath.abs(distanceToGoVec.x);
            if(absDelta > 0.1f){
                currentPosVec.x += stepX;
                spatial2Move.setLocalTranslation(currentPosVec);
                return;
            } else {
                doMoveX = false;
                doMoveY = true;
                System.out.println("X movement done.");
            }
        }
        
        if(doMoveY) {
            absDelta = FastMath.abs(distanceToGoVec.y);
            if(absDelta > 0.1f){            
                currentPosVec.y += stepY;
                spatial2Move.setLocalTranslation(currentPosVec);
                return;
            } else {
                doMoveY = false;
                doMoveZ = true;
                System.out.println("Y movement done.");
            }
        }
        
        if(doMoveZ){
            absDelta = FastMath.abs(distanceToGoVec.z);
            if(absDelta > 0.1f){            
                currentPosVec.z += stepZ;
                spatial2Move.setLocalTranslation(currentPosVec);
                return;
            } else {
                doMoveZ = false;
                doMove = false;   // movement is complete.
                System.out.println("Z movement done.");
            }
        }
    }

    private void move_X_Z_Y() {
//        dif = speed2Move * tpf;
//        
//        if (currentPosVec == null) {
//            currentPosVec = spatial2Move.getLocalTranslation();
//        }
//        distanceToGoVec = finalPlaceVec.subtract(currentPosVec);   
//        
//        if (distanceToGoVec.x < 0) {
//            stepX = -1f * dif;
//        } else {
//            stepX = 1f * dif;
//        }
//        
//        if (distanceToGoVec.y < 0) {
//            stepY = -1f * dif;
//        } else {
//            stepY = 1f * dif;
//        }
//        
//        if (distanceToGoVec.z < 0) {
//            stepZ = -1f * dif;
//        } else {
//            stepZ = 1f * dif;
//        }
//        
//        dif = speed2Move * tpf;
//        
        if(doMoveX){
            absDelta = FastMath.abs(distanceToGoVec.x);
            if(absDelta > 0.1f){
                currentPosVec.x += stepX;
                spatial2Move.setLocalTranslation(currentPosVec);
                return;
            } else {
                doMoveX = false;
                doMoveZ = true;
                System.out.println("X movement done.");
            }
        }
         
        if(doMoveZ){
            absDelta = FastMath.abs(distanceToGoVec.z);
            if(absDelta > 0.1f){            
                currentPosVec.z += stepZ;
                spatial2Move.setLocalTranslation(currentPosVec);
                return;
            } else {
                doMoveZ = false;
                doMoveY = true;   // movement is complete.
                System.out.println("Z movement done.");
            }
        }
        
        if(doMoveY) {
            absDelta = FastMath.abs(distanceToGoVec.y);
            if(absDelta > 0.1f){            
                currentPosVec.y += stepY;
                spatial2Move.setLocalTranslation(currentPosVec);
                return;
            } else {
                doMoveY = false;
                doMove = false;
                System.out.println("Y movement done.");
            }
        }
        
       
    }

    private void move_Y_X_Z() {
//        dif = speed2Move * tpf;
//        
//        if (currentPosVec == null) {
//            currentPosVec = spatial2Move.getLocalTranslation();
//        }
//        distanceToGoVec = finalPlaceVec.subtract(currentPosVec);   
//        
//        if (distanceToGoVec.x < 0) {
//            stepX = -1f * dif;
//        } else {
//            stepX = 1f * dif;
//        }
//        
//        if (distanceToGoVec.y < 0) {
//            stepY = -1f * dif;
//        } else {
//            stepY = 1f * dif;
//        }
//        
//        if (distanceToGoVec.z < 0) {
//            stepZ = -1f * dif;
//        } else {
//            stepZ = 1f * dif;
//        }
//        
//        dif = speed2Move * tpf;
//        
        if(doMoveY) {
            absDelta = FastMath.abs(distanceToGoVec.y);
            if(absDelta > 0.1f){            
                currentPosVec.y += stepY;
                spatial2Move.setLocalTranslation(currentPosVec);
                return;
            } else {
                doMoveY = false;
                doMoveX = true;
                System.out.println("Y movement done.");
            }
        }
        
        if(doMoveX){
            absDelta = FastMath.abs(distanceToGoVec.x);
            if(absDelta > 0.1f){
                currentPosVec.x += stepX;
                spatial2Move.setLocalTranslation(currentPosVec);
                return;
            } else {
                doMoveX = false;
                doMoveZ = true;
                System.out.println("X movement done.");
            }
        }
         
        if(doMoveZ){
            absDelta = FastMath.abs(distanceToGoVec.z);
            if(absDelta > 0.1f){            
                currentPosVec.z += stepZ;
                spatial2Move.setLocalTranslation(currentPosVec);
                return;
            } else {
                doMoveZ = false;
                doMove = false;   // movement is complete.
                System.out.println("Z movement done.");
            }
        }
        
        
    }

    private void move_Y_Z_X() {
//        dif = speed2Move * tpf;
//        
//        if (currentPosVec == null) {
//            currentPosVec = spatial2Move.getLocalTranslation();
//        }
//        distanceToGoVec = finalPlaceVec.subtract(currentPosVec);   
//        
//        if (distanceToGoVec.x < 0) {
//            stepX = -1f * dif;
//        } else {
//            stepX = 1f * dif;
//        }
//        
//        if (distanceToGoVec.y < 0) {
//            stepY = -1f * dif;
//        } else {
//            stepY = 1f * dif;
//        }
//        
//        if (distanceToGoVec.z < 0) {
//            stepZ = -1f * dif;
//        } else {
//            stepZ = 1f * dif;
//        }
//        
//        dif = speed2Move * tpf;
//        
        if(doMoveY) {
            absDelta = FastMath.abs(distanceToGoVec.y);
            if(absDelta > 0.1f){            
                currentPosVec.y += stepY;
                spatial2Move.setLocalTranslation(currentPosVec);
                return;
            } else {
                doMoveY = false;
                doMoveZ = true;
                System.out.println("Y movement done.");
            }
        }
        
        if(doMoveZ){
            absDelta = FastMath.abs(distanceToGoVec.z);
            if(absDelta > 0.1f){            
                currentPosVec.z += stepZ;
                spatial2Move.setLocalTranslation(currentPosVec);
                return;
            } else {
                doMoveZ = false;
                doMoveX = true;
                System.out.println("Z movement done.");
            }
        }
        
        
        if(doMoveX){
            absDelta = FastMath.abs(distanceToGoVec.x);
            if(absDelta > 0.1f){
                currentPosVec.x += stepX;
                spatial2Move.setLocalTranslation(currentPosVec);
                return;
            } else {
                doMoveX = false;
                doMove = false;
                System.out.println("X movement done.");
            }
        }
         
    }

    private void move_Z_X_Y() {
//        dif = speed2Move * tpf;
//        
//        if (currentPosVec == null) {
//            currentPosVec = spatial2Move.getLocalTranslation();
//        }
//        distanceToGoVec = finalPlaceVec.subtract(currentPosVec);   
//        
//        if (distanceToGoVec.x < 0) {
//            stepX = -1f * dif;
//        } else {
//            stepX = 1f * dif;
//        }
//        
//        if (distanceToGoVec.y < 0) {
//            stepY = -1f * dif;
//        } else {
//            stepY = 1f * dif;
//        }
//        
//        if (distanceToGoVec.z < 0) {
//            stepZ = -1f * dif;
//        } else {
//            stepZ = 1f * dif;
//        }
//        
//        dif = speed2Move * tpf;
//        
        if(doMoveZ){
            absDelta = FastMath.abs(distanceToGoVec.z);
            if(absDelta > 0.1f){            
                currentPosVec.z += stepZ;
                spatial2Move.setLocalTranslation(currentPosVec);
                return;
            } else {
                doMoveZ = false;
                doMoveX = true;
                System.out.println("Z movement done.");
            }
        }
        
        

        if(doMoveX){
            absDelta = FastMath.abs(distanceToGoVec.x);
            if(absDelta > 0.1f){
                currentPosVec.x += stepX;
                spatial2Move.setLocalTranslation(currentPosVec);
                return;
            } else {
                doMoveX = false;
                doMoveY = true;
                System.out.println("X movement done.");
            }
        }

        
        if(doMoveY) {
            absDelta = FastMath.abs(distanceToGoVec.y);
            if(absDelta > 0.1f){            
                currentPosVec.y += stepY;
                spatial2Move.setLocalTranslation(currentPosVec);
                return;
            } else {
                doMoveY = false;
                doMove = false;
                System.out.println("Y movement done.");
            }
        }
        
         
    }

    private void move_Z_Y_X() {
        
        if(doMoveZ){
            absDelta = FastMath.abs(distanceToGoVec.z);
            if(absDelta > 0.1f){            
                currentPosVec.z += stepZ;
                spatial2Move.setLocalTranslation(currentPosVec);
                return;
            } else {
                doMoveZ = false;
                doMoveY = true;
                System.out.println("Z movement done.");
            }
        }
        
        

        if(doMoveY) {
            absDelta = FastMath.abs(distanceToGoVec.y);
            if(absDelta > 0.1f){            
                currentPosVec.y += stepY;
                spatial2Move.setLocalTranslation(currentPosVec);
                return;
            } else {
                doMoveY = false;
                doMoveX = true;
                System.out.println("Y movement done.");
            }
        }        

        if(doMoveX){
            absDelta = FastMath.abs(distanceToGoVec.x);
            if(absDelta > 0.1f){
                currentPosVec.x += stepX;
                spatial2Move.setLocalTranslation(currentPosVec);
                return;
            } else {
                doMoveX = false;
                doMove = false;
                System.out.println("X movement done.");
            }
        }

        
        
    }
    
}
