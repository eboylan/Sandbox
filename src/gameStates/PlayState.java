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
import entities.BaseEntity;
import entities.EntityFactory;
import entityStates.IdleState;
import inventory.Potion;
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

    World world;
    Image ground;
    SpriteSheet groundTiles;
    int xOffset;
    int yOffset;
    private BaseEntity player;
    int tileSize;
    int screenWidthTiles;
    int screenHeightTiles;
    boolean showInfo;

    List<String> messages;
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
        //stopRunFrame = 0;
        
        ground = new Image("data/DungeonCrawl_ProjectUtumnoTileset.png");
        
        music = new Music("data/Music/Desolate.wav");
               
        groundTiles = new SpriteSheet(ground, tileSize, tileSize);
        createWorld();
        showInfo = true;
               
        gc.setShowFPS(false);

        entFactory = new EntityFactory(world);
        player = entFactory.newPlayer();
        itemFactory = new ItemFactory(world);
        
        getPlayer().setEquipedArmour(new Armour("Tunic", 36, 21, 0, "Simple tunic"));
        getPlayer().setEquipedWeapon(new Weapon("Dagger", 19, 29, 1, "+1 Attack"));
        getPlayer().setOffHand(new Item("Torch", 57, 18, "Lights up Area"));
       
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
            for (int j = 0; j < 3 + i; j++) {
                entFactory.newGoblin(i, getPlayer());
            }
        }

        entFactory.newManticore(world.getDepth() - 1, getPlayer());

        for (int i = 0; i < world.getDepth(); i++) {
            itemFactory.newLeatherArmour(i);
            itemFactory.newLeatherArmour(i);
            itemFactory.newTunic(i);
            itemFactory.newShield(i);
            itemFactory.newShield(i);
            itemFactory.newShield(i);
            itemFactory.newDagger(i);
            itemFactory.newAxe(i);
            itemFactory.newSword(i);
            itemFactory.newTorch(i);
            itemFactory.newBlackPotion(i);
            itemFactory.newBlackPotion(i);
            itemFactory.newBlackPotion(i);
            itemFactory.newBluePotion(i);
            itemFactory.newBluePotion(i);
            itemFactory.newBluePotion(i);
            itemFactory.newBrownPotion(i);
            itemFactory.newBrownPotion(i);
            itemFactory.newBrownPotion(i);
            if (Math.random() < i / 2f) {
                itemFactory.newLeatherArmour(i);
            }
            if (Math.random() < i / 5f) {
                itemFactory.newChainArmour(i);
            }
            if (Math.random() < i / 10f) {
                itemFactory.newPlateArmour(i);
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
        
        for (BaseEntity be : world.entities) {
            if (getPlayer().canSeeLit(z, be.getPosX(), be.getPosY()) && z == be.getPosZ()) {
                if (!be.getType().equals("player") && !be.getType().equals("goblin")) {
                    groundTiles.getSubImage(be.getImageCol(), be.getImageRow()).draw(tileSize * be.getPosX(), tileSize * be.getPosY());
                }
                if (be.getType().equals("player") || be.getType().equals("goblin")) {
                    //be.render();
                    groundTiles.getSubImage(be.getImageCol(), be.getImageRow()).draw(tileSize * be.getPosX(), tileSize * be.getPosY());
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
        for (BaseEntity be : world.entities) {
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
        if (gc.getInput().isKeyPressed(Input.KEY_ENTER)) {
            sbg.enterState(2);
        }
        if (gc.getInput().isKeyPressed(Input.KEY_RIGHT) || gc.getInput().isKeyPressed(Input.KEY_MULTIPLY)) {
            getPlayer().setSelectX(1);
        }
        if (gc.getInput().isKeyPressed(Input.KEY_LEFT) || gc.getInput().isKeyPressed(Input.KEY_DIVIDE)) {
            getPlayer().setSelectX(-1);
        } 
        if (gc.getInput().isKeyPressed(Input.KEY_UP) || gc.getInput().isKeyPressed(Input.KEY_SUBTRACT)) {
            getPlayer().setSelectY(-1);
        }
        if (gc.getInput().isKeyPressed(Input.KEY_DOWN) || gc.getInput().isKeyPressed(Input.KEY_ADD)) {
            getPlayer().setSelectY(1);
        }
        
        if (true) {//player.isIdle()) {
            
            if(gc.getInput().isKeyPressed(Input.KEY_NUMPAD6)) {
                //
                //player.stopAnim();
                getPlayer().moveBy(0, 1, 0);
                //player.setMove();
                xOffset = getScrollX();
                world.update();
            }
            if (gc.getInput().isKeyPressed(Input.KEY_NUMPAD4)) {
                //player.stopAnim();
                getPlayer().moveBy(0, -1, 0);
                xOffset = getScrollX();
                world.update();
            }
            if (gc.getInput().isKeyPressed(Input.KEY_NUMPAD8)) {
                //player.stopAnim();
                getPlayer().moveBy(0, 0, -1);
                yOffset = getScrollY();
                world.update();
            }
            if (gc.getInput().isKeyPressed(Input.KEY_NUMPAD2)) {
                //player.stopAnim();
                getPlayer().moveBy(0, 0, 1);
                yOffset = getScrollY();
                world.update();
            }
            if (gc.getInput().isKeyPressed(Input.KEY_NUMPAD9)) {
                //player.stopAnim();
                getPlayer().moveBy(0, 1, -1);
                xOffset = getScrollX();
                world.update();
            }
            if (gc.getInput().isKeyPressed(Input.KEY_NUMPAD7)) {
                //player.stopAnim();
                getPlayer().moveBy(0, -1, -1);
                xOffset = getScrollX();
                world.update();
            }
            if (gc.getInput().isKeyPressed(Input.KEY_NUMPAD1)) {
                //player.stopAnim();
                getPlayer().moveBy(0, -1, 1);
                xOffset = getScrollX();
                world.update();
            }
            if (gc.getInput().isKeyPressed(Input.KEY_NUMPAD3)) {
                //player.stopAnim();
                getPlayer().moveBy(0, 1, 1);
                xOffset = getScrollX();
                world.update();
            }
        }
        if (gc.getInput().isKeyPressed(Input.KEY_NUMPAD5)) {
            if (world.tile(getPlayer().getPosZ(), getPlayer().getPosX(), getPlayer().getPosY()) == Tile.STAIRSUP) {
                getPlayer().moveBy(-1, 0, 0);
            } else if (world.tile(getPlayer().getPosZ(), getPlayer().getPosX(), getPlayer().getPosY()) == Tile.STAIRSDOWN) {
                getPlayer().moveBy(1, 0, 0);
            }
            world.update();
        }
        if (gc.getInput().isKeyPressed(Input.KEY_P)) {
            Item pickupItem = world.isItemAt(getPlayer().getPosZ(), getPlayer().getPosX(), getPlayer().getPosY());
            if (pickupItem != null) {
                world.worldInventory.remove(pickupItem);
                getPlayer().getInventry().add(pickupItem);
                world.update();
            }
        }
        if (gc.getInput().isKeyPressed(Input.KEY_D)) {
            getPlayer().drop();
        }
        if (gc.getInput().isKeyPressed(Input.KEY_E)) {
            getPlayer().equip();
        }
        if (gc.getInput().isKeyPressed(Input.KEY_U)) {
            getPlayer().use();
            /*Item useItem = super.getInventry().get((selectY * 5) + selectX);
            if (useItem != null && useItem.getClass().equals(Potion.class)) {
                player.getInventry().remove(useItem);
                Potion p = (Potion) useItem;
                player.addEffect(p.getEffect());
                p.getEffect().setBE(player);
                world.update();
            }*/
        }
        if (gc.getInput().isKeyPressed(Input.KEY_I)) {
            showInfo = !showInfo;
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
        g.drawString("Defence Value", (xOffset + 24) * tileSize, (yOffset + 3) * tileSize + tileSize/2);
        g.drawString("Hit Points", (xOffset + 24) * tileSize, (yOffset + 5) * tileSize + tileSize/2);
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
    public BaseEntity getPlayer() {
        return player;
    }
}
