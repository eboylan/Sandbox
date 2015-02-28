/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Emmet
 */
public class StartState extends BasicGameState {

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.drawString("    Start State", 200, 200);
        g.drawString("Press Enter to Start", 200, 230);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        if(gc.getInput().isKeyPressed(Input.KEY_ENTER)) {
            sbg.enterState(1);
        }
    }
    
}
