/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityStates;

/**
 *
 * @author Emmet
 */
public interface entityState {
    
    
    public void render();
    
    public boolean isIdle();

    public void stopAnim();
}