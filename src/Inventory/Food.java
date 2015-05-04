package inventory;
/**
 * Author: Emmet Boylan
 * Project: Sandbox Warrior
 * File: Food.java
 * 
 * Extension of Item class that includes a food value
 **/
public class Food extends Item{
    
    private int foodValue;
    
    public Food(String name, int imageCol, int imageRow, int foodValue, String description) {
        super(name, imageCol, imageRow, description);
        this.foodValue = foodValue;
    }

    /**
     * @return the foodValue
     */
    public int getFoodValue() {
        return foodValue;
    }

    /**
     * @param foodValue the foodValue to set
     */
    public void setFoodValue(int foodValue) {
        this.foodValue = foodValue;
    }
    
}
