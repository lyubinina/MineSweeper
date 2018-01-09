/**
 * This class will contain draw information as well as the main
 */

public class MineSweeper {
	
	/** Logical model of the Game*/
	private MineSweeperModel model;
	
	/** Number of tiles in a Row*/
	private int boardWidth;
	
	/** Number of mines in the board*/
	private int mines;
	
	/** Stores the board coordinates of the previous click */
	private int[] lastClick = { -1, -1 };
	
	/** Keeps track of the time counter*/
	private int time;
	
	/** Stores the number of placed flags*/
	private int flags;
	
	/** Keeps track whether the mouse unlcicks to allow the time counter to work properly*/
	private boolean unClick;

	/**
	 * Runs the MineSweeper game.
	 */
	public static void main(String[] args) {
		new MineSweeper().run();
	}

	/**
	 * Calls the selectBoardSize method and creates an instance of MineSweeperModel.
	 */
	public MineSweeper() {
		selectBoardSize();
		model = new MineSweeperModel(boardWidth, boardWidth, mines);
	}

	/**
	 * Draws all elements of the board including different colored numbers, mines,
	 * flags, the smiley face, time counter and mine counter.
	 */
	public void draw() {
		StdDraw.clear(StdDraw.WHITE);
		// draws board
		for (int i = 0; i < model.getBoardWidth(); ++i) {
			for (int j = 0; j < model.getBoardWidth(); ++j) {
				double scaledX = 0.1 + (0.8 / (model.getBoardWidth() * 2) + i * (0.8 / model.getBoardWidth()));
				double scaledY = (0.8 / (model.getBoardWidth() * 2) + j * (0.8 / model.getBoardWidth()));
				StdDraw.setPenColor(StdDraw.RED);
				if (model.getTile(i, j).isMine()) {
					// places mines on board
					if (model.gameOver() == 1 && lastClick[0] == i && lastClick[1] == j) {
						StdDraw.setPenColor(StdDraw.RED);
						StdDraw.filledSquare(scaledX, scaledY, 0.8 / (2 * model.getBoardWidth()));
					}
					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.filledCircle(scaledX, scaledY, 0.8 / (boardWidth * 4));
					for (int k = 0; k < 6; ++k) {
						double[] w = { scaledX + Math.sin(k * Math.PI / 3) * (0.8 / (boardWidth * 5)),
								scaledX + Math.sin(k * Math.PI / 3 + Math.PI / 3) * (0.8 / (boardWidth * 5)),
								scaledX + Math.sin(k * Math.PI / 3 + Math.PI / 6) * (0.8 / (boardWidth * 2)) };
						double[] z = { scaledY + Math.cos(k * Math.PI / 3) * (0.8 / (boardWidth * 5)),
								scaledY + Math.cos(k * Math.PI / 3 + Math.PI / 3) * (0.8 / (boardWidth * 5)),
								scaledY + Math.cos(k * Math.PI / 3 + Math.PI / 6) * (0.8 / (boardWidth * 2)) };
						StdDraw.filledPolygon(w, z);
					}
					StdDraw.setPenColor(StdDraw.WHITE);
					StdDraw.filledCircle(scaledX - 0.8 / (boardWidth * 10), scaledY + 0.6 / (boardWidth * 10),
							0.6 / (boardWidth * 12));

				} else if (model.getTile(i, j).getnumNeighbors() > 0) {
					int text = model.getTile(i, j).getnumNeighbors();
					switch (text) {
					case 1:
						StdDraw.setPenColor(StdDraw.BLUE);
						break;
					case 2:
						StdDraw.setPenColor(StdDraw.GREEN);
						break;
					case 3:
						StdDraw.setPenColor(StdDraw.RED);
						break;
					case 4:
						StdDraw.setPenColor(StdDraw.MAGENTA);
						break;
					case 5:
						StdDraw.setPenColor(StdDraw.BOOK_RED);
						break;
					case 6:
						StdDraw.setPenColor(StdDraw.CYAN);
						break;
					case 7:
						StdDraw.setPenColor(StdDraw.BLACK);
						break;
					case 8:
						StdDraw.setPenColor(StdDraw.DARK_GRAY);
						break;
					}
					StdDraw.text(scaledX, scaledY, new Integer(text).toString());
				}

				if (model.getTile(i, j).isCovered()) {
					StdDraw.setPenColor(StdDraw.GRAY);
					StdDraw.filledSquare(scaledX, scaledY, 0.8 / (2 * model.getBoardWidth()));
				}

				if (model.getTile(i, j).getFlag()) {
					// draws flag if a flag is present
					StdDraw.setPenColor(StdDraw.BLACK);
					StdDraw.filledRectangle(scaledX, scaledY, 0.8 / (boardWidth * 20), 0.8 / (boardWidth * 3));
					double[] x = { scaledX, scaledX, scaledX + 0.8 / (boardWidth * 2.5) };
					double[] y = { scaledY, scaledY + 0.8 / (boardWidth * 3), scaledY + 0.8 / (boardWidth * 6) };
					StdDraw.setPenColor(StdDraw.RED);
					StdDraw.filledPolygon(x, y);
				}
				StdDraw.setPenColor(StdDraw.BLACK);
				StdDraw.square(scaledX, scaledY, 0.8 / (2 * model.getBoardWidth()));
			}
		}
		if (model.gameOver() != 1) {
			// draw happy face
			StdDraw.setPenColor(StdDraw.YELLOW);
			StdDraw.filledCircle(.5, 0.9, .04);
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.filledCircle(.485, 0.915, .003);
			StdDraw.filledCircle(.515, 0.915, .003);
			StdDraw.arc(0.50, 0.885, 0.01, 180, 360);
		}
		if (model.gameOver() == 1) {
			// draw sad face
			StdDraw.setPenColor(StdDraw.YELLOW);
			StdDraw.filledCircle(.5, 0.9, .04);
			StdDraw.setPenColor(StdDraw.BLACK);
			StdDraw.filledCircle(.485, 0.915, .003);
			StdDraw.filledCircle(.515, 0.915, .003);
			StdDraw.arc(0.50, 0.885, 0.01, 360, 180);
			//draw game over message
			StdDraw.text(.5, .95, "GAME OVER");
		}

		// sets the location of the time counter
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.rectangle(.7, .875, .125, .05);
		StdDraw.text(.7, .9, "Time Counter");
		StdDraw.text(.7, .85, "" + (time / 1000));
		// sets the location of mine counter
		StdDraw.rectangle(.3, .875, .125, .05);
		StdDraw.text(.3, .9, "Mine Counter");
		StdDraw.text(.3, .85, "" + (mines - flags));

		StdDraw.show();
	}

	/**
	 * Displays the rules of the game, uncovers tiles, runs the time counter, and
	 * draws the game over screen.
	 */
	public void run() {
		StdDraw.enableDoubleBuffering();
		showRules();
		draw();
		while (lastClick[0] == -1) {
			handleCommand();
		}
		while (model.getTile(lastClick[0], lastClick[1]).isMine()) {
			model = new MineSweeperModel(boardWidth, boardWidth, mines);
		}
		model.uncover(lastClick[0], lastClick[1]);
		while (model.gameOver() == 0) {
			StdDraw.pause(100);
			time += 100;
			draw();
			handleCommand();
		}
		drawGameOver();
	}

	/**
	 * Waits for the user to click a mouse button, and stores the location of the
	 * click. It also keeps track of flags.
	 */
	@SuppressWarnings("deprecation")
	public void handleCommand() {
		if(!StdDraw.mousePressed()) {
			unClick = true;
		}

		if (StdDraw.mousePressed() && unClick) {
			// Wait for mouse press

			double x = StdDraw.mouseX();
			double y = StdDraw.mouseY();

			// assigns (x,y) coordinates to a row a column
			int row = (int) Math.floor((x - 0.1) / (0.8 / boardWidth));
			int column = (int) Math.floor((y) / (0.8 / boardWidth));
			if (row >= boardWidth || row < 0 || column >= boardWidth || column < 0) {
				return;
			}

			if (StdDraw.isKeyPressed(java.awt.event.KeyEvent.VK_ALT) && model.getTile(row, column).isCovered()) {
				if (model.getTile(row, column).getFlag()) {
					flags--;
				} else {
					flags++;
				}
				model.getTile(row, column).setFlag();
			}

			else if (model.getTile(row, column).getFlag()) {

			} else {
				model.uncover(row, column);
			}

			lastClick[0] = row;
			lastClick[1] = column;
			unClick = false;
			return;
		}
	}

	/**
	 * Uncovers all the mines when the game is over or indicates win.
	 */
	public void drawGameOver() {
		for (int i = 0; i < model.getBoardWidth(); ++i) {
			for (int j = 0; j < model.getBoardWidth(); ++j) {
				Tile tile = model.getTile(i, j);
				if (tile.isMine() && !tile.getFlag()) {
					if (model.gameOver() == 2) { // win
						tile.setFlag();
					} else { // lose
						tile.setCovered(false);

					}
				}
			}
		}
		draw();
	}

	/**
	 * Provides 3 options of board size to choose from.
	 * 
	 * @return
	 */
	@SuppressWarnings({ "deprecation" })
	public int selectBoardSize() {
		StdDraw.clear();
		StdDraw.text(0.5, 0.75, "MineSweeper");
		StdDraw.text(0.5, 0.5, "Click to select board width");
		StdDraw.text(0.25, 0.25, "8x8");
		StdDraw.text(0.5, 0.25, "16x16");
		StdDraw.text(0.75, 0.25, "20x20");
		StdDraw.show();
		while (!StdDraw.mousePressed()) {
			// Wait for mouse press
		}
		while (StdDraw.mousePressed()) {
			// Wait for mouse release
		}
		double x = StdDraw.mouseX();
		if (x < 0.3) {
			boardWidth = 8;
			mines = 8;
			return this.boardWidth;
		} else if (x < 0.5) {
			boardWidth = 16;
			mines = 40;
			return this.boardWidth;
		} else {
			boardWidth = 20;
			mines = 99;
			return this.boardWidth;
		}
	}

	/**
	 * Displays the rules of the game, and waits for the user to click the mouse to
	 * start the game.
	 */
	@SuppressWarnings("deprecation")
	public void showRules() {
		StdDraw.clear();
		StdDraw.text(0.5, 0.9, "CONCISE RULES OF MINESWEEPER:");
		StdDraw.text(0.5, 0.8, "Click on board to uncover squares.");
		StdDraw.text(0.5, 0.7, "If a square uncovers a mine, the game is over.");
		StdDraw.text(0.5, 0.6, "Place flags on potential mines to win the game.");
		StdDraw.text(0.5, 0.5, "Hold ALT and click to place a flag.");
		StdDraw.text(0.5, 0.3, "CLICK TO CONTINUE");
		StdDraw.show();
		while (!StdDraw.mousePressed()) {
			// Wait for mouse press
		}
		while (StdDraw.mousePressed()) {
			// Wait for mouse release
		}
	}
	
}