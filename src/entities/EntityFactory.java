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
        new EntityAI(player);
        player.setEquipedArmour(itemFactory.newTunic());
        player.setEquipedWeapon(itemFactory.newDagger());
        player.setOffHand(itemFactory.newTorch());
        player.addEffect(new Hunger(player, -1));
        player.addEffect(new Regenerate(player, -1));
        return player;
    }
    
    public BaseEntity newMyconoid(boolean spawn, int depth) throws SlickException {
        BaseEntity myconoid = new BaseEntity("myconoid", world, 40, 5, 0, 2, 4, 2);
        world.putInClearTile(myconoid, depth);
        new MyconoidAI(myconoid, this, spawn);
        myconoid.inventoryAdd(itemFactory.newQuorn());
        return myconoid;
    }
    
    public BaseEntity newLizard(int depth) throws SlickException {
        BaseEntity lizard = new BaseEntity("lizard", world, 3, 3, 6, 1, 6, 3);
        world.putInClearTile(lizard, depth);
        new WanderMonsterAI(lizard);
        lizard.inventoryAdd(itemFactory.newLizMeat());
        return lizard;
    }
    
    public BaseEntity newGoblin(int depth, BaseEntity player) throws SlickException {
        BaseEntity goblin = new BaseEntity("goblin", world, 3, 2, 6, 2, 5, 6);
        world.putInClearTile(goblin, depth);
        new HuntingMonsterAI(goblin, player);
        goblin.setEquipedArmour(itemFactory.newTunic());
        goblin.setEquipedWeapon(itemFactory.newDagger());
        goblin.setOffHand(itemFactory.newShield());
        return goblin;
    }
    
    public BaseEntity newManticore(int depth, BaseEntity player) throws SlickException {
        BaseEntity manticore = new BaseEntity("manticore", world, 22, 2, 8, 4, 15, 5);
        world.putInClearTile(manticore, depth);
        manticore.addEffect(new Regenerate(manticore, -1));
        new HuntingMonsterAI(manticore, player);
        return manticore;
    }
}
