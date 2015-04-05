/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;


import entityEffects.*;
import inventory.Inventory;
import inventory.Item;
import inventory.Weapon;
import entityStates.*;
import inventory.Armour;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
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
public class BaseEntity implements EntityState, Comparable<BaseEntity> {
    //name
    private String type;
    
    //reference to world object
    private World world;
    private EntityAI entAI;
    
    //entities position on grid
    private int posZ;
    private int posX;
    private int posY;
    
    private long fCount;
    
    private int attValue;
    private int defValue;
    private int hitPoints;
    private int visionRadius;
    private int food;
    
    private static final int tileSize = 32;
    
    //placeholder static graphics, swap later for animations
    private int imageRow;
    private int imageCol;
    
    private boolean alive = true;
    
    private Inventory inventory;
    private Item[] equipment;
    private EntityState entState;
    private EntityState[] animationStates;
    private CopyOnWriteArrayList<Effect> status;
    
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
    private final SpriteSheet deathSheet;
    private final Animation die;
    
    
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
        animationStates = new EntityState[6];
        
        status = new CopyOnWriteArrayList<>();
        status.add(new Hunger(this, -1));
        status.add(new Regenerate(this, -1));
        
        facing = 1;
        fCount = 0;
        food = 40;
        
        if(type.equals("player")){
            runSheet = new SpriteSheet("data/Hero/HeroRun.png", 140, 140);
            att1Sheet = new SpriteSheet("data/Hero/HeroAttackA.png", 140, 140);
            att2Sheet = new SpriteSheet("data/Hero/HeroAttackB.png", 140, 140);
            att3Sheet = new SpriteSheet("data/Hero/HeroAttackC.png", 140, 140);
            idleSheet = new SpriteSheet("data/Hero/HeroFidget.png", 140, 140);
            deathSheet = new SpriteSheet("data/Hero/HeroDeath.png", 140, 140);
            run = new Animation(runSheet, 45);
            att1 = new Animation(att1Sheet, 90);
            att2 = new Animation(att2Sheet, 90);
            att3 = new Animation(att3Sheet, 90);
            idle = new Animation(idleSheet, 90);
            die = new Animation(deathSheet, 90);
            animationStates[0] = new IdleState(this, idle, 30);
            animationStates[1] = new AttackState(this, att1, 15);
            animationStates[2] = new AttackState(this, att2, 15);
            animationStates[3] = new AttackState(this, att3, 15);
            animationStates[4] = new MoveState(this, run, 15);
            animationStates[5] = new DeathState(this, die, 15);
            this.entState = animationStates[0];
            
        } else {
            runSheet = new SpriteSheet("data/Goblin/GoblinRun.png", 120, 120);
            att1Sheet = new SpriteSheet("data/Goblin/GoblinAttack.png", 120, 120);
            att2Sheet = new SpriteSheet("data/Goblin/GoblinAttack.png", 120, 120);
            att3Sheet = new SpriteSheet("data/Goblin/GoblinAttack.png", 120, 120);
            idleSheet = new SpriteSheet("data/Goblin/GoblinFidget.png", 120, 120);
            deathSheet = new SpriteSheet("data/Goblin/GoblinDeath.png", 140, 140);
            run = new Animation(runSheet, 90);
            att1 = new Animation(att1Sheet, 90);
            att2 = new Animation(att2Sheet, 90);
            att3 = new Animation(att3Sheet, 90);
            idle = new Animation(idleSheet, 90);
            die = new Animation(deathSheet, 90);
            animationStates[0] = new IdleState(this, idle, 15);
            animationStates[1] = new AttackState(this, att1, 15);
            animationStates[2] = new AttackState(this, att2, 15);
            animationStates[3] = new AttackState(this, att3, 15);
            animationStates[4] = new MoveState(this, run, 10);
            animationStates[5] = new DeathState(this, die, 10);
            this.entState = animationStates[0];
        }
        
        
       
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
        
        if(target == null || !target.isAlive()) {
            
            if(canEnter(posZ + z, posX + x, posY + y)) {
                getEntAI().onEnter(posZ + z, posX + x, posY + y, getWorld().tile(posZ + z, posX + x, posY + y));
                setMove();
            } else {
                setIdle();
            }
            
            
        } else {
            attack(target);
            //setAttack(target);
        }
    }
    
    public void attack(BaseEntity target) {
        int dmg = Math.max(0, getAV() - target.getDV());
        dmg = (int) (Math.random() * dmg + 1); 
        
        target.modHP(-dmg);
    }
    
    public void update() {
        fCount++;
        getEntAI().onUpdate();
        for(Effect e : status) {
            e.update();
        }
    }

    public boolean canEnter(int z, int x, int y) {
	return getWorld().tile(z, x, y).isGround() && getWorld().isEntityAt(z, x, y) == null;	
    }
    
    public void modHP(int x) {
        hitPoints += x;
        
        if(hitPoints < 1 && getEntState() != animationStates[5]) {
            if(type == "manticore") getWorld().setWin(true);
            //getWorld().remove(this);
            if(type.equals("player")) {
                //entState = animationStates[5];
                //start();
            } else {
                getWorld().remove(this);
            }
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
        int vr = visionRadius;
        return vr;
    }
    
    public boolean canSeeDim(int z, int x, int y) {
        return getEntAI().canSeeDim(z, x, y);
    }
    
    public boolean canSeeLit(int z, int x, int y) {
        return getEntAI().canSeeLit(z, x, y);
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
        getEntState().render();
        //run.stopAt(stopRunFrame);
        //run.draw(tileSize * this.getPosX() - (70 - 16), tileSize * this.getPosY() - (70 + 32));
    }


    public void setIdle() {
        entState = animationStates[0]; 
        start();
    }

    public void setMove() {
        entState = animationStates[4]; 
        start();
    }

    public void setAttack(BaseEntity target) {
        int x = (int)Math.random() * 3;
        entState = animationStates[x];
        entState.setTarget(target);
        start();
    }

    /**
     * @return the facing
     */
    public int getFacing() {
        return facing;
    }

    /**
     * @param facing the facing to set
     */
    public void setFacing(int facing) {
        this.facing = facing;
    }

    /**
     * @return the entState
     */
    public EntityState getEntState() {
        return entState;
    }

    /**
     * @param entState the entState to set
     */
    public void setEntState(EntityState entState) {
        this.entState = entState;
    }
    
    public boolean isIdle() {
        return entState.isIdle();
    }
    
    public void stopAnim() {
        entState.stopAnim();
    }
    
    public static int compareY() {
        return 1;
    }

    /**
     * @return the entAI
     */
    public EntityAI getEntAI() {
        return entAI;
    }

    @Override
    public int compareTo(BaseEntity be) {
        int y = be.getPosY();
        return y - this.posY;
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void start() {
        entState.start();
    }

    public void kill() {
        entState = animationStates[5];
        start();
        //2world.entities.remove(this);
    }
    
    public boolean isAlive() {
        return alive;
    }

    @Override
    public void setTarget(BaseEntity target) {
        entState.setTarget(target);
    }

    /**
     * @return the food
     */
    public int getFood() {
        return food;
    }

    /**
     * @param food the food to set
     */
    public void modFood(int c) {
        this.food += c;
    }

    public long getFcount() {
        return fCount;
    }

    public void removeEffect(Effect e) {
        status.remove(e);
    }
    
    public void addEffect(Effect e) {
        status.add(e);
    }

    public void effectAttack(int mod) {
        this.attValue += mod;
    }

    public void effectDefence(int mod) {
        this.defValue += mod;
    }

    public void effectHP(int mod) {
        this.hitPoints += mod;
    }

    public void use() {
        //leave for player
    }
}
