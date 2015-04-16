/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actorEffects;

import actors.Actor;

/**
 *
 * @author Emmet
 */
public class ModDef implements Effect{
    private Actor a;
    private long duration;
    private int mod;
    
    public ModDef(Actor be, long l, int i) {
        this.a = a;
        this.duration = l;
        this.mod = i;
        
    }
    
    @Override
    public void update() {
        if(duration == -1) {
            
        }
        else if (a.getFCount() > duration) {
           a.effectDefence(-mod); 
           a.removeEffect(this);
        }
    }
    
    @Override
    public void setActor(Actor a) {
        this.a = a;
        a.effectDefence(mod);
        if (duration > 0) {
            this.duration += a.getFCount();
        }
    }
}
