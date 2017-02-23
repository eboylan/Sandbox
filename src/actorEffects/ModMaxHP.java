/*
 * Author: Emmet Boylan
 * Project: Sandbox Warrior
 * File: ModHP.java
 * 
 * Class that handles Effects that modify Hit Point variable
 */
package actorEffects;

import actors.Actor;

/**
 *
 * @author Emmet
 */
public class ModMaxHP implements Effect{
    private Actor a;
    private long duration;
    private int mod;
    
    public ModMaxHP(Actor a, long l, int i) {
        this.a = a;
        this.duration = l;
        this.mod = i;
        
    }
    
    @Override
    public void update() {
        if(duration == -1) {
            
        }
        else if (a.getFCount() > duration) {
           a.effectMaxHitPoints(-mod);
           a.effectHP(-mod);
           if(mod > 0) {
                a.message("Max hit points lowered by " + mod);
           } else {
               a.message("Max hit points increased by " + -(mod));
           }
           a.removeEffect(this);
        }
    }
    
    @Override
    public void setActor(Actor a) {
        this.a = a;
        a.effectMaxHitPoints(mod);
        a.effectHP(mod);
        if(mod > 0) {
                a.message("Hitpoints increased by " + mod);
           } else {
               a.message("Hitpoints lowered by " + mod);
           }
        if (duration > 0) {
            this.duration += a.getFCount();
        }
    }
}
