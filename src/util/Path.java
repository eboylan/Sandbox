/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

//import entities.Actor;
import actors.Actor;
import java.util.List;

/**
 *
 * @author Emmet
 */
public class Path {

  private static Pathfinder pf = new Pathfinder();

  private List<Point> points;
  public List<Point> points() { return points; }

  
  public Path(Actor a, int x, int y){
      points = pf.findPath(a, 
                           new Point(a.getPosZ(), a.getPosX(), a.getPosY()), 
                           new Point(a.getPosZ(), x, y), 
                           300);
  }
}
