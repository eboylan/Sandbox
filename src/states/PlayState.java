/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import Inventory.Item;
import Inventory.ItemFactory;
import entities.BaseEntity;
import entities.EntityFactory;
import java.util.ArrayList;
import java.util.List;
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
        ground = new Image("data/DungeonCrawl_ProjectUtumnoTileset.png");
        groundTiles = new SpriteSheet(ground, tileSize, tileSize);
        createWorld();
        messages = new ArrayList<>();
        messages.add("I'm a message box");
        messages.add("I show messages");
        messages.add("this is a message");
        //fov = new FieldOfView(world);

        
        EntityFactory entFactory = new EntityFactory(world);
        player = entFactory.newPlayer();

        for (int i = 0; i < world.getDepth(); i++) {
            for (int j = 0; j < 8 + (2 * i); j++){
                entFactory.newMyconoid(true, i);
            }
        }
        
        for (int i = 0; i < world.getDepth(); i++) {
            for (int j = 0; j < 8 + i; j++){
                entFactory.newLizard(i);
            }
        }
        
        for (int i = 0; i < world.getDepth(); i++) {
            for (int j = 0; j < 3 + i; j++){
                entFactory.newGoblin(i, player);
            }
        }
        
        entFactory.newManticore(world.getDepth() - 1, player);
        
        ItemFactory itemFactory = new ItemFactory(world);
        for (int i = 0; i < world.getDepth(); i++) {
            itemFactory.newLeatherArmour(i);
            itemFactory.newLeatherArmour(i);
            itemFactory.newLeatherArmour(i);
            itemFactory.newLeatherArmour(i);
            itemFactory.newLeatherArmour(i);
            itemFactory.newLeatherArmour(i);
            if(Math.random() < i/2f) {
                itemFactory.newLeatherArmour(i);
            }
            if(Math.random() < i/5f) {
                itemFactory.newChainArmour(i);
            }
            if(Math.random() < i/10f) {
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
                if(world.tile(z, x, y) == Tile.STAIRSUP || world.tile(z, x, y) == Tile.STAIRSDOWN) {
                    groundTiles.getSubImage(22, 17).draw(x * tileSize, y * tileSize);    
                }
                groundTiles.getSubImage(world.getImageCol(z, x, y), world.getImageRow(z, x, y)).draw(x * tileSize, y * tileSize);              
               if(!player.canSee(z, x, y)) {
                    groundTiles.getSubImage(4, 1).draw(x * tileSize, y * tileSize);
                }
            }
        }
        for(Item i : world.worldInventory) {
            if(player.canSee(z, i.getPosX(), i.getPosY()) && z == i.getPosZ()) {
                groundTiles.getSubImage(i.getImageCol(), i.getImageRow()).draw(tileSize * i.getPosX(), tileSize * i.getPosY());
            }           
        }
        for(BaseEntity be : world.entities) {
            if(player.canSee(z, be.getPosX(), be.getPosY()) && z == be.getPosZ()) {
                groundTiles.getSubImage(be.getImageCol(), be.getImageRow()).draw(tileSize * be.getPosX(), tileSize * be.getPosY());
            }           
        }
        player.playerUI(g, z, xOffset, yOffset, groundTiles, tileSize,  screenWidthTiles,  screenHeightTiles);
        //playerUI(g);
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
        if (gc.getInput().isKeyPressed(Input.KEY_RIGHT) || gc.getInput().isKeyPressed(Input.KEY_NUMPAD6)) {
            player.moveBy(0, 1, 0);
            xOffset = getScrollX();
            world.update();
        }
        if (gc.getInput().isKeyPressed(Input.KEY_LEFT) || gc.getInput().isKeyPressed(Input.KEY_NUMPAD4)) {
            player.moveBy(0, -1, 0);
            xOffset = getScrollX();
            world.update();
        }
        if (gc.getInput().isKeyPressed(Input.KEY_UP)  || gc.getInput().isKeyPressed(Input.KEY_NUMPAD8)) {
            player.moveBy(0, 0, -1);
            yOffset = getScrollY();
            world.update();
        }
        if (gc.getInput().isKeyPressed(Input.KEY_DOWN)  || gc.getInput().isKeyPressed(Input.KEY_NUMPAD2)) {
            player.moveBy(0, 0, 1);
            yOffset = getScrollY();
            world.update();
        }
        if (gc.getInput().isKeyPressed(Input.KEY_NUMPAD9)) {
            player.moveBy(0, 1, -1);
            xOffset = getScrollX();
            world.update();
        }
        if (gc.getInput().isKeyPressed(Input.KEY_NUMPAD7)) {
            player.moveBy(0, -1, -1);
            xOffset = getScrollX();
            world.update();
        }
        if (gc.getInput().isKeyPressed(Input.KEY_NUMPAD1)) {
            player.moveBy(0, -1, 1);
            xOffset = getScrollX();
            world.update();
        }
        if (gc.getInput().isKeyPressed(Input.KEY_NUMPAD3)) {
            player.moveBy(0, 1, 1);
            xOffset = getScrollX();
            world.update();
        }
        if (gc.getInput().isKeyPressed(Input.KEY_NUMPAD5)) {
            if (world.tile(player.getPosZ(), player.getPosX(), player.getPosY()) == Tile.STAIRSUP) {
                player.moveBy(-1, 0, 0);
            } else if (world.tile(player.getPosZ(), player.getPosX(), player.getPosY()) == Tile.STAIRSDOWN){
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
            Item dropItem = player.getInventry().get(0);
            if (dropItem != null && world.isItemAt(player.getPosZ(), player.getPosX(), player.getPosY()) == null) {
                dropItem.setPos(player.getPosZ(), player.getPosX(), player.getPosY());
                world.worldInventory.add(dropItem);
                player.getInventry().remove(dropItem);
                world.update();
            }
        }
        if (gc.getInput().isKeyPressed(Input.KEY_I)) {
            sbg.enterState(4);
        }
        if (gc.getInput().isKeyPressed(Input.KEY_M)) {
            sbg.enterState(5);
        }
        if (player.getHP() < 1) {
            sbg.enterState(3);
        }
        if (world.ifWon()) {
            sbg.enterState(2);
        }
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
