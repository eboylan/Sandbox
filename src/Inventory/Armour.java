package inventory;
/*
 * Author: Emmet Boylan
 * Project: Sandbox Warrior
 * File: Armour.java
 * 
 * Extension of Item class that modifies Defence of Entities
 * 
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
