package Inventry;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Emmet
 */
public class Armour extends Item {
    int defMod;
    
    public Armour(String name, int imageCol, int imageRow, int defMod) {
        super(name, imageCol, imageRow);
        this.defMod = defMod;
    }
}
