/*
 * Author: Emmet Boylan and http://trystans.blogspot.ie/ 
 * Project: Sandbox Warrior
 * File: ActorFactory.java
 * 
 * Class to generate Actor objects
 * Based on and expanded from implementation from http://trystans.blogspot.ie/
 * Animation and Effects are major author contributions
 */
package actors;

import actorEffects.*;
import actors.states.*;
import inventory.Armour;
import inventory.Food;
import inventory.Item;
import inventory.ItemFactory;
import inventory.Weapon;
import java.util.Random;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import world.World;

/**
 *
 * @author Emmet
 */
public class ActorFactory {
    private Random r;
    private World world;
    private ItemFactory itemFactory;
    private SpriteSheet playerRunSheet, playerAtt1Sheet,playerAtt2Sheet;
    private SpriteSheet playerAtt3Sheet, playerIdleSheet, playerDeathSheet;
    private SpriteSheet goblinRunSheet, goblinAtt1Sheet, goblinAtt2Sheet;
    private SpriteSheet goblinAtt3Sheet, goblinIdleSheet, goblinDeathSheet;
    private SpriteSheet lizardRunSheet, lizardAtt1Sheet, lizardAtt2Sheet;
    private SpriteSheet lizardAtt3Sheet, lizardIdleSheet, lizardDeathSheet;
    private SpriteSheet manticoreRunSheet;
    private SpriteSheet manticoreAtt1Sheet;
    private SpriteSheet manticoreAtt2Sheet;
    private SpriteSheet manticoreAtt3Sheet;
    private SpriteSheet manticoreIdleSheet;
    private SpriteSheet manticoreDeathSheet;

    
    public ActorFactory(World world, ItemFactory iFact) throws SlickException {
        r = new Random();
        this.world = world;
        this.itemFactory = iFact;
        loadSprites();
    }
    
    public Player newPlayer() throws SlickException {
        Player player = new Player("player", world, 4, 2, 6, 2, 12, 6);
        world.putInClearTile(player, 0);
        player.setAI(new AI(player));
        player.addState(0, new IdleState(player, playerIdleSheet, 30, -62, -64));
        player.addState(1, new AttackState(player, playerAtt1Sheet, 15, -62, -54));
        player.addState(2, new AttackState(player, playerAtt2Sheet, 15, -62, -54));
        player.addState(3, new AttackState(player, playerAtt3Sheet, 15, -62, -54));
        player.addState(4, new RunState(player, playerRunSheet, 15, -56, -100));
        player.addState(5, new DeathState(player, playerDeathSheet, 15, -62, -54));
        player.setState(0);
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
        myconoid.setAI(new MyconoidAI(myconoid, this, spawn));
        myconoid.inventoryAdd(itemFactory.newQuorn());
        return myconoid;
    }
    
    public Actor newLizard(int depth) throws SlickException {
        Actor lizard = new Actor("lizard", world, 3, 3, 6, 1, 6, 3);
        world.putInClearTile(lizard, 0);
        lizard.setAI(new WanderMonsterAI(lizard));
        lizard.addState(0, new IdleState(lizard, lizardIdleSheet, 10, -20, -8));
        lizard.addState(1, new AttackState(lizard, lizardAtt1Sheet, 10, -36, -8));
        lizard.addState(2, new AttackState(lizard, lizardAtt2Sheet, 10, -36, -8));
        lizard.addState(3, new AttackState(lizard, lizardAtt3Sheet, 10, -36, -8));
        lizard.addState(4, new RunState(lizard, lizardRunSheet, 13, -9, -4));
        lizard.addState(5, new DeathState(lizard, lizardDeathSheet, 14, -32, 0));
        lizard.setState(0);
        lizard.inventoryAdd(itemFactory.newLizMeat());
        return lizard;
    }
    
    public Actor newGoblin(int depth, Player player) throws SlickException {
        Actor goblin = new Actor("goblin", world, 3, 2, 6, 2, 5, 6);
        world.putInClearTile(goblin, depth);     
        goblin.setAI(new HuntingMonsterAI(goblin, player));
        goblin.addState(0, new IdleState(goblin, goblinIdleSheet, 15, -44, -48));
        goblin.addState(1, new AttackState(goblin, goblinAtt1Sheet, 15, -60, -48));
        goblin.addState(2, new AttackState(goblin, goblinAtt2Sheet, 15, -60, -48));
        goblin.addState(3, new AttackState(goblin, goblinAtt3Sheet, 15, -60, -48));
        goblin.addState(4, new RunState(goblin, goblinRunSheet, 10, -44, -48));
        goblin.addState(5, new DeathState(goblin, goblinDeathSheet, 10, -44, -48));
        goblin.setState(0);
        goblin.setEquipedArmour(itemFactory.newTunic());
        goblin.setEquipedWeapon(itemFactory.newRandomWeapon());
        int i = r.nextInt(10);
        if(i > 4) {
            goblin.setOffHand(itemFactory.newShield());
        } else {
            goblin.setOffHand(null);
        }
        int j = r.nextInt(10);
        if(j > 5) goblin.inventoryAdd(itemFactory.newRandomPotion());
        if(j > 8) goblin.inventoryAdd(itemFactory.newRandomPotion());
        return goblin;
    }
    
    public Actor newManticore(int depth, Player player) throws SlickException {
        Actor manticore = new Actor("manticore", world, 22, 2, 8, 4, 15, 5);
        world.putInClearTile(manticore, depth);
        manticore.addEffect(new Regenerate(manticore, -1));
        manticore.setAI(new HuntingMonsterAI(manticore, player));
        manticore.addState(0, new IdleState(manticore, manticoreIdleSheet, 31, -45, -35));
        manticore.addState(1, new AttackState(manticore, manticoreAtt1Sheet, 31, -45, -35));
        manticore.addState(2, new AttackState(manticore, manticoreAtt2Sheet, 31, -45, -35));
        manticore.addState(3, new AttackState(manticore, manticoreAtt3Sheet, 31, -45, -35));
        manticore.addState(4, new RunState(manticore, manticoreRunSheet, 26, -45, -35));
        manticore.addState(5, new DeathState(manticore, manticoreDeathSheet, 62, -45, -35));
        manticore.inventoryAdd(itemFactory.newManticoreRemains());
        manticore.setState(0);
        return manticore;
    }
    
    public void loadSprites() throws SlickException {
        //player sprites
            playerRunSheet = new SpriteSheet("data/Hero/HeroRun.png", 140, 140);
            playerAtt1Sheet = new SpriteSheet("data/Hero/HeroAttackA.png", 140, 140);
            playerAtt2Sheet = new SpriteSheet("data/Hero/HeroAttackB.png", 140, 140);
            playerAtt3Sheet = new SpriteSheet("data/Hero/HeroAttackC.png", 140, 140);
            playerIdleSheet = new SpriteSheet("data/Hero/HeroFidget.png", 140, 140);
            playerDeathSheet = new SpriteSheet("data/Hero/HeroDeath.png", 140, 140);
                       
        //goblin sprites
            goblinRunSheet = new SpriteSheet("data/Goblin/GoblinRun.png", 120, 120);
            goblinAtt1Sheet = new SpriteSheet("data/Goblin/GoblinAttack.png", 120, 120);
            goblinAtt2Sheet = new SpriteSheet("data/Goblin/GoblinAttack.png", 120, 120);
            goblinAtt3Sheet = new SpriteSheet("data/Goblin/GoblinAttack.png", 120, 120);
            goblinIdleSheet = new SpriteSheet("data/Goblin/GoblinFidget.png", 120, 120);
            goblinDeathSheet = new SpriteSheet("data/Goblin/GoblinDeath.png", 120, 120);
            
         //lizard sprites   
            lizardRunSheet = new SpriteSheet("data/Lizard/LizRun.png", 55, 40);
            lizardAtt1Sheet = new SpriteSheet("data/Lizard/LizAttack.png", 68, 47);
            lizardAtt2Sheet = new SpriteSheet("data/Lizard/LizAttack.png", 68, 47);
            lizardAtt3Sheet = new SpriteSheet("data/Lizard/LizAttack.png", 68, 47);
            lizardIdleSheet = new SpriteSheet("data/Lizard/LizIdle.png", 68, 47);
            lizardDeathSheet = new SpriteSheet("data/Lizard/LizDeath.png", 70, 44); 
            
         //manticore sprites
            manticoreRunSheet = new SpriteSheet("data/Manticore/ManticoreRun.png", 105, 79);
            manticoreAtt1Sheet = new SpriteSheet("data/Manticore/ManticoreAttack.png", 105, 79);
            manticoreAtt2Sheet = new SpriteSheet("data/Manticore/ManticoreAttack.png", 105, 79);
            manticoreAtt3Sheet = new SpriteSheet("data/Manticore/ManticoreAttack.png", 105, 79);
            manticoreIdleSheet = new SpriteSheet("data/Manticore/ManticoreIdle.png", 105, 79);
            manticoreDeathSheet = new SpriteSheet("data/Manticore/ManticoreDeath.png", 105, 79);
    }

}
