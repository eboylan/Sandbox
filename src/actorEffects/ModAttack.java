/*
 * Author: Emmet Boylan
 * Project: Sandbox Warrior
 * File: ModAttack.java
 * 
 * Class that handles Effects that modify Attack variable
 */
package actorEffects;

import actors.Actor;

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
        if(mod > 0) {
                a.message("Attack increased by " + mod);
           } else {
               a.message("Attack lowered by " + mod);
           }
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
          if(mod > 0) {
               a.message("Attack lowered by " + mod); 
           } else {
              a.message("Attack increased by " + -(mod));
           }
           a.removeEffect(this);
        }
    }
}
