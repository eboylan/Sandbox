/*
 * Author: Emmet Boylan and http://trystans.blogspot.ie/ 
 * Project: Sandbox Warrior
 * File: MyconoidAI.java
 * 
 * Class to define and support AI behaviour of static, spawning Entities
 * Based on and expanded from implementation from http://trystans.blogspot.ie/
 * Dropped from latest implementation, kept for later reintroduction
 */
package actors;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Emmet
 */
public class MyconoidAI extends AI {
    private ActorFactory actFactory;
    private int spreadcount;
    private boolean spawn;
 
    public MyconoidAI(Actor a, ActorFactory actFactory, boolean spawn) {
        super(a);
        this.actFactory = actFactory;
        this.spawn = spawn;
    }

    /**
     *
     */
    @Override
    public void onUpdate(){
        if (spreadcount < 5 && Math.random() < 0.02 && spawn) {
            try {
                spread();
            } catch (SlickException ex) {
                Logger.getLogger(MyconoidAI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
 
    private void spread() throws SlickException{
        int z = a.getPosZ();
        int x = a.getPosX() + (int)(Math.random() * 11) - 5;
        int y = a.getPosY() + (int)(Math.random() * 11) - 5;
  
        if (!a.canEnter(z, x, y)) {
            return;
        }
  
        Actor child = actFactory.newMyconoid(false, z);
        child.setPos(z, x, y);
        spreadcount++;
    }
}
