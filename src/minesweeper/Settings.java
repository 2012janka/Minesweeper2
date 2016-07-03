package minesweeper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Settings implements Serializable {
	private final int rowCount;
	private final int columnCount;
	private final int mineCount;

	public static final Settings BEGINNER = new Settings(9, 9, 10);
	public static final Settings INTERMEDIATE = new Settings(16, 16, 40);
	public static final Settings EXPERT = new Settings(16, 30, 99);

	private static final String SETTING_FILE = System.getProperty("user.home") + System.getProperty("file.separator")
			+ "minesweeper.settings";

	public Settings(int rowCount, int columnCount, int mineCount) {
		super();
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		this.mineCount = mineCount;
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

	public boolean equals(Object o) {
		if (o instanceof Settings && this.getRowCount() == ((Settings) o).getRowCount()
				&& this.getColumnCount() == ((Settings) o).getColumnCount()
				&& this.getMineCount() == ((Settings) o).getMineCount()) {
			return true;
		} else {
			return false;
		}
	}

	public int hashCode() {
		return getRowCount() * getColumnCount() * getMineCount();
	}

	public void save() {
		try (FileOutputStream fos = new FileOutputStream(SETTING_FILE);
				ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Settings load() {
		try (FileInputStream fis = new FileInputStream(SETTING_FILE);
				ObjectInputStream ois = new ObjectInputStream(fis)) {
			return (Settings) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return Settings.BEGINNER;
		}
	}
}
