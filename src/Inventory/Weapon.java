/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Inventory;

/**
 *
 * @author Emmet
 */
public class Weapon extends Item {
    private int attMod;
    
    public Weapon(String name, int imageCol, int imageRow, int attMod) {
        super(name, imageCol, imageRow);
        this.attMod = attMod;
    }

    /**
     * @return the attMod
     */
    public int getAttMod() {
        return attMod;
    }
}
