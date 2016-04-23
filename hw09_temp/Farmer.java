/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;

/**
 * A basic farmer game object, beginning in the center.
 * 
 */
public class Farmer extends GameObj {

    public static final int SIZE = 20;
    public static final int INIT_POS_X = 130;
    public static final int INIT_POS_Y = 130;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;

    public Farmer(int courtWidth, int courtHeight) {
        super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, SIZE, SIZE,
                courtWidth, courtHeight);
    }


    //TODO: change from a blue circle to a person
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval(pos_x, pos_y, width, height);
    }

}
