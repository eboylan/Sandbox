package inventory;
/*
 * Author: Emmet Boylan
 * Project: Sandbox Warrior
 * File: Icon.java
 * 
 * An enumeration of Icons to provide locations of graphic tiles to GUI

 */
public enum Icon {
    ATTACK(8, 1),
    DEFENCE(5, 1),
    HITPOINTS(0, 0),
    FOOD(47, 23),
    SELECTBOX(10, 0),
    UIBORDER(31, 17),
    CHEST(43, 45),
    CRAFTSELECT(8, 0),
    ARMOURSLOT(7, 1),
    HANDSLOT(12, 21);
    
    private int imageRow;
    private int imageCol;
    
    Icon(int c, int r)
    {
        this.imageRow = r;
        this.imageCol = c;      
    }

    public int getImageRow() {
        return imageRow;
    }

    public int getImageCol() {
        return imageCol;
    }
}
