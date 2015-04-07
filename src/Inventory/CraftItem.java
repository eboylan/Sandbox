/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory;

/**
 *
 * @author Emmet
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
