/*
 * Author: http://trystans.blogspot.ie/ modified by Emmet Boylan
 * Project: Sandbox Warrior
 * File: Line.java
 * 
 * Class to define and support Paths
 * Based on implementation from http://trystans.blogspot.ie/
 */
package util;

//import entities.Actor;
import actors.Actor;
import java.util.List;


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
