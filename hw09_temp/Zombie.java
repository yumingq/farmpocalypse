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
    public static final int WIDTH = 21;
    public static final int HEIGHT = 38;
    public static int initPosX = 270;
    public static int initPosY = 270;
    public static final int INIT_VEL_X = 1;
    public static final int INIT_VEL_Y = 1;
    public static final boolean chase = false;
    public static int[] leftPointList;
    public static int[] rightPointList;

    public static Collection<Point> collisionPoints;

    private static BufferedImage img;
    boolean[][] transparent;

    public Zombie(int courtWidth, int courtHeight) {
        super(INIT_VEL_X, INIT_VEL_Y, initPosX, initPosY, WIDTH, HEIGHT, courtWidth,
                courtHeight, null);
        try {
            if (img == null) {
                img = ImageIO.read(new File(img_file));

                int image_width = img.getWidth();
                int image_height = img.getHeight();
                //2D array of whether or not the pixels are transparent
                transparent = new boolean[image_width][image_height];
                //array of left and right edge points
                leftPointList = new int[image_height];
                rightPointList = new int[image_height];
                
                //find the transparent pixels
                for (int i = 0; i < image_width; i++) {
                    for (int j = 0; j < image_height; j++) {
                        transparent[i][j] = isTransparent(i, j);
                    }
                }
                //find the edges
                findEdges();
                //update collection of points with the edges
                Collection<Point> collPts = setCollisionPts();
                
                //update collection of points in GameObj
                setCollPts(collPts);
                collisionPoints = collPts;
            } else {
                //update collection of points in GameObj
              setCollPts(collisionPoints);
          }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }

    }
    
    //is a pixel transparent?
    public boolean isTransparent(int x, int y) {
        int pixel = img.getRGB(x,y);
        if( (pixel>>24) == 0x00 ) {
            return true;

        } else {
            return false;
        }
    }
    
    //find the edges of non-transparency
    public void findEdges() {
        boolean[][] edges = new boolean[img.getHeight()][img.getWidth()];
        
        //go through each row
        for (int i = 0; i < img.getHeight(); i++) {
            int rightEdge = Integer.MIN_VALUE;
            int leftEdge = Integer.MAX_VALUE;
            //go through each col in each row
            for (int j = 0; j < img.getWidth(); j++) {
                //find the rightmost edge pixel in the row
                if(!transparent[j][i] && rightEdge < j) {
                    rightEdge = j;
                }
                //find the leftmost edge pixel in the row
                if (!transparent[j][i] && leftEdge > j) {
                    leftEdge = j;
                }
            }
            //update them in array of left edge points
            if (leftEdge != Integer.MAX_VALUE) {
                edges[i][leftEdge] = true;
                leftPointList[i] = leftEdge;
            } 
            //update them in array of right edge points
            if (rightEdge != Integer.MIN_VALUE) {
                edges[i][rightEdge] = true;
                rightPointList[i] = rightEdge;
            } 
        }
    }

    //create a collection of collision points
    public Collection<Point> setCollisionPts() {
        
        Collection<Point> totalCollisions = new ArrayList<Point>();
        
        //go through all the edge points in the arrays and add them
        //as points
            for (int j = 0; j < leftPointList.length; j++) {
                    totalCollisions.add(new Point(j, leftPointList[j]));
                    totalCollisions.add(new Point(j, rightPointList[j]));
                    //add top and bottom edges too
                    if (j == 0 || j == leftPointList.length - 1) {
                        for (int x = leftPointList[j]; x < rightPointList[j]; x++) {
                            totalCollisions.add(new Point(j, x));
                        }
                    }
        }
        return totalCollisions;
    }

  //implementing complex intersection
    @Override
    public boolean intersects(GameObj obj){
        //if the other object is a simple squareish object
        if (obj.collisionPts == null) {
            for (Point indiv : collisionPoints) {
                if (pos_x + indiv.getX() >= obj.pos_x
                        && pos_y + indiv.getY() >= obj.pos_y
                        && obj.pos_x + obj.width >= pos_x
                        && obj.pos_y + obj.height >= pos_y) {
                    return true;
                }
            }
        } else {
            //check box first
            if ((pos_x + width >= obj.pos_x
                    && pos_y + height >= obj.pos_y
                    && obj.pos_x + obj.width >= pos_x 
                    && obj.pos_y + obj.height >= pos_y)) {
                //if they are within the box, check collision points
                for (Point indiv : collisionPoints) {
                    for(Point objIndiv : obj.collisionPts) {
                        if (pos_x + indiv.getX() == obj.pos_x + objIndiv.getX()
                        && pos_y + indiv.getY() == obj.pos_y + objIndiv.getY()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(img, pos_x, pos_y, width, height, null);
    }

}
