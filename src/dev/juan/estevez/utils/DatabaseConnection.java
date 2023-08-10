package dev.juan.estevez.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	/**
	 * Connects to the local database.
	 *
	 * @return the connection object
	 * @throws SQLException if there was an error connecting to the database
	 */
	public static Connection connect() throws SQLException {
		try {
			String url = "jdbc:mysql://localhost/estevez_corporation";
			String user = "root";
			String password = "";
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException ex) {
			throw new SQLException("Error connecting to the local database", ex);
		}
	}

}
