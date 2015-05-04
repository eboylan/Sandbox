package inventory;

/*
 * Author: Emmet Boylan
 * Project: Sandbox Warrior
 * File: Weapon.java
 * 
 * Class that extends Item, includes a modifier to Entities Damage
 */

public class Weapon extends Item {
    private int attMod;
    
    public Weapon(String name, int imageCol, int imageRow, int attMod, String description) {
        super(name, imageCol, imageRow, description);
        this.attMod = attMod;
    }

    /**
     * @return the attMod
     */
    public int getAttMod() {
        return attMod;
    }
}
