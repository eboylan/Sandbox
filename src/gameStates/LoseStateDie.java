/*
 * Author: Emmet Boylan
 * Project: Sandbox Warrior
 * File: LoseStateDie.java
 * 
 * Simply displays User message upon death
 */
package gameStates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class LoseStateDie extends BasicGameState{

    @Override
    public int getID() {
        return 2;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.drawString("You Died Bravely ", 200, 200);
        g.drawString("Press Enter to return to start or Escape to exit", 200, 230);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        if(gc.getInput().isKeyPressed(Input.KEY_ENTER)) {
            sbg.enterState(0);
        }
        if(gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
            System.exit(0);
        }
    }
    
}
