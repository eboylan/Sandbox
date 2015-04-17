/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actors;

import java.util.List;
import util.Path;
import util.Point;

/**
 *
 * @author Emmet
 */
public class HuntingMonsterAI extends AI {

    Player target;

    public HuntingMonsterAI(Actor a, Player player) {
        super(a);
        this.target = player;
    }

    @Override
    public void onUpdate() {
        a.update();  
        if (a.getHitPoints() > 0) {
            if (a.canSeeDim(target.getPosZ(), target.getPosX(), target.getPosY())) {
                hunt(target);
            } else {
                wander();
            }
        } else {
            a.setState(5);
        }
    }

    public void hunt(Player target) {
        List<Point> points;
        points = new Path(a, target.getPosX(), target.getPosY()).points();

        int mx = points.get(0).x - a.getPosX();
        int my = points.get(0).y - a.getPosY();
        if((mx == 0 && my == 0) || (!a.canDrawPath(a.getPosZ(), a.getPosX() + mx, a.getPosY() + my))) {
            a.moveBy(0, (int)(Math.random() * 3) -1, (int)(Math.random() * 3) -1);
        }     
        a.moveBy(0, mx, my);
    }
    
    @Override
    public void wander() {
        int i = 0;
        int x = (int) (Math.random() * 3) -1;
        int y = (int) (Math.random() * 3) -1;
        do {
           i++;
           x = (int) (Math.random() * 3) -1;
           y = (int) (Math.random() * 3) -1;
        } while (!a.canEnter(a.getPosZ(), a.getPosX() + x, a.getPosY() + y) && i < 8);
        
        a.moveBy(0, x, y);
 
    }
}