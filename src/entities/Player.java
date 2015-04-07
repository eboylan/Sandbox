/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import inventory.Armour;
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
public class Player extends BaseEntity {

    private int selectX;
    private int selectY;
    List<String> messages;

    public Player(String type, World w, int ic, int ir, int av, int dv, int hp, int vr) throws SlickException {
        super(type, w, ic, ir, av, dv, hp, vr);
        selectX = 0;
        selectY = 0;
        messages = new ArrayList<>();
        messages.add("I'm a message box");
        messages.add("I show messages");
        messages.add("this is a message");
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
        groundTiles.getSubImage(42 + (super.getAV() / 10), 0).draw((xOffset + 25) * tileSize, (yOffset + 2) * tileSize);
        groundTiles.getSubImage(42 + (super.getAV() % 10), 0).draw((xOffset + 26) * tileSize, (yOffset + 2) * tileSize);
        //g.fillRect((xOffset + 24) * tileSize, (yOffset + 3) * tileSize, 3 * tileSize, tileSize);
        groundTiles.getSubImage(Icon.DEFENCE.getImageCol(), Icon.DEFENCE.getImageRow()).draw((xOffset + 24) * tileSize, (yOffset + 3) * tileSize);
        groundTiles.getSubImage(42 + (super.getDV() / 10), 0).draw((xOffset + 25) * tileSize, (yOffset + 3) * tileSize);
        groundTiles.getSubImage(42 + (super.getDV() % 10), 0).draw((xOffset + 26) * tileSize, (yOffset + 3) * tileSize);
        //g.fillRect((xOffset + 24) * tileSize, (yOffset + 4) * tileSize, 3 * tileSize, tileSize);
        groundTiles.getSubImage(Icon.HITPOINTS.getImageCol(), Icon.HITPOINTS.getImageRow()).draw((xOffset + 24) * tileSize, (yOffset + 4) * tileSize);
        groundTiles.getSubImage(42 + (super.getHP() / 10), 0).draw((xOffset + 25) * tileSize, (yOffset + 4) * tileSize);
        groundTiles.getSubImage(42 + (super.getHP() % 10), 0).draw((xOffset + 26) * tileSize, (yOffset + 4) * tileSize);
        groundTiles.getSubImage(Icon.FOOD.getImageCol(), Icon.FOOD.getImageRow()).draw((xOffset + 24) * tileSize, (yOffset + 5) * tileSize);
        groundTiles.getSubImage(42 + (super.getFood() / 10), 0).draw((xOffset + 25) * tileSize, (yOffset + 5) * tileSize);
        groundTiles.getSubImage(42 + (super.getFood() % 10), 0).draw((xOffset + 26) * tileSize, (yOffset + 5) * tileSize);
        
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
                if (super.getInventry().get(i) != null) {
                    groundTiles.getSubImage(super.getInventry().get(i).getImageCol(), super.getInventry().get(i).getImageRow()).draw((xOffset + 25 + x) * tileSize, (yOffset + 8 + y) * tileSize);
                }
            }
        }
        groundTiles.getSubImage(Icon.SELECTBOX.getImageCol(), Icon.SELECTBOX.getImageRow()).draw(tileSize * (getSelectX() + xOffset + 25), tileSize * (getSelectY() + yOffset + 8));

        g.fillRect((xOffset + 24) * tileSize, (yOffset + 13) * tileSize, 7 * tileSize, 2 * tileSize);
        
        g.fillRect((xOffset + 24) * tileSize, (yOffset + 16) * tileSize, 7 * tileSize, 6 * tileSize);
        g.setColor(Color.white);
        if (super.getInventry().get(5 * selectY + selectX) != null) {
            g.drawString("" + super.getInventry().get(5 * selectY + selectX).getName(), (xOffset + 24) * tileSize, (yOffset + 13) * tileSize);
            g.drawString("" + super.getInventry().get(5 * selectY + selectX).getDesription(), (xOffset + 24) * tileSize, (yOffset + 14) * tileSize);
            if (super.getInventry().get(5 * selectY + selectX).getClass().equals(Potion.class)) {
                g.drawString("(U)se or (D)rop", (xOffset + 24) * tileSize, (yOffset + 14) * tileSize + tileSize/2);
            } else {
                g.drawString("(E)quip or (D)rop", (xOffset + 24) * tileSize, (yOffset + 14) * tileSize + tileSize/2);
            }
        }
        
        for (int i = 0; i < messages.size(); i++) {
            g.drawString("" + messages.get(i), tileSize * (24 + xOffset), tileSize * (yOffset + 16 + i));
        }


    }

    /**
     * @param selectX the selectX to set
     */
    @Override
    public void setSelectX(int x) {
        selectX += x;
        if (getSelectX() < 0) {
            selectX = 0;
        }
        if (getSelectX() > 4) {
            selectX = 4;
        }
    }

    /**
     * @param selectY the selectY to set
     */
    @Override
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
    
    @Override
    public int getVisionRadius() {
        int vr = super.getVisionRadius();
        if (getEquipment(2).getName().equals("Torch")) vr += 3;
        return vr;
    }

    public void drop() {
        Item dropItem = super.getInventry().get((selectY * 5) + selectX);
        if (dropItem != null && super.getWorld().isItemAt(super.getPosZ(), super.getPosX(), super.getPosY()) == null) {
            dropItem.setPos(super.getPosZ(), super.getPosX(), super.getPosY());
            super.getWorld().worldInventory.add(dropItem);
            super.getInventry().remove(dropItem);
            super.getWorld().update();
            message("player dropped " + System.getProperty("line.separator") + dropItem);
        }
    }
    
    public void equip() {
        Item equipItem = super.getInventry().get((selectY * 5) + selectX);
        if (equipItem != null) {              
            if (equipItem.getClass().equals(Armour.class)) {
                super.getInventry().remove(equipItem);
                super.getInventry().add(super.getEquipment(0));
                super.setEquipedArmour((Armour)equipItem);
            } else if (equipItem.getClass().equals(Weapon.class)) {
                super.getInventry().remove(equipItem);
                super.getInventry().add(super.getEquipment(1));               
                super.setEquipedWeapon((Weapon)equipItem);
            } else if (equipItem.getClass().equals(Item.class)) {
                super.getInventry().remove(equipItem);
                super.getInventry().add(super.getEquipment(2));          
                super.setOffHand(equipItem);
            }
            message("player equiped " + System.getProperty("line.separator") + equipItem.getName());
        }
    }
    
    public void use() {
        Item useItem = super.getInventry().get((selectY * 5) + selectX);
            if (useItem != null && useItem.getClass().equals(Potion.class)) {
                super.getInventry().remove(useItem);
                Potion p = (Potion) useItem;
                super.addEffect(p.getEffect());
                p.getEffect().setBE(this); 
                message("player used " + System.getProperty("line.separator") + useItem.getName());
            } else if (useItem != null && useItem.getClass().equals(Food.class)) {
                super.getInventry().remove(useItem);
                Food f = (Food) useItem;
                super.modFood(f.getFoodValue());
                message("player used " + System.getProperty("line.separator") + useItem.getName());
            }
        
    }
    
    public void message(String m) {
        messages.add(m);
        while(messages.size() > 6) messages.remove(0);
    }
}
