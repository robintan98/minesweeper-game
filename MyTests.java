import static org.junit.Assert.*;
import org.junit.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Iterator;
import org.junit.Test;

public class MyTests {
	
	/*
	 * Things to test:
	 * Board
	 * makeRandomMines - MRM
	 * countMines	   - CM
	 * fillInMines 	   - FIM
	 * fillInNonMines  - FINM
	 * fillInCBoard    - FICB
	 * hasLost         - HL
	 * hasWon          - HW
	 */
	
	@Test(timeout=500)
    public void testBoard() {
        Board b = new Board(8, 6, 4);
        assertEquals("nRows = 8", 8, b.getNRows());
        assertEquals("nCols = 6", 6, b.getNCols());
        assertEquals("nMines = 4", 4, b.getNMines());
    }
	
	@Test(timeout=500)
    public void testBoardInputNegativeRows() {
		try {
			Board b = new Board(-8, 6, 4);
			int numRows = b.getNRows();
            fail("Expected an IllegalArgumentException - negative rows.");
        } catch (IllegalArgumentException e) {    
        }
    }
	
	@Test(timeout=500)
    public void testBoardInputNegativeCols() {
		try {
			Board b = new Board(8, -6, 4);
			int numCols = b.getNCols();
            fail("Expected an IllegalArgumentException - negative columns.");
        } catch (IllegalArgumentException e) {    
        }
    }
	
	@Test(timeout=500)
    public void testBoardInputNegativeMines() {
		try {
			Board b = new Board(8, 6, -4);
			int numMines = b.getNMines();
            fail("Expected an IllegalArgumentException - negative mines.");
        } catch (IllegalArgumentException e) {    
        }
    }
	
	@Test(timeout=500)
    public void testBoardInputTooManyMines() {
		try {
			Board b = new Board(8, 6, 400);
			int numMines = b.getNMines();
            fail("Expected an IllegalArgumentException - negative rows.");
        } catch (IllegalArgumentException e) {    
        }
    }
	
	@Test(timeout=500)
    public void testMRMSize() {
        Board b = new Board(6, 6, 4);
        Collection<Integer> randomMinesOne = b.makeRandomMines();
        Collection<Integer> randomMinesTwo = b.makeRandomMines();
        assertEquals("randomMinesOne size = 4", 4, randomMinesOne.size());
        assertEquals("randomMinesTwo size = 4", 4, randomMinesTwo.size());
    }
	
	@Test(timeout=500)
    public void testMRMRandomness() {
        Board b = new Board(6, 6, 4);
        Collection<Integer> randomMinesOne = b.makeRandomMines();
        Collection<Integer> randomMinesTwo = b.makeRandomMines();
        Collection<Integer> randomMinesThree = b.makeRandomMines();
        assertFalse("randomMines One and Two are different", randomMinesOne.equals(randomMinesTwo));
        assertFalse("randomMines One and Three are different", randomMinesOne.equals(randomMinesThree));
        assertFalse("randomMines Two and Three are different", randomMinesTwo.equals(randomMinesThree));
    }
	
	@Test(timeout=500)
    public void testMRMUniqueness() {
		Board b = new Board(6, 6, 4);
		Collection<Integer> randomMinesOne = b.makeRandomMines();
		Iterator<Integer> it = randomMinesOne.iterator();
		Collection<Integer> randomMinesAL = new ArrayList<Integer>();
		while (it.hasNext()){
			int nextInt = it.next();
			if (!(randomMinesAL.contains(nextInt))) {
				randomMinesAL.add(nextInt);
			}
		}
		assertEquals("# of unique mines = 4", 4, randomMinesAL.size());
    }
	
	@Test(timeout=500)
    public void testMRMTooManyMines() {
		try {
			Board b = new Board(6, 6, 40);
			Collection<Integer> randomMinesOne = b.makeRandomMines();
            fail("Expected an IllegalArgumentException - more mines than total cells.");
        } catch (IllegalArgumentException e) {    
        }
    }
	
	@Test(timeout=500)
    public void testMRMNegativeMines() {
		try {
			Board b = new Board(6, 6, -4);
			Collection<Integer> randomMinesOne = b.makeRandomMines();
            fail("Expected an IllegalArgumentException - negative mines.");
        } catch (IllegalArgumentException e) {    
        }
    }
	
	@Test(timeout=500)
    public void testCMOneNeighboringMine() {
		Board b = new Board(4, 4, 1);
		List<Integer> randomMines = new ArrayList<Integer>(1);
		randomMines.add(6);
		String[][] bWithMines = b.fillInMines(randomMines);
		assertEquals("One neighboring mine", 1, b.countMines(1, 1, bWithMines));
    }
	
	@Test(timeout=500)
    public void testCMOneNeighboringMineInCorner() {
		Board b = new Board(4, 4, 1);
		List<Integer> randomMines = new ArrayList<Integer>(1);
		randomMines.add(0);
		String[][] bWithMines = b.fillInMines(randomMines);
		assertEquals("One neighboring mine in corner", 1, b.countMines(0, 1, bWithMines));
    }
	
	@Test(timeout=500)
    public void testCMCornerOneNeighboringMine() {
		Board b = new Board(4, 4, 1);
		List<Integer> randomMines = new ArrayList<Integer>(1);
		randomMines.add(1);
		String[][] bWithMines = b.fillInMines(randomMines);
		assertEquals("Corner has one neighboring mine", 1, b.countMines(0, 0, bWithMines));
    }
	
	@Test(timeout=500)
    public void testCMMultipleMines() {
		Board b = new Board(4, 4, 3);
		List<Integer> randomMines = new ArrayList<Integer>(3);
		randomMines.add(1);
		randomMines.add(2);
		randomMines.add(3);
		String[][] bWithMines = b.fillInMines(randomMines);
		assertEquals("Corner has multiple mines", 3, b.countMines(1, 2, bWithMines));
    }
	
	@Test(timeout=500)
    public void testCMEightMines() {
		Board b = new Board(4, 4, 3);
		List<Integer> randomMines = new ArrayList<Integer>(3);
		randomMines.add(4);
		randomMines.add(5);
		randomMines.add(6);
		randomMines.add(8);
		randomMines.add(10);
		randomMines.add(12);
		randomMines.add(13);
		randomMines.add(14);		
		String[][] bWithMines = b.fillInMines(randomMines);
		assertEquals("Corner has one neighboring mine", 8, b.countMines(2, 1, bWithMines));
    }
	
	@Test(timeout=500)
    public void testFIMZeroMines() {
		Board b = new Board(4, 4, 0);
		String[][] bWithMines;
		List<Integer> randomMines = new ArrayList<Integer>(0);
		bWithMines = b.fillInMines(randomMines);
		String bString = Grid.arrayToString(bWithMines);
		assertTrue("bString contains no mines", bString.indexOf("+") == -1);
    }
	
	@Test(timeout=500)
    public void testFIMOneMine() {
		Board b = new Board(4, 4, 1);
		String[][] bWithMines;
		List<Integer> randomMines = new ArrayList<Integer>(1);
		randomMines.add(0);
		bWithMines = b.fillInMines(randomMines);
		assertEquals("Mine at [0][0]", "+", bWithMines[0][0]);
		randomMines.clear();
		randomMines.add(9);
		bWithMines = b.fillInMines(randomMines);
		assertEquals("Mine at [2][1]", "+", bWithMines[2][1]);
		randomMines.clear();
		randomMines.add(15);
		bWithMines = b.fillInMines(randomMines);
		assertEquals("Mine at [3][3]", "+", bWithMines[3][3]);
    }
	
	@Test(timeout=500)
    public void testFIMMultipleMines() {
		Board b = new Board(4, 4, 3);
		String[][] bWithMines;
		List<Integer> randomMines = new ArrayList<Integer>(3);
		randomMines.add(1);
		randomMines.add(11);
		randomMines.add(15);
		bWithMines = b.fillInMines(randomMines);
		assertEquals("Mine at [0][1]", "+", bWithMines[0][1]);
		assertEquals("Mine at [2][3]", "+", bWithMines[2][3]);
		assertEquals("Mine at [3][3]", "+", bWithMines[3][3]);
    }
	
	@Test(timeout=500)
    public void testFINMZeroMines() {
		Board b = new Board(4, 4, 0);
		List<Integer> randomMines = new ArrayList<Integer>(0);
		String[][] bWithMines = b.fillInMines(randomMines);
		bWithMines = b.fillInNonMines(bWithMines);
		String str = "0000"
					+"0000"
					+"0000"
					+"0000";
		String[][] board = Grid.stringToArray(str, 4, 4);
		for(int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				assertEquals("sBoard with zero mines", bWithMines[i][j], board[i][j]);
			}
		}
    }
	
	@Test(timeout=500)
    public void testFINMOneMine() {
		Board b = new Board(4, 4, 1);
		List<Integer> randomMines = new ArrayList<Integer>(1);
		randomMines.add(5);
		String[][] bWithMines = b.fillInMines(randomMines);
		bWithMines = b.fillInNonMines(bWithMines);
		String str = "1110"
					+"1+10"
					+"1110"
					+"0000";
		String[][] board = Grid.stringToArray(str, 4, 4);
		for(int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				assertEquals("sBoard with one mine", bWithMines[i][j], board[i][j]);
			}
		}
    }
	
	@Test(timeout=500)
    public void testFINMMultipleMines() {
		Board b;
		List<Integer> randomMines;
		String[][] bWithMines;
		String[][] board;
		String str;
		b = new Board(4, 4, 2);
		randomMines = new ArrayList<Integer>(2);
		randomMines.add(4);
		randomMines.add(15);
		bWithMines = b.fillInMines(randomMines);
		bWithMines = b.fillInNonMines(bWithMines);
		str = 		 "1100"
					+"+100"
					+"1111"
					+"001+";
		board = Grid.stringToArray(str, 4, 4);
		for(int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				assertEquals("sBoard with multiple mines, first test", bWithMines[i][j], board[i][j]);
			}
		}
		b = new Board(4, 4, 3);
		randomMines = new ArrayList<Integer>(3);
		randomMines.add(4);
		randomMines.add(9);
		randomMines.add(12);
		bWithMines = b.fillInMines(randomMines);
		bWithMines = b.fillInNonMines(bWithMines);
		str = 		 "1100"
					+"+210"
					+"3+10"
					+"+210";
		board = Grid.stringToArray(str, 4, 4);
		for(int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				assertEquals("sBoard with multiple mines, second test", bWithMines[i][j], board[i][j]);
			}
		}		
		b = new Board(4, 4, 4);
		randomMines = new ArrayList<Integer>(4);
		randomMines.add(4);
		randomMines.add(5);
		randomMines.add(9);
		randomMines.add(10);
		bWithMines = b.fillInMines(randomMines);
		bWithMines = b.fillInNonMines(bWithMines);
		str = 		 "2210"
					+"++31"
					+"3++1"
					+"1221";
		board = Grid.stringToArray(str, 4, 4);
		for(int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				assertEquals("sBoard with multiple mines, third test", bWithMines[i][j], board[i][j]);
			}
		}		
    }
	
	@Test(timeout=500)
    public void testFICBAllCovered() {
		Board b = new Board(4, 4, 0);
		String[][] bFilledCBoard = b.fillInCBoard();
		String str =    "CCCC"
					   +"CCCC"
				       +"CCCC"
				       +"CCCC";
		String[][] board = Grid.stringToArray(str, 4, 4);
		for(int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				assertEquals("cBoard all covered", bFilledCBoard[i][j], board[i][j]);
			}
		}
    }
	
	@Test(timeout=500)
    public void testFICBDifferentDimensions() {
		Board b = new Board(4, 5, 0);
		String[][] bFilledCBoard = b.fillInCBoard();
		String str =    "CCCCC"
					   +"CCCCC"
				       +"CCCCC"
				       +"CCCCC";
		String[][] board = Grid.stringToArray(str, 4, 5);
		for(int i = 0; i < 4; i++) {
			for (int j = 0; j < 5; j++) {
				assertEquals("cBoard all covered, different dimensions",
												bFilledCBoard[i][j], board[i][j]);
			}
		}
    }
	
	@Test(timeout=500)
    public void testHLNoMinePresent() {
		Board b = new Board(4, 4, 2);
		String[][] bFilledCBoard = b.fillInCBoard();
		b.replaceCBoard(bFilledCBoard);
		assertFalse("hasLost, no mine present", b.hasLost());
    }
	
	@Test(timeout=500)
    public void testHLMinePresent() {
		Board b = new Board(4, 4, 2);
		String[][] bFilledCBoard = b.fillInCBoard();
		bFilledCBoard[2][2] = "M";
		b.replaceCBoard(bFilledCBoard);
		assertTrue("hasLost, mine present", b.hasLost());
    }
	
	@Test(timeout=500)
    public void testHLCornerMinePresent() {
		Board b = new Board(4, 4, 2);
		String[][] bFilledCBoard = b.fillInCBoard();
		bFilledCBoard[0][0] = "M";
		b.replaceCBoard(bFilledCBoard);
		assertTrue("hasLost, corner mine present", b.hasLost());
    }
	
	@Test(timeout=500)
    public void testHWNoMinePresent() {
		Board b = new Board(4, 4, 0);
		String[][] bFilledCBoard = b.fillInCBoard();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				bFilledCBoard[i][j] = "0";
			}
		}
		b.replaceCBoard(bFilledCBoard);
		assertTrue("hasWon, no mine present", b.hasWon());
    }
	
	@Test(timeout=500)
    public void testHWMinesPresent() {
		Board b = new Board(4, 4, 3);
		String str =    "01C1"
				   	   +"1221"
			           +"1F21"
			           +"12F1";
	    String[][] board = Grid.stringToArray(str, 4, 4);
		b.replaceCBoard(board);
		assertTrue("hasWon, mines present, flags present", b.hasWon());
    }
	
	@Test(timeout=500)
    public void testHWNotAllMinesUncovered() {
		Board b = new Board(4, 4, 1);
		String str =    "01C1"
				   	   +"1221"
			           +"1F21"
			           +"12F1";
	    String[][] board = Grid.stringToArray(str, 4, 4);
		b.replaceCBoard(board);
		assertFalse("hasWon, not all mines uncovered", b.hasWon());
	}
	
}
