/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import Inventory.Inventory;
import Inventory.Item;
import util.Point;
import world.Tile;
import world.World;

/**
 *
 * @author Emmet
 */
public class BaseEntity {
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
    
    //placeholder static graphics, swap later for animations
    private int imageRow;
    private int imageCol;
    
    private Inventory inventory;
    
    public BaseEntity(String type, World w, int ic, int ir, int av, int dv, int hp, int vr) {
        this.type = type;
        this.world = w;
        this.imageCol = ic;
        this.imageRow = ir;
        this.attValue = av;
        this.defValue = dv;
        this.hitPoints = hp;
        this.visionRadius = vr;
        this.inventory = new Inventory(20);
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
        BaseEntity target = world.isEntityAt(posZ + z, posX + x, posY + y);
        
        if(target == null) {
            entAI.onEnter(posZ + z, posX + x, posY + y, world.tile(posZ + z, posX + x, posY + y));
        } else {
            attack(target);
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
	return world.tile(z, x, y).isGround() && world.isEntityAt(z, x, y) == null;	
    }
    
    public void modHP(int x) {
        hitPoints += x;
        
        if(hitPoints < 1) {
            if(type == "manticore") world.setWin(true);
            world.remove(this);
        }
    }

    /**
     * @return the attValue
     */
    public int getAV() {
        return attValue;
    }

    /**
     * @return the defValue
     */
    public int getDV() {
        return defValue;
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
    
    public boolean canSee(int z, int x, int y) {
        return entAI.canSee(z, x, y);
    }
    
    public Tile tile(int z, int x, int y) {
        return world.tile(z, x, y);
    }

    /**
     * @return the inventory
     */
    public Inventory getInventry() {
        return inventory;
    }
}
