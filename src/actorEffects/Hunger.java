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
public class Hunger implements Effect{
    private Actor a;
    private long duration;
    
    public Hunger(Actor a, long l) {
        this.a = a;
        this.duration = l;
    }
    
    @Override
    public void update() {
        if(duration == -1) {
           
        } else if(a.getFCount() > duration) {
            a.removeEffect(this);
        }
        if(a.getFCount() % 10 == 0) {
            if(a.getFood() > 0) {
                a.modFood(-1);
            }
            if(a.getFood() < 1) {
                a.modHP(-1);
            }           
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
