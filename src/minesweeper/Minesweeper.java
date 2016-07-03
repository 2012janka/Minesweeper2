package minesweeper;

import minesweeper.consoleui.ConsoleUI;
import minesweeper.core.Field;

/**
 * Main application class.
 */
public class Minesweeper {
	/** User interface. */
	private UserInterface userInterface;
	private long startMillis;
	private BestTimes bestTimes = new BestTimes();
	private static Minesweeper instance;
	private Settings setting;

	/**
	 * Constructor.
	 */
	private Minesweeper() {
		instance = this;

		userInterface = new ConsoleUI();

		Field field = new Field(Settings.load().getRowCount(), Settings.load().getColumnCount(),
				Settings.load().getMineCount());

		startMillis = System.currentTimeMillis();

		userInterface.newGameStarted(field);
	}

	public int getPlayingSeconds() {
		return (int) (System.currentTimeMillis() - startMillis) / 1000;
	}

	/**
	 * Main method.
	 * 
	 * @param args
	 *            arguments
	 */
	public static void main(String[] args) {
		new Minesweeper();
	}

	public BestTimes getBestTimes() {
		return bestTimes;
	}

	public void setBestTimes(BestTimes bestTimes) {
		this.bestTimes = bestTimes;
	}

	public static Minesweeper getInstance() {
		return instance;
	}

	public Settings getSetting() {
		return setting;
	}

	public void setSetting(Settings setting) {
		this.setting = setting;
		setting.save();
	}
}
