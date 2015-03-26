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
public class attackState implements entityState {
    
    Animation a;
    int i;        
    private final BaseEntity be;
    int stopFrame;

    public attackState(BaseEntity be, Animation a, int i) {
        this.be = be;
        this.a = a;
        this.i = i;
        a.setCurrentFrame(be.getFacing() * i);
        stopFrame = (be.getFacing() + 1) * i - 1;
    }

    @Override
    public void render() {
        a.stopAt(stopFrame);
        a.draw(32 * be.getPosX() - (70 - 16), 32 * be.getPosY() - (70 - 8));
        if (stopFrame == a.getFrameCount()) {
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
