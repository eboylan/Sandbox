package inventory;


import inventory.Armour;
import inventory.Weapon;
import world.World;
import entityEffects.*;

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
    
    public Item newTunic(int depth) {
        Item tu = new Armour("Tunic", 36, 21, 0, "Plain tunic");
        world.putItemInClearTile(tu, depth);
        return tu;
    }
    
    public Item newLeatherArmour(int depth) {
        Item la = new Armour("Leather Armour", 22, 21, 1, "Tough Leather +1 Defence");
        world.putItemInClearTile(la, depth);
        return la;
    }
    
    public Item newChainArmour(int depth) {
        Item ca = new Armour("Chain Armour", 2, 21, 2, "Steel Chain +2 Defence");
        world.putItemInClearTile(ca, depth);
        return ca;
    }
    
    public Item newPlateArmour(int depth) {
        Item pa = new Armour("Plate Armour", 32, 21, 3, "Solid Plate +3 Defence");
        world.putItemInClearTile(pa, depth);
        return pa;
    }
    
    public Item newShield(int depth) {
        Item sh = new Item("Shield", 46, 22, "Thick wood, +1 Defence");
        world.putItemInClearTile(sh, depth);
        return sh;
    }
    
    public Item newDagger(int depth) {
        Item dg = new Weapon("Dagger", 19, 29, 1, "+1 Attack");
        world.putItemInClearTile(dg, depth);
        return dg;
    }
    
    public Item newAxe(int depth) {
        Item ax = new Weapon("Axe", 12, 28, 2, "+2 Attack");
        world.putItemInClearTile(ax, depth);
        return ax;
    }
    
    public Item newSword(int depth) {
        Item dg = new Weapon("Sword", 18, 29, 3, "+3 Attack");
        world.putItemInClearTile(dg, depth);
        return dg;
    }
    
    public Item newTorch(int depth) {
        Item tc = new Item("Torch", 57, 18, "Lights up area");
        world.putItemInClearTile(tc, depth);
        return tc;
    }
    
    public Item newBlackPotion(int depth) {
        Item bp = new Potion("Black Potion", 28, 24, new ModAttack(null, 10, 2), "Strange black liquid");
        world.putItemInClearTile(bp, depth);
        return bp;
    }
    
    public Item newBluePotion(int depth) {
        Item bp = new Potion("Blue Potion", 29, 24, new ModDef(null, 10, 2), "Strange blue liquid");
        world.putItemInClearTile(bp, depth);
        return bp;
    }
    
    public Item newBrownPotion(int depth) {
        Item bp = new Potion("Brown Potion", 30, 24, new ModHP(null, -1, 2), "Strange brown liquid");
        world.putItemInClearTile(bp, depth);
        return bp;
    }
}
