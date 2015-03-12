/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;
import world.World;

/**
 *
 * @author Emmet
 */
public class Player extends BaseEntity {
    public Player(String type, World w, int ic, int ir, int av, int dv, int hp, int vr) {
        super(type, w, ic, ir, av, dv, hp, vr);
    }
    
    public void playerUI(Graphics g, int z, int xOffset, int yOffset, SpriteSheet groundTiles, int tileSize, int screenWidthTiles, int screenHeightTiles) {
        for (int x = xOffset + screenWidthTiles; x < xOffset + screenWidthTiles + 9; x++) {
            for (int y = yOffset; y < yOffset + screenHeightTiles; y++) {
                groundTiles.getSubImage(31, 17).draw(x * tileSize, y  * tileSize);                        
            }
        }
        g.setColor(Color.black);
        g.fillRect((xOffset +  24)* tileSize, (yOffset + 2)  * tileSize, 3 * tileSize, tileSize);
        groundTiles.getSubImage(8, 1).draw((xOffset +  24)* tileSize, (yOffset + 2)  * tileSize);
        groundTiles.getSubImage(42 + (super.getAV()/10), 0).draw((xOffset +  25)* tileSize, (yOffset + 2)  * tileSize);
        groundTiles.getSubImage(42 + (super.getAV()%10), 0).draw((xOffset +  26)* tileSize, (yOffset + 2)  * tileSize);
        g.fillRect((xOffset +  24)* tileSize, (yOffset + 4)  * tileSize, 3 * tileSize, tileSize);
        groundTiles.getSubImage(5, 1).draw((xOffset +  24)* tileSize, (yOffset + 4)  * tileSize);
        groundTiles.getSubImage(42 + (super.getDV()/10), 0).draw((xOffset +  25)* tileSize, (yOffset + 4)  * tileSize);
        groundTiles.getSubImage(42 + (super.getDV()%10), 0).draw((xOffset +  26)* tileSize, (yOffset + 4)  * tileSize);
        g.fillRect((xOffset +  24)* tileSize, (yOffset + 6)  * tileSize, 3 * tileSize, tileSize);
        groundTiles.getSubImage(7, 1).draw((xOffset +  24)* tileSize, (yOffset + 6)  * tileSize);
        groundTiles.getSubImage(42 + (super.getHP()/10), 0).draw((xOffset +  25)* tileSize, (yOffset + 6)  * tileSize);
        groundTiles.getSubImage(42 + (super.getHP()%10), 0).draw((xOffset +  26)* tileSize, (yOffset + 6)  * tileSize);
        
        g.fillRect((xOffset +  24)* tileSize, (yOffset + 8)  * tileSize, tileSize, tileSize);
        groundTiles.getSubImage(43, 45).draw((xOffset +  24)* tileSize, (yOffset + 8)  * tileSize);
        g.fillRect((xOffset +  25)* tileSize, (yOffset + 8)  * tileSize, 5 * tileSize, 4 * tileSize);
        for(int y = 0; y < 4; y++){
            for(int x = 0; x < 5; x++){
                int i = x + (5*y);
                if (super.getInventry().get(i) != null) {
                    groundTiles.getSubImage(super.getInventry().get(i).getImageCol(), super.getInventry().get(i).getImageRow()).draw((xOffset +  25 + x)* tileSize, (yOffset + 8 + y)  * tileSize);
                }
            }
        }
        
        g.fillRect((xOffset +  24)* tileSize, (yOffset + 16)  * tileSize, 7 * tileSize, 6 * tileSize);
    }
}
