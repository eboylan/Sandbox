/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actors;

import inventory.Armour;
import inventory.CraftItem;
import inventory.Food;
import inventory.Icon;
import inventory.Item;
import inventory.Potion;
import inventory.Weapon;
import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import world.World;

/**
 *
 * @author Emmet
 */
public class Player extends Actor {

    private int selectX, selectY;
    List<String> messages;
    boolean showCraft;
    private int craftX, craftY;
    

    public Player(String type, World w, int ic, int ir, int av, int dv, int hp, int vr) throws SlickException {
        super(type, w, ic, ir, av, dv, hp, vr);
        selectX = 0;
        selectY = 0;
        craftX = 0;
        craftY = 0;
        messages = new ArrayList<>();
        showCraft = false;
    }

    public void playerUI(Graphics g, int z, int xOffset, int yOffset, SpriteSheet groundTiles, int tileSize, int screenWidthTiles, int screenHeightTiles) {
        for (int x = xOffset + screenWidthTiles; x < xOffset + screenWidthTiles + 9; x++) {
            for (int y = yOffset; y < yOffset + screenHeightTiles; y++) {
                groundTiles.getSubImage(Icon.UIBORDER.getImageCol(), Icon.UIBORDER.getImageRow()).draw(x * tileSize, y * tileSize);
            }
        }
        g.setColor(Color.black);
        g.fillRect((xOffset + 24) * tileSize, (yOffset + 2) * tileSize, 3 * tileSize, 4 * tileSize);
        groundTiles.getSubImage(Icon.ATTACK.getImageCol(), Icon.ATTACK.getImageRow()).draw((xOffset + 24) * tileSize, (yOffset + 2) * tileSize);
        int av = super.getAV();
        if(av < 0) {
            av = 0;
        }
        groundTiles.getSubImage(42 + (av / 10), 0).draw((xOffset + 25) * tileSize, (yOffset + 2) * tileSize);
        groundTiles.getSubImage(42 + (av % 10), 0).draw((xOffset + 26) * tileSize, (yOffset + 2) * tileSize);
        g.fillRect((xOffset + 24) * tileSize, (yOffset + 3) * tileSize, 3 * tileSize, tileSize);
        groundTiles.getSubImage(Icon.DEFENCE.getImageCol(), Icon.DEFENCE.getImageRow()).draw((xOffset + 24) * tileSize, (yOffset + 3) * tileSize);
        int dv = super.getDV();
        if(dv < 0) {
            dv = 0;
        }
        groundTiles.getSubImage(42 + (dv / 10), 0).draw((xOffset + 25) * tileSize, (yOffset + 3) * tileSize);
        groundTiles.getSubImage(42 + (dv % 10), 0).draw((xOffset + 26) * tileSize, (yOffset + 3) * tileSize);
        g.fillRect((xOffset + 24) * tileSize, (yOffset + 4) * tileSize, 3 * tileSize, tileSize);
        groundTiles.getSubImage(Icon.HITPOINTS.getImageCol(), Icon.HITPOINTS.getImageRow()).draw((xOffset + 24) * tileSize, (yOffset + 4) * tileSize);
        int hp = super.getHitPoints();
        if(hp < 0) {
            hp = 0;
        }
        groundTiles.getSubImage(42 + (hp / 10), 0).draw((xOffset + 25) * tileSize, (yOffset + 4) * tileSize);
        groundTiles.getSubImage(42 + (hp % 10), 0).draw((xOffset + 26) * tileSize, (yOffset + 4) * tileSize);
        groundTiles.getSubImage(Icon.FOOD.getImageCol(), Icon.FOOD.getImageRow()).draw((xOffset + 24) * tileSize, (yOffset + 5) * tileSize);
        int fd = super.getFood();
        if(fd < 0) {
            fd = 0;
        }
        groundTiles.getSubImage(42 + (fd / 10), 0).draw((xOffset + 25) * tileSize, (yOffset + 5) * tileSize);
        groundTiles.getSubImage(42 + (fd % 10), 0).draw((xOffset + 26) * tileSize, (yOffset + 5) * tileSize);
        
        
        g.fillRect((xOffset + 28) * tileSize, (yOffset + 2) * tileSize, 2 * tileSize, tileSize);
        groundTiles.getSubImage(Icon.HANDSLOT.getImageCol(), Icon.HANDSLOT.getImageRow()).draw((xOffset + 28) * tileSize, (yOffset + 2) * tileSize);
        groundTiles.getSubImage(super.getEquipment(1).getImageCol(), super.getEquipment(1).getImageRow()).draw((xOffset + 29) * tileSize, (yOffset + 2) * tileSize);
        g.fillRect((xOffset + 28) * tileSize, (yOffset + 3) * tileSize, 2 * tileSize, tileSize);
        groundTiles.getSubImage(Icon.HANDSLOT.getImageCol(), Icon.HANDSLOT.getImageRow()).getFlippedCopy(true, false).draw((xOffset + 28) * tileSize, (yOffset + 3) * tileSize);
        groundTiles.getSubImage(super.getEquipment(2).getImageCol(), super.getEquipment(2).getImageRow()).draw((xOffset + 29) * tileSize, (yOffset + 3) * tileSize);
        
        g.fillRect((xOffset + 28) * tileSize, (yOffset + 4) * tileSize, 2 * tileSize, tileSize);
        groundTiles.getSubImage(Icon.ARMOURSLOT.getImageCol(), Icon.ARMOURSLOT.getImageRow()).draw((xOffset + 28) * tileSize, (yOffset + 4) * tileSize);
        groundTiles.getSubImage(super.getEquipment(0).getImageCol(), super.getEquipment(0).getImageRow()).draw((xOffset + 29) * tileSize, (yOffset + 4) * tileSize);

        g.fillRect((xOffset + 24) * tileSize, (yOffset + 8) * tileSize, tileSize, tileSize);
        groundTiles.getSubImage(Icon.CHEST.getImageCol(), Icon.CHEST.getImageRow()).draw((xOffset + 24) * tileSize, (yOffset + 8) * tileSize);
        g.fillRect((xOffset + 25) * tileSize, (yOffset + 8) * tileSize, 5 * tileSize, 4 * tileSize);
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 5; x++) {
                int i = x + (5 * y);
                if (super.getInventory().get(i) != null) {
                    groundTiles.getSubImage(super.getInventory().get(i).getImageCol(), super.getInventory().get(i).getImageRow()).draw((xOffset + 25 + x) * tileSize, (yOffset + 8 + y) * tileSize);
                }
            }
        }
        groundTiles.getSubImage(Icon.SELECTBOX.getImageCol(), Icon.SELECTBOX.getImageRow()).draw(tileSize * (getSelectX() + xOffset + 25), tileSize * (getSelectY() + yOffset + 8));

        if (showCraft) groundTiles.getSubImage(Icon.CRAFTSELECT.getImageCol(), Icon.CRAFTSELECT.getImageRow()).draw(tileSize * (getCraftX() + xOffset + 25), tileSize * (getCraftY() + yOffset + 8));
        g.fillRect((xOffset + 24) * tileSize, (yOffset + 13) * tileSize, 7 * tileSize, 2 * tileSize);
        
        g.fillRect((xOffset + 24) * tileSize, (yOffset + 16) * tileSize, 7 * tileSize, 6 * tileSize);
        g.setColor(Color.white);       
        
        if (super.getInventory().get(5 * selectY + selectX) != null) {
            g.drawString("" + super.getInventory().get(5 * selectY + selectX).getName(), (xOffset + 24) * tileSize, (yOffset + 13) * tileSize);
            g.drawString("" + super.getInventory().get(5 * selectY + selectX).getDesription(), (xOffset + 24) * tileSize, (yOffset + 14) * tileSize);
            if (super.getInventory().get(5 * selectY + selectX).getClass().equals(Potion.class)) {
                g.drawString("(U)se or (D)rop", (xOffset + 24) * tileSize, (yOffset + 14) * tileSize + tileSize/2);
            } else {
                g.drawString("(E)quip or (D)rop", (xOffset + 24) * tileSize, (yOffset + 14) * tileSize + tileSize/2);
            }
        }
        
        
        for (int i = 0; i < messages.size(); i++) {
            g.drawString("" + messages.get(i), tileSize * (24 + xOffset), tileSize * (yOffset + 16) +  (i * tileSize/2));
        }


    }

    public void setSelectX(int x) {
        selectX += x;
        if (getSelectX() < 0) {
            selectX = 0;
        }
        if (getSelectX() > 4) {
            selectX = 4;
        }
    }

    public void setSelectY(int y) {
        selectY += y;
        if (getSelectY() < 0) {
            selectY = 0;
        }
        if (getSelectY() > 3) {
            selectY = 3;
        }
    }
    
    /**
     * @return the selectX
     */
    public int getSelectX() {
        return selectX;
    }

    /**
     * @return the selectY
     */
    public int getSelectY() {
        return selectY;
    }
    
    public void setCraftX(int x) {
        craftX += x;
        if (getCraftX() < 0) {
            craftX = 0;
        }
        if (getCraftX() > 4) {
            craftX = 4;
        }
    }

    public void setCraftY(int y) {
        craftY += y;
        if (getCraftY() < 0) {
            craftY = 0;
        }
        if (getCraftY() > 3) {
            craftY = 3;
        }
    }

    public int getCraftX() {
        return craftX;
    }

    public int getCraftY() {
        return craftY;
    }
    
    @Override
    public int getVisionRadius() {
        int vr = super.getVisionRadius();
        if (getEquipment(2).getName().equals("Torch")) {
            vr += 3;
        }
        return vr;
    }

    @Override
    public void drop() {
        Item dropItem = super.getInventory().get((selectY * 5) + selectX);
        if (dropItem != null && super.getWorld().isItemAt(super.getPosZ(), super.getPosX(), super.getPosY()) == null) {
            dropItem.setPos(super.getPosZ(), super.getPosX(), super.getPosY());
            super.getWorld().worldInventory.add(dropItem);
            super.getInventory().remove(dropItem);
            //super.getWorld().update();//replace with updateActors
            message("player dropped ");
            message(dropItem.getName());
        }
    }
    
    public void equip() {
        Item equipItem = super.getInventory().get((selectY * 5) + selectX);
        if (equipItem != null) {              
            if (equipItem.getClass().equals(Armour.class)) {
                super.getInventory().remove(equipItem);
                super.getInventory().add(super.getEquipment(0));
                super.setEquipedArmour((Armour)equipItem);
            } else if (equipItem.getClass().equals(Weapon.class)) {
                super.getInventory().remove(equipItem);
                super.getInventory().add(super.getEquipment(1));               
                super.setEquipedWeapon((Weapon)equipItem);
            } else if (equipItem.getClass().equals(Item.class)) {
                super.getInventory().remove(equipItem);
                super.getInventory().add(super.getEquipment(2));          
                super.setOffHand(equipItem);
            }
            message("player equiped ");
            message(equipItem.getName());
        }
    }
    
    public void use() {
        Item useItem = super.getInventory().get((selectY * 5) + selectX);
            if (useItem != null && useItem.getClass().equals(Potion.class)) {
                super.getInventory().remove(useItem);
                Potion p = (Potion) useItem;
                super.addEffect(p.getEffect());
                p.getEffect().setActor(this); 
                message("player used "); 
                message(useItem.getName());
            } else if (useItem != null && useItem.getClass().equals(Food.class)) {
                super.getInventory().remove(useItem);
                Food f = (Food) useItem;
                super.modFood(f.getFoodValue());
                message("player used "); 
                message(useItem.getName());
            }
        
    }
    
    public void message(String m) {
        messages.add(m);
        while(messages.size() > 10) messages.remove(0);
    }
    
    public void setShowCraft(boolean sc) {
        showCraft = sc;
    }
    
    public boolean isShowCraft() {
        return showCraft;
    }

    public void craft(CraftItem craftItem) {
        if(showCraft) {
                Item i1 = getInventory().get((getSelectY() * 5) + getSelectX());
                Item i2 = getInventory().get((getCraftY() * 5) + getCraftX());
                if (i1 == null || i2 == null) {
                    showCraft = false;
                } else {
                   getInventory().remove(i1); 
                   getInventory().remove(i2);
                   Item newItem = craftItem.craft(i1, i2);
                   if(newItem != null) {
                        getInventory().add(craftItem.craft(i1, i2));
                    }
                  setShowCraft(false);
                }
            } else {
                showCraft = true; 
            }
    }
    
    @Override
    public void update() {
        super.update();
    }
}