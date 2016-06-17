package minesweeper.core;

import java.util.Random;

/**
 * Field represents playing field and game logic.
 */
public class Field {
	/**
	 * Playing field tiles.
	 */
	private final Tile[][] tiles;

	/**
	 * Field row count. Rows are indexed from 0 to (rowCount - 1).
	 */
	private final int rowCount;

	/**
	 * Column count. Columns are indexed from 0 to (columnCount - 1).
	 */
	private final int columnCount;

	/**
	 * Mine count.
	 */
	private final int mineCount;

	/**
	 * Game state.
	 */
	private GameState state = GameState.PLAYING;

	/**
	 * Constructor.
	 *
	 * @param rowCount
	 *            row count
	 * @param columnCount
	 *            column count
	 * @param mineCount
	 *            mine count
	 */
	public Field(int rowCount, int columnCount, int mineCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		this.mineCount = mineCount;
		tiles = new Tile[rowCount][columnCount];

		// generate the field content
		generate();
	}

	/**
	 * Opens tile at specified indeces.
	 *
	 * @param row
	 *            row number
	 * @param column
	 *            column number
	 */

	public GameState getState() {
		return state;
	}

	public Tile[][] getTiles() {
		return tiles;
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public int getMineCount() {
		return mineCount;
	}

	public Tile getTile(int row, int column) {
		return tiles[row][column];
	}

	public void openTile(int row, int column) {
		Tile tile = tiles[row][column];
		if (tile.getState() == Tile.State.CLOSED) {
			tile.setState(Tile.State.OPEN);
			if (tile instanceof Mine) {
				state = GameState.FAILED;
				return;
			}

			if (isSolved()) {
				state = GameState.SOLVED;
				return;
			}
			
			if (tile instanceof Clue && ((Clue) tiles[row][column]).getValue() == 0) {
				openAdjacentTiles(row, column);
				//return;
			}
		}
	}

	/**
	 * Marks tile at specified indeces.
	 *
	 * @param row
	 *            row number
	 * @param column
	 *            column number
	 */
	public void markTile(int row, int column) {
		Tile tile = tiles[row][column];
		if (tile.getState() == Tile.State.CLOSED) {
			tile.setState(Tile.State.MARKED);
		}

		else if (tile.getState() == Tile.State.MARKED) {
			tile.setState(Tile.State.CLOSED);
		}
	}

	/**
	 * Generates playing field.
	 */
	private void generate() {
		int i = 0;
		while (i < mineCount) {
			Random cislo1 = new Random();
			Random cislo2 = new Random();

			int row = cislo1.nextInt(rowCount);
			int column = cislo2.nextInt(columnCount);
			if (tiles[row][column] == null) {
				tiles[row][column] = new Mine();
				// tiles[row][column].setState(Tile.State.OPEN);
				i++;
			}
		}
		for (int m = 0; m < this.getRowCount(); m++) {
			for (int n = 0; n < this.getColumnCount(); n++) {
				if (tiles[m][n] == null) {
					tiles[m][n] = new Clue(countAdjacentMines(m, n));
					// tiles[m][n].setState(Tile.State.OPEN);
				}
			}
		}
	}

	/**
	 * Returns true if game is solved, false otherwise.
	 *
	 * @return true if game is solved, false otherwise
	 */
	private boolean isSolved() {
		int pocetVsetkychDlazdic = this.getRowCount() * this.getColumnCount();
		int pocetOdokrytychDlazdic = getNumberOf(Tile.State.OPEN);
		if (pocetVsetkychDlazdic - pocetOdokrytychDlazdic == mineCount) {
			return true;
		}
		return false;
	}

	/**
	 * Returns number of adjacent mines for a tile at specified position in the
	 * field.
	 *
	 * @param row
	 *            row number.
	 * @param column
	 *            column number.
	 * @return number of adjacent mines.
	 */
	private int countAdjacentMines(int row, int column) {
		int count = 0;
		for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
			int actRow = row + rowOffset;
			if (actRow >= 0 && actRow < rowCount) {
				for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
					int actColumn = column + columnOffset;
					if (actColumn >= 0 && actColumn < columnCount) {
						if (tiles[actRow][actColumn] instanceof Mine) {
							count++;
						}
					}
				}
			}
		}
		return count;
	}

	private int getNumberOf(Tile.State state) {
		int pocet = 0;
		for (int m = 0; m < this.getRowCount(); m++) {
			for (int n = 0; n < this.getColumnCount(); n++) {
				if (this.getTile(m, n).getState() == state) {
					pocet++;
				}
			}
		}
		return pocet;
	}

	private void openAdjacentTiles(int row, int column) {
		for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
			int actRow = row + rowOffset;
			if (actRow >= 0 && actRow < rowCount) {
				for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
					int actColumn = column + columnOffset;
					if (actColumn >= 0 && actColumn < columnCount) {
						if (tiles[actRow][actColumn] instanceof Clue) {
							openTile(actRow,actColumn);
						}
					}
				}
			}
		}
	}
	
	public int getRemainingMineCount(){
		return mineCount - this.getNumberOf(Tile.State.MARKED);
	}
}
