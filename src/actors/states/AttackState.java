/*
 * Author: Emmet Boylan
 * Project: Sandbox Warrior
 * File: AttackState.java
 * 
 * Handles drawing animations for entity based on current state
 * Changes state on termination
 */
package actors.states;

import actors.Actor;
import org.newdawn.slick.SpriteSheet;

public class AttackState implements ActorState{
    Actor actor;
    SpriteSheet sprites;
    public int frames;
    public int animFCount;
    Actor target;
    int animXoffset;
    int animYoffset;
    
    public AttackState(Actor actor, SpriteSheet sprites, int frames, int xoff, int yoff) {
        this.actor = actor;
        this.sprites = sprites;
        this.frames = frames;
        animFCount = 0;
        animXoffset = xoff;
        animYoffset = yoff;
        target = null;
    }
    
    @Override
    public void draw() {
        sprites.getSprite(animFCount, actor.getFacing())
                .draw(32 * actor.getPosX() + animXoffset + 16, 
                32 * actor.getPosY() + animYoffset); 
 
    }
    
    @Override
    public void updateAnim() {
        
       if (animFCount < frames - 1) {
            animFCount++;
        }
       if (animFCount == frames - 1) {
            actor.attack(target);
            actor.setActive(false);
       }
        
    }
    
    @Override
    public void start() {
        animFCount = 0;
    }

    @Override
    public int getAnimFCount() {
        return animFCount;
    }
    
    @Override
    public void setTarget(Actor target) {
        this.target = target;
    }

    @Override
    public int getAnimXoffset() {
        return animXoffset;
    }

    @Override
    public int getAnimYoffset() {
        return animYoffset;
    }
}
