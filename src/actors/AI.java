package actors;

import java.util.Iterator;
import util.Line;
import util.Point;
import world.Tile;

public class AI {
    protected Actor a;
    
    public AI(Actor a) {
        this.a = a;
        this.a.setAI(this);
    }
    
    public void onEnter(int z, int x, int y, Tile tile) {
        if(tile.isGround()) {
            a.setPos(z, x, y);
        } else {
            //
        }
    }
    
    public void onUpdate() {
        a.update();
        if(a.getHitPoints() > 0) {
           
        } else {
            a.setState(5);
        }
    }

    public boolean canSeeDim(int z, int x, int y) {
        if (z != a.getPosZ() || (a.getPosX() - x) * (a.getPosX() - x) + (a.getPosY() - y) * (a.getPosY() - y) > a.getVisionRadius() * a.getVisionRadius()) {          
            return false;
        }
        Line line = new Line(a.getPosX(), a.getPosY(), x, y);
        for (Iterator it = line.iterator(); it.hasNext();) {
            Point p = (Point) it.next();
            if (a.tile(z, p.x, p.y).isGround() || p.x == x && p.y == y)
                continue;
            return false;
        }
        return true;
    }
    
    public boolean canSeeLit(int z, int x, int y) {
        if (z != a.getPosZ() || (a.getPosX() - x) * (a.getPosX() - x) + (a.getPosY() - y) * (a.getPosY() - y) > a.getVisionRadius()/2 * a.getVisionRadius()/2) {          
            return false;
        }
        Line line = new Line(a.getPosX(), a.getPosY(), x, y);
        for (Iterator it = line.iterator(); it.hasNext();) {
            Point p = (Point) it.next();
            if (a.tile(z, p.x, p.y).isGround() || p.x == x && p.y == y)
                continue;
            return false;
        }
        return true;
    }
    
    public void wander() {
        int x = (int) (Math.random() * 3) -1;
        int y = (int) (Math.random() * 3) -1;

        if(a.canEnter(a.getPosZ(), a.getPosX() + x, a.getPosY() + y)) {
            a.moveBy(0, x, y);
        } else {
            a.moveBy(0, 0, 0);
        }
    }
}
