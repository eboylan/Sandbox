/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import inventory.Item;
import actors.Actor;
import actors.Player;
import actors.Actor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 *
 * @author Emmet
 */
public class World {

    private Tile[][][] tiles;
    private int depth;
    private int height;
    private int width;
    public CopyOnWriteArrayList<Actor> actors;
    public List<Item> worldInventory;
    private Map<Tile, Actor> reservedTiles;
    private boolean won = false;
    private Player playerRef = null;

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
    
    public int getDepth() {
        return depth;
    }
    
    public boolean ifWon() {
        return won;
    }
    
    public void setWin(boolean w) {
        won = w;
    }

    public World(Tile[][][] tiles) {
        this.tiles = tiles;
        this.depth = tiles.length;
        this.width = tiles[0].length;
        this.height = tiles[0][0].length;
        this.actors = new CopyOnWriteArrayList<>();
        this.worldInventory = new ArrayList<>();
        this.reservedTiles = new HashMap<Tile, Actor>();
    }

    public Tile tile(int d, int c, int r) {
        if (d < 0 || d >= depth || c < 0 || c >= width || r < 0 || r >= height) {
            return Tile.BOUNDS;
        } else {
            return tiles[d][c][r];
        }
    }

    public int getImageCol(int z, int x, int y) {
        return tiles[z][x][y].getImageCol() + ((x * y + z) % tiles[z][x][y].getNumAlts());
    }

    public int getImageRow(int z, int x, int y) {
        return tiles[z][x][y].getImageRow();
    }

    
    public Actor isEntityAt(int z, int x, int y) {
        Actor p = getPlayer();
        if ((p != null) && (p.getPosZ() == z && p.getPosX() == x && p.getPosY() == y)) {
            return p;
        }
        for (Actor a : actors) {
            if (a.getPosZ() == z && a.getPosX() == x && a.getPosY() == y) {
                return a;
            }
        }
        return null;
    }
    
    
    
    public Item isItemAt(int z, int x, int y) {
        for (Item i : worldInventory) {
            if (i.getPosZ() == z && i.getPosX() == x && i.getPosY() == y) {
                return i;
            }
        }
        return null;
    }
      
    public void putInClearTile(Actor a, int z) {
        int x;
        int y;

        do {
            x = (int) (Math.random() * getWidth());
            y = (int) (Math.random() * getHeight());
        } while (tile(z, x, y).isGround() == false || isEntityAt(z, x, y) != null);
        a.setPos(z, x, y);
        actors.add(a);
    }
   
    public void remove(Actor target) {
        actors.remove(target);
    }
     
    public void putItemInClearTile(Item i, int depth) {
        int x;
        int y;

        do {
            x = (int) (Math.random() * getWidth());
            y = (int) (Math.random() * getHeight());
        } while (tile(depth, x, y).isGround() == false);
        i.setPos(depth, x, y);
        worldInventory.add(i);
    } 
    
    public void setPlayerRef(Player p) {
        playerRef = p;
    }
    
    public Player getPlayer() {
        return playerRef;
    }
    
    public void reserveTile(Tile t, Actor a) {
        reservedTiles.put(t,a);
    }
    
    public void freeReservedTiles() {
        reservedTiles.clear();
    }
    
    public boolean isTileFree(Tile t, Actor a) {
        if (reservedTiles.containsKey(t)) {
            if(reservedTiles.get(t).equals(a)) {
                return true;
            } else {
                return false;
            }
        } 
        return true;
    }
}
