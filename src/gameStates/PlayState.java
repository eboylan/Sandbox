/*
 * Author: Emmet Boylan
 * Project: Sandbox Warrior
 * File: PlayState.java
 * 
 * Main driver of game. Handles input, rendering, GUI,
 * calls world builder and calls Item and Actor creation
 * Handles turn loop and calls update and animations from entities
 */
package gameStates;


import actors.Actor;
import actors.ActorFactory;
import actors.Player;
import inventory.CraftItem;
import inventory.Item;
import inventory.ItemFactory;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;
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
    private CraftItem craftItem;
    private int tileSize;
    private int screenWidthTiles;
    private int screenHeightTiles;
    boolean showInfo;
    private long frameCount = 0;
    private Music music;
    private ItemFactory itemFactory;
    private ActorFactory actFactory;
    private Player player;
    private boolean debug = false;

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
        
        showInfo = true;

        gc.setShowFPS(false);
        

        music.setVolume(0.4f);
    }

    @Override
    public void enter(GameContainer gc, StateBasedGame sbg) throws SlickException {
        music.loop();
        createWorld();
        
        itemFactory = new ItemFactory();
        
        actFactory = new ActorFactory(world, itemFactory);
        player = actFactory.newPlayer();
        craftItem = new CraftItem(itemFactory);
        
        for (int i = 0; i < world.getDepth(); i++) {
            for (int j = 0; j < world.getWidth() / 10; j++) {
                actFactory.newLizard(i);
            }
        }

        for (int i = 0; i < world.getDepth(); i++) {
            for (int j = 0; j < world.getWidth() / 10 + (2 * i); j++) {
                actFactory.newGoblin(i, player);
            }
        }
        world.actors.remove(player);
        world.setPlayerRef(player);
           
        actFactory.newManticore(world.getDepth() - 1, player);

        world.addDungeonExit(player.getPos());

        for (int i = 0; i < world.getDepth(); i++) {
            for (int numTorch = 0; numTorch < 5; numTorch++) {
                world.putItemInClearTile(itemFactory.newTorch(), i);
            }
            for (int numPotion = 0; numPotion < 10; numPotion++) {
                world.putItemInClearTile(itemFactory.newRandomPotion(), i);
            }
            for (int numWpn = 0; numWpn < 5; numWpn++) {
                world.putItemInClearTile(itemFactory.newRandomWeapon(), i);
            }
            if (Math.random() < i / 2f) {
                world.putItemInClearTile(itemFactory.newLeatherArmour(), i);
            }
            if (Math.random() < i / 3f) {
                world.putItemInClearTile(itemFactory.newChainArmour(), i);
            }
            if (Math.random() < i / 4f) {
                world.putItemInClearTile(itemFactory.newPlateArmour(), i);
            }
        }

        xOffset = getScrollX();
        yOffset = getScrollY();
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        groundTiles.startUse();
        int z = player.getPosZ();
        for (int x = xOffset; x < xOffset + screenWidthTiles; x++) {
            for (int y = yOffset; y < yOffset + screenHeightTiles; y++) {
                if (world.tile(z, x, y) == Tile.STAIRSUP || world.tile(z, x, y) == Tile.STAIRSDOWN) {
                    groundTiles.getSubImage(22, 17).draw(x * tileSize, y * tileSize);
                }
                groundTiles.getSubImage(world.getImageCol(z, x, y), world.getImageRow(z, x, y)).draw(x * tileSize, y * tileSize);
                if (!player.canSeeDim(z, x, y)) {
                    groundTiles.getSubImage(4, 1).draw(x * tileSize, y * tileSize);
                }
                if (player.canSeeDim(z, x, y) && !player.canSeeLit(z, x, y)) {
                    groundTiles.getSubImage(53, 0).draw(x * tileSize, y * tileSize);
                }
            }
        }

        for (Item i : world.worldInventory) {
            if (player.canSeeLit(z, i.getPosX(), i.getPosY()) && z == i.getPosZ()) {
                groundTiles.getSubImage(i.getImageCol(), i.getImageRow()).draw(tileSize * i.getPosX(), tileSize * i.getPosY());
            }
        }

        player.playerUI(g, xOffset, yOffset, groundTiles, tileSize, screenWidthTiles, screenHeightTiles);
        groundTiles.endUse();
        CopyOnWriteArrayList<Actor> drawList = world.actors;
        Collections.reverse(drawList);
        for (Actor a : drawList) {
            if (player.canSeeLit(a.getPosZ(), a.getPosX(), a.getPosY())) {
                a.draw();
                if (showInfo) {
                    g.drawString("" + a.getHitPoints(), tileSize * a.getPosX(), tileSize * a.getPosY());
                }
                if(debug) {
                    groundTiles.getSubImage(a.getImageCol(), a.getImageRow()).draw(tileSize * a.getPosX(), tileSize * a.getPosY());
                }
            }
        }
        player.draw();
        if(debug) {
            groundTiles.getSubImage(player.getImageCol(), player.getImageRow()).draw(tileSize * player.getPosX(), tileSize * player.getPosY());
        }
        
        if(showInfo) {
            renderInfo(g);
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        xOffset = getScrollX();
        yOffset = getScrollY();
        frameCount++;
        if (frameCount % 2 == 0) {

            if (player.isActive()) {
                
                player.updateAnim();
                
            } else {
                
            for (Actor a : world.actors) {
                if (a.isActive()) {
                    if (a.getAnimFCount() == 0) {
                        a.getAI().onUpdate();
                    }
                    if (player.canSeeLit(a.getPosZ(), a.getPosX(), a.getPosY())) {
                        a.updateAnim();
                    } else {
                        a.setActive(false);
                    }
                }
            }
                world.freeReservedTiles();
            }
            
        }
        
        if(frameCount > 60) {
            getInput(gc, sbg);
        }

        
    }

    private void createWorld() {
        int depth = (int)(Math.random() * 5) + 3;
        int width = (int) ((Math.random() * 5) + 5 ) * 10;
        int height = (int) ((Math.random() * 5) + 5 ) * 10;
        world = new WorldBuilder(depth, width, height)
                .makeCaves()
                .build();
    }

    public int getScrollX() {
        return Math.max(0, Math.min(player.getPosX() - screenWidthTiles / 2, world.getWidth() - screenWidthTiles));
    }

    public int getScrollY() {
        return Math.max(0, Math.min(player.getPosY() - screenHeightTiles / 2, world.getHeight() - screenHeightTiles));
    }

    public void updateActors() {
        player.update();
        player.setActive(true);
        for (Actor a : world.actors) {           
            a.setActive(true);
        }
    }

    public boolean ifPlayerTurn() {
        for (Actor a : world.actors) {
            if (a.isActive()) {
                return false;
            }
        }
        return true;
    }
    
    public void renderInfo(Graphics g) {
        g.setColor(Color.yellow);
        g.drawString("Attack Value", (xOffset + 24) * tileSize, (yOffset + 1) * tileSize + tileSize/2);
        g.drawString("Equipped Items", (xOffset + 28) * tileSize, (yOffset + 1) * tileSize + tileSize/2);
        g.drawString("Defence Value", (xOffset + 24) * tileSize, (yOffset + 2) * tileSize + tileSize/2);
        g.drawString("Hit Points", (xOffset + 24) * tileSize, (yOffset + 3) * tileSize + tileSize/2);
        g.drawString("Food", (xOffset + 24) * tileSize, (yOffset + 4) * tileSize + tileSize/2);
        g.drawString("Inventory", (xOffset + 24) * tileSize, (yOffset + 7) * tileSize + tileSize/2);
        g.drawString("Examine", (xOffset + 24) * tileSize, (yOffset + 12) * tileSize + tileSize/2);
        g.setColor(Color.white);
    }

    private void getInput(GameContainer gc, StateBasedGame sbg) {
        if(player.isDead()) {
                sbg.enterState(2);
        }
        
        if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
            System.exit(0);
        }

        if (gc.getInput().isKeyDown(Input.KEY_NUMPAD6) && ifPlayerTurn() && !player.isActive()) {
            gc.getInput().clearKeyPressedRecord();
            player.moveBy(0, 1, 0);
            xOffset = getScrollX();
            yOffset = getScrollY();
            updateActors();
        }
        if (gc.getInput().isKeyDown(Input.KEY_NUMPAD4) && ifPlayerTurn() && !player.isActive()) {
            gc.getInput().clearKeyPressedRecord();
            player.moveBy(0, -1, 0);
            xOffset = getScrollX();
            yOffset = getScrollY();
            updateActors();
        }
        if (gc.getInput().isKeyDown(Input.KEY_NUMPAD8) && ifPlayerTurn() && !player.isActive()) {
            gc.getInput().clearKeyPressedRecord();
            player.moveBy(0, 0, -1);
            xOffset = getScrollX();
            yOffset = getScrollY();
            updateActors();
        }
        if (gc.getInput().isKeyDown(Input.KEY_NUMPAD2) && ifPlayerTurn() && !player.isActive()) {
            gc.getInput().clearKeyPressedRecord();
            player.moveBy(0, 0, 1);
            xOffset = getScrollX();
            yOffset = getScrollY();
            updateActors();
        }
        if (gc.getInput().isKeyDown(Input.KEY_NUMPAD9) && ifPlayerTurn() && !player.isActive()) {
            gc.getInput().clearKeyPressedRecord();
            player.moveBy(0, 1, -1);
            xOffset = getScrollX();
            yOffset = getScrollY();
            updateActors();
        }
        if (gc.getInput().isKeyDown(Input.KEY_NUMPAD7) && ifPlayerTurn() && !player.isActive()) {
            gc.getInput().clearKeyPressedRecord();
            player.moveBy(0, -1, -1);
            xOffset = getScrollX();
            yOffset = getScrollY();
            updateActors();
        }
        if (gc.getInput().isKeyDown(Input.KEY_NUMPAD1) && ifPlayerTurn() && !player.isActive()) {
            gc.getInput().clearKeyPressedRecord();
            player.moveBy(0, -1, 1);
            xOffset = getScrollX();
            yOffset = getScrollY();
            updateActors();
        }
        if (gc.getInput().isKeyDown(Input.KEY_NUMPAD3) && ifPlayerTurn() && !player.isActive()) {
            gc.getInput().clearKeyPressedRecord();
            player.moveBy(0, 1, 1);
            xOffset = getScrollX();
            yOffset = getScrollY();
            updateActors();
        }
        if (gc.getInput().isKeyPressed(Input.KEY_NUMPAD5) && ifPlayerTurn() && !player.isActive()) {
            gc.getInput().clearKeyPressedRecord();

            if (player.getPosZ() == 0 && world.tile(player.getPosZ(), player.getPosX(), player.getPosY()) == Tile.STAIRSUP) {
                if(player.getInventory().hasItem("Skeleton")) {
                    sbg.enterState(4);
                } else {
                    sbg.enterState(3);
                }
            }
            else if (world.tile(player.getPosZ(), player.getPosX(), player.getPosY()) == Tile.STAIRSUP) {
                player.moveBy(-1, 0, 0);
                world.getPlayer().message("player went upstairs");
            } else if (world.tile(player.getPosZ(), player.getPosX(), player.getPosY()) == Tile.STAIRSDOWN) {
                player.moveBy(1, 0, 0);
                world.getPlayer().message("player went downstairs");
            } else {
                player.moveBy(0, 0, 0);
                world.getPlayer().message("player waited a turn");
            }
            xOffset = getScrollX();
            yOffset = getScrollY();
            updateActors();
        }

        if (gc.getInput().isKeyPressed(Input.KEY_RIGHT) || gc.getInput().isKeyPressed(Input.KEY_MULTIPLY)) {
            if (player.isShowCraft()) {
                player.setCraftX(1);
            } else {
                player.setSelectX(1);
            }
        }
        if (gc.getInput().isKeyPressed(Input.KEY_LEFT) || gc.getInput().isKeyPressed(Input.KEY_DIVIDE)) {
            if (player.isShowCraft()) {
                player.setCraftX(-1);
            } else {
                player.setSelectX(-1);
            }
        }
        if (gc.getInput().isKeyPressed(Input.KEY_UP) || gc.getInput().isKeyPressed(Input.KEY_SUBTRACT)) {
            if (player.isShowCraft()) {
                player.setCraftY(-1);
            } else {
                player.setSelectY(-1);
            }
        }
        if (gc.getInput().isKeyPressed(Input.KEY_DOWN) || gc.getInput().isKeyPressed(Input.KEY_ADD)) {
            if (player.isShowCraft()) {
                player.setCraftY(1);
            } else {
                player.setSelectY(1);
            }
        }
        if (gc.getInput().isKeyPressed(Input.KEY_P)) {
            Item pickupItem = world.isItemAt(player.getPosZ(), player.getPosX(), player.getPosY());
            if (pickupItem != null) {
                world.worldInventory.remove(pickupItem);
                player.getInventory().add(pickupItem);
                
                world.getPlayer().message("played picked up ");
                world.getPlayer().message(pickupItem.getName());
            }
            
        }
        if (gc.getInput().isKeyPressed(Input.KEY_D)) {
            player.drop();
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
    }
}
