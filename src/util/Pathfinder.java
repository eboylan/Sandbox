/*
 * Author: http://trystans.blogspot.ie/
 * Project: Sandbox Warrior
 * File: Pathfinder.java
 * 
 * Class to define and support Pathfinding
 * Based on A* algorithm
 * Based on implementation from http://trystans.blogspot.ie/
 */
package util;

import actors.Actor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Pathfinder {
       private ArrayList<Point> open;
       private ArrayList<Point> closed;
       private HashMap<Point, Point> parents;
       private HashMap<Point,Integer> totalCost;
     
       public Pathfinder() {
             this.open = new ArrayList<>();
             this.closed = new ArrayList<>();
             this.parents = new HashMap<>();
             this.totalCost = new HashMap<>();
       }
     
       private int heuristicCost(Point from, Point to) {
             return Math.max(Math.abs(from.x - to.x), Math.abs(from.y - to.y));
       }

       private int costToGetTo(Point from) {
             return parents.get(from) == null ? 0 : (1 + costToGetTo(parents.get(from)));
       }
     
       private int totalCost(Point from, Point to) {
             if (totalCost.containsKey(from)) {
               return totalCost.get(from);
           }
           
             int cost = costToGetTo(from) + heuristicCost(from, to);
             totalCost.put(from, cost);
             return cost;
       }

       private void reParent(Point child, Point parent){
             parents.put(child, parent);
             totalCost.remove(child);
       }

       
       public ArrayList<Point> findPath(Actor a, Point start, Point end, int maxTries) {
             open.clear();
             closed.clear();
             parents.clear();
             totalCost.clear();
       
             open.add(start);
           
             for (int tries = 0; tries < maxTries && open.size() > 0; tries++){
                   Point closest = getClosestPoint(end);
                 
                   open.remove(closest);
                   closed.add(closest);

                   if (closest.equals(end)) {
                     return createPath(start, closest);
                 }
                   else {
                     checkNeighbors(a, end, closest);
                 }
             }
             return null;
       }

        private Point getClosestPoint(Point end) {
            Point closest = open.get(0);
            for (Point other : open){
                if (totalCost(other, end) < totalCost(closest, end)) {
                    closest = other;
                }
            }
            return closest;
        }

        
        private void checkNeighbors(Actor a, Point end, Point closest) {
            for (Point neighbor : closest.neighbors8()) {
                if (closed.contains(neighbor)
                 || !a.canDrawPath(neighbor.z/*a.getPosZ()*/, neighbor.x, neighbor.y)
                 && !neighbor.equals(end)) {
                    continue;
                }
    
                if (open.contains(neighbor)) {
                    reParentNeighborIfNecessary(closest, neighbor);
                }
                else {
                    reParentNeighbor(closest, neighbor);
                }
            }
        }

        private void reParentNeighbor(Point closest, Point neighbor) {
            reParent(neighbor, closest);
            open.add(neighbor);
        }

        private void reParentNeighborIfNecessary(Point closest, Point neighbor) {
            Point originalParent = parents.get(neighbor);
            double currentCost = costToGetTo(neighbor);
            reParent(neighbor, closest);
            double reparentCost = costToGetTo(neighbor);
  
            if (reparentCost < currentCost) {
                open.remove(neighbor);
            }
            else {
                reParent(neighbor, originalParent);
            }
        }

        private ArrayList<Point> createPath(Point start, Point end) {
            ArrayList<Point> path = new ArrayList<>();

            while (!end.equals(start)) {
                path.add(end);
                end = parents.get(end);
            }

            Collections.reverse(path);
            return path;
        }
    }
