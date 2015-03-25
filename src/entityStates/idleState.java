/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityStates;

import entities.BaseEntity;
import entityStates.idleState;
import org.newdawn.slick.Animation;

/**
 *
 * @author Emmet
 */
public class idleState implements entityState {
    
    Animation a;
    int i;        
    private final BaseEntity be;

    public idleState(BaseEntity be, Animation a, int i) {
        this.be = be;
        this.a = a;
        this.i = i;
    }

    @Override
    public void render() {
        //a.stopAt(i);
        a.draw(32 * be.getPosX() - (70 - 16), 32 * be.getPosY() - (70 - 8));
    }
    
}
