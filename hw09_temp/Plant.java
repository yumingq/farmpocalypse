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

/** An object in the game. 
 *
 *  Game objects exist in the game court. They have a position, 
 *  velocity, size and bounds. Their velocity controls how they 
 *  move; their position should always be within their bounds.
 */
public class Plant extends GameObj {
    public String img_file;
    public String growing_img;
    public static final String rot_img_file = "dead_plant.jpg";
    public static final int SIZE = 20;
    public int init_x;
    public int init_y;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;
    private String state;
    public int fullGrowthTime;
    public int rottingTime;
    public int costToBuy;
    public int harvestProfit;

    private static BufferedImage img;
    private static BufferedImage green_img;
    private static BufferedImage rot_img;

    /**
     * Constructor
     */
    public Plant(int court_width, int court_height, int pos_x, int pos_y, String state, 
            int fullGrowthTime, int rottingTime, int costToBuy, 
            int harvestProfit, String img_file, String growing_img){
        super(INIT_VEL_X, INIT_VEL_Y, pos_x, pos_y, SIZE, SIZE, court_width,
                court_height);
        init_x = pos_x;
        init_y = pos_y;
        //may create problems- image assigning
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

    public int decToGrowth() {
        fullGrowthTime --;
        if (fullGrowthTime <= 0) {
            state = "grown";
        }
        return fullGrowthTime;
    }

    public int decToRot() {
        rottingTime --;
        if (rottingTime <= 0) {
            state = "rotten";
        }
        return rottingTime;
    }

    public String getImg() {
        return img_file;
    }

    public String getState() {
        String state2 = state;
        return state2;
    }

    /**
     * Default draw method that provides how the object should be drawn 
     * in the GUI. This method does not draw anything. Subclass should 
     * override this method based on how their object should appear.
     * 
     * @param g 
     *  The <code>Graphics</code> context used for drawing the object.
     *  Remember graphics contexts that we used in OCaml, it gives the 
     *  context in which the object should be drawn (a canvas, a frame, 
     *  etc.)
     */
//    public void draw(Graphics g) {
////        if(state.equals("grown")) {
////            g.drawImage(img, pos_x, pos_y, width, height, null);
////        } else if (state.equals("rotten")) {
////            g.drawImage(rot_img, pos_x, pos_y, width, height, null);
////        } else {
////            g.drawImage(green_img, pos_x, pos_y, width, height, null);
////        }
//    }

}
