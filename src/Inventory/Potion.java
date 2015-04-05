/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory;

import entities.BaseEntity;
import entityEffects.Effect;

/**
 *
 * @author Emmet
 */
public class Potion extends Item {
    private Effect effect;
    private long duration;
    
    public Potion(String name, int imageCol, int imageRow, Effect e, String description) {
        super(name, imageCol, imageRow, description);
        this.effect = e;
    }

    /**
     * @return the effect
     */
    public Effect getEffect() {
        return effect;
    }

    /**
     * @return the duration
     */
    public long getDuration() {
        return duration;
    }
}
