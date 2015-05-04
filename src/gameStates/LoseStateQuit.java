/*
 * Author: Emmet Boylan
 * Project: Sandbox Warrior
 * File: LoseStateQuit.java
 * 
 * Simply displays User message upon quitting
 */
package gameStates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class LoseStateQuit extends BasicGameState{

    @Override
    public int getID() {
        return 3;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.drawString("You Escape the Caves, mission failed ", 200, 200);
        g.drawString("As the Manticore claims more lives you", 200, 230);
        g.drawString("sink into depression and turn to drink", 200, 260);
        g.drawString("You die alone and unloved", 200, 290);
        g.drawString("Press Enter to return to start or Escape to exit", 200, 320);
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
