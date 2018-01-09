/**
 * This class will store the information of an individual tile
 */
public class Tile {

	private boolean covered;
	private int numNeighbors;
	private boolean isMine;
	private boolean flagged;

	/**
	 * Sets tile.covered to true, indicating that a tile on the board is covered.
	 */
	public Tile() {
		covered = true;
	}

	/**
	 * Returns true for isCovered if a tile is covered, and false if a tile has been
	 * uncovered.
	 * 
	 * @return
	 */
	public boolean isCovered() {
		return covered;
	}

	/**
	 * Takes a boolean input and sets the private instance variable covered to that
	 * boolean input, indicating whether a tile is covered.
	 * 
	 * @param covered
	 */
	public void setCovered(boolean covered) {
		this.covered = covered;
	}

	/**
	 * Returns the number of mine neighbors a tile has.
	 * 
	 * @return
	 */
	public int getnumNeighbors() {
		return numNeighbors;
	}

	/**
	 * Sets the private instance variable of the number of mine neighbors to an
	 * input.
	 * 
	 * @param numNeighbors
	 */
	public void setnumNeighbors(int numNeighbors) {
		this.numNeighbors = numNeighbors;
	}

	/**
	 * Returns true if a tile contains a mine, and false if a tile does not contain
	 * a mine.
	 * 
	 * @return
	 */
	public boolean isMine() {
		return isMine;
	}

	/**
	 * Sets private instance variable isMine to a boolean input.
	 * 
	 * @param isMine
	 */
	public void setMine(boolean isMine) {
		this.isMine = isMine;
	}

	/**
	 * Sets private instance variable flagged to true if it has not been uncovered
	 * or flagged, and false if it has been flagged by the user.
	 */
	public void setFlag() {
		if (flagged) {
			this.flagged = false;
		} else {
			this.flagged = true;
		}
	}

	/**
	 * Returns true or false for the boolean flagged.
	 * 
	 * @return
	 */
	public boolean getFlag() {
		return flagged;
	}

}