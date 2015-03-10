package Inventry;

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
    private int imageCol;
    private int imageRow;

    public Item(String name, int imageCol, int imageRow) {
        this.name = name;
        this.imageCol = imageCol;
        this.imageRow = imageRow;
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
}
