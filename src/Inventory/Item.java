package Inventory;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Emmet
 */
public class Item {
    private String name;
    private String description;
    private int imageCol;
    private int imageRow;
    private int posZ;
    private int posX;
    private int posY;

    public Item(String name, int imageCol, int imageRow, String description) {
        this.name = name;
        this.imageCol = imageCol;
        this.imageRow = imageRow;
        this.description = description;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the imageCol
     */
    public int getImageCol() {
        return imageCol;
    }

    /**
     * @return the imageRow
     */
    public int getImageRow() {
        return imageRow;
    }

    /**
     * @return the posX
     */
    public int getPosX() {
        return posX;
    }

    /**
     * @return the posY
     */
    public int getPosY() {
        return posY;
    }

    /**
     * @return the posZ
     */
    public int getPosZ() {
        return posZ;
    }
    
    public void setPos(int z, int x, int y) {
        this.posZ = z;
        this.posX = x;
        this.posY = y;
    }

    /**
     * @return the desription
     */
    public String getDesription() {
        return description;
    }

}
