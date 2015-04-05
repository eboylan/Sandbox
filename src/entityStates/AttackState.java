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
public class AttackState implements EntityState {
    
    Animation a;
    int i;        
    private BaseEntity be;
    private BaseEntity target;
    int stopFrame;

    public AttackState(BaseEntity be, Animation a, int i) {
        this.be = be;
        this.a = a;
        this.i = i;
        target = null;
    }

    @Override
    public void render() {
        a.stopAt(stopFrame);
        a.draw(32 * be.getPosX() - (70 - 16), 32 * be.getPosY() - (70 - 8));
        if (a.isStopped()) {
            be.attack(target);
            be.setIdle();
            be.getWorld().update();
        }
    }
    
    public boolean isIdle() {
        return false;
    }

    @Override
    public void stopAnim() {
        a.stop();
    }

    @Override
    public void start() {
        a.setCurrentFrame(be.getFacing() * i);
        stopFrame = (be.getFacing() + 1) * i - 1;
        a.start();
    }
    
    public void setTarget(BaseEntity t) {
        target = t;
    }
}
