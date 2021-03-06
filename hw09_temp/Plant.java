/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/** 
 * Plant object- abstract class for plants
 */
public class Plant extends GameObj {
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;
    public static final int SIZE = 40;
    
    public String img_file; 
    public String growing_img;
    public static final String rot_img_file = "dead_plant.jpg";
    
    public int init_x;
    public int init_y;
    
    private String state; //is the plant growing, grown, or rotten
    public int fullGrowthTime; //how long does it take to grow
    public int rottingTime; //how long does it take to rot
    public int costToBuy; //how much does it cost to plant
    public int harvestProfit; //how much is it worth harvested?

    private static BufferedImage img;
    private static BufferedImage green_img;
    private static BufferedImage rot_img;

    /**
     * Constructor
     */
    public Plant(int court_width, int court_height, int pos_x, int pos_y, String state, 
            int fullGrowthTime, int rottingTime, int costToBuy, 
            int harvestProfit, String img_file, String growing_img){
        super (INIT_VEL_X, INIT_VEL_Y, pos_x, pos_y, SIZE, SIZE, court_width,
                court_height, null);
        init_x = pos_x;
        init_y = pos_y;
        try {
            if (img == null) {
                img = ImageIO.read(new File(img_file));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
        try {
            if (green_img == null) {
                green_img = ImageIO.read(new File(growing_img));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }

        try {
            if (rot_img == null) {
                rot_img = ImageIO.read(new File(rot_img_file));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
        this.state = state;
        this.fullGrowthTime = fullGrowthTime;
        this.rottingTime = rottingTime;
        this.costToBuy = costToBuy;
        this.harvestProfit = harvestProfit;
        this.img_file = img_file;
        this.growing_img = growing_img;
    }
    
    //to be overwritten
    public int decToGrowth() {
        return 0;
    }
    
    //to be overwritten
    public int decToRot() {
        return 0;
    }
    
    //to be overwritten
    public String getImg() {
        return null;
    }

    //to be overwritten
    public String getState() {
        String state2 = state;
        return state2;
    }


}
