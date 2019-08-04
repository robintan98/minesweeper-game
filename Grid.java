import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Grid extends JPanel {
	
	private LinkedList<String[][]> cBoardList; //LinkedList to store game states (for undo)
	private Board currentBoard; //Current Board object
	private String[][] currentCBoard;
	private String[][] blankCBoard;
	private int clickedRow = -1;
    private int clickedCol = -1;
    private boolean isFlagClick = false;
    private Image[] images;
	private boolean playing = false; // whether the game is running 
    private JLabel status; // Current status text, i.e. "Running..."
    
    //Game constants
    private int width;
    private int height;
    private int rows;
    private int cols;
    private int mines;
    private int cellHeight;
    private int cellWidth;
    
    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 35;
    
    public Grid(JLabel status, int inWidth, int inHeight, int inRows, int inCols, int inMines) {
    	this.width = inWidth;
    	this.height = inHeight;
    	this.rows = inRows;
    	this.cols = inCols;
    	this.mines = inMines;
    	this.cellHeight = height/rows;
    	this.cellWidth = width/cols;
    	
    	currentBoard = new Board(rows, cols, mines);
    	currentCBoard = currentBoard.getCBoard();
    	cBoardList = new LinkedList<String[][]>();
    	
    	blankCBoard = new String[rows][cols];
    	blankCBoard = currentBoard.fillInCBoard();    	
    	cBoardList.add(Grid.copyStringBoard(blankCBoard));
    	
        // creates border around the Grid area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Timer triggers an action periodically with the given INTERVAL.
        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start();

        // Enable keyboard focus on the Grid area.
        setFocusable(true);

        // This mouse listener allows Grid to know which square has been clicked, as well as
        // whether or not it was flagged.       
        addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
        		if (playing) {
	        		if (e.getButton() == MouseEvent.BUTTON3) {
	        			isFlagClick = true; //Toggles "Flag mode" if right button is clicked
	        		}
	        		int clickedX = e.getX();
	        		int clickedY = e.getY();
	        		
	        		clickedRow = clickedX/cellHeight; //Calculates which row has been clicked by user
	        		clickedCol = clickedY/cellWidth;  //Calculates which column has been clicked by user
	        		
	        		//Add a copy of the cBoard to cBoardList if something noticeable has changed
	        		String[][] tempCBoard = Grid.copyStringBoard(currentCBoard);
	        		currentBoard.updateCBoard(clickedRow, clickedCol, isFlagClick, true);
	            	currentCBoard = currentBoard.getCBoard();
	            	if (!(Grid.compareStringBoard(tempCBoard, currentCBoard))) {
	            		String[][] addedCBoard = new String[rows][cols];
	            		addedCBoard = Grid.copyStringBoard(currentCBoard);
	                	cBoardList.add(addedCBoard);
	            	}
		        	isFlagClick = false;
        		}
        	}
        });
        
        images = new Image[12];
        
        for (int i = 0; i < 12; i++) {
        	String path;
        	if (i == 9) {
        		path = "files\\C.png";
        	} else if (i == 10) {
        		path = "files\\F.png";
        	} else if (i == 11) {
        		path = "files\\M.png";
        	} else {
        		path = "files\\" + i + ".png";
        	}
        	images[i] = (new ImageIcon(path)).getImage(); //Uploads images into images array
        }
        
        this.status = status;
        
    }
    
    /**
     * Converts an String array to a single String, reading left-to-right and top-to-bottom
     * @param 2-D String array
     * @return String with each character being an element in 2-D String array, read l-to-r and t-to-b
     */	
    public static String arrayToString(String[][] board) {
    	String str = "";
    	for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				str += board[i][j];
			}
		}
    	return str;
    }
    
    /**
     * Converts a String to a 2-D String array
     * @param String, number of rows, and number of columns
     * @return 2-D String array with each character of String being assigned to an element in 2-D array
     */	
    public static String[][] stringToArray(String str, int numRows, int numCols) {
    	if (str.length() != numRows*numCols) {
    		throw new IllegalArgumentException();
    	}
    	String[][] board = new String[numRows][numCols];
    	for (int i = 0; i < str.length(); i++) {
    		board[i / numCols][Math.floorMod(i, numCols)] = Character.toString(str.charAt(i));
    	}
    	return board;
    }
    
    /**
     * Creates a newly instantiated copy of String board with exact same elements and size
     * @param 2-D String array to be copied
     * @return Copied identical but different 2-D String array
     */	
    public static String[][] copyStringBoard(String[][] oldBoard){
    	String[][] newBoard = new String[oldBoard.length][oldBoard[0].length];
    	for (int i = 0; i < oldBoard.length; i++) {
			for (int j = 0; j < oldBoard[0].length; j++) {
				newBoard[i][j] = oldBoard[i][j];
			}
		}
    	return newBoard;
    }
    
    /**
     * Compares two 2-D String arrays for identical size and elements
     * @param Two 2-D String arrays to be compared
     * @return Returns true if two String arrays have the exact same size and elements, false otherwise
     */	
    public static boolean compareStringBoard(String[][] boardOne, String[][] boardTwo){
    	if ((boardOne.length != boardTwo.length) || (boardOne[0].length != boardTwo[0].length)) {
    		return false;
    	}
    	for (int i = 0; i < boardOne.length; i++) {
			for (int j = 0; j < boardOne[0].length; j++) {
				if (boardOne[i][j] != boardTwo[i][j]) {
					return false;
				}
			}
		}
    	return true;
    }
    
    /**
     * Starts new game, re-randomizes board, clears LinkedList, repaints
     */	
    public void newGame() {
    	currentBoard = new Board(rows, cols, mines);
    	currentCBoard = currentBoard.getCBoard();
    	cBoardList.clear();

        playing = true;
        status.setText("Running...");

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
        repaint();
    }

    /**
     * Covers board again but does not re-randomize board, clears LinkedList, repaints
     */	
    public void reset() {
    	currentBoard.replaceCBoard(Grid.copyStringBoard(blankCBoard));
		currentCBoard = Grid.copyStringBoard(blankCBoard);
		cBoardList.clear();

        playing = true;
        status.setText("Running...");

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
        repaint();
    }
    
    /**
     * When pushed by user, reverts to previous move, removes last element of LinkedList, repaints
     */
    public void undo() {
    	playing = true;
    	status.setText("Running...");
    	if (cBoardList.size() > 1) {
    		cBoardList.removeLast();
			currentBoard.replaceCBoard(Grid.copyStringBoard(cBoardList.getLast()));
			currentCBoard = cBoardList.getLast();
    	} else {
    		currentBoard.replaceCBoard(Grid.copyStringBoard(blankCBoard));
			currentCBoard = Grid.copyStringBoard(blankCBoard);
			cBoardList.clear();
    	}
    	requestFocusInWindow();
    	repaint();
    }
    
    /**
     * Called every time the timer defined in the constructor triggers.
     */
    void tick() {
        if (playing) {
            // check for the game end conditions
            if (currentBoard.hasLost()) {
                playing = false;
                status.setText("You lose!");
            } else if (currentBoard.hasWon()) {
                playing = false;
                status.setText("You win!");
            }
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //For each cell, paints appropriate image according to value of String in cell and size of cell
        for (int i = 0; i < rows; i++) {
        	for (int j = 0; j < cols; j++) {
        		if (currentCBoard[i][j].equals("C")) {
        			g.drawImage(images[9], i*cellHeight, j*cellWidth, cellHeight, cellWidth, this);
        		} else if (currentCBoard[i][j].equals("F")) {
        			g.drawImage(images[10], i*cellHeight, j*cellWidth, cellHeight, cellWidth, this);
        		} else if (currentCBoard[i][j].equals("M")) {
        			g.drawImage(images[11], i*cellHeight, j*cellWidth, cellHeight, cellWidth, this);
        		} else {
        			int imageNum = Integer.parseInt(currentCBoard[i][j]);
        			g.drawImage(images[imageNum], i*cellHeight, j*cellWidth,
        											cellHeight, cellWidth, this);
        		}
        	}
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }
	    
}
