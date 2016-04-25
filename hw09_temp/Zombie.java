/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.imageio.ImageIO;

/**
 * A basic zombie gameobject
 * 
 */
public class Zombie extends GameObj {
    public static final String img_file = "zombie.png";
    public static final int WIDTH = 31;
    public static final int HEIGHT = 60;
    public static int initPosX = (int) (Math.random() * 490);
    public static int initPosY = (int) (Math.random() * 490);
    public static final int INIT_VEL_X = 1;
    public static final int INIT_VEL_Y = 1;
    public static final boolean chase = false;
    public static int[] leftPointList;
    public static int[] rightPointList;

    private static BufferedImage img;
    boolean[][] transparent;

    public Zombie(int courtWidth, int courtHeight) {
        super(INIT_VEL_X, INIT_VEL_Y, initPosX, initPosY, WIDTH, HEIGHT, courtWidth,
                courtHeight);
        try {
            if (img == null) {
                img = ImageIO.read(new File(img_file));

                int image_width = img.getWidth();
                int image_height = img.getHeight();
                transparent = new boolean[image_width][image_height];
                leftPointList = new int[image_height];
                rightPointList = new int[image_height];
                
                for (int i = 0; i < image_width; i++) {
                    for (int j = 0; j < image_height; j++) {
                        transparent[i][j] = isTransparent(i, j);
                    }
                }
                boolean[][] edges = findEdges();
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }

    }

    public boolean isTransparent(int x, int y) {
        int pixel = img.getRGB(x,y);
        if( (pixel>>24) == 0x00 ) {
//            System.out.println("found transparent pixel");
            return true;
            
        } else {
            return false;
        }
    }

    public boolean[][] findEdges() {
        boolean[][] edges = new boolean[img.getHeight()][img.getWidth()];
        int counter = 0;
        
        
        for (int i = 0; i < img.getHeight(); i++) {
            int rightEdge = Integer.MIN_VALUE;
            int leftEdge = Integer.MAX_VALUE;
            counter++;
            for (int j = 0; j < img.getWidth(); j++) {
                if(!transparent[j][i] && rightEdge < j) {
                    rightEdge = j;
                }
                if (!transparent[j][i] && leftEdge > j) {
                    leftEdge = j;
                }
            }
            if (leftEdge != Integer.MAX_VALUE) {
                edges[i][leftEdge] = true;
                leftPointList[i] = leftEdge;
                System.out.println("left edge: " + Integer.toString(i) + ": " + 
                Integer.toString(leftEdge));
            }
            if (rightEdge != Integer.MIN_VALUE) {
                edges[i][rightEdge] = true;
                rightPointList[i] = rightEdge;
                System.out.println("right edge: " + Integer.toString(i) + ": " + 
                Integer.toString(rightEdge));
            }
        }
        System.out.println("This picture has " + Integer.toString(counter) + " rows");
//        System.out.println(Integer.toString(img.getWidth()));
        return edges;
    }
    
    public Point[] setCollisionPts(boolean[][] edges) {
        Point[] rightCollisionPts = new Point[10];
        Point[] leftCollisionPts = new Point[10];
        
        int leftMin = Integer.MAX_VALUE;
        int rightMax = Integer.MIN_VALUE;
        int rows = img.getHeight();
        int[] rowIntervals = img.getHeight() / 8;
        for (int i = 0; i < img.getHeight(); i++) {
            if (leftPointList[i] < leftMin) {
                leftCollisionPts[i] = new Point(i, leftPointList[i]);
                leftMin = leftPointList[i];
            }
            if (rightPointList[i] > rightMax) {
                rightCollisionPts[i] = new Point(i, rightPointList[i]);
                rightMax = rightPointList[i];
            }
        }
        
        Point[] totalCollisions = new Point[20];
        for(int i = 0; i < 20; i++) {
            if (i < 10) {
                totalCollisions[i] = leftCollisionPts[i];
            } else {
                totalCollisions[i] = rightCollisionPts[i - 10];
            }
        }
        
        return totalCollisions;
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
