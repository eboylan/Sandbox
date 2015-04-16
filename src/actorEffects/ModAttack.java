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
public class ModAttack implements Effect{
    private Actor a;
    private long duration;
    private int mod;
    
    public ModAttack(Actor a, long l, int i) {
        this.a = a;
        this.duration = l;
        this.mod = i;
    }
    
    @Override
    public void setActor(Actor a) {
        this.a = a;
        a.effectAttack(mod);
        if (duration > 0) {
            this.duration += a.getFCount();
        }
    }
    
    @Override
    public void update() {
        if(duration == -1) {
            
        }
        else if (a.getFCount() > duration) {
           a.effectAttack(-mod); 
           a.removeEffect(this);
        }
    }
}
