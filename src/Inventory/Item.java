package inventory;

/*
 * Author: Emmet Boylan 
 * Project: Sandbox Warrior
 * File: Item.java
 * 
 * Class defining attributes of Item
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

    public String getName() {
        return name;
    }

    public int getImageCol() {
        return imageCol;
    }

    public int getImageRow() {
        return imageRow;
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
    
    public void setPos(int z, int x, int y) {
        this.posZ = z;
        this.posX = x;
        this.posY = y;
    }

    public String getDesription() {
        return description;
    }

}
