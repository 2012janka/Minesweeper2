package minesweeper;

public class DatabaseSetting {
    public static final String DRIVER_CLASS = "org.apache.derby.jdbc.ClientDriver";
    public static final String URL = "jdbc:derby://localhost/minesweeper";
    public static final String USER = "minesweeper";
    public static final String PASSWORD = "minesweeper";

    public static final String QUERY_CREATE_BEST_TIMES = "CREATE TABLE player_time (name VARCHAR(128) NOT NULL, best_time INT NOT NULL)";
    public static final String QUERY_ADD_BEST_TIME = "INSERT INTO player_time (name, best_time) VALUES (?, ?)";
    public static final String QUERY_SELECT_BEST_TIMES = "SELECT name, best_time FROM player_time";

    private DatabaseSetting() {}
}