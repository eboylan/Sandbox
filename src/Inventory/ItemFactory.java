package inventory;

import actorEffects.Effect;
import actorEffects.ModAttack;
import actorEffects.ModDef;
import actorEffects.ModHP;
import java.util.ArrayList;
import java.util.Collections;

/*
 * Author: Emmet Boylan
 * Project: Sandbox Warrior
 * File: ItemFactory.java
 * 
 * Factory class to generate Item objects
 */

public class ItemFactory {
    ArrayList<Effect> potionEffects;

    public ItemFactory() {
        potionEffects = new ArrayList<>();
        potionEffects.add(new ModAttack(null, 10, 2));
        potionEffects.add(new ModDef(null, 10, 2));
        potionEffects.add(new ModHP(null, -1, 2));
        potionEffects.add(new ModAttack(null, 10, -2));
        potionEffects.add(new ModDef(null, 10, -2));
        potionEffects.add(new ModHP(null, -1, -2));
        Collections.shuffle(potionEffects);
    }
    
    public Armour newTunic() {
        Armour tu = new Armour("Tunic", 36, 21, 0, "Plain tunic");
        return tu;
    }
    
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
    
    public Item newMess() {
        Item m = new Item("Runined Mess", 6, 30, "Useless item");
        return m;
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
        Potion bp = new Potion("Black Potion", 28, 24, potionEffects.get(0), "Strange black liquid");
        return bp;
    }
    
    public Potion newBluePotion() {
        Potion bp = new Potion("Blue Potion", 29, 24, potionEffects.get(1), "Strange blue liquid");
        return bp;
    }
    
    public Potion newBrownPotion() {
        Potion bp = new Potion("Brown Potion", 30, 24, potionEffects.get(2), "Strange brown liquid");
        return bp;
    }
    
    public Food newQuorn() {
        Food q = new Food("Quorn", 37, 23, 10, "myconoid meat +10 food");
        return q;
    }
    
    public Food newLizMeat() {
        Food q = new Food("Lizard Meat", 36, 23, 20, "lizard meat +20 food");
        return q;
    }
    
    public Food newCookedQuorn() {
        Food cq = new Food("Cooked Quorn", 43, 23, 20, "cooked quorn +20 food;");
        return cq;
    }
    
    public Food newCookedLizMeat() {
        Food clm = new Food("Cooked Meat", 43, 23, 40, "cooked meat +40 food;");
        return clm;
    }
    
    public Item newManticoreRemains() {
        Item mr = new Item("Skeleton", 16, 7, "Manticore Remains;");
        return mr;
    }
}
