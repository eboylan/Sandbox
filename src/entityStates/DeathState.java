/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityStates;

import entities.Actor;
import org.newdawn.slick.Animation;

/**
 *
 * @author Emmet
 */
public class DeathState implements EntityState {
    private final Actor be;
    private final Animation a;
    private final int i;
    private final int stopFrame;
    
    public DeathState(Actor be, Animation a, int i) {
       this.be = be;
       this.a = a;
       this.i = i;
       stopFrame = (be.getFacing() + 1) * i - 1;
    }

    @Override
    public void render() {
        a.stopAt(stopFrame);
        a.draw((32 * be.getPosX() - (a.getWidth()/2 - 16)), (32 * be.getPosY() - (a.getHeight()/2 - 8)));
        if (a.isStopped()) {
            be.kill();
        }
    }

    @Override
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
       a.start();
    }

    @Override
    public void setTarget(Actor target) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
