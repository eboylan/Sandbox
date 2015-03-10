package Inventory;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Emmet
 */
public enum Icon {
    ATTACK(8, 1),
    DEFENCE(5, 1),
    HITPOINTS(7, 1);
    
    private int imageRow;
    private int imageCol;
    
    Icon(int r, int c)
    {
        this.imageRow = r;
        this.imageCol = c;      
    }

    /**
     * @return the imageRow
     */
    public int getImageRow() {
        return imageRow;
    }

    /**
     * @return the imageCol
     */
    public int getImageCol() {
        return imageCol;
    }
}
