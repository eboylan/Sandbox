/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import entities.BaseEntity;
import org.newdawn.slick.Color;
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
public class InventoryState extends BasicGameState {
    

    @Override
    public int getID() {
        return 4;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.drawString("Inventory and Information ", 200, 200);
        g.drawString("Press Enter to return to Play State", 200, 230);
        
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        if(gc.getInput().isKeyPressed(Input.KEY_ENTER)) {
            sbg.enterState(1);
        }
    }
    
    
}
