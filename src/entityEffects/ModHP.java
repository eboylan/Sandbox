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
public class ModHP implements Effect{
    private BaseEntity be;
    private long duration;
    private int mod;
    
    public ModHP(BaseEntity be, long l, int i) {
        this.be = be;
        this.duration = l;
        this.mod = i;
        
    }
    
    public void update() {
        if(duration == -1) {
            
        }
        else if (be.getFcount() > duration) {
           be.effectHP(-mod); 
           be.removeEffect(this);
        }
    }
    
    public void setBE(BaseEntity be) {
        this.be = be;
        be.effectHP(mod);
        if (duration > 0) {
            this.duration += be.getFcount();
        }
    }
}
