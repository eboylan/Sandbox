package inventory;

/*
 * Author: Emmet Boylan and http://trystans.blogspot.ie/ 
 * Project: Sandbox Warrior
 * File: Inventory.java
 * 
 * Class to define and support Item inventory
 * Based on implementation from http://trystans.blogspot.ie/
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
            if (inventory[i] == null ? item == null : inventory[i].getName().equals(item)) {
                return true;
            }
        }
        return false;
    }
}
