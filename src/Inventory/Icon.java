package inventory;

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
    HITPOINTS(0, 0),
    SELECTBOX(10, 0),
    UIBORDER(31, 17),
    CHEST(43, 45),
    ARMOURSLOT(7, 1),
    HANDSLOT(12, 21);
    
    private int imageRow;
    private int imageCol;
    
    Icon(int c, int r)
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
