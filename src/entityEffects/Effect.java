/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityEffects;

import entities.Actor;

/**
 *
 * @author Emmet
 */
public interface Effect {
    public void update();

    public void setBE(Actor player);
    
}
