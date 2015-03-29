/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityStates;

import entities.BaseEntity;
import org.newdawn.slick.Animation;

/**
 *
 * @author Emmet
 */
public class moveState implements entityState {
    Animation a;
    int i;        
    private final BaseEntity be;
    int stopFrame;
    int xOffset;
    int yOffset;

    public moveState(BaseEntity be, Animation a, int i) {
       this.be = be;
       this.a = a;
       this.i = i; 
       a.setCurrentFrame(be.getFacing() * i);
       stopFrame = (be.getFacing() + 1) * i - 1;
       a.start();
    }

    @Override
    public void render() {
        a.stopAt(stopFrame);
        switch (be.getFacing()) {
            case 0:
                xOffset = (int) ((int)(32/i)  * ((float)i - (float)a.getFrame()%(float)i));
                yOffset = -(int) ((int)(32/i)  * ((float)i - (float)a.getFrame()%(float)i));
                break;
            case 1:
                xOffset = 0;
                yOffset = -(int) ((int)(32/i)  * ((float)i - (float)a.getFrame()%(float)i));
                break;
            case 2:
                xOffset = -(int) ((int)(32/i)  * ((float)i - (float)a.getFrame()%(float)i));
                yOffset = -(int) ((int)(32/i)  * ((float)i - (float)a.getFrame()%(float)i));
                break;
            case 3:
                xOffset = -(int) ((int)(32/i)  * ((float)i - (float)a.getFrame()%(float)i));
                yOffset = 0;
                break;
            case 4:
                xOffset = -(int) ((int)(32/i)  * ((float)i - (float)a.getFrame()%(float)i));
                yOffset = (int) ((int)(32/i)  * ((float)i - (float)a.getFrame()%(float)i));
                break;
            case 5:
                xOffset = 0;
                yOffset = (int) ((int)(32/i)  * ((float)i - (float)a.getFrame()%(float)i));
                break;
            case 6:
                xOffset = (int) ((int)(32/i)  * ((float)i - (float)a.getFrame()%(float)i));
                yOffset = (int) ((int)(32/i)  * ((float)i - (float)a.getFrame()%(float)i));
                break;
            case 7:
                xOffset = (int) ((int)(32/i)  * ((float)i - (float)a.getFrame()%(float)i)); 
                yOffset = 0;
                break;
        }
        a.draw((32 * be.getPosX() - (a.getWidth()/2 - 16)) + xOffset, (32 * be.getPosY() - (a.getHeight()/2 + 32)) + yOffset);
        if (a.isStopped()) {//stopFrame == a.getFrameCount()) {
            be.setIdle();
        }
        
    }
    
    public boolean isIdle() {
        return false;
    }

    @Override
    public void stopAnim() {
        a.stop();
    }
    
}
