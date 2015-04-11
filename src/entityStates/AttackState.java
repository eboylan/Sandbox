/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityStates;

import entities.Actor;
import entities.Player;
import org.newdawn.slick.Animation;

/**
 *
 * @author Emmet
 */
public class AttackState implements EntityState {
    
    Animation a;
    int i;        
    private Actor be;
    private Actor target;
    int stopFrame;

    public AttackState(Actor be, Animation a, int i) {
        this.be = be;
        this.a = a;
        this.i = i;
        target = null;
    }

    @Override
    public void render() {
        synchronized(this) {
        a.stopAt(stopFrame);
        a.draw(32 * be.getPosX() - (a.getWidth() / 2 - 16), 32 * be.getPosY() - (a.getHeight() / 2 - 8));
        if (a.isStopped()) {
            be.attack(target);
            be.setIdle();
            if (be.getClass().equals(Player.class)) {
                be.getWorld().update();
            }
        }
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
    
    public void setTarget(Actor t) {
        target = t;
    }
}
