/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

import javax.imageio.ImageIO;
import javax.swing.*;


/**
 * FarmLand (GameCourt)
 * 
 * This class holds the primary game logic for how different objects interact
 * with one another. 
 * 
 */
@SuppressWarnings("serial")
public class FarmLand extends JPanel {

    // the state of the game logic
    private Farmer farmer; 
    private Collection<Zombie> zombies; //potentially more than one zombie
    private SinglePlot[][] plotArray; //the farming plots

    public boolean playing = false; // whether the game is running
    private int score = 0; 
    private int coins = 8;
    private JLabel status; // Current status text (i.e. Running...)
    private JLabel scoreLabel; 
    private JLabel coinLabel;
    private JLabel timeLabel;
    private int time = 0;
    private boolean lost; //has the player lost?

    // Game constants
    public static final int LAND_WIDTH = 500;
    public static final int LAND_HEIGHT = 500;
    public static final int FARMER_VELOCITY = 8;

    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 35; //update game screen time
    public static final int ONE_SECOND = 1000; //one second timer for plant decrement
    public int zombieTimer = 25000; //time for new zombie creation
    public static final int ZOMBIE_VELOCITY = 1; //base zombie velocity
    public int upgradeDifficulty = 100000;
    public int diffScale = 1;
    private static BufferedImage img; //background image
    private static BufferedImage gameOverImg; //game over screen

    public FarmLand(JLabel status, JLabel scoreLabel, JLabel coinLabel, JLabel timeLabel) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        //create the farm plots
        plotArray = new SinglePlot[5][5];
        //create the list of zombies
        zombies = new ArrayList<Zombie>();
        
        //instantiate the plots
        instantiatePlotArray();
        
        //try to read in the background and game over image
        try {
            if (img == null) {
                img = ImageIO.read(new File("grass.jpg"));
            } 
            if (gameOverImg == null) {
                gameOverImg = ImageIO.read(new File("gameOver.jpg"));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }
        
        // The timer is an object which triggers an action periodically
        // with the given INTERVAL. One registers an ActionListener with
        // this timer, whose actionPerformed() method will be called
        // each time the timer triggers. We define a helper method
        // called tick() that actually does everything that should
        // be done in a single timestep.
        
        //base timer- updates screens and necessary displays ever 35 milliseconds
        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start(); //start the timer
        
        //times each second for plant growth and time tracking
        Timer secondTimer = new Timer(ONE_SECOND, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tock();
            }
        });
        secondTimer.start();
        
//        //times zombie creation
//        Timer zombieTimer = new Timer(NEW_ZOMBIE_TIMER, new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                if (playing) {
//                    createNewZombie();
//                }
//            }
//        });
//        zombieTimer.start();

        // Enable keyboard focus on the court area.
        // When this component has the keyboard focus, key
        // events will be handled by its key listener.
        setFocusable(true);

        // This key listener allows the square to move as long
        // as an arrow key is pressed, by changing the square's
        // velocity accordingly. (The tick method below actually
        // moves the square.)
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                //movement of farmer
                if (e.getKeyCode() == KeyEvent.VK_LEFT)
                    farmer.v_x = -FARMER_VELOCITY;
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                    farmer.v_x = FARMER_VELOCITY;
                else if (e.getKeyCode() == KeyEvent.VK_DOWN)
                    farmer.v_y = FARMER_VELOCITY;
                else if (e.getKeyCode() == KeyEvent.VK_UP)
                    farmer.v_y = -FARMER_VELOCITY;
                //harvesting or clearing
                else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 5; j++) {
                            if (farmer.intersects(plotArray[i][j])) {
                                if (plotArray[i][j].isRotting()) {
                                    plotArray[i][j].deletePlant();
                                } else if (plotArray[i][j].isFullGrown()){
                                    score += plotArray[i][j].getPlant().harvestProfit;
                                    coins += plotArray[i][j].getPlant().harvestProfit;
                                    plotArray[i][j].deletePlant();
                                }
                            }
                        }
                    }
                //plant strawberries with 1
                } else if (e.getKeyCode() == KeyEvent.VK_1) {
                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 5; j++) {
                            if (farmer.intersects(plotArray[i][j])) {
                                if (coins >= Strawberry.COST) {
                                    if (plotArray[i][j].isEmpty()) {
                                        plotArray[i][j].setStrawberry(plotArray[i][j].getXPos(), 
                                                plotArray[i][j].getYPos(), LAND_WIDTH, LAND_HEIGHT);
                                        coins -= Strawberry.COST;
                                    }
                                }
                            }
                        }
                    }
                //plant pumpkins with 2
                } else if (e.getKeyCode() == KeyEvent.VK_2) {
                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 5; j++) {
                            if (farmer.intersects(plotArray[i][j])) {
                                if (coins >= Pumpkin.COST) {
                                    if (plotArray[i][j].isEmpty()) {
                                        plotArray[i][j].setPumpkin(plotArray[i][j].getXPos(), 
                                                plotArray[i][j].getYPos(), LAND_WIDTH, LAND_HEIGHT);
                                        coins -= Pumpkin.COST;
                                    }
                                }
                            }
                        }
                    }
                //plant wheat with 3
                } else if (e.getKeyCode() == KeyEvent.VK_3) {
                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 5; j++) {
                            if (farmer.intersects(plotArray[i][j])) {
                                if (coins >= Wheat.COST) {
                                    if (plotArray[i][j].isEmpty()) {
                                        plotArray[i][j].setWheat(plotArray[i][j].getXPos(), 
                                                plotArray[i][j].getYPos(), LAND_WIDTH, LAND_HEIGHT);
                                        coins -= Wheat.COST;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            
            //stop the farmer if the key was released
            public void keyReleased(KeyEvent e) {
                farmer.v_x = 0;
                farmer.v_y = 0;
            }
        });

        this.status = status;
        this.scoreLabel = scoreLabel;
        this.coinLabel = coinLabel;
        this.timeLabel = timeLabel;
    }

    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
        //create new farmer and zombies
        farmer = new Farmer(LAND_WIDTH, LAND_HEIGHT);
        //initiate the plots again
        instantiatePlotArray();
        zombies.clear();
        zombies.add(new Zombie(LAND_WIDTH, LAND_HEIGHT));

        playing = true;
        status.setText("Running...");
        score = 0;
        coins = 8;
        time = 0;
        lost = false;

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }
    
    //instantiate plots!
    public void instantiatePlotArray() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                plotArray[i][j] = new SinglePlot(LAND_WIDTH, LAND_HEIGHT,  (LAND_WIDTH / (5)) * i, 
                        (LAND_HEIGHT / (5)) * j, null);
            }
        }
    }
    
    //control zombie chasing the farmer
    private void chase(int vel) {
        double x = farmer.pos_x;
        double y = farmer.pos_y;

        for (Zombie indiv : zombies) {
            if (indiv.pos_x > x) {
                indiv.v_x = -vel;
            } else {
                indiv.v_x = vel;
            }
            if (indiv.pos_y > y) {
                indiv.v_y = -vel;
            } else {
                indiv.v_y = vel;
            }
            indiv.move();
            indiv.bounce(indiv.hitWall());
        }

    }


    /**
     * This method is called every time the timer defined in the constructor
     * triggers.
     */
    private void tick() {
        if (playing) {
//            zombies chase the farmer!
//            chase(ZOMBIE_VELOCITY);
            //move the farmer
            farmer.move();
            
            //create a new zombie 
            zombieTimer = zombieTimer - INTERVAL;
            if (zombieTimer <= 0) {
                createNewZombie();
                zombieTimer = 25000;
            }
            
            upgradeDifficulty = upgradeDifficulty - INTERVAL;
            //chase zombie based on difficulty
            if (upgradeDifficulty <= 0) {
                diffScale++;
                chase(ZOMBIE_VELOCITY * diffScale);
                upgradeDifficulty = 100000;
            } else {
                chase(ZOMBIE_VELOCITY * diffScale);
            }

            // check for the game end conditions
            for(Zombie indiv : zombies) {
                if (farmer.intersects(indiv)) {
                    playing = false;
                    status.setText("You lose!");
                    lost = true;
                } 
            }

            //update score and coins and time
            scoreLabel.setText("Score: " + Integer.toString(score));
            coinLabel.setText("Coins: " + Integer.toString(coins));
            timeLabel.setText("Time: " + Integer.toString(time));

            // update the display
            repaint();
        }
    }

    //called every second
    private void tock() {
        if (playing) {
            //go through all plots,  decrement plant timers
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (plotArray[i][j].getPlant() != null) {
                        if (plotArray[i][j].isGrowing()) {
                            plotArray[i][j].getPlant().decToGrowth();
                        } else if (plotArray[i][j].isFullGrown()) {
                            plotArray[i][j].getPlant().decToRot();
                        } 
                    }
                }
            }
            //update the actual time in-game
            time++;
        }
    }

  //add new zombie
    void createNewZombie() {
        if (playing) {
            zombies.add(new Zombie(LAND_WIDTH, LAND_HEIGHT));
            // update the display
            repaint();
        }
    }

    
    //pause the game
    public void pause() {
        playing = false;
    }
    
    //unpause the game
    public void unpause() {
        playing = true;
    }
    
    //get the score
    public int getScore() {
        return score;
    }


    //override paint component to draw the game
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //draw background
        g.drawImage(img, 0, 0, LAND_WIDTH, LAND_HEIGHT, null);

        //draw plots
        for (int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++) {
                plotArray[i][j].draw(g);
            }
        }
        //draw farmer
        farmer.draw(g);
        //draw all zombies
        for(Zombie indiv: zombies) {
            indiv.draw(g);
        }
        
        if (lost) {
            //draw game over image
            g.drawImage(gameOverImg, 0, 0, LAND_WIDTH, LAND_HEIGHT, null);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(LAND_WIDTH, LAND_HEIGHT);
    }
}
