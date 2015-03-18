/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Iterator;
import util.Line;
import util.Point;
import world.Tile;

/**
 *
 * @author Emmet
 */
public class EntityAI {
    protected BaseEntity be;
    
    public EntityAI(BaseEntity be) {
        this.be = be;
        this.be.setEntAI(this);
    }
    
    //use this method to spot at walls, open doors, chests, attack etc
    public void onEnter(int z, int x, int y, Tile tile) {
        if(tile.isGround()) {
            be.setPos(z, x, y);
        } else {
            //add tile blocked message to UI
        }
    }
    
    public void onUpdate() {
        //do something
    }

    public boolean canSeeDim(int z, int x, int y) {
        if (z != be.getPosZ() || (be.getPosX() - x) * (be.getPosX() - x) + (be.getPosY() - y) * (be.getPosY() - y) > be.getVisionRadius() * be.getVisionRadius()) {          
            return false;
        }
        Line line = new Line(be.getPosX(), be.getPosY(), x, y);
        for (Iterator it = line.iterator(); it.hasNext();) {
            Point p = (Point) it.next();
            if (be.tile(z, p.x, p.y).isGround() || p.x == x && p.y == y)
                continue;
            return false;
        }
        return true;
    }
    
    public boolean canSeeLit(int z, int x, int y) {
        if (z != be.getPosZ() || (be.getPosX() - x) * (be.getPosX() - x) + (be.getPosY() - y) * (be.getPosY() - y) > be.getVisionRadius()/2 * be.getVisionRadius()/2) {          
            return false;
        }
        Line line = new Line(be.getPosX(), be.getPosY(), x, y);
        for (Iterator it = line.iterator(); it.hasNext();) {
            Point p = (Point) it.next();
            if (be.tile(z, p.x, p.y).isGround() || p.x == x && p.y == y)
                continue;
            return false;
        }
        return true;
    }
    
    public void wander() {
        int x = (int) (Math.random() * 3) -1;
        int y = (int) (Math.random() * 3) -1;
        if(x == 0 && y == 0){  //prevent attacking self
            return;
        }
        be.moveBy(0, x, y);
    }
}
