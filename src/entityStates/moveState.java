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

    public moveState(BaseEntity be, Animation a, int i) {
       this.be = be;
       this.a = a;
       this.i = i; 
       a.setCurrentFrame(0);
       a.start();
    }

    @Override
    public void render() {
        a.stopAt(i);
        a.draw(32 * be.getPosX() - (70 - 16), 32 * be.getPosY() - (70 - 8));
        if (i == a.getFrameCount()) {
            be.setIdle();
        }
    }
    
}
