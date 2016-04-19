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

    // Game constants
    public static final int LAND_WIDTH = 500;
    public static final int LAND_HEIGHT = 500;
    public static final int FARMER_VELOCITY = 4;
    public static final int ZOMBIE_VELOCITY = 3;
    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 35;



    public FarmLand(JLabel status) {
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
                                if (plotArray[i][j].isEmpty()) {
                                    //pause the game
                                    pause = true;
                                    status.setText("Paused");
                                    zombie.v_x = 0;
                                    zombie.v_y = 0;
                                    //show a menu with its own keylisteners?
                                    //if time- create menu option, otherwise just do 1,2,3
                                    
                                    
                                    //How do I wait for user input?

                                    //after selection, unpause game
                                    status.setText("Running...");
                                    pause = false;
                                } else if (plotArray[i][j].isRotting()) {
                                    plotArray[i][j].plant = null;
                                } else if (plotArray[i][j].isFullGrown()){
                                    score += plotArray[i][j].plant.harvestProfit;
                                    plotArray[i][j].plant = null;
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

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }

    public void instantiatePlotArray() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                plotArray[i][j] = new SinglePlot(LAND_WIDTH, LAND_HEIGHT, 
                        LAND_WIDTH / (i + 1), LAND_HEIGHT / (j + 1), null);
//                System.out.println("initialized plot: " + plotArray[i][j]);
            }
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
            //            else if (farmer.intersects(snitch)) {
            //                playing = false;
            //                status.setText("You win!");
            //            }

            // update the display
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        farmer.draw(g);
        for (int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++) {
//                System.out.println("drawing plot: " + plotArray[i][j]);
//                System.out.println("drawing i:" + Integer.toString(i));
//                System.out.println("drawing j:" + Integer.toString(j));
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
