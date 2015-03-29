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
import entityStates.idleState;
import java.util.List;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
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
    BaseEntity player;
    int tileSize;
    int screenWidthTiles;
    int screenHeightTiles;
    boolean showInfo;


    
    List<String> messages;
    
    
    //FieldOfView fov;

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
        
        groundTiles = new SpriteSheet(ground, tileSize, tileSize);
        createWorld();
        showInfo = true;
        
        /*
        messages = new ArrayList<>();
        messages.add("I'm a message box");
        messages.add("I show messages");
        messages.add("this is a message");
        * */
        gc.setShowFPS(false);
        


        EntityFactory entFactory = new EntityFactory(world);
        player = entFactory.newPlayer();
        ItemFactory itemFactory = new ItemFactory(world);
        
        player.setEquipedArmour(new Armour("Tunic", 36, 21, 0, "Simple tunic"));
        player.setEquipedWeapon(new Weapon("Dagger", 19, 29, 1, "+1 Attack"));
        player.setOffHand(new Item("Torch", 57, 18, "Lights up Area"));

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
                entFactory.newGoblin(i, player);
            }
        }

        entFactory.newManticore(world.getDepth() - 1, player);

        
        for (int i = 0; i < world.getDepth(); i++) {
            itemFactory.newLeatherArmour(i);
            itemFactory.newLeatherArmour(i);
            itemFactory.newLeatherArmour(i);
            itemFactory.newLeatherArmour(i);
            itemFactory.newLeatherArmour(i);
            itemFactory.newLeatherArmour(i);
            itemFactory.newTunic(i);
            itemFactory.newTunic(i);
            itemFactory.newTunic(i);
            itemFactory.newTunic(i);
            itemFactory.newTunic(i);
            itemFactory.newShield(i);
            itemFactory.newShield(i);
            itemFactory.newShield(i);
            itemFactory.newShield(i);
            itemFactory.newShield(i);
            itemFactory.newShield(i);
            itemFactory.newDagger(i);
            itemFactory.newDagger(i);
            itemFactory.newDagger(i);
            itemFactory.newDagger(i);
            itemFactory.newDagger(i);
            itemFactory.newAxe(i);
            itemFactory.newAxe(i);
            itemFactory.newAxe(i);
            itemFactory.newAxe(i);
            itemFactory.newAxe(i);
            itemFactory.newSword(i);
            itemFactory.newSword(i);
            itemFactory.newSword(i);
            itemFactory.newSword(i);
            itemFactory.newSword(i);
            itemFactory.newTorch(i);
            itemFactory.newTorch(i);
            itemFactory.newTorch(i);
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

        xOffset = getScrollX();
        yOffset = getScrollY();
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        int z = player.getPosZ();
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
                if (!player.getEquipment(2).getName().equals("Torch") && player.canSeeDim(z, x, y) && !player.canSeeLit(z, x, y)) {
                    groundTiles.getSubImage(53, 0).draw(x * tileSize, y * tileSize);
                }
            }
        }
        for (Item i : world.worldInventory) {
            if (player.canSeeLit(z, i.getPosX(), i.getPosY()) && z == i.getPosZ()) {
                groundTiles.getSubImage(i.getImageCol(), i.getImageRow()).draw(tileSize * i.getPosX(), tileSize * i.getPosY());
            }
            if (player.getEquipment(2).getName().equals("Torch") && player.canSeeDim(z, i.getPosX(), i.getPosY()) && !player.canSeeLit(z, i.getPosX(), i.getPosY()) && z == i.getPosZ()) {
                groundTiles.getSubImage(i.getImageCol(), i.getImageRow()).draw(tileSize * i.getPosX(), tileSize * i.getPosY());
            }
        }
        for (BaseEntity be : world.entities) {
            if (player.canSeeLit(z, be.getPosX(), be.getPosY()) && z == be.getPosZ()) {
                groundTiles.getSubImage(be.getImageCol(), be.getImageRow()).draw(tileSize * be.getPosX(), tileSize * be.getPosY());
                if (be.getType().equals("player") || be.getType().equals("goblin")) {
                    be.render();
                }
            }
        }
        player.playerUI(g, z, xOffset, yOffset, groundTiles, tileSize, screenWidthTiles, screenHeightTiles);
        
        g.setColor(Color.white);
        g.drawString("Play State FPS: " + gc.getFPS(), tileSize * (24 + xOffset), (yOffset + 16) * tileSize);
        g.drawString("Press Escape to lose", tileSize * (24 + xOffset), (yOffset + 17) * tileSize);
        g.drawString("Press Enter to win", tileSize * (24 + xOffset), (yOffset + 18) * tileSize);
        g.drawString("Entities: " + world.entities.size() + " Items: " + world.worldInventory.size(), tileSize * (24 + xOffset), (yOffset + 19) * tileSize);
        g.drawString("Player x : " + player.getPosX() + " y: " + player.getPosY() + " z: " + player.getPosZ(), tileSize * (24 + xOffset), (yOffset + 20) * tileSize);
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
        if (gc.getInput().isKeyPressed(Input.KEY_RIGHT)) {
            player.setSelectX(1);
        }
        if (gc.getInput().isKeyPressed(Input.KEY_LEFT)) {
            player.setSelectX(-1);
        } 
        if (gc.getInput().isKeyPressed(Input.KEY_UP)) {
            player.setSelectY(-1);
        }
        if (gc.getInput().isKeyPressed(Input.KEY_DOWN)) {
            player.setSelectY(1);
        }
        
        if (player.isIdle()) {
            
            if(gc.getInput().isKeyDown(Input.KEY_NUMPAD6)) {
                player.stopAnim();
                player.moveBy(0, 1, 0);
                xOffset = getScrollX();
                world.update();
            }
            if (gc.getInput().isKeyDown(Input.KEY_NUMPAD4)) {
                player.stopAnim();
                player.moveBy(0, -1, 0);
                xOffset = getScrollX();
                world.update();
            }
            if (gc.getInput().isKeyDown(Input.KEY_NUMPAD8)) {
                player.stopAnim();
                player.moveBy(0, 0, -1);
                yOffset = getScrollY();
                world.update();
            }
            if (gc.getInput().isKeyDown(Input.KEY_NUMPAD2)) {
                player.stopAnim();
                player.moveBy(0, 0, 1);
                yOffset = getScrollY();
                world.update();
            }
            if (gc.getInput().isKeyDown(Input.KEY_NUMPAD9)) {
                player.stopAnim();
                player.moveBy(0, 1, -1);
                xOffset = getScrollX();
                world.update();
            }
            if (gc.getInput().isKeyDown(Input.KEY_NUMPAD7)) {
                player.stopAnim();
                player.moveBy(0, -1, -1);
                xOffset = getScrollX();
                world.update();
            }
            if (gc.getInput().isKeyDown(Input.KEY_NUMPAD1)) {
                player.stopAnim();
                player.moveBy(0, -1, 1);
                xOffset = getScrollX();
                world.update();
            }
            if (gc.getInput().isKeyDown(Input.KEY_NUMPAD3)) {
                player.stopAnim();
                player.moveBy(0, 1, 1);
                xOffset = getScrollX();
                world.update();
            }
        }
        if (gc.getInput().isKeyPressed(Input.KEY_NUMPAD5)) {
            if (world.tile(player.getPosZ(), player.getPosX(), player.getPosY()) == Tile.STAIRSUP) {
                player.moveBy(-1, 0, 0);
            } else if (world.tile(player.getPosZ(), player.getPosX(), player.getPosY()) == Tile.STAIRSDOWN) {
                player.moveBy(1, 0, 0);
            }
            world.update();
        }
        if (gc.getInput().isKeyPressed(Input.KEY_P)) {
            Item pickupItem = world.isItemAt(player.getPosZ(), player.getPosX(), player.getPosY());
            if (pickupItem != null) {
                world.worldInventory.remove(pickupItem);
                player.getInventry().add(pickupItem);
                world.update();
            }
        }
        if (gc.getInput().isKeyPressed(Input.KEY_D)) {
            player.drop();
        }
        if (gc.getInput().isKeyPressed(Input.KEY_E)) {
            player.equip();
        }
        if (gc.getInput().isKeyPressed(Input.KEY_I)) {
            showInfo = !showInfo;
        }
        if (gc.getInput().isKeyPressed(Input.KEY_M)) {
            //sbg.enterState(5);
        }
        if (player.getHP() < 1) {
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
        return Math.max(0, Math.min(player.getPosX() - screenWidthTiles / 2, world.getWidth() - screenWidthTiles));
    }

    public int getScrollY() {
        return Math.max(0, Math.min(player.getPosY() - screenHeightTiles / 2, world.getHeight() - screenHeightTiles));
    }

    private void createWorld() {
        world = new WorldBuilder(6, 80, 80)
                .makeCaves()
                .build();
    }
}
