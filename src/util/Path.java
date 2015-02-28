/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import entities.BaseEntity;
import java.util.List;

/**
 *
 * @author Emmet
 */
public class Path {

  private static Pathfinder pf = new Pathfinder();

  private List<Point> points;
  public List<Point> points() { return points; }

  public Path(BaseEntity be, int x, int y){
      points = pf.findPath(be, 
                           new Point(be.getPosZ(), be.getPosX(), be.getPosY()), 
                           new Point(be.getPosZ(), x, y), 
                           300);
  }
}
