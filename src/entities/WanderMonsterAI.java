/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.List;
import util.Path;
import util.Point;

/**
 *
 * @author Emmet
 */
public class WanderMonsterAI extends EntityAI {
    Point target = null;
    public WanderMonsterAI(Actor be) {
        super(be);
    }
    
    public void onUpdate() {    
        wander();
    }
    
    public void goToPoint(Point target) {
        List<Point> points;
        points = new Path(be, target.x, target.y).points();

        int mx = points.get(0).x - be.getPosX();
        int my = points.get(0).y - be.getPosY();

        be.moveBy(0, mx, my);
    }
}
