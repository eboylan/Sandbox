/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import inventory.Armour;
import inventory.Inventory;
import inventory.Item;
import inventory.Weapon;
import entityStates.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import util.Point;
import world.Tile;
import world.World;

/**
 *
 * @author Emmet
 */
public class BaseEntity implements entityState {
    //name
    private String type;
    
    //reference to world object
    private World world;
    private EntityAI entAI;
    
    //entities position on grid
    private int posZ;
    private int posX;
    private int posY;
    
    private int attValue;
    private int defValue;
    private int hitPoints;
    private int visionRadius;
    
    private static final int tileSize = 32;
    
    //placeholder static graphics, swap later for animations
    private int imageRow;
    private int imageCol;
    
    private Inventory inventory;
    private Item[] equipment;
    private entityState entState;
    
    private SpriteSheet runSheet;
    private Animation run;
    private SpriteSheet att1Sheet;
    private SpriteSheet att2Sheet;
    private SpriteSheet att3Sheet;
    private Animation att1;
    private Animation att2;
    private Animation att3;
    //private int stopRunFrame;
    private int facing;
    private final SpriteSheet idleSheet;
    private final Animation idle;
    
    
    public BaseEntity(String type, World w, int ic, int ir, int av, int dv, int hp, int vr) throws SlickException {
        this.type = type;
        this.world = w;
        this.imageCol = ic;
        this.imageRow = ir;
        this.attValue = av;
        this.defValue = dv;
        this.hitPoints = hp;
        this.visionRadius = vr;
        this.inventory = new Inventory(20);
        this.equipment = new Item[3];
        
        runSheet = new SpriteSheet("data/Hero/HeroRun.png", 140, 140);
        att1Sheet = new SpriteSheet("data/Hero/HeroAttackA.png", 140, 140);
        att2Sheet = new SpriteSheet("data/Hero/HeroAttackB.png", 140, 140);
        att3Sheet = new SpriteSheet("data/Hero/HeroAttackC.png", 140, 140);
        idleSheet = new SpriteSheet("data/Hero/HeroFidget.png", 140, 140);
        run = new Animation(runSheet, 90);
        att1 = new Animation(att1Sheet, 90);
        att2 = new Animation(att2Sheet, 90);
        att3 = new Animation(att3Sheet, 90);
        idle = new Animation(idleSheet, 90);
        
        this.entState = new idleState(this, idle, 29);
        //stopRunFrame = 14;
    }

    public String getType() {
        return type;
    }
    /**
     * @return the PosX
     */
    public int getPosX() {
        return posX;
    }

    /**
     * @return the PosY
     */
    public int getPosY() {
        return posY;
    }
    
    public int getPosZ() {
        return posZ;
    }
    
    public Point getPos() {
        return new Point(posZ, posX, posY);
    }

    /**
     * @return the imageRow
     */
    public int getImageRow() {
        return imageRow;
    }

    /**
     * @return the imageCol
     */
    public int getImageCol() {
        return imageCol;
    }
    
    public void setPos(int z, int x, int y) {
        this.posZ = z;
        this.posX = x;
        this.posY = y;
    }
    
    //set by setter injection
    public void setEntAI(EntityAI entAI) {
        this.entAI = entAI;
    }
    
    public void moveBy(int z, int x, int y) {
        BaseEntity target = getWorld().isEntityAt(posZ + z, posX + x, posY + y);
        
        if(target == null) {
            entAI.onEnter(posZ + z, posX + x, posY + y, getWorld().tile(posZ + z, posX + x, posY + y));
            setMove();
        } else {
            attack(target);
            setAttack();
        }
    }
    
    public void attack(BaseEntity target) {
        int dmg = Math.max(0, getAV() - target.getDV());
        dmg = (int) (Math.random() * dmg + 1); 
        
        target.modHP(-dmg);
    }
    
    public void update() {
        entAI.onUpdate();
    }

    public boolean canEnter(int z, int x, int y) {
	return getWorld().tile(z, x, y).isGround() && getWorld().isEntityAt(z, x, y) == null;	
    }
    
    public void modHP(int x) {
        hitPoints += x;
        
        if(hitPoints < 1) {
            if(type == "manticore") getWorld().setWin(true);
            getWorld().remove(this);
        }
    }

    /**
     * @return the attValue
     */
    public int getAV() {
        Weapon wpn = (Weapon)equipment[1];
        int av = attValue;
        if (wpn != null) {
            av += wpn.getAttMod();
        }
        return av;
    }

    /**
     * @return the defValue
     */
    public int getDV() {
        Armour arm = (Armour)equipment[0];
        int dv = defValue;
        if (arm != null) {
            dv += arm.getDefMod();
        }
        Item oh = equipment[2];
        if (oh != null && "Shield".equals(oh.getName())) {
            dv++;
        }
        return dv;
    }

    /**
     * @return the hitPoints
     */
    public int getHP() {
        return hitPoints;
    }

    /**
     * @return the visionRadius
     */
    public int getVisionRadius() {
        return visionRadius;
    }
    
    public boolean canSeeDim(int z, int x, int y) {
        return entAI.canSeeDim(z, x, y);
    }
    
    public boolean canSeeLit(int z, int x, int y) {
        return entAI.canSeeLit(z, x, y);
    }
    
    public Tile tile(int z, int x, int y) {
        return getWorld().tile(z, x, y);
    }

    /**
     * @return the inventory
     */
    public Inventory getInventry() {
        return inventory;
    }

    /**
     * @return the world
     */
    public World getWorld() {
        return world;
    }

    public void playerUI(Graphics g, int z, int xOffset, int yOffset, SpriteSheet groundTiles, int tileSize, int screenWidthTiles, int screenHeightTiles) {
        
    }

    /**
     * @return the equipedArmour
     */
    public Item getEquipment(int i) {
        return equipment[i];
    }
    
    

    /**
     * @param equipedArmour the equipedArmour to set
     */
    public void setEquipedArmour(Armour equipedArmour) {
        this.equipment[0] = equipedArmour;
    }
    
    public void setEquipedWeapon(Weapon equipedWeapon) {
        this.equipment[1] = equipedWeapon;
    }
    
    public void setOffHand(Item offHand) {
        this.equipment[2] = offHand;
    }

    public void setSelectX(int i) {
        //leave for player
    }

    public void setSelectY(int i) {
        //leave for player
    }

    public void drop() {
        //leave for player
    }

    public void equip() {
       //leave for player
    }

    @Override
    public void render() {
        entState.render();
        //run.stopAt(stopRunFrame);
        //run.draw(tileSize * this.getPosX() - (70 - 16), tileSize * this.getPosY() - (70 + 32));
    }


    public void setIdle() {
        entState = new idleState(this, idle, 29);
    }

    private void setMove() {
        entState = new moveState(this, run, 14);
    }

    private void setAttack() {
        entState = new moveState(this, att1, 14);
    }
}
