/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityStates;

import entities.BaseEntity;
import org.newdawn.slick.Animation;

/**
 *
 * @author Emmet
 */
public class MoveState implements EntityState {

    Animation a;
    int i;
    private final BaseEntity be;
    int stopFrame;
    
    int xOffset;
    int yOffset;
    int x;
    int y;
    int z;

    public MoveState(BaseEntity be, Animation a, int i) {
        this.be = be;
        this.a = a;
        this.i = i;

    }

    @Override
    public void render() {
        a.stopAt(stopFrame);
        if (z == 0) {
            switch (be.getFacing()) {
                case 0:
                    xOffset = (int) ((int) (32 / i) * ((float) i - (float) a.getFrame() % (float) i));
                    yOffset = -(int) ((int) (32 / i) * ((float) i - (float) a.getFrame() % (float) i));
                    x = -1;
                    y = 1;
                    break;
                case 1:
                    xOffset = 0;
                    yOffset = -(int) ((int) (32 / i) * ((float) i - (float) a.getFrame() % (float) i));
                    x = 0;
                    y = 1;
                    break;
                case 2:
                    xOffset = -(int) ((int) (32 / i) * ((float) i - (float) a.getFrame() % (float) i));
                    yOffset = -(int) ((int) (32 / i) * ((float) i - (float) a.getFrame() % (float) i));
                    x = 1;
                    y = 1;
                    break;
                case 3:
                    xOffset = -(int) ((int) (32 / i) * ((float) i - (float) a.getFrame() % (float) i));
                    yOffset = 0;
                    x = 1;
                    y = 0;
                    break;
                case 4:
                    xOffset = -(int) ((int) (32 / i) * ((float) i - (float) a.getFrame() % (float) i));
                    yOffset = (int) ((int) (32 / i) * ((float) i - (float) a.getFrame() % (float) i));
                    x = 1;
                    y = -1;
                    break;
                case 5:
                    xOffset = 0;
                    yOffset = (int) ((int) (32 / i) * ((float) i - (float) a.getFrame() % (float) i));
                    x = 0;
                    y = -1;
                    break;
                case 6:
                    xOffset = (int) ((int) (32 / i) * ((float) i - (float) a.getFrame() % (float) i));
                    yOffset = (int) ((int) (32 / i) * ((float) i - (float) a.getFrame() % (float) i));
                    x = -1;
                    y = -1;
                    break;
                case 7:
                    xOffset = (int) ((int) (32 / i) * ((float) i - (float) a.getFrame() % (float) i));
                    yOffset = 0;
                    x = -1;
                    y = 0;
                    break;
            }
        } else {
            xOffset = 0;
            yOffset = 0;
            x = 0;
            y = 0;
        }
        a.draw((32 * be.getPosX() - (a.getWidth() / 2 - 16)) + xOffset, (32 * be.getPosY() - (a.getHeight() / 2 + 32)) + yOffset);
        if (a.isStopped()) {
            
            be.setIdle();
            be.getWorld().update();
            //be.getEntAI().onEnter(be.getPosZ() + z, be.getPosX() + x, be.getPosY() + y, be.getWorld().tile(be.getPosZ() + z, be.getPosX() + x, be.getPosY() + y));
        }

    }

    public boolean isIdle() {
        return false;
    }

    @Override
    public void stopAnim() {
        a.stop();
    }

    @Override
    public void start() {
        a.setCurrentFrame(be.getFacing() * i);
        stopFrame = (be.getFacing() + 1) * i - 1;
        a.start();
    }

    @Override
    public void setTarget(BaseEntity target) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}
