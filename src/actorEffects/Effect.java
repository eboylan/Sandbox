package actorEffects;

import actors.Actor;
/*
 * Author: Emmet Boylan
 * Project: Sandbox Warrior
 * File: Effect.java
 * 
 * Interface for Effect classes
 */
public interface Effect {
    public void update();

    public void setActor(Actor a);
    
}
