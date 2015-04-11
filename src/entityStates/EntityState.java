/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityStates;

import entities.Actor;

/**
 *
 * @author Emmet
 */
public interface EntityState {
    
    
    public void render();
    
    public boolean isIdle();

    public void stopAnim();
    
    public void start();

    public void setTarget(Actor target);
}
