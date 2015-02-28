/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author Emmet
 */
public class WanderMonsterAI extends EntityAI {
    public WanderMonsterAI(BaseEntity be) {
        super(be);
    }
    
    public void onUpdate() {
        wander();
    }
}
