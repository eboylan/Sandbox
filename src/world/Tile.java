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
    FLOOR(19, 13, true),
    FLOOR2(20, 13, true),
    FLOOR3(21, 13, true),
    FLOOR4(22, 13, true),
    FLOOR5(23, 13, true),
    FLOOR6(24, 13, true),
    FLOOR7(25, 13, true),
    FLOOR8(26, 13, true),
    WALL(16, 16, false),
    BOUNDS(0, 2, false),
    STAIRSUP(31,15, true),
    STAIRSDOWN(15,15, true),
    UNKNOWN(4,1, false);
    
    private int imageCol;
    private int imageRow;
    private boolean isGround;
        
    public int getImageCol() { return imageCol; }
    public int getImageRow() { return imageRow; }

    Tile(int r, int c, boolean ig) {
        this.imageRow = c;
        this.imageCol = r;
        this.isGround = ig;
    }
    
    public boolean isGround() {
	return isGround;
    }
}
