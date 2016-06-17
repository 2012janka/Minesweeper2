import static org.junit.Assert.*;

import java.util.Random;

import minesweeper.core.Clue;
import minesweeper.core.Field;
import minesweeper.core.GameState;
import minesweeper.core.Mine;

import org.junit.Test;

public class FieldTest { // isSolved, pocet riadkov, pocet stlpcov, pocet min,
							// getMineCount, ci nemam policka hodnoty null,
							// pocet dlazdic typu Clue, ci Clues
							// maju spravne nastavene hodnoty

	static final int ROWS = 9;
	static final int COLUMNS = 9;
	static final int MINES = 10;

	@Test
	public void isSolved() {
		Field field = new Field(ROWS, COLUMNS, MINES);

		assertEquals(GameState.PLAYING, field.getState());

		int open = 0;
		for (int row = 0; row < field.getRowCount(); row++) {
			for (int column = 0; column < field.getColumnCount(); column++) {
				if (field.getTile(row, column) instanceof Clue) {
					field.openTile(row, column);
					open++;
				}
				if (field.getRowCount() * field.getColumnCount() - open == field
						.getMineCount()) {
					assertEquals(GameState.SOLVED, field.getState());
				} else {
					assertNotSame(GameState.FAILED, field.getState());
				}
			}
		}

		assertEquals(GameState.SOLVED, field.getState());
	}

	@Test
	public void testGenerateNumberOfRows() {

		Random cislo = new Random();
		int rows = cislo.nextInt(20) + 1;
		int columns = cislo.nextInt(20) + 1;
		int mines = cislo.nextInt(rows * columns);
		System.out.println("riadky: " + rows + " stlpce: " + columns
				+ " miny: " + mines);

		Field testField = new Field(rows, columns, mines);
		assertEquals(rows, testField.getRowCount());
	}

	@Test
	public void testGenerateNumberOfColumns() {

		Random cislo = new Random();
		int rows = cislo.nextInt(20) + 1;
		int columns = cislo.nextInt(20) + 1;
		int mines = cislo.nextInt(rows * columns);
		System.out.println("riadky: " + rows + " stlpce: " + columns
				+ " miny: " + mines);

		Field testField = new Field(rows, columns, mines);

		assertEquals(columns, testField.getColumnCount());
	}

	@Test
	public void testGenerateNumberOfMines() {
		int pocetMinVTestovacomPoli = 0;

		Random cislo = new Random();
		int rows = cislo.nextInt(20) + 1;
		int columns = cislo.nextInt(20) + 1;
		int mines = cislo.nextInt(rows * columns);
		System.out.println("riadky: " + rows + " stlpce: " + columns
				+ " miny: " + mines);

		Field testField = new Field(rows, columns, mines);
		for (int m = 0; m < testField.getRowCount(); m++) {
			for (int n = 0; n < testField.getColumnCount(); n++) {
				if (testField.getTile(m, n) instanceof Mine) {
					pocetMinVTestovacomPoli++;
				}
			}
		}
		assertEquals(pocetMinVTestovacomPoli, mines);
	}

	@Test
	public void testGenerateMineCount() {
		int pocetMinVTestovacomPoli = 0;

		Random cislo = new Random();
		int rows = cislo.nextInt(20) + 1;
		int columns = cislo.nextInt(20) + 1;
		int mines = cislo.nextInt(rows * columns);
		System.out.println("riadky: " + rows + " stlpce: " + columns
				+ " miny: " + mines);

		Field testField = new Field(rows, columns, mines);
		for (int m = 0; m < testField.getRowCount(); m++) {
			for (int n = 0; n < testField.getColumnCount(); n++) {
				if (testField.getTile(m, n) instanceof Mine) {
					pocetMinVTestovacomPoli++;
				}
			}
		}
		assertEquals(pocetMinVTestovacomPoli, testField.getMineCount());
	}

	@Test
	public void testGenerateNotNull() {
		Random cislo = new Random();
		int rows = cislo.nextInt(20) + 1;
		int columns = cislo.nextInt(20) + 1;
		int mines = cislo.nextInt(rows * columns);
		System.out.println("riadky: " + rows + " stlpce: " + columns
				+ " miny: " + mines);

		Field testField = new Field(rows, columns, mines);
		for (int m = 0; m < testField.getRowCount(); m++) {
			for (int n = 0; n < testField.getColumnCount(); n++) {
				assertNotNull(testField.getTile(m, n));
			}
		}
	}

	@Test
	public void testGenerateNumberOfClues() {
		int clueCount = 0;

		Random cislo = new Random();
		int rows = cislo.nextInt(20) + 1;
		int columns = cislo.nextInt(20) + 1;
		int mines = cislo.nextInt(rows * columns);
		System.out.println("riadky: " + rows + " stlpce: " + columns
				+ " miny: " + mines);

		Field testField = new Field(rows, columns, mines);
		for (int m = 0; m < testField.getRowCount(); m++) {
			for (int n = 0; n < testField.getColumnCount(); n++) {
				if (testField.getTile(m, n) instanceof Clue) {
					clueCount++;
				}
			}
		}
		assertEquals(rows * columns - mines, clueCount);
	}

	@Test
	public void testGenerateClues() {
		Random cislo = new Random();
		int rows = cislo.nextInt(20) + 1;
		int columns = cislo.nextInt(20) + 1;
		int mines = cislo.nextInt(rows * columns);

		System.out.println("riadky: " + rows + " stlpce: " + columns
				+ " miny: " + mines);
		int testClue = 0;

		Field testField = new Field(rows, columns, mines);

		for (int m = 0; m < testField.getRowCount(); m++) {
			for (int n = 0; n < testField.getColumnCount(); n++) {
				if (testField.getTile(m, n) instanceof Clue) {
					testClue = ((Clue) testField.getTile(m, n)).getValue();
					int count = 0;
					for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
						int actRow = m + rowOffset;
						if (actRow >= 0 && actRow < testField.getRowCount()) {
							for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
								int actColumn = n + columnOffset;
								if (actColumn >= 0
										&& actColumn < testField
												.getColumnCount()) {
									if (testField.getTile(actRow, actColumn) instanceof Mine) {
										count++;
									}
								}
							}
						}
					}
					assertEquals(testClue, count);
				}
			}
		}
	}
}
