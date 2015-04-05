/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityEffects;

import entities.BaseEntity;

/**
 *
 * @author Emmet
 */
public class Hunger implements Effect{
    private BaseEntity be;
    private long duration;
    
    public Hunger(BaseEntity be, long l) {
        this.be = be;
        this.duration = l;
    }
    
    public void update() {
        if(duration == -1) {
           
        } else if(be.getFcount() > duration) {
            be.removeEffect(this);
        }
        if(be.getFcount() % 10 == 0) {
            be.modFood(-1);
            if(be.getFood() < 1) {
                be.modHP(-1);
            }           
        }       
    }

    public void setBE(BaseEntity be) {
        this.be = be;
        if (duration > 0) {
            this.duration += be.getFcount();
        }
    }

}
