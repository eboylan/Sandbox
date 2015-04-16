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
public class Regenerate implements Effect{
    private Actor a;
    private long duration;
    
    public Regenerate(Actor a, long l) {
        this.a = a;
        this.duration = l;
    }
    
    @Override
    public void update() {
        if(duration == -1) {
           
        } else if(a.getFCount() > duration) {
            a.removeEffect(this);
        }
        if(a.getFCount() % 100 == 0 && a.getFood() > 20) {
             a.modHP(1);
        }       
    }
    
    @Override
    public void setActor(Actor a) {
        this.a = a;
        if (duration > 0) {
            this.duration += a.getFCount();
        }
    }
}
