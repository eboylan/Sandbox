/*
 * Author: Emmet Boylan
 * Project: Sandbox Warrior
 * File: DeathState.java
 * 
 * Handles drawing animations for entity based on current state
 * and kills entity upon termination
 */
package actors.states;

import actors.Actor;
import org.newdawn.slick.SpriteSheet;

public class DeathState implements ActorState{
    Actor actor;
    SpriteSheet sprites;
    public int frames;
    public int animFCount;
    int animXoffset;
    int animYoffset;
    
    public DeathState(Actor actor, SpriteSheet sprites, int frames, int xoff, int yoff) {
        this.actor = actor;
        this.sprites = sprites;
        this.frames = frames;
        animXoffset = xoff;
        animYoffset = yoff;
        animFCount = 0;
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
           actor.die();
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
       //
    }

    @Override
    public int getAnimXoffset() {
        return 0;
    }

    @Override
    public int getAnimYoffset() {
        return 0;
    }
}
