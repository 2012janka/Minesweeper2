package minesweeper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;

/**
 * Player times.
 */
public class BestTimes implements Iterable<BestTimes.PlayerTime> {
	/** List of best player times. */
	private List<PlayerTime> playerTimes = new ArrayList<PlayerTime>();

	/**
	 * Returns an iterator over a set of best times.
	 * 
	 * @return an iterator
	 */
	public Iterator<PlayerTime> iterator() {
		return playerTimes.iterator();
	}

	/**
	 * Adds player time to best times.
	 * 
	 * @param name
	 *            name of the player
	 * @param time
	 *            player time in seconds
	 */
	public void addPlayerTime(String name, int time) {
		
		PlayerTime player = new PlayerTime(name, time);
		playerTimes.add(player);
		try {
			insertToDB(player);
		} catch (SQLException e) {
			System.out.println("Chyba pri ukladani casu: "+e.getMessage());
		}
		Collections.sort(playerTimes);
	}

	/**
	 * Returns a string representation of the object.
	 * 
	 * @return a string representation of the object
	 */
	public String toString() {
		selectFromDB();
		Formatter f = new Formatter();
		int index = 0;
		for (PlayerTime p : playerTimes) {
			index++;
			f.format("%d. Name: %s \nTime: %d", index, p.getName(), p.getTime());
		}
		//f.close();
		return f.toString();
	}

	void reset() {
		playerTimes.clear();
	}

	private void insertToDB(PlayerTime playerTime) throws SQLException {
		// Class.forName(DatabaseSetting.DRIVER_CLASS);
		Connection connection = DriverManager.getConnection(DatabaseSetting.URL, DatabaseSetting.USER,
				DatabaseSetting.PASSWORD);
		Statement stm = connection.createStatement();
		try {
			stm.executeUpdate(DatabaseSetting.QUERY_CREATE_BEST_TIMES);
		} catch (Exception e) {
			// do not propagate exception, table may already exist
		}
		stm.close();

		PreparedStatement pstm = connection.prepareStatement(DatabaseSetting.QUERY_ADD_BEST_TIME);
		try {
			pstm.setString(1, playerTime.getName());
			pstm.setInt(2, playerTime.getTime());
			pstm.execute();
		} catch (Exception e) {
			System.out.println("Exception occured during saving high score to database: " + e.getMessage());
		}
		pstm.close();
	}

	private void selectFromDB() {
		// Class.forName(DatabaseSetting.DRIVER_CLASS);
		try (Connection connection = DriverManager.getConnection(DatabaseSetting.URL, DatabaseSetting.USER,
				DatabaseSetting.PASSWORD);
				Statement stm = connection.createStatement();
				ResultSet rs = stm.executeQuery(DatabaseSetting.QUERY_SELECT_BEST_TIMES);) {
			while (rs.next()) {
				PlayerTime pt = new PlayerTime(rs.getString(1), rs.getInt(2));
				playerTimes.add(pt);
			}
		} catch (Exception e) {
			System.out.println("Exception occured during loading high score to database: " + e.getMessage());
		}
	}

	/**
	 * Player time.
	 */
	public static class PlayerTime implements Comparable<PlayerTime> {
		/** Player name. */
		private final String name;

		/** Playing time in seconds. */
		private final int time;

		/**
		 * Constructor.
		 * 
		 * @param name
		 *            player name
		 * @param time
		 *            playing game time in seconds
		 */
		public PlayerTime(String name, int time) {
			this.name = name;
			this.time = time;
		}

		private String getName() {
			return name;
		}

		private int getTime() {
			return time;
		}

		public int compareTo(PlayerTime o) {
			return this.getTime() - o.getTime();
		}

	}
}
