/*
 * Author: Emmet Boylan
 * Project: Sandbox Warrior
 * File: AI.java
 * 
 * Interface for Actor State classes
 */
package actors.states;

import actors.Actor;

public interface ActorState {
    void draw();
    
    void updateAnim();
    
    void start();
    
    int getAnimFCount();

    public void setTarget(Actor target);
    
    int getAnimXoffset();
    
    int getAnimYoffset();
}
