/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 * 
 */
@SuppressWarnings("serial")
public class FarmLand extends JPanel {

    // the state of the game logic
    private Farmer farmer; // the Black Square, keyboard control
    private Zombie zombie; // the Golden Snitch, bounces
    private SinglePlot[][] plotArray;

    public boolean playing = false; // whether the game is running
    public boolean pause = false; 
    private int score = 2; 
    private JLabel status; // Current status text (i.e. Running...)
    private JLabel scoreLabel;

    // Game constants
    public static final int LAND_WIDTH = 500;
    public static final int LAND_HEIGHT = 450;
    public static final int FARMER_VELOCITY = 4;
    public static final int ZOMBIE_VELOCITY = 3;
    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 35;
    public static final int ONE_SECOND = 1000;



    public FarmLand(JLabel status, JLabel scoreLabel) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        plotArray = new SinglePlot[5][5];
        instantiatePlotArray();
        // The timer is an object which triggers an action periodically
        // with the given INTERVAL. One registers an ActionListener with
        // this timer, whose actionPerformed() method will be called
        // each time the timer triggers. We define a helper method
        // called tick() that actually does everything that should
        // be done in a single timestep.
        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start(); // MAKE SURE TO START THE TIMER!

        Timer secondTimer = new Timer(ONE_SECOND, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tock();
            }
        });
        secondTimer.start();

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
                if (e.getKeyCode() == KeyEvent.VK_LEFT)
                    farmer.v_x = -FARMER_VELOCITY;
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                    farmer.v_x = FARMER_VELOCITY;
                else if (e.getKeyCode() == KeyEvent.VK_DOWN)
                    farmer.v_y = FARMER_VELOCITY;
                else if (e.getKeyCode() == KeyEvent.VK_UP)
                    farmer.v_y = -FARMER_VELOCITY;
                else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 5; j++) {
                            if (farmer.intersects(plotArray[i][j])) {
                                //                                if (plotArray[i][j].isEmpty()) {
                                //                                    //pause the game
                                //                                    pause = true;
                                //                                    status.setText("Paused");
                                //                                    zombie.v_x = 0;
                                //                                    zombie.v_y = 0;
                                //                                    //how do I pause the plant growth?
                                //                                    //show a menu with its own keylisteners?
                                //                                    //if time- create menu option, otherwise just do 1,2,3
                                //
                                //                                    //How do I listen for user input?
                                //
                                //                                    //after selection, unpause game
                                //                                    status.setText("Running...");
                                //                                    pause = false;
                                //                                } else 
                                if (plotArray[i][j].isRotting()) {
                                    plotArray[i][j].deletePlant();
                                } else if (plotArray[i][j].isFullGrown()){
                                    score += plotArray[i][j].getPlant().harvestProfit;
                                    plotArray[i][j].deletePlant();
                                }
                            }
                        }
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_1) {
                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 5; j++) {
                            if (farmer.intersects(plotArray[i][j])) {
                                if (score >= Strawberry.COST) {
                                    if (plotArray[i][j].isEmpty()) {
                                        plotArray[i][j].setStrawberry(plotArray[i][j].getXPos(), 
                                                plotArray[i][j].getYPos(), LAND_WIDTH, LAND_HEIGHT);
                                        score -= Strawberry.COST;
                                    }
                                }
                            }
                        }
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_2) {
                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 5; j++) {
                            if (farmer.intersects(plotArray[i][j])) {
                                if (score >= Pumpkin.COST) {
                                    if (plotArray[i][j].isEmpty()) {
                                        plotArray[i][j].setPumpkin(plotArray[i][j].getXPos(), 
                                                plotArray[i][j].getYPos(), LAND_WIDTH, LAND_HEIGHT);
                                        score -= Pumpkin.COST;
                                    }
                                }
                            }
                        }
                    }
                }
                else if (e.getKeyCode() == KeyEvent.VK_3) {
                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 5; j++) {
                            if (farmer.intersects(plotArray[i][j])) {
                                if (score >= Wheat.COST) {
                                    if (plotArray[i][j].isEmpty()) {
                                        plotArray[i][j].setWheat(plotArray[i][j].getXPos(), 
                                                plotArray[i][j].getYPos(), LAND_WIDTH, LAND_HEIGHT);
                                        score -= Wheat.COST;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            public void keyReleased(KeyEvent e) {
                farmer.v_x = 0;
                farmer.v_y = 0;
            }
        });

        this.status = status;
        this.scoreLabel = scoreLabel;
    }

    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {

        farmer = new Farmer(LAND_WIDTH, LAND_HEIGHT);
        instantiatePlotArray();
        zombie = new Zombie(LAND_WIDTH, LAND_HEIGHT);

        playing = true;
        status.setText("Running...");

        scoreLabel.setText("Score: " + Integer.toString(score));

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }

    public void instantiatePlotArray() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                plotArray[i][j] = new SinglePlot(LAND_WIDTH, LAND_HEIGHT,  (LAND_WIDTH / (5)) * i, 
                        (LAND_HEIGHT / (5)) * j, null);
            }
        }
    }
    
    public void chase() {
        int x = farmer.pos_x;
        int y = farmer.pos_y;
        
        if (zombie.pos_x > x) {
            zombie.v_x = -ZOMBIE_VELOCITY;
        } else {
            zombie.v_x = ZOMBIE_VELOCITY;
        }
        if (zombie.pos_y > y) {
            zombie.v_y = -ZOMBIE_VELOCITY;
        } else {
            zombie.v_y = ZOMBIE_VELOCITY;
        }
        
    }


    /**
     * This method is called every time the timer defined in the constructor
     * triggers.
     */
    void tick() {
        if (playing) {
            // advance the square and snitch in their
            // current direction.
            chase();
            zombie.move();
            farmer.move();

            // make the snitch bounce off walls...
            zombie.bounce(zombie.hitWall());
            // ...and the mushroom
            //            zombie.bounce(zombie.hitObj(poison));

            // check for the game end conditions
            if (farmer.intersects(zombie)) {
                playing = false;
                status.setText("You lose!");

            } 


            scoreLabel.setText("Score: " + Integer.toString(score));

            // update the display
            repaint();
        }
    }

    void tock() {
        if (playing) {
            //go through all plots,  decrement plant timers
            if (pause == false) {
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 5; j++) {
                        if (plotArray[i][j].getPlant() != null) {
                            //TODO: add decrement statements
                            if (plotArray[i][j].isGrowing()) {
                                plotArray[i][j].getPlant().decToGrowth();
                                System.out.println("growing - 1");
                            } else if (plotArray[i][j].isFullGrown()) {
                                plotArray[i][j].getPlant().decToRot();
                                System.out.println("rotting - 1");
                            } else if (plotArray[i][j].isRotting()) {
                                System.out.println("totally rotten");
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        farmer.draw(g);
        for (int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++) {
                plotArray[i][j].draw(g);
            }
        }
        zombie.draw(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(LAND_WIDTH, LAND_HEIGHT);
    }
}
