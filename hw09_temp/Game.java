/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleContext;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
	public void run() {
		// NOTE : recall that the 'final' keyword notes inmutability
		// even for local variables.

		// Top-level frame in which game components live
		// Be sure to change "TOP LEVEL FRAME" to the name of your game
		final JFrame frame = new JFrame("TOP LEVEL FRAME");
		frame.setLocation(300, 300);

		// Status panel
		final JPanel status_panel = new JPanel();
		frame.add(status_panel, BorderLayout.SOUTH);
		final JLabel status = new JLabel("Running...");
		status_panel.add(status);
		
		// Score panel
		final JPanel score_panel = new JPanel();
		frame.add(score_panel, BorderLayout.NORTH);
		final JLabel score = new JLabel("0");
		score_panel.add(score);

		// Main playing area
		final FarmLand farm = new FarmLand(status, score);
		frame.add(farm, BorderLayout.CENTER);

		// Reset button
		final JPanel control_panel = new JPanel();
		frame.add(control_panel, BorderLayout.EAST);
		control_panel.setLayout(new BoxLayout(control_panel, BoxLayout.PAGE_AXIS));

		// Note here that when we add an action listener to the reset
		// button, we define it as an anonymous inner class that is
		// an instance of ActionListener with its actionPerformed()
		// method overridden. When the button is pressed,
		// actionPerformed() will be called.
		final JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				farm.reset();
			}
		});
		control_panel.add(reset);
		
		JButton addScore = new JButton();
		addScore.setText("Add a high score!");
		control_panel.add(addScore);
		
		addScore.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		         String userName = JOptionPane.showInputDialog(frame, 
		                "What is your username?"); 
		         
		         }
		});
		
		JButton instructions = new JButton();
		instructions.setText("Instructions");
		control_panel.add(instructions);
		
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
		                + "\n" + "full grown, costs 10 points and profits 20 points."
		                + "\n" + "Pumpkins take 10 seconds to grow, rots in 6 seconds after it is"
		                + "\n" + "full grown, costs 30 points to grow, and profits 90 points."
		                + "\n" + "Wheat takes 3 seconds to grow, rots in 12 seconds after it is"
		                + "\n" + "full grown, costs 2 points, and profits 4 points."
		                + "\n" + "\n"
		                + "Harvest with enter key when next to a harvestable (full grown) plot"
		                + "\n" + "If a plant is rotten, hit enter to clear the plot."
		                + "\n" + "Don't let the plants rot!"
		                + "\n" + "Don't get eaten by zombies. If you're doing well,"
		                + "\n" + "new zombies will appear.");
		        farm.unpause();
//		        farm.setFocusable(true);
//		        setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
//		        StyleContext sc = new StyleContext();
//		        final DefaultStyledDocument doc = new DefaultStyledDocument(sc);
//		        JTextPane instructPanel = new JTextPane(doc);
		        
//		        try {
//		            String text = "Instructions put here";
//		            doc.insertString(0, text, null);
//		        } catch (Exception ex) {
//		            System.out.println("Exception when reading Instructions");
//		        }
//		        
//		        frame.getContentPane().add(new JScrollPane(instructPanel));
		    }
		});

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
