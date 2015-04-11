/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gameStates;

import inventory.Armour;
import inventory.Icon;
import inventory.Item;
import inventory.ItemFactory;
import inventory.Weapon;
import entities.Actor;
import entities.EntityFactory;
import entities.Player;
import entityStates.IdleState;
import inventory.CraftItem;
import inventory.Potion;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.*;
import world.*;

/**
 *
 * @author Emmet
 */
public class PlayState extends BasicGameState {

    private World world;
    private Image ground;
    private SpriteSheet groundTiles;
    private int xOffset;
    private int yOffset;
    private Player player;
    private CraftItem craftItem;
    private int tileSize;
    private int screenWidthTiles;
    private int screenHeightTiles;
    boolean showInfo;
    
    private Music music;
    private ItemFactory itemFactory;
    private EntityFactory entFactory;

    @Override
    public int getID() {
        return 1;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        tileSize = 32;
        screenWidthTiles = 32 - 9;
        screenHeightTiles = 23;
        
        ground = new Image("data/DungeonCrawl_ProjectUtumnoTileset.png");
        
        music = new Music("data/Music/Desolate.wav");
               
        groundTiles = new SpriteSheet(ground, tileSize, tileSize);
        createWorld();
        showInfo = true;
                 
        gc.setShowFPS(false);

        itemFactory = new ItemFactory(world);
        entFactory = new EntityFactory(world, itemFactory);
        player = entFactory.newPlayer();
        player.modHP(50);
       
        xOffset = getScrollX();
        yOffset = getScrollY();
        
        music.setVolume(0.4f);        
    }
    
    @Override
    public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
        music.loop();
        for (int i = 0; i < world.getDepth(); i++) {
            for (int j = 0; j < 8 + (2 * i); j++) {
                entFactory.newMyconoid(true, i);
            }
        }

        for (int i = 0; i < world.getDepth(); i++) {
            for (int j = 0; j < 8 + i; j++) {
                entFactory.newLizard(i);
            }
        } 

        for (int i = 0; i < world.getDepth(); i++) {
            for (int j = 0; j < world.getWidth()/10 +  (2 * i); j++) {
                entFactory.newGoblin(i, getPlayer());
            }
        }

        entFactory.newManticore(world.getDepth() - 1, getPlayer());

        for (int i = 0; i < world.getDepth(); i++) {
            world.putItemInClearTile(getItemFactory().newLeatherArmour(), i);
            world.putItemInClearTile(getItemFactory().newLeatherArmour(), i);//getItemFactory().newLeatherArmour(i);
            world.putItemInClearTile(getItemFactory().newTunic(), i); //getItemFactory().newTunic();
            world.putItemInClearTile(getItemFactory().newShield(), i);
            world.putItemInClearTile(getItemFactory().newShield(), i);
            world.putItemInClearTile(getItemFactory().newShield(), i);
            world.putItemInClearTile(getItemFactory().newDagger(), i);
            world.putItemInClearTile(getItemFactory().newAxe(), i);
            world.putItemInClearTile(getItemFactory().newSword(), i);
            world.putItemInClearTile(getItemFactory().newTorch(), i);
            world.putItemInClearTile(getItemFactory().newBlackPotion(), i);
            world.putItemInClearTile(getItemFactory().newBlackPotion(), i);
            world.putItemInClearTile(getItemFactory().newBlackPotion(), i);
            world.putItemInClearTile(getItemFactory().newBluePotion(), i);
            world.putItemInClearTile(getItemFactory().newBluePotion(), i);
            world.putItemInClearTile(getItemFactory().newBluePotion(), i);
            world.putItemInClearTile(getItemFactory().newBrownPotion(), i);
            world.putItemInClearTile(getItemFactory().newBrownPotion(), i);
            world.putItemInClearTile(getItemFactory().newBrownPotion(), i);
            if (Math.random() < i / 2f) {
                world.putItemInClearTile(getItemFactory().newLeatherArmour(), i);
            }
            if (Math.random() < i / 5f) {
                world.putItemInClearTile(getItemFactory().newChainArmour(), i);
            }
            if (Math.random() < i / 10f) {
                world.putItemInClearTile(getItemFactory().newPlateArmour(), i);
            }
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        //music.loop();
        int z = getPlayer().getPosZ();
        groundTiles.startUse();
        for (int x = xOffset; x < xOffset + screenWidthTiles; x++) {
            for (int y = yOffset; y < yOffset + screenHeightTiles; y++) {
                if (world.tile(z, x, y) == Tile.STAIRSUP || world.tile(z, x, y) == Tile.STAIRSDOWN) {
                    groundTiles.getSubImage(22, 17).draw(x * tileSize, y * tileSize);
                }
                groundTiles.getSubImage(world.getImageCol(z, x, y), world.getImageRow(z, x, y)).draw(x * tileSize, y * tileSize);
                if (!player.canSeeDim(z, x, y)) {
                    groundTiles.getSubImage(4, 1).draw(x * tileSize, y * tileSize);
                }
                if (getPlayer().canSeeDim(z, x, y) && !player.canSeeLit(z, x, y)) {
                    groundTiles.getSubImage(53, 0).draw(x * tileSize, y * tileSize);
                }
            }
        }
        for (Item i : world.worldInventory) {
            if (getPlayer().canSeeLit(z, i.getPosX(), i.getPosY()) && z == i.getPosZ()) {
                groundTiles.getSubImage(i.getImageCol(), i.getImageRow()).draw(tileSize * i.getPosX(), tileSize * i.getPosY());
            }
            if (getPlayer().canSeeDim(z, i.getPosX(), i.getPosY()) && !player.canSeeLit(z, i.getPosX(), i.getPosY()) && z == i.getPosZ()) {
                //groundTiles.getSubImage(i.getImageCol(), i.getImageRow()).draw(tileSize * i.getPosX(), tileSize * i.getPosY());
            }
        }
        
        for (Actor be : world.entities) {
            if (getPlayer().canSeeLit(z, be.getPosX(), be.getPosY()) && z == be.getPosZ()) {
                if (!be.getType().equals("player") && !be.getType().equals("goblin")) {
                    groundTiles.getSubImage(be.getImageCol(), be.getImageRow()).draw(tileSize * be.getPosX(), tileSize * be.getPosY());
                }
                if (be.getType().equals("player") || be.getType().equals("goblin")) {
                    //if(be.getType().equals("player")) {
                        be.render();
                    //} else {//test, take out
                        //groundTiles.getSubImage(be.getImageCol(), be.getImageRow()).draw(tileSize * be.getPosX(), tileSize * be.getPosY());
                    //}
                }
            }
        }
        getPlayer().playerUI(g, z, xOffset, yOffset, groundTiles, tileSize, screenWidthTiles, screenHeightTiles);
        
        g.setColor(Color.white);
        
        
        g.drawString("Play State FPS: " + gc.getFPS(), tileSize * (24 + xOffset), (yOffset + 16) * tileSize);
        g.drawString("Press Escape to lose", tileSize * (24 + xOffset), (yOffset + 17) * tileSize);
        //g.drawString("Press Enter to win", tileSize * (24 + xOffset), (yOffset + 18) * tileSize);
        g.drawString("Entity State: " + getPlayer().getEntState(), tileSize * (22 + xOffset), (yOffset + 18) * tileSize);
        //g.drawString("Entities: " + world.entities.size() + " Items: " + world.worldInventory.size(), tileSize * (24 + xOffset), (yOffset + 19) * tileSize);
        g.drawString("Player x : " + getPlayer().getPosX() + " y: " + getPlayer().getPosY() + " z: " + getPlayer().getPosZ(), tileSize * (24 + xOffset), (yOffset + 20) * tileSize);
        for (Actor be : world.entities) {
            if ("manticore".equals(be.getType())) {
                g.drawString("boss : " + be.getPosX() + " y: " + be.getPosY() + " z: " + be.getPosZ(), tileSize * (24 + xOffset), (yOffset + 21) * tileSize);
            }
        }
        
        if(showInfo) {
            renderInfo(g);
        }
        
        groundTiles.endUse();
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        
        if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
            sbg.enterState(3);
        }
        /*if (gc.getInput().isKeyPressed(Input.KEY_ENTER)) {
            sbg.enterState(2);
        }*/
        if (gc.getInput().isKeyPressed(Input.KEY_RIGHT) || gc.getInput().isKeyPressed(Input.KEY_MULTIPLY)) {
            if (player.isShowCraft()) {
                getPlayer().setCraftX(1);
            } else {
                getPlayer().setSelectX(1);
            }
        }
        if (gc.getInput().isKeyPressed(Input.KEY_LEFT) || gc.getInput().isKeyPressed(Input.KEY_DIVIDE)) {
            if (player.isShowCraft()) {
                getPlayer().setCraftX(-1);
            } else {
                getPlayer().setSelectX(-1);
            }
        } 
        if (gc.getInput().isKeyPressed(Input.KEY_UP) || gc.getInput().isKeyPressed(Input.KEY_SUBTRACT)) {
            if (player.isShowCraft()) {
                getPlayer().setCraftY(-1);
            } else {
                getPlayer().setSelectY(-1);
            }
        }
        if (gc.getInput().isKeyPressed(Input.KEY_DOWN) || gc.getInput().isKeyPressed(Input.KEY_ADD)) {
            if (player.isShowCraft()) {
                getPlayer().setCraftY(1);
            } else {
                getPlayer().setSelectY(1);
            }
        }
        
        if (player.isIdle()) {
            
            if(gc.getInput().isKeyPressed(Input.KEY_NUMPAD6)) {
                
                player.stopAnim();
                getPlayer().moveBy(0, 1, 0);
                player.setMove();
                xOffset = getScrollX();             
                //world.update();
                gc.resume();
          
            }
            if (gc.getInput().isKeyPressed(Input.KEY_NUMPAD4)) {
                player.stopAnim();
                getPlayer().moveBy(0, -1, 0);
                xOffset = getScrollX();
                //world.update();
            }
            if (gc.getInput().isKeyPressed(Input.KEY_NUMPAD8)) {
                player.stopAnim();
                getPlayer().moveBy(0, 0, -1);
                yOffset = getScrollY();
                //world.update();
            }
            if (gc.getInput().isKeyPressed(Input.KEY_NUMPAD2)) {
                player.stopAnim();
                getPlayer().moveBy(0, 0, 1);
                yOffset = getScrollY();
                //world.update();
            }
            if (gc.getInput().isKeyPressed(Input.KEY_NUMPAD9)) {
                player.stopAnim();
                getPlayer().moveBy(0, 1, -1);
                xOffset = getScrollX();
                //world.update();
            }
            if (gc.getInput().isKeyPressed(Input.KEY_NUMPAD7)) {
                player.stopAnim();
                getPlayer().moveBy(0, -1, -1);
                xOffset = getScrollX();
                //world.update();
            }
            if (gc.getInput().isKeyPressed(Input.KEY_NUMPAD1)) {
                player.stopAnim();
                getPlayer().moveBy(0, -1, 1);
                xOffset = getScrollX();
                //world.update();
            }
            if (gc.getInput().isKeyPressed(Input.KEY_NUMPAD3)) {
                player.stopAnim();
                getPlayer().moveBy(0, 1, 1);
                xOffset = getScrollX();
                //world.update();
            }
        } 
        if (gc.getInput().isKeyPressed(Input.KEY_NUMPAD5)) {
            if (world.tile(getPlayer().getPosZ(), getPlayer().getPosX(), getPlayer().getPosY()) == Tile.STAIRSUP) {
                getPlayer().moveBy(-1, 0, 0);
                world.getPlayer().message("played went upstairs");
            } else if (world.tile(getPlayer().getPosZ(), getPlayer().getPosX(), getPlayer().getPosY()) == Tile.STAIRSDOWN) {
                getPlayer().moveBy(1, 0, 0);
                world.getPlayer().message("played went downstairs");
            } else {
                world.getPlayer().message("played waits a turn");
            }
            world.update();
        }
        if (gc.getInput().isKeyPressed(Input.KEY_P)) {
            Item pickupItem = world.isItemAt(getPlayer().getPosZ(), getPlayer().getPosX(), getPlayer().getPosY());
            if (pickupItem != null) {
                world.worldInventory.remove(pickupItem);
                getPlayer().getInventry().add(pickupItem);
                world.update();
                world.getPlayer().message("played picked up ");
                world.getPlayer().message(pickupItem.getName());
            }
        }
        if (gc.getInput().isKeyPressed(Input.KEY_D)) {
            getPlayer().drop();
        }
        if (gc.getInput().isKeyPressed(Input.KEY_E)) {
            player.equip();
        }
        if (gc.getInput().isKeyPressed(Input.KEY_U)) {
            player.use();
        }
        if (gc.getInput().isKeyPressed(Input.KEY_I)) {
            showInfo = !showInfo;
        }
        if (gc.getInput().isKeyPressed(Input.KEY_C)) {
            player.craft(craftItem);
        }
        
        if (getPlayer().getHP() < 1) {
            sbg.enterState(3);
        }
        if (world.ifWon()) {
            sbg.enterState(2);
        }
    }
    
    public void renderInfo(Graphics g) {
        g.setColor(Color.yellow);
        g.drawString("Attack Value", (xOffset + 24) * tileSize, (yOffset + 1) * tileSize + tileSize/2);
        g.drawString("Equipped Items", (xOffset + 28) * tileSize, (yOffset + 1) * tileSize + tileSize/2);
        g.drawString("Defence Value", (xOffset + 24) * tileSize, (yOffset + 2) * tileSize + tileSize/2);
        g.drawString("Hit Points", (xOffset + 24) * tileSize, (yOffset + 3) * tileSize + tileSize/2);
        g.drawString("Inventory", (xOffset + 24) * tileSize, (yOffset + 7) * tileSize + tileSize/2);
        g.drawString("Examine", (xOffset + 24) * tileSize, (yOffset + 12) * tileSize + tileSize/2);
        g.setColor(Color.white);
    }

    public int getScrollX() {
        return Math.max(0, Math.min(getPlayer().getPosX() - screenWidthTiles / 2, world.getWidth() - screenWidthTiles));
    }

    public int getScrollY() {
        return Math.max(0, Math.min(getPlayer().getPosY() - screenHeightTiles / 2, world.getHeight() - screenHeightTiles));
    }

    private void createWorld() {
        world = new WorldBuilder(6, 80, 80)
                .makeCaves()
                .build();
    }

    /**
     * @return the player
     */
    public Actor getPlayer() {
        return player;
    }

    /**
     * @return the itemFactory
     */
    public ItemFactory getItemFactory() {
        return itemFactory;
    }
}
