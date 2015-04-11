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
public class HuntingMonsterAI extends EntityAI {

    Actor target;

    public HuntingMonsterAI(Actor be, Actor player) {
        super(be);
        this.target = player;
    }

    public void onUpdate() {
        if (be.canSeeLit(target.getPosZ(), target.getPosX(), target.getPosY()) || be.canSeeDim(target.getPosZ(), target.getPosX(), target.getPosY()) && target.getEquipment(2).getName().equals("torch")) {
            hunt(target);
        } else {
            wander();
        }
    }

    public void hunt(Actor target) {
        List<Point> points;
        points = new Path(be, target.getPosX(), target.getPosY()).points();

        int mx = points.get(0).x - be.getPosX();
        int my = points.get(0).y - be.getPosY();

        be.moveBy(0, mx, my);
    }
}