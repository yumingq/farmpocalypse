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

/**
 * A basic zombie gameobject, starting in the upper left
 * corner of the game court.
 * 
 */
public class Zombie extends GameObj {
    public static final String img_file = "Newspaper_Zombie.png";
    public static final int SIZE = 55;
    public static final int INIT_POS_X = 170;
    public static final int INIT_POS_Y = 170;
    public static final int INIT_VEL_X = 1;
    public static final int INIT_VEL_Y = 1;
    public static final boolean chase = false;
    
    private static BufferedImage img;

    public Zombie(int courtWidth, int courtHeight) {
        super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, SIZE, SIZE, courtWidth,
                courtHeight);
        try {
            if (img == null) {
                img = ImageIO.read(new File(img_file));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }
    
    //TODO: how to chase the farmer?
    public void chase(Graphics g) {
        
    }
    
    public void wander(Graphics g) {
    }
    
    //begins as poison mushroom
    //TODO: update later to a zombie image
    @Override
    public void draw(Graphics g) {
        g.drawImage(img, pos_x, pos_y, width, height, null);
    }

}
