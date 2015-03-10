/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import Inventry.Item;
import Inventry.ItemFactory;
import entities.BaseEntity;
import entities.EntityFactory;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
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
    FieldOfView fov;

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
                groundTiles.getSubImage(world.getImageCol(z, x, y), world.getImageRow(z, x, y)).draw(x * tileSize, y * tileSize);              
               if(!player.canSee(z, x, y)) {
                    groundTiles.getSubImage(4, 1).draw(x * tileSize, y * tileSize);
                }
            }
        }
        for (int x = xOffset + screenWidthTiles; x < xOffset + screenWidthTiles + 9; x++) {
            for (int y = yOffset; y < yOffset + screenHeightTiles; y++) {
                groundTiles.getSubImage(31, 17).draw(x * tileSize, y  * tileSize);                        
            }
        }
        g.setColor(Color.black);
        g.fillRect((xOffset +  24)* tileSize, (yOffset + 2)  * tileSize, 3 * tileSize, tileSize);
        groundTiles.getSubImage(8, 1).draw((xOffset +  24)* tileSize, (yOffset + 2)  * tileSize);
        groundTiles.getSubImage(42 + (player.getAV()/10), 0).draw((xOffset +  25)* tileSize, (yOffset + 2)  * tileSize);
        groundTiles.getSubImage(42 + (player.getAV()%10), 0).draw((xOffset +  26)* tileSize, (yOffset + 2)  * tileSize);
        g.fillRect((xOffset +  24)* tileSize, (yOffset + 4)  * tileSize, 3 * tileSize, tileSize);
        groundTiles.getSubImage(5, 1).draw((xOffset +  24)* tileSize, (yOffset + 4)  * tileSize);
        groundTiles.getSubImage(42 + (player.getDV()/10), 0).draw((xOffset +  25)* tileSize, (yOffset + 4)  * tileSize);
        groundTiles.getSubImage(42 + (player.getDV()%10), 0).draw((xOffset +  26)* tileSize, (yOffset + 4)  * tileSize);
        g.fillRect((xOffset +  24)* tileSize, (yOffset + 6)  * tileSize, 3 * tileSize, tileSize);
        groundTiles.getSubImage(7, 1).draw((xOffset +  24)* tileSize, (yOffset + 6)  * tileSize);
        groundTiles.getSubImage(42 + (player.getHP()/10), 0).draw((xOffset +  25)* tileSize, (yOffset + 6)  * tileSize);
        groundTiles.getSubImage(42 + (player.getHP()%10), 0).draw((xOffset +  26)* tileSize, (yOffset + 6)  * tileSize);
        
        for(Item i : world.worldInventry) {
            if(player.canSee(z, i.getPosX(), i.getPosY()) && z == i.getPosZ()) {
                groundTiles.getSubImage(i.getImageCol(), i.getImageRow()).draw(tileSize * i.getPosX(), tileSize * i.getPosY());
            }           
        }
        for(BaseEntity be : world.entities) {
            if(player.canSee(z, be.getPosX(), be.getPosY()) && z == be.getPosZ()) {
                groundTiles.getSubImage(be.getImageCol(), be.getImageRow()).draw(tileSize * be.getPosX(), tileSize * be.getPosY());
            }           
        }
        groundTiles.endUse();
        g.setColor(Color.white);
        g.drawString("   Play State", 200 + (tileSize * xOffset), 10 + (tileSize * yOffset));
        g.drawString("Press Escape to lose or Enter to win", 200 + (tileSize * xOffset), 40 + (tileSize * yOffset));
        g.drawString("Entities: " + world.entities.size() + " Items: " + world.worldInventry.size(), 200 + (tileSize * xOffset), 70 + (tileSize * yOffset));
        g.drawString("Player x : " + player.getPosX() + " y: " + player.getPosY() + " z: " + player.getPosZ() ,200 + (tileSize * xOffset), 100 + (tileSize * yOffset));
        for(BaseEntity be : world.entities) {
            if(be.getType() == "manticore") {
                g.drawString("boss : " + be.getPosX() + " y: " + be.getPosY() + " z: " + be.getPosZ() ,200 + (tileSize * xOffset), 130 + (tileSize * yOffset));
            }          
        }
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
