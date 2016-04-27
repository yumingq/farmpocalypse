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
    public static final int SIZE = 40;
    public int init_x;
    public int init_y;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;
    private Plant plant;

    public SinglePlot(int courtWidth, int courtHeight, int pos_x, int pos_y, Plant plant) {
        super(INIT_VEL_X, INIT_VEL_Y, pos_x, pos_y, SIZE, SIZE, courtWidth,
                courtHeight, null);
        init_x = pos_x;
        init_y = pos_y;
        this.plant = plant;
        
    }

    @Override
    public void draw(Graphics g) {
        if (!(plant == null)) {
            plant.draw(g);
        } else {
            g.setColor(new Color(117, 63, 5));
            g.fillRect(init_x, init_y, width, height);
        }
    }

    public int getXPos() {
        return init_x;
    }

    public int getYPos() {
        return init_y;
    }

    public boolean isEmpty() {
        if (plant == null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isRotting() {
        if (plant == null) {
            return false;
        }

        if (plant.getState().equals("rotten")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isFullGrown() {
        if (plant == null) {
            return false;
        }

        if (plant.getState().equals("grown")) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean isGrowing() {
        if (plant == null) {
            return false;
        }

        if (plant.getState().equals("growing")) {
            return true;
        } else {
            return false;
        }
    }
    
    public Plant getPlant() {
        //this may just be aliasing...I'm not very good at encapsulation
        Plant plantCopy = plant;
        return plantCopy;
    }
    
    public void deletePlant() {
        plant = null;
    }
    
    public void setPumpkin(int x,  int y, int width, int height) {
        plant = new Pumpkin(x, y, width, height);
    }
    
    public void setStrawberry(int x,  int y, int width, int height) {
        plant = new Strawberry(x, y, width, height);
    }
    
    public void setWheat(int x,  int y, int width, int height) {
        plant = new Wheat(x, y, width, height);
    }

}
