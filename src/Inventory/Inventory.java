/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory;

/**
 *
 * @author Emmet
 */
public class Inventory {

    private Item[] inventory;

    public Item[] getItems() {
        return inventory;
    }

    public Item get(int i) {
        return inventory[i];
    }

    public Inventory(int max) {
        inventory = new Item[max];
    }

    public void add(Item item) {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == null) {
                inventory[i] = item;
                break;
            }
        }
    }

    public void remove(Item item) {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == item) {
                inventory[i] = null;
                return;
            }
        }
    }

    public boolean isFull() {
        int size = 0;
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null) {
                size++;
            }
        }
        return size == inventory.length;
    }
    
    public int length() {
        return inventory.length;
    }
    
    public boolean hasItem(String item) {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i].getName() == item) {
                return true;
            }
        }
        return false;
    }
}
