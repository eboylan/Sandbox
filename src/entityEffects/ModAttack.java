/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityEffects;

import entities.BaseEntity;

/**
 *
 * @author Emmet
 */
public class ModAttack implements Effect{
    private BaseEntity be;
    private long duration;
    private int mod;
    
    public ModAttack(BaseEntity be, long l, int i) {
        this.be = be;
        this.duration = l;
        this.mod = i;
    }
    
    public void setBE(BaseEntity be) {
        this.be = be;
        be.effectAttack(mod);
        if (duration > 0) {
            this.duration += be.getFcount();
        }
    }
    
    public void update() {
        if(duration == -1) {
            
        }
        else if (be.getFcount() > duration) {
           be.effectAttack(-mod); 
           be.removeEffect(this);
        }
    }
}
