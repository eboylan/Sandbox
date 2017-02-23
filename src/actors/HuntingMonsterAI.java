/*
 * Author: Emmet Boylan and http://trystans.blogspot.ie/ 
 * Project: Sandbox Warrior
 * File: HuntingMonsterAI.java
 * 
 * Class to define and support hunting monster AI behaviour
 * Based on and expanded from implementation from http://trystans.blogspot.ie/
 */
package actors;

import java.util.List;
import util.Path;
import util.Point;

public class HuntingMonsterAI extends AI {

    Actor target;

    public HuntingMonsterAI(Actor a, Actor target) {
        super(a);
        this.target = target;
    }

    @Override
    public void onUpdate() {
        if (target == null) {
            a.setAI(new WanderMonsterAI(a));
        }
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

    public void hunt(Actor target) {
        List<Point> points;
        points = new Path(a, target.getPosX(), target.getPosY()).points();

        int mx = points.get(0).x - a.getPosX();
        int my = points.get(0).y - a.getPosY();
        if ((mx == 0 && my == 0) || (!a.canDrawPath(a.getPosZ(), a.getPosX() + mx, a.getPosY() + my))) {
            //a.moveBy(0, (int)(Math.random() * 3) -1, (int)(Math.random() * 3) -1);
            int i = 0;
            do {
                i++;
                mx = (int) (Math.random() * 3) - 1;
                my = (int) (Math.random() * 3) - 1;
            } while (!a.canEnter(a.getPosZ(), a.getPosX() + mx, a.getPosY() + my) && i < 8);
        }
        a.moveBy(0, mx, my);
    }

    @Override
    public void wander() {
        int i = 0;
        int x = (int) (Math.random() * 3) - 1;
        int y = (int) (Math.random() * 3) - 1;
        do {
            i++;
            x = (int) (Math.random() * 3) - 1;
            y = (int) (Math.random() * 3) - 1;
        } while (!a.canEnter(a.getPosZ(), a.getPosX() + x, a.getPosY() + y) && i < 8);

        a.moveBy(0, x, y);

    }
}
