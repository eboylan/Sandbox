
package world;

/*
 * Author: Emmet Boylan
 * Project: Sandbox Warrior
 * File: Tile.java
 * 
 * Enum defining Tiles attributes
 */
public enum Tile {
    FLOOR(12, 17, true, 8),
    WALL(8, 16, false, 8),
    BOUNDS(0, 2, false, 1),
    STAIRSUP(31, 15, true, 1),
    STAIRSDOWN(15, 15, true, 1),
    UNKNOWN(4, 1, false, 1);
    
    private int imageCol;
    private int imageRow;
    private boolean isGround;
    private int numAlts;
        
    public int getImageCol() { return imageCol; }
    public int getImageRow() { return imageRow; }
    public boolean isGround() {
	return isGround;
    }
    public int getNumAlts() {
        return numAlts;
    }

    Tile(int c, int r, boolean ig, int numAlts) {
        this.imageCol = c;
        this.imageRow = r;
        this.isGround = ig;
        this.numAlts = numAlts;
    }
    
    
}
