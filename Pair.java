/**
 * PairClass: Helper class solely used to combine two integers (row, column) into one object
 * Implements Comparable interface
 * Includes compareTo method to compare two Pairs "lexicographically" (used in TreeSet)
 */

@SuppressWarnings("rawtypes")
public class Pair implements Comparable{
	private int rowC;
	private int colC;
	
	public Pair(int rowCoordinate, int colCoordinate) {
		this.rowC = rowCoordinate;
		this.colC = colCoordinate;
	}
	
	public int getRowC() {
		return rowC;
	}
	
	public int getColC() {
		return colC;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		Pair otherPair = (Pair) o;
		if (this.rowC < otherPair.getRowC()) {
			return -1;
		} else if (this.rowC > otherPair.getRowC()) {
			return 1;
		} else {
			if (this.colC < otherPair.getColC()) {
				return -1;
			} else if (this.colC > otherPair.getColC()) {
				return 1;
			} else {
				return 0;
			}
		}
	}
	
}