package inventory;

/*
 * Author: Emmet Boylan
 * Project: Sandbox Warrior
 * File: CraftItem.java
 * 
 * Implements code to support crafting of items by creating
 * new items based on ingredients.
 * 
 */
public class CraftItem {
    private final ItemFactory itemFactory;
   public CraftItem(ItemFactory iFact) {
       this.itemFactory = iFact;
   } 
   
   public Item craft(Item i1, Item i2) {
       if (i1.getName().equals("Torch") && i2.getName().equals("Quorn") || i2.getName().equals("Torch") && i1.getName().equals("Quorn")) {
            return itemFactory.newCookedQuorn(); 
          } else if (i1.getName().equals("Torch") && i2.getName().equals("Lizard Meat") || i2.getName().equals("Torch") && i1.getName().equals("Lizard Meat")) {
            return itemFactory.newCookedLizMeat(); 
          } else {
            return itemFactory.newMess();
          }
   }
}
