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

    public static final String rot_img_file = "dead_plant.jpg";
    public static final int SIZE = 30;
    public static final int INIT_X = 490;
    public static final int INIT_Y = 490;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;
    public static Plant[][] plotArray = new Plant[5][5];

    private static BufferedImage rot_img;
//    private static BufferedImage[][] imgArray = new BufferedImage[5][5];
    
    public Plots(int courtWidth, int courtHeight) {
        super(INIT_VEL_X, INIT_VEL_Y, INIT_X, INIT_Y, SIZE, SIZE, courtWidth,
                courtHeight);
        try {
            if (rot_img == null) {
                rot_img = ImageIO.read(new File(rot_img_file));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
        
//        for (int x = 0; x < 5; x++) {
//            for (int y = 0; y < 5; y++) {
//                try {
//                    if (imgArray[x][y] == null && !(isEmpty(plotArray[x][y]))) {
//                        imgArray[x][y] = ImageIO.read(new File(plotArray[x][y].img_file));
//                    }
//                } catch (IOException e) {
//                    System.out.println("Internal Error:" + e.getMessage());
//                }
//            }
//        }
    }

    @Override
    public void draw(Graphics g) {
        for (int x = 1; x < 6; x++) {
            for (int y = 1; y < 6; y++) {
                if (isEmpty(plotArray[x - 1][y - 1])) {
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(pos_x - pos_x / x, pos_y - pos_y / y, width, height);
                } else if (isRotting(plotArray[x - 1][y - 1])) {
                    g.drawImage(rot_img, pos_x, pos_y, width, height, null);
                } else {
                    BufferedImage img;
                    try {
                        img = ImageIO.read(new File(plotArray[x][y].img_file));
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
