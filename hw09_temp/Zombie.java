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
                transparent = new boolean[image_width][image_height];
                leftPointList = new int[image_height];
                rightPointList = new int[image_height];

                for (int i = 0; i < image_width; i++) {
                    for (int j = 0; j < image_height; j++) {
                        transparent[i][j] = isTransparent(i, j);
                    }
                }
                boolean[][] edges = findEdges();
                Collection<Point> collPts = setCollisionPts(edges);

                setCollPts(collPts);
                collisionPoints = collPts;

//                for (Point indiv : collPts) {
//                    System.out.println("zomb X: " + (indiv.getX() * 2) 
//                            + " zomb Y: " + (indiv.getY() * 2));
//                }
                for (Point indiv : collPts) {
                    System.out.println("zomb X: " + indiv.getX() 
                            + " zomb Y: " + indiv.getY());
                }
            } else {
              setCollPts(collisionPoints);
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
                //                System.out.println("left edge: " + Integer.toString(i) + ": " + 
                //                        Integer.toString(leftEdge));
            } 
            if (rightEdge != Integer.MIN_VALUE) {
                edges[i][rightEdge] = true;
                rightPointList[i] = rightEdge;
                //                System.out.println("right edge: " + Integer.toString(i) + ": " + 
                //                        Integer.toString(rightEdge));
            } 
        }
        System.out.println("This picture has " + Integer.toString(counter) + " rows");
        System.out.println("This picture has " + img.getWidth() + " columns");
        //        System.out.println(Integer.toString(img.getWidth()));
        return edges;
    }

    public Collection<Point> setCollisionPts(boolean[][] edges) {

        int numPts = HEIGHT;
        int[] rowIntervals = new int[numPts];
        Collection<Point> totalCollisions = new ArrayList<Point>();

        for (int i = 0; i < numPts; i++) {
//            rowIntervals[i] = (img.getHeight() / (numPts / 2)) * i;
            rowIntervals[i] = i;
        }
//        rowIntervals[numPts - 1] = img.getHeight() - 1; 

//        int leftTracker = 0;
//        int rightTracker = 0;
//        for (int i = 0; i < img.getHeight(); i++) {
//            if (leftPointList[i] < leftMin) {
//                leftMin = leftPointList[i];
//                leftTracker = i;
//            }
//            if (rightPointList[i] > rightMax) {
//                rightMax = rightPointList[i];
//                rightTracker = i;
//            }
            for (int j = 0; j < leftPointList.length; j++) {
//                if (rowIntervals[j] == i) {
                    totalCollisions.add(new Point(j, leftPointList[j]));
                    totalCollisions.add(new Point(j, rightPointList[j]));
                    if (j == 0 || j == leftPointList.length - 1) {
                        for (int x = leftPointList[j]; x < rightPointList[j]; x++) {
                            totalCollisions.add(new Point(j, x));
                        }
                    }
                
//                }
//            }
        }
//        Point minLeftCollision = new Point(leftTracker, leftPointList[leftTracker]);
//        Point maxRightCollision = new Point(rightTracker, rightPointList[rightTracker]);

//        totalCollisions.add(minLeftCollision);
//        totalCollisions.add(maxRightCollision);

        return totalCollisions;
    }

  //implementing complex intersection
    @Override
    public boolean intersects(GameObj obj){
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
                        && pos_y + indiv.getY() == obj.pos_y + objIndiv.getY()
                                ) {
                            System.out.println("Zombie Intersection at x = " + indiv.getX() 
                            + " and y = " + indiv.getY());
                            System.out.println("with farmer at x = " + objIndiv.getX() 
                            + " and y = " + objIndiv.getY());
//                            System.out.println("Position x: " + (pos_x + indiv.getY()));
//                            System.out.println("Position y: " + (pos_y + indiv.getX()));
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
