package Inventory;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Emmet
 */
public class Armour extends Item {
    private int defMod;
    
    public Armour(String name, int imageCol, int imageRow, int defMod, String description) {
        super(name, imageCol, imageRow, description);
        this.defMod = defMod;
    }

    /**
     * @return the defMod
     */
    public int getDefMod() {
        return defMod;
    }
}
