/*
 * Author: Emmet Boylan
 * Project: Sandbox Warrior
 * File: RunState.java
 * 
 * Handles drawing animations for entity based on current state
 * Changes state on termination
 */
package actors.states;

import actors.Actor;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import world.Tile;

public class RunState implements ActorState{
    Actor actor;
    SpriteSheet sprites;
    public int frames;
    public int animFCount;
    int animXoffset;
    int animYoffset;
    
    public RunState(Actor actor, SpriteSheet sprites, int frames, int xoff, int yoff) {
        this.actor = actor;
        this.sprites = sprites;
        this.frames = frames;
        animFCount = 0;
        animXoffset = xoff;
        animYoffset = yoff;
    }
    
    @Override
    public void draw() {
        sprites.getSprite(animFCount, actor.getFacing())
                .draw((32 * actor.getPosX() + animXoffset  + (32 * actor.getHeadingX()) * animFCount/frames), 
                32 * actor.getPosY() + animYoffset  + (32 * actor.getHeadingY() * animFCount/frames)); 
    }
    
    @Override
    public void updateAnim() {
        
       if (animFCount < frames - 1) {
            animFCount++;
        }
       if (animFCount == frames - 1) {
           actor.getAI().onEnter(actor.getPosZ() + actor.getHeadingZ(), 
                    actor.getPosX() + actor.getHeadingX(), 
                    actor.getPosY() + actor.getHeadingY(), 
                    actor.getWorld()
                .tile(actor.getPosZ() + actor.getHeadingZ(), 
                    actor.getPosX() + actor.getHeadingX(), 
                    actor.getPosY() + actor.getHeadingY())); 
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
