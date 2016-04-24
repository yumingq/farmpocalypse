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
 * A basic farmer game object, beginning in the center.
 * 
 */
public class Farmer extends GameObj {
    public static final String img_file = "Farmer.png";
    public static final int SIZE = 20;
    public static final int INIT_POS_X = 130;
    public static final int INIT_POS_Y = 130;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;
    
    private static BufferedImage img;
    boolean[][] transparent;

    public Farmer(int courtWidth, int courtHeight) {
        super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, SIZE, SIZE,
                courtWidth, courtHeight);
        try {
            if (img == null) {
                img = ImageIO.read(new File(img_file));
                
                int image_width = img.getWidth();
                int image_height = img.getHeight();
                transparent = new boolean[image_width][image_height];
                
                for (int i = 0; i < image_width; i++) {
                    for (int j = 0; i < image_height; j++) {
                        transparent[i][j] = isTransparent(i, j);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }


    @Override
    public void draw(Graphics g) {
        g.drawImage(img, pos_x, pos_y, width, height, null);
    }

    public boolean isTransparent( int x, int y ) {
        int pixel = img.getRGB(x,y);
        if( (pixel>>24) == 0x00 ) {
            return true;
        } else {
            return false;
        }
      }
}
