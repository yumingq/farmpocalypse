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


public class SinglePlot extends GameObj {

    public static final String rot_img_file = "dead_plant.jpg";
    public static final int SIZE = 30;
    public int init_X;
    public int init_Y;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;
    public static Plant plant;

    private static BufferedImage rot_img;
    
    public SinglePlot(int courtWidth, int courtHeight, int pos_x, int pos_y, Plant plant) {
        super(INIT_VEL_X, INIT_VEL_Y, pos_x, pos_y, SIZE, SIZE, courtWidth,
                courtHeight);
        this.plant = plant;
        try {
            if (rot_img == null) {
                rot_img = ImageIO.read(new File(rot_img_file));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }

    @Override
    public void draw(Graphics g) {
        for (int x = 1; x < 6; x++) {
            for (int y = 1; y < 6; y++) {
                if (isEmpty(plant)) {
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(pos_x - pos_x / x, pos_y - pos_y / y, width, height);
                } else if (isRotting(plant)) {
                    g.drawImage(rot_img, pos_x, pos_y, width, height, null);
                } else {
                    BufferedImage img;
                    try {
                        img = ImageIO.read(new File(plant.img_file));
                        g.drawImage(img, pos_x, pos_y, width, height, null);
                    } catch (IOException e) {
                        System.out.println("Internal Error:" + e.getMessage());
                    }
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
