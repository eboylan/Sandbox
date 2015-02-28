/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package world;

/**
 *
 * @author Emmet
 */

public enum Tile {
    FLOOR(19, 13),
    WALL(16, 16),
    BOUNDS(0, 2),
    STAIRSUP(31,15),
    STAIRSDOWN(15,15),
    UNKNOWN(4,1);
    
    private int imageCol;
    private int imageRow;
        
    public int getImageCol() { return imageCol; }
    public int getImageRow() { return imageRow; }

    Tile(int r, int c) {
        this.imageRow = c;
        this.imageCol = r;
    }
    
    public boolean isGround() {
	return this != WALL && this != BOUNDS;
    }
}
