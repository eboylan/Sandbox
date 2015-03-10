/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

import Inventry.Item;
import entities.BaseEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Emmet
 */
public class World {

    private Tile[][][] tiles;
    private int depth;
    private int height;
    private int width;
    public List<BaseEntity> entities;
    public List<Item> worldInventry;
    private boolean won = false;

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
        this.entities = new ArrayList<>();
        this.worldInventry = new ArrayList<>();
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

    public BaseEntity isEntityAt(int z, int x, int y) {
        for (BaseEntity be : entities) {
            if (be.getPosZ() == z && be.getPosX() == x && be.getPosY() == y) {
                return be;
            }
        }
        return null;
    }

    public void putInClearTile(BaseEntity be, int z) {
        int x;
        int y;

        do {
            x = (int) (Math.random() * getWidth());
            y = (int) (Math.random() * getHeight());
        } while (tile(z, x, y).isGround() == false || isEntityAt(z, x, y) != null);
        be.setPos(z, x, y);
        entities.add(be);
    }
    
    public void remove(BaseEntity target) {
        entities.remove(target);
    }

    public void update() {
        List<BaseEntity> toUpdate = new ArrayList<>(entities);
            for (BaseEntity be : toUpdate){
                be.update();
            }
    }

    public void putItemInClearTile(Item i, int depth) {
        int x;
        int y;

        do {
            x = (int) (Math.random() * getWidth());
            y = (int) (Math.random() * getHeight());
        } while (tile(depth, x, y).isGround() == false);
        i.setPos(depth, x, y);
        worldInventry.add(i);
    }
}
