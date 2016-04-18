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
    private Poison poison; // the Poison Mushroom, doesn't move

    public boolean playing = false; // whether the game is running
    private JLabel status; // Current status text (i.e. Running...)

    // Game constants
    public static final int COURT_WIDTH = 300;
    public static final int COURT_HEIGHT = 300;
    public static final int SQUARE_VELOCITY = 4;
    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 35;

    public FarmLand(JLabel status) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

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
                    farmer.v_x = -SQUARE_VELOCITY;
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                    farmer.v_x = SQUARE_VELOCITY;
                else if (e.getKeyCode() == KeyEvent.VK_DOWN)
                    farmer.v_y = SQUARE_VELOCITY;
                else if (e.getKeyCode() == KeyEvent.VK_UP)
                    farmer.v_y = -SQUARE_VELOCITY;
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

        farmer = new Farmer(COURT_WIDTH, COURT_HEIGHT);
        poison = new Poison(COURT_WIDTH, COURT_HEIGHT);
        zombie = new Zombie(COURT_WIDTH, COURT_HEIGHT);

        playing = true;
        status.setText("Running...");

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
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
            zombie.bounce(zombie.hitObj(poison));

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
        poison.draw(g);
        zombie.draw(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
}
