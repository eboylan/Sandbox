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
public class IdleState implements EntityState {
    
    Animation a;
    int i;        
    private final BaseEntity be;
    private int stopFrame;

    public IdleState(BaseEntity be, Animation a, int i) {
        this.be = be;
        this.a = a;
        this.i = i;
        
    }

    @Override
    public void render() {
        a.stopAt(stopFrame);
        a.draw(32 * be.getPosX() - (70 - 16), 32 * be.getPosY() - (70 - 8));
        if(a.isStopped()) {
            if(Math.random() > 0.9995) {
                a.setCurrentFrame(stopFrame - i);
               a.start();
            }
        }
    }
    
    public void stopAnim() {
        a.stop();
    }
    
    public boolean isIdle() {
        return true;
    }

    @Override
    public void start() {
        a.setCurrentFrame(be.getFacing() * i);
        stopFrame = (be.getFacing() + 1) * i - 1;
        //a.start();
    }

    @Override
    public void setTarget(BaseEntity target) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}
