import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Game implements Runnable {
    public void run() {

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Minesweeper");
        frame.setLocation(100, 100);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Minesweeper Running...");
        status_panel.add(status);

        // Main playing grid
        // Constructs Grid object with parameters
        final Grid grid = new Grid(status, 360, 360, 12, 12, 24);
        frame.add(grid, BorderLayout.CENTER);

        // Control panel
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Instructions button: Pop-up dialog displaying game instructions and controls
        String instructionMessage = 
        		"Welcome to Minesweeper!\n\n"
        		+ "Minesweeper is a single-player puzzle game with the objective of clearing all mines "
        		+ "on a rectangular grid. \nYou will get clues about the locations of mines based on "
        		+ "how many mines are neighboring a certain cell. \nYou can also flag cells that you "
        		+ "believe might contain a mine. \nYou win when you clear all non-mine cells in the "
        		+ "grid.\n\nControls: \nLeft click to reveal a covered cell. \nRight click to flag a "
        		+ "covered cell. \nClicking on a covered cell hiding a mine will result in a loss. "
        		+ "\nGood luck!";
        final JButton instructions = new JButton("Instructions");
        instructions.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	JOptionPane.showMessageDialog(null, instructionMessage,
            							"Minesweeper Instructions", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        control_panel.add(instructions);
        
        // New game button: Starts new game, re-randomizes board
        final JButton new_game = new JButton("New Game");
        new_game.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	grid.newGame();
            }
        });
        control_panel.add(new_game);
        
        // Reset button: Covers board, but does not re-randomize board        
        final JButton reset_button = new JButton("Reset");
        reset_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	grid.reset();
            }
        });
        control_panel.add(reset_button);
        
        // Undo Button: When pushed by user, reverts to previous move
        final JButton undo = new JButton("Undo");
        undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	grid.undo();
            }
        });
        control_panel.add(undo);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        grid.newGame();
    }

    /**
     * Main method run to start and run the game. Initializes the GUI elements specified in Game and
     * runs it.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
    
}