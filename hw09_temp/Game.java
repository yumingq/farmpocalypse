/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import javax.swing.*;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleContext;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
//Questions: complex intersection confusion, zombie timer
public class Game implements Runnable {
    public void run() {
        final JFrame frame = new JFrame("FarmPocalypse");
        frame.setLocation(200, 200);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Running...");
        status_panel.add(status);
        final JLabel timer = new JLabel("0");
        status_panel.add(timer);

        // Score & coin panel
        final JPanel score_panel = new JPanel();
        frame.add(score_panel, BorderLayout.NORTH);
        final JLabel score = new JLabel("0");
        final JLabel coins = new JLabel("8");
        score_panel.add(score);
        score_panel.add(coins);
        
        

        // Main playing area
        final FarmLand farm = new FarmLand(status, score, coins, timer);
        frame.add(farm, BorderLayout.CENTER);

        // Control Panel
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.EAST);
        
        //short instructions on the side for reading while playing if necessary
        final JLabel shortInstruct = new JLabel("<html> Press 1 to plant Strawberries"
                + "<br> grows in 6 sec"
                + "<br> rots in 8 sec"
                + "<br> costs 10 coins "
                + "<br> profits 20 coins"
                + "<br>"
                + "<br> Press 2 to plant Pumpkins"
                + "<br> grows in 10 sec"
                + "<br> rots in 6 sec"
                + "<br> costs 30 coins"
                + "<br> profits 90 coins"
                + "<br>"
                + "<br> Press 3 to plant Wheat"
                + "<br> grows in 10 sec"
                + "<br> rots in 12 sec"
                + "<br> costs 2 coins"
                + "<br> profits 4 coins"
                + "</html>");
        //set a vertical layout for control panel
        control_panel.setLayout(new BoxLayout(control_panel, BoxLayout.Y_AXIS));
        shortInstruct.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        
 
        // reset button
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                farm.reset();
            }
        });
        reset.setAlignmentX(Component.CENTER_ALIGNMENT);
        control_panel.add(reset);
        
        //add high score button
        final JButton addScore = new JButton();
        addScore.setText("Add a high score!");
        addScore.setAlignmentX(Component.CENTER_ALIGNMENT);
        control_panel.add(addScore);
        addScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = JOptionPane.showInputDialog(frame, 
                        "What is your username?"); 
                try {
                    //try reading out the username they input
                    Reader in = new BufferedReader(new FileReader("highscorebase.txt"));
                    Writer out = new BufferedWriter(new FileWriter("highscores.txt"));
                    HighScores newScore = new HighScores(farm.getScore());
                    newScore.processDocument(in, out, userName); 
                    in.close();
                    out.close();
                    
                    //updates the base document to track the new scores
                    Reader in2 = new BufferedReader(new FileReader("highscores.txt"));
                    Writer out2 = new BufferedWriter(new FileWriter("highscorebase.txt"));
                    newScore.updateDoc(in2, out2);
                    in2.close();
                    out2.close();
                } catch (IOException ex) {
                    System.out.println("Exception with reading/adding high score "
                            + ex.getMessage());
                } catch (HighScores.FormatException formatEx) {
                    System.out.println("Exception with format of high score");
                }
                
                try {
                    //displaying high score messages
                    Reader in = new FileReader("highscores.txt");
                    BufferedReader reader = new BufferedReader(in);
                    
                    String text = "High Scores!" + System.getProperty("line.separator")
                    + System.getProperty("line.separator");

                    while(reader.ready()) {
                        text = text + reader.readLine();
                        text = text + System.getProperty("line.separator");
                    }

                    JOptionPane.showMessageDialog(frame, text); 
                    in.close();
                    reader.close();
                } catch (Exception ex2) {
                    System.out.println("Exception with writing high scores out");
                } 
            }
        });

        
        //create button to display instructions
        final JButton instructions = new JButton();
        instructions.setText("Instructions");
        instructions.setAlignmentX(Component.CENTER_ALIGNMENT);
        control_panel.add(instructions);

        //add instructions
        instructions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                farm.pause();
                JOptionPane.showMessageDialog(frame, "Instructions:" + "\n" + "\n"
                        + "You are a farmer in the apocalypse. Your calling is growing crops" 
                        + "\n" + "and you're not about to let any zombies stop you." + "\n"
                        + "It takes a few seconds for crops to grow, and if you wait too" + "\n"
                        + "long to harvest, they will rot. You must stand at a plot to plant or"
                        + "\n" + "harvest there. It also costs a few points to grow crops, so at"
                        + "\n" + "first you can probably only afford to grow wheat." + "\n" + "\n"
                        + "Move with arrow keys." + "\n"
                        + "Plant strawberries with the 1 key." + "\n"
                        + "Plant pumpkins with the 2 key." + "\n"
                        + "Plant wheat with the 3 key." + "\n"
                        + "Strawberries take 6 seconds to grow, rots in 8 seconds after it is"
                        + "\n" + "full grown, costs 10 coins and profits 20 coins."
                        + "\n" + "Pumpkins take 10 seconds to grow, rots in 6 seconds after it is"
                        + "\n" + "full grown, costs 30 coins to grow, and profits 90 coins."
                        + "\n" + "Wheat takes 3 seconds to grow, rots in 12 seconds after it is"
                        + "\n" + "full grown, costs 2 coins, and profits 4 coins."
                        + "\n" + "\n"
                        + "Harvest with enter key when next to a harvestable (full grown) plot"
                        + "\n" + "If a plant is rotten, hit enter to clear the plot."
                        + "\n" + "Don't let the plants rot!"
                        + "\n" + "Don't get eaten by zombies. If you're doing well,"
                        + "\n" + "new zombies will appear.");
                farm.unpause();
                farm.requestFocusInWindow();
            }
        });
        
        //add the short instructions (from earlier) to the bottom of control panel
        control_panel.add(shortInstruct);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        farm.reset();
        
        
    }

    /*
     * Main method run to start and run the game Initializes the GUI elements
     * specified in Game and runs it IMPORTANT: Do NOT delete! You MUST include
     * this in the final submission of your game.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}
