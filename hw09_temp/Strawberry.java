import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Strawberry extends Plant {
    public static final String IMG_FILE = "strawberry.jpg";
    public static final String GROWING_IMG = "strawberry_green.jpg";
    public static final String ROT_IMG_FILE = "dead_plant.jpg";
    public int init_x;
    public int init_y;
    public String state = "growing";
    public static final int GROWTH_TIME = 6;
    public static final int ROTTING_TIME = 8;
    public static final int COST = 10;
    public static final int PROFIT = 20;
    
    private static BufferedImage img;
    private static BufferedImage green_img;
    private static BufferedImage rot_img;
    
    public Strawberry(int pos_x, int pos_y, int court_width, int court_height) {
        super(court_width, court_height, pos_x, pos_y, "growing", GROWTH_TIME, ROTTING_TIME,
              COST, PROFIT, IMG_FILE, GROWING_IMG);
        state = "growing";
        init_x = pos_x;
        init_y = pos_y;
        
        try {
            if (img == null) {
                img = ImageIO.read(new File(IMG_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
        try {
            if (green_img == null) {
                green_img = ImageIO.read(new File(GROWING_IMG));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }

        try {
            if (rot_img == null) {
                rot_img = ImageIO.read(new File(ROT_IMG_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
    }
    
    @Override
    public int decToGrowth() {
        fullGrowthTime --;
        if (fullGrowthTime <= 0) {
            state = "grown";
        }
        return fullGrowthTime;
    }
    
    @Override
    public int decToRot() {
        rottingTime --;
        if (rottingTime <= 0) {
            state = "rotten";
        }
        return rottingTime;
    }
    
    @Override
    public String getState() {
        String state2 = state;
        return state2;
    }
    
    @Override
    public void draw(Graphics g) {
        if(state.equals("grown")) {
            g.drawImage(img, pos_x, pos_y, width, height, null);
        } else if (state.equals("rotten")) {
            g.drawImage(rot_img, pos_x, pos_y, width, height, null);
        } else {
            g.drawImage(green_img, pos_x, pos_y, width, height, null);
        }
    }
}