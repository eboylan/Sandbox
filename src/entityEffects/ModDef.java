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
public class ModDef implements Effect{
    private Actor be;
    private long duration;
    private int mod;
    
    public ModDef(Actor be, long l, int i) {
        this.be = be;
        this.duration = l;
        this.mod = i;
        
    }
    
    public void update() {
        if(duration == -1) {
            
        }
        else if (be.getFcount() > duration) {
           be.effectDefence(-mod); 
           be.removeEffect(this);
        }
    }
    
    public void setBE(Actor be) {
        this.be = be;
        be.effectDefence(mod);
        if (duration > 0) {
            this.duration += be.getFcount();
        }
    }
}
