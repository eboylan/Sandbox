/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import Inventory.Armour;
import Inventory.Inventory;
import Inventory.Item;
import Inventory.Weapon;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;
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
    private Item[] equipment;
    
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
        this.equipment = new Item[3];
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
}
