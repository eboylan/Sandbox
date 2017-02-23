/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actorEffects;

import actors.Actor;

/**
 *
 * @author Emmet
 */
public class ModVision implements Effect{
    private Actor a;
    private long duration;
    private int mod;
    
    public ModVision(Actor a, long l, int i) {
        this.a = a;
        this.duration = l;
        this.mod = i;
    }
    
public void update() {
        if(duration == -1) {
            
        }
        else if (a.getFCount() > duration) {
           a.effectVision(-mod);
          if(mod > 0) {
               a.message("Attack lowered by " + mod); 
           } else {
              a.message("Attack increased by " + -(mod));
           }
           a.removeEffect(this);
        }
    }

    @Override
    public void setActor(Actor a) {
        this.a = a;
        a.effectVision(mod);
        if(mod > 0) {
               a.message("Vision range increased by " + mod);
           } else {
               a.message("Vision range lowered by " + mod);
           }
        if (duration > 0) {
            this.duration += a.getFCount();
        }
    }
    
}
