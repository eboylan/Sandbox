/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameStates;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Emmet
 */
public class StartState extends BasicGameState {
    private Image bGround;
    private Music introMusic;

    @Override
    public int getID() {
        return 0;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        bGround = new Image("data/dDelve.jpg");
        introMusic = new Music("data/Music/Soliloquy_1.wav");
        introMusic.setVolume(0.4f);
        introMusic.loop();
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        
        bGround.getSubImage(10, 10, 800, 800).draw(112, 0);
        g.setColor(Color.white);
        g.drawString("    Start State", 200, 100);
        g.drawString("Press Enter to Start", 200, 130);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        if(gc.getInput().isKeyPressed(Input.KEY_ENTER)) {
            introMusic.stop();
            sbg.enterState(1);
        }
    }   
}
