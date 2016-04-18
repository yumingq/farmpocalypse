/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Plots extends GameObj {
    public static final int SIZE = 30;
    public static final int INIT_X = 290;
    public static final int INIT_Y = 290;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;
    public static Plant[][] plotArray = new Plant[5][5];

    public Plots(int courtWidth, int courtHeight) {
        super(INIT_VEL_X, INIT_VEL_Y, INIT_X, INIT_Y, SIZE, SIZE, courtWidth,
                courtHeight);
    }

    @Override
    public void draw(Graphics g) {
        for (int x = 1; x < 6; x++) {
            for (int y = 1; y < 6; y++) {
                if (isEmpty(plotArray[x - 1][y - 1])) {
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(pos_x - pos_x / x, pos_y - pos_y / y, width, height);
                }
            }
        }
    }
    
    public boolean isEmpty(Plant current) {
        if (current == null) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean isRotting(Plant current) {
        if (current.state.equals("rotting")) {
            return true;
        } else {
            return false;
        }
    }


}
