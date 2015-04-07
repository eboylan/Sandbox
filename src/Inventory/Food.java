/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory;

/**
 *
 * @author Emmet
 */
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
