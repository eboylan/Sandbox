/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import states.LoseState;
import world.FieldOfView;

/**
 *
 * @author Emmet
 */
public class PlayerAI extends EntityAI {

    FieldOfView fov;

    public PlayerAI(BaseEntity be) {
        super(be);
    }
}
