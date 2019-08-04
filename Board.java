import java.util.*;

public class Board{
	
	/* Two board representations of Board object: sBoard(String[][]), cBoard(String[][]) */
	
	/* sBoard: The actual state of each square in the board ("s" for "state")
	 * "+": Mine square
	 * "0": Non-mine non-numbered square
	 * "1"..."8": Non-mine numbered square
	 */
	
	/* cBoard: What the player sees as the state of each square in the board ("c" for "cell/see")
	 * "C": Covered square
	 * "F": Flagged square
	 * "M": Uncovered mine square (i.e., last thing the player will see if they lose)
	 * "0": Uncovered, non-mine, non-numbered square
	 * "1"..."8": Uncovered, non-mine, numbered square
	 */
	
	/* sBoard and cBoard MUST be the same size. */
	
	private String[][] sBoard;
	private String[][] cBoard;
	private int nRows;
	private int nCols;
	private int nMines;
	private int totalCells;	
	private Set<Pair> updatePairTS = new TreeSet<Pair>(); //Used during recursion to find
														  //neighboring 0 cells
	
	/**
     * Constructs an instance of Board given number of rows, columns, and mines
     * @param number of rows, columns, and mines
     * @return Board object with initialized sBoard w/ random mines and default cBoard
     * @throws IllegalArgumentException if numRows <= 0, numCols <= 0, numMines < 0, 
     * 									or numMines > numRows*numCols
     */	
	public Board(int numRows, int numCols, int numMines) {
		if ((numRows <= 0) || (numCols <= 0) || (numMines < 0) || (numMines > numRows*numCols)) {
			throw new IllegalArgumentException();
		}
		this.nRows = numRows;
		this.nCols = numCols;
		this.nMines = numMines;
		this.totalCells = nRows*nCols;
		
		List<Integer> randomMines = makeRandomMines();
		this.sBoard = fillInMines(randomMines);
		this.sBoard = fillInNonMines(sBoard);
		this.cBoard = fillInCBoard();

	}
	
	/**
     * Constructs an instance of Board given number of rows, columns, mines, and a given sBoard
     * @param number of rows, columns, mines, and a given sBoard
     * @return Board object with sBoard initialized with given sBoard and default cBoard
     * @throws IllegalArgumentException if numRows <= 0, numCols <= 0, numMines < 0, 
     * 									or numMines > numRows*numCols
     */	
	public Board(int numRows, int numCols, int numMines,
											String[][] stateBoard) {
		if ((numRows <= 0) || (numCols <= 0) || (numMines < 0) || (numMines > numRows*numCols)) {
			throw new IllegalArgumentException();
		}
		this.nRows = numRows;
		this.nCols = numCols;
		this.nMines = numMines;
		this.sBoard = stateBoard;
		this.totalCells = nRows*nCols;
		this.cBoard = fillInCBoard();
	}
	
	public String[][] getSBoard(){
		return sBoard;
	}
	
	public String[][] getCBoard(){
		return cBoard;
	}
	
	public int getNRows() {
		return nRows;
	}

	public int getNCols() {
		return nCols;
	}
	
	public int getNMines() {
		return nMines;
	}
	
	public void setCBoardSquare (int row, int col, String state) {
		cBoard[row][col] = state;
	}
	
	public void replaceSBoard(String[][] stateBoard) {
		sBoard = stateBoard;
	}
	
	public void replaceCBoard(String[][] cellBoard) {
		cBoard = cellBoard;
	}
	
	/**
     * Returns list of randomized indices to place mines
     * @return List<Integer> of indices to place random mines
     * @throws IllegalArgumentException if nMines > totalCells or nMines < 0
     */	
	public List<Integer> makeRandomMines() {
		if ((nMines > totalCells) || (nMines < 0)) {
			throw new IllegalArgumentException();
		}
		List<Integer> randomMinesAL = new ArrayList<Integer>(nMines);
		int i = 0;
		while (i < nMines) {
			Random random = new Random();
			int randomInt = random.nextInt(totalCells);
			if (!(randomMinesAL.contains(randomInt))) {
				randomMinesAL.add(randomInt);
				i++;
			}
		}
		return randomMinesAL;
	}
	
	/**
     * Counts neighboring mines of a certain cell
     * @param row index, column index, sBoard in which mines are placed
     * @return number of mines neighboring the cell at the row, column index
     */	
	public int countMines(int row, int col, String[][] oldSboard) {
		int mineCount = 0;
		for (int a = Math.max(0, row-1); a <= Math.min(nRows-1, row+1); a++) {
			for (int b = Math.max(0, col-1); b <= Math.min(nCols-1, col+1); b++) {
				if (oldSboard[a][b] == "+") {
					mineCount += 1;
				}
			}
		}
		return mineCount;
	}
	
	/**
     * Fills board with mines
     * @param ArrayList of indices where mines are placed
     * @return 2-D String array in which mines are placed
     */	
	public String[][] fillInMines(List<Integer> randomMinesAL){
		String[][] newSBoard = new String[nRows][nCols];
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				int rmIndex = i*nCols + j;
				if (randomMinesAL.contains(rmIndex)) {
					newSBoard[i][j] = "+";
				}
			}
		}
		return newSBoard;
	}
	
	/**
     * Fills board with non-mine cells based on mine placement
     * @param 2-D String array with mines are already placed
     * @return 2-D String array in which mines are placed
     */		
	public String[][] fillInNonMines(String[][] oldSBoard){
		String[][] newSBoard = oldSBoard;
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				if (newSBoard[i][j] != "+") {
					int mineCount = countMines(i, j, newSBoard);
					if (mineCount == 0) {
						newSBoard[i][j] = "0";
					} else {
						newSBoard[i][j] = String.valueOf(mineCount);
					}
				}
			}
		}
		return newSBoard;
	}
	
	/**
     * Fills cBoard with cover "C" cells
     * @return 2-D String array of cover "C" cells
     */		
	public String[][] fillInCBoard(){
		String[][] newCBoard = new String[nRows][nCols];
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				newCBoard[i][j] = "C";
			}
		}
		return newCBoard;
	}
	
	/**
     * Returns whether user has lost game
     * @return boolean determining if cBoard contains a mine "M"
     */		
	public boolean hasLost() {
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				if (cBoard[i][j].equals("M")) {
					return true;
				}
			}
		}
		return false;		
	}
	
	/**
     * Returns whether user has won game
     * @return boolean determining if number of covered cells =
     * 							      number of mines (i.e., user has won) 
     */	
	public boolean hasWon() {
		int coverCount = 0;
		for (int i = 0; i < nRows; i++) {
			for (int j = 0; j < nCols; j++) {
				if ((cBoard[i][j].equals("C")) || (cBoard[i][j].equals("F"))) {
					coverCount += 1;
				}
			}
		}
		return (coverCount == nMines);
	}
	
	/**
     * Updates cBoard based on what user has clicked
     * @param row index, column index,
     * boolean determining whether flag is clicked, boolean determining whether
     * 												(when method is called, should be true) 
     */	
	public void updateCBoard(int row, int col, boolean isFlagClick, boolean isFinishedRec) {
		String state = sBoard[row][col];
		String cell = cBoard[row][col];
		if (isFlagClick) { //"Flag mode" on
			if (cell.equals("C")) {
				cBoard[row][col] = "F"; //Flags a covered cell
			} else if (cell.equals("F")) {
				cBoard[row][col] = "C"; //Unflags a flagged cell
			}
		}
		else {
			if (cell != "F") { //Does not flagged cell when "Flag mode" is off
				if (state.equals("+")) {
					cBoard[row][col] = "M"; //Reveals mine
				} else if (state.equals("0")) {
					cBoard[row][col] = "0"; //Reveals covered cell
					Pair updatePair = new Pair(row, col);
					updatePairTS.add(updatePair); //Adds Pair object storing coordinates to TreeSet
												  //in order to make recursion more efficient and
												  //prevent infinite recursion
					for (int i = Math.max(0, row-1); i <= Math.min(nRows-1, row+1); i++) {
						for (int j = Math.max(0, col-1); j <= Math.min(nCols-1, col+1); j++) {
							if (!((i != row) && (j != col))) {
								Pair updatePairRec = new Pair(i, j);
								if (!(updatePairTS.contains(updatePairRec))) {
									updateCBoard(i, j, false, false); //Performs recursion on
																	  //neighboring 0 cells
								}
							}
						}
					}
				} else {
					cBoard[row][col] = state; //Reveals numbered cell
				}
			}
		}
		if (isFinishedRec) { //Allows TreeSet to clear only after recursion is finished
			updatePairTS.clear();
		}
	}
	
}


