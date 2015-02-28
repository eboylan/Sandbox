/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import java.util.Iterator;
import util.Line;
import util.Point;

/**
 *
 * @author Emmet
 */
public class FieldOfView {
    private World world;
    private int depth;

    private boolean[][] visible;
    public boolean isVisible(int z, int x, int y){
        return z == depth && x >= 0 && y >= 0 && x < visible.length && y < visible[0].length && visible[x][y];
    }

    private Tile[][][] tiles;
    public Tile tile(int z, int x, int y){
        return tiles[z][x][y];
    }

    public FieldOfView(World world){
        this.world = world;
        this.visible = new boolean[world.getWidth()][world.getHeight()];
        this.tiles = new Tile[world.getDepth()][world.getWidth()][world.getHeight()];
    
        for (int z = 0; z < world.getDepth(); z++){
            for (int y = 0; y < world.getHeight(); y++){
                for (int x = 0; x < world.getWidth(); x++){
                    tiles[z][x][y] = Tile.UNKNOWN;
                }
            }
        }
    }
    
    public void update(int wz, int wx, int wy, int r){
        depth = wz;
        visible = new boolean[world.getWidth()][world.getHeight()];
    
        for (int x = -r; x < r; x++){
            for (int y = -r; y < r; y++){
                if (x*x + y*y > r*r)
                    continue;
         
                if (wx + x < 0 || wx + x >= world.getWidth() 
                 || wy + y < 0 || wy + y >= world.getHeight())
                    continue;
         
                Line l = new Line(wx, wy, wx + x, wy + y);
                for (Iterator it = l.iterator(); it.hasNext();) {
                    Point p = (Point) it.next();
                    Tile tile = world.tile(wz, p.x, p.y);
                    visible[p.x][p.y] = true;
                    tiles[wz][p.x][p.y] = tile;
                    if (!tile.isGround())
                        break;
                }
            }
        }
    }
}
