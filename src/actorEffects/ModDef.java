/*
 * Author: Emmet Boylan
 * Project: Sandbox Warrior
 * File: DefAttack.java
 * 
 * Class that handles Effects that modify Defence variable
 */
package actorEffects;

import actors.Actor;

public class ModDef implements Effect{
    private Actor a;
    private long duration;
    private int mod;
    
    public ModDef(Actor a, long l, int i) {
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
           if(mod > 0) {
                a.message("Defence lowered by " + mod);
           } else {
               a.message("Defence increased by " + -(mod));
           }
           a.removeEffect(this);
        }
    }
    
    @Override
    public void setActor(Actor a) {
        this.a = a;
        a.effectDefence(mod);
        if(mod > 0) {
                a.message("Defence increased by " + mod);
           } else {
               a.message("Defence lowered by " + mod);
           }
        if (duration > 0) {
            this.duration += a.getFCount();
        }
    }
}
