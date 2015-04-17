/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actors;


//import entityEffects.*;
import actorEffects.Effect;
import actors.states.ActorState;
import actors.states.DeathState;
import inventory.Armour;
import inventory.Inventory;
import inventory.Item;
import inventory.Weapon;
import java.util.concurrent.CopyOnWriteArrayList;
import org.newdawn.slick.SlickException;
import util.Point;
import world.Tile;
import world.World;


/**
 *
 * @author Emmet
 */
public class Actor {
    //name
    private String type;
    
    //reference to world object
    private World world;
    private AI ai;
    
    //entities position on grid
    private int posZ;
    private int posX;
    private int posY;
    private int headingX;
    private int headingY;
    private int headingZ;
    
    private long fCount;
    private boolean active = true;
    
    private int attValue;
    private int defValue;
    private int hitPoints;
    private int maxHitPoints;
    private int visionRadius;
    private int food;
    
    //placeholder static graphics, swap later for animations
    private int imageRow;
    private int imageCol;
 
    private Inventory inventory;
    private Item[] equipment;
    private ActorState actState;
    private ActorState[] animationStates;
    private CopyOnWriteArrayList<Effect> status;
    private int facing;
     
    public Actor(String type, World w, int ic, int ir, int av, int dv, int hp, int vr) throws SlickException  {
        this.type = type;
        this.world = w;
        this.imageCol = ic;
        this.imageRow = ir;
        this.attValue = av;
        this.defValue = dv;
        this.hitPoints = hp;
        this.maxHitPoints = hp;
        this.visionRadius = vr;
        this.inventory = new Inventory(20);
        this.equipment = new Item[3];
        animationStates = new ActorState[6];       
        status = new CopyOnWriteArrayList<>();      
        facing = 1;
        fCount = 0;
        food = 40;        
    }
    
    public void draw() {
        actState.draw();
    }

    public void updateAnim() {
        actState.updateAnim();
    }
    
    public String getType() {
        return type;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
    
    public int getPosZ() {
        return posZ;
    }
    
    public Point getPos() {
        return new Point(getPosZ(), getPosX(), getPosY());
    }

    public int getImageRow() {
        return imageRow;
    }

    public int getImageCol() {
        return imageCol;
    }
    
    public boolean isDead() {
        return false;
    }
    
    public void setPos(int z, int x, int y) {
        this.posZ = z;
        this.posX = x;
        this.posY = y;
    }

    public World getWorld() {
        return world;
    }

    public int getAttValue() {
        return attValue;
    }

    public int getDefValue() {
        return defValue;
    }

    public int getHitPoints() {
        if(hitPoints < 1) {
            setState(5);
            if(this.type.equals("player")) {
                active = true;
            }
        }
        return hitPoints;
    }

    public int getFood() {
        return food;
    }

    public int getAnimFCount() {
        return actState.getAnimFCount();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;     
        actState.start();       
    }

    public int getVisionRadius() {
        return visionRadius;
    }
    
    public void update() {
        fCount++;
        if (food > 80) {
            food = 80;
        }
        for(actorEffects.Effect e : status) {
            e.update();
        }
    }
    
    public void moveBy(int z, int x, int y) {
        Actor target = getWorld().isEntityAt(posZ + z, posX + x, posY + y);
        headingZ = z;
        headingX = x;
        headingY = y;
        if (x < 0 && y < 0) {
            setFacing(6);
        }
        
        else if (x > 0 && y < 0) {
            setFacing(4);  
        }
        
        else if (x < 0 && y == 0) {
            setFacing(7);
        }
        
        else if (x > 0 && y == 0) {
            setFacing(3);
        }
        
        else if (x < 0 && y > 0) {
            setFacing(0);
        }
        
        else if (x > 0 && y > 0) {
            setFacing(2);
        }
        
        else if (x == 0 && y > 0) {
            setFacing(1);
        }
        
        else if (x == 0 && y < 0) {
            setFacing(5);
        }
        if(target != null && target != this) {
            setState(1);
            actState.setTarget(target);
        } else if (canDrawPath(posZ + headingZ, posX + headingX, posY + headingY)) {
            getWorld().reserveTile(getWorld().tile(z, x, y), this);
            setState(4);
        } else {
            setState(0);
        }
    }
    
    public void attack(Actor target) {
        int dmg = Math.max(0, getAV() - target.getDV());
        dmg = (int) (Math.random() * dmg + 1);
        if (this.type.equals("player") || target.type.equals("player")) {
            world.getPlayer().message(this.type + " attacks " + target.getType());
            world.getPlayer().message(" for " + dmg + " damage");
        }
        target.modHP(-dmg);
        /*if(target.getAI().getClass().equals(WanderMonsterAI.class) ) {
            target.setAI(new HuntingMonsterAI(target, this));
        }*/
    }

    public void setFacing(int facing) {
        this.facing = facing;
    }
    
    public Tile tile(int z, int x, int y) {
        return getWorld().tile(z, x, y);
    }
    
    public void setAI(AI ai) {
        this.ai = ai;
    }
    
    public AI getAI() {
        return ai;
    }
    
    public boolean canEnter(int z, int x, int y) {
	return getAI().canEnter(z, x, y);
    }
    
    public boolean canSeeDim(int z, int x, int y) {
        return getAI().canSeeDim(z, x, y);
    }
    
    public boolean canSeeLit(int z, int x, int y) {
        return getAI().canSeeLit(z, x, y);
    }
    
    public boolean canDrawPath(int z, int x, int y) {
	return getAI().canDrawPath(z, x, y);	
    }

    public long getFCount() {
        return fCount;
    }
    
    public void removeEffect(Effect e) {
        status.remove(e);
    }
    
    public void addEffect(Effect e) {
        status.add(e);
    }

    public void modFood(int i) {
        food += i;
    }

    public void modHP(int i) {
        hitPoints += i;
        if (hitPoints > maxHitPoints) {
            hitPoints = maxHitPoints;
        }
    }

    public void effectAttack(int i) {
        attValue += i;
        if(attValue < 0) {
            attValue = 0;
        }
    }

    public void effectDefence(int i) {
        defValue += i;
        if(defValue < 0) {
            defValue = 0;
        }
    }

    public void effectHP(int i) {
        hitPoints += i;
    }

    public int getFacing() {
        return facing;
    }

    public int getHeadingX() {
        return headingX;
    }

    public int getHeadingY() {
        return headingY;
    }

    public void addState(int i, ActorState as) {
        animationStates[i] = as;
    }
    
    public void setState(int i) {
        actState = animationStates[i];
    }
    
    public void setEquipedArmour(Armour equipedArmour) {
        this.equipment[0] = equipedArmour;
    }
    
    public void setEquipedWeapon(Weapon equipedWeapon) {
        this.equipment[1] = equipedWeapon;
    }
    
    public void setOffHand(Item offHand) {
        this.equipment[2] = offHand;
    }  
    
    public void inventoryAdd(Item i) {
        inventory.add(i);
    }
    
    public Item getEquipment(int i) {
        return equipment[i];
    }

    public Inventory getInventory() {
        return inventory;
    }
    
    public int getAV() {
        Weapon wpn = (Weapon)equipment[1];
        int av = attValue;
        if (wpn != null) {
            av += wpn.getAttMod();
        }
        return av;
    }

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

    public int getHeadingZ() {
        return headingZ;
    }
    
    public void die() {
        drop();
        world.actors.remove(this);
    }
    
    public void drop() {
        for (int i = 0; i < inventory.length(); i++) {
            Item dropItem = inventory.get(i);
            if (dropItem != null) {
               dropItem.setPos(getPosZ(), getPosX(), getPosY());
               getWorld().worldInventory.add(dropItem);
               inventory.remove(dropItem);
            }
        }
        for (int i = 0; i < equipment.length; i++) {
            Item dropItem = equipment[i];
            if (dropItem != null) {
               dropItem.setPos(getPosZ(), getPosX(), getPosY());
               getWorld().worldInventory.add(dropItem);
            }
        }
    }
}