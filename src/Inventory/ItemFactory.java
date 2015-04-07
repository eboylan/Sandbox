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
     /*
    public Armour newTunic(int depth) {
        Armour tu = new Armour("Tunic", 36, 21, 0, "Plain tunic");
        world.putItemInClearTile(tu, depth);
        return tu;
    }*/
    
    public Armour newTunic() {
        Armour tu = new Armour("Tunic", 36, 21, 0, "Plain tunic");
        return tu;
    }
    
    /* Item newLeatherArmour(int depth) {
        Item la = new Armour("Leather Armour", 22, 21, 1, "Tough Leather +1 Defence");
        world.putItemInClearTile(la, depth);
        return la;
    }*/
    
    public Armour newLeatherArmour() {
        Armour la = new Armour("Leather Armour", 22, 21, 1, "Tough Leather +1 Defence");
        return la;
    }
    
    
    public Armour newChainArmour() {
        Armour ca = new Armour("Chain Armour", 2, 21, 2, "Steel Chain +2 Defence");
        return ca;
    }
    
    public Armour newPlateArmour() {
        Armour pa = new Armour("Plate Armour", 32, 21, 3, "Solid Plate +3 Defence");
        return pa;
    }
    
    public Item newShield() {
        Item sh = new Item("Shield", 46, 22, "Thick wood, +1 Defence");
        return sh;
    }
    
    public Weapon newDagger() {
        Weapon dg = new Weapon("Dagger", 19, 29, 1, "+1 Attack");
        return dg;
    }
    
    public Weapon newAxe() {
        Weapon ax = new Weapon("Axe", 12, 28, 2, "+2 Attack");
        return ax;
    }
    
    public Weapon newSword() {
        Weapon dg = new Weapon("Sword", 18, 29, 3, "+3 Attack");
        return dg;
    }
    
    public Item newTorch() {
        Item tc = new Item("Torch", 57, 18, "Lights up area");
        return tc;
    }
    
    public Potion newBlackPotion() {
        Potion bp = new Potion("Black Potion", 28, 24, new ModAttack(null, 10, 2), "Strange black liquid");
        return bp;
    }
    
    public Item newBluePotion() {
        Item bp = new Potion("Blue Potion", 29, 24, new ModDef(null, 10, 2), "Strange blue liquid");
        return bp;
    }
    
    public Item newBrownPotion() {
        Item bp = new Potion("Brown Potion", 30, 24, new ModHP(null, -1, 2), "Strange brown liquid");
        return bp;
    }
    
    public Item newQuorn() {
        Item q = new Food("Quorn", 37, 23, 10, "myconoid meat +10 food");
        return q;
    }
    
    public Item newLizMeat() {
        Item q = new Food("Lizard Meat", 36, 23, 20, "lizard meat +20 food");
        return q;
    }
}
