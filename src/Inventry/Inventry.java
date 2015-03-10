/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Inventry;

/**
 *
 * @author Emmet
 */
public class Inventry {

    private Item[] inventry;

    public Item[] getItems() {
        return inventry;
    }

    public Item get(int i) {
        return inventry[i];
    }

    public Inventry(int max) {
        inventry = new Item[max];
    }

    public void add(Item item) {
        for (int i = 0; i < inventry.length; i++) {
            if (inventry[i] == null) {
                inventry[i] = item;
                break;
            }
        }
    }

    public void remove(Item item) {
        for (int i = 0; i < inventry.length; i++) {
            if (inventry[i] == item) {
                inventry[i] = null;
                return;
            }
        }
    }

    public boolean isFull() {
        int size = 0;
        for (int i = 0; i < inventry.length; i++) {
            if (inventry[i] != null) {
                size++;
            }
        }
        return size == inventry.length;
    }
}
