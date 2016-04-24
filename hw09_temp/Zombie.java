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
 * A basic zombie gameobject
 * 
 */
public class Zombie extends GameObj {
    public static final String img_file = "Newspaper_Zombie.png";
    public static final int SIZE = 55;
    public static int initPosX = (int) (Math.random() * 490);
    public static int initPosY = (int) (Math.random() * 490);
    public static final int INIT_VEL_X = 1;
    public static final int INIT_VEL_Y = 1;
    public static final boolean chase = false;
    
    private static BufferedImage img;
    boolean[][] transparent;

    public Zombie(int courtWidth, int courtHeight) {
        super(INIT_VEL_X, INIT_VEL_Y, initPosX, initPosY, SIZE, SIZE, courtWidth,
                courtHeight);
        try {
            if (img == null) {
                img = ImageIO.read(new File(img_file));
                
                int image_width = img.getWidth();
                int image_height = img.getHeight();
                transparent = new boolean[image_width][image_height];
                
                
                for (int i = 0; i < image_width; i++) {
                    for (int j = 0; j < image_height; j++) {
                        transparent[i][j] = isTransparent(i, j);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }

    }
    
    public boolean isTransparent(int x, int y) {
        int pixel = img.getRGB(x,y);
        if( (pixel>>24) == 0x00 ) {
            return true;
        } else {
            return false;
        }
      }
    
    //implementing complex intersection?
    @Override
    public boolean intersects(GameObj obj){
        return (pos_x + width >= obj.pos_x
                && pos_y + height >= obj.pos_y
                && obj.pos_x + obj.width >= pos_x 
                && obj.pos_y + obj.height >= pos_y);
    }
    
    @Override
    public void draw(Graphics g) {
        g.drawImage(img, pos_x, pos_y, width, height, null);
    }

}
