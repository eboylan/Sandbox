/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Emmet
 */
public class Line implements Iterable {
    private List<Point> points;
    public List<Point> getPoints() { return points; }

    public Line(int x0, int y0, int x1, int y1) {
        points = new ArrayList<>();
    
        int dx = Math.abs(x1-x0);
        int dy = Math.abs(y1-y0);
    
        int sx = x0 < x1 ? 1 : -1;
        int sy = y0 < y1 ? 1 : -1;
        int err = dx-dy;
    
        while (true){
            points.add(new Point(0, x0, y0));
        
            if (x0==x1 && y0==y1) {
                break;
            }
        
            int e2 = err * 2;
            if (e2 > -dx) {
                err -= dy;
                x0 += sx;
            }
            if (e2 < dx){
                err += dx;
                y0 += sy;
            }
        }
    }

    
    public Iterator iterator() {
        return points.iterator();
    }
}
