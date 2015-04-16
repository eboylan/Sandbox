package simpleslickgame;
import gameStates.PlayState;
import gameStates.StartState;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class SimpleSlickGame extends StateBasedGame
{
    public long l1 = 0, l2 = 0, l3 = 0;
    //public int active;
    
	public SimpleSlickGame(String gamename)
	{
		super(gamename);
	}

	public static void main(String[] args)
	{
		try
		{
			AppGameContainer appgc;
			appgc = new AppGameContainer(new SimpleSlickGame("Simple Slick Game"));
			appgc.setDisplayMode(1024, 768, true);
                        appgc.setTargetFrameRate(59);
			appgc.start();
		}
		catch (SlickException ex)
		{
			Logger.getLogger(SimpleSlickGame.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        this.addState(new StartState());
        this.addState(new PlayState());
    }
}