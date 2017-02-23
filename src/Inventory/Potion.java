
package inventory;

import actorEffects.Effect;

/*
 * Author: Emmet Boylan
 * Project: Sandbox Warrior
 * File: Potion.java
 * 
 * Class that defines Potion objects and stores their Effect
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
