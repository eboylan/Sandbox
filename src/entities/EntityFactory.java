/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entityEffects.*;
import inventory.Armour;
import inventory.Food;
import inventory.Item;
import inventory.ItemFactory;
import inventory.Weapon;
import org.newdawn.slick.SlickException;
import world.World;

/**
 *
 * @author Emmet
 */
public class EntityFactory {
    private World world;
    private ItemFactory itemFactory;
    
    public EntityFactory(World world, ItemFactory iFact) {
        this.world = world;
        this.itemFactory = iFact;
    }
    
    public Player newPlayer() throws SlickException {
        Player player = new Player("player", world, 4, 2, 6, 2, 12, 6);
        world.putInClearTile(player, 0);
        player.setEntAI(new EntityAI(player));
        player.setEquipedArmour(itemFactory.newTunic());
        player.setEquipedWeapon(itemFactory.newDagger());
        player.setOffHand(itemFactory.newTorch());
        player.addEffect(new Hunger(player, -1));
        player.addEffect(new Regenerate(player, -1));
        return player;
    }
    
    public Actor newMyconoid(boolean spawn, int depth) throws SlickException {
        Actor myconoid = new Actor("myconoid", world, 40, 5, 0, 2, 4, 2);
        world.putInClearTile(myconoid, depth);
        myconoid.setEntAI(new MyconoidAI(myconoid, this, spawn));
        myconoid.inventoryAdd(itemFactory.newQuorn());
        return myconoid;
    }
    
    public Actor newLizard(int depth) throws SlickException {
        Actor lizard = new Actor("lizard", world, 3, 3, 6, 1, 6, 3);
        world.putInClearTile(lizard, depth);
        lizard.setEntAI(new WanderMonsterAI(lizard));
        lizard.inventoryAdd(itemFactory.newLizMeat());
        return lizard;
    }
    
    public Actor newGoblin(int depth, Actor player) throws SlickException {
        Actor goblin = new Actor("goblin", world, 3, 2, 6, 2, 5, 6);

        world.putInClearTile(goblin, depth);     
        goblin.setEntAI(new HuntingMonsterAI(goblin, player));
        goblin.setEquipedArmour(itemFactory.newTunic());
        goblin.setEquipedWeapon(itemFactory.newDagger());
        goblin.setOffHand(itemFactory.newShield());
        return goblin;
    }
    
    public Actor newManticore(int depth, Actor player) throws SlickException {
        Actor manticore = new Actor("manticore", world, 22, 2, 8, 4, 15, 5);
        world.putInClearTile(manticore, depth);
        manticore.addEffect(new Regenerate(manticore, -1));
        manticore.setEntAI(new HuntingMonsterAI(manticore, player));
        return manticore;
    }
}
