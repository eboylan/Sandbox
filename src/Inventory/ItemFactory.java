package Inventory;


import Inventory.Armour;
import world.World;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Emmet
 */
public class ItemFactory {
    private World world;

    public ItemFactory(World world) {
        this.world = world;
    }
    
    public Item newLeatherArmour(int depth) {
        Item la = new Armour("Leather Armour", 22, 21, 1);
        world.putItemInClearTile(la, depth);
        return la;
    }
    
    public Item newChainArmour(int depth) {
        Item ca = new Armour("Chain Armour", 2, 21, 2);
        world.putItemInClearTile(ca, depth);
        return ca;
    }
    
    public Item newPlateArmour(int depth) {
        Item pa = new Armour("Plate Armour", 32, 21, 3);
        world.putItemInClearTile(pa, depth);
        return pa;
    }
}
