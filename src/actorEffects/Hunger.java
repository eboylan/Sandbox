
package actorEffects;

import actors.Actor;

/*
 * Author: Emmet Boylan
 * Project: Sandbox Warrior
 * File: Hunger.java
 * 
 * Effect class that reduces health if food variable falls below threshold
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
                a.message("Lost health due to hunger");
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
