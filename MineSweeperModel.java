/**
 * This class will store the information on the board
 */
public class MineSweeperModel {
/**Stores coordinates of tiles on board.*/
	private Tile[][] board;
	/**Number of tiles in a row.*/
	private int xDimension;
	/**Number of tiles in a column.	 */
	private int yDimension;
	/**Used for generation of neighbors. */
	public static final int[][] OFFSETS = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 }, { -1, -1 }, { 1, 1 }, { 1, -1 },
			{ -1, 1 } };
	/**Indicates if game is still going, a loss or a win. */
	public int gameOver;
	/**Stores the number of mines. */
	public int mines;
	/**Used in game over to check for winning or losing condition. */
	public int revealed;

	/**
	 * Takes board size and number of mines and creates the board, setting the mines
	 * and neighbors of mines by calling those setter methods.
	 * 
	 * @param xDimension
	 * @param yDimension
	 * @param mines
	 */
	public MineSweeperModel(int xDimension, int yDimension, int mines) {
		this.board = new Tile[xDimension][yDimension];
		for (int i = 0; i < xDimension; i++) {
			for (int j = 0; j < yDimension; j++) {
				board[i][j] = new Tile();
			}
		}
		this.xDimension = xDimension;
		this.yDimension = yDimension;
		this.mines = mines;
		setMines(mines);
		setNeighbors();
	}

	/**
	 * Uncovers a tile, and sets off a chain uncover if tile has no mine neighbors.
	 * Sets gameOver to true if uncovering a mine.
	 */
	public void uncover(int row, int column) {
		if (row < 0 || column < 0 || row == xDimension || column == yDimension) {
			return;
		}
		Tile square = board[row][column];
		if (!square.isCovered() || square.getFlag()) {
			return;
		}
		square.setCovered(false);
		if (square.isMine()) {
			gameOver = 1;
			return;
		}
		revealed++;
		if (revealed == xDimension * yDimension - mines) {
			gameOver = 2;
			return;
		}
		if (square.getnumNeighbors() == 0) { // chain uncover
			for (int[] buddy : OFFSETS) {
				uncover(row + buddy[0], column + buddy[1]);
			}
		}
	}

	/**
	 * Checks the surrounding 8 tiles around a point and records number of
	 * neighboring mines to each tile.
	 */
	public void setNeighbors() {
		for (int x = 0; x < xDimension; x++) {
			for (int y = 0; y < yDimension; y++) {
				int i = 0;
				for (int[] off : OFFSETS) {
					if (x + off[0] > -1 && x + off[0] < xDimension && y + off[1] > -1 && y + off[1] < yDimension) {
						if (board[x + off[0]][y + off[1]].isMine()) {
							i++;
						}
					}
				}
				board[x][y].setnumNeighbors(i);
			}
		}
	}

	/**
	 * Initializes the board with mines. Goes through each row and column then rolls
	 * a random number to decide whether or not to place a mine there.
	 * 
	 */
	public void setMines(int mines) {
		int i = 0;
		while (i < mines) {
			for (Tile[] row : board) {
				for (Tile t : row) {
					int setMine = StdRandom.uniform(1, 10);
					if (setMine == 2 && !t.isMine()) {
						t.setMine(true);
						i++;
					}
					if (i == mines) {
						return;
					}
				}
			}
		}
	}

	/**
	 * Saves the private board size into a public method that can be used between
	 * classes.
	 * 
	 * @return
	 */
	public int getBoardWidth() {
		return xDimension;
	}

	/**
	 * Returns the coordinates of a tile based on row and column inputs.
	 * 
	 * @param row
	 * @param column
	 * @return
	 */
	public Tile getTile(int row, int column) {
		return board[row][column];
	}

	/**
	 * Returns the value of gameOver, which will indicate if the game is still going (0), a win (2) or a loss (1).
	 * 
	 * @return
	 */
	public int gameOver() {
		return gameOver;
	}

}