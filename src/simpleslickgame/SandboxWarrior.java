package simpleslickgame;

/*
 * Author: Emmet Boylan
 * Project: Sandbox Warrior
 * File: SandboxWarrior.java
 * 
 * Slick2D Game container and state manager
 */
import gameStates.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class SandboxWarrior extends StateBasedGame
{
    public long l1 = 0, l2 = 0, l3 = 0;
    //public int active;
    
	public SandboxWarrior(String gamename)
	{
		super(gamename);
	}

	public static void main(String[] args)
	{
		try
		{
			AppGameContainer appgc;
			appgc = new AppGameContainer(new SandboxWarrior("Sandbox Warrior"));
			appgc.setDisplayMode(1024, 768, true);
                        appgc.setTargetFrameRate(59);
			appgc.start();
		}
		catch (SlickException ex)
		{
			Logger.getLogger(SandboxWarrior.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        this.addState(new StartState());
        this.addState(new PlayState());
        this.addState(new LoseStateDie());
        this.addState(new LoseStateQuit());
        this.addState(new WinState());
    }
}