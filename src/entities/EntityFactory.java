/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import world.World;

/**
 *
 * @author Emmet
 */
public class EntityFactory {
    private World world;
    
    public EntityFactory(World world) {
        this.world = world;
    }
    
    public BaseEntity newPlayer() {
        BaseEntity player = new BaseEntity("player", world, 4, 2, 6, 2, 12, 9);
        world.putInClearTile(player, 0);
        new EntityAI(player);
        return player;
    }
    
    public BaseEntity newMyconoid(boolean spawn, int depth) {
        BaseEntity myconoid = new BaseEntity("myconoid", world, 40, 5, 0, 2, 4, 2);
        world.putInClearTile(myconoid, depth);
        new MyconoidAI(myconoid, this, spawn);
        return myconoid;
    }
    
    public BaseEntity newLizard(int depth) {
        BaseEntity lizard = new BaseEntity("lizard", world, 3, 3, 6, 1, 6, 3);
        world.putInClearTile(lizard, depth);
        new WanderMonsterAI(lizard);
        return lizard;
    }
    
    public BaseEntity newGoblin(int depth, BaseEntity player) {
        BaseEntity goblin = new BaseEntity("goblin", world, 3, 2, 6, 2, 5, 4);
        world.putInClearTile(goblin, depth);
        new HuntingMonsterAI(goblin, player);
        return goblin;
    }
    
    public BaseEntity newManticore(int depth, BaseEntity player) {
        BaseEntity manticore = new BaseEntity("manticore", world, 22, 2, 8, 4, 15, 5);
        world.putInClearTile(manticore, depth);
        new HuntingMonsterAI(manticore, player);
        return manticore;
    }
}
