/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actors.states;

import actors.Actor;

/**
 *
 * @author Emmet
 */
public interface ActorState {
    void draw();
    
    void updateAnim();
    
    void start();
    
    int getAnimFCount();

    public void setTarget(Actor target);
}
