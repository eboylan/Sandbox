/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author Emmet
 */
public class MyconoidAI extends EntityAI {
    private EntityFactory entFactory;
    private int spreadcount;
    private boolean spawn;
 
    public MyconoidAI(BaseEntity be, EntityFactory entFactory, boolean spawn) {
        super(be);
        this.entFactory = entFactory;
        this.spawn = spawn;
    }

    /**
     *
     */
    @Override
    public void onUpdate(){
        if (spreadcount < 5 && Math.random() < 0.02 && spawn) {
            spread();
        }
    }
 
    private void spread(){
        int z = be.getPosZ();
        int x = be.getPosX() + (int)(Math.random() * 11) - 5;
        int y = be.getPosY() + (int)(Math.random() * 11) - 5;
  
        if (!be.canEnter(z, x, y)) {
            return;
        }
  
        BaseEntity child = entFactory.newMyconoid(false, z);
        child.setPos(z, x, y);
        spreadcount++;
    }
}
